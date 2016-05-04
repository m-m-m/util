/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is the {@link JavaScriptObject} representing a text selection.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JsSelection extends JavaScriptObject {

  /**
   * The constructor.
   */
  protected JsSelection() {

    super();
  }

  // formatter:off

  // /**
  // * Clears the entire selection with all contained ranges.
  // */
  // public final native JavaScriptRange getRangeAt(int index) /*-{
  // return this.getRangeAt(index);
  // }-*/;

  /**
   * Clears the entire selection with all contained ranges.
   */
  public final native void removeAllRanges() /*-{
                                             return this.removeAllRanges();
                                             }-*/;

  /**
   * @return the plain text of the selection.
   */
  public final native String getText() /*-{
                                       return this.toString();
                                       }-*/;

  /**
   * @return the HTML code of the selection.
   */
  public final native String getHtml() /*-{
                                       
                                       var element = $doc.createElement("span");
                                       element.appendChild(this.getRangeAt(0).cloneContents());
                                       return element.innerHTML;
                                       }-*/;

  // formatter:on

}
