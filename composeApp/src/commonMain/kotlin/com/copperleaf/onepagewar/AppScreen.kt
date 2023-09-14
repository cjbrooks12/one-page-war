package com.copperleaf.onepagewar

import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouteAnnotation
import com.copperleaf.ballast.navigation.routing.RouteMatcher

internal enum class AppScreen(
    routeFormat: String,
    override val annotations: Set<RouteAnnotation> = emptySet(),
) : Route {
    LogIn("/login"),
    PlayGame("/app/game/{themeId}?initialGameId={?}"),
    ;

    override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)
}
