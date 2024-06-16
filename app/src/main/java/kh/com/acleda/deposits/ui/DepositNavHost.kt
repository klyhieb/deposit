package kh.com.acleda.deposits.ui

import android.os.Build
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import kh.com.acleda.deposits.core.calculate.CloseTermCalculator
import kh.com.acleda.deposits.core.calculate.StopRenewalCalculator
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.roundDoubleAmount
import kh.com.acleda.deposits.modules.closeTerm.domain.model.AuthCloseTermModel
import kh.com.acleda.deposits.modules.closeTerm.presentation.CloseTermSuccessScreen
import kh.com.acleda.deposits.modules.depositList.presentation.DepositDetailScreen
import kh.com.acleda.deposits.modules.depositList.presentation.DepositListScreen
import kh.com.acleda.deposits.modules.depositList.presentation.component.DepositMenu
import kh.com.acleda.deposits.modules.depositList.presentation.component.getCcyEnum
import kh.com.acleda.deposits.modules.depositList.presentation.component.getTermTypeEnum
import kh.com.acleda.deposits.modules.eCertificate.domain.model.ECertificateModel
import kh.com.acleda.deposits.modules.eCertificate.presentation.ECertificateScreen
import kh.com.acleda.deposits.modules.home.domain.model.DepositItemModel
import kh.com.acleda.deposits.modules.home.presentation.HomeScreen
import kh.com.acleda.deposits.modules.openNewTerm.domain.model.UnAthOpenTermModel
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermConfirmScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermScreen
import kh.com.acleda.deposits.modules.openNewTerm.presentation.OpenNewTermSuccessScreen
import kh.com.acleda.deposits.modules.renewal.domain.model.UnAuthRenewalModel
import kh.com.acleda.deposits.modules.renewal.presentation.RenewalConfirmScreen
import kh.com.acleda.deposits.modules.renewal.presentation.RenewalScreen
import kh.com.acleda.deposits.modules.renewal.presentation.RenewalSuccessScreen
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
                            navController.navigateToRenewal(dataString)
                        }

                        DepositMenu.STOP_RENEWAL -> {
                            val calculator = StopRenewalCalculator()
                            val model = StopRenewalConfirmModel(
                                depositAmount = term.depositAmount.toString(),
                                ccy = term.currency,
                                mmNumber = term.mm,
                                depositTypeId = term.termTypeId,
                                depositTerm = term.depositTerm,
                                autoRenewal = term.autoRenewal,
                                rolloverTime = term.rolloverTime,
                                maturityDate = term.maturityDate,
                                newRolloverTime = "0",
                                newMaturityDate = calculator.calculateNewMaturityDate(originalDate = term.maturityDate, renewalCount = term.rolloverTime.toIntOrNull() ?: 0, termMonths = term.depositTerm.toLongOrNull() ?: 0),
                            )

                            val modelStr = gson.toJson(model)
                            navController.navigateToStopRenewalConfirm(modelStr)
                        }

                        DepositMenu.E_CERTIFICATE -> {
                            val model = ECertificateModel(
                                termTypeId = term.termTypeId,
                                mmNumber = term.mm,
                                depositAmount = term.depositAmount.toString(),
                                ccy = term.currency
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
            val termObjectString: String = backStackEntry.arguments?.getString(DepositDetail.depositDetailWithTermArg) ?: ""
            val term: DepositItemModel = gson.fromJson(termObjectString)

            val isFromCloseRequest: Boolean = backStackEntry.arguments?.getBoolean(DepositDetail.isFromCloseRequestArg) ?: false

            DepositDetailScreen(
                term = term,
                isFromCloseRequest = isFromCloseRequest,
                onBackPress = { navController.popBackStack() },
                onCloseTermDialogConfirm = { closeTerm ->
                    val ccy = getCcyEnum(closeTerm.currency)
                    val calculator = CloseTermCalculator()
                    val depositDays = calculator.depositDays(effectiveDateStr = closeTerm.effectiveDate)
                    val interest = calculator.interest(principalAmount = closeTerm.depositAmount.toDouble(), annualRate = closeTerm.interestRate.toDoubleOrNull() ?: 0.0, numberOfDays = depositDays)
                    val tax = calculator.tax(interestAmount = interest, taxRate = 6.0) // for now just fixed number
                    val netInterest = calculator.netInterest(interestAmount = interest, taxAmount = tax)

                    val model = AuthCloseTermModel(
                        ccy = ccy,
                        depositDays = depositDays,
                        receivedInterest = roundDoubleAmount(netInterest, ccy)
                    )

                    val modelStr: String = gson.toJson(model)

                    navController.navigateToCloseTermSuccess(modelStr)
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
                    val modelStr = gson.toJson(it)
                    navController.navigateToOpenNewTermConfirm(model = modelStr)
                }
            )
        }

        composable(
            route = OpenNewTermConfirm.routWithArg,
            arguments = OpenNewTermConfirm.argument
        ) { backStackEntry ->
            val modelStr: String = backStackEntry.arguments?.getString(OpenNewTermConfirm.modelArg) ?: ""
            val model: UnAthOpenTermModel = gson.fromJson(modelStr)

            OpenNewTermConfirmScreen(
                model = model,
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
            val modelStr = backStackEntry.arguments?.getString(CloseTermSuccess.modelArg) ?: ""
            val model: AuthCloseTermModel = gson.fromJson(modelStr)

            CloseTermSuccessScreen(
                model = model,
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
            val model: DepositItemModel = gson.fromJson(modelObjectString)

            RenewalScreen(
                model = model,
                onBackPress = { navController.popBackStack() },
                onRenewalClick = { unAuthModel ->
                    val modelString = gson.toJson(unAuthModel)

                    navController.navigateToRenewalConfirm(modelString)
                }
            )
        }

        composable(
            route = RenewalConfirm.routWithArg,
            arguments = RenewalConfirm.argument
        ) { backStackEntry ->
            val modelObjectString: String = backStackEntry.arguments?.getString(RenewalConfirm.modelArg) ?: ""
            val model: UnAuthRenewalModel = gson.fromJson(modelObjectString)

            RenewalConfirmScreen(
                model = model,
                onBackPress = { navController.popBackStack() },
                onConfirmClick = {
                    navController.navigateSingleTopTo(RenewalSuccess.route)
                }
            )
        }

        composable(route = RenewalSuccess.route) {
            RenewalSuccessScreen(
                onClick = { navController.navigateClearTop(DepositList.route, navController) }
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

private fun NavHostController.navigateToOpenNewTermConfirm(model: String) {
    this.navigate("${OpenNewTermConfirm.route}?${OpenNewTermConfirm.modelArg}=$model")
}

private fun NavHostController.navigateToDepositDetail(term: String, isFromCloseRequest: Boolean) {
    this.navigate("${DepositDetail.route}/$term?${DepositDetail.isFromCloseRequestArg}=$isFromCloseRequest")
}

private fun NavHostController.navigateToCloseTermSuccess(model: String) {
    this.navigate("${CloseTermSuccess.route}?${CloseTermSuccess.modelArg}=$model")
}

private fun NavHostController.navigateToECertificate(model: String) {
    this.navigate("${ECertificate.route}?${ECertificate.modelArg}=$model")
}

private fun NavHostController.navigateToRenewal(model: String) {
    this.navigate("${Renewal.route}?${Renewal.modelArg}=$model")
}

private fun NavHostController.navigateToRenewalConfirm(model: String) {
    this.navigate("${RenewalConfirm.route}?${RenewalConfirm.modelArg}=$model")
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
