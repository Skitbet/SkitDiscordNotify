package gay.skitbet.skitDiscordNotify

import gay.skitbet.skitDiscordNotify.placeholder.PlaceholderProcessor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class WebhookSender(private val plugin: JavaPlugin) {

    private val webhookUrl = plugin.config.getString("webhook-url") ?: ""
    private val placeholderProcessor = PlaceholderProcessor()

    fun send(eventKey: String, playerName: String? = null, schedule: Boolean = true, ) {
        if (schedule) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                sendWebhook(eventKey, playerName)
            })
            return
        }
        sendWebhook(eventKey, playerName)
    }

    private fun sendWebhook(eventKey: String, playerName: String? = null) {
        // if webhook is not configured then dont send lets not do anyhting
        if (webhookUrl.isBlank()) {
            plugin.logger.warning("Webhook URL is not configured!")
            return
        }

        // fetch the config section related to the status event
        val eventSection = plugin.config.getConfigurationSection("events.${eventKey}") ?: return
        if (!eventSection.getBoolean("enabled")) return // return if not enabled

        // should we use embed?
        val useEmbed = eventSection.getBoolean("embed")

        val player = playerName?.let { Bukkit.getPlayerExact(it) }
        val messageRaw = eventSection.getString("message") ?: ""
        val message = placeholderProcessor.process(messageRaw, player)

        // generate the payload for the webhook
        val payload = if (useEmbed) {
            // fetch all the embed data
            val embedSection = eventSection.getConfigurationSection("embed-data") ?: return
            val title = embedSection.getString("title")?.replace("%player%", playerName ?: "") ?: "Status"
            val desc = embedSection.getString("description")?.replace("%player%", playerName ?: "") ?: message
            val color = embedSection.getInt("color", 3447003)
            """
               {
                 "embeds": [{
                   "title": "$title",
                   "description": "$desc",
                   "color": $color
                 }]
               }
            """.trimIndent()
        } else {
            """{ "content": "$message" }"""
        }

        // send the webhook
        try {
            val url = URL(webhookUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            OutputStreamWriter(connection.outputStream).use { it.write(payload) }
            connection.inputStream.close()
        } catch (e: Exception) {
            plugin.logger.warning("Failed to send webhook for '$eventKey': ${e.message}")
        }
    }

}