/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.api;

import java.io.File;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * A {@link FileAttributeModificationFailedException} is thrown if the <em>attributes</em> of a
 * {@link File#isFile() file} or {@link File#isDirectory() directory} failed to be modified. The term
 * attributes refers to {@link net.sf.mmm.util.file.base.FileAccessPermissions}s as well as other metadata
 * such as e.g. {@link File#setLastModified(long) last modified}.
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class FileAttributeModificationFailedException extends RuntimeIoException {

  /** UID for serialization. */
  private static final long serialVersionUID = 64670818305399447L;

  /**
   * The constructor.
   * 
   * @param file is the file that could NOT be created.
   */
  public FileAttributeModificationFailedException(File file) {

    this(file.getAbsolutePath(), file.isDirectory());
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be created.
   */
  public FileAttributeModificationFailedException(String file) {

    this(file, false);
  }

  /**
   * The constructor.
   * 
   * @param file is the name of the file that could NOT be created.
   * @param directory - <code>true</code> if the exception is about a directory, <code>false</code> if the
   *        exception is about a file.
   */
  public FileAttributeModificationFailedException(String file, boolean directory) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorFileAttributeModificationFailed(file, directory));
  }

}
