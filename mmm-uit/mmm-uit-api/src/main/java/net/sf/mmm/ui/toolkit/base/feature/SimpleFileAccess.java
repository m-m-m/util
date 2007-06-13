/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.mmm.ui.toolkit.api.feature.FileAccess;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.FileAccess} interface. It gives
 * read-access to a given file in the filesystem.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleFileAccess implements FileAccess {

  /** the actuall file */
  private final File file;

  /**
   * The constructor.
   * 
   * @param dataFile is the file to access.
   */
  public SimpleFileAccess(File dataFile) {

    super();
    this.file = dataFile;
  }

  /**
   * {@inheritDoc}
   */
  public InputStream getFile() throws IOException {

    return new FileInputStream(this.file);
  }

  /**
   * {@inheritDoc}
   */
  public String getFilename() {

    return this.file.getName();
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return this.file.length();
  }

}
