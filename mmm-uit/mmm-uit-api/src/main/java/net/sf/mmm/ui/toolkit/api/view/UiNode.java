/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view;

import net.sf.mmm.client.ui.api.attribute.AttributeReadEnabled;
import net.sf.mmm.client.ui.api.attribute.AttributeReadEnabledState;
import net.sf.mmm.client.ui.api.attribute.AttributeReadVisible;
import net.sf.mmm.client.ui.api.attribute.AttributeReadVisibleState;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStylesAdvanced;
import net.sf.mmm.ui.toolkit.api.UiObject;
import net.sf.mmm.ui.toolkit.api.event.UiEventSender;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;

/**
 * This is the abstract interface of all view {@link UiObject objects} of the UI. Such objects are called
 * nodes as they are organized in a hierarchical tree and therefore have a {@link #getParent() parent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiNode extends UiObject, AttributeWriteHtmlId, AttributeWriteStylesAdvanced, AttributeReadVisible,
    AttributeReadVisibleState, AttributeReadEnabled, AttributeReadEnabledState, AttributeWriteDisposed, UiEventSender {

  /**
   * This method gets the parent component.
   * 
   * @return the parent component or <code>null</code> if this is a root frame.
   */
  UiNode getParent();

  /**
   * This method gets the parent frame of this component.
   * 
   * @return the parent frame or <code>null</code> if this is a root frame.
   */
  UiFrame getParentFrame();

  /**
   * This method gets the parent window of this component.
   * 
   * @return the parent window or <code>null</code> if this is a root frame.
   */
  UiWindow getParentWindow();

  /**
   * This method refreshes this node. If not explicitly specified in the according sub-interface, there is no
   * need to call this method explicitly.
   */
  void refresh();

}
