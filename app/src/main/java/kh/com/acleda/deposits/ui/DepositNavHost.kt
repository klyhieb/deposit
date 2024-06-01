package kh.com.acleda.deposits.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.modules.closeTerm.presentation.CloseTermSuccessScreen
import kh.com.acleda.deposits.modules.depositList.data.repository.ViewTermDetailRepo
import kh.com.acleda.deposits.modules.depositList.domain.model.ViewTermDetailModel
import kh.com.acleda.deposits.modules.depositList.presentation.DepositDetailScreen
import kh.com.acleda.deposits.modules.depositList.presentation.DepositListScreen
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositMenu
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermTypeEnum
import kh.com.acleda.deposits.modules.eCertificate.domain.model.ECertificateModel
import kh.com.acleda.deposits.modules.eCertificate.presentation.ECertificateScreen
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.HomeScreen
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.OpenTermDepositModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermConfirmScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermSuccessScreen
import kh.com.acleda.deposits.modules.renewal.presentation.RenewalScreen
import kh.com.acleda.deposits.modules.splashScreen.SplashScreen
import kh.com.acleda.deposits.modules.stopRenewal.domain.model.StopRenewalConfirmModel
import kh.com.acleda.deposits.modules.stopRenewal.presentation.StopRenewalConfirmScreen
import kh.com.acleda.deposits.modules.stopRenewal.presentation.StopRenewalSuccessScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DepositNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val gson = Gson()
    val context = LocalContext.current

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
                onClickOpenNewTerm = { termType ->
                    navController.navigateToOpenNewTerm(termType.id)
                }
            )
        }

        composable(route = DepositList.route) {
            DepositListScreen(
                onBackPress = { navController.navigateClearTop(Home.route, navController) },
                onSingleTermClick = { term ->
                    val dataString = gson.toJson(term)
                    navController.navigateToDepositDetail(dataString, isFromCloseRequest = false)
                },
                onPopupMenuClick = { menu, term ->
                    val dataString = gson.toJson(term)
                    when(menu) {
                        DepositMenu.RENEWAL -> {
                            val testModel = ViewTermDetailRepo.getViewTermDetail(context)
                            val modelStr = gson.toJson(testModel)

                            navController.navigateToRenewal(modelStr)
                        }

                        DepositMenu.STOP_RENEWAL -> {
                            val testModel = StopRenewalConfirmModel(
                                depositAmount = "400000",
                                ccy = "KHR",
                                mmNumber = "MM2331400003",
                                depositTypeId = "21011",
                                depositTerm = "36",
                                autoRenewal = "Renewal with principal",
                                rolloverTime = "12",
                                maturityDate = "April 03, 2026",
                                newRolloverTime = "0",
                                newMaturityDate = "April 03, 2024",
                            )

                            val modelStr = gson.toJson(testModel)
                            navController.navigateToStopRenewalConfirm(modelStr)
                        }

                        DepositMenu.E_CERTIFICATE -> {
                            val model = ECertificateModel(
                                termTypeId = term.termTypeId ?: "",
                                mmNumber = term.mm ?: "",
                                depositAmount = term.AmountOri.toString(),
                                ccy = term.currency ?: ""
                            )

                            val modelString = gson.toJson(model)

                            navController.navigateToECertificate(modelString)
                        }

                        DepositMenu.CLOSE_TERM -> {
                            navController.navigateToDepositDetail(term = dataString, isFromCloseRequest = true)
                        }
                    }
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

            val isFromCloseRequest: Boolean = backStackEntry.arguments?.getBoolean(DepositDetail.isFromCloseRequestArg) ?: false

            DepositDetailScreen(
                term = term,
                isFromCloseRequest = isFromCloseRequest,
                onBackPress = { navController.popBackStack() },
                onCloseTermDialogConfirm = {
                    navController.navigateToCloseTermSuccess(totalReceiveAmount = 1.68f, ccy = it.currency)
                }
            )
        }

        composable(
            route = OpenNewTerm.routWithArg,
            arguments = OpenNewTerm.argument
        ) {  backStackEntry ->
            val termTypeId: String = backStackEntry.arguments?.getString(OpenNewTerm.termTypeIdArg) ?: ""
            OpenNewTermScreen(
                termType = getTermTypeEnum(termTypeId),
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

        composable(
            route = CloseTermSuccess.routWithArg,
            arguments = CloseTermSuccess.argument
        ) { backStackEntry ->
            val totalReceivedAmount: Float = backStackEntry.arguments?.getFloat(CloseTermSuccess.totalReceivedArg) ?: 0.0f
            val ccy: String = backStackEntry.arguments?.getString(CloseTermSuccess.ccyArg) ?: ""

            CloseTermSuccessScreen(
                totalReceived = totalReceivedAmount,
                ccy = ccy,
                onClick = {
                    navController.navigateClearTop(DepositList.route, navController)
                }
            )
        }

        composable(
            route = ECertificate.routWithArg,
            arguments = ECertificate.argument
        ) { backStackEntry ->
            val modelObjectString: String = backStackEntry.arguments?.getString(ECertificate.modelArg) ?: ""
            val model: ECertificateModel = gson.fromJson(modelObjectString)

            ECertificateScreen(
                model = model,
                onBackPress = { navController.popBackStack() }
            )
        }

        composable(
            route = Renewal.routWithArg,
            arguments = Renewal.argument
        ) { backStackEntry ->
            val modelObjectString: String = backStackEntry.arguments?.getString(Renewal.modelArg) ?: ""
            val model: ViewTermDetailModel = gson.fromJson(modelObjectString)

            RenewalScreen(
                model = model,
                onBackPress = { navController.popBackStack() },
                onRenewalClick = {
                    Log.e("TAG", "DepositNavHost: $it")
                }
            )
        }

        composable(
            route = StopRenewalConfirm.routWithArg,
            arguments = StopRenewalConfirm.argument
        ) {  backStackEntry ->
            val modelObjectString: String = backStackEntry.arguments?.getString(StopRenewalConfirm.modelArg) ?: ""
            val model: StopRenewalConfirmModel = gson.fromJson(modelObjectString)

            StopRenewalConfirmScreen(
                model = model,
                onBackPress = { navController.popBackStack() },
                onConfirmClick = {
                    navController.navigateSingleTopTo(StopRenewalSuccess.route)
                }
            )
        }

        composable( route = StopRenewalSuccess.route) {
            StopRenewalSuccessScreen(
                onClick = { navController.navigateClearTop(DepositList.route, navController) }
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

private fun NavHostController.navigateToOpenNewTerm(termTypeId: String) {
    this.navigate("${OpenNewTerm.route}/$termTypeId")
}
private fun NavHostController.navigateToDepositDetail(term: String, isFromCloseRequest: Boolean) {
    this.navigate("${DepositDetail.route}/$term?${DepositDetail.isFromCloseRequestArg}=$isFromCloseRequest")
}

private fun NavHostController.navigateToCloseTermSuccess(totalReceiveAmount: Float, ccy: String?) {
    this.navigate("${CloseTermSuccess.route}?${CloseTermSuccess.totalReceivedArg}=$totalReceiveAmount&${CloseTermSuccess.ccyArg}=$ccy")
}

private fun NavHostController.navigateToECertificate(model: String) {
    this.navigate("${ECertificate.route}?${ECertificate.modelArg}=$model")
}

private fun NavHostController.navigateToRenewal(model: String) {
    this.navigate("${Renewal.route}?${Renewal.modelArg}=$model")
}

private fun NavHostController.navigateToStopRenewalConfirm(model: String) {
    this.navigate("${StopRenewalConfirm.route}?${StopRenewalConfirm.modelArg}=$model")
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
