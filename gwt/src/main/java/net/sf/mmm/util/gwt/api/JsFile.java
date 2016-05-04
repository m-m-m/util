/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import net.sf.mmm.util.io.api.FileItem;

/**
 * This is the {@link com.google.gwt.core.client.JavaScriptObject} representing a {@link FileItem}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JsFile extends JsBlob implements FileItem {

  /**
   * The constructor.
   */
  protected JsFile() {

    super();
  }

  // formatter:off

  @Override
  public final Object getId() {

    return null;
  }

  @Override
  public final native String getName() /*-{
                                       return this.name;
                                       }-*/;

  // formatter:on

}
