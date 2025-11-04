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

@Database(entities = [Producto::class, Usuario::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val instance = getInstance(context)
                        val dao = instance.productoDao()
                        dao.upsertAll(
                            Producto(nombre = "QUEBEC", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, stock = 10, categoria = "Rock", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, stock = 8, categoria = "Rock", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The New Sound", descripcion = "Es el álbum debut en solitario de Geordie Greep, líder de la banda Black Midi, lanzado el 4 de octubre de 2024.", precio = 27990.0, stock = 12, categoria = "Experimental", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Queen Is Dead", descripcion = "Led Zeppelin - Álbum de 1971", precio = 31990.0, stock = 7, categoria = "Rock", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Stratosphere", descripcion = "Amy Winehouse - Álbum de 2006", precio = 26990.0, stock = 9, categoria = "Soul", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "A Moon Shaped Pool", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Deathconsciousness", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Hail to The Thief", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Vespertine", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Exmilitary", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Heaven or Las Vegas", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Dream Pop", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "DRUKQS", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Electronic", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Loveless", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Shoegaze", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "VELOCITY : DESIGN : COMFORT", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Electronic", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "DUMMY", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Trip Hop", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "POST", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Pop", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "LONG SEASON", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Experimental", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "FOREVER HOWLONG", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "IN THE AEROPLANE OVER THE SEA", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Indie", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Siamese Dream", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Bocanada", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Rock Latino", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "pinkerton", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Twin Fantasy", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, stock = 6, categoria = "Indie Rock", imageResId = R.drawable.ic_launcher_foreground)
                        )

                        val usuarioDao = instance.usuarioDao()
                        val testUser = Usuario(
                            correo = "test@example.com",
                            contraseña = "123456"
                        )
                        usuarioDao.upsert(testUser)
                    }
                }
            }).build()
        }
    }
}