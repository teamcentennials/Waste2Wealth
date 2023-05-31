package app.waste2wealth.com.ktorClient
//import android.util.Log
//import io.ktor.client.*
//import io.ktor.client.engine.android.Android
//import io.ktor.client.engine.cio.CIO
//import io.ktor.client.features.*
//import io.ktor.client.features.json.*
//import io.ktor.client.features.json.serializer.*
//import io.ktor.client.features.logging.*
//import io.ktor.client.features.observer.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import javax.inject.Inject
//
//class TmdbHttpClient @Inject constructor() {
//
//    fun getHttpClient() = HttpClient(Android) {
//
//        install(JsonFeature) {
//            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            })
//
//        }
//
//        install(Logging)
//
//    }
//
//}