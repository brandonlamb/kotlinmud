package kotlinmud.affect.service

import kotlinmud.affect.model.AffectInstance
import kotlinmud.affect.type.AffectType
import kotlinmud.helper.Noun

class AffectService(private val noun: Noun) {
    fun add(affect: AffectInstance) {
        noun.affects.find { it.affectType == affect.affectType }?.let {
            if (it.timeout < affect.timeout) {
                it.timeout += (affect.timeout - it.timeout) / 2
            }
            return
        }
        noun.affects.add(affect)
    }

    fun findByType(affectType: AffectType): AffectInstance? {
        return noun.affects.find { it.affectType == affectType }
    }

    fun decrement() {
        noun.affects.removeIf {
            it.timeout--
            it.timeout < 0
        }
    }

    fun copyFrom(fromNoun: Noun) {
        fromNoun.affects.forEach {
            noun.affects().add(it.copy())
        }
    }

    fun getAffects(): List<AffectInstance> {
        return noun.affects
    }
}
