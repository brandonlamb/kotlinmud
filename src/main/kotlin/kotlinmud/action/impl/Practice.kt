package kotlinmud.action.impl

import kotlinmud.action.helper.mustBeStanding
import kotlinmud.action.model.Action
import kotlinmud.action.type.Command
import kotlinmud.io.factory.skillToPractice
import kotlinmud.io.model.MessageBuilder
import kotlinmud.io.type.Syntax
import kotlinmud.mob.skill.type.SkillType

fun createPracticeAction(): Action {
    return Action(Command.PRACTICE, mustBeStanding(), skillToPractice()) {
        val skillType: SkillType = it.get(Syntax.SKILL_TO_PRACTICE)
        val card = it.getMobCard()
        card.practices -= 1
        it.getMob().skills[skillType] = it.getMob().skills[skillType]!!.plus(1)
        it.createOkResponse(
            MessageBuilder()
                .toActionCreator("you practice $skillType.")
                .toObservers("${it.getMob()} practices $skillType.")
                .build()
        )
    }
}
