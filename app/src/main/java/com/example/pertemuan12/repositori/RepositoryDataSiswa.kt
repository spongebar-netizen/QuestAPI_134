package com.example.pertemuan12.repositori

import com.example.pertemuan12.apiservice.ServiceApiSiswa
import com.example.pertemuan12.modeldata.DataSiswa

interface RepositoryDataSiswa {

    suspend fun getDataSiswa() : List<DataSiswa>

    suspend fun postDataSiswa(dataSiswa: DataSiswa) :retrofit2.Response<Void>
//  suspend fun getStatusSiswa(id:Int) : DataSiswa
//  suspend fun editDataSiswa(id:Int,DataSiswa) :retrofit2.Response<Void>
//  suspend fun hapusDataSiswa(id: Int) :retrofit2.Response<Void>
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
): RepositoryDataSiswa{
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa):retrofit2.Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)
//override suspend fun getSatuSiswa(id: Int): DataSiswa - serviceApiSiswa.getSatuSiswa()
//override suspend fun ediSatuSiswa(id: Int,dataSiswa :DataSiswa):retrofit2.Response<Void> = serviceApiSiswa.editSiswa(id,dataSiswa)
//override suspend fun hapusSatuSiswa(id: Int):retrofit2.Response<Void> = serviceApiSiswa.hapusSatuSiswa(id)
}