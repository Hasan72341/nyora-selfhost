package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import com.nyora.hasan72341.shared.db.bookmark.SelectAll
import kotlin.Any
import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.String

public class BookmarkQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    manga_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    note: String,
    created_at: Long,
    manga_title: String?,
    manga_cover_url: String?,
  ) -> T): Query<T> = Query(-2_107_907_658, arrayOf("bookmark", "manga"), driver, "Bookmark.sq",
      "selectAll", """
  |SELECT b.id, b.manga_id, b.chapter_id, b.chapter_title, b.page, b.note, b.created_at,
  |       m.title AS manga_title, m.cover_url AS manga_cover_url
  |FROM bookmark b
  |LEFT JOIN manga m ON m.id = b.manga_id
  |ORDER BY b.created_at DESC
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!,
      cursor.getString(7),
      cursor.getString(8)
    )
  }

  public fun selectAll(): Query<SelectAll> = selectAll { id, manga_id, chapter_id, chapter_title,
      page, note, created_at, manga_title, manga_cover_url ->
    SelectAll(
      id,
      manga_id,
      chapter_id,
      chapter_title,
      page,
      note,
      created_at,
      manga_title,
      manga_cover_url
    )
  }

  public fun <T : Any> selectForChapter(
    manga_id: String,
    chapter_id: String,
    mapper: (
      id: Long,
      manga_id: String,
      chapter_id: String,
      chapter_title: String,
      page: Long,
      note: String,
      created_at: Long,
      manga_title: String?,
      manga_cover_url: String?,
    ) -> T,
  ): Query<T> = SelectForChapterQuery(manga_id, chapter_id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!,
      cursor.getString(7),
      cursor.getString(8)
    )
  }

  public fun selectForChapter(manga_id: String, chapter_id: String): Query<SelectForChapter> =
      selectForChapter(manga_id, chapter_id) { id, manga_id_, chapter_id_, chapter_title, page,
      note, created_at, manga_title, manga_cover_url ->
    SelectForChapter(
      id,
      manga_id_,
      chapter_id_,
      chapter_title,
      page,
      note,
      created_at,
      manga_title,
      manga_cover_url
    )
  }

  public fun existsForPage(
    manga_id: String,
    chapter_id: String,
    page: Long,
  ): Query<Boolean> = ExistsForPageQuery(manga_id, chapter_id, page) { cursor ->
    cursor.getBoolean(0)!!
  }

  public fun <T : Any> selectAllIncludingDeleted(mapper: (
    id: Long,
    manga_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    note: String,
    image_url: String,
    percent: Double,
    created_at: Long,
    updated_at: Long,
    deleted_at: Long?,
  ) -> T): Query<T> = Query(410_169_034, arrayOf("bookmark"), driver, "Bookmark.sq",
      "selectAllIncludingDeleted",
      "SELECT bookmark.id, bookmark.manga_id, bookmark.chapter_id, bookmark.chapter_title, bookmark.page, bookmark.scroll, bookmark.note, bookmark.image_url, bookmark.percent, bookmark.created_at, bookmark.updated_at, bookmark.deleted_at FROM bookmark") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getDouble(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)
    )
  }

  public fun selectAllIncludingDeleted(): Query<Bookmark> = selectAllIncludingDeleted { id,
      manga_id, chapter_id, chapter_title, page, scroll, note, image_url, percent, created_at,
      updated_at, deleted_at ->
    Bookmark(
      id,
      manga_id,
      chapter_id,
      chapter_title,
      page,
      scroll,
      note,
      image_url,
      percent,
      created_at,
      updated_at,
      deleted_at
    )
  }

  public fun countAll(): Query<Long> = Query(1_277_870_401, arrayOf("bookmark"), driver,
      "Bookmark.sq", "countAll", "SELECT COUNT(*) FROM bookmark") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun insert(
    manga_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    note: String,
    image_url: String,
    percent: Double,
    created_at: Long,
    updated_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-1_052_466_776, """
        |INSERT OR REPLACE INTO bookmark (
        |    manga_id, chapter_id, chapter_title, page, scroll, note, image_url, percent, created_at, updated_at
        |) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 10) {
          bindString(0, manga_id)
          bindString(1, chapter_id)
          bindString(2, chapter_title)
          bindLong(3, page)
          bindDouble(4, scroll)
          bindString(5, note)
          bindString(6, image_url)
          bindDouble(7, percent)
          bindLong(8, created_at)
          bindLong(9, updated_at)
        }
    notifyQueries(-1_052_466_776) { emit ->
      emit("bookmark")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun softDelete(
    deleted_at: Long?,
    manga_id: String,
    chapter_id: String,
    page: Long,
  ): QueryResult<Long> {
    val result = driver.execute(140_307_940,
        """UPDATE bookmark SET deleted_at = ? WHERE manga_id = ? AND chapter_id = ? AND page = ?""",
        4) {
          bindLong(0, deleted_at)
          bindString(1, manga_id)
          bindString(2, chapter_id)
          bindLong(3, page)
        }
    notifyQueries(140_307_940) { emit ->
      emit("bookmark")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteById(id: Long): QueryResult<Long> {
    val result = driver.execute(-1_795_008_628, """DELETE FROM bookmark WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(-1_795_008_628) { emit ->
      emit("bookmark")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByPage(
    manga_id: String,
    chapter_id: String,
    page: Long,
  ): QueryResult<Long> {
    val result = driver.execute(1_573_770_432, """
        |DELETE FROM bookmark
        |WHERE manga_id = ? AND chapter_id = ? AND page = ?
        """.trimMargin(), 3) {
          bindString(0, manga_id)
          bindString(1, chapter_id)
          bindLong(2, page)
        }
    notifyQueries(1_573_770_432) { emit ->
      emit("bookmark")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(-750_641_497, """DELETE FROM bookmark""", 0)
    notifyQueries(-750_641_497) { emit ->
      emit("bookmark")
    }
    return result
  }

  private inner class SelectForChapterQuery<out T : Any>(
    public val manga_id: String,
    public val chapter_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("bookmark", "manga", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("bookmark", "manga", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_955_826_063, """
    |SELECT b.id, b.manga_id, b.chapter_id, b.chapter_title, b.page, b.note, b.created_at,
    |       m.title AS manga_title, m.cover_url AS manga_cover_url
    |FROM bookmark b
    |LEFT JOIN manga m ON m.id = b.manga_id
    |WHERE b.manga_id = ? AND b.chapter_id = ?
    |ORDER BY b.page
    """.trimMargin(), mapper, 2) {
      bindString(0, manga_id)
      bindString(1, chapter_id)
    }

    override fun toString(): String = "Bookmark.sq:selectForChapter"
  }

  private inner class ExistsForPageQuery<out T : Any>(
    public val manga_id: String,
    public val chapter_id: String,
    public val page: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("bookmark", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("bookmark", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-324_706_003, """
    |SELECT EXISTS(SELECT 1 FROM bookmark
    |              WHERE manga_id = ? AND chapter_id = ? AND page = ?)
    """.trimMargin(), mapper, 3) {
      bindString(0, manga_id)
      bindString(1, chapter_id)
      bindLong(2, page)
    }

    override fun toString(): String = "Bookmark.sq:existsForPage"
  }
}
