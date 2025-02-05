# Aplicación de Gestión de Tareas
Esta es una aplicación de gestión de tareas desarrollada en Android utilizando Kotlin y Jetpack Compose, la cual permite crear y eliminar dichas tareas.
La aplicación utiliza varias tecnologías para garantizar un código limpio, escalable y facil de mantener

## Tecnologías utilizadas
- **Arquitectura MVVM** como patrón de diseño para mejorar la mantenibilidad y escalabilidad del código
- **ROOM** como base de datos con SQLite para la persistencia de datos
- **Dagger Hilt** para la inyección de dependencias
- **Livedata** para asegurar que los datos de la interfaz del usuario coincidan con el estado actual de los datos de la aplicación
- **Flow** para manejar flujos de datos asíncronos
- **Pruebas de UI** Se han implementado pruebas de interfaz de usuario para garantizar que la aplicación funcione correctamente desde la perspectiva del usuario

## Estructura del proyecto
El proyecto esta organizado de la siguiente manera:
- **data** Contiene las clases relacionadas con la base de datos(ROOM en este caso), el DAO y el repositorio.
  Además dentro de data se encuentra la carpeta de **di** que contine el modulo de Dagger Hilt para la inyección de dependencias a la base de datos.
- **domain** Contine los casos de uso para interartuar con la base de datos
- **ui** Este directorio además esta dividido de la siguiente manera:
  - **model** En el se encuentra una data class que sirve como modelo de datos y una interfaz para controlar el estado de la aplicación.
  - **view** Donde he añadido la parte de ui de la aplicación
  - **viewmodel** Donde he añadido la lógica para su implementación
