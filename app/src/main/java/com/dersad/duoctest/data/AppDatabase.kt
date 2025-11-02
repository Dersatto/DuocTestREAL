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

@Database(entities = [Producto::class], version = 2, exportSchema = false) // Versión actualizada
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

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
                            Producto(nombre = "QUEBEC", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),

                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973", precio = 29990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The New Sound", descripcion = "Es el álbum debut en solitario de Geordie Greep, líder de la banda Black Midi, lanzado el 4 de octubre de 2024.", precio = 27990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Hamilton", descripcion = "", precio = 34990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Court Of The Crimson King", descripcion = "Fleetwood Mac - Álbum de 1977", precio = 25990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "The Queen Is Dead", descripcion = "Led Zeppelin - Álbum de 1971", precio = 31990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "In Utero", descripcion = "Nirvana - Álbum de 1991", precio = 28990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Stratosphere", descripcion = "Amy Winehouse - Álbum de 2006", precio = 26990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "A Moon Shaped Pool", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Deathconsciousness", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Hail to The Thief", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Vespertine", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Exmilitary", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground), Producto(nombre = "AM", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Heaven or Las Vegas", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "American Football", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "DRUKQS", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "SAW 85-92", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Loveless", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "VELOCITY : DESIGN : COMFORT", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Come in Weatherday", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "DUMMY", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "BLACK HOLES & REVELATIONS", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "POST", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Discovery", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "LONG SEASON", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Grace", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Discovery", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "IN THE AEROPLANE OVER THE SEA", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "FOREVER HOWLONG", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "IN THE AEROPLANE OVER THE SEA", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Siamese Dream", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Bocanada", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "pinkerton", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "Twin Fantasy", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),
                            Producto(nombre = "THIS IS HAPPENING", descripcion = "Arctic Monkeys - Álbum de 2013", precio = 30990.0, imageResId = R.drawable.ic_launcher_foreground),

                        )
                    }
                }
            }).build()
        }
    }
}