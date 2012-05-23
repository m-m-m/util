/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteDisposed;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.ui.toolkit.api.view.UiNode;

/**
 * This is the interface that adapts to the native UI object of the underlying
 * toolkit implementation for a {@link net.sf.mmm.ui.toolkit.api.view.UiNode}.<br/>
 * It is a design trade-off as java does not have multi-inheritance (we would
 * need scala traits here). This way it is possible to implement an abstract
 * base-implementation for the types of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode}-hierarchy and inherit different
 * implementations (Swing, SWT, GWT, etc.) from that without creating redundant
 * code.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public interface UiNodeAdapter<DELEGATE> extends AttributeWriteHtmlId, AttributeWriteStyles, AttributeWriteVisible, AttributeWriteEnabled,
    UiWriteDisposed {

  /**
   * This method gets the native UI object.
   * 
   * @return the native UI object.
   */
  DELEGATE getDelegate();

  /**
   * This method gets the top-level UI object of this node. This is typically
   * the same as the {@link #getDelegate() delegate}. However more complex
   * widgets may be composed out of multiple native UI objects. In such case
   * this method can be overridden to return the top-level delegate used for
   * composition e.g. via
   * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiExtendableComposite#addChild(net.sf.mmm.ui.toolkit.api.view.UiElement)}
   * as well as to update {@link AttributeWriteVisible#setVisible(boolean) visibility}.
   * However {@link #getDelegate() delegate} still returns the active part of
   * the composed widget used to update
   * {@link AttributeWriteEnabled#setEnabled(boolean) activity}, etc.
   * 
   * @return the top-level UI object.
   */
  Object getToplevelDelegate();

  /**
   * This method is invoked by {@link AbstractUiNode#setParent(UiNode)}.
   * 
   * @param newParent is the new parent node. May be <code>null</code> to remove
   *        this object from the UI tree.
   */
  void setParent(UiNode newParent);

}
