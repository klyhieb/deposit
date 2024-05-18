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
    val routWithArg = "$route/{$depositDetailWithTermArg}"
    val argument = listOf(
        navArgument(depositDetailWithTermArg) { type = NavType.StringType }
    )
}

object OpenNewTerm: DepositDestination {
    override val route = "open_new_term"
}

object OpenNewTermConfirm: DepositDestination {
    override val route = "open_new_term_un_ath"
}

object OpenNewTermSuccess: DepositDestination {
    override val route = "open_new_term_ath"
}