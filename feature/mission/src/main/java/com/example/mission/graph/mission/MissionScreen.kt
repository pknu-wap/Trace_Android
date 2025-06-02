package com.example.mission.graph.mission

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.TraceTheme
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import com.example.domain.model.mission.MissionFeed
import com.example.mission.graph.mission.component.MissionCompletedHeaderView
import com.example.mission.graph.mission.component.MissionHeaderView
import com.example.mission.graph.mission.component.VerifiedMissionBox

@Composable
internal fun MissionRoute(
    viewModel: MissionViewModel = hiltViewModel(),
    navigateToPost: (Int) -> Unit,
    navigateToVerifyMission: (String) -> Unit,
) {
    val dailyMission by viewModel.dailyMission.collectAsStateWithLifecycle()
    val verifiedMissions by viewModel.verifiedMissions.collectAsStateWithLifecycle()

    if (!dailyMission.mission.isVerified) {
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.getDailyMission()
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
  
    MissionScreen(
        dailyMission = dailyMission,
        verifiedMissions = verifiedMissions,
        changeMission = viewModel::changeDailyMission,
        navigateToPost = navigateToPost,
        onVerifyMission = navigateToVerifyMission
    )
}

@Composable
private fun MissionScreen(
    dailyMission: DailyMission,
    verifiedMissions: List<MissionFeed>,
    changeMission: () -> Unit,
    navigateToPost: (Int) -> Unit,
    onVerifyMission: (String) -> Unit,
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
                    verifyMission = onVerifyMission
                )
            }

            if (dailyMission.mission.isVerified) {
                MissionCompletedHeaderView(
                    dailyMission = dailyMission
                )
            }

            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                "미션 기록",
                style = TraceTheme.typography.bodySSB.copy(fontSize = 16.sp, lineHeight = 20.sp)
            )

            Spacer(Modifier.height(16.dp))
        }

        items(count = verifiedMissions.size, key = { index ->
            verifiedMissions[index].missionId
        }) { index ->
            VerifiedMissionBox(verifiedMissions[index], navigateToPost = navigateToPost)

            Spacer(Modifier.height(10.dp))
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
        verifiedMissions = fakeMissionFeeds1,
        changeMission = {},
        navigateToPost = {},
        onVerifyMission = {}
    )
}