package com.example.lab6msec.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lab6msec.data.HRep
import com.example.lab6msec.screens.AddDestination
import com.example.lab6msec.screens.AddScreen
import com.example.lab6msec.screens.GivePerms
import com.example.lab6msec.screens.HomeDestination
import com.example.lab6msec.screens.HomeScreen
import com.example.lab6msec.screens.PermDestination

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    repka:HRep
) {

    NavHost(
        navController = navController,
        startDestination = PermDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = modifier,
                repo = repka,
                navigateToAdd = {navController.navigate(AddDestination.route)}
            )
        }

        composable(route = AddDestination.route)
        {
            AddScreen(
                modifier,
                repka,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = PermDestination.route) {
            GivePerms(
                modifier = modifier,
                navigateHome = {navController.navigate(HomeDestination.route)},
                appCont = LocalContext.current
            )
        }

    }
}