package com.dai.novel.database

/**
 * Created by dai on 2017/7/26.
 */
data class Company(val map: MutableMap<String, Any?>) {
    var _id: Long by map
    var name: String by map
    var address: String by map

    constructor() : this(HashMap()) {
    }

    constructor(id: Long, name: String, address: String) : this(HashMap()) {
        this._id = id
        this.name = name
        this.address = address
    }

}