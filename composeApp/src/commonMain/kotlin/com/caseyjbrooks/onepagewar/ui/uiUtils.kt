package com.caseyjbrooks.onepagewar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.di.AppInjector

internal val LocalInjector: ProvidableCompositionLocal<AppInjector> = staticCompositionLocalOf {
    error("LocalInjector not provided")
}

@Composable
internal fun <T> FixedGrid(
    items: List<T>,
    columnCount: Int,
    modifier: Modifier = Modifier,
    contentSpacing: Dp = 16.dp,
    contentPadding: PaddingValues = PaddingValues(all = contentSpacing),
    itemContent: @Composable (T) -> Unit,
) {
    BoxWithConstraints(modifier) {
        val rowCount = items.size / columnCount + if (items.size % columnCount > 0) 1 else 0
        val gridHeight = maxHeight -
                contentPadding.calculateTopPadding() -
                contentPadding.calculateBottomPadding()
        val itemHeight = (gridHeight - contentSpacing * (rowCount - 1)) / rowCount
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            columns = GridCells.Fixed(columnCount),
            verticalArrangement = Arrangement.spacedBy(contentSpacing),
            horizontalArrangement = Arrangement.spacedBy(contentSpacing)
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(itemHeight),
                ) {
                    itemContent(item)
                }
            }
        }
    }
}


@Composable
public fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .fillMaxHeight()
            .width(targetThickness)
            .background(color = color)
    )
}

internal val Color.calculatedContentColor: Color
    get() {
        return if (luminance() > 0.5) Color.Black else Color.White
    }
