package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var loggedInBoleta by remember { mutableStateOf<String?>(value = null) }
    var loggedInGrupo by remember { mutableStateOf<String?>(value = null) }
    var loggedInHorario by remember { mutableStateOf<String?>(value = null) }

    if (loggedInBoleta == null) {
        LoginScreen(
            onLoginSuccess = { boleta, grupo, horario ->
                loggedInBoleta = boleta
                loggedInGrupo = grupo
                loggedInHorario = horario
            }
        )
    } else {
        HomeScreen(
            boleta = loggedInBoleta!!,
            grupo = loggedInGrupo!!,
            horario = loggedInHorario!!,
            onLogout = {
                loggedInBoleta = null
                loggedInGrupo = null
                loggedInHorario = null
            }
        )
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: (String, String, String) -> Unit
) {
    var boleta by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(value = false) }
    var errorMessage by remember { mutableStateOf<String?>(value = null) }
    var isLoading by remember { mutableStateOf(false) }

    val db = FirebaseFirestore.getInstance()

    Box(modifier = Modifier.fillMaxSize()) {
        SmoothFluidBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // Encabezado
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                AsyncImage(
                    model = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Logo_Instituto_Politecnico_Nacional.png/800px-Logo_Instituto_Politecnico_Nacional.png",
                    contentDescription = "Escudo Oficial IPN",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(bottom = 12.dp)
                )

                Text(
                    text = "INSTITUTO POLITÉCNICO NACIONAL",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "La Técnica al Servicio de la Patria",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFE0E0E0),
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Tarjeta de Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0x33000000)
                ),
                border = BorderStroke(1.dp, Color(0x33FFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = boleta,
                        onValueChange = {
                            boleta = it
                            errorMessage = null
                        },
                        label = { Text("Número de Boleta", color = Color(0xFFE0E0E0)) },
                        placeholder = { Text("Ej. 2023600001", color = Color(0xFFA0A0A0)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = "Boleta",
                                tint = Color.White
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFFB4A2),
                            unfocusedBorderColor = Color(0x88FFFFFF),
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color(0xFFE0E0E0),
                            cursorColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        shape = RoundedCornerShape(16.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        label = { Text("Contraseña", color = Color(0xFFE0E0E0)) },
                        placeholder = { Text("••••••••", color = Color(0xFFA0A0A0)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                    tint = Color.White
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFFB4A2),
                            unfocusedBorderColor = Color(0x88FFFFFF),
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color(0xFFE0E0E0),
                            cursorColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(16.dp)
                    )

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color(0xFFFF8A8A),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    Button(
                        onClick = {
                            val inputBoleta = boleta.trim()
                            val inputPass = password.trim()

                            if (inputBoleta.isEmpty() || inputPass.isEmpty()) {
                                errorMessage = "Ingresa boleta y contraseña"
                                return@Button
                            }

                            isLoading = true
                            // Consulta a Firestore: Colección "Alumnos", documento con la boleta
                            db.collection("Alumnos").document(inputBoleta)
                                .get()
                                .addOnSuccessListener { documento ->
                                    isLoading = false
                                    if (documento.exists()) {
                                        val passwordRegistrada = documento.getString("Password")
                                        if (passwordRegistrada == inputPass) {
                                            val grupoDelAlumno = documento.getString("Grupo") ?: ""

                                            // Consulta a la colección "Grupos" el documento del grupo (ej. "5IV11")
                                            db.collection("Grupos").document(grupoDelAlumno)
                                                .get()
                                                .addOnSuccessListener { docGrupo ->
                                                    val horario = if (docGrupo.exists()) {
                                                        docGrupo.getString("Horario") ?: "Sin horario registrado"
                                                    } else {
                                                        "Horario no encontrado"
                                                    }
                                                    onLoginSuccess(inputBoleta, grupoDelAlumno, horario)
                                                }
                                                .addOnFailureListener {
                                                    onLoginSuccess(inputBoleta, grupoDelAlumno, "No se pudo cargar el horario")
                                                }
                                        } else {
                                            errorMessage = "Contraseña incorrecta"
                                        }
                                    } else {
                                        errorMessage = "La boleta no está registrada"
                                    }
                                }
                                .addOnFailureListener { e ->
                                    isLoading = false
                                    errorMessage = "Error de conexión: ${e.localizedMessage}"
                                }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7D2242),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = "INICIAR SESIÓN",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    boleta: String,
    grupo: String,
    horario: String,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val ipnColor = Color(0xFF7D2242)
    val darkText = Color(0xFF1E1E1E)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent,
                drawerShape = RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp),
                modifier = Modifier.fillMaxWidth(0.95f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    SmoothFluidBackground(modifier = Modifier.fillMaxSize())

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        // --- ENCABEZADO DEL MENÚ ---
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp, top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Logo_Instituto_Politecnico_Nacional.png/800px-Logo_Instituto_Politecnico_Nacional.png",
                                contentDescription = "Logo IPN",
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "PORTAL IPN",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Boleta: $boleta",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFE0E0E0)
                                )
                                Text(
                                    text = "Grupo: $grupo",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFFFB4A2)
                                )
                            }
                        }

                        HorizontalDivider(color = Color(0x33FFFFFF), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        // --- OPCIONES DEL MENÚ ---
                        Text(
                            text = "Servicios Académicos",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        val services = listOf(
                            "Calificaciones" to Icons.Default.School,
                            "Horario de Clases" to Icons.Default.Schedule,
                            "Kárdex Académico" to Icons.Default.Assessment,
                            "Reinscripción" to Icons.Default.Description
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (service in services) {
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            imageVector = service.second,
                                            contentDescription = service.first,
                                            tint = Color(0xFFFFB4A2)
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = service.first,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White
                                        )
                                    },
                                    selected = false,
                                    onClick = { /* Acción al seleccionar */ },
                                    colors = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = Color.Transparent
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // --- BOTÓN CERRAR SESIÓN ---
                        OutlinedButton(
                            onClick = onLogout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFFFB4A2)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null,
                                tint = Color(0xFFFFB4A2)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CERRAR SESIÓN",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    ) {
        // --- CONTENIDO DE LA PANTALLA PRINCIPAL ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                // Barra superior en la pantalla principal
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = ipnColor)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Inicio",
                        style = MaterialTheme.typography.titleLarge,
                        color = darkText,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Tarjeta de bienvenida, grupo y horario obtenido de Firestore
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "¡Bienvenido Politécnico!",
                            style = MaterialTheme.typography.titleMedium,
                            color = ipnColor,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Boleta: $boleta",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Grupo: $grupo",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ipnColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Horario: $horario",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SmoothFluidBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "fluid_bg")

    val color1 by infiniteTransition.animateColor(
        initialValue = Color(0xFF550E28),
        targetValue = Color(0xFF8C1E46),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color1"
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFF14030A),
        targetValue = Color(0xFF370819),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color2"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(color1, color2)
                )
            )
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen(onLoginSuccess = { _, _, _ -> })
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen(boleta = "2023600001", grupo = "5IV11", horario = "Lunes a Viernes 7:00 - 13:00 hrs", onLogout = {})
    }
}
