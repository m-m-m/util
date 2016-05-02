/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.file.NlsBundleUtilFileRoot;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FileNotExistsException} is thrown if a file or directory is expected but was NOT found. The
 * exception is both for the case, that the {@link File file} does NOT {@link File#exists() exist} at all as
 * well as the {@link FileType type} differs (e.g. if a file was expected but a directory has been found).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FileNotExistsException extends RuntimeIoException {

  /** UID for serialization. */
  private static final long serialVersionUID = 4648212905848792934L;

  /**
   * The constructor.
   *
   * @param file is the file that does NOT exist.
   */
  public FileNotExistsException(File file) {

    this(file.getAbsolutePath());
    assert (!file.exists());
  }

  /**
   * The constructor.
   *
   * @param file is the name of the file that does NOT exist.
   */
  public FileNotExistsException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   *
   * @param file is the name of the file that does NOT exist.
   * @param directory - {@code true} if the exception is about a directory, {@code false} if the
   *        exception is about a file.
   */
  public FileNotExistsException(String file, boolean directory) {

    super(createBundle(NlsBundleUtilFileRoot.class).errorFileNotExists(file, directory));
  }

}
