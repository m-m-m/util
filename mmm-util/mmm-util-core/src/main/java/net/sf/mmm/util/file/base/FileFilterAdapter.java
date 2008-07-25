/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileFilter;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This class implements the {@link FileFilter} interface by simply delegating
 * to a <code>{@link Filter}&lt;{@link File}&gt;</code>.
 * 
 * @see FileStringFilterAdapter
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FileFilterAdapter implements FileFilter {

  /** @see #accept(File) */
  private final Filter<File> filter;

  /**
   * The constructor.
   * 
   * @param filter is the filter to adapt.
   */
  public FileFilterAdapter(Filter<File> filter) {

    super();
    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(File file) {

    return this.filter.accept(file);
  }

}
