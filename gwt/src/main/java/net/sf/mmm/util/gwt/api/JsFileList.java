/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is the {@link JavaScriptObject} representing a {@code FileList} what is an array of {@link JsFile files}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JsFileList extends JavaScriptObject {

  /**
   * The constructor.
   */
  protected JsFileList() {

    super();
  }

  // formatter:off

  /**
   * @return the length of this list (the number of {@link JsFile files}).
   */
  public final native int length() /*-{
                                   return this.length;
                                   }-*/;

  /**
   * @see java.util.List#get(int)
   *
   * @param index is the index of the {@link JsFile file} to get.
   * @return the {@link JsFile file} at the given {@code index}.
   */
  public final native JsFile item(int index) /*-{
                                             return this.item(index);
                                             }-*/;

  // formatter:on

  /**
   * @return this list as regular Java array.
   */
  public final JsFile[] toFileArray() {

    JsFile[] result = new JsFile[length()];
    for (int i = 0; i < result.length; i++) {
      result[i] = item(i);
    }
    return result;
  }

}
