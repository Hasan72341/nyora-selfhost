package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import com.nyora.hasan72341.shared.db.mangaUpdate.SelectAll
import kotlin.Any
import kotlin.Long
import kotlin.String

public class MangaUpdateQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllWithNew(mapper: (
    manga_id: String,
    source_id: String,
    last_chapter_count: Long,
    new_chapters_count: Long,
    latest_chapter_title: String,
    last_synced_at: Long,
    manga_title: String,
    manga_cover_url: String,
  ) -> T): Query<T> = Query(-121_135_915, arrayOf("manga_update", "manga"), driver,
      "MangaUpdate.sq", "selectAllWithNew", """
  |SELECT u.manga_id, u.source_id, u.last_chapter_count, u.new_chapters_count,
  |       u.latest_chapter_title, u.last_synced_at,
  |       m.title AS manga_title, m.cover_url AS manga_cover_url
  |FROM manga_update u
  |JOIN manga m ON m.id = u.manga_id
  |WHERE u.new_chapters_count > 0
  |ORDER BY u.last_synced_at DESC
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!
    )
  }

  public fun selectAllWithNew(): Query<SelectAllWithNew> = selectAllWithNew { manga_id, source_id,
      last_chapter_count, new_chapters_count, latest_chapter_title, last_synced_at, manga_title,
      manga_cover_url ->
    SelectAllWithNew(
      manga_id,
      source_id,
      last_chapter_count,
      new_chapters_count,
      latest_chapter_title,
      last_synced_at,
      manga_title,
      manga_cover_url
    )
  }

  public fun <T : Any> selectAll(mapper: (
    manga_id: String,
    source_id: String,
    last_chapter_count: Long,
    new_chapters_count: Long,
    latest_chapter_title: String,
    last_synced_at: Long,
    manga_title: String,
    manga_cover_url: String,
  ) -> T): Query<T> = Query(477_688_421, arrayOf("manga_update", "manga"), driver, "MangaUpdate.sq",
      "selectAll", """
  |SELECT u.manga_id, u.source_id, u.last_chapter_count, u.new_chapters_count,
  |       u.latest_chapter_title, u.last_synced_at,
  |       m.title AS manga_title, m.cover_url AS manga_cover_url
  |FROM manga_update u
  |JOIN manga m ON m.id = u.manga_id
  |ORDER BY u.last_synced_at DESC
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!
    )
  }

  public fun selectAll(): Query<SelectAll> = selectAll { manga_id, source_id, last_chapter_count,
      new_chapters_count, latest_chapter_title, last_synced_at, manga_title, manga_cover_url ->
    SelectAll(
      manga_id,
      source_id,
      last_chapter_count,
      new_chapters_count,
      latest_chapter_title,
      last_synced_at,
      manga_title,
      manga_cover_url
    )
  }

  public fun <T : Any> selectByMangaId(manga_id: String, mapper: (
    manga_id: String,
    source_id: String,
    last_chapter_count: Long,
    new_chapters_count: Long,
    latest_chapter_title: String,
    last_synced_at: Long,
    updated_at: Long,
  ) -> T): Query<T> = SelectByMangaIdQuery(manga_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectByMangaId(manga_id: String): Query<Manga_update> = selectByMangaId(manga_id) {
      manga_id_, source_id, last_chapter_count, new_chapters_count, latest_chapter_title,
      last_synced_at, updated_at ->
    Manga_update(
      manga_id_,
      source_id,
      last_chapter_count,
      new_chapters_count,
      latest_chapter_title,
      last_synced_at,
      updated_at
    )
  }

  public fun <T : Any> selectAllIncludingDeleted(mapper: (
    manga_id: String,
    source_id: String,
    last_chapter_count: Long,
    new_chapters_count: Long,
    latest_chapter_title: String,
    last_synced_at: Long,
    updated_at: Long,
  ) -> T): Query<T> = Query(-1_701_936_775, arrayOf("manga_update"), driver, "MangaUpdate.sq",
      "selectAllIncludingDeleted",
      "SELECT manga_update.manga_id, manga_update.source_id, manga_update.last_chapter_count, manga_update.new_chapters_count, manga_update.latest_chapter_title, manga_update.last_synced_at, manga_update.updated_at FROM manga_update") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getString(4)!!,
      cursor.getLong(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectAllIncludingDeleted(): Query<Manga_update> = selectAllIncludingDeleted {
      manga_id, source_id, last_chapter_count, new_chapters_count, latest_chapter_title,
      last_synced_at, updated_at ->
    Manga_update(
      manga_id,
      source_id,
      last_chapter_count,
      new_chapters_count,
      latest_chapter_title,
      last_synced_at,
      updated_at
    )
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    manga_id: String,
    source_id: String,
    last_chapter_count: Long,
    new_chapters_count: Long,
    latest_chapter_title: String,
    last_synced_at: Long,
    updated_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(164_380_111, """
        |INSERT OR REPLACE INTO manga_update (
        |    manga_id, source_id, last_chapter_count, new_chapters_count, latest_chapter_title, last_synced_at, updated_at
        |) VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 7) {
          bindString(0, manga_id)
          bindString(1, source_id)
          bindLong(2, last_chapter_count)
          bindLong(3, new_chapters_count)
          bindString(4, latest_chapter_title)
          bindLong(5, last_synced_at)
          bindLong(6, updated_at)
        }
    notifyQueries(164_380_111) { emit ->
      emit("manga_update")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun markSeen(manga_id: String): QueryResult<Long> {
    val result = driver.execute(1_841_207_176,
        """UPDATE manga_update SET new_chapters_count = 0 WHERE manga_id = ?""", 1) {
          bindString(0, manga_id)
        }
    notifyQueries(1_841_207_176) { emit ->
      emit("manga_update")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun markAllSeen(): QueryResult<Long> {
    val result = driver.execute(1_786_610_575, """UPDATE manga_update SET new_chapters_count = 0""",
        0)
    notifyQueries(1_786_610_575) { emit ->
      emit("manga_update")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(1_834_954_582, """DELETE FROM manga_update""", 0)
    notifyQueries(1_834_954_582) { emit ->
      emit("manga_update")
    }
    return result
  }

  private inner class SelectByMangaIdQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga_update", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga_update", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-2_081_959_588,
        """SELECT manga_update.manga_id, manga_update.source_id, manga_update.last_chapter_count, manga_update.new_chapters_count, manga_update.latest_chapter_title, manga_update.last_synced_at, manga_update.updated_at FROM manga_update WHERE manga_id = ?""",
        mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "MangaUpdate.sq:selectByMangaId"
  }
}
