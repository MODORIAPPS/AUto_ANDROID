package com.modori.kwonkiseokee.AUto.data

class TagListRepository(private val dao: TagDao) {

    val tagList = dao.getTagLists()
    val isAvailable: Boolean = dao.getSize() > 0

    suspend fun update(newTag: String, targetId: Int) {
        dao.update(newTag, targetId)
    }

    suspend fun resetTagList() {
        dao.deleteAll()
        val tags = arrayOf("Landscape", "Anyvoca", "MilkyWay", "Yosemite", "Family", "Home")
        for ((value, index) in tags.withIndex()) {
            val tag = Tag(value, index)
            dao.insert(tag)
        }
    }

    suspend fun getSize(): Int {
        return dao.getSize()
    }

}