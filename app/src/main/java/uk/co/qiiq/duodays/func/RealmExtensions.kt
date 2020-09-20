package uk.co.qiiq.duodays.func

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import uk.co.qiiq.duodays.data.ContentDao

fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

fun Realm.contentDao() : ContentDao = ContentDao(this)