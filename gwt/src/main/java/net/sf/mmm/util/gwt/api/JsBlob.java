/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;

/**
 * This is the {@link JavaScriptObject} representing a BLOB.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JsBlob extends JavaScriptObject {

  /**
   * The constructor.
   */
  protected JsBlob() {

    super();
  }

  // formatter:off

  /**
   * @return the size of this BLOB in bytes.
   */
  @UnsafeNativeLong
  public final native long getSize() /*-{
                                     return this.size;
                                     }-*/;

  /**
   * @return the mimetype of this BLOB (e.g. "image/jpeg").
   */
  public final native String getType() /*-{
                                       return this.type;
                                       }-*/;

  // formatter:on
}
