package com.example.bookworm.network

import com.example.bookworm.model.Book
import com.example.bookworm.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi
{
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item


}