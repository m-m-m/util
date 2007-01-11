/* $Id$ */
package net.sf.mmm.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the implementation of the {@link Resource} interface for a resource
 * that is a {@link File#isFile() regular} {@link File}. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileResource extends AbstractResource {

  /** the file */
  private File file;

  /**
   * The constructor
   * 
   * @param someFile
   *        is the file.
   */
  public FileResource(File someFile) {

    super();
    this.file = someFile;
  }

  /**
   * @see net.sf.mmm.util.resource.Resource#getPath()
   */
  public String getPath() {

    return this.file.getAbsolutePath();
  }

  /**
   * @see net.sf.mmm.util.resource.AbstractResource#getSize()
   */
  @Override
  public long getSize() throws ResourceNotAvailableException {

    if (!isAvailable()) {
      throw new ResourceNotAvailableException(getPath());
    }
    return this.file.length();
  }

  /**
   * @see net.sf.mmm.util.resource.AbstractResource#openStream()
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
   * @see net.sf.mmm.util.resource.Resource#getUrl()
   */
  public URL getUrl() throws ResourceNotAvailableException {

    try {
      return this.file.toURI().toURL();
    } catch (MalformedURLException e) {
      throw new ResourceNotAvailableException(getPath());
    }
  }

  /**
   * @see net.sf.mmm.util.resource.Resource#isAvailable()
   */
  public boolean isAvailable() {

    // todo works for symlinks to files???
    return this.file.isFile();
  }

}
