package gay.skitbet.skitdiscordnotify.util

enum class ServerEventType(val configKey: String) {
    SERVER_START("server_start"),
    SERVER_STOP("server-stop"),
    PLAYER_JOIN("player-join"),
    PLAYER_QUIT("player-quit");

    override fun toString(): String = configKey
}