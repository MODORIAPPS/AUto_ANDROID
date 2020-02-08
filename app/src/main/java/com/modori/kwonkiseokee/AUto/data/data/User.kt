package com.modori.kwonkiseokee.AUto.data.data

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: String,
        @SerializedName("update_at") val update_at: String,
        @SerializedName("username") val username:String,
        @SerializedName("name") val name:String,
        @SerializedName("portfolio_url") val portfolio_url:String,
        @SerializedName("profile_image") val profile_image:ProfileImage)

//"user": {
//        "id": "Ul0QVz12Goo",
//                "username": "ugmonk",
//                "name": "Jeff Sheldon",
//                "first_name": "Jeff",
//                "last_name": "Sheldon",
//                "instagram_username": "instantgrammer",
//                "twitter_username": "ugmonk",
//                "portfolio_url": "http://ugmonk.com/",
//                "profile_image": {
//            "small": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41",
//                    "medium": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f",
//                    "large": "https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202"
//        },