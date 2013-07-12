/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is the {@link JavaScriptObject} representing a BLOB.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JavaScriptBlob extends JavaScriptObject {

  /**
   * The constructor.
   */
  protected JavaScriptBlob() {

    super();
  }

  //formatter:off

  /**
   * @return the size of this BLOB in bytes.
   */
  public final native long getSize() /*-{
    return this.size;
  }-*/;

  /**
   * @return the mimetype of this BLOB (e.g. "image/jpeg").
   */
  public final native String getType() /*-{
    return this.type;
  }-*/;

  //formatter:on
}
