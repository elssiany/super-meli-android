# Super MELI App 🛍️

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Super MELI es una aplicación de Android de muestra y busca productos en Mercado Libre 🛍️ creada para demostrar el uso de herramientas modernas de desarrollo de Android (Kotlin, componentes de arquitectura, arquitectura limpia con MVVM, Room, componentes de materiales).

**Puede instalar y probar la última aplicación 👇**

[![SuperMELI](https://img.shields.io/badge/SuperMELI%F0%9F%93%A8%F0%9F%93%B2-APK-brightgreen.svg?style=for-the-badge&logo=android)](https://github.com/kevinserranoapps/super-meli-android/raw/develop/app/SuperMELI-App.apk)

## Mas
Carga la lista de prodcutos de Mercado Libre, usando la API de TheSportsDB, también puedes ver los detalles de un producto, agregar a los ultimos vistos y mucho más usando la API Developer Mercado Libre.

- Interfaz de usuario de material limpio y simple🤩.

## Usando [`Trello`](https://trello.com/)
Para gestionar tareas y aplicar correctamente metodologías ágiles se utilizó como complemento Trello, puedes ver el tablero de trabajo aquí [**(Super MELI Tablero)**](https://trello.com/b/iNF1gq5X/super-meli-android) Trello.


## 🔨 Construido con 🛠️
- [Kotlin](https://kotlinlang.org/) - Lenguaje de programación oficial y de primera clase para el desarrollo de Android.
- [Coroutines](https://developer.android.com/codelabs/kotlin-coroutines#0) - Las corrutinas nos brindan una manera fácil de hacer programación síncrona y asíncrona. Coroutines allow execution to be suspended and resumed later ..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Colección de bibliotecas que lo ayudan a diseñar aplicaciones sólidas, comprobables y mantenibles.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Objetos de datos que notifican a las vistas cuando cambia la base de datos subyacente.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - La clase ViewModel está diseñada para almacenar y administrar datos relacionados con la interfaz de usuario de una manera optimizada para los ciclos de vida..
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - La vinculación de vistas es una función que facilita la escritura de código que interactúa con las vistas.
  - [Room](https://developer.android.com/training/data-storage/room) - es un ORM (mapeador relacional de objetos) para la base de datos SQLite en Android. Es parte de los Componentes de Arquitectura lanzados por Google.
  - [Navegation](https://developer.android.com/guide/navigation/navigation-principles) - El componente de navegación de Android hace que todo el sistema de navegación de la aplicación sea fácilmente manejable.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -... Hilt es una biblioteca de inyección de dependencias para Android que reduce la necesidad de realizar una inyección de dependencias manual en tu proyecto. Hacer una inyección manual de dependencias requiere que construyas cada clase y sus dependencias a mano, y que uses contenedores para reutilizar y administrar las dependencias.
- [Retrofit](https://square.github.io/retrofit/) - es un cliente REST con seguridad de tipos para Android, Java y Kotlin desarrollado por Square. La biblioteca proporciona un marco poderoso para autenticar e interactuar con las API y enviar solicitudes de red con OkHttp....
- [Glide](https://bumptech.github.io/glide/) - es una biblioteca Image Loader para Android desarrollada por bumptech y es una biblioteca recomendada por Google...
- [Material Components for Android](https://github.com/material-components/material-components-android) - Componentes modulares y personalizables de Material Design UI para Android.
- [Core Testing](https://developer.android.com/training/testing/fundamentals) - La app esta aprobada con testing para garantizar el buen funcionamiento de los features.
- [Espresso Core](https://developer.android.com/training/testing/espresso/setup) - Pruebas de UI para tener los ultimos estandades de experiencia de usuario.


## Servicios utilizados en la aplicación y probados en Postman
Puedes ver aquí [**Postman Docs)**](https://www.postman.com/cryosat-physicist-36006246/workspace/developer-mercado-libre/collection/22661298-3cc0ada3-e87d-42c0-8911-8977f1cc011f?action=share&creator=22661298) API.



## Arquitectura
Esta aplicación utiliza [(**Clean Architecture with MVVM)**](https://www.toptal.com/android/android-apps-mvvm-with-clean-architecture) architecture.

![](https://github.com/kevinserranoapps/super-meli-android/raw/develop/screenshot/arquitectura-supermeli.png)

## Contact
Si necesitas ayuda, puedes conectarte conmigo.

Visit:- [Kevin Serrano](https://www.linkedin.com/in/kevin-serrano-m/)



## License
```
MIT License

Copyright (c) 2023 Kҽʋιɳ Sҽɾɾαɳσ

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
