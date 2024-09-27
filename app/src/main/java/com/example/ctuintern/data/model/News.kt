package com.example.ctuintern.data.model

import java.io.Serializable

data class News(
    var newID: String? = null,
    var title: String? = null,
    var content: String? = null,
    var postDay: String? = null,
    var quantity: Int? = null,
    var salary: Double? = null,
    var location: String? = null,
    var expireDay: String? = null,
    var employer: Employer? = null,
    var isFavorite: Boolean = false
):Serializable {
    override fun toString(): String {
        return "News(newID=$newID, title=$title, content=$content, postDay=$postDay, quantity=$quantity, salary=$salary, location=$location, expireDay=$expireDay, employer=$employer)"
    }
}