/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectedValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;

/**
 * This is the interface for the {@link UiFeature features} of an object that offers
 * {@link #getSelectedValues() selection of values}. Additionally, it allows to
 * {@link #addSelectionHandler(UiHandlerEventSelection) add} and
 * {@link #removeSelectionHandler(UiHandlerEventSelection) remove} instances of
 * {@link UiHandlerEventSelection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 */
public interface UiFeatureSelectedValue<VALUE> extends AttributeWriteSelectedValue<VALUE> {

  /**
   * This method adds the given {@link UiHandlerEventSelection} to this object.
   * 
   * @param handler is the {@link UiHandlerEventSelection} to add.
   */
  void addSelectionHandler(UiHandlerEventSelection<VALUE> handler);

  /**
   * This method removes the given {@link UiHandlerEventSelection} from this object.
   * 
   * @param handler is the {@link UiHandlerEventSelection} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addSelectionHandler(UiHandlerEventSelection) registered} and nothing has
   *         changed.
   */
  boolean removeSelectionHandler(UiHandlerEventSelection<VALUE> handler);

}
