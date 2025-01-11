package com.pam.pamfirebase_139.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import com.pam.pamfirebase_139.model.Mahasiswa
import com.pam.pamfirebase_139.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mhs: RepositoryMhs
) : ViewModel() {

    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
            mhs.getAllMhs()
                .onStart {
                    Log.d("HomeViewModel", "Fetching data started")
                    mhsUIState = HomeUiState.Loading
                }
                .catch { e ->
                    Log.e("HomeViewModel", "Error fetching data: ${e.message}")
                    mhsUIState = HomeUiState.Error(Exception("Terjadi kesalahan: ${e.message}"))
                }
                .collect { result ->
                    if (result.isEmpty()) {
                        Log.d("HomeViewModel", "No data found in Firestore")
                        mhsUIState = HomeUiState.Success(emptyList())
                    } else {
                        Log.d("HomeViewModel", "Data fetched successfully: ${result.size} items")
                        mhsUIState = HomeUiState.Success(result)
                    }
                }
        }
    }

    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                // Call repository's delete function to remove student data
                mhs.deleteMhs(nim)
                Log.d("HomeViewModel", "Successfully deleted student: $nim")

                // Refresh the student list after successful deletion
                getMhs()

            } catch (e: FirebaseFirestoreException) {
                // Handle Firebase Firestore specific errors
                Log.e("HomeViewModel", "Firebase error deleting student: ${e.message}")
                mhsUIState = HomeUiState.Error(
                    Exception("Failed to delete student from Firestore: ${e.message}")
                )

            } catch (e: IllegalArgumentException) {
                // Handle invalid argument exception (e.g., invalid or missing NIM)
                Log.e("HomeViewModel", "Invalid student data: ${e.message}")
                mhsUIState = HomeUiState.Error(
                    Exception("Invalid student data: ${e.message}")
                )

            } catch (e: Exception) {
                // Handle other general exceptions
                Log.e("HomeViewModel", "Error deleting student: ${e.message}")
                mhsUIState = HomeUiState.Error(
                    Exception("Failed to delete student: ${e.message}")
                )
            }
        }
    }
}

    sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    data class Error(val e: Throwable) : HomeUiState()
    object Loading : HomeUiState()
}
