package gay.skitbet.skitDiscordNotify.command

import org.bukkit.command.CommandSender

interface SubCommand {

    val name: String
    val description: String
    fun execute(sender: CommandSender, args: List<String?>)


}