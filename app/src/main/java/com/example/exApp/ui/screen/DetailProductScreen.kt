package com.example.exApp.ui.screen

import android.widget.Button
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.*
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.exApp.R
import com.pemmob.azizamirul.data.model.*
import com.pemmob.azizamirul.data.dummy.DummyData
import com.pemmob.azizamirul.data.model.Category
import kotlinx.coroutines.launch

// Daftar Produk ada di sini
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DetailProductScreen(productId: Int, navController: NavController?) {
    val context = LocalContext.current
    var product by remember{ mutableStateOf<Product?>(null) }
    var isLoading by remember{ mutableStateOf(true) }
    var quantity by remember{ mutableStateOf(1) }

    LaunchedEffect(productId){
        isLoading = true
        delay(1000)
        product = DummyData.products.find { it.id==productId}
        isLoading = false
    }
    StatelessDetailProduct(
        product = product,
        isLoading=isLoading,
        quantity=quantity,
        onQuantityChange={quantity=it},
        onBackClick={navController?.popBackStack()},
        onAddToCartClick = { Toast.makeText(context, "Dimasukkan: $quantity", Toast.LENGTH_SHORT).show() }
    )
    /* Scaffold (
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

        Column() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    text = "Kategori Produk",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(DummyData.categories) { category ->
                        CategoryItem(
                            category = category,
                            isSelected = category.id == selectedCategoryId,
                            onClick = { selectedCategoryId = category.id }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Daftar Produk",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ){
                    items(filteredProducts) { product ->
                        ProductItemCard(product = product){
                            Toast.makeText(context, "Clicked ${product.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0XFFE0E0E0))
                        .padding(16.dp)
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
                ) {
                    Text(text = "Hubungi Kami", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(modifier = Modifier.width(16.dp))

            }
        }
     }*/


}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun StatelessDetailProduct(
    product: Product?, isLoading: Boolean, quantity:Int,
    onQuantityChange: (Int)->Unit, onBackClick: () -> Unit, onAddToCartClick: ()->Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Produk") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(id = R.drawable.ic_launcher), "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if(isLoading){
                Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
            } else if (product != null){
                Column(modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState())) {
                    val imageRes = if (product.img == "dummy_product") R.drawable.ic_launcher else R.drawable.ic_launcher
                    Image(painterResource(id= imageRes), contentDescription = null, Modifier.fillMaxWidth().height(280.dp))
                    Column(modifier = Modifier.padding(16.dp)){
                        Text(product.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("Rp ${product.price}", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Deskripsi", fontWeight = FontWeight.Bold)
                        Text(product.description ?: "")
                        Text("Stok: ${product.stock}")
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Jumlah Beli")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FilledTonalIconButton(
                                    onClick = { if (quantity > 1) onQuantityChange(quantity + 1) },
                                    enabled = quantity > 1
                                ) {Text("")}

                                Text(quantity.toString(), modifier = Modifier.padding(horizontal = 16.dp))

                                FilledTonalIconButton(
                                    onClick = { if (quantity < product.stock) onQuantityChange(quantity + 1) },
                                    enabled = quantity < product.stock
                                ) {Text("+")}
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onAddToCartClick,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            enabled = product.stock > 0 && quantity > 0
                        ){
                            Text(text = "Tambah ke Keranjang")
                        }
                    }
                }
            }
    }
}