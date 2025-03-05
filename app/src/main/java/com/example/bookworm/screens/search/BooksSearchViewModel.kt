package com.example.bookworm.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.DataOrException
import com.example.bookworm.model.Item
import com.example.bookworm.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.bookworm.data.Resource
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class BooksSearchViewModel @Inject constructor(private val repository: BookRepository): ViewModel()
{
//                              USING Resource WRAPPER
    //var list: MutableState<List<Item>> = mutableStateOf(listOf()) --->> Alternate method
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)
    init{
        loadBooks()
    }
    private fun loadBooks()
    {
        searchBooks("Android")
    }
    fun searchBooks(query: String)
    {
        viewModelScope.launch(Dispatchers.Default){
            isLoading = true
            if(query.isEmpty())
                return@launch
            try{
                when(val response = repository.getBooks(query))
                {
                    is Resource.Success -> {
                        list = response.data!!
                        if(list.isNotEmpty())
                            isLoading = false
                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.e("Network", "searchBooks: Failed getting books")
                    }
                    else -> {isLoading = false}
                }
            }catch(exception: Exception)
            {
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }
        }
    }
}

//                              USING DataOrException WRAPPER
//    var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//    init{
//        searchBooks("Android")
//    }
//
//    fun searchBooks(query: String)
//    {
//        viewModelScope.launch()
//        {
//            if(query.isEmpty())
//            {
//                return@launch
//            }
//            listOfBooks.value.loading = true
//            listOfBooks.value = repository.getBooks(query)
//            Log.d("Search", "searchBooks: ${listOfBooks.value.data}")
//            if(listOfBooks.value.data.toString().isNotEmpty())
//                listOfBooks.value.loading = false
//        }
//    }
