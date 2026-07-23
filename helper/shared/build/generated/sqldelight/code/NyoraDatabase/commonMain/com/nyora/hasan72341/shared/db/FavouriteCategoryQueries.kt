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

public class FavouriteCategoryQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllCategories(mapper: (
    id: Long,
    title: String,
    sort_key: Long,
    created_at: Long,
    manga_count: Long,
  ) -> T): Query<T> = Query(857_620_407, arrayOf("manga_favourite_category", "favourite_category"),
      driver, "FavouriteCategory.sq", "selectAllCategories", """
  |SELECT id, title, sort_key, created_at,
  |       (SELECT COUNT(*) FROM manga_favourite_category WHERE category_id = favourite_category.id) AS manga_count
  |FROM favourite_category
  |WHERE deleted_at IS NULL
  |ORDER BY sort_key, LOWER(title)
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!
    )
  }

  public fun selectAllCategories(): Query<SelectAllCategories> = selectAllCategories { id, title,
      sort_key, created_at, manga_count ->
    SelectAllCategories(
      id,
      title,
      sort_key,
      created_at,
      manga_count
    )
  }

  public fun <T : Any> selectAllCategoriesIncludingDeleted(mapper: (
    id: Long,
    title: String,
    sort_key: Long,
    created_at: Long,
    deleted_at: Long?,
  ) -> T): Query<T> = Query(-1_251_808_565, arrayOf("favourite_category"), driver,
      "FavouriteCategory.sq", "selectAllCategoriesIncludingDeleted", """
  |SELECT id, title, sort_key, created_at, deleted_at
  |FROM favourite_category
  |ORDER BY sort_key, LOWER(title)
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)
    )
  }

  public fun selectAllCategoriesIncludingDeleted(): Query<Favourite_category> =
      selectAllCategoriesIncludingDeleted { id, title, sort_key, created_at, deleted_at ->
    Favourite_category(
      id,
      title,
      sort_key,
      created_at,
      deleted_at
    )
  }

  public fun <T : Any> selectCategoriesForManga(manga_id: String, mapper: (
    id: Long,
    title: String,
    sort_key: Long,
    created_at: Long,
  ) -> T): Query<T> = SelectCategoriesForMangaQuery(manga_id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getLong(2)!!,
      cursor.getLong(3)!!
    )
  }

  public fun selectCategoriesForManga(manga_id: String): Query<SelectCategoriesForManga> =
      selectCategoriesForManga(manga_id) { id, title, sort_key, created_at ->
    SelectCategoriesForManga(
      id,
      title,
      sort_key,
      created_at
    )
  }

  public fun <T : Any> selectFavouritesByCategory(category_id: Long, mapper: (
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
  ) -> T): Query<T> = SelectFavouritesByCategoryQuery(category_id) { cursor ->
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
      cursor.getLong(19)!!
    )
  }

  public fun selectFavouritesByCategory(category_id: Long): Query<SelectFavouritesByCategory> =
      selectFavouritesByCategory(category_id) { id, title, alt_titles, url, public_url, rating,
      is_nsfw, content_rating, cover_url, large_cover_url, state, authors, source_ref, description,
      tags, chapters, unread, progress, updated_at, added_at ->
    SelectFavouritesByCategory(
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
      added_at
    )
  }

  public fun <T : Any> selectAllMangaCategories(mapper: (manga_id: String, category_id: Long) -> T):
      Query<T> = Query(-635_919_307, arrayOf("manga_favourite_category"), driver,
      "FavouriteCategory.sq", "selectAllMangaCategories",
      "SELECT manga_favourite_category.manga_id, manga_favourite_category.category_id FROM manga_favourite_category") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!
    )
  }

  public fun selectAllMangaCategories(): Query<Manga_favourite_category> =
      selectAllMangaCategories { manga_id, category_id ->
    Manga_favourite_category(
      manga_id,
      category_id
    )
  }

  /**
   * @return The number of rows updated.
   */
  public fun insertCategory(
    title: String,
    sort_key: Long,
    created_at: Long,
  ): QueryResult<Long> {
    val result = driver.execute(-1_674_183_583,
        """INSERT INTO favourite_category (title, sort_key, created_at) VALUES (?, ?, ?)""", 3) {
          bindString(0, title)
          bindLong(1, sort_key)
          bindLong(2, created_at)
        }
    notifyQueries(-1_674_183_583) { emit ->
      emit("favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun renameCategory(title: String, id: Long): QueryResult<Long> {
    val result = driver.execute(988_421_094,
        """UPDATE favourite_category SET title = ? WHERE id = ?""", 2) {
          bindString(0, title)
          bindLong(1, id)
        }
    notifyQueries(988_421_094) { emit ->
      emit("favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteCategory(id: Long): QueryResult<Long> {
    val result = driver.execute(-755_323_565, """DELETE FROM favourite_category WHERE id = ?""", 1)
        {
          bindLong(0, id)
        }
    notifyQueries(-755_323_565) { emit ->
      emit("favourite_category")
      emit("manga_favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun softDeleteCategory(deleted_at: Long?, id: Long): QueryResult<Long> {
    val result = driver.execute(-1_380_186_339,
        """UPDATE favourite_category SET deleted_at = ? WHERE id = ?""", 2) {
          bindLong(0, deleted_at)
          bindLong(1, id)
        }
    notifyQueries(-1_380_186_339) { emit ->
      emit("favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun addMangaToCategory(manga_id: String, category_id: Long): QueryResult<Long> {
    val result = driver.execute(-1_328_879_338,
        """INSERT OR IGNORE INTO manga_favourite_category (manga_id, category_id) VALUES (?, ?)""",
        2) {
          bindString(0, manga_id)
          bindLong(1, category_id)
        }
    notifyQueries(-1_328_879_338) { emit ->
      emit("manga_favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun removeMangaFromCategory(manga_id: String, category_id: Long): QueryResult<Long> {
    val result = driver.execute(408_171_054,
        """DELETE FROM manga_favourite_category WHERE manga_id = ? AND category_id = ?""", 2) {
          bindString(0, manga_id)
          bindLong(1, category_id)
        }
    notifyQueries(408_171_054) { emit ->
      emit("manga_favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun removeMangaFromAllCategories(manga_id: String): QueryResult<Long> {
    val result = driver.execute(-1_050_407_059,
        """DELETE FROM manga_favourite_category WHERE manga_id = ?""", 1) {
          bindString(0, manga_id)
        }
    notifyQueries(-1_050_407_059) { emit ->
      emit("manga_favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAllMangaCategories(): QueryResult<Long> {
    val result = driver.execute(1_791_280_932, """DELETE FROM manga_favourite_category""", 0)
    notifyQueries(1_791_280_932) { emit ->
      emit("manga_favourite_category")
    }
    return result
  }

  /**
   * @return The number of rows updated.
   */
  public fun deleteAllCategories(): QueryResult<Long> {
    val result = driver.execute(-508_181_656, """DELETE FROM favourite_category""", 0)
    notifyQueries(-508_181_656) { emit ->
      emit("favourite_category")
      emit("manga_favourite_category")
    }
    return result
  }

  private inner class SelectCategoriesForMangaQuery<out T : Any>(
    public val manga_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("favourite_category", "manga_favourite_category", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("favourite_category", "manga_favourite_category", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-259_665_459, """
    |SELECT c.id, c.title, c.sort_key, c.created_at
    |FROM manga_favourite_category mc
    |JOIN favourite_category c ON c.id = mc.category_id
    |WHERE mc.manga_id = ?
    |ORDER BY c.sort_key, LOWER(c.title)
    """.trimMargin(), mapper, 1) {
      bindString(0, manga_id)
    }

    override fun toString(): String = "FavouriteCategory.sq:selectCategoriesForManga"
  }

  private inner class SelectFavouritesByCategoryQuery<out T : Any>(
    public val category_id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("manga", "manga_favourite", "manga_favourite_category", listener =
          listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("manga", "manga_favourite", "manga_favourite_category", listener =
          listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(679_448_741, """
    |SELECT m.id, m.title, m.alt_titles, m.url, m.public_url, m.rating, m.is_nsfw, m.content_rating, m.cover_url, m.large_cover_url, m.state, m.authors, m.source_ref, m.description, m.tags, m.chapters, m.unread, m.progress, m.updated_at, f.added_at
    |FROM manga_favourite f
    |JOIN manga m ON m.id = f.manga_id
    |JOIN manga_favourite_category mc ON mc.manga_id = m.id
    |WHERE mc.category_id = ?
    |ORDER BY f.added_at DESC
    """.trimMargin(), mapper, 1) {
      bindLong(0, category_id)
    }

    override fun toString(): String = "FavouriteCategory.sq:selectFavouritesByCategory"
  }
}
