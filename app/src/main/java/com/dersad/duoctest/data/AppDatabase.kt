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
                            Producto(nombre = "QUEBEC", descripcion = "Ween - Álbum de 2003, conocido por su eclecticismo y elementos psicodélicos.", precio = 29990.0, stock = 10, categoria = "Rock", imageResId = R.mipmap.quebn_foreground),
                            Producto(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd - Álbum de 1973, un hito del rock progresivo que explora temas como la locura y el conflicto.", precio = 29990.0, stock = 8, categoria = "Rock", imageResId = R.mipmap.dark_foreground),
                            Producto(nombre = "The New Sound", descripcion = "Es el álbum debut en solitario de Geordie Greep, líder de la banda Black Midi, lanzado el 4 de octubre de 2024.", precio = 27990.0, stock = 12, categoria = "Experimental", imageResId = R.mipmap.news_foreground),
                            Producto(nombre = "The Queen Is Dead", descripcion = "The Smiths - Álbum de 1986, considerado su obra maestra y un pilar del indie rock británico.", precio = 31990.0, stock = 7, categoria = "Rock", imageResId = R.mipmap.gayaf_foreground),
                            Producto(nombre = "Stratosphere", descripcion = "Duster - Álbum de 1998, un influyente trabajo de 'slowcore' que combina guitarras espaciales y melancolía.", precio = 26990.0, stock = 9, categoria = "Soul", imageResId = R.mipmap.helena_foreground),
                            Producto(nombre = "A Moon Shaped Pool", descripcion = "Radiohead - Álbum de 2016, caracterizado por su enfoque orquestal, texturas ambientales y temas de desamor.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.moom_foreground),
                            Producto(nombre = "Deathconsciousness", descripcion = "Have a Nice Life - Álbum doble de 2008, un trabajo sombrío y épico de post-punk/ambient/doom pop.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.have_foreground),
                            Producto(nombre = "Hail to The Thief", descripcion = "Radiohead - Álbum de 2003, combina la experimentación electrónica con un regreso a estructuras de rock más directas.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.hail_foreground),
                            Producto(nombre = "Vespertine", descripcion = "Björk - Álbum de 2001, enfocado en sonidos íntimos, microbeats y arreglos delicados.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.bjo_foreground),
                            Producto(nombre = "Exmilitary", descripcion = "Death Grips - Mixtape debut de 2011, que fusiona hip-hop con noise, punk y electrónica industrial.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.tak_foreground),
                            Producto(nombre = "Heaven or Las Vegas", descripcion = "Cocteau Twins - Álbum de 1990, un punto culminante del 'Dream Pop' con voces etéreas y producción exuberante.", precio = 30990.0, stock = 6, categoria = "Dream Pop", imageResId = R.mipmap.heav_foreground),
                            Producto(nombre = "DRUKQS", descripcion = "Aphex Twin - Álbum de 2001, una vasta colección de IDM compleja y piezas minimalistas de piano.", precio = 40990.0, stock = 6, categoria = "Electronic", imageResId = R.mipmap.aphex_foreground),
                            Producto(nombre = "Deseo, carne y voluntad", descripcion = "Candelabro banda de Matias Avila,pesimo", precio = 666.0, stock = 6, categoria = "Shoegaze", imageResId = R.mipmap.matias_foreground),
                            Producto(nombre = "VELOCITY : DESIGN : COMFORT", descripcion = "Sweet Trip - Álbum de 2003, conocido por su mezcla caótica de IDM, shoegaze y pop.", precio =20990.0, stock = 6, categoria = "Electronic", imageResId = R.mipmap.ve_foreground),
                            Producto(nombre = "DUMMY", descripcion = "Portishead - Álbum debut de 1994, uno de los trabajos fundacionales del género 'Trip Hop'.", precio = 30990.0, stock = 6, categoria = "Trip Hop", imageResId = R.mipmap.dummy_foreground),
                            Producto(nombre = "POST", descripcion = "Björk - Álbum de 1995, un trabajo ecléctico y vibrante que explora géneros desde el trip hop hasta el jazz.", precio = 30990.0, stock = 6, categoria = "Pop", imageResId = R.mipmap.post_foreground),
                            Producto(nombre = "LONG SEASON", descripcion = "Fishmans - Álbum de 1996, una pieza de 'Dub' y 'Dream Pop' consistente en una sola canción de 35 minutos.", precio = 30990.0, stock = 6, categoria = "Experimental", imageResId = R.mipmap.longs_foreground),
                            Producto(nombre = "Hellfire", descripcion = "Black Midi - Álbum de 2022. Una obra teatral y caótica que abarca jazz fusion, art rock y metal progresivo. Incluye el tema 'Sugar/Tzu'.", precio = 30990.0, stock = 6, categoria = "Experimental", imageResId = R.mipmap.hell_foreground),
                            Producto(nombre = "Tornamesa Vinilos", descripcion = "Escucha Vinilos con una tornamesa de vinilos", precio = 666000.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.torna_foreground),
                            Producto(nombre = "Bocanada", descripcion = "Gustavo Cerati - Álbum de 1999, considerado una de las obras más importantes del rock latino, con influencias electrónicas.", precio = 30990.0, stock = 6, categoria = "Rock Latino", imageResId = R.mipmap.pdf_foreground),
                            Producto(nombre = "pinkerton", descripcion = "Weezer - Álbum de 1996, un trabajo más oscuro y personal que el anterior, valorado por su honestidad emocional.", precio = 30990.0, stock = 6, categoria = "Alternative", imageResId = R.mipmap.homies_foreground),
                            Producto(nombre = "Ants from Up There", descripcion = "Black Country, New Road - Álbum de 2022, un aclamado trabajo de post-rock y chamber pop, que se caracteriza por un desarrollo musical épico.", precio = 30990.0, stock = 6, categoria = "Indie", imageResId = R.mipmap.ants_foreground)
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