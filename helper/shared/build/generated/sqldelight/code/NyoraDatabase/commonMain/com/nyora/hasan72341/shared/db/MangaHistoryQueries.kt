package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Double
import kotlin.Long
import kotlin.String

public class MangaHistoryQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectRecent(limit: Long, mapper: (
    id: String,
    title: String,
    alt_titles: String,
    url: String,
    public_url: String,
    rating: Double,
    is_nsfw: Long,
    content_rating: String?,
    cover_url: String,
    large_cover_url: String?,
    state: String?,
    authors: String,
    source_ref: String,
    description: String,
    tags: String,
    chapters: String,
    unread: Long,
    progress: Double,
    updated_at: Long,
    source_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    percent: Double,
    chapters_count: Long,
    updated_at_: Long,
    deleted_at: Long?,
  ) -> T): Query<T> = SelectRecentQuery(limit) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getDouble(5)!!,
      cursor.getLong(6)!!,
      cursor.getString(7),
      cursor.getString(8)!!,
      cursor.getString(9),
      cursor.getString(10),
      cursor.getString(11)!!,
      cursor.getString(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getLong(16)!!,
      cursor.getDouble(17)!!,
      cursor.getLong(18)!!,
      cursor.getString(19)!!,
      cursor.getString(20)!!,
      cursor.getString(21)!!,
      cursor.getLong(22)!!,
      cursor.getDouble(23)!!,
      cursor.getDouble(24)!!,
      cursor.getLong(25)!!,
      cursor.getLong(26)!!,
      cursor.getLong(27)
    )
  }

  public fun selectRecent(limit: Long): Query<SelectRecent> = selectRecent(limit) { id, title,
      alt_titles, url, public_url, rating, is_nsfw, content_rating, cover_url, large_cover_url,
      state, authors, source_ref, description, tags, chapters, unread, progress, updated_at,
      source_id, chapter_id, chapter_title, page, scroll, percent, chapters_count, updated_at_,
      deleted_at ->
    SelectRecent(
      id,
      title,
      alt_titles,
      url,
      public_url,
      rating,
      is_nsfw,
      content_rating,
      cover_url,
      large_cover_url,
      state,
      authors,
      source_ref,
      description,
      tags,
      chapters,
      unread,
      progress,
      updated_at,
      source_id,
      chapter_id,
      chapter_title,
      page,
      scroll,
      percent,
      chapters_count,
      updated_at_,
      deleted_at
    )
  }

  public fun <T : Any> selectByMangaId(manga_id: String, mapper: (
    manga_id: String,
    source_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    percent: Double,
    chapters_count: Long,
    updated_at: Long,
    deleted_at: Long?,
  ) -> T): Query<T> = SelectByMangaIdQuery(manga_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getDouble(6)!!,
      cursor.getLong(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)
    )
  }

  public fun selectByMangaId(manga_id: String): Query<Manga_history> = selectByMangaId(manga_id) {
      manga_id_, source_id, chapter_id, chapter_title, page, scroll, percent, chapters_count,
      updated_at, deleted_at ->
    Manga_history(
      manga_id_,
      source_id,
      chapter_id,
      chapter_title,
      page,
      scroll,
      percent,
      chapters_count,
      updated_at,
      deleted_at
    )
  }

  public fun <T : Any> selectAllIncludingDeleted(mapper: (
    manga_id: String,
    source_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    percent: Double,
    chapters_count: Long,
    updated_at: Long,
    deleted_at: Long?,
  ) -> T): Query<T> = Query(-1_988_022_400, arrayOf("manga_history"), driver, "MangaHistory.sq",
      "selectAllIncludingDeleted",
      "SELECT manga_history.manga_id, manga_history.source_id, manga_history.chapter_id, manga_history.chapter_title, manga_history.page, manga_history.scroll, manga_history.percent, manga_history.chapters_count, manga_history.updated_at, manga_history.deleted_at FROM manga_history") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getDouble(6)!!,
      cursor.getLong(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)
    )
  }

  public fun selectAllIncludingDeleted(): Query<Manga_history> = selectAllIncludingDeleted {
      manga_id, source_id, chapter_id, chapter_title, page, scroll, percent, chapters_count,
      updated_at, deleted_at ->
    Manga_history(
      manga_id,
      source_id,
      chapter_id,
      chapter_title,
      page,
      scroll,
      percent,
      chapters_count,
      updated_at,
      deleted_at
    )
  }

  public fun countAll(): Query<Long> = Query(-733_698_613, arrayOf("manga_history"), driver,
      "MangaHistory.sq", "countAll", "SELECT COUNT(*) FROM manga_history") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    manga_id: String,
    source_id: String,
    chapter_id: String,
    chapter_title: String,
    page: Long,
    scroll: Double,
    percent: Double,
    chapters_count: Long,
    updated_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-950_503_640, """
        |INSERT OR REPLACE INTO manga_history (manga_id, source_id, chapter_id, chapter_title, page, scroll, percent, chapters_count, updated_at)
        |VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 9) {
          bindString(0, manga_id)
          bindString(1, source_id)
          bindString(2, chapter_id)
          bindString(3, chapter_title)
          bindLong(4, page)
          bindDouble(5, scroll)
          bindDouble(6, percent)
          bindLong(7, chapters_count)
          bindLong(8, updated_at)
        }
    notifyQueries(-950_503_640) { emit ->
      emit("manga_history")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun softDelete(deleted_at: Long?, manga_id: String): QueryResult<Long> {
    val result = driver.execute(-242_231_314,
        """UPDATE manga_history SET deleted_at = ? WHERE manga_id = ?""", 2) {
          bindLong(0, deleted_at)
          bindString(1, manga_id)
        }
    notifyQueries(-242_231_314) { emit ->
      emit("manga_history")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByMangaId(manga_id: String): QueryResult<Long> {
    val result = driver.execute(-627_371_948, """DELETE FROM manga_history WHERE manga_id = ?""", 1)
        {
          bindString(0, manga_id)
        }
    notifyQueries(-627_371_948) { emit ->
      emit("manga_history")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(1_315_228_509, """DELETE FROM manga_history""", 0)
    notifyQueries(1_315_228_509) { emit ->
      emit("manga_history")
    }
    return result
  }

  private inner class SelectRecentQuery<out T : Any>(
    public val limit: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga", "manga_history", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga", "manga_history", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-2_028_144_208, """
    |SELECT m.id, m.title, m.alt_titles, m.url, m.public_url, m.rating, m.is_nsfw, m.content_rating, m.cover_url, m.large_cover_url, m.state, m.authors, m.source_ref, m.description, m.tags, m.chapters, m.unread, m.progress, m.updated_at, h.source_id, h.chapter_id, h.chapter_title, h.page, h.scroll, h.percent, h.chapters_count, h.updated_at, h.deleted_at
    |FROM manga_history h
    |JOIN manga m ON m.id = h.manga_id
    |WHERE h.deleted_at IS NULL
    |ORDER BY h.updated_at DESC
    |LIMIT ?
    """.trimMargin(), mapper, 1) {
      bindLong(0, limit)
    }

    override fun toString(): String = "MangaHistory.sq:selectRecent"
  }

  private inner class SelectByMangaIdQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga_history", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga_history", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_953_231_837,
        """SELECT manga_history.manga_id, manga_history.source_id, manga_history.chapter_id, manga_history.chapter_title, manga_history.page, manga_history.scroll, manga_history.percent, manga_history.chapters_count, manga_history.updated_at, manga_history.deleted_at FROM manga_history WHERE manga_id = ?""",
        mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "MangaHistory.sq:selectByMangaId"
  }
}
