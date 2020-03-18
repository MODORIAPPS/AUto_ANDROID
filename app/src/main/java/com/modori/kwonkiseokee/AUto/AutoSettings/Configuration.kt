package com.modori.kwonkiseokee.AUto.AutoSettings

enum class SourceType{
    DOWNLOADED, MY_FOLDER, GALLERY_PICK
}

enum class PresentColor(val color:String){
    DOWNLOADED("#3DADF2"), MY_FOLDER("#D7F205"), GALLERY_PICK("#23D9B7"),
    DOWNLOADED_ENABLED("#ffffff"), MY_FOLDER_ENABLED("#ffffff"), GALLERY_PICK_ENABLED("#ffffff")
}

enum class PeriodType(val value:String){
    TWO_DAYS("02-00-00"),ONE_DAY("01-00-00"),TWE_HOURS("00-12-00"),SIX_HOURS("00-06-00"),
    ONE_HOUR("00-01-00"),THR_MINS("00-00-30"),FIT_MINS("00-00-15"),ONE_MIN("00-00-01")
}