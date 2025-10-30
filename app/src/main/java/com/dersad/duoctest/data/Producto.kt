package com.dersad.duoctest.data

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "descripcion") val descripcion: String,
    @ColumnInfo(name = "precio") val precio: Double,
    @DrawableRes @ColumnInfo(name = "image_res_id") val imageResId: Int
)
