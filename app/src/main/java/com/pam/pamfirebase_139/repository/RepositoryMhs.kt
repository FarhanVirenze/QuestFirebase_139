package com.pam.pamfirebase_139.repository

import com.pam.pamfirebase_139.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    //getAllMhs
    fun getAllMhs(): Flow<List<Mahasiswa>>
    //getMhs
    fun getMhs(nim: String): Flow<Mahasiswa>
    //deleteMhs
    suspend fun deleteMhs(nim: String)
    //updateMhs
    suspend fun updateMhs(nim: String, mahasiswa: Mahasiswa)
}