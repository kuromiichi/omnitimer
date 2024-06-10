package dev.kuromiichi.omnitimer.ui.screens.timer

import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.kuromiichi.omnitimer.ui.composables.timer.CategoryDisplay
import dev.kuromiichi.omnitimer.ui.composables.timer.PenaltySelector
import dev.kuromiichi.omnitimer.ui.composables.timer.ScrambleDisplay
import dev.kuromiichi.omnitimer.ui.composables.timer.ScrambleImage
import dev.kuromiichi.omnitimer.ui.composables.timer.StatsDisplay
import dev.kuromiichi.omnitimer.ui.composables.timer.TimerArea

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
    val focusRequester = remember { FocusRequester() }

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
            onRefreshClick = {
                viewModel.refreshScramble()
                focusRequester.requestFocus()
            }
        )

        var ignoreKeyUp by remember { mutableStateOf(false) }
        var ignoreKeyDown by remember { mutableStateOf(false) }
        TimerArea(
            time = viewModel.getTime(),
            fontSize = timerFontSize,
            onSurfaceClick = { viewModel.toggleTimerState() },
            modifier = Modifier.weight(1f).fillMaxWidth()
                .focusRequester(focusRequester)
                .focusable()
                .onPreviewKeyEvent {
                    if (it.key == Key.Spacebar) {
                        when (viewModel.uiState.value.timerState) {
                            TimerViewModel.TimerState.Stopped -> {
                                if (ignoreKeyDown) {
                                    if (it.type == KeyEventType.KeyUp) {
                                        ignoreKeyDown = false
                                        true
                                    } else false
                                } else {
                                    if (viewModel.inspectionEnabled && it.type == KeyEventType.KeyDown) {
                                        ignoreKeyUp = true
                                        viewModel.toggleTimerState()
                                        true
                                    } else if (!viewModel.inspectionEnabled && it.type == KeyEventType.KeyUp) {
                                        viewModel.toggleTimerState()
                                        true
                                    } else false
                                }
                            }

                            TimerViewModel.TimerState.Inspection -> {
                                if (ignoreKeyUp && it.type == KeyEventType.KeyUp) {
                                    ignoreKeyUp = false
                                    true
                                } else {
                                    if (it.type == KeyEventType.KeyUp) {
                                        viewModel.toggleTimerState()
                                        true
                                    } else false
                                }
                            }

                            TimerViewModel.TimerState.Running -> {
                                if (it.type == KeyEventType.KeyDown) {
                                    ignoreKeyDown = true
                                    viewModel.toggleTimerState()
                                    true
                                } else false
                            }
                        }
                    } else false
                }
        )
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
        PenaltySelector(
            selectedPenalty = state.penalty,
            onPenaltySelected = {
                viewModel.changePenalty(it)
                focusRequester.requestFocus()
            },
            onDeleteClick = {
                viewModel.deleteLastSolve()
                focusRequester.requestFocus()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(IntrinsicSize.Max).fillMaxWidth()
        ) {
            StatsDisplay(
                stats = state.stats,
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
    val focusRequester = remember { FocusRequester() }

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
                onRefreshClick = {
                    viewModel.refreshScramble()
                    focusRequester.requestFocus()
                },
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
                    stats = state.stats,
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
                var ignoreKeyUp by remember { mutableStateOf(false) }
                var ignoreKeyDown by remember { mutableStateOf(false) }
                TimerArea(
                    time = viewModel.getTime(),
                    fontSize = timerFontSize,
                    onSurfaceClick = { viewModel.toggleTimerState() },
                    modifier = Modifier.fillMaxSize()
                        .focusRequester(focusRequester)
                        .focusable()
                        .onPreviewKeyEvent {
                            if (it.key == Key.Spacebar) {
                                when (viewModel.uiState.value.timerState) {
                                    TimerViewModel.TimerState.Stopped -> {
                                        if (ignoreKeyDown) {
                                            if (it.type == KeyEventType.KeyUp) {
                                                ignoreKeyDown = false
                                                true
                                            } else false
                                        } else {
                                            if (viewModel.inspectionEnabled && it.type == KeyEventType.KeyDown) {
                                                ignoreKeyUp = true
                                                viewModel.toggleTimerState()
                                                true
                                            } else if (!viewModel.inspectionEnabled && it.type == KeyEventType.KeyUp) {
                                                viewModel.toggleTimerState()
                                                true
                                            } else false
                                        }
                                    }

                                    TimerViewModel.TimerState.Inspection -> {
                                        if (ignoreKeyUp && it.type == KeyEventType.KeyUp) {
                                            ignoreKeyUp = false
                                            true
                                        } else {
                                            if (it.type == KeyEventType.KeyUp) {
                                                viewModel.toggleTimerState()
                                                true
                                            } else false
                                        }
                                    }

                                    TimerViewModel.TimerState.Running -> {
                                        if (it.type == KeyEventType.KeyDown) {
                                            ignoreKeyDown = true
                                            viewModel.toggleTimerState()
                                            true
                                        } else false
                                    }
                                }
                            } else false
                        }
                )
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
                PenaltySelector(
                    selectedPenalty = state.penalty,
                    onPenaltySelected = {
                        viewModel.changePenalty(it)
                        focusRequester.requestFocus()
                    },
                    onDeleteClick = {
                        viewModel.deleteLastSolve()
                        focusRequester.requestFocus()
                    },
                    modifier = Modifier.widthIn(max = 480.dp).padding(top = 288.dp)
                )
            }
        }
    }
}
