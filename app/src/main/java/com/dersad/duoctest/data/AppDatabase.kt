package com.dersad.duoctest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Producto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app.db"
            )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    INSTANCE?.let {
                        database ->
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = database.productoDao()
                            dao.upsert(Producto(nombre = "Vinilo de Shaw", descripcion = "Edición especial del álbum de Shaw", precio = 25000.0))
                            dao.upsert(Producto(nombre = "Laptop Gamer", descripcion = "Potente laptop para juegos", precio = 1200000.0))
                            dao.upsert(Producto(nombre = "Teclado Mecánico", descripcion = "Teclado con switches Cherry MX", precio = 85000.0))
                        }
                    }
                }
            })
            .build()
            .also { INSTANCE = it }
        }
    }
}