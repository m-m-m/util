/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.action.UiHandlerAction;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the interface for a factory used to create a {@link UiWidgetButton} for a particular
 * {@link UiHandlerAction}.
 * 
 * @param <HANDLER> is the generic type of the {@link #getHandlerInterface() handler interface}. E.g.
 *        {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiSingleWidgetButtonFactory<HANDLER extends UiHandlerAction> {

  /**
   * @return the {@link UiHandlerAction} sub-interface managed by this factory.
   */
  Class<HANDLER> getHandlerInterface();

  /**
   * Determines if the given <code>handler</code> {@link Class#isInstance(Object) is an instance} of the
   * managed {@link #getHandlerInterface() handler interface}. Needs to be implemented explicitly in order to
   * be GWT compatible.
   * 
   * @param handler is the handler instance to check.
   * @return <code>true</code> if the given <code>handler</code> {@link Class#isInstance(Object) is an
   *         instance} of the managed {@link #getHandlerInterface() handler interface}.
   */
  boolean isInstance(UiHandlerAction handler);

  /**
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createButton(Class, UiHandlerAction, boolean)
   * 
   * @param context is the instance of {@link UiContext}.
   * @param handler is the instance of the {@link UiHandlerAction}.
   * @param preventConfirmationPopup - see
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createButton(Class, UiHandlerAction, boolean)}
   * @return the new {@link UiWidgetButton}.
   */
  UiWidgetButton create(UiContext context, HANDLER handler, boolean preventConfirmationPopup);

}
