/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileFilter;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This class implements the {@link FileFilter} interface by simply delegating
 * to a <code>{@link Filter}&lt;{@link String}&gt;</code>. It will get the
 * {@link File#getPath() path and filename} of the {@link #accept(File) file to
 * check} and normalizes all {@link File#separator separators} to
 * <code>'/'</code>. The resulting string is passed to the adapted
 * {@link Filter}.
 * 
 * @see FileFilterAdapter
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public final class FileStringFilterAdapter implements FileFilter {

  /** @see #accept(File) */
  private final Filter<String> filter;

  /**
   * The constructor.
   * 
   * @param filter is the filter to adapt.
   */
  public FileStringFilterAdapter(Filter<String> filter) {

    super();
    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(File file) {

    String filename = file.getPath().replace('\\', '/');
    return this.filter.accept(filename);
  }

}
