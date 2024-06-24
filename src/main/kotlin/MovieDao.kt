
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import models.Movie


@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getMovies(): Flow<List<Movie>>


    @Insert
    suspend fun insertMovie(movie: Movie)


    @Delete
    suspend fun removeMovie(movie: Movie)



}