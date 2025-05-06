package gay.skitbet.skitDiscordNotify.config

import gay.skitbet.skitDiscordNotify.util.EmbedData

data class WebhookEventConfig(
    val enabled: Boolean,
    val embed: Boolean,
    val message: String?,
    val embedData: EmbedData?
)
