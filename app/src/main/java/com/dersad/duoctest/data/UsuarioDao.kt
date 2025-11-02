package com.dersad.duoctest.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Upsert
    suspend fun upsert(usuario: Usuario)

    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getById(id: Int): Flow<Usuario?>

    @Query("DELETE FROM usuarios")
    suspend fun clear()
}
