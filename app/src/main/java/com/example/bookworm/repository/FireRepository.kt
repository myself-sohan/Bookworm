package com.example.bookworm.repository

import com.example.bookworm.data.DataOrException
import com.example.bookworm.model.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBooks: Query
)
{
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception>
    {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()
        try{
            dataOrException.loading = true
            dataOrException.data = queryBooks.get().await().documents.map {documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if(dataOrException.data.isNullOrEmpty())
                dataOrException.loading = false
        }catch (exception: FirebaseFirestoreException){
            dataOrException.e = exception
        }
        return dataOrException
    }
}