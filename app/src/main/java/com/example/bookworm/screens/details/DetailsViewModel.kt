package com.example.bookworm.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookworm.data.Resource
import com.example.bookworm.model.Item
import com.example.bookworm.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository)
    : ViewModel()
{
    private val _bookInfo = mutableStateOf<Resource<Item>>(Resource.Loading())
    val bookInfo: MutableState<Resource<Item>> = _bookInfo
    fun getBookInfo(bookId: String): Resource<Item>
    {
        viewModelScope.launch{
             _bookInfo.value = repository.getBookInfo(bookId)
        }
        return bookInfo.value
    }

}