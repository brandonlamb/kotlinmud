package kotlinmud.player.authStep.impl

import kotlinmud.io.model.PreAuthRequest
import kotlinmud.io.model.PreAuthResponse
import kotlinmud.io.type.IOStatus
import kotlinmud.player.authStep.AuthStep
import kotlinmud.player.authStep.AuthStepService
import kotlinmud.player.authStep.AuthorizationStep

class EmailAuthStep(private val authService: AuthStepService) :
    AuthStep {
    override val authorizationStep: AuthorizationStep =
        AuthorizationStep.EMAIL
    override val promptMessage: String = "email address:"
    override val errorMessage: String = "sorry, try again."

    override fun handlePreAuthRequest(request: PreAuthRequest): PreAuthResponse {
        authService.findPlayerByOTP(request.input)?.let {
            authService.sendOTP(it)
        } ?: run {
            val player = authService.createPlayer(request.input)
            authService.sendOTP(player)
        }
        return PreAuthResponse(request, IOStatus.OK, "ok")
    }

    override fun getNextAuthStep(): AuthStep {
        return PasswordAuthStep(authService)
    }
}
