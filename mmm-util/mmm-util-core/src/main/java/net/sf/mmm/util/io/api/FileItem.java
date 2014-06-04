/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for a {@link java.io.File}. This abstraction is required as abstraction since a
 * web-client cannot support {@link java.io.File}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface FileItem {

  /**
   * @return the unique ID of the file. This can be the absolute path of the filesystem or the ID of a BLOB in
   *         a database.
   */
  Object getId();

  /**
   * @return the name of the file (e.g. "Sounds of joy.ogg").
   */
  String getName();

  /**
   * @return the size of the file in bytes.
   */
  long getSize();

}
