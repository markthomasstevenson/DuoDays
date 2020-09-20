package uk.co.qiiq.duodays.viewmodel

import androidx.lifecycle.LiveData
import io.realm.RealmResults
import uk.co.qiiq.duodays.func.contentDao
import uk.co.qiiq.duodays.model.Content

open class ListContentViewModel : BaseViewModel() {
    fun getContent(): LiveData<RealmResults<Content>> {
        return realm.contentDao().getContent()
    }

    fun deleteContent(id: String) {
        realm.contentDao().deleteContent(id)
    }
}