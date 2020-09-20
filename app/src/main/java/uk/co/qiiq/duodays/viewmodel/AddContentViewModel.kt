package uk.co.qiiq.duodays.viewmodel

import uk.co.qiiq.duodays.func.contentDao
import uk.co.qiiq.duodays.model.ContentResponse

open class AddContentViewModel : BaseViewModel() {
    fun saveContent(title: String, content: String): ContentResponse {
        if (title.isEmpty()) {
            return ContentResponse.EmptyTitle
        }
        if (content.isEmpty()) {
            return ContentResponse.EmptyContent
        }
        realm.contentDao().addContent(title, content)
        return ContentResponse.Success
    }
}