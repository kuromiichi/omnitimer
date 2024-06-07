package ui.screens.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.composables.timer.CategoryDisplay
import ui.composables.timer.PenaltySelector
import ui.composables.timer.ScrambleDisplay
import ui.composables.timer.ScrambleImage
import ui.composables.timer.StatsDisplay
import ui.composables.timer.TimerArea

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TimerScreen() {
    val viewModel = getViewModel(Unit, viewModelFactory { TimerViewModel() })
    val state: TimerUiState by viewModel.uiState.collectAsState()

    val windowSizeClass = calculateWindowSizeClass()

    val timerFontSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 144.sp
        else -> 96.sp
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Expanded -> TimerScreenLandscape(viewModel, state, timerFontSize)
            else -> TimerScreenPortrait(viewModel, state, timerFontSize)
        }
    }
}

@Composable
fun TimerScreenPortrait(
    viewModel: TimerViewModel,
    state: TimerUiState,
    timerFontSize: TextUnit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        CategoryDisplay(
            categoryName = state.subcategory.category.displayName,
            subcategoryName = state.subcategory.name,
            modifier = Modifier.height(IntrinsicSize.Max)
        )
        ScrambleDisplay(
            scramble = state.scramble.value,
            onRefreshClick = { viewModel.refreshScramble() }
        )
        TimerArea(
            time = viewModel.getTime(),
            fontSize = timerFontSize,
            onSurfaceClick = { viewModel.toggleTimerState() },
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
        PenaltySelector(
            selectedPenalty = state.penalty,
            onPenaltySelected = { viewModel.changePenalty(it) },
            onDeleteClick = { viewModel.deleteLastSolve() },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(IntrinsicSize.Max).fillMaxWidth()
        ) {
            StatsDisplay(
                stats = mapOf(),
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            ScrambleImage(
                image = viewModel.getImage(state.scramble.image),
                modifier = Modifier.weight(1f).heightIn(min = 96.dp).fillMaxHeight()
            )
        }
    }
}

@Composable
fun TimerScreenLandscape(
    viewModel: TimerViewModel,
    state: TimerUiState,
    timerFontSize: TextUnit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(IntrinsicSize.Max).fillMaxWidth()
        ) {
            CategoryDisplay(
                categoryName = state.subcategory.category.displayName,
                subcategoryName = state.subcategory.name,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            ScrambleDisplay(
                scramble = state.scramble.value,
                onRefreshClick = { viewModel.refreshScramble() },
                modifier = Modifier.weight(2f)
            )

        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                StatsDisplay(
                    stats = mapOf(),
                    modifier = Modifier.weight(2f).fillMaxWidth()
                )
                ScrambleImage(
                    image = viewModel.getImage(state.scramble.image),
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(3f)
            ) {
                TimerArea(
                    time = viewModel.getTime(),
                    fontSize = timerFontSize,
                    onSurfaceClick = { viewModel.toggleTimerState() },
                    modifier = Modifier.fillMaxSize()
                )
                PenaltySelector(
                    selectedPenalty = state.penalty,
                    onPenaltySelected = { viewModel.changePenalty(it) },
                    onDeleteClick = { viewModel.deleteLastSolve() },
                    modifier = Modifier.widthIn(max = 480.dp).padding(top = 288.dp)
                )
            }
        }
    }
}
