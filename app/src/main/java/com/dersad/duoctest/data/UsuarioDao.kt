package com.dersad.duoctest.data

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UsuarioDao {

    @Upsert
    suspend fun upsert(usuario: Usuario)
    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    suspend fun getAllUsersOnce(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getById(id: Int): Usuario?

    @Query("DELETE FROM usuarios")
    suspend fun clear()
}
