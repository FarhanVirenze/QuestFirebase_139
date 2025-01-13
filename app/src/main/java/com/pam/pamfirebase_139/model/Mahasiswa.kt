package com.pam.pamfirebase_139.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val gender: String,
    val kelas: String,
    val angkatan: String,
    val judul: String,
    val dosbim1: String,
    val dosbim2: String
) {
    constructor() : this("", "", "", "", "", "", "", "",)
}
