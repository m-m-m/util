/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;

/**
 * This is the interface for the {@link UiFeature features} of a collapsable object. It can be
 * {@link #setCollapsed(boolean) collapsed and expanded} and allows to
 * {@link #addCollapseHandler(UiHandlerEventCollapse) add} and
 * {@link #removeCollapseHandle(UiHandlerEventCollapse) remove} instances of {@link UiHandlerEventCollapse}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureCollapse extends UiFeature, AttributeWriteCollapsed {

  /**
   * This method adds the given {@link UiHandlerEventCollapse} to this object.
   * 
   * @param handler is the {@link UiHandlerEventCollapse} to add.
   */
  void addCollapseHandler(UiHandlerEventCollapse handler);

  /**
   * This method removes the given {@link UiHandlerEventCollapse} from this object.
   * 
   * @param handler is the {@link UiHandlerEventCollapse} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addCollapseHandler(UiHandlerEventCollapse) registered} and nothing has
   *         changed.
   */
  boolean removeCollapseHandle(UiHandlerEventCollapse handler);

}
