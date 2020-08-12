package com.kkco.nongenderedrestroomfinder.restrooms.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restrooms")
data class Restroom(
    @field:SerializedName("accessible")
    val accessible: Boolean,
    @field:SerializedName("approved")
    val approved: Boolean,
    @field:SerializedName("bearing")
    val bearing: String,
    @field:SerializedName("changing_table")
    val changingTable: Boolean,
    @field:SerializedName("city")
    val city: String,
    @field:SerializedName("comment")
    val comment: String,
    @field:SerializedName("country")
    val country: String,
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("directions")
    val directions: String,
    @field:SerializedName("distance")
    val distance: Double,
    @field:SerializedName("downvote")
    val downvote: Int,
    @field:SerializedName("edit_id")
    val editId: Int,
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("latitude")
    val latitude: Double,
    @field:SerializedName("longitude")
    val longitude: Double,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("state")
    val state: String,
    @field:SerializedName("street")
    val street: String,
    @field:SerializedName("unisex")
    val unisex: Boolean,
    @field:SerializedName("updated_at")
    val updatedAt: String,
    @field:SerializedName("upvote")
    val upvote: Int
) {
    override fun toString() = name
}