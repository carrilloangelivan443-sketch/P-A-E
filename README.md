# P-A-E
🚀 Descripción del Proyecto

PAE es una aplicación móvil nativa desarrollada en Kotlin (para Android) que surge como una reimaginación total del sistema escolar tradicional (SAES) del CECyT 5 "Benito Juárez" del IPN. Su filosofía principal es eliminar la fricción burocrática, depurar la información innecesaria y ofrecer una interfaz minimalista (inspirada en herramientas modernas como Google Classroom) que centralice las necesidades más vitales de la comunidad estudiantil.
✨ Características Principales

    Sesión Persistente: Inicio de sesión único sincronizado con bases de datos en la nube (Cloud Firestore), evitando accesos repetitivos.

    Horario Acomodado: Interfaz visual optimizada que corrige los problemas de superposición y distribución del sistema web anterior.

    Módulo Offline: Almacenamiento local para garantizar el acceso inmediato al Horario, Boleta e Inscripción actual sin necesidad de datos móviles en los filtros de entrada del plantel.

    Asistente Virtual "Benitsito": Un agente inteligente (potenciado por la API de Gemini) representado por la mascota del plantel (el águila), entrenado con el Reglamento General de Estudios del IPN para orientar a los alumnos en trámites críticos 24/7.

    Gestión Académica Integral: Menú lateral estructurado lógicamente para separar los avisos diarios de los trámites de continuidad académica (ETS, saberes previos, etc.).

   [ FASE 1: ARQUITECTURA Y DISEÑO BASE ] (Completada 🟢)
 ├── Definición de la propuesta de investigación y objetivos[cite: 3, 7]
 ├── Estructura de navegación y diseño minimalista en Jetpack Compose[cite: 1]
 └── Configuración de la paleta institucional (Guinda #671C36 y Blanco)[cite: 3]

[ FASE 2: CONEXIÓN EN LA NUBE Y AUTH ] (En proceso / Validando 🟡)
 ├── Integración de Firebase Firestore para alumnos y grupos[cite: 6, 8]
 ├── Validación de credenciales con boleta y contraseña persistente
 └── Carga dinámica del grupo (ej. 5IV11) para mostrar el horario correspondiente[cite: 6]

[ FASE 3: MÓDULO OFFLINE Y MULTI-GRUPO ] (Siguiente paso ⏳)
 ├── Configuración de almacenamiento local (Room / SharedPreferences) para acceso sin internet[cite: 6, 7]
 └── Pruebas con múltiples perfiles y grupos de prueba del CECyT 5[cite: 6, 7]

[ FASE 4: INTEGRACIÓN DE LA INTELIGENCIA ARTIFICIAL ] (Próximamente ⏳)
 ├── Conexión del SDK de la API de Gemini (Gemini Flash) para "Benitsito"[cite: 3, 4]
 ├── Inserción de las instrucciones de sistema (System Instructions) con el Reglamento del IPN
 └── Implementación del botón de respaldo offline (Árbol de decisiones en JSON)[cite: 4]

[ FASE 5: PRUEBAS DE USUARIO Y PITCH ] (Meta Final 🏁)
 ├── Pruebas de usabilidad con compañeros del plantel[cite: 7]
 └── Ensayo de la presentación en vivo (Demostración desconectando internet y consulta a la IA)[cite: 4, 7] 
