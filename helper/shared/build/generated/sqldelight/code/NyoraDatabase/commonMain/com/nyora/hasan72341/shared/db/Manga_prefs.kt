package com.nyora.hasan72341.shared.db

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class Manga_prefs(
  public val manga_id: String,
  public val reader_mode: String,
  public val brightness: Double,
  public val contrast: Double,
  public val saturation: Double,
  public val hue: Double,
  public val palette: String,
  public val updated_at: Long,
)
