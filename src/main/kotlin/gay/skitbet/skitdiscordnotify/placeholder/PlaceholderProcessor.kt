package gay.skitbet.skitdiscordnotify.placeholder

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlaceholderProcessor {

    fun process(text: String, player: Player?): String {
        var result = text

        // apply built-in proccessors
        if (player != null) {
            result = result
                .replace("%player%", player.name)
                .replace("%world%", player.world.name)
        }
        result = result
            .replace("%server_online%", Bukkit.getOnlinePlayers().size.toString())
            .replace("%server_max%", Bukkit.getMaxPlayers().toString())

        // apply placeholders if available
        if (player != null && isPlaceholderApiAvailable()) {
            result = PlaceholderAPI.setPlaceholders(player, result)
        }

        return result
    }

    private fun isPlaceholderApiAvailable(): Boolean {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null
    }

}