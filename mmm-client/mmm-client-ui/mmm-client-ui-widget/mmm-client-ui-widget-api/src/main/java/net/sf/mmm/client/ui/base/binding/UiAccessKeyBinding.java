/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.api.widget.UiWidgetActive;

/**
 * This is the interface for a central manager of
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetActive#setAccessKey(char) access keys}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiAccessKeyBinding {

  /**
   * This method registers the given <code>key</code> for the given <code>widget</code>.
   * 
   * @param key is the {@link net.sf.mmm.client.ui.api.attribute.AttributeReadAccessKey#getAccessKey() access
   *        key}.
   * @param widget is the {@link UiWidgetActive active widget} that has been bound to the access key.
   */
  void bindAccessKey(char key, UiWidgetActive widget);

  /**
   * This method unregisters the given <code>key</code> for the given <code>widget</code>.
   * 
   * @param key is the {@link net.sf.mmm.client.ui.api.attribute.AttributeReadAccessKey#getAccessKey() access
   *        key}.
   * @param widget is the {@link UiWidgetActive active widget} that has been unbound from the access key.
   * @return <code>true</code> if the access key has been unbound successfully, <code>false</code> otherwise
   *         (it was not bound before).
   */
  boolean unbindAccessKey(char key, UiWidgetActive widget);

  /**
   * This method simulates the effect of pressing the access key programmatically. It may be used for native
   * toolkits that do not support access keys as well as for testing.
   * 
   * @param key is the {@link net.sf.mmm.client.ui.api.attribute.AttributeReadAccessKey#getAccessKey() access
   *        key}.
   * @return <code>true</code> if the access key has been invoked successfully (as it was bound to a widget
   *         currently visible), <code>false</code> otherwise.
   */
  boolean invokeAccessKey(char key);

  /**
   * This method verifies that there are no conflicts with multiple bindings of the same access key.<br/>
   * <b>ATTENTION:</b><br/>
   * It is totally legal to create multiple widgets with the same access key. However, there shall never be
   * two widgets visible on the screen at the same time with the same access key. This is what this method
   * verifies for the moment in time when it is called.
   */
  void verifyUniqueAccessKeys();

}
