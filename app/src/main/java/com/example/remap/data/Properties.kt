package com.example.remap.data

data class Properties(
    var property_name: String,
    var property_description: String,
    var property_latitude: Double,
    var property_longitude: Double
){
    constructor():this("","",47.234,39.700)
}
