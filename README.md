**PhotoApp-Multi-Module-Android-Clean-Architecture-Jetpack-Compose-Kotlin-Hilt-Flow**
<p align="justify">
PhotoApp is a modern Android application built using Multi-Module Clean Architecture principles to ensure scalability, testability, and maintainability. It leverages the latest Android development tools and libraries, including Jetpack Compose for building a declarative and reactive UI, Kotlin Coroutines with Flow for managing asynchronous and reactive data streams, and Hilt for dependency injection
</p>

The application follows a layered architecture divided into three core modules:
- App Module → Responsible for UI, navigation, and dependency injection setup. It integrates with the domain layer through ViewModels and Jetpack Compose screens.
- Domain Module → Pure Kotlin layer containing the business logic, use cases, and entity models. It defines repository interfaces and remains independent of Android frameworks.
- Data Module → Provides the actual implementation of repositories. It handles data from remote APIs (via Retrofit & OkHttp).

By separating concerns into these distinct modules, the project achieves:
- ✅ Clear boundaries between layers
- ✅ High testability (domain logic independent of Android)
- ✅ Easier scalability for adding new features or modules
- ✅ Reactive and responsive UI powered by Kotlin Flow and Compose

It contains 2 screens **list of photos** and **details of the selected photo**.

**Screenshots** <br>
<img width="280" height="550" alt="List_Screen_1" src="https://github.com/user-attachments/assets/fd65c6a0-edba-424a-a137-370da41ea53d" /> &nbsp; <img width="280" height="550" alt="List_Screen_2" src="https://github.com/user-attachments/assets/3328cf47-a20f-4e3c-a360-35e234d7a546" /> <br/><br/>
<img width="280" height="550" alt="Detail_Screen_1" src="https://github.com/user-attachments/assets/3c2ad6f1-2bba-47ea-a761-de9ca41b14a2" /> &nbsp; <img width="280" height="550" alt="Detail_Screen_2" src="https://github.com/user-attachments/assets/985d1273-c388-40e7-bc73-b1edf167156c" />

**Features**
- Get list of photos
- Get details of the list items.

The tools I have used to gain the Android Clean Architecture are:

- **Android Clean Architecture:** The Clean Architecture is a software design pattern that separates the business logic from the presentation layer. This makes the application more modular and easier to test.
- **MVVM :** MVVM architecure is followed for the code boilerplate. Where Model ,View and ViewModel are clearly used to maintain the SOLID principle.
- **Kotlin :** Kotlin is a modern programming language that is fully interoperable with Java. It is concise, expressive, and safe (https://kotlinlang.org/).
- **Coroutine :** To reduce the main thread task we can divide the task in many thread asychronously using the Kotlin Coroutine using lifecycle scope (https://developer.android.com/kotlin/coroutines).
- **Hilt :** Hilt is a dependency injection library that makes it easy to manage dependencies in Android applications.
- **Jetpack Compose Navigation :** Jetpack Compose Navigation is a declarative navigation library that makes it easy to navigate between different screens in your app. It is declarative, meaning that you can describe your navigation in terms of what you want to happen, rather than how you want it to happen. This makes it easy to create complex navigation flows with less code.
- **Kotlin Flow :** In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value (https://developer.android.com/kotlin/flow).
- **Retrofit :** Network calling library .
- **Unit Testing:** Unit Testing is a software testing technique that is used to verify the correctness of individual units of code. I have used Junit4 and Mockk for the unit testing purpose.

**API Specification**

For this repository I have used a public unsplash api.It lists images with details.The api is "https://api.unsplash.com/photos?client_id=""&query=food".


