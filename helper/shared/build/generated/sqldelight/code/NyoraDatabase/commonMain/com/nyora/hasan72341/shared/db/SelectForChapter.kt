package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class SelectForChapter(
  public val id: Long,
  public val manga_id: String,
  public val chapter_id: String,
  public val chapter_title: String,
  public val page: Long,
  public val note: String,
  public val created_at: Long,
  public val manga_title: String?,
  public val manga_cover_url: String?,
)
