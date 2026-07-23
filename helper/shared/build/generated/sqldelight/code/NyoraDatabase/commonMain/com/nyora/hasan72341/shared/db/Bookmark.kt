package com.nyora.hasan72341.shared.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Bookmark(
  public val id: Long,
  public val manga_id: String,
  public val chapter_id: String,
  public val chapter_title: String,
  public val page: Long,
  public val scroll: Double,
  public val note: String,
  public val image_url: String,
  public val percent: Double,
  public val created_at: Long,
  public val updated_at: Long,
  public val deleted_at: Long?,
)
