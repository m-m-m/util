/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureHandlerObserver;

/**
 * This interface gives read and write access to the {@link #getHandlerObserver() handler observer} of an
 * object.
 * 
 * @see UiFeatureHandlerObserver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteHandlerObserver extends AttributeReadHandlerObserver {

  /**
   * This method gets the editable status.
   * 
   * @param handlerObserver is the {@link UiFeatureHandlerObserver}.
   */
  void setHandlerObserver(UiFeatureHandlerObserver handlerObserver);

}
