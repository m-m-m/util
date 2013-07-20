/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the default implementation of {@link UiAccessKeyBinding}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiAccessKeyBindingImpl implements UiAccessKeyBinding {

  /** @see #bindAccessKey(char, UiWidgetActive) */
  private Map<Character, Collection<AbstractUiWidgetActive<?, ?>>> accessKey2WidgetsMap;

  /**
   * The constructor.
   */
  public UiAccessKeyBindingImpl() {

    super();
    this.accessKey2WidgetsMap = new HashMap<Character, Collection<AbstractUiWidgetActive<?, ?>>>();
  }

  /**
   * Gets or creates the {@link Collection} with the widgets for the given <code>accessKey</code>.
   * 
   * @param accessKey is the {@link net.sf.mmm.client.ui.api.attribute.AttributeReadAccessKey#getAccessKey()
   *        access key}.
   * @return the requested {@link Collection}.
   */
  private Collection<AbstractUiWidgetActive<?, ?>> requireWidgetsForAccessKey(char accessKey) {

    Character key = Character.valueOf(accessKey);
    Collection<AbstractUiWidgetActive<?, ?>> result = this.accessKey2WidgetsMap.get(key);
    if (result == null) {
      result = new LinkedList<AbstractUiWidgetActive<?, ?>>();
      this.accessKey2WidgetsMap.put(key, result);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void bindAccessKey(char key, UiWidgetActive widget) {

    Collection<AbstractUiWidgetActive<?, ?>> widgets = requireWidgetsForAccessKey(key);
    widgets.add((AbstractUiWidgetActive<?, ?>) widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean unbindAccessKey(char key, UiWidgetActive widget) {

    Collection<AbstractUiWidgetActive<?, ?>> widgets = this.accessKey2WidgetsMap.get(Character.valueOf(key));
    if (widgets != null) {
      return widgets.remove(widget);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean invokeAccessKey(char key) {

    Collection<AbstractUiWidgetActive<?, ?>> widgets = this.accessKey2WidgetsMap.get(Character.valueOf(key));
    boolean invoked = false;
    if (widgets != null) {
      for (AbstractUiWidgetActive<?, ?> widget : widgets) {
        if (widget.isVisibleRecursive()) {
          if (invoked) {
            assert false : "Duplicate access key " + key + " for " + widget;
          } else {
            widget.onAccessKeyPressed(true);
            invoked = true;
          }
        }
      }
    }
    return invoked;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void verifyUniqueAccessKeys() {

    for (Character key : this.accessKey2WidgetsMap.keySet()) {
      Collection<AbstractUiWidgetActive<?, ?>> widgets = this.accessKey2WidgetsMap.get(key);
      AbstractUiWidgetActive<?, ?> visibleWidget = null;
      for (AbstractUiWidgetActive<?, ?> widget : widgets) {
        if (widget.isVisibleRecursive()) {
          if (visibleWidget == null) {
            visibleWidget = widget;
          } else {
            throw new DuplicateObjectException(widget, key, visibleWidget);
          }
        }
      }
    }
  }

}
