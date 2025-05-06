package gay.skitbet.skitdiscordnotify.command

import gay.skitbet.skitdiscordnotify.command.sub.HelpCommand
import gay.skitbet.skitdiscordnotify.command.sub.ReloadCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class DiscordNotifyCommand(private val plugin: JavaPlugin) : CommandExecutor {

    private val subCommands = mutableListOf<SubCommand>()

    init {
        // register subcommands
        subCommands.add(ReloadCommand(plugin))
        subCommands.add(HelpCommand(subCommands))
    }

    override fun onCommand(
        sender: CommandSender,
        p1: Command,
        label: String,
        args: Array<out String?>?
    ): Boolean {
        if (args!!.isEmpty()) {
            sender.sendMessage("§cUsage: /${label} help")
            return true
        }

        val sub = subCommands.find { it.name.equals(args[0], ignoreCase = true) }
        if (sub != null) {
            sub.execute(sender, args.drop(1))
        } else {
            sender.sendMessage("§cUnknown subcommand. Use /$label help")
        }

        return true
    }
}