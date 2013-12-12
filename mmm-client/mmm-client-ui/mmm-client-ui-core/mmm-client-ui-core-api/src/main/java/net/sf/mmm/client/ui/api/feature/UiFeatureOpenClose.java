/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventOpenClose;

/**
 * This is the interface for the {@link UiFeature features} of an object (e.g. a window) that can be
 * {@link #open() opened} and {@link #close() closed}. It allows to
 * {@link #addOpenCloseHandler(UiHandlerEventOpenClose) add} and
 * {@link #removeOpenCloseHandle(UiHandlerEventOpenClose) remove} instances of {@link UiHandlerEventOpenClose
 * open/close handlers}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureOpenClose extends UiFeature, AttributeWriteVisible {

  /**
   * Opens this object so it gets {@link #isVisible() visible}. Has the same effect as
   * <code>{@link #setVisible(boolean) setVisible}(true)</code>.
   */
  void open();

  /**
   * Closes this object so it gets {@link #isVisible() hidden}. Has the same effect as
   * <code>{@link #setVisible(boolean) setVisible}(false)</code>.
   */
  void close();

  /**
   * This method adds the given {@link UiHandlerEventOpenClose} to this object.
   * 
   * @param handler is the {@link UiHandlerEventOpenClose} to add.
   */
  void addOpenCloseHandler(UiHandlerEventOpenClose handler);

  /**
   * This method removes the given {@link UiHandlerEventOpenClose} from this object.
   * 
   * @param handler is the {@link UiHandlerEventOpenClose} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addOpenCloseHandler(UiHandlerEventOpenClose) registered} and nothing has
   *         changed.
   */
  boolean removeOpenCloseHandle(UiHandlerEventOpenClose handler);

}
