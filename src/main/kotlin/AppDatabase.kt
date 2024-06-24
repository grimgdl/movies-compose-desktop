import androidx.room.Database
import androidx.room.RoomDatabase
import models.Movie

@Database(entities = [Movie::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract fun MovieDao(): MovieDao
}