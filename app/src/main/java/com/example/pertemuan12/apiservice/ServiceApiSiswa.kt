package com.example.pertemuan12.apiservice

import com.example.pertemuan12.modeldata.DataSiswa
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa():List<DataSiswa>

    @POST ("InsertTM.php")
    suspend fun  postSiswa(@Body dataSiswa: DataSiswa):retrofit2.Response<Void>

    //@GET("bacaTeman.php/{id}")
    //suspend fun getSatuSiswa(@Query("id") id: Int): DataSiswa
    //
    //@PUT("editTM.php/{id}")
    //suspend fun editSatuSiswa(@Query("id") id:Int,@Body dataSiswa):retrofit2.Response<Void>
    //
    //@DELETE("deleteTM.php/{id}")
    //suspend fun hapusSatuSiswa(@Query("id") id: Int):retrofit2.Response<Void>
}