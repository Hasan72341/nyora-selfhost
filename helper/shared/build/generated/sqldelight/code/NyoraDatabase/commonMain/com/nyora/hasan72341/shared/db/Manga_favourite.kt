package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class Manga_favourite(
  public val manga_id: String,
  public val added_at: Long,
  public val sort_key: Long,
  public val deleted_at: Long?,
  public val updated_at: Long,
)
