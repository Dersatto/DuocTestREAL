package com.dersad.duoctest.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Upsert
    suspend fun upsert(producto: Producto)

    @Upsert
    suspend fun upsertAll(vararg producto: Producto) // Añadido para insertar múltiples productos

    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE id = :id")
    fun getById(id: Int): Flow<Producto?>

    @Query("DELETE FROM productos")
    suspend fun clear()
}
