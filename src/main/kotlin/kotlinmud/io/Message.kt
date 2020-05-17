package kotlinmud.io

import com.thinkinglogic.builder.annotation.Builder
import com.thinkinglogic.builder.annotation.DefaultValue

@Builder
data class Message(
    @DefaultValue("\"\"") val toActionCreator: String,
    @DefaultValue("\"\"") val toTarget: String,
    @DefaultValue("toTarget") val toObservers: String,
    @DefaultValue("true") val sendPrompt: Boolean
)
