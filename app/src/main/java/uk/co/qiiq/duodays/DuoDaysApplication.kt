package uk.co.qiiq.duodays

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class DuoDaysApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("duodays.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}