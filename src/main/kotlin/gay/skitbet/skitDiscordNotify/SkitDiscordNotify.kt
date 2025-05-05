package gay.skitbet.skitDiscordNotify

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

        // register this class as events and send start status
        server.pluginManager.registerEvents(this, this)
        webhookSender.send("server-start")
    }

    override fun onDisable() {
        // send stop status
        webhookSender.send("server-stop")
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
