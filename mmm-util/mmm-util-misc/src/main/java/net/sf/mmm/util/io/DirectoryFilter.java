/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.File;
import java.io.FileFilter;

/**
 * This class represents a {@link java.io.FileFilter file-filter} that only
 * accepts {@link java.io.File#isDirectory() directories}. Use
 * {@link #getInstance()} to get the instance of this filter.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DirectoryFilter implements FileFilter {

  /** the singleton instance */
  private static final FileFilter INSTANCE = new DirectoryFilter();

  /**
   * The constructor.
   */
  private DirectoryFilter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(File file) {

    return file.isDirectory();
  }

  /**
   * This method gets the signleton instance of this file-filter.
   * 
   * @return the filter.
   */
  public static FileFilter getInstance() {

    return INSTANCE;
  }

}
