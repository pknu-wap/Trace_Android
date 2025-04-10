package com.example.main.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.example.common.ui.NoRippleInteractionSource
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.White
import com.example.navigation.HomeRoute
import com.example.navigation.Route
import com.example.navigation.eqaulsRoute

@Composable
internal fun AppBottomBar(
    currentDestination: NavDestination?,
    navigateToBottomNaviDestination: (Route) -> Unit,
    modifier: Modifier = Modifier
) {


    NavigationBar(
        containerColor = White,
    ) {
        TopLevelDestination.entries.forEach { topLevelRoute ->
            val icon = if (currentDestination.eqaulsRoute(topLevelRoute.route)) topLevelRoute.selectedIcon else topLevelRoute.unSelectedIcon

            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.padding(top = 2.dp),
                    ) {

                        Icon(
                            painter = painterResource(icon),
                            contentDescription = topLevelRoute.contentDescription,
                            modifier = Modifier.size(32.dp),
                        )

                        Spacer(Modifier.height(2.dp))

                        Text(
                            text = topLevelRoute.title,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                            )
                        )
                    }
                },
                onClick = {
                    when (topLevelRoute) {
                        TopLevelDestination.HOME -> navigateToBottomNaviDestination(HomeRoute)
                        TopLevelDestination.Mission -> navigateToBottomNaviDestination(HomeRoute)
                        TopLevelDestination.MyPage -> navigateToBottomNaviDestination(HomeRoute)
                    }
                },
                selected = currentDestination.eqaulsRoute(topLevelRoute.route),
                interactionSource = remember { NoRippleInteractionSource() },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryDefault,
                    selectedTextColor = PrimaryDefault,
                    unselectedTextColor = Black,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }

}