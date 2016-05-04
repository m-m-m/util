/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.resource.api.ResourceUri;

/**
 * This is the implementation of the {@link ResourceUri} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ResourceUriImpl implements ResourceUri {

  /**
   * The {@link Pattern} for a {@link ResourceUri} {@link #getUri() as string}.
   */
  private static final Pattern SCHEME_PATTERN = Pattern.compile("([a-zA-Z][a-zA-Z0-9\\.\\-+]+:(//)?)(.*)");

  private  final String uri;

  private  final String schemePrefix;

  private  final String path;

  /**
   * The constructor.
   *
   * @param uri is the {@link #getUri() URI}.
   */
  public ResourceUriImpl(String uri) {

    super();
    if (uri == null) {
      throw new NlsNullPointerException("uri");
    }
    this.uri = uri;
    Matcher matcher = SCHEME_PATTERN.matcher(uri);
    if (matcher.matches()) {
      this.schemePrefix = matcher.group(1).toLowerCase(Locale.US);
      this.path = matcher.group(3);
    } else {
      this.schemePrefix = null;
      this.path = uri;
    }
  }

  /**
   * The constructor.
   *
   * @param schemePrefix is the {@link #getSchemePrefix() scheme-prefix}.
   * @param path is the {@link #getPath() path}.
   */
  public ResourceUriImpl(String schemePrefix, String path) {

    super();
    this.path = path;
    if (schemePrefix == null) {
      this.schemePrefix = null;
      this.uri = this.path;
    } else {
      this.schemePrefix = schemePrefix.toLowerCase(Locale.US);
      this.uri = this.schemePrefix + this.path;
    }
  }

  @Override
  public String getUri() {

    return this.uri;
  }

  @Override
  public String getSchemePrefix() {

    return this.schemePrefix;
  }

  @Override
  public String getPath() {

    return this.path;
  }

  @Override
  public String toString() {

    return this.uri;
  }
}
