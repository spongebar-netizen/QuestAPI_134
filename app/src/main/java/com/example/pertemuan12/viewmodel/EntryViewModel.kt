package com.example.pertemuan12.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pertemuan12.model.DetailSiswa
import com.example.pertemuan12.model.UIStateSiswa
import com.example.pertemuan12.model.toDataSiswa
import com.example.pertemuan12.repositori.RepositoryDataSiswa

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }


    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    suspend fun addSiswa() {
        if (validasiInput()) {
            try {

                repositoryDataSiswa.insertSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
                println("Sukses Tambah Data")
            } catch (e: Exception) {
                println("Gagal tambah data: ${e.message}")
            }
        }
    }
}