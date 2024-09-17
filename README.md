# RickAndMortyApp
Este es el proyecto prueba de Flow, en esta app se muestra un listado de los personajes de la serie Rick And Morty y se puede acceder a cada uno para ver sus detalles.

# Experiencia de usuario
Este proyecto contiene las siguientes características:

* La pantalla principal donde se ve un listado de personajes.
* Una vista con un personaje específico con más información del mismo (se accede seleccionado un personaje del listado de la primera pantalla).
# Capturas de pantalla

<p align="center">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/eaae0b63-3443-46e8-91d2-337020436a87">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/c63fbdbc-582b-4503-b786-99cd007f8d6d">
  
</p>

## Guía de implementación
Traigo la información desde la api de Rick And Morty: https://rickandmortyapi.com/documentation

### Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

#### Capas
* Presentation: Fragments, Activities, Viewmodels.
* Data: contiene la implementación del repositorio y los sources donde se conecta con la api y con la base de datos.
* Domain: contiene los casos de uso y la definición del repositorio.
Este proyecto usa ViewModel para almacenar y manejar datos, así como comunicar cambios hacia la vista.

### Administrador de solicitudes: Retrofit

Este proyecto utiliza Retrofit para mostrar los personajes desde una API.

### Inyección de dependencia - Dagger

Este proyecto utiliza Dagger para gestionar la inyección de dependencia.

### Persistencia de datos - Room

Este proyecto utiliza la base de datos de Room para almacenar los personajes.

### Testing

La app posee tests hechos con JUnit4 y Espresso

### Patrones de diseño

Utilizo algunos patrones de diseño como Observer, Singleton, Builder

### Paginación

Tambien posee una rama donde implemento paging unicamente con la informacion remota.

# Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/RickAndMortyApp.git. Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
