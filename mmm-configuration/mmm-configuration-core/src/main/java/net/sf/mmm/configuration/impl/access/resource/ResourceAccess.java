/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.access.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.base.ConfigurationWriteException;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccess} interface
 * using {@link java.io.File} or {@link ClassLoader#getResource(String)} to read
 * and write data.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceAccess extends AbstractConfigurationAccess {

  /** the file to access */
  private final File file;

  /** the file to access */
  private final URL classpathResource;

  /** @see #getPath() */
  private final String path;

  /**
   * The constructor.
   * 
   * @param href
   *        is the absolute href of the resource to access.
   */
  public ResourceAccess(String href) {

    this(new File("").getAbsolutePath(), href);
  }

  /**
   * The constructor.
   * 
   * @param fileRootPath
   *        is the root path in the filesystem where the lookup for
   *        file-resources.
   * @param href
   *        is the absolute href of the resource to access.
   */
  public ResourceAccess(String fileRootPath, String href) {

    super();
    this.path = href;
    this.file = new File(fileRootPath, href);
    String resourcePath = href;
    if (resourcePath.charAt(0) == '/') {
      resourcePath = resourcePath.substring(1);
    }
    this.classpathResource = Thread.currentThread().getContextClassLoader().getResource(
        resourcePath);
    if ((this.classpathResource == null) && (!this.file.isFile())) {
      throw new ConfigurationReadException(href);
    }
    setContextPrefix(ConfigurationAccessFactory.CONTEXT_VARIABLE_PREFIX
        + ResourceAccessFactory.CONTEXT_DEFAULT_NAME);
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getUniqueUri()
   */
  public String getUniqueUri() {

    return this.file.getPath();
  }

  /**
   * This method gets the current path where the resource is located.
   * 
   * @return the path of the resource.
   */
  public String getPath() {

    return this.path;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getName()
   */
  public String getName() {

    return this.file.getPath();
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getReadAccess()
   */
  public InputStream getReadAccess() throws ConfigurationException {

    try {
      if (this.file.isFile()) {
        return new FileInputStream(this.file);
      } else if (this.classpathResource != null) {
        return this.classpathResource.openStream();
      } else {
        throw new FileNotFoundException(this.file.getPath());
      }
    } catch (IOException e) {
      throw new ConfigurationReadException(this, e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#getWriteAccess()
   */
  public OutputStream getWriteAccess() throws ConfigurationException {

    try {
      if (!this.file.isFile()) {
        this.file.createNewFile();
      }
      return new FileOutputStream(this.file);
    } catch (IOException e) {
      throw new ConfigurationWriteException(this, e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccess#isReadOnly()
   */
  public boolean isReadOnly() {

    return false;
  }

}
