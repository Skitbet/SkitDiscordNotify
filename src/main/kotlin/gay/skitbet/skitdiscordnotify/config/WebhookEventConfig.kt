package gay.skitbet.skitdiscordnotify.config

import gay.skitbet.skitdiscordnotify.config.EmbedData

data class WebhookEventConfig(
    val enabled: Boolean,
    val embed: Boolean,
    val message: String?,
    val embedData: EmbedData?
)
