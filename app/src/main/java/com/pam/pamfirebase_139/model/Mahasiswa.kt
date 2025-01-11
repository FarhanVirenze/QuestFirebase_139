package com.pam.pamfirebase_139.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val gender: String,
    val kelas: String,
    val angkatan: String,
) {
    constructor() : this("", "", "", "", "", "")
}
