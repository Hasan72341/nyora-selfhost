package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import com.nyora.hasan72341.shared.db.mangaFavourite.SelectAll
import kotlin.Any
import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.String

public class MangaFavouriteQueries(
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
    added_at: Long,
    sort_key: Long,
  ) -> T): Query<T> = Query(2_103_633_463, arrayOf("manga", "manga_favourite"), driver,
      "MangaFavourite.sq", "selectAll", """
  |SELECT m.id, m.title, m.alt_titles, m.url, m.public_url, m.rating, m.is_nsfw, m.content_rating, m.cover_url, m.large_cover_url, m.state, m.authors, m.source_ref, m.description, m.tags, m.chapters, m.unread, m.progress, m.updated_at, f.added_at, f.sort_key
  |FROM manga_favourite f
  |JOIN manga m ON m.id = f.manga_id
  |WHERE f.deleted_at IS NULL
  |ORDER BY f.added_at DESC
  """.trimMargin()) { cursor ->
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
      cursor.getLong(19)!!,
      cursor.getLong(20)!!
    )
  }

  public fun selectAll(): Query<SelectAll> = selectAll { id, title, alt_titles, url, public_url,
      rating, is_nsfw, content_rating, cover_url, large_cover_url, state, authors, source_ref,
      description, tags, chapters, unread, progress, updated_at, added_at, sort_key ->
    SelectAll(
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
      added_at,
      sort_key
    )
  }

  public fun isFavourited(manga_id: String): Query<Boolean> = IsFavouritedQuery(manga_id) {
      cursor ->
    cursor.getBoolean(0)!!
  }

  public fun <T : Any> selectAllIncludingDeleted(mapper: (
    manga_id: String,
    added_at: Long,
    sort_key: Long,
    deleted_at: Long?,
    updated_at: Long,
  ) -> T): Query<T> = Query(1_921_683_787, arrayOf("manga_favourite"), driver, "MangaFavourite.sq",
      "selectAllIncludingDeleted",
      "SELECT manga_favourite.manga_id, manga_favourite.added_at, manga_favourite.sort_key, manga_favourite.deleted_at, manga_favourite.updated_at FROM manga_favourite") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3),
      cursor.getLong(4)!!
    )
  }

  public fun selectAllIncludingDeleted(): Query<Manga_favourite> = selectAllIncludingDeleted {
      manga_id, added_at, sort_key, deleted_at, updated_at ->
    Manga_favourite(
      manga_id,
      added_at,
      sort_key,
      deleted_at,
      updated_at
    )
  }

  public fun countAll(): Query<Long> = Query(-941_578_080, arrayOf("manga_favourite"), driver,
      "MangaFavourite.sq", "countAll", "SELECT COUNT(*) FROM manga_favourite") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun insert(
    manga_id: String,
    added_at: Long,
    sort_key: Long,
    updated_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-2_118_462_265,
        """INSERT OR REPLACE INTO manga_favourite (manga_id, added_at, sort_key, updated_at) VALUES (?, ?, ?, ?)""",
        4) {
          bindString(0, manga_id)
          bindLong(1, added_at)
          bindLong(2, sort_key)
          bindLong(3, updated_at)
        }
    notifyQueries(-2_118_462_265) { emit ->
      emit("manga_favourite")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun softDelete(deleted_at: Long?, manga_id: String): QueryResult<Long> {
    val result = driver.execute(1_849_063_811,
        """UPDATE manga_favourite SET deleted_at = ? WHERE manga_id = ?""", 2) {
          bindLong(0, deleted_at)
          bindString(1, manga_id)
        }
    notifyQueries(1_849_063_811) { emit ->
      emit("manga_favourite")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByMangaId(manga_id: String): QueryResult<Long> {
    val result = driver.execute(271_786_975, """DELETE FROM manga_favourite WHERE manga_id = ?""",
        1) {
          bindString(0, manga_id)
        }
    notifyQueries(271_786_975) { emit ->
      emit("manga_favourite")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(-834_067_672, """DELETE FROM manga_favourite""", 0)
    notifyQueries(-834_067_672) { emit ->
      emit("manga_favourite")
    }
    return result
  }

  private inner class IsFavouritedQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga_favourite", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga_favourite", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_578_811_085,
        """SELECT EXISTS(SELECT 1 FROM manga_favourite WHERE manga_id = ?)""", mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "MangaFavourite.sq:isFavourited"
  }
}
