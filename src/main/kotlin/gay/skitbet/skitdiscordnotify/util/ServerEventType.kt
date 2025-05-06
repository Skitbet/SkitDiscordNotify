package gay.skitbet.skitdiscordnotify.util

enum class ServerEventType(val configKey: String) {
    SERVER_START("server_start"),
    SERVER_STOP("server-stop"),
    PLAYER_JOIN("player-join"),
    PLAYER_QUIT("player-quit"),
    PLAYER_DEATH("player-death")
    ;

    override fun toString(): String = configKey
}