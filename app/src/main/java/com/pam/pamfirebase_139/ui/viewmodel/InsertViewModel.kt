package com.pam.pamfirebase_139.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.pamfirebase_139.model.Mahasiswa
import com.pam.pamfirebase_139.repository.RepositoryMhs
import kotlinx.coroutines.launch

class InsertViewModel(private val mhs: RepositoryMhs) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    fun isFormValid(): Boolean {
        val event = uiState.insertUiEvent
        return event.nama.isNotBlank() &&
                event.nim.isNotBlank() &&
                event.gender.isNotBlank() &&
                event.kelas.isNotBlank() &&
                event.angkatan.isNotBlank() &&
                event.judul.isNotBlank() &&
                event.dosbim1.isNotBlank() &&
                event.dosbim2.isNotBlank()
    }

    fun insertMhs() {
        viewModelScope.launch {
            try {
                val mahasiswa = uiState.insertUiEvent.toMhs()
                mhs.insertMhs(mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val nim: String = "",
    val nama: String = "",
    val gender: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judul: String = "",
    val dosbim1: String = "",
    val dosbim2: String = ""
)

fun InsertUiEvent.toMhs(): Mahasiswa {
    return Mahasiswa(
        nim = nim,
        nama = nama,
        gender = gender,
        kelas = kelas,
        angkatan = angkatan,
        judul = judul,
        dosbim1 = dosbim1,
        dosbim2 = dosbim2
    )
}