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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pam.pamfirebase_139.costumwidget.TopAppBar
import com.pam.pamfirebase_139.model.Mahasiswa
import com.pam.pamfirebase_139.navigation.DestinasiNavigasi
import com.pam.pamfirebase_139.ui.viewmodel.PenyediaViewModel
import com.pam.pamfirebase_139.ui.viewmodel.UpdateUiEvent
import com.pam.pamfirebase_139.ui.viewmodel.UpdateUiState
import com.pam.pamfirebase_139.ui.viewmodel.UpdateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mahasiswa"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(nim) {
        viewModel.loadMahasiswa(nim)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateMahasiswaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMahasiswa(nim)
                    navigateBack()  // Navigate back after saving
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateUiState,
    onSiswaValueChange: (UpdateUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiState.updateUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@Composable
fun FormInput(
    updateUiEvent: UpdateUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val genderOptions = listOf("Laki-Laki","Perempuan")
    val kelasOptions = listOf("A", "B", "C")


    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = updateUiEvent.nama,
            onValueChange = { onValueChange(updateUiEvent.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan nama") },
            isError = updateUiEvent.nama.isBlank()
        )

        OutlinedTextField(
            value = updateUiEvent.nim,
            onValueChange = { onValueChange(updateUiEvent.copy(nim = it)) },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = updateUiEvent.nim.isBlank()
        )

        OutlinedTextField(
            value = updateUiEvent.judul,
            onValueChange = { onValueChange(updateUiEvent.copy(judul = it)) },
            label = { Text("Judul Skripsi ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Judul Skripsi") },
            isError = updateUiEvent.judul.isBlank()
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
                        selected = updateUiEvent.gender == gender,
                        onClick = { onValueChange(updateUiEvent.copy(gender = gender)) },
                        enabled = enabled
                    )
                    Text(text = gender)
                }
            }
        }

        OutlinedTextField(
            value = updateUiEvent.dosbim1,
            onValueChange = { onValueChange(updateUiEvent.copy(dosbim1 = it)) },
            label = { Text("Dosbim1") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Dosbim1") },
            isError = updateUiEvent.dosbim1.isBlank()
        )

        OutlinedTextField(
            value = updateUiEvent.dosbim2,
            onValueChange = { onValueChange(updateUiEvent.copy(dosbim2 = it)) },
            label = { Text("Dosbim2") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Dosbim2") },
            isError = updateUiEvent.dosbim2.isBlank()
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
                        selected = updateUiEvent.kelas == kelas,
                        onClick = { onValueChange(updateUiEvent.copy(kelas = kelas)) },
                        enabled = enabled
                    )
                    Text(text = kelas)
                }
            }
        }

        OutlinedTextField(
            value = updateUiEvent.angkatan,
            onValueChange = { onValueChange(updateUiEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = updateUiEvent.angkatan.isBlank()
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
