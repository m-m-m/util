/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.SelectionMode;

/**
 * This interface gives read and write access to the {@link #getSelectionMode() selection mode} of an object.
 * 
 * @see #setSelectionMode
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSelectionMode extends AttributeReadSelectionMode {

  /**
   * This method sets the {@link #getSelectionMode() selection mode}.<br/>
   * <b>ATTENTION:</b><br/>
   * It is recommended to set the {@link SelectionMode} immediately after creating the object. The initial
   * default is always {@link SelectionMode#SINGLE_SELECTION}. Dynamically changing the {@link SelectionMode}
   * may be expensive or not be supported (in exotic implementations) and should therefore be avoided.
   * 
   * @param selectionMode is the new {@link SelectionMode}.
   */
  void setSelectionMode(SelectionMode selectionMode);

}
