package com.example.remap.data

data class Properties(
        var property_adress: String,
        var property_contacts: String,
        var property_description: String,
        var property_latitude: Double,
        var property_longitude: Double,
        var property_name: String,
        var property_office_hours: String
        ){
    constructor():this("","","", 43.279, 39.313, "", "")
}
