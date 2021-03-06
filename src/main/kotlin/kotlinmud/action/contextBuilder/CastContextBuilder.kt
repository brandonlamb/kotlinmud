package kotlinmud.action.contextBuilder

import kotlinmud.action.model.Context
import kotlinmud.action.type.Status
import kotlinmud.io.type.Syntax

class CastContextBuilder : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        return if (kotlinmud.helper.string.matches("cast", word))
            Context<Any>(syntax, Status.OK, word)
            else
            Context<Any>(syntax, Status.ERROR, "what was that?")
    }
}
