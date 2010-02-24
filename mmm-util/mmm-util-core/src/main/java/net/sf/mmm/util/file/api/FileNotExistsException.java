/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link FileNotExistsException} is thrown if a file or directory is expected
 * but was NOT found. The exception is both for the case, that the {@link File
 * file} does NOT {@link File#exists() exist} at all as well as the
 * {@link FileType type} differs (e.g. if a file was expected but a directory
 * has been found).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class FileNotExistsException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 4648212905848792934L;

  /**
   * The constructor.
   * 
   * @param file is the file that does NOT exist.
   */
  public FileNotExistsException(File file) {

    this(file.getAbsolutePath());
    assert (file.exists());
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that does NOT exist.
   */
  public FileNotExistsException(String file) {

    super(NlsBundleUtilCore.ERR_FILE_NOT_EXISTS, toMap(KEY_FILE, file));
  }
}
