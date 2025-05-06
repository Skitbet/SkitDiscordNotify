package gay.skitbet.skitDiscordNotify.command.sub

import gay.skitbet.skitDiscordNotify.command.SubCommand
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