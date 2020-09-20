package uk.co.qiiq.duodays

import io.realm.Realm
import io.realm.log.RealmLog
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Spy
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import uk.co.qiiq.duodays.func.contentDao
import uk.co.qiiq.duodays.model.ContentResponse
import uk.co.qiiq.duodays.viewmodel.AddContentViewModel

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], application = MockApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, RealmLog::class)
class AddContentViewModelTest {
    @Rule
    @JvmField
    var rule: PowerMockRule = PowerMockRule()

    @Spy
    lateinit var mockRealm: Realm
    lateinit var mockViewModel: AddContentViewModel

    @Before
    fun setup() {
        PowerMockito.mockStatic(RealmLog::class.java)
        PowerMockito.mockStatic(Realm::class.java)

        val mockRealm = mock(Realm::class.java)

        val mockViewModel = PowerMockito.mock(AddContentViewModel::class.java)
        mockViewModel.realm = mockRealm


        this.mockRealm = mockRealm
        this.mockViewModel = mockViewModel
    }

    @Test
    fun saveContentWhenEmptyTitleReturnsCorrectResponse() {
        val result = mockViewModel.saveContent("", "content one")

        Assert.assertTrue(result == ContentResponse.EmptyTitle)
    }

    @Test
    fun saveContentWhenEmptyContentReturnsCorrectResponse() {
        val result = mockViewModel.saveContent("title one", "")

        Assert.assertTrue(result == ContentResponse.EmptyContent)
    }

    @Test
    fun saveContentWhenCorrectContentReturnsCorrectResponse() {
        val result = mockViewModel.saveContent("title one", "content one")

        Assert.assertTrue(result == ContentResponse.Success)
    }

    @Test
    fun saveContentWhenCorrectContentCallsContentDaoOnce() {
        mockViewModel.saveContent("title one", "content one")

        verify(mockRealm, times(1)).contentDao()
    }
}