package com.dersad.duoctest.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "correo") var correo: String = "",
    @ColumnInfo(name = "contraseña") var contraseña: String = "",
    @Ignore val errores: UsuarioErrores = UsuarioErrores()
)
