package com.nyora.hasan72341.shared.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Tracking(
  public val tracker_id: String,
  public val manga_id: String,
  public val remote_id: String,
  public val source_id: String,
  public val title: String,
  public val status: String,
  public val score: Double,
  public val last_read_chapter: Double,
  public val last_read_volume: Long,
  public val total_chapters: Long,
  public val total_volumes: Long,
  public val chapter_offset: Long,
  public val started_at: String,
  public val finished_at: String,
  public val comment: String,
  public val updated_at: String,
  public val deleted_at: String,
)
