/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FileAlreadyExistsException} is thrown if a file or directory already exists but was NOT expected.
 * This means the {@link File file} is in the way in order to create a new one. The exception is both for the
 * case, that the {@link File file} should NOT be overwritten to prevent loss of data as well as the
 * {@link FileType type} differs (e.g. if there is already a directory with the same name, a regular file can
 * NOT be created).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FileAlreadyExistsException extends RuntimeIoException {

  /** UID for serialization. */
  private static final long serialVersionUID = 4648212905848792934L;

  /**
   * The constructor.
   * 
   * @param file is the file that already exists.
   */
  public FileAlreadyExistsException(File file) {

    this(file.getAbsolutePath());
    assert (file.exists());
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that already exists.
   */
  public FileAlreadyExistsException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that already exists.
   * @param directory - <code>true</code> if the exception is about a directory, <code>false</code> if the
   *        exception is about a file.
   */
  public FileAlreadyExistsException(String file, boolean directory) {

    super(createBundle(NlsMessagesBundleUtilCore.class).errorFileAlreadyExists(file, directory));
  }
}
