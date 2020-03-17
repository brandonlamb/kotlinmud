package kotlinmud.action.contextBuilder

import kotlinmud.action.Context
import kotlinmud.action.Status
import kotlinmud.io.Syntax

class CastContextBuilder : ContextBuilder {
    override fun build(syntax: Syntax, word: String): Context<Any> {
        println("word: $word")
        return if (kotlinmud.string.matches("cast", word))
                Context<Any>(syntax, Status.OK, word)
            else
                Context<Any>(syntax, Status.ERROR, "what was that?")
    }
}
