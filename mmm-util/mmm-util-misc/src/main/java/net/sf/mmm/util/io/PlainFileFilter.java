/* $Id$ */
package net.sf.mmm.util.io;

import java.io.File;
import java.io.FileFilter;

/**
 * This class represents a {@link java.io.FileFilter file-filter} that only
 * accepts {@link java.io.File#isFile() "plain files"}. Use
 * {@link #getInstance()} to get the instance of this filter.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PlainFileFilter implements FileFilter {

  /** the singleton instance */
  private static final FileFilter INSTANCE = new PlainFileFilter();

  /**
   * The constructor.
   */
  private PlainFileFilter() {

    super();
  }

  /**
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(File file) {

    return file.isFile();
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
