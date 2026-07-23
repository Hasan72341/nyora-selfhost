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

public class MangaPrefsQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectByMangaId(manga_id: String, mapper: (
    manga_id: String,
    reader_mode: String,
    brightness: Double,
    contrast: Double,
    saturation: Double,
    hue: Double,
    palette: String,
    updated_at: Long,
  ) -> T): Query<T> = SelectByMangaIdQuery(manga_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getDouble(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getLong(7)!!
    )
  }

  public fun selectByMangaId(manga_id: String): Query<Manga_prefs> = selectByMangaId(manga_id) {
      manga_id_, reader_mode, brightness, contrast, saturation, hue, palette, updated_at ->
    Manga_prefs(
      manga_id_,
      reader_mode,
      brightness,
      contrast,
      saturation,
      hue,
      palette,
      updated_at
    )
  }

  public fun <T : Any> selectAll(mapper: (
    manga_id: String,
    reader_mode: String,
    brightness: Double,
    contrast: Double,
    saturation: Double,
    hue: Double,
    palette: String,
    updated_at: Long,
  ) -> T): Query<T> = Query(468_132_880, arrayOf("manga_prefs"), driver, "MangaPrefs.sq",
      "selectAll",
      "SELECT manga_prefs.manga_id, manga_prefs.reader_mode, manga_prefs.brightness, manga_prefs.contrast, manga_prefs.saturation, manga_prefs.hue, manga_prefs.palette, manga_prefs.updated_at FROM manga_prefs") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getDouble(2)!!,
      cursor.getDouble(3)!!,
      cursor.getDouble(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getLong(7)!!
    )
  }

  public fun selectAll(): Query<Manga_prefs> = selectAll { manga_id, reader_mode, brightness,
      contrast, saturation, hue, palette, updated_at ->
    Manga_prefs(
      manga_id,
      reader_mode,
      brightness,
      contrast,
      saturation,
      hue,
      palette,
      updated_at
    )
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    manga_id: String,
    reader_mode: String,
    brightness: Double,
    contrast: Double,
    saturation: Double,
    hue: Double,
    palette: String,
    updated_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(1_438_409_732, """
        |INSERT OR REPLACE INTO manga_prefs (
        |    manga_id, reader_mode, brightness, contrast, saturation, hue, palette, updated_at
        |) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 8) {
          bindString(0, manga_id)
          bindString(1, reader_mode)
          bindDouble(2, brightness)
          bindDouble(3, contrast)
          bindDouble(4, saturation)
          bindDouble(5, hue)
          bindString(6, palette)
          bindLong(7, updated_at)
        }
    notifyQueries(1_438_409_732) { emit ->
      emit("manga_prefs")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteByMangaId(manga_id: String): QueryResult<Long> {
    val result = driver.execute(1_862_130_424, """DELETE FROM manga_prefs WHERE manga_id = ?""", 1)
        {
          bindString(0, manga_id)
        }
    notifyQueries(1_862_130_424) { emit ->
      emit("manga_prefs")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAll(): QueryResult<Long> {
    val result = driver.execute(1_825_399_041, """DELETE FROM manga_prefs""", 0)
    notifyQueries(1_825_399_041) { emit ->
      emit("manga_prefs")
    }
    return result
  }

  private inner class SelectByMangaIdQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga_prefs", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga_prefs", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(536_270_535,
        """SELECT manga_prefs.manga_id, manga_prefs.reader_mode, manga_prefs.brightness, manga_prefs.contrast, manga_prefs.saturation, manga_prefs.hue, manga_prefs.palette, manga_prefs.updated_at FROM manga_prefs WHERE manga_id = ?""",
        mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "MangaPrefs.sq:selectByMangaId"
  }
}
