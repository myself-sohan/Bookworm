package com.example.bookworm.di

import androidx.compose.runtime.Composable
import com.example.bookworm.network.BooksApi
import com.example.bookworm.repository.BookRepository
import com.example.bookworm.repository.FireRepository
import com.example.bookworm.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(FirebaseFirestore.getInstance()
        .collection("books")
    )

    @Singleton
    @Provides
    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi() : BooksApi
    {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}