package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.google.gson.annotations.SerializedName

data class Restroom(
    @SerializedName("accessible")
    val accessible: Boolean,
    @SerializedName("approved")
    val approved: Boolean,
    @SerializedName("bearing")
    val bearing: String,
    @SerializedName("changing_table")
    val changingTable: Boolean,
    @SerializedName("city")
    val city: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("directions")
    val directions: String,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("downvote")
    val downvote: Int,
    @SerializedName("edit_id")
    val editId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("unisex")
    val unisex: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("upvote")
    val upvote: Int
)