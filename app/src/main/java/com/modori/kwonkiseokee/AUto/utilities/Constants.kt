package com.modori.kwonkiseokee.AUto.utilities

import com.modori.kwonkiseokee.AUto.R

/**
 *  Constants used throughout the app.
 * */

const val CHANNEL_ID = "AUTO_SLIDE"
const val GROUP_KEY_WORK_AUTO = "AUTO_SLIDE_GROUP"

const val APP_DATABASE_NAME = "auto-db"
const val TAG_DATABASE_NAME = "tag-db"

const val PREFS_FILE = "PrefsFile"

enum class DownloadType(val type: Int, val text: String) {
    RAW(0, "RAW"), FULL(1, "FULL"), REGULAR(2, "REGULAR")
}

enum class SourceType(val source: String) {
    // 0,1,2
    DOWNLOADED("앱에서 다운로드 받은 사진"), MY_FOLDER("내가 선택한 폴더"), GALLERY_PICK("내가 갤러리에서 선택한 사진들")
}

enum class PresentColor(val color: Int) {
    DOWNLOADED(R.color.DOWNLOAD), MY_FOLDER(R.color.MY_FOLDER), GALLERY_PICK(R.color.GALLERY_PICK),
    DOWNLOADED_ENABLED(R.color.DOWNLOAD_ENABLED), MY_FOLDER_ENABLED(R.color.MY_FOLDER_ENABLED), GALLERY_PICK_ENABLED(R.color.GALLERY_PICK_ENABLED)
}

enum class PeriodType(val value: String, val info: String) {
    TWO_DAYS("02-00-00","이틀마다 한번씩 변경"), ONE_DAY("01-00-00","하루에 한번 변경"), //0,1
    TWE_HOURS("00-12-00","열두시간 마다 한번씩 변경"), SIX_HOURS("00-06-00","여섯시간 마다 한번씩 변경"),// 2,3
    ONE_HOUR("00-01-00","한시간 마다 변경"), THR_MINS("00-00-30","30분마다 변경"),// 4,5
    FIT_MINS("00-00-15","15분마다 변경"), ONE_MIN("00-00-01","1분마다 변경") // 6,7
}