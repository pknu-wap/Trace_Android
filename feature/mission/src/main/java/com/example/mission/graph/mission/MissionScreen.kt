package com.example.mission.graph.mission

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import com.example.mission.graph.mission.component.MissionCompletedHeaderView
import com.example.mission.graph.mission.component.MissionHeaderView

@Composable
internal fun MissionRoute(
    viewModel: MissionViewModel = hiltViewModel(),
) {
    val dailyMission by viewModel.dailyMission.collectAsStateWithLifecycle()

    MissionScreen(
        dailyMission = dailyMission,
        changeMission = viewModel::changeMission,
        verifyMission = {},
    )
}

@Composable
private fun MissionScreen(
    dailyMission: DailyMission,
    changeMission: () -> Unit,
    verifyMission: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        item {
            if (!dailyMission.mission.isVerified) {
                MissionHeaderView(
                    dailyMission = dailyMission,
                    changeMission = changeMission,
                    verifyMission = verifyMission
                )
            }

            if (dailyMission.mission.isVerified) {
                MissionCompletedHeaderView(
                    dailyMission = dailyMission
                )
            }
        }
    }
}


@Preview
@Composable
fun MissionScreenPreview() {
    MissionScreen(
        dailyMission = DailyMission(
            mission = Mission(
                description = "길거리에서 쓰레기 줍기",
                isVerified = false,
            ),

            changeCount = 0
        ),
        changeMission = {},
        verifyMission = {}
    )
}