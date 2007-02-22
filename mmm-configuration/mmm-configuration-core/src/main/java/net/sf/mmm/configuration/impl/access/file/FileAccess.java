/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.access.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.base.ConfigurationWriteException;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccess} interface
 * using {@link java.io.File} to read and write data.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileAccess extends AbstractConfigurationAccess {

  /** the file to access */
  private final File file;

  /** the canonical path of the file */
  private final String canonicalPath;

  /**
   * The constructor.
   * 
   * @param configurationFile
   *        is the file to access.
   */
  public FileAccess(File configurationFile) {

    super();
    try {
      this.file = configurationFile;
      if (!this.file.isFile()) {
        throw new ConfigurationException("The given path " + this.file.getAbsolutePath()
            + " is no file!");
      }
      this.canonicalPath = this.file.getCanonicalPath();
    } catch (IOException e) {
      throw new ConfigurationException("", e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getUniqueUri()
   */
  public String getUniqueUri() {

    return this.canonicalPath;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getName()
   */
  public String getName() {

    return this.file.getName();
  }

  /**
   * This method gets the file to access.
   * 
   * @return the access file.
   */
  public File getFile() {

    return this.file;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getReadAccess()
   */
  public InputStream getReadAccess() throws ConfigurationException {

    try {
      return new FileInputStream(this.file);
    } catch (FileNotFoundException e) {
      throw new ConfigurationReadException(this, e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getWriteAccess()
   */
  public OutputStream getWriteAccess() throws ConfigurationException {

    try {
      this.file.createNewFile();
      return new FileOutputStream(this.file);
    } catch (IOException e) {
      throw new ConfigurationWriteException(this);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#isReadOnly()
   */
  public boolean isReadOnly() {

    return !(this.file.canWrite());
  }

}
