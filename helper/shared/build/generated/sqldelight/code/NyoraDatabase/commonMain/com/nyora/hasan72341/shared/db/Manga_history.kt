package com.nyora.hasan72341.shared.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Manga_history(
  public val manga_id: String,
  public val source_id: String,
  public val chapter_id: String,
  public val chapter_title: String,
  public val page: Long,
  public val scroll: Double,
  public val percent: Double,
  public val chapters_count: Long,
  public val updated_at: Long,
  public val deleted_at: Long?,
)
