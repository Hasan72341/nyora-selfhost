package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class Favourite_category(
  public val id: Long,
  public val title: String,
  public val sort_key: Long,
  public val created_at: Long,
  public val deleted_at: Long?,
)
