/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FileDeletionFailedException} is thrown if a file or directory should be deleted but the deletion
 * failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FileDeletionFailedException extends RuntimeIoException {

  /** UID for serialization. */
  private static final long serialVersionUID = 2351628909914860156L;

  /**
   * The constructor.
   * 
   * @param file is the file that could NOT be deleted.
   */
  public FileDeletionFailedException(File file) {

    this(file.getAbsolutePath());
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be deleted.
   */
  public FileDeletionFailedException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be deleted.
   * @param directory - <code>true</code> if the exception is about a directory, <code>false</code> if the
   *        exception is about a file.
   */
  public FileDeletionFailedException(String file, boolean directory) {

    super(createBundle(NlsBundleUtilCore.class).errorFileDeletionFailed(file, directory));
  }

}
