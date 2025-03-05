package com.example.bookworm.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.DataOrException
import com.example.bookworm.model.MBook
import com.example.bookworm.repository.BookRepository
import com.example.bookworm.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository)
    : ViewModel()
{
//        val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>>
//            = mutableStateOf(
//                DataOrException(listOf(), true, Exception(""))
//            )
private var _data = MutableStateFlow(DataOrException(emptyList<MBook>(), true, Exception("")))
    val data: StateFlow<DataOrException<List<MBook>, Boolean, Exception>> = _data
    init {
        getAllBooksFromDatabase()
    }

    fun getAllBooksFromDatabase()
    {
        viewModelScope.launch{
            _data.value.loading = true
            _data.value = repository.getAllBooksFromDatabase()
            if(!_data.value.data.isNullOrEmpty())
                _data.value.loading = false
        }
        Log.d("GET","${_data.value.data?.toList()}")
    }
//    fun getAllBooksFromDatabase()
//    {
//        viewModelScope.launch{
//            data.value.loading = true
//            data.value = repository.getAllBooksFromDatabase()
//            if(!data.value.data.isNullOrEmpty())
//                data.value.loading = false
//        }
//        Log.d("GET","${data.value.data?.toList()}")
//    }
}