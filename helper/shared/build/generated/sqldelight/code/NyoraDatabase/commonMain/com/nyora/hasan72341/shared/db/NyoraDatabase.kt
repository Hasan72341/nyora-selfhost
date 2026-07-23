package com.nyora.hasan72341.shared.db

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.nyora.hasan72341.shared.db.shared.newInstance
import com.nyora.hasan72341.shared.db.shared.schema
import kotlin.Unit

public interface NyoraDatabase : Transacter {
  public val bookmarkQueries: BookmarkQueries

  public val chapterPagesQueries: ChapterPagesQueries

  public val favouriteCategoryQueries: FavouriteCategoryQueries

  public val mangaQueries: MangaQueries

  public val mangaFavouriteQueries: MangaFavouriteQueries

  public val mangaHistoryQueries: MangaHistoryQueries

  public val mangaPrefsQueries: MangaPrefsQueries

  public val mangaSourceQueries: MangaSourceQueries

  public val mangaUpdateQueries: MangaUpdateQueries

  public val trackingQueries: TrackingQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = NyoraDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): NyoraDatabase =
        NyoraDatabase::class.newInstance(driver)
  }
}
