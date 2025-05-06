package gay.skitbet.skitdiscordnotify.listeners

import gay.skitbet.skitdiscordnotify.StatusManager
import gay.skitbet.skitdiscordnotify.util.ServerEventType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListeners : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        StatusManager.send(ServerEventType.PLAYER_JOIN, playerName = event.player.name)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        StatusManager.send(ServerEventType.PLAYER_QUIT, playerName = event.player.name)
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        StatusManager.send(ServerEventType.PLAYER_DEATH, playerName = event.entity.name)
    }


}