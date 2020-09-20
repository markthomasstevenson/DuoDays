package uk.co.qiiq.duodays.data

import androidx.lifecycle.LiveData
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import uk.co.qiiq.duodays.func.asLiveData
import uk.co.qiiq.duodays.model.Content

open class ContentDao(val realm: Realm) {

    fun addContent(titleInput: String, contentInput: String){
        realm.beginTransaction()

        val newItem = Content().apply {
            title = titleInput
            content = contentInput
        }
        realm.insert(newItem)

        realm.commitTransaction()
    }

    fun getContent(): LiveData<RealmResults<Content>> {
        return realm.where(Content::class.java).sort("created", Sort.DESCENDING)
            .findAllAsync().asLiveData()
    }

    fun deleteContent(id: String) {
        realm.beginTransaction()

        val result = realm.where(Content::class.java).equalTo("id", id).findFirst()
        result?.deleteFromRealm()

        realm.commitTransaction()
    }
}