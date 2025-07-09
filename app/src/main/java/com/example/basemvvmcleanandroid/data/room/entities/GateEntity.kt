package com.example.basemvvmcleanandroid.data.room.entities


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "gate",
    foreignKeys = [
        ForeignKey(
            entity = LoginResponseEntity::class,
            parentColumns = ["id"],
            childColumns = ["loginResponseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val loginResponseId: Int,
    // Add your Gate properties here based on your Gate data class
    // For example:
    val gateId: String,
    val gateName: String,
    val isActive: Boolean
    // Add other properties as needed
)
