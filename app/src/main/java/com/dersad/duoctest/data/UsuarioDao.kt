package com.dersad.duoctest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsersOnce(): List<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(usuario: Usuario)

    @Update
    suspend fun update(usuario: Usuario)
}