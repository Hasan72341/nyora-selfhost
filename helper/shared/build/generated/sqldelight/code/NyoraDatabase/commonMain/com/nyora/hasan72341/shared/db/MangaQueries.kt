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

public class MangaQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
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
  ) -> T): Query<T> = Query(-835_055_122, arrayOf("manga"), driver, "Manga.sq", "selectAll",
      "SELECT manga.id, manga.title, manga.alt_titles, manga.url, manga.public_url, manga.rating, manga.is_nsfw, manga.content_rating, manga.cover_url, manga.large_cover_url, manga.state, manga.authors, manga.source_ref, manga.description, manga.tags, manga.chapters, manga.unread, manga.progress, manga.updated_at FROM manga ORDER BY title") {
      cursor ->
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
      cursor.getLong(18)!!
    )
  }

  public fun selectAll(): Query<Manga> = selectAll { id, title, alt_titles, url, public_url, rating,
      is_nsfw, content_rating, cover_url, large_cover_url, state, authors, source_ref, description,
      tags, chapters, unread, progress, updated_at ->
    Manga(
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
      updated_at
    )
  }

  public fun <T : Any> selectById(id: String, mapper: (
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
  ) -> T): Query<T> = SelectByIdQuery(id) { cursor ->
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
      cursor.getLong(18)!!
    )
  }

  public fun selectById(id: String): Query<Manga> = selectById(id) { id_, title, alt_titles, url,
      public_url, rating, is_nsfw, content_rating, cover_url, large_cover_url, state, authors,
      source_ref, description, tags, chapters, unread, progress, updated_at ->
    Manga(
      id_,
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
      updated_at
    )
  }

  public fun countAll(): Query<Long> = Query(-2_144_753_143, arrayOf("manga"), driver, "Manga.sq",
      "countAll", "SELECT COUNT(*) FROM manga") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
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
  ): QueryResult<Long> {
    val result = driver.execute(-326_274_330, """
        |INSERT OR REPLACE INTO manga (
        |    id, title, alt_titles, url, public_url, rating, is_nsfw, content_rating,
        |    cover_url, large_cover_url, state, authors, source_ref, description, tags,
        |    chapters, unread, progress, updated_at
        |) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 19) {
          bindString(0, id)
          bindString(1, title)
          bindString(2, alt_titles)
          bindString(3, url)
          bindString(4, public_url)
          bindDouble(5, rating)
          bindLong(6, is_nsfw)
          bindString(7, content_rating)
          bindString(8, cover_url)
          bindString(9, large_cover_url)
          bindString(10, state)
          bindString(11, authors)
          bindString(12, source_ref)
          bindString(13, description)
          bindString(14, tags)
          bindString(15, chapters)
          bindLong(16, unread)
          bindDouble(17, progress)
          bindLong(18, updated_at)
        }
    notifyQueries(-326_274_330) { emit ->
      emit("manga")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteById(id: String): QueryResult<Long> {
    val result = driver.execute(-991_285_676, """DELETE FROM manga WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(-991_285_676) { emit ->
      emit("bookmark")
      emit("manga")
      emit("manga_favourite")
      emit("manga_favourite_category")
      emit("manga_history")
      emit("manga_prefs")
      emit("manga_update")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(522_211_039, """DELETE FROM manga""", 0)
    notifyQueries(522_211_039) { emit ->
      emit("bookmark")
      emit("manga")
      emit("manga_favourite")
      emit("manga_favourite_category")
      emit("manga_history")
      emit("manga_prefs")
      emit("manga_update")
    }
    return result
  }

  private inner class SelectByIdQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-116_863_707,
        """SELECT manga.id, manga.title, manga.alt_titles, manga.url, manga.public_url, manga.rating, manga.is_nsfw, manga.content_rating, manga.cover_url, manga.large_cover_url, manga.state, manga.authors, manga.source_ref, manga.description, manga.tags, manga.chapters, manga.unread, manga.progress, manga.updated_at FROM manga WHERE id = ?""",
        mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "Manga.sq:selectById"
  }
}
