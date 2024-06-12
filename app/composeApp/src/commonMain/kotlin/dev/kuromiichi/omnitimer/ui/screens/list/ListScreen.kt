package dev.kuromiichi.omnitimer.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.kuromiichi.omnitimer.ui.composables.common.CategoryDialog
import dev.kuromiichi.omnitimer.ui.composables.common.CategoryDisplay
import dev.kuromiichi.omnitimer.ui.composables.common.EditSubcategoryDialog
import dev.kuromiichi.omnitimer.ui.composables.list.ListContent
import dev.kuromiichi.omnitimer.ui.composables.list.ListItem
import dev.kuromiichi.omnitimer.utils.getTimeStringFromMillis

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ListScreen() {
    val viewModel = getViewModel(Unit, viewModelFactory { ListViewModel() })
    val state: ListUiState by viewModel.uiState.collectAsState()

    val windowSizeClass = calculateWindowSizeClass()
    val isPortrait = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryDisplay(
                modifier = Modifier.height(IntrinsicSize.Max).widthIn(max = 960.dp),
                categoryName = state.subcategory.category.displayName,
                subcategoryName = state.subcategory.name,
                onCategoryClick = { viewModel.onCategorySelectorClick() },
                onSubcategoryClick = { viewModel.onSubcategorySelectorClick() }
            )

            if (state.isCategoryDialogShowing) {
                CategoryDialog(
                    elements = viewModel.categories,
                    title = "Select category",
                    onClick = { viewModel.onCategorySelected(it) },
                    onDismiss = {
                        viewModel.onCategoryDialogDismiss()
                    },
                )
            }

            if (state.isSubcategoryDialogShowing) {
                CategoryDialog(
                    elements = viewModel.getSubcategories(),
                    title = "Select subcategory",
                    isEditable = true,
                    onClick = { viewModel.onSubcategorySelected(it) },
                    onEditClick = { viewModel.onEditSubcategoryClick(it) },
                    onDismiss = {
                        viewModel.onSubcategoryDialogDismiss()
                    },
                    confirmButton = {
                        Button(onClick = { viewModel.onCreateSubcategoryClick() }) {
                            Text(text = "New subcategory")
                        }
                    }
                )
            }

            if (state.isCreateSubcategoryDialogShowing) {
                EditSubcategoryDialog(
                    isOpen = state.isCreateSubcategoryDialogShowing,
                    title = "New subcategory",
                    subcategory = state.subcategoryName,
                    onSubcategoryChange = { viewModel.onSubcategoryNameChange(it) },
                    onDismiss = { viewModel.onCreateSubcategoryDialogDismiss() },
                    onConfirm = { viewModel.onCreateSubcategoryConfirmClick() }
                )
            }

            if (state.isEditSubcategoryDialogShowing) {
                EditSubcategoryDialog(
                    isOpen = state.isEditSubcategoryDialogShowing,
                    isEdit = true,
                    title = "Edit subcategory",
                    subcategory = state.subcategoryName,
                    onSubcategoryChange = { viewModel.onSubcategoryNameChange(it) },
                    onDelete = { viewModel.onDeleteSubcategoryClick() },
                    onDismiss = { viewModel.onEditSubcategoryDialogDismiss() },
                    onConfirm = { viewModel.onEditSubcategoryConfirmClick(state.originalSubcategoryName) }
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.solves.count()) { solve ->
                    var isExpanded by rememberSaveable { mutableStateOf(false) }

                    ListItem(
                        time = getTimeStringFromMillis(viewModel.solves[solve].time),
                        date = "${viewModel.solves[solve].date.dayOfMonth}-" +
                                "${viewModel.solves[solve].date.monthNumber}-" +
                                "${viewModel.solves[solve].date.year}",
                        isExpanded = isExpanded,
                        onExpandedClick = { isExpanded = !isExpanded },
                    ) {
                        ListContent(
                            modifier = Modifier.padding(8.dp),
                            scramble = viewModel.solves[solve].scramble.value,
                            image = viewModel.solves[solve].scramble.image,
                            selectedPenalty = viewModel.solves[solve].status,
                            isPortrait = isPortrait,
                            onPenaltySelected = {
                                viewModel.changePenalty(solve, it)
                                isExpanded = !isExpanded
                                isExpanded = !isExpanded
                            },
                            onDeleteClick = {},
                            onArchiveClick = {}
                        )
                    }
                }
            }
        }
    }
}


