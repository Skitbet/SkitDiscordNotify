package gay.skitbet.skitdiscordnotify

import gay.skitbet.skitdiscordnotify.command.DiscordNotifyCommand
import gay.skitbet.skitdiscordnotify.config.WebhookEventConfig
import gay.skitbet.skitdiscordnotify.config.EmbedData
import gay.skitbet.skitdiscordnotify.util.ServerEventType
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class SkitDiscordNotify : JavaPlugin(), Listener {

    companion object {
        lateinit var instance: SkitDiscordNotify
    }

    override fun onEnable() {
        instance = this
        // if webhook is not configured then dont send lets disable the plugin
        if (config.getString("webhook-url").isNullOrBlank()) {
            logger.warning("Webhook URL is not configured!")
            server.pluginManager.disablePlugin(this)
            return
        }

        saveDefaultConfig()

        // register commands
        getCommand("discordnotify")?.setExecutor(DiscordNotifyCommand(this))

        // register this class as events and send start status
        server.pluginManager.registerEvents(this, this)
        StatusManager.send(ServerEventType.SERVER_START)
    }

    override fun onDisable() {
        // send stop status
        StatusManager.send(ServerEventType.SERVER_START, schedule = false)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // send join status
        StatusManager.send(ServerEventType.PLAYER_JOIN, playerName = event.player.name)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        // send leave status
        StatusManager.send(ServerEventType.PLAYER_QUIT, playerName = event.player.name)
    }
}
