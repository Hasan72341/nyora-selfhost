package com.nyora.hasan72341.shared.db.shared

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.nyora.hasan72341.shared.db.BookmarkQueries
import com.nyora.hasan72341.shared.db.ChapterPagesQueries
import com.nyora.hasan72341.shared.db.FavouriteCategoryQueries
import com.nyora.hasan72341.shared.db.MangaFavouriteQueries
import com.nyora.hasan72341.shared.db.MangaHistoryQueries
import com.nyora.hasan72341.shared.db.MangaPrefsQueries
import com.nyora.hasan72341.shared.db.MangaQueries
import com.nyora.hasan72341.shared.db.MangaSourceQueries
import com.nyora.hasan72341.shared.db.MangaUpdateQueries
import com.nyora.hasan72341.shared.db.NyoraDatabase
import com.nyora.hasan72341.shared.db.TrackingQueries
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<NyoraDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = NyoraDatabaseImpl.Schema

internal fun KClass<NyoraDatabase>.newInstance(driver: SqlDriver): NyoraDatabase =
    NyoraDatabaseImpl(driver)

private class NyoraDatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver),
    NyoraDatabase {
  override val bookmarkQueries: BookmarkQueries = BookmarkQueries(driver)

  override val chapterPagesQueries: ChapterPagesQueries = ChapterPagesQueries(driver)

  override val favouriteCategoryQueries: FavouriteCategoryQueries = FavouriteCategoryQueries(driver)

  override val mangaQueries: MangaQueries = MangaQueries(driver)

  override val mangaFavouriteQueries: MangaFavouriteQueries = MangaFavouriteQueries(driver)

  override val mangaHistoryQueries: MangaHistoryQueries = MangaHistoryQueries(driver)

  override val mangaPrefsQueries: MangaPrefsQueries = MangaPrefsQueries(driver)

  override val mangaSourceQueries: MangaSourceQueries = MangaSourceQueries(driver)

  override val mangaUpdateQueries: MangaUpdateQueries = MangaUpdateQueries(driver)

  override val trackingQueries: TrackingQueries = TrackingQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS bookmark (
          |    id INTEGER PRIMARY KEY AUTOINCREMENT,
          |    manga_id TEXT NOT NULL,
          |    chapter_id TEXT NOT NULL,
          |    chapter_title TEXT NOT NULL DEFAULT '',
          |    page INTEGER NOT NULL DEFAULT 0,
          |    scroll REAL NOT NULL DEFAULT 0.0,
          |    note TEXT NOT NULL DEFAULT '',
          |    image_url TEXT NOT NULL DEFAULT '',
          |    percent REAL NOT NULL DEFAULT 0.0,
          |    created_at INTEGER NOT NULL DEFAULT 0,
          |    updated_at INTEGER NOT NULL DEFAULT 0,
          |    deleted_at INTEGER,
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS chapter_pages (
          |    chapter_url TEXT NOT NULL PRIMARY KEY,
          |    manga_id TEXT NOT NULL DEFAULT '',
          |    pages_json TEXT NOT NULL DEFAULT '[]',
          |    fetched_at INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS favourite_category (
          |    id INTEGER PRIMARY KEY AUTOINCREMENT,
          |    title TEXT NOT NULL,
          |    sort_key INTEGER NOT NULL DEFAULT 0,
          |    created_at INTEGER NOT NULL DEFAULT 0,
          |    deleted_at INTEGER
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_favourite_category (
          |    manga_id TEXT NOT NULL,
          |    category_id INTEGER NOT NULL,
          |    PRIMARY KEY (manga_id, category_id),
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE,
          |    FOREIGN KEY (category_id) REFERENCES favourite_category(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga (
          |    id TEXT NOT NULL PRIMARY KEY,
          |    title TEXT NOT NULL,
          |    alt_titles TEXT NOT NULL DEFAULT '[]',         -- JSON array
          |    url TEXT NOT NULL DEFAULT '',
          |    public_url TEXT NOT NULL DEFAULT '',
          |    rating REAL NOT NULL DEFAULT -1.0,
          |    is_nsfw INTEGER NOT NULL DEFAULT 0,
          |    content_rating TEXT,
          |    cover_url TEXT NOT NULL DEFAULT '',
          |    large_cover_url TEXT,
          |    state TEXT,
          |    authors TEXT NOT NULL DEFAULT '[]',            -- JSON array
          |    source_ref TEXT NOT NULL DEFAULT '{}',         -- JSON of MangaSourceRef
          |    description TEXT NOT NULL DEFAULT '',
          |    tags TEXT NOT NULL DEFAULT '[]',               -- JSON array of {key,title}
          |    chapters TEXT NOT NULL DEFAULT '[]',           -- JSON array of MangaChapter
          |    unread INTEGER NOT NULL DEFAULT 0,
          |    progress REAL NOT NULL DEFAULT 0.0,
          |    updated_at INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_favourite (
          |    manga_id TEXT NOT NULL PRIMARY KEY,
          |    added_at INTEGER NOT NULL DEFAULT 0,
          |    sort_key INTEGER NOT NULL DEFAULT 0,
          |    deleted_at INTEGER,
          |    updated_at INTEGER NOT NULL DEFAULT 0,
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_history (
          |    manga_id TEXT NOT NULL PRIMARY KEY,
          |    source_id TEXT NOT NULL DEFAULT '',
          |    chapter_id TEXT NOT NULL DEFAULT '',
          |    chapter_title TEXT NOT NULL DEFAULT '',
          |    page INTEGER NOT NULL DEFAULT 0,
          |    scroll REAL NOT NULL DEFAULT 0.0,
          |    percent REAL NOT NULL DEFAULT 0.0,
          |    chapters_count INTEGER NOT NULL DEFAULT 0,
          |    updated_at INTEGER NOT NULL DEFAULT 0,
          |    deleted_at INTEGER,
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_prefs (
          |    manga_id TEXT NOT NULL PRIMARY KEY,
          |    reader_mode TEXT NOT NULL DEFAULT '',
          |    brightness REAL NOT NULL DEFAULT 0.0,
          |    contrast REAL NOT NULL DEFAULT 1.0,
          |    saturation REAL NOT NULL DEFAULT 1.0,
          |    hue REAL NOT NULL DEFAULT 0.0,
          |    palette TEXT NOT NULL DEFAULT '',
          |    updated_at INTEGER NOT NULL DEFAULT 0,
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_source (
          |    id TEXT NOT NULL PRIMARY KEY,
          |    name TEXT NOT NULL,
          |    lang TEXT NOT NULL DEFAULT 'all',
          |    base_url TEXT NOT NULL DEFAULT '',
          |    package_name TEXT NOT NULL DEFAULT '',
          |    source_code_url TEXT NOT NULL DEFAULT '',
          |    icon_url TEXT NOT NULL DEFAULT '',
          |    version TEXT NOT NULL DEFAULT '0.0.1',
          |    version_code INTEGER NOT NULL DEFAULT 0,
          |    is_installed INTEGER NOT NULL DEFAULT 0,
          |    is_pinned INTEGER NOT NULL DEFAULT 0,
          |    is_nsfw INTEGER NOT NULL DEFAULT 0,
          |    is_obsolete INTEGER NOT NULL DEFAULT 0,
          |    engine TEXT NOT NULL DEFAULT 'Mihon',
          |    content_type TEXT NOT NULL DEFAULT 'Manga',
          |    notes TEXT NOT NULL DEFAULT '',
          |    local_path TEXT NOT NULL DEFAULT '',
          |    installed_at INTEGER NOT NULL DEFAULT 0,
          |    can_uninstall INTEGER NOT NULL DEFAULT 1
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS manga_update (
          |    manga_id TEXT NOT NULL PRIMARY KEY,
          |    source_id TEXT NOT NULL DEFAULT '',
          |    last_chapter_count INTEGER NOT NULL DEFAULT 0,
          |    new_chapters_count INTEGER NOT NULL DEFAULT 0,
          |    latest_chapter_title TEXT NOT NULL DEFAULT '',
          |    last_synced_at INTEGER NOT NULL DEFAULT 0,
          |    updated_at INTEGER NOT NULL DEFAULT 0,
          |    FOREIGN KEY (manga_id) REFERENCES manga(id) ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS tracking (
          |    tracker_id TEXT NOT NULL,
          |    manga_id TEXT NOT NULL,
          |    remote_id TEXT NOT NULL DEFAULT '',
          |    source_id TEXT NOT NULL DEFAULT '',
          |    title TEXT NOT NULL DEFAULT '',
          |    status TEXT NOT NULL DEFAULT '',
          |    score REAL NOT NULL DEFAULT 0.0,
          |    last_read_chapter REAL NOT NULL DEFAULT 0.0,
          |    last_read_volume INTEGER NOT NULL DEFAULT 0,
          |    total_chapters INTEGER NOT NULL DEFAULT 0,
          |    total_volumes INTEGER NOT NULL DEFAULT 0,
          |    chapter_offset INTEGER NOT NULL DEFAULT 0,
          |    started_at TEXT NOT NULL DEFAULT '',
          |    finished_at TEXT NOT NULL DEFAULT '',
          |    comment TEXT NOT NULL DEFAULT '',
          |    updated_at TEXT NOT NULL DEFAULT '',
          |    deleted_at TEXT NOT NULL DEFAULT '',
          |    PRIMARY KEY (tracker_id, manga_id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, "CREATE INDEX IF NOT EXISTS bookmark_manga_idx ON bookmark(manga_id)", 0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS bookmark_created_idx ON bookmark(created_at DESC)", 0)
      driver.execute(null,
          "CREATE UNIQUE INDEX IF NOT EXISTS bookmark_unique_idx ON bookmark(manga_id, chapter_id, page)",
          0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS chapter_pages_manga_idx ON chapter_pages(manga_id)", 0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS manga_favcat_manga_idx ON manga_favourite_category(manga_id)",
          0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS manga_favcat_cat_idx ON manga_favourite_category(category_id)",
          0)
      driver.execute(null, "CREATE INDEX IF NOT EXISTS manga_title_idx ON manga(title)", 0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS manga_favourite_added_idx ON manga_favourite(added_at DESC)",
          0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS manga_history_updated_idx ON manga_history(updated_at DESC)",
          0)
      driver.execute(null, "CREATE INDEX IF NOT EXISTS manga_source_name_idx ON manga_source(name)",
          0)
      driver.execute(null,
          "CREATE INDEX IF NOT EXISTS manga_update_new_idx ON manga_update(new_chapters_count DESC)",
          0)
      driver.execute(null, "CREATE INDEX IF NOT EXISTS tracking_manga_idx ON tracking(manga_id)", 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
