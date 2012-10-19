/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This is the interface for a {@link java.io.File}. This abstraction is required as abstraction since a
 * web-client cannot support {@link java.io.File}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface FileItem {

  /**
   * @return the name of the file (e.g. "Sounds of joy.ogg").
   */
  String getName();

  /**
   * @return the size of the file in bytes.
   */
  long getSize();

}
