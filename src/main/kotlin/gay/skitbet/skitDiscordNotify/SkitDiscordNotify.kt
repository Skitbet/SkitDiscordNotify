package gay.skitbet.skitDiscordNotify

import gay.skitbet.skitDiscordNotify.command.DiscordNotifyCommand
import gay.skitbet.skitDiscordNotify.config.WebhookEventConfig
import gay.skitbet.skitDiscordNotify.util.EmbedData
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class SkitDiscordNotify : JavaPlugin(), Listener {

    private lateinit var webhookSender: WebhookSender

    override fun onEnable() {
        saveDefaultConfig()
        //create webhook sender instance
        webhookSender = WebhookSender(this)

        // register commands
        getCommand("discordnotify")?.setExecutor(DiscordNotifyCommand(this))

        // register this class as events and send start status
        server.pluginManager.registerEvents(this, this)
        webhookSender.send("server-start")
    }

    override fun onDisable() {
        // send stop status
        webhookSender.send("server-stop", schedule = false)
    }

    fun loadEvents(config: FileConfiguration): Map<String, WebhookEventConfig> {
        val result = mutableMapOf<String, WebhookEventConfig>()
        config.getConfigurationSection("events")?.getKeys(false)?.forEach { key ->
            val section = config.getConfigurationSection("events.$key") ?: return@forEach
            val embedSection = section.getConfigurationSection("embed-data")
            result[key] = WebhookEventConfig(
                enabled = section.getBoolean("enabled", false),
                embed = section.getBoolean("embed", false),
                message = section.getString("message"),
                embedData = if (embedSection != null) {
                    EmbedData(
                        title = embedSection.getString("title"),
                        description = embedSection.getString("description"),
                        color = embedSection.getInt("color", 3447003)
                    )
                } else {
                    null
                }
            )
        }
        return result
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // send join status
        webhookSender.send("player-join", event.player.name)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        // send leave status
        webhookSender.send("player-leave", event.player.name)
    }
}
