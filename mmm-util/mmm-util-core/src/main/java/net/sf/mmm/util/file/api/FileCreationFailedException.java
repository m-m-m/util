/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FileCreationFailedException} is thrown if a file or directory should be created but the creation
 * failed. The exception is both for the case, that the {@link File file} did NOT exist before and could NOT
 * be created because of a technical problem as well as that a the file already exists but has the wrong type
 * (e.g. a directory should be created but a regular file with that name already exists). However in the
 * second case it is better to throw a {@link FileAlreadyExistsException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FileCreationFailedException extends RuntimeIoException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1399669289660138804L;

  /**
   * The constructor.
   * 
   * @param file is the file that could NOT be created.
   */
  public FileCreationFailedException(File file) {

    this(file.getAbsolutePath());
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be created.
   */
  public FileCreationFailedException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be created.
   * @param directory - <code>true</code> if the exception is about a directory, <code>false</code> if the
   *        exception is about a file.
   */
  public FileCreationFailedException(String file, boolean directory) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorFileCreationFailed(file, directory));
  }

}
