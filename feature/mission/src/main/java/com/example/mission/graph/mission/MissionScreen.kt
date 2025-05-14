package com.example.mission.graph.mission

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.MissionBackground
import com.example.designsystem.theme.MissionHeader
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.VerificationButton
import com.example.designsystem.theme.White
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.MAX_MISSION_CHANGE_COUNT
import com.example.domain.model.mission.Mission

@Composable
internal fun MissionRoute(
    viewModel: MissionViewModel = hiltViewModel()
) {
    val dailyMission by viewModel.dailyMission.collectAsStateWithLifecycle()

    MissionScreen(
        dailyMission = dailyMission,
        changeMission = viewModel::changeMission
    )
}

@Composable
private fun MissionScreen(
    dailyMission: DailyMission,
    changeMission: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(MissionBackground)
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Canvas(modifier = Modifier.size(6.dp)) {
                        drawCircle(
                            color = MissionHeader,
                            radius = size.minDimension / 2f
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    Text(
                        "오늘의 선행 미션",
                        style = TraceTheme.typography.missionHeader,
                        color = MissionHeader
                    )

                    Spacer(Modifier.width(4.dp))

                    Canvas(modifier = Modifier.size(6.dp)) {
                        drawCircle(
                            color = MissionHeader,
                            radius = size.minDimension / 2f
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    Box() {
                        IconButton(
                            onClick = { changeMission() },
                            content = {
                                Image(
                                    painter = painterResource(R.drawable.refresh_mission_ic),
                                    contentDescription = "미션 변경",
                                )
                            },
                            modifier = Modifier.align(Alignment.Center)
                        )

                        Text(
                            "${dailyMission.changeCount}/${MAX_MISSION_CHANGE_COUNT}",
                            style = TraceTheme.typography.bodySR.copy(
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            ),
                            color = MissionHeader,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(top = 1.dp)
                        )
                    }
                }

                Spacer(Modifier.height(38.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        dailyMission.mission.description,
                        style = TraceTheme.typography.missionTitle,
                        color = Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(Modifier.height(35.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(VerificationButton)
                            .width(61.dp)
                            .height(32.dp)
                            .clickable { }
                    ) {
                        Text(
                            "인증하기",
                            style = TraceTheme.typography.missionVerification,
                            color = White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }


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
    )
}