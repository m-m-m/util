/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getUrl() URL} attribute of an object (e.g. an image).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadUrl {

  /**
   * This method gets the <em>URL</em> of this object. The URL can be absolute or relative (a path relative to
   * the current base URL).
   * 
   * @return the URL or <code>null</code> if NOT set.
   */
  String getUrl();

}
