package com.nyora.hasan72341.shared.db.mangaFavourite

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class SelectAll(
  public val id: String,
  public val title: String,
  public val alt_titles: String,
  public val url: String,
  public val public_url: String,
  public val rating: Double,
  public val is_nsfw: Long,
  public val content_rating: String?,
  public val cover_url: String,
  public val large_cover_url: String?,
  public val state: String?,
  public val authors: String,
  public val source_ref: String,
  public val description: String,
  public val tags: String,
  public val chapters: String,
  public val unread: Long,
  public val progress: Double,
  public val updated_at: Long,
  public val added_at: Long,
  public val sort_key: Long,
)
