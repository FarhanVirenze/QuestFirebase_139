package com.pam.pamfirebase_139.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.pam.pamfirebase_139.model.Mahasiswa
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkRepository(
    private val firestore: FirebaseFirestore
) : RepositoryMhs {

    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e: Exception) {
            throw Exception("Gagal menambahkan data mahasiswa: ${e.message}")
        }
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                value?.let {
                    val mhsList = it.documents.mapNotNull { doc ->
                        doc.toObject(Mahasiswa::class.java)
                    }
                    trySend(mhsList).isSuccess
                }
            }

        awaitClose {
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> = callbackFlow {
        val listenerRegistration = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    snapshot.toObject(Mahasiswa::class.java)?.let { mhs ->
                        trySend(mhs).isSuccess
                    } ?: close(IllegalStateException("Failed to parse Mahasiswa object."))
                } else {
                    close(IllegalStateException("Mahasiswa dengan NIM $nim tidak ditemukan."))
                }
            }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun deleteMhs(nim: String) {
        if (nim.isBlank()) {
            Log.e("RepositoryMhs", "NIM tidak boleh kosong.")
            throw IllegalArgumentException("NIM tidak boleh kosong.")
        }

        try {
            firestore.collection("Mahasiswa")
                .document(nim)
                .delete()
                .await()

            Log.d("RepositoryMhs", "Berhasil menghapus data mahasiswa dengan NIM: $nim")
        } catch (e: FirebaseFirestoreException) {
            Log.e("RepositoryMhs", "Kesalahan Firebase saat menghapus mahasiswa: ${e.message}")
            throw FirebaseFirestoreException(
                "Gagal menghapus data mahasiswa dari Firestore: ${e.message}",
                e.code
            )
        } catch (e: Exception) {
            Log.e("RepositoryMhs", "Kesalahan saat menghapus mahasiswa: ${e.message}")
            throw Exception("Gagal menghapus mahasiswa: ${e.message}")
        }
    }

    override suspend fun updateMhs(nim: String, mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim) // Field unik "nim"
                .set(mahasiswa)
                .await()
        } catch (e: Exception) {
            throw Exception("Gagal memperbarui data mahasiswa: ${e.message}")
        }
    }
}
