/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.collection.base.EmptyIterator;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceNotWritableException;
import net.sf.mmm.util.resource.api.ResourceUri;

/**
 * This is the implementation of the {@link BrowsableResource} interface for a resource that is a
 * {@link File#isFile() regular} {@link File}. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class FileResource extends AbstractBrowsableResource {

  /**
   * The {@link #getSchemePrefix() scheme-prefix} for this type of {@link BrowsableResource}.
   */
  public static final String SCHEME_PREFIX = ResourceUri.SCHEME_PREFIX_FILE;

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
   * @param filePath is the {@link File#getPath() path} to the {@link File} to represent.
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
  @Override
  public OutputStream openOutputStream() throws ResourceNotWritableException {

    try {
      return new FileOutputStream(this.file);
    } catch (FileNotFoundException e) {
      throw new ResourceNotWritableException(e, getPath());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
  public boolean isData() {

    return this.file.exists() && !this.file.isDirectory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFolder() {

    return this.file.isDirectory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getLastModificationDate() {

    long lastModified = this.file.lastModified();
    if (lastModified != 0) {
      return new Date(lastModified);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResource navigate(String resourcePath) {

    File parent = this.file.getParentFile();
    return new FileResource(new File(parent, resourcePath));
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
    @Override
    public Iterator<BrowsableResource> iterator() {

      File[] files;
      if (FileResource.this.file.isDirectory()) {
        files = FileResource.this.file.listFiles();
      } else {
        return EmptyIterator.getInstance();
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
    @Override
    public boolean hasNext() {

      return (this.index < this.files.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileResource next() {

      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return new FileResource(this.files[this.index++]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {

      // we better do NOT delete the file here...
      throw new UnsupportedOperationException();
    }

  }

}
