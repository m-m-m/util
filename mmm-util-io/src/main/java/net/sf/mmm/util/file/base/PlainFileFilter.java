/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileFilter;

/**
 * This class represents a {@link java.io.FileFilter file-filter} that only accepts
 * {@link java.io.File#isFile() "plain files"}. Use {@link #getInstance()} to get the instance of this filter.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public final class PlainFileFilter implements FileFilter {

  /** the singleton instance */
  private static final FileFilter INSTANCE = new PlainFileFilter();

  /**
   * The constructor.
   */
  private PlainFileFilter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(File file) {

    return file.isFile();
  }

  /**
   * This method gets the singleton instance of this file-filter.
   * 
   * @return the filter.
   */
  public static FileFilter getInstance() {

    return INSTANCE;
  }

}
