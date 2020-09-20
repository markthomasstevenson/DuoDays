package uk.co.qiiq.duodays.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import io.realm.Realm


open class BaseViewModel : ViewModel() {
    var realm: Realm = Realm.getDefaultInstance()

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}