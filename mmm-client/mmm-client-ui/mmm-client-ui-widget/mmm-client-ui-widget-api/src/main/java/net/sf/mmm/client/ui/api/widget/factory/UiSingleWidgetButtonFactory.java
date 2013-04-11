/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the interface for a factory used to create a {@link UiWidgetButton} for a particular
 * {@link UiHandlerPlain}.
 * 
 * @param <HANDLER> is the generic type of the {@link #getHandlerInterface() handler interface}. E.g.
 *        {@link net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiSingleWidgetButtonFactory<HANDLER extends UiHandlerPlain> {

  /**
   * @return the {@link UiHandlerPlain} sub-interface managed by this factory.
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
  boolean isInstance(UiHandlerPlain handler);

  /**
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createButton(Class, UiHandlerPlain, boolean)
   * 
   * @param context is the instance of {@link UiContext}.
   * @param handler is the instance of the {@link UiHandlerPlain}.
   * @param preventConfirmationPopup - see
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createButton(Class, UiHandlerPlain, boolean)}
   * @param variant is the optional {@link net.sf.mmm.util.lang.api.Variant} to use. May be <code>null</code>.
   * @return the new {@link UiWidgetButton}.
   */
  UiWidgetButton create(UiContext context, HANDLER handler, boolean preventConfirmationPopup, Object variant);

}
