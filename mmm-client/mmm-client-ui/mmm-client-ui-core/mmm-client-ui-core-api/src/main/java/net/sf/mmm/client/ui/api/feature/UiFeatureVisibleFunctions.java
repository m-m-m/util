/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeReadVisible;

/**
 * This is the interface for the {@link UiFeature} of an object that owns
 * {@link #addVisibleFunction(AttributeReadVisible) visible-functions} that can be
 * {@link #addVisibleFunction(AttributeReadVisible) added} and
 * {@link #removeVisibleFunction(AttributeReadVisible) removed}.
 * 
 * @see UiFeatureVisible
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureVisibleFunctions extends UiFeature {

  /**
   * This method adds the given function that {@link AttributeReadVisible#isVisible() dynamically computes} an
   * additional visibility. This is typically an external function implemented as anonymous class but may also
   * be another instance of {@link UiFeatureVisibleFunctions}.<br/>
   * In every bigger client applications there are additional circumstances like authorization or other states
   * that have influence on the visibility. If then additional dynamics are added (e.g. via
   * {@link UiFeatureValue#addChangeHandler(net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange)
   * change handler}) that may {@link UiFeatureVisible#setVisible(boolean) set the visibility}, this will
   * easily cause bugs or unmaintainable code. By separating these aspects you can address cross-cutting
   * concerns via a visibility function that is independent of the
   * {@link UiFeatureVisible#setVisible(boolean) visible flag} used for dialog specific dynamics.<br/>
   * Your implementation of {@link AttributeReadVisible} may contain a central state-object that itself
   * contains the top-most related UI node (widget) and offer an API for state changes that automatically
   * trigger {@link UiFeatureVisible#updateVisibility()} on that UI node.<br/>
   * <b>NOTE:</b><br/>
   * For consistency this method will automatically trigger {@link UiFeatureVisible#updateVisibilityLocal()}.
   * 
   * @see UiFeatureVisible#isVisibleAggregated()
   * 
   * @param function is the visible-function.
   */
  void addVisibleFunction(AttributeReadVisible function);

  /**
   * This method removes the given {@link #addVisibleFunction(AttributeReadVisible) visible-function}.<br/>
   * <b>NOTE:</b><br/>
   * For consistency it will automatically trigger {@link UiFeatureVisible#updateVisibilityLocal()} if the
   * result is <code>true</code>.
   * 
   * @param function is the visible-function.
   * @return <code>true</code> if the given <code>function</code> has successfully been removed,
   *         <code>false</code> if it has NOT previously been
   *         {@link #addVisibleFunction(AttributeReadVisible) added} and this method call had no effect.
   */
  boolean removeVisibleFunction(AttributeReadVisible function);

}
