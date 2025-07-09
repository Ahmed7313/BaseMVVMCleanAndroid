package com.example.basemvvmcleanandroid.screens.Settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aramex.mypos.Presentation.Components.MainEditText
import com.daman.edman.screens.components.AppSpacerHeight
import com.example.basemvvmcleanandroid.R
import com.example.basemvvmcleanandroid.data.remote.DTO.EventDTO.Result
import com.example.basemvvmcleanandroid.screens.NavGrapgh.HomeScreen
import com.example.basemvvmcleanandroid.screens.NavGrapgh.SettingsScreen
import com.example.basemvvmcleanandroid.ui.theme.GreenColor
import com.trend.thecontent.screens.components.LoadingView
import com.trend.thecontent.screens.components.MainButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val events by remember { derivedStateOf { viewModel.events } }
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Gate state
    val selectedGate by viewModel.selectedGate
    val availableGates by viewModel.availableGates.collectAsState()
    val isGateDropdownExpanded by viewModel.isGateDropdownExpanded
    val isLoadingGates by viewModel.isLoadingGates

    // Check-in lists state using Resource pattern
    val checkInListState by viewModel.checkInListState
    val isLoadingProgressBar by viewModel.isLoadingProgressBar.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Result?>(null) }

    // Handle navigation after successful API call
    LaunchedEffect(key1 = viewModel.navigateToHome) {
        viewModel.navigateToHome.collect { shouldNavigate ->
            if (shouldNavigate) {
                navController.navigate(HomeScreen) {
                    popUpTo(SettingsScreen) { inclusive = true }
                }
            }
        }
    }

    // Show loading dialog
    LoadingView(isLoadingProgressBar)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.home_bg_sports),
            contentDescription = "Launcher Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f,
            alignment = Alignment.Center,
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppSpacerHeight()

            when {
                isLoading -> {
                    CircularProgressIndicator(color = GreenColor)
                }

                error != null -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red.copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            // Gate Selection Section
                            GateSelectionCard(
                                selectedGate = selectedGate,
                                availableGates = availableGates,
                                isDropdownExpanded = isGateDropdownExpanded,
                                isLoadingGates = isLoadingGates,
                                onToggleDropdown = { viewModel.toggleGateDropdown() },
                                onSelectGate = { gate -> viewModel.selectGate(gate) }
                            )
                        }

                        item {
                            // Event Selection Section
                            EventSelectionCard(
                                events = events,
                                selectedEvent = selectedEvent,
                                isDropdownExpanded = expanded,
                                onToggleDropdown = { expanded = !expanded },
                                onSelectEvent = { event -> selectedEvent = event }
                            )
                        }

                        item {
                            // Show check-in list error if any
                            if (checkInListState.isError.isNotBlank()) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Red.copy(alpha = 0.1f)
                                    )
                                ) {
                                    Text(
                                        text = "Error: ${checkInListState.isError}",
                                        color = Color.Red,
                                        modifier = Modifier.padding(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // Navigate to Home Button
                            MainButton(
                                text = "Continue to Home",
                                modifier = Modifier.fillMaxWidth(),
                                enabled = selectedEvent != null && !checkInListState.isLoading
                            ) {
                                selectedEvent?.let { event ->
                                    viewModel.proceedToHome(event)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GateSelectionCard(
    selectedGate: com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate?,
    availableGates: List<com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate>,
    isDropdownExpanded: Boolean,
    isLoadingGates: Boolean,
    onToggleDropdown: () -> Unit,
    onSelectGate: (com.example.basemvvmcleanandroid.data.remote.DTO.Login.Gate) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Select Gate",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Gate Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { if (availableGates.isNotEmpty()) onToggleDropdown() }
                ) {
                    MainEditText(
                        text = if (isLoadingGates) "Loading gates..."
                        else selectedGate?.name ?: "Select a gate",
                        onTextChange = { },
                        isError = false,
                        readOnly = true,
                        trailingIcon = {
                            if (availableGates.isNotEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                            }
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { onToggleDropdown() }
                    ) {
                        availableGates.forEach { gate ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = gate.name,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "ID: ${gate.id}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                },
                                onClick = {
                                    onSelectGate(gate)
                                }
                            )
                        }
                    }
                }
            }

            // Selected Gate Details
            selectedGate?.let { gate ->
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GreenColor)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Selected Gate Details",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Name: ${gate.name}",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "ID: ${gate.id}",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        }
                    }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventSelectionCard(
    events: List<Result>,
    selectedEvent: Result?,
    isDropdownExpanded: Boolean,
    onToggleDropdown: () -> Unit,
    onSelectEvent: (Result) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Select Event",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Event Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { if (events.isNotEmpty()) onToggleDropdown() }
                ) {
                    MainEditText(
                        text = selectedEvent?.name?.ar ?: "Select an event",
                        onTextChange = { },
                        isError = false,
                        readOnly = true,
                        trailingIcon = {
                            if (events.isNotEmpty()) {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                            }
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { onToggleDropdown() }
                    ) {
                        events.forEach { event ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = event.name?.ar ?: "Unknown Event",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "ID: ${event.slug}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                },
                                onClick = {
                                    onSelectEvent(event)
                                }
                            )
                        }
                    }
                }
            }

            // Selected Event Details
            selectedEvent?.let { event ->
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GreenColor)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Selected Event Details",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Name: ${event.name?.ar ?: "N/A"}",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "ID: ${event.isSport}",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        // Add slug if available
                        event.slug?.let { slug ->
                            Text(
                                text = "Slug: $slug",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        // Add date info if available
                        event.dateTo?.let { dateTo ->
                            Text(
                                text = "Date: $dateTo",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Show message if no events available
            if (events.isEmpty()) {
                Text(
                    text = "No events available",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}