/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import net.sf.mmm.util.io.api.FileItem;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is the {@link JavaScriptObject} representing a {@link FileItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JavaScriptFile extends JavaScriptBlob implements FileItem {

  /**
   * The constructor.
   */
  protected JavaScriptFile() {

    super();
  }

  //formatter:off

  /**
   * {@inheritDoc}
   */
  @Override
  public final Object getId() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final native String getName() /*-{
    return this.name;
  }-*/;

  //formatter:on

}
