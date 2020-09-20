package uk.co.qiiq.duodays.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Content(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var content: String = "",
    var created: Date = Calendar.getInstance().time) : RealmObject()