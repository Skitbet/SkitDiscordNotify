package gay.skitbet.skitDiscordNotify.command.sub

import gay.skitbet.skitDiscordNotify.command.SubCommand
import org.bukkit.command.CommandSender

class HelpCommand(private val registeredCommands: List<SubCommand>) : SubCommand {

    override val name = "help"
    override val description = "Display this help message"

    override fun execute(sender: CommandSender, args: List<String?>) {
        sender.sendMessage("§b--- DiscordStatus Help ---")
        for (cmd in registeredCommands) {
            sender.sendMessage("§7/discordstatus ${cmd.name} §f- ${cmd.description}")
        }
    }
}