/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.net.URI;
import java.net.URISyntaxException;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.4.0
 */
public final class UriHelper {

  private UriHelper() {}

  /**
   * @param uri the {@link URI} as {@link String}.
   * @return the parsed {@link URI} instance.
   * @throws RuntimeException if the instantiation failed.
   */
  public static URI newUri(String uri) {

    try {
      return new URI(uri);
    } catch (URISyntaxException e) {
      throw new NlsIllegalArgumentException(uri, "URI", e);
    }
  }

}
