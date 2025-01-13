package com.pam.pamfirebase_139.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.pamfirebase_139.model.Mahasiswa
import com.pam.pamfirebase_139.repository.RepositoryMhs
import kotlinx.coroutines.launch

class UpdateViewModel(private val mhs: RepositoryMhs) : ViewModel() {
    var uiState by mutableStateOf(UpdateUiState())
        private set

    fun loadMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                mhs.getMhs(nim).collect { mahasiswa ->
                    uiState = UpdateUiState(updateUiEvent = mahasiswa.toUpdateUiEvent())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMahasiswaState(updateUiEvent: UpdateUiEvent) {
        uiState = uiState.copy(updateUiEvent = updateUiEvent)
    }

    fun updateMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = uiState.updateUiEvent.toMahasiswa()
                mhs.updateMhs(nim, mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent()
)

data class UpdateUiEvent(
    val nim: String = "",
    val nama: String = "",
    val gender: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judul: String = "",
    val dosbim1: String = "",
    val dosbim2: String = ""
)

fun UpdateUiEvent.toMahasiswa(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    gender = gender,
    kelas = kelas,
    angkatan = angkatan,
    judul = judul,
    dosbim1 = dosbim1,
    dosbim2 = dosbim2
)

fun Mahasiswa.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    nim = nim,
    nama = nama,
    gender = gender,
    kelas = kelas,
    angkatan = angkatan,
    judul = judul,
    dosbim1 = dosbim1,
    dosbim2 = dosbim2
)

