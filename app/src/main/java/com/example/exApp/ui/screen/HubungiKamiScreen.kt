package com.example.exApp.ui.screen

import android.R.id.message
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import com.example.exApp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HubungiKamiScreen(navController: NavController) {
    var emailText by remember {mutableStateOf("")}
    var messageText by remember {mutableStateOf("")}
    var problemType by rememberSaveable{ mutableStateOf("Pilih Tipe Pesan")}
    var isAgreed by rememberSaveable{ mutableStateOf(false)}
    var imageUri by rememberSaveable{ mutableStateOf(null)}

    val isEmailValid = emailText.contains("@")
    val isMessageValid = messageText.length >= 10
    val isFormValid = isEmailValid && isMessageValid && isAgreed && (problemType != "Pilih Tipe Pesan")

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hubungi Kami") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack()}) {
                        Icon(
                            painter = painterResource(id = com.example.exApp.R.drawable.ic_launcher),
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        }
    ){
        paddingValues ->

        StatelessFormHubungiKami(
            modifier = Modifier.padding(paddingValues),
            email = emailText,
            onEmailChange = { emailText = it },
            isEmailValid = isEmailValid,
            message = messageText,
            onMessageChange = { messageText = it },
            problemType = problemType,
            onProblemTypeChange = { problemType = it},
            isAgreed = isAgreed,
            onAgreedChange = { isAgreed = it},
            imageUri = imageUri,
            onImagePicked = { },
            isFormValid = isFormValid,
            onSubmit = {
                scope.launch {
                    snackbarHostState.showSnackbar("Pesanan Terkirim!")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =  "Hubungi Kami",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                label = { Text("Email Anda") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                label = { Text("Pesan") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    scope.launch {
                        snackbarHostState.showSnackbar("Pesan Terkirim")
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Kirim Pesan", style = MaterialTheme.typography.labelLarge)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                   Row(
                       modifier = Modifier.padding(16.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ){
                       Text(
                           text = "Misi Kami",
                           style = MaterialTheme.typography.titleLarge,
                           color = Color.White,
                           modifier = Modifier.weight(1f)
                       )
                       Text(
                           text = "Memajukan UMKM Lokal",
                           modifier = Modifier.weight(2f)
                       )
                       Spacer(modifier = Modifier.weight(1f))
                   }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessFormHubungiKami(
    modifier: Modifier = Modifier,
    email: String, onEmailChange: (String) -> Unit, isEmailValid: Boolean,
    message: String, onMessageChange: (String) -> Unit,
    problemType: String, onProblemTypeChange: (String) -> Unit,
    isAgreed: Boolean, onAgreedChange: (Boolean) -> Unit,
    imageUri:Uri?, onImagePicked:(Uri?) -> Unit,
    isFormValid: Boolean, onSubmit: () -> Unit
){
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri-> onImagePicked(uri) }
    )
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Pertanyaan", "Keluhan", "Saran")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Hubungi Kami",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email Anda") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher),
                    contentDescription = "Email"
                )
            },
            isError = email.isNotEmpty()&&!isEmailValid,
            supportingText = {if(email.isNotEmpty()&&!isEmailValid) Text("Format Email Salah")},
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded}
        ) {
            OutlinedTextField(
                readOnly = true,
                value = problemType,
                onValueChange = {},
                label = { Text("Tipe Pesan") },
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach {selectionOption->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onProblemTypeChange(selectionOption)
                            expanded = false
                        }
                    )
                }
            }

            if(imageUri != null){
                Spacer(modifier = Modifier.height(8.dp))
                Card (colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)){
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically){
                        Icon(painterResource(id = R.drawable.ic_launcher), contentDescription = "File")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("File terpilih: ${imageUri.lastPathSegment}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = isAgreed, onCheckedChange = onAgreedChange)
                Text("Saya menyetujui syarat dan ketentuan")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSubmit,
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Kirim Pesan", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}