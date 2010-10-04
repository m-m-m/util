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
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

/**
 * This is the implementation of the {@link BrowsableResource} interface for a
 * resource that is a {@link File#isFile() regular} {@link File}. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class FileResource extends AbstractBrowsableResource {

  /**
   * The {@link #getSchemePrefix() scheme-prefix} for this type of
   * {@link BrowsableResource}.
   */
  public static final String SCHEME_PREFIX = "file://";

  /** The {@link File} to adapt. */
  private final File file;

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
  @Override
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
  @Override
  public String getUri() {

    return SCHEME_PREFIX + this.file.getAbsolutePath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.file.getName();
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
  public boolean isFolder() {

    return this.file.isDirectory();
  }

  /**
   * {@inheritDoc}
   */
  public BrowsableResource navigate(String resourcePath) {

    return new FileResource(new File(this.file.getParentFile(), resourcePath));
  }

  /**
   * {@inheritDoc}
   */
  public Iterable<BrowsableResource> getChildResources() {

    return new FileResourceIterable();
  }

  /**
   * This inner class is an {@link Iterable} of {@link FileResource}s.
   */
  protected class FileResourceIterable implements Iterable<BrowsableResource> {

    /**
     * The constructor.
     */
    public FileResourceIterable() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<BrowsableResource> iterator() {

      File[] files;
      if (FileResource.this.file.isDirectory()) {
        files = FileResource.this.file.listFiles();
      } else {
        files = FileUtil.NO_FILES;
      }
      return new FileResourceIterator(files);
    }
  }

  /**
   * This inner class is an {@link Iterator} of {@link FileResource}s.
   */
  protected static class FileResourceIterator implements Iterator<BrowsableResource> {

    /** The array of {@link File}s. */
    private final File[] files;

    /** The current index in {@link #files}. */
    private int index;

    /**
     * The constructor.
     * 
     * @param files are the {@link File}s to iterate.
     */
    public FileResourceIterator(File[] files) {

      super();
      this.files = files;
      this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {

      return (this.index < this.files.length);
    }

    /**
     * {@inheritDoc}
     */
    public FileResource next() {

      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return new FileResource(this.files[this.index++]);
    }

    /**
     * {@inheritDoc}
     */
    public void remove() {

      // we better do NOT delete the file here...
      throw new UnsupportedOperationException();
    }

  }

}
