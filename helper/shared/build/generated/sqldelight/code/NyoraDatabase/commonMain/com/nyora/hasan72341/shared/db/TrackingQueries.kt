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

public class TrackingQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    tracker_id: String,
    manga_id: String,
    remote_id: String,
    source_id: String,
    title: String,
    status: String,
    score: Double,
    last_read_chapter: Double,
    last_read_volume: Long,
    total_chapters: Long,
    total_volumes: Long,
    chapter_offset: Long,
    started_at: String,
    finished_at: String,
    comment: String,
    updated_at: String,
    deleted_at: String,
  ) -> T): Query<T> = Query(880_320_981, arrayOf("tracking"), driver, "Tracking.sq", "selectAll",
      "SELECT tracking.tracker_id, tracking.manga_id, tracking.remote_id, tracking.source_id, tracking.title, tracking.status, tracking.score, tracking.last_read_chapter, tracking.last_read_volume, tracking.total_chapters, tracking.total_volumes, tracking.chapter_offset, tracking.started_at, tracking.finished_at, tracking.comment, tracking.updated_at, tracking.deleted_at FROM tracking") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getDouble(6)!!,
      cursor.getDouble(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getString(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!
    )
  }

  public fun selectAll(): Query<Tracking> = selectAll { tracker_id, manga_id, remote_id, source_id,
      title, status, score, last_read_chapter, last_read_volume, total_chapters, total_volumes,
      chapter_offset, started_at, finished_at, comment, updated_at, deleted_at ->
    Tracking(
      tracker_id,
      manga_id,
      remote_id,
      source_id,
      title,
      status,
      score,
      last_read_chapter,
      last_read_volume,
      total_chapters,
      total_volumes,
      chapter_offset,
      started_at,
      finished_at,
      comment,
      updated_at,
      deleted_at
    )
  }

  public fun <T : Any> selectLive(mapper: (
    tracker_id: String,
    manga_id: String,
    remote_id: String,
    source_id: String,
    title: String,
    status: String,
    score: Double,
    last_read_chapter: Double,
    last_read_volume: Long,
    total_chapters: Long,
    total_volumes: Long,
    chapter_offset: Long,
    started_at: String,
    finished_at: String,
    comment: String,
    updated_at: String,
    deleted_at: String,
  ) -> T): Query<T> = Query(1_520_471_864, arrayOf("tracking"), driver, "Tracking.sq", "selectLive",
      "SELECT tracking.tracker_id, tracking.manga_id, tracking.remote_id, tracking.source_id, tracking.title, tracking.status, tracking.score, tracking.last_read_chapter, tracking.last_read_volume, tracking.total_chapters, tracking.total_volumes, tracking.chapter_offset, tracking.started_at, tracking.finished_at, tracking.comment, tracking.updated_at, tracking.deleted_at FROM tracking WHERE deleted_at = ''") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getDouble(6)!!,
      cursor.getDouble(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getString(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!
    )
  }

  public fun selectLive(): Query<Tracking> = selectLive { tracker_id, manga_id, remote_id,
      source_id, title, status, score, last_read_chapter, last_read_volume, total_chapters,
      total_volumes, chapter_offset, started_at, finished_at, comment, updated_at, deleted_at ->
    Tracking(
      tracker_id,
      manga_id,
      remote_id,
      source_id,
      title,
      status,
      score,
      last_read_chapter,
      last_read_volume,
      total_chapters,
      total_volumes,
      chapter_offset,
      started_at,
      finished_at,
      comment,
      updated_at,
      deleted_at
    )
  }

  public fun <T : Any> selectByKey(
    tracker_id: String,
    manga_id: String,
    mapper: (
      tracker_id: String,
      manga_id: String,
      remote_id: String,
      source_id: String,
      title: String,
      status: String,
      score: Double,
      last_read_chapter: Double,
      last_read_volume: Long,
      total_chapters: Long,
      total_volumes: Long,
      chapter_offset: Long,
      started_at: String,
      finished_at: String,
      comment: String,
      updated_at: String,
      deleted_at: String,
    ) -> T,
  ): Query<T> = SelectByKeyQuery(tracker_id, manga_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getDouble(6)!!,
      cursor.getDouble(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getString(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!
    )
  }

  public fun selectByKey(tracker_id: String, manga_id: String): Query<Tracking> =
      selectByKey(tracker_id, manga_id) { tracker_id_, manga_id_, remote_id, source_id, title,
      status, score, last_read_chapter, last_read_volume, total_chapters, total_volumes,
      chapter_offset, started_at, finished_at, comment, updated_at, deleted_at ->
    Tracking(
      tracker_id_,
      manga_id_,
      remote_id,
      source_id,
      title,
      status,
      score,
      last_read_chapter,
      last_read_volume,
      total_chapters,
      total_volumes,
      chapter_offset,
      started_at,
      finished_at,
      comment,
      updated_at,
      deleted_at
    )
  }

  public fun <T : Any> selectForManga(manga_id: String, mapper: (
    tracker_id: String,
    manga_id: String,
    remote_id: String,
    source_id: String,
    title: String,
    status: String,
    score: Double,
    last_read_chapter: Double,
    last_read_volume: Long,
    total_chapters: Long,
    total_volumes: Long,
    chapter_offset: Long,
    started_at: String,
    finished_at: String,
    comment: String,
    updated_at: String,
    deleted_at: String,
  ) -> T): Query<T> = SelectForMangaQuery(manga_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getDouble(6)!!,
      cursor.getDouble(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getString(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!
    )
  }

  public fun selectForManga(manga_id: String): Query<Tracking> = selectForManga(manga_id) {
      tracker_id, manga_id_, remote_id, source_id, title, status, score, last_read_chapter,
      last_read_volume, total_chapters, total_volumes, chapter_offset, started_at, finished_at,
      comment, updated_at, deleted_at ->
    Tracking(
      tracker_id,
      manga_id_,
      remote_id,
      source_id,
      title,
      status,
      score,
      last_read_chapter,
      last_read_volume,
      total_chapters,
      total_volumes,
      chapter_offset,
      started_at,
      finished_at,
      comment,
      updated_at,
      deleted_at
    )
  }

  public fun countLive(): Query<Long> = Query(-347_136_661, arrayOf("tracking"), driver,
      "Tracking.sq", "countLive", "SELECT COUNT(*) FROM tracking WHERE deleted_at = ''") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    tracker_id: String,
    manga_id: String,
    remote_id: String,
    source_id: String,
    title: String,
    status: String,
    score: Double,
    last_read_chapter: Double,
    last_read_volume: Long,
    total_chapters: Long,
    total_volumes: Long,
    chapter_offset: Long,
    started_at: String,
    finished_at: String,
    comment: String,
    updated_at: String,
    deleted_at: String,
  ): QueryResult<Long> {
    val result = driver.execute(-1_287_109_537, """
        |INSERT OR REPLACE INTO tracking (
        |    tracker_id, manga_id, remote_id, source_id, title, status, score,
        |    last_read_chapter, last_read_volume, total_chapters, total_volumes,
        |    chapter_offset, started_at, finished_at, comment, updated_at, deleted_at
        |) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 17) {
          bindString(0, tracker_id)
          bindString(1, manga_id)
          bindString(2, remote_id)
          bindString(3, source_id)
          bindString(4, title)
          bindString(5, status)
          bindDouble(6, score)
          bindDouble(7, last_read_chapter)
          bindLong(8, last_read_volume)
          bindLong(9, total_chapters)
          bindLong(10, total_volumes)
          bindLong(11, chapter_offset)
          bindString(12, started_at)
          bindString(13, finished_at)
          bindString(14, comment)
          bindString(15, updated_at)
          bindString(16, deleted_at)
        }
    notifyQueries(-1_287_109_537) { emit ->
      emit("tracking")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun softDelete(
    deleted_at: String,
    updated_at: String,
    tracker_id: String,
    manga_id: String,
  ): QueryResult<Long> {
    val result = driver.execute(-1_713_884_763, """
        |UPDATE tracking SET deleted_at = ?, updated_at = ?
        |WHERE tracker_id = ? AND manga_id = ?
        """.trimMargin(), 4) {
          bindString(0, deleted_at)
          bindString(1, updated_at)
          bindString(2, tracker_id)
          bindString(3, manga_id)
        }
    notifyQueries(-1_713_884_763) { emit ->
      emit("tracking")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByKey(tracker_id: String, manga_id: String): QueryResult<Long> {
    val result = driver.execute(-1_456_089_491,
        """DELETE FROM tracking WHERE tracker_id = ? AND manga_id = ?""", 2) {
          bindString(0, tracker_id)
          bindString(1, manga_id)
        }
    notifyQueries(-1_456_089_491) { emit ->
      emit("tracking")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(-2_057_380_154, """DELETE FROM tracking""", 0)
    notifyQueries(-2_057_380_154) { emit ->
      emit("tracking")
    }
    return result
  }

  private inner class SelectByKeyQuery<out T : Any>(
    public val tracker_id: String,
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("tracking", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("tracking", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-118_812_228,
        """SELECT tracking.tracker_id, tracking.manga_id, tracking.remote_id, tracking.source_id, tracking.title, tracking.status, tracking.score, tracking.last_read_chapter, tracking.last_read_volume, tracking.total_chapters, tracking.total_volumes, tracking.chapter_offset, tracking.started_at, tracking.finished_at, tracking.comment, tracking.updated_at, tracking.deleted_at FROM tracking WHERE tracker_id = ? AND manga_id = ?""",
        mapper, 2) {
      bindString(0, tracker_id)
      bindString(1, manga_id)
    }

    override fun toString(): String = "Tracking.sq:selectByKey"
  }

  private inner class SelectForMangaQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("tracking", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("tracking", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_292_061_513,
        """SELECT tracking.tracker_id, tracking.manga_id, tracking.remote_id, tracking.source_id, tracking.title, tracking.status, tracking.score, tracking.last_read_chapter, tracking.last_read_volume, tracking.total_chapters, tracking.total_volumes, tracking.chapter_offset, tracking.started_at, tracking.finished_at, tracking.comment, tracking.updated_at, tracking.deleted_at FROM tracking WHERE manga_id = ? AND deleted_at = ''""",
        mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "Tracking.sq:selectForManga"
  }
}
