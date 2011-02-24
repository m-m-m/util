/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.UiFileAccess} interface. It gives
 * read-access to a given file in the filesystem.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UiFileAccessSimple implements UiFileAccess {

  /** @see #getUrl() */
  private String url;

  /**
   * The constructor.
   * 
   * @param url is the {@link #getUrl() URL}.
   */
  public UiFileAccessSimple(String url) {

    super();
    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  public String getUrl() {

    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return -1;
  }

}
