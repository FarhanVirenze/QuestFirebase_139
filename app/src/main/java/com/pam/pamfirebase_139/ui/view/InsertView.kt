package com.pam.pamfirebase_139.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pam.pamfirebase_139.costumwidget.TopAppBar
import com.pam.pamfirebase_139.navigation.DestinasiNavigasi
import com.pam.pamfirebase_139.ui.viewmodel.InsertUiEvent
import com.pam.pamfirebase_139.ui.viewmodel.InsertUiState
import com.pam.pamfirebase_139.ui.viewmodel.InsertViewModel
import com.pam.pamfirebase_139.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "insert"
    override val titleRes = "Tambah Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMhsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMhs()
                    navigateBack()
                }
            },
            isSaveEnabled = viewModel.isFormValid(),
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = isSaveEnabled
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val genderOptions = listOf("Laki-Laki","Perempuan")
    val kelasOptions = listOf("A", "B", "C")

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = insertUiEvent.nama,
            onValueChange = { onValueChange(insertUiEvent.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan nama") },
            isError = insertUiEvent.nama.isBlank()
        )

        OutlinedTextField(
            value = insertUiEvent.nim,
            onValueChange = { onValueChange(insertUiEvent.copy(nim = it)) },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = insertUiEvent.nim.isBlank()
        )

        OutlinedTextField(
            value = insertUiEvent.judul,
            onValueChange = { onValueChange(insertUiEvent.copy(judul = it)) },
            label = { Text("Judul Skripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Judul Skripsi") },
            isError = insertUiEvent.judul.isBlank()
        )

        Text(text = "Jenis Kelamin")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between radio buttons
        ) {
            genderOptions.forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = insertUiEvent.gender == gender,
                        onClick = { onValueChange(insertUiEvent.copy(gender = gender)) },
                        enabled = enabled
                    )
                    Text(text = gender)
                }
            }
        }

        OutlinedTextField(
            value = insertUiEvent.dosbim1,
            onValueChange = { onValueChange(insertUiEvent.copy(dosbim1 = it)) },
            label = { Text("Dosbim1") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Dosbim1") },
            isError = insertUiEvent.dosbim1.isBlank()
        )

        OutlinedTextField(
            value = insertUiEvent.dosbim2,
            onValueChange = { onValueChange(insertUiEvent.copy(dosbim2 = it)) },
            label = { Text("Dosbim2") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Dosbim2") },
            isError = insertUiEvent.dosbim2.isBlank()
        )

        Text(text = "Kelas")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between radio buttons
        ) {
            kelasOptions.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = insertUiEvent.kelas == kelas,
                        onClick = { onValueChange(insertUiEvent.copy(kelas = kelas)) },
                        enabled = enabled
                    )
                    Text(text = kelas)
                }
            }
        }

        OutlinedTextField(
            value = insertUiEvent.angkatan,
            onValueChange = { onValueChange(insertUiEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = insertUiEvent.angkatan.isBlank()
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data Untuk Menyimpan",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

