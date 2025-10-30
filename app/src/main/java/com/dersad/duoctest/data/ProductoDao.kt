package com.dersad.duoctest.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


//DATA ACCESS OBJECT(DAO)
//@Dao: marca la interfaz como capa de acceso a datos.
//exactamente cmo la guia...
@Dao
interface ProductoDao {
    // Inserta si no existe (por PK) o actualiza si ya existe
    @Upsert
    suspend fun upsert(producto: Producto)

    // Observa en tiempo real la tabla completa
    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun getAll(): Flow<List<Producto>>

    // Obtener un producto por su id
    @Query("SELECT * FROM productos WHERE id = :id")
    fun getById(id: Int): Flow<Producto?> // Corregido a Int

    // Borra todo (útil para pruebas / botón “Limpiar”)
    @Query("DELETE FROM productos")
    suspend fun clear()
}
