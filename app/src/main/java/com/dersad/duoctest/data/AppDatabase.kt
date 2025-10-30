package com.dersad.duoctest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dersad.duoctest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Producto::class],
    version = 2, // 1. Versión incrementada a 2
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
                            // Asignamos una imagen a cada producto
                            dao.upsert(Producto(nombre = "Shaw", descripcion = "Shaw", precio = 25000.0, imageResId = R.mipmap.shaw_foreground))
                            dao.upsert(Producto(nombre = "Ushanka", descripcion = "Ushanka", precio = 1200000.0, imageResId = R.drawable.ic_launcher_foreground))
                            dao.upsert(Producto(nombre = "Fren", descripcion = "Fren", precio = 85000.0, imageResId = R.mipmap.mrfrog_foreground))
                            dao.upsert(Producto(nombre = "hola", descripcion = "hello", precio = 85000.0, imageResId = R.mipmap.mrfrog_foreground))
                            dao.upsert(Producto(nombre = "si agregas más productos recuerda reinstalar la app", descripcion = "hola", precio = 85000.0, imageResId = R.drawable.ic_launcher_foreground))

                            //IMPORTANTE, aqui ponemos los productos que iran directo a la base de datos
                            //
                        }
                    }
                }
            })
            .build()
            .also { INSTANCE = it }
        }
    }
}