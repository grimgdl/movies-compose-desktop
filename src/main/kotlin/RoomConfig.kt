import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.Dispatchers
import java.io.File


fun getDatabaseBuilder() : RoomDatabase.Builder<AppDatabase> {

    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    )

}




fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {

    return builder.setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addMigrations(migration1To2)
        .addMigrations(migration2_3)
        .build()

}



val migration2_3 = object : Migration(2,3) {
    override fun migrate(connection: SQLiteConnection) {

        connection.execSQL("alter table movie add column url TEXT")


    }
}



val migration1To2 = object:  Migration(1,2) {

    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
            
            CREATE TABLE new_movie(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT not null)
            
        """.trimIndent())


        connection.execSQL("""
            insert into new_movie(id, name)
            select id, name from movie
        """.trimIndent())

        connection.execSQL("drop table movie")


        connection.execSQL("alter table new_movie rename to movie")
    }

}







