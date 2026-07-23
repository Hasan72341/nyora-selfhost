package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class Manga_update(
  public val manga_id: String,
  public val source_id: String,
  public val last_chapter_count: Long,
  public val new_chapters_count: Long,
  public val latest_chapter_title: String,
  public val last_synced_at: Long,
  public val updated_at: Long,
)
