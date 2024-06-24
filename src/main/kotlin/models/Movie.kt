package models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(@PrimaryKey(autoGenerate = true) val id: Int = 0, val name: String, val url: String? = null)