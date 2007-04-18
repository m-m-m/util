/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the implementation of the {@link DataResource} interface for a resource
 * that is a {@link File#isFile() regular} {@link File}. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileResource extends AbstractResource {

  /** the file */
  private File file;

  /**
   * The constructor. 
   * 
   * @param someFile
   *        is the file.
   */
  public FileResource(File someFile) {

    super();
    this.file = someFile;
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    return this.file.getAbsolutePath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getSize() throws ResourceNotAvailableException {

    if (!isAvailable()) {
      throw new ResourceNotAvailableException(getPath());
    }
    return this.file.length();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InputStream openStream() throws ResourceNotAvailableException, IOException {

    try {
      return new FileInputStream(this.file);
    } catch (FileNotFoundException e) {
      throw new ResourceNotAvailableException(e, getPath());
    }
  }

  /**
   * {@inheritDoc}
   */
  public URL getUrl() throws ResourceNotAvailableException {

    try {
      return this.file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new ResourceNotAvailableException(getPath());
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAvailable() {

    // todo works for symlinks to files???
    return this.file.isFile();
  }

}
