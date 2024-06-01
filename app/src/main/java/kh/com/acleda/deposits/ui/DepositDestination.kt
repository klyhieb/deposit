package kh.com.acleda.deposits.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Contract for information needed on every Deposit navigation destination
 */

interface DepositDestination {
    val route: String
}

/**
 * Deposit app navigation destinations
 */
object SplashScreen: DepositDestination {
    override val route = "splash_screen"
}

object Home: DepositDestination {
    override val route = "home"
}

object DepositList: DepositDestination {
    override val route = "deposit_list"
}

object DepositDetail: DepositDestination {
    override val route = "deposit_detail"
    const val depositDetailWithTermArg = "term"
    const val isFromCloseRequestArg = "isFromCloseRequest"
    val routWithArg = "$route/{$depositDetailWithTermArg}?$isFromCloseRequestArg={$isFromCloseRequestArg}"
    val argument = listOf(
        navArgument(depositDetailWithTermArg) {
            type = NavType.StringType
        },
        navArgument(isFromCloseRequestArg) {
            defaultValue = false
            type = NavType.BoolType
        }
    )
}

object OpenNewTerm: DepositDestination {
    override val route = "open_new_term"
    const val termTypeIdArg = "termType"
    val routWithArg = "${route}/{$termTypeIdArg}"
    val argument = listOf(
        navArgument(termTypeIdArg) {
            type = NavType.StringType
        }
    )
}

object OpenNewTermConfirm: DepositDestination {
    override val route = "open_new_term_un_ath"
}

object OpenNewTermSuccess: DepositDestination {
    override val route = "open_new_term_ath"
}

object CloseTermSuccess: DepositDestination {
    override val route = "close_term_ath"
    const val totalReceivedArg = "totalReceived"
    const val ccyArg = "ccy"
    val routWithArg = "$route?$totalReceivedArg={$totalReceivedArg}&$ccyArg={$ccyArg}"
    val argument = listOf(
        navArgument(totalReceivedArg) {
            type = NavType.FloatType
            defaultValue = 0.0f
        },
        navArgument(ccyArg) {
            type = NavType.StringType
            nullable = true
        }
    )
}

object Renewal: DepositDestination {
    override val route = "renewal"
    const val modelArg = "modelArg"
    val routWithArg = "${route}?$modelArg={$modelArg}"
    val argument = listOf(
        navArgument(modelArg) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}

object StopRenewalConfirm: DepositDestination {
    override val route = "stop_renewal_un_auth"
    const val modelArg = "modelArg"
    val routWithArg = "${route}?$modelArg={$modelArg}"
    val argument = listOf(
        navArgument(modelArg) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}

object StopRenewalSuccess: DepositDestination {
    override val route = "stop_renewal_auth"
    const val modelArg = "modelArg"
    val routWithArg = "${route}?$modelArg={$modelArg}"
    val argument = listOf(
        navArgument(modelArg) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}

object ECertificate: DepositDestination {
    override val route = "e_certificate"
    const val modelArg = "modelArg"
    val routWithArg = "${route}?$modelArg={$modelArg}"
    val argument = listOf(
        navArgument(modelArg) {
            type = NavType.StringType
            defaultValue = ""
        }
    )
}