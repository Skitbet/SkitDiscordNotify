package gay.skitbet.skitdiscordnotify

import gay.skitbet.skitdiscordnotify.config.WebhookEventConfig
import gay.skitbet.skitdiscordnotify.placeholder.PlaceholderProcessor
import gay.skitbet.skitdiscordnotify.config.EmbedData
import gay.skitbet.skitdiscordnotify.util.ServerEventType
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object StatusManager {

    private val plugin: JavaPlugin = SkitDiscordNotify.instance
    private val webhookUrl = plugin.config.getString("webhook-url") ?: ""
    private val placeholderProcessor = PlaceholderProcessor()

    fun send(eventKey: ServerEventType, playerName: String? = null, schedule: Boolean = true, ) {
        if (schedule) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                sendWebhook(eventKey, playerName)
            })
            return
        }
        sendWebhook(eventKey, playerName)
    }

    private fun sendWebhook(eventKey: ServerEventType, playerName: String? = null) {
        // fetch the config section related to the status event
        val eventSection = plugin.config.getConfigurationSection("events.${eventKey.configKey}") ?: return
        if (!eventSection.getBoolean("enabled")) return // return if not enabled

        // should we use embed?
        val useEmbed = eventSection.getBoolean("embed")

        val player = playerName?.let { Bukkit.getPlayerExact(it) }
        val messageRaw = eventSection.getString("message") ?: ""
        val message = placeholderProcessor.process(messageRaw, player)

        // generate the payload for the webhook
        val payload = if (useEmbed) {
            // fetch all the embed data
            val embedSection = eventSection.getConfigurationSection("embed-data")
            if (embedSection == null) {
                plugin.logger.warning("Missing `embed-data` section for: ${eventKey.configKey}")
                return
            }

            // TODO: not create the whole object every send lol
            val title = embedSection.getString("title")?.replace("%player%", playerName ?: "") ?: "Status"
            val desc = embedSection.getString("description")?.replace("%player%", playerName ?: "") ?: message
            val color = embedSection.getInt("color", 3447003)

            // create json embed data
            val embedJson = JSONObject()
                .put("title", title)
                .put("description", desc)
                .put("color", color)

            JSONObject().put("embeds", JSONArray().put(embedJson)).toString()
        } else {
            JSONObject().put("content", message).toString()
        }

        // send the webhook
        try {
            val url = URL(webhookUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.doOutput = true

            OutputStreamWriter(connection.outputStream).use { it.write(payload) }

            // handle networking errors
            val responseCode = connection.responseCode
            if (responseCode !in 200..299) {
                val error = connection.errorStream?.bufferedReader()?.readText()
                plugin.logger.warning("Webhook for '${eventKey.configKey}' failed: $responseCode - $error")
            }

            connection.inputStream.close()
        } catch (e: Exception) {
            plugin.logger.warning("Failed to send webhook for '${eventKey.configKey}': ${e.message}")
        }
    }
}