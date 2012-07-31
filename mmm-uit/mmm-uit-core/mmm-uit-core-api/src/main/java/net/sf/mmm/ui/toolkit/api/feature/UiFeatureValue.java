/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteValueAdvanced;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;

/**
 * This is the interface for the {@link UiFeature features} of an object that has a {@link #getValue() value}
 * that can be {@link net.sf.mmm.util.lang.api.attribute.AttributeWriteValue#setValue(Object) changed
 * programmatic} as well as by the user (e.g. by typing in a text input field). Additionally, it allows to
 * {@link #addChangeHandler(UiHandlerEventValueChange) add} and
 * {@link #removeChangeHandler(UiHandlerEventValueChange) remove} instances of
 * {@link UiHandlerEventValueChange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiFeatureValue<VALUE> extends UiFeature, AttributeWriteValueAdvanced<VALUE> {

  /**
   * This method adds the given {@link UiHandlerEventValueChange} to this object.
   * 
   * @param handler is the {@link UiHandlerEventValueChange} to add.
   */
  void addChangeHandler(UiHandlerEventValueChange<VALUE> handler);

  /**
   * This method removes the given {@link UiHandlerEventValueChange} from this object.
   * 
   * @param handler is the {@link UiHandlerEventValueChange} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addChangeHandler(UiHandlerEventValueChange) registered} and nothing has
   *         changed.
   */
  boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler);

}
