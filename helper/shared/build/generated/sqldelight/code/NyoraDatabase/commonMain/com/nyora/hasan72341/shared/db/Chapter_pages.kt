package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class Chapter_pages(
  public val chapter_url: String,
  public val manga_id: String,
  public val pages_json: String,
  public val fetched_at: Long,
)
