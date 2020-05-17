package kotlinmud.mob.skill.impl

import kotlinmud.action.ActionContextService
import kotlinmud.action.Command
import kotlinmud.action.mustBeAlert
import kotlinmud.affect.AffectInstance
import kotlinmud.affect.AffectType
import kotlinmud.affect.impl.BerserkAffect
import kotlinmud.io.MessageBuilder
import kotlinmud.io.Response
import kotlinmud.io.Syntax
import kotlinmud.mob.Disposition
import kotlinmud.mob.Intent
import kotlinmud.mob.SpecializationType
import kotlinmud.mob.skill.Cost
import kotlinmud.mob.skill.CostType
import kotlinmud.mob.skill.LearningDifficulty
import kotlinmud.mob.skill.SkillAction
import kotlinmud.mob.skill.SkillInvokesOn
import kotlinmud.mob.skill.SkillType

class Berserk : SkillAction {
    override val type: SkillType = SkillType.BERSERK
    override val command: Command = Command.BERSERK
    override val levelObtained: Map<SpecializationType, Int> = mapOf(
        Pair(SpecializationType.WARRIOR, 1),
        Pair(SpecializationType.THIEF, 45)
    )
    override val difficulty: Map<SpecializationType, LearningDifficulty> = mapOf(
        Pair(SpecializationType.WARRIOR, LearningDifficulty.HARD),
        Pair(SpecializationType.THIEF, LearningDifficulty.VERY_HARD)
    )
    override val dispositions: List<Disposition> = mustBeAlert()
    override val costs: List<Cost> = listOf(
        Cost(CostType.MV_PERCENT, 20),
        Cost(CostType.DELAY, 2))
    override val intent: Intent = Intent.NEUTRAL
    override val syntax: List<Syntax> = listOf(Syntax.COMMAND)
    override val invokesOn: SkillInvokesOn = SkillInvokesOn.INPUT
    override val affect = BerserkAffect()

    override fun invoke(actionContextService: ActionContextService): Response {
        actionContextService.getMob().affects().add(
            AffectInstance(
                AffectType.BERSERK,
                actionContextService.getLevel() / 8
            )
        )
        return actionContextService.createResponse(
            MessageBuilder()
                .toActionCreator("Your pulse speeds up as you are consumed by rage!")
                .toObservers("${actionContextService.getMob()}'s pulse speeds up as they are consumed by rage!")
                .build()
        )
    }
}
