/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.file.NlsBundleUtilFileRoot;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FilePermissionException} is thrown if a file or directory can not be read or modified due to limited
 * permissions.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.4.0
 */
public class FilePermissionException extends RuntimeIoException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param file that could not be accessed or modified due to insufficient permissions.
   */
  public FilePermissionException(File file) {

    this(file.getAbsolutePath(), file.isDirectory());
  }

  /**
   * The constructor.
   *
   * @param file that could not be accessed or modified due to insufficient permissions.
   */
  public FilePermissionException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   *
   * @param file that could not be accessed or modified due to insufficient permissions.
   * @param directory - {@code true} if the exception is about a directory, {@code false} if the exception is about a
   *        file.
   */
  public FilePermissionException(String file, boolean directory) {

    super(createBundle(NlsBundleUtilFileRoot.class).errorFilePermission(file, directory));
  }

}
