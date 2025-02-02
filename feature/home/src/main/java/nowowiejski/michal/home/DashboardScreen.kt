import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen() {
    val primaryColor = Color(0xFF66517C) // Fioletowy
    val accentColor = Color(0xFFCBB52E) // Złoty

    val mockRecipes = listOf(
        "Chocolate Cake",
        "Pasta Primavera",
        "Vegan Burger",
        "Mango Smoothie",
        "Grilled Salmon"
    )

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    decorationBox = { innerTextField ->
                        if ("".isEmpty()) {
                            Text(text = "Search recipes...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Featured Recipes
            Text("Featured Recipes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = primaryColor)
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(mockRecipes.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(150.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = android.R.drawable.ic_menu_report_image),
                                contentDescription = "Recipe Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = mockRecipes[index],
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = primaryColor)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Breakfast")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Lunch")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Dinner")
                }
            }
    }
}
