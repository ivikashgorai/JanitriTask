package com.example.janitritask


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.janitritask.database.Vitals
import com.example.janitritask.viewmodels.VitalsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(viewModel: VitalsViewModel) {
    var vitalsList = viewModel.vitalsList.collectAsState().value
    var dialog by remember {
        mutableStateOf(false)
    }
    var sysBP by remember {
        mutableStateOf("")
    }
    var diaBP by remember {
        mutableStateOf("")
    }
    var weight by remember {
        mutableStateOf("")
    }
    var kicks by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFC8ADFC)), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                "Track My Pregnancy",
                modifier = Modifier
                    .padding(start = 15.dp),
                fontSize = 20.sp,
                color = Color(0xFF5F1C9C),
                fontFamily = FontFamily(Font(R.font.inter600))
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        VitalsList(vitalsList)


        if (dialog) {
            AddVitalsDialog(sysBP, onSysBPChange = {
                sysBP = it
            },
                diaBP, onDiaBPChange = {
                    diaBP = it
                },
                weight,
                onWeightChange = {
                    weight = it
                },
                kicks,
                onKicksChange = {
                    kicks = it
                },
                onDismiss = {
                    dialog = !dialog
                },
                onClick = {
                    val dateFormat =
                        SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.getDefault())
                    val currentDate = dateFormat.format(Date())
                    val addVital = Vitals(
                        bloodPressure = sysBP,
                        heartRate = diaBP,
                        weight = weight.toDouble(),
                        kicks = kicks.toInt(),
                        vitalSavedDate = currentDate
                    )
                    viewModel.insertVitals(addVital)
                    sysBP = ""
                    diaBP = ""
                    weight = ""
                    kicks = ""
                    dialog = !dialog
                }
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = {
                dialog = !dialog
            },
            containerColor = Color(0xFF9C4DB9),
            shape = CircleShape,
            modifier = Modifier.padding(end = 20.dp, bottom = 50.dp)
        ) {
            Image(painter = painterResource(R.drawable.add), contentDescription = "Add")
        }
    }
}

@Composable
fun VitalsList(vitalsList: List<Vitals>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(vitalsList) { vitals ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(125.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEBB9FE),
                ),
                shape = RoundedCornerShape(6.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.heart_rate),
                                contentDescription = "Heart Rate",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(25.dp)
                            )
                            Text(
                                vitals.heartRate + " bpm",
                                fontFamily = FontFamily(Font(R.font.inter600)),
                                color = Color(0xFF3F0A71)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.blood_pressure),
                                contentDescription = "Heart Rate",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(25.dp)
                            )
                            Text(
                                vitals.bloodPressure + " mmHg",
                                fontFamily = FontFamily(Font(R.font.inter600)),
                                color = Color(0xFF3F0A71)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.weight),
                                contentDescription = "Weight",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(22.dp)
                            )
                            Text(
                                vitals.weight.toString() + " kg",
                                fontFamily = FontFamily(Font(R.font.inter600)),
                                color = Color(0xFF3F0A71)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.kicks),
                                contentDescription = "Kicks Count",
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(25.dp)
                            )
                            Text(
                                vitals.kicks.toString() + " kicks",
                                fontFamily = FontFamily(Font(R.font.inter600)),
                                color = Color(0xFF3F0A71)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF9C4DB9))
                            .padding(end = 10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            vitals.vitalSavedDate,
                            color = Color.White,
                            fontFamily = FontFamily(
                                Font(R.font.inter300)
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AddVitalsDialog(
    sysBP: String,
    onSysBPChange: (String) -> Unit,
    diaBP: String,
    onDiaBPChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    kicks: String,
    onKicksChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Add Vitals",
                fontFamily = FontFamily(Font(R.font.inter600)),
                fontSize = 24.sp,
                color = Color(0xFF3F0A71),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = sysBP,
                    onValueChange = onSysBPChange,
                    label = { Text("Sys BP") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = diaBP,
                    onValueChange = onDiaBPChange,
                    label = { Text("Dia BP") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight ( in kg )") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = kicks,
                onValueChange = onKicksChange,
                label = { Text("Baby Kicks") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF9C4CC9)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Submit", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
