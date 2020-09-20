package uk.co.qiiq.duodays

import io.realm.*
import io.realm.log.RealmLog
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyObject
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import uk.co.qiiq.duodays.data.ContentDao
import uk.co.qiiq.duodays.model.Content


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], application = MockApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, RealmLog::class, RealmResults::class, RealmObject::class)
class ContentDaoTest {
    @Rule @JvmField
    var rule: PowerMockRule = PowerMockRule()

    lateinit var mockRealm: Realm
    lateinit var mockQuery: RealmQuery<Content>
    lateinit var contentDao: ContentDao
    lateinit var contentList: MutableList<Content>

    @Before
    fun setup() {
        mockStatic(RealmLog::class.java)
        mockStatic(Realm::class.java)
        mockStatic(RealmObject::class.java)

        // Set up some naive stubs
        val c1 = mock(Content::class.java)
        c1.title = "Title 1"
        c1.content = "Content 1"

        val contentList: MutableList<Content> = mutableListOf(c1)

        val mockRealm = mock(Realm::class.java)
        mockRealm.insert(c1)
        `when`(Realm.getDefaultInstance()).thenReturn(mockRealm)
        val mockQuery = mock(RealmQuery::class.java) as RealmQuery<Content>
        `when`(mockRealm.where(Content::class.java)).thenReturn(mockQuery)
        `when`(mockQuery.sort(anyString(), anyObject())).thenReturn(mockQuery)
        `when`(mockQuery.equalTo(anyString(), anyString())).thenReturn(mockQuery)
        Mockito.doNothing().`when`(c1).deleteFromRealm()


        mockStatic(RealmResults::class.java)
        val mockResults: RealmResults<Content> = mock(RealmResults::class.java) as RealmResults<Content>

        `when`(mockQuery.findAllAsync()).thenReturn(mockResults)

        `when`(mockQuery.findFirst()).thenReturn(contentList[0])

        `when`(mockResults.iterator()).thenReturn(contentList.iterator() as MutableIterator<Content>)

        `when`(mockResults.size).thenReturn(contentList.size)

        this.mockRealm = mockRealm
        this.mockQuery = mockQuery
        this.contentDao = ContentDao(mockRealm)
        this.contentList = contentList
    }

    @Test
    fun shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), `is`(mockRealm))
    }

    // region Add Content
    @Test
    fun addContentCallsBeginTransactionOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.addContent("title one", "content two")

        Mockito.verify(mockRealm, Mockito.times(1))
            .beginTransaction()
    }

    @Test
    fun addContentCallsInsertOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.addContent("title one", "content two")

        Mockito.verify(mockRealm, Mockito.times(2))
            .insert(ArgumentMatchers.any(Content::class.java))
    }

    @Test
    fun addContentCallsCommitTransactionOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.addContent("title one", "content two")

        Mockito.verify(mockRealm, Mockito.times(1))
            .commitTransaction()
    }
    //endregion

    //region Get Content
    @Test
    fun getContentCallsWhereOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.getContent()

        Mockito.verify(mockRealm, Mockito.times(1))
            .where(Content::class.java)
    }

    @Test
    fun getContentCallsSortOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.getContent()

        Mockito.verify(mockQuery, Mockito.times(1))
            .sort("created", Sort.DESCENDING)
    }

    @Test
    fun getContentCallsFindAllAsyncOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.getContent()

        Mockito.verify(mockQuery, Mockito.times(1))
            .findAllAsync()
    }
    //endregion

    // region Delete Content
    @Test
    fun deleteContentCallsBeginTransactionOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.deleteContent("id")

        Mockito.verify(mockRealm, Mockito.times(1))
            .beginTransaction()
    }

    @Test
    fun deleteContentCallsWhereOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.deleteContent("id")

        Mockito.verify(mockRealm, Mockito.times(1))
            .where(Content::class.java)
    }

    @Test
    fun deleteContentCallsFindFirstOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.deleteContent("id")

        Mockito.verify(mockQuery, Mockito.times(1))
            .findFirst()
    }

    @Test
    fun deleteContentCallsDeleteOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.deleteContent("id")

        Mockito.verify(contentList[0], Mockito.times(1))
            .deleteFromRealm()
    }

    @Test
    fun deleteContentCallsCommitTransactionOnce()
    {
        doCallRealMethod().`when`(mockRealm).executeTransaction(Mockito.any(Realm.Transaction::class.java))

        contentDao.deleteContent("id")

        Mockito.verify(mockRealm, Mockito.times(1))
            .commitTransaction()
    }
    //endregion
}