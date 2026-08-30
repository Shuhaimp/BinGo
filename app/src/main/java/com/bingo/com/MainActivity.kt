
package com.bingo.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BinGoApp() }
    }
}

@Composable
fun BinGoApp() {
    var isLoggedIn by remember { mutableStateOf(Firebase.auth.currentUser != null) }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val bg = Color(0xFF06130E)
    val card = Color(0xFF143724)
    val green = Color(0xFF8BD34F)

    MaterialTheme {
        Box(Modifier.fillMaxSize().background(bg).padding(20.dp), contentAlignment = Alignment.Center) {
            if (!isLoggedIn) {
                // AUTH CARD - Native version of your HTML auth-card
                Column(Modifier.background(card.copy(alpha=0.9f), RoundedCornerShape(30.dp)).padding(34.dp).widthIn(max=450.dp)) {
                    Text("BinGo", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("Household Login", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(top=8.dp))
                    Text("Ecological waste collection system", color = Color(0xFFA9B9AE), modifier = Modifier.padding(bottom=24.dp))
                    if (error.isNotEmpty()) {
                        Text(error, color = Color(0xFFED625D), modifier = Modifier.padding(bottom=12.dp).background(Color(0x22ED625D), RoundedCornerShape(12.dp)).padding(10.dp))
                    }
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Household ID / Email") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = green, focusedLabelColor = green))
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = green, focusedLabelColor = green))
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = {
                        loading = true; error = ""
                        Firebase.auth.signInWithEmailAndPassword(email.trim(), pass)
                            .addOnSuccessListener { isLoggedIn = true; loading = false }
                            .addOnFailureListener { e -> error = e.message ?: "Login failed"; loading = false }
                    }, modifier = Modifier.fillMaxWidth().height(51.dp), colors = ButtonDefaults.buttonColors(containerColor = green, contentColor = Color(0xFF07150C)), shape = RoundedCornerShape(16.dp)) {
                        Text(if (loading) "Signing in..." else "Sign In", fontWeight = FontWeight.ExtraBold)
                    }
                }
            } else {
                // DASHBOARD - Native
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("BinGo Dashboard", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
                        Button(onClick = { Firebase.auth.signOut(); isLoggedIn = false }, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(0.08f))) { Text("Logout") }
                    }
                    Spacer(Modifier.height(20.dp))
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = card), shape = RoundedCornerShape(26.dp)) {
                        Column(Modifier.padding(24.dp)) {
                            Text("Welcome", color = green, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            Text(Firebase.auth.currentUser?.email ?: "Household", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text("Your native Android app is ready. No WebView used. All data from Firebase Firestore.", color = Color(0xFFA9B9AE), modifier = Modifier.padding(top=8.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Next step: Connect your Firestore collections (schedule, notifications, collectionPoints, wasteTypes) here. Your original HTML logic is in ecoFirestore - we can map it to these native composables.", color = Color(0xFFA9B9AE))
                }
            }
        }
    }
}
