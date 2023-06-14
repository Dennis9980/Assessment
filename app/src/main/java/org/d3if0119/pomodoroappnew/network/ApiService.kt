package org.d3if0119.pomodoroappnew.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if0119.pomodoroappnew.model.Book
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://gist.github.com/Dennis9980/f3c803d8a4423cf6fb3780e86b0af566/raw/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("static-api.json")
    suspend fun getBook(): List<Book>
}
object BookApi{
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    fun getBookUrl(judul: String): String{
        return "$BASE_URL$judul.png"
    }
}
enum class ApiStatus{LOADING, SUCCESS, FAILED}