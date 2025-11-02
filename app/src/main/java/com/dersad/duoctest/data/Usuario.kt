package com.dersad.duoctest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "correo") val correo: String = "",
    @ColumnInfo(name = "contraseña") val contraseña: String = "",
    @ColumnInfo(name = "rol") val rol: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)
