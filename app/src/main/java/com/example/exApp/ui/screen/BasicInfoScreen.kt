package com.example.exApp.ui.screen

import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.exApp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun BasicInfoScreen(onNavigateToContact: () -> Unit) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Tentang Jualan") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher),
                contentDescription = "Logo Aplikasi",
                modifier = Modifier.size(120.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tentang Jualan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Aplikasi Jualan adalah platform yang mewadahi produk lokal UMKM di wilayah Kabupaten Purbalingga, Jawa Tengah",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFFE0E0E0))
                    .padding( 16.dp)
            ) {

                Text(
                    text = "Misi Kami:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Memajukan UMKM Lokal",
                    modifier = Modifier.weight(2f)
                )
            }

            Spacer(modifier = Modifier.width(120.dp))

            Button(
                onClick = onNavigateToContact,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ){
                Text(text = "Hubungi Kami", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.width(16.dp))

            }
     }
}