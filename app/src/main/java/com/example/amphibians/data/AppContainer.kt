package com.example.amphibians.data

import com.example.amphibians.network.AmphibianApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibianRepository: AmphibianRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitApiService: AmphibianApiService by lazy {
        retrofit.create(AmphibianApiService::class.java)
    }

    override val amphibianRepository: AmphibianRepository by lazy {
        NetworkAmphibianRepository(retrofitApiService)
    }


}

/* Why in this code there are no retrofit and api inside the AppContainer interface?
https://gemini.google.com/app/68a18cb97358dcb3

That's a great question, and it gets to the heart of why we use interfaces and patterns like Dependency Injection 
or Service Location in software development, especially in Android!

Here's the reason why `retrofit` and `retrofitApiService` are in the `DefaultAppContainer` class but *not* in the 
`AppContainer` interface:

1.  **Abstraction and Information Hiding:**
    * The `AppContainer` interface defines the *contract* or *public API* of the container. It specifies *what* 
    dependencies the rest of your application can *get* from the container. In this case, it promises to provide 
    an `AmphibianRepository`.
    * The `DefaultAppContainer` is one specific *implementation* of that contract. It knows *how* to create the 
    `AmphibianRepository`. To do that, it *internally* uses Retrofit and the `AmphibianApiService`.
    * Retrofit and the specific `AmphibianApiService` are *implementation details* of how the `DefaultAppContainer` 
    fetches amphibian data over the network. The parts of your application that *use* the `AppContainer` don't need 
    to know *how* the repository gets its data (whether it's from a network, a database, a fake source for testing, etc.). 
    They only need the `AmphibianRepository` itself.

2.  **Flexibility and Swappability:**
    * By hiding `retrofit` and `retrofitApiService` in the implementation, you can easily provide a *different* 
    implementation of `AppContainer` later. For example:
        * You could create a `FakeAppContainer` for testing that provides a `FakeAmphibianRepository` which returns 
        hardcoded data without any networking. This `FakeAppContainer` wouldn't need Retrofit at all.
        * If you decided to switch from Retrofit to a different networking library (like Ktor or Volley), you would 
        only need to create a new implementation of the `AppContainer` interface (e.g., `KtorAppContainer`) that uses 
        the new library internally. The `AppContainer` interface and the code *using* the `AppContainer` wouldn't need to change.
    * This promotes loose coupling: the code that depends on `AppContainer` is only coupled to the `AmphibianRepository` 
    interface, not to the specific networking library or API service implementation.

3.  **Clarity and Focus:**
    * The `AppContainer` interface clearly states its primary purpose: providing the `AmphibianRepository`. 
    Including internal building blocks like `retrofit` in the interface would clutter it and expose details that consuming 
    code doesn't need or shouldn't rely on.

In essence, the `AppContainer` interface declares *what* dependencies are available, while the `DefaultAppContainer` 
class defines *how* those dependencies are created, using whatever internal components (like Retrofit) it needs. 
This is a core principle of good software design for testability, maintainability, and flexibility.*/
