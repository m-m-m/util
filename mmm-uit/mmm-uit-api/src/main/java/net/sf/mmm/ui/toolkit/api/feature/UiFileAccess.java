/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

/**
 * This is the interface to access a file (or any arbitrary data).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiFileAccess {

  /**
   * This method gets the href of the identified file. This can be an absolute URL but may also be a path
   * relative to the base URL of the application.
   * 
   * @return the URL or href.
   */
  String getUrl();

  /**
   * This method gets the size of the data in bytes or <code>-1</code> if the size is unknown.
   * 
   * @return the size in bytes or <code>-1</code> if unknown.
   */
  long getSize();

}
