/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FileFilterAdapter {

  /**
   * The forbidden constructor.
   */
  private FileFilterAdapter() {

    super();
  }

  /**
   * This method converts the given <code>filter</code> to a
   * {@link FileFilter}.
   * 
   * @param filter
   *        is a string-filter.
   * @return a {@link FileFilter} that {@link FileFilter#accept(File) accepts}
   *         if the given <code>filter</code>
   *         {@link Filter#accept(Object) accepts} the
   *         {@link File#getPath() path} of the file.
   */
  public static FileFilter convertStringFilter(final Filter<String> filter) {

    return new FileFilter() {

      public boolean accept(File file) {

        return filter.accept(file.getPath().replace('\\', '/'));
      }
    };
  }

  /**
   * This method converts the given <code>filter</code> to a
   * {@link FileFilter}.
   * 
   * @param filter
   *        is a file-filter.
   * @return a {@link FileFilter} that {@link FileFilter#accept(File) accepts}
   *         if the given <code>filter</code>
   *         {@link Filter#accept(Object) accepts}.
   */
  public static FileFilter convertFileFilter(final Filter<File> filter) {

    return new FileFilter() {

      public boolean accept(File file) {

        return filter.accept(file);
      }
    };
  }
}
