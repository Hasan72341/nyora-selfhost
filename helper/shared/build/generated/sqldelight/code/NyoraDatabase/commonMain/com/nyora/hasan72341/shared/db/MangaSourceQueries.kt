package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class MangaSourceQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: String,
    name: String,
    lang: String,
    base_url: String,
    package_name: String,
    source_code_url: String,
    icon_url: String,
    version: String,
    version_code: Long,
    is_installed: Long,
    is_pinned: Long,
    is_nsfw: Long,
    is_obsolete: Long,
    engine: String,
    content_type: String,
    notes: String,
    local_path: String,
    installed_at: Long,
    can_uninstall: Long,
  ) -> T): Query<T> = Query(452_442_195, arrayOf("manga_source"), driver, "MangaSource.sq",
      "selectAll",
      "SELECT manga_source.id, manga_source.name, manga_source.lang, manga_source.base_url, manga_source.package_name, manga_source.source_code_url, manga_source.icon_url, manga_source.version, manga_source.version_code, manga_source.is_installed, manga_source.is_pinned, manga_source.is_nsfw, manga_source.is_obsolete, manga_source.engine, manga_source.content_type, manga_source.notes, manga_source.local_path, manga_source.installed_at, manga_source.can_uninstall FROM manga_source ORDER BY lang, LOWER(name)") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getLong(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!,
      cursor.getLong(17)!!,
      cursor.getLong(18)!!
    )
  }

  public fun selectAll(): Query<Manga_source> = selectAll { id, name, lang, base_url, package_name,
      source_code_url, icon_url, version, version_code, is_installed, is_pinned, is_nsfw,
      is_obsolete, engine, content_type, notes, local_path, installed_at, can_uninstall ->
    Manga_source(
      id,
      name,
      lang,
      base_url,
      package_name,
      source_code_url,
      icon_url,
      version,
      version_code,
      is_installed,
      is_pinned,
      is_nsfw,
      is_obsolete,
      engine,
      content_type,
      notes,
      local_path,
      installed_at,
      can_uninstall
    )
  }

  public fun <T : Any> selectById(id: String, mapper: (
    id: String,
    name: String,
    lang: String,
    base_url: String,
    package_name: String,
    source_code_url: String,
    icon_url: String,
    version: String,
    version_code: Long,
    is_installed: Long,
    is_pinned: Long,
    is_nsfw: Long,
    is_obsolete: Long,
    engine: String,
    content_type: String,
    notes: String,
    local_path: String,
    installed_at: Long,
    can_uninstall: Long,
  ) -> T): Query<T> = SelectByIdQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getLong(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!,
      cursor.getLong(17)!!,
      cursor.getLong(18)!!
    )
  }

  public fun selectById(id: String): Query<Manga_source> = selectById(id) { id_, name, lang,
      base_url, package_name, source_code_url, icon_url, version, version_code, is_installed,
      is_pinned, is_nsfw, is_obsolete, engine, content_type, notes, local_path, installed_at,
      can_uninstall ->
    Manga_source(
      id_,
      name,
      lang,
      base_url,
      package_name,
      source_code_url,
      icon_url,
      version,
      version_code,
      is_installed,
      is_pinned,
      is_nsfw,
      is_obsolete,
      engine,
      content_type,
      notes,
      local_path,
      installed_at,
      can_uninstall
    )
  }

  public fun <T : Any> selectInstalled(mapper: (
    id: String,
    name: String,
    lang: String,
    base_url: String,
    package_name: String,
    source_code_url: String,
    icon_url: String,
    version: String,
    version_code: Long,
    is_installed: Long,
    is_pinned: Long,
    is_nsfw: Long,
    is_obsolete: Long,
    engine: String,
    content_type: String,
    notes: String,
    local_path: String,
    installed_at: Long,
    can_uninstall: Long,
  ) -> T): Query<T> = Query(379_594_924, arrayOf("manga_source"), driver, "MangaSource.sq",
      "selectInstalled",
      "SELECT manga_source.id, manga_source.name, manga_source.lang, manga_source.base_url, manga_source.package_name, manga_source.source_code_url, manga_source.icon_url, manga_source.version, manga_source.version_code, manga_source.is_installed, manga_source.is_pinned, manga_source.is_nsfw, manga_source.is_obsolete, manga_source.engine, manga_source.content_type, manga_source.notes, manga_source.local_path, manga_source.installed_at, manga_source.can_uninstall FROM manga_source WHERE is_installed = 1 ORDER BY is_pinned DESC, LOWER(name)") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!,
      cursor.getLong(9)!!,
      cursor.getLong(10)!!,
      cursor.getLong(11)!!,
      cursor.getLong(12)!!,
      cursor.getString(13)!!,
      cursor.getString(14)!!,
      cursor.getString(15)!!,
      cursor.getString(16)!!,
      cursor.getLong(17)!!,
      cursor.getLong(18)!!
    )
  }

  public fun selectInstalled(): Query<Manga_source> = selectInstalled { id, name, lang, base_url,
      package_name, source_code_url, icon_url, version, version_code, is_installed, is_pinned,
      is_nsfw, is_obsolete, engine, content_type, notes, local_path, installed_at, can_uninstall ->
    Manga_source(
      id,
      name,
      lang,
      base_url,
      package_name,
      source_code_url,
      icon_url,
      version,
      version_code,
      is_installed,
      is_pinned,
      is_nsfw,
      is_obsolete,
      engine,
      content_type,
      notes,
      local_path,
      installed_at,
      can_uninstall
    )
  }

  public fun countAll(): Query<Long> = Query(1_637_556_996, arrayOf("manga_source"), driver,
      "MangaSource.sq", "countAll", "SELECT COUNT(*) FROM manga_source") { cursor ->
    cursor.getLong(0)!!
  }

  /**
   * @return The number of rows updated.
   */
  public fun upsert(
    id: String,
    name: String,
    lang: String,
    base_url: String,
    package_name: String,
    source_code_url: String,
    icon_url: String,
    version: String,
    version_code: Long,
    is_installed: Long,
    is_pinned: Long,
    is_nsfw: Long,
    is_obsolete: Long,
    engine: String,
    content_type: String,
    notes: String,
    local_path: String,
    installed_at: Long,
    can_uninstall: Long,
  ): QueryResult<Long> {
    val result = driver.execute(1_081_011_873, """
        |INSERT OR REPLACE INTO manga_source (
        |    id, name, lang, base_url, package_name, source_code_url, icon_url, version,
        |    version_code, is_installed, is_pinned, is_nsfw, is_obsolete, engine,
        |    content_type, notes, local_path, installed_at, can_uninstall
        |) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 19) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, lang)
          bindString(3, base_url)
          bindString(4, package_name)
          bindString(5, source_code_url)
          bindString(6, icon_url)
          bindString(7, version)
          bindLong(8, version_code)
          bindLong(9, is_installed)
          bindLong(10, is_pinned)
          bindLong(11, is_nsfw)
          bindLong(12, is_obsolete)
          bindString(13, engine)
          bindString(14, content_type)
          bindString(15, notes)
          bindString(16, local_path)
          bindLong(17, installed_at)
          bindLong(18, can_uninstall)
        }
    notifyQueries(1_081_011_873) { emit ->
      emit("manga_source")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun togglePin(id: String): QueryResult<Long> {
    val result = driver.execute(-1_184_191_761,
        """UPDATE manga_source SET is_pinned = CASE is_pinned WHEN 0 THEN 1 ELSE 0 END WHERE id = ?""",
        1) {
          bindString(0, id)
        }
    notifyQueries(-1_184_191_761) { emit ->
      emit("manga_source")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteById(id: String): QueryResult<Long> {
    val result = driver.execute(266_425_487, """DELETE FROM manga_source WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(266_425_487) { emit ->
      emit("manga_source")
    }
    return result
  }

  private inner class SelectByIdQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga_source", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga_source", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_140_847_456,
        """SELECT manga_source.id, manga_source.name, manga_source.lang, manga_source.base_url, manga_source.package_name, manga_source.source_code_url, manga_source.icon_url, manga_source.version, manga_source.version_code, manga_source.is_installed, manga_source.is_pinned, manga_source.is_nsfw, manga_source.is_obsolete, manga_source.engine, manga_source.content_type, manga_source.notes, manga_source.local_path, manga_source.installed_at, manga_source.can_uninstall FROM manga_source WHERE id = ?""",
        mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "MangaSource.sq:selectById"
  }
}
