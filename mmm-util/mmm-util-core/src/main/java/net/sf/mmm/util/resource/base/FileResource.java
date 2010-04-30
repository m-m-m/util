/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

/**
 * This is the implementation of the {@link DataResource} interface for a
 * resource that is a {@link File#isFile() regular} {@link File}. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class FileResource extends AbstractDataResource {

  /**
   * The {@link #getSchemePrefix() scheme-prefix} for this type of
   * {@link DataResource}.
   */
  public static final String SCHEME_PREFIX = "file://";

  /** the file */
  private File file;

  /**
   * The constructor.
   * 
   * @param file is the {@link File} to represent.
   */
  public FileResource(File file) {

    super();
    this.file = file;
  }

  /**
   * The constructor.
   * 
   * @param filePath is the {@link File#getPath() path} to the {@link File} to
   *        represent.
   */
  public FileResource(String filePath) {

    this(new File(filePath));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSchemePrefix() {

    return SCHEME_PREFIX;
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
  public InputStream openStream() {

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

  /**
   * {@inheritDoc}
   */
  public DataResource navigate(String relativePath) {

    return new FileResource(new File(this.file.getParentFile(), relativePath));
  }

}
