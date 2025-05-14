package com.example.mission.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mission.graph.mission.MissionRoute
import com.example.navigation.MissionBaseRoute
import com.example.navigation.MissionGraph

fun NavController.navigateToMission(navOptions: NavOptions?= null) {
    navigate(MissionGraph.MissionRoute)
}

fun NavController.navigateToWriteMission(navOptions: NavOptions?= null) {
    navigate(MissionGraph.SubmitMissionRoute)
}


fun NavGraphBuilder.missionNavGraph(

) {
    navigation<MissionBaseRoute>(startDestination = MissionGraph.MissionRoute) {
        composable<MissionGraph.MissionRoute> {
            MissionRoute()
        }


    }
}