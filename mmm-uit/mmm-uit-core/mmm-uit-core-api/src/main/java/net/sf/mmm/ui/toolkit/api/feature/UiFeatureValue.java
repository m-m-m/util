/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventChange;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for the {@link UiFeature features} of an object that has a value that can be changed
 * programmatic as well as by the user (e.g. by typing in a text input field).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiFeatureValue<VALUE> extends UiFeature, AttributeWriteValue<VALUE>, AttributeWriteHandlerObserver {

  /**
   * This method adds the given {@link UiHandlerEventChange} to this object.
   * 
   * @param handler is the {@link UiHandlerEventChange} to add.
   */
  void addChangeHandler(UiHandlerEventChange<VALUE> handler);

  /**
   * This method removes the given {@link UiHandlerEventChange} from this object.
   * 
   * @param handler is the {@link UiHandlerEventChange} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addChangeHandler(UiHandlerEventChange) registered} and nothing has changed.
   */
  boolean removeChangeHandler(UiHandlerEventChange<VALUE> handler);

}
