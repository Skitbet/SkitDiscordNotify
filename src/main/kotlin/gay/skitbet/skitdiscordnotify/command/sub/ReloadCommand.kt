package gay.skitbet.skitdiscordnotify.command.sub

import gay.skitbet.skitdiscordnotify.command.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class ReloadCommand(private val plugin: JavaPlugin) : SubCommand {

    override val name = "reload"
    override val description = "Reload the plugin configuration"

    override fun execute(sender: CommandSender, args: List<String?>) {
        plugin.reloadConfig()
        sender.sendMessage("§aSkitDiscordNotify config has reloaded.")
    }
}