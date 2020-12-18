package com.anubhab.das.marveltask.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.anubhab.das.marveltask.R
import com.anubhab.das.marveltask.ui.marvel.adapter.MarvelAdapter
import com.anubhab.das.marveltask.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Marvel::class), version = 1, exportSchema = false)
public abstract class MarvelDatabase: RoomDatabase() {

    abstract fun marvelDao(): MarvelDAO

    private class MarvelDatabaseCallback(
        private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val marvelDao = database.marvelDao()
                    val utils = Utils()

                    //marvelDao.deleteAll()
                    val marvel = (Marvel(1, "Iron Man", "When Tony Stark, an industrialist, is captured" +
                            ", he constructs a high-tech armoured suit to escape.", utils.getURLForResource(
                        R.drawable.iron_man)))
                    marvelDao.addMarvelCharacter(marvel)
                    val marvel1 = (Marvel(2, "Captain America", "During World War II, Steve Rogers decides to volunteer in" +
                            " an experiment that transforms his weak body.", utils.getURLForResource(
                        R.drawable.captain_america)))
                    marvelDao.addMarvelCharacter(marvel1)
                    val marvel2 = (Marvel(3, "Hulk", "Hulk is a 2003 American superhero film based on" +
                            " the Marvel Comics.", utils.getURLForResource(
                        R.drawable.hulk)))
                    marvelDao.addMarvelCharacter(marvel2)
                    val marvel3 = (Marvel(4, "Loki", "Loki is a fictional character appearing in American" +
                            " comic books published by Marvel Comics. ", utils.getURLForResource(
                        R.drawable.loki)))
                    marvelDao.addMarvelCharacter(marvel3)
                    val marvel4 = (Marvel(5, "Thor", "Thor is a 2011 American superhero film based on the Marvel" +
                            " Comics character of the same name. ", utils.getURLForResource(
                        R.drawable.thor)))
                    marvelDao.addMarvelCharacter(marvel4)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: MarvelDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MarvelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarvelDatabase::class.java,
                    "marvel_database"
                ).addCallback(MarvelDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
//    private val CALLBACK = object : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            suspend {
//                marvelDao().addMarvelCharacter(Marvel(1, "Stark", "asjkdasjkdjkas", "/askka/askks/nsjs"))
//                marvelDao().addMarvelCharacter(Marvel(1, "Stark", "asjkdasjkdjkas", "/askka/askks/nsjs"))
//                marvelDao().addMarvelCharacter(Marvel(1, "Stark", "asjkdasjkdjkas", "/askka/askks/nsjs"))
//                marvelDao().addMarvelCharacter(Marvel(1, "Stark", "asjkdasjkdjkas", "/askka/askks/nsjs"))
//                marvelDao().addMarvelCharacter(Marvel(1, "Stark", "asjkdasjkdjkas", "/askka/askks/nsjs"))
//            }
//        }
//    }
}