/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getHeightInRows() height in rows} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadHeightInRows {

  /**
   * This method gets the <em>height in rows</em> of this object. This is the number of rows (e.g. textual
   * lines) that are visible at a time. If the there are more rows than this height a scrollbar will
   * automatically appear.
   * 
   * @return the height in rows.
   */
  int getHeightInRows();

}
