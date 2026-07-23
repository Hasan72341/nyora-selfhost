package com.nyora.hasan72341.shared.db

import kotlin.Long
import kotlin.String

public data class Manga_source(
  public val id: String,
  public val name: String,
  public val lang: String,
  public val base_url: String,
  public val package_name: String,
  public val source_code_url: String,
  public val icon_url: String,
  public val version: String,
  public val version_code: Long,
  public val is_installed: Long,
  public val is_pinned: Long,
  public val is_nsfw: Long,
  public val is_obsolete: Long,
  public val engine: String,
  public val content_type: String,
  public val notes: String,
  public val local_path: String,
  public val installed_at: Long,
  public val can_uninstall: Long,
)
