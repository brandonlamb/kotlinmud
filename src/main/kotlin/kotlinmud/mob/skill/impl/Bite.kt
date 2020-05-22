package kotlinmud.mob.skill.impl

import kotlin.random.Random
import kotlinmud.action.ActionContextService
import kotlinmud.action.Command
import kotlinmud.action.mustBeAlert
import kotlinmud.affect.Affect
import kotlinmud.io.MessageBuilder
import kotlinmud.io.Response
import kotlinmud.io.Syntax
import kotlinmud.mob.Mob
import kotlinmud.mob.fight.DamageType
import kotlinmud.mob.skill.Cost
import kotlinmud.mob.skill.CostType
import kotlinmud.mob.skill.LearningDifficulty
import kotlinmud.mob.skill.SkillAction
import kotlinmud.mob.skill.SkillInvokesOn
import kotlinmud.mob.skill.SkillType
import kotlinmud.mob.type.Disposition
import kotlinmud.mob.type.Intent
import kotlinmud.mob.type.SpecializationType

class Bite : SkillAction {
    override val type: SkillType = SkillType.BITE
    override val command: Command = Command.BITE
    override val levelObtained: Map<SpecializationType, Int> = mapOf()
    override val difficulty: Map<SpecializationType, LearningDifficulty> = mapOf()
    override val dispositions: List<Disposition> = mustBeAlert()
    override val costs: List<Cost> = listOf(
        Cost(CostType.DELAY, 1),
        Cost(CostType.MV_AMOUNT, 20)
    )
    override val intent: Intent = Intent.OFFENSIVE
    override val syntax: List<Syntax> = listOf(Syntax.COMMAND, Syntax.TARGET_MOB)
    override val invokesOn: SkillInvokesOn = SkillInvokesOn.INPUT
    override val affect: Affect? = null

    override fun invoke(actionContextService: ActionContextService): Response {
        val target: Mob = actionContextService.get(Syntax.TARGET_MOB)
        val limit = (actionContextService.getLevel() / 10).coerceAtLeast(2)
        target.hp -= Random.nextInt(1, limit) +
                if (target.savesAgainst(DamageType.PIERCE)) 0 else Random.nextInt(1, limit)
        return actionContextService.createResponse(
            MessageBuilder()
                .toActionCreator("You bite $target.")
                .toTarget("${actionContextService.getMob()} bites you.")
                .toObservers("${actionContextService.getMob()} bites $target.")
                .build()
        )
    }
}
