/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.handler.UiEventObserver;

/**
 * This interface gives read and write access to the {@link #getEventObserver() event observer} of an object.
 * 
 * @see UiEventObserver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteEventObserver extends AttributeReadEventObserver {

  /**
   * This method gets the editable status.
   * 
   * @param eventObserver is the {@link UiEventObserver}.
   */
  void setEventObserver(UiEventObserver eventObserver);

}
