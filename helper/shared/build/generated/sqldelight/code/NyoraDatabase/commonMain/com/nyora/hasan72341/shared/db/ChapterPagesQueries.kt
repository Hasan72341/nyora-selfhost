package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class ChapterPagesQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectForChapter(chapter_url: String, mapper: (
    chapter_url: String,
    manga_id: String,
    pages_json: String,
    fetched_at: Long,
  ) -> T): Query<T> = SelectForChapterQuery(chapter_url) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!
    )
  }

  public fun selectForChapter(chapter_url: String): Query<Chapter_pages> =
      selectForChapter(chapter_url) { chapter_url_, manga_id, pages_json, fetched_at ->
    Chapter_pages(
      chapter_url_,
      manga_id,
      pages_json,
      fetched_at
    )
  }

  public fun countAll(): Query<Long> = Query(1_666_830_466, arrayOf("chapter_pages"), driver,
      "ChapterPages.sq", "countAll", "SELECT COUNT(*) FROM chapter_pages") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    chapter_url: String,
    manga_id: String,
    pages_json: String,
    fetched_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-1_672_027_233, """
        |INSERT OR REPLACE INTO chapter_pages (chapter_url, manga_id, pages_json, fetched_at)
        |VALUES (?, ?, ?, ?)
        """.trimMargin(), 4) {
          bindString(0, chapter_url)
          bindString(1, manga_id)
          bindString(2, pages_json)
          bindLong(3, fetched_at)
        }
    notifyQueries(-1_672_027_233) { emit ->
      emit("chapter_pages")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByMangaId(manga_id: String): QueryResult<Long> {
    val result = driver.execute(1_295_628_733, """DELETE FROM chapter_pages WHERE manga_id = ?""",
        1) {
          bindString(0, manga_id)
        }
    notifyQueries(1_295_628_733) { emit ->
      emit("chapter_pages")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(-1_577_781_370, """DELETE FROM chapter_pages""", 0)
    notifyQueries(-1_577_781_370) { emit ->
      emit("chapter_pages")
    }
    return result
  }

  private inner class SelectForChapterQuery<out T : Any>(
    public val chapter_url: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("chapter_pages", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("chapter_pages", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_801_205_808,
        """SELECT chapter_pages.chapter_url, chapter_pages.manga_id, chapter_pages.pages_json, chapter_pages.fetched_at FROM chapter_pages WHERE chapter_url = ?""",
        mapper, 1) {
      bindString(0, chapter_url)
    }

    override fun toString(): String = "ChapterPages.sq:selectForChapter"
  }
}
