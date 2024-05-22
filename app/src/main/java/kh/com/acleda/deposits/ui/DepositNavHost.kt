package kh.com.acleda.deposits.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import kh.com.acleda.deposits.components.dialog.CloseTermDialog
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.modules.depositList.presentation.DepositDetailScreen
import kh.com.acleda.deposits.modules.depositList.presentation.DepositListScreen
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.HomeScreen
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.OpenTermDepositModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermConfirmScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermSuccessScreen
import kh.com.acleda.deposits.modules.splashScreen.SplashScreen

@Composable
fun DepositNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val gson = Gson()

    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(
            route = SplashScreen.route,
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ) {
            SplashScreen(
                onSplashScreenFinish = {
                    navController.navigateClearTop(Home.route, navController)
                }
            )
        }

        composable(route = Home.route) {
            HomeScreen(
                onClickViewDepositList = {
                    navController.navigateSingleTopTo(DepositList.route)
                },
                onClickOpenNewTerm = {
                    navController.navigateSingleTopTo(OpenNewTerm.route)
                }
            )
        }

        composable(route = DepositList.route) {
            DepositListScreen(
                onBackPress = { navController.navigateClearTop(Home.route, navController) },
                onSingleTermClick = { term ->
                    val dataString = gson.toJson(term)
                    navController.navigateDepositDetailDefault(dataString)
                }
            )
        }

        composable(
            route = DepositDetail.routWithArg,
            arguments = DepositDetail.argument
        ) { backStackEntry ->
            val termObjectString: String =
                backStackEntry.arguments?.getString(DepositDetail.depositDetailWithTermArg) ?: ""
            val term: DepositItemModel = gson.fromJson(termObjectString)
            DepositDetailScreen(
                term = term,
                isFromCloseTermRequested = true,
                onBackPress = { navController.popBackStack() },
                onCloseTermDialogConfirm = { /*TODO*/}
            )
        }

        composable(route = OpenNewTerm.route) {
            OpenNewTermScreen(
                onBackPress = { navController.popBackStack() },
                onClickDeposit = {
                    navController.navigateSingleTopTo(OpenNewTermConfirm.route)
                }
            )
        }

        composable(route = OpenNewTermConfirm.route) {
            OpenNewTermConfirmScreen(
                summary = OpenTermDepositModel(),
                onBackPress = { navController.popBackStack() },
                onClickConfirm = {
                    navController.navigateSingleTopTo(OpenNewTermSuccess.route)
                }
            )
        }

        composable(route = OpenNewTermSuccess.route) {
            OpenNewTermSuccessScreen(
                onClick = {
                    navController.navigateClearTop(DepositList.route, navController)
                }
            )
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

fun NavHostController.navigateClearTop(toRoute: String, navController: NavHostController) =
    this.navigate(toRoute) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
        launchSingleTop = true
    }

private fun NavHostController.navigateDepositDetail(term: String) {
    this.navigateSingleTopTo("${DepositDetail.route}/$term")
}

private fun NavHostController.navigateDepositDetailDefault(term: String) {
    this.navigate("${DepositDetail.route}/$term")
}

// ================================================================================================

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.9f else 1.1f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.9f else 1.1f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 90))
}

enum class ScaleTransitionDirection {
    INWARDS, OUTWARDS
}
