/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.element.composite;

import net.sf.mmm.ui.toolkit.api.element.UiElement;
import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of the underlying {@link #getWidget() widget}.
 */
public abstract class UiElementComposite<VALUE, WIDGET extends UiWidget> extends UiElement<VALUE, WIDGET> {

  /**
   * The constructor.
   * 
   * @param id is the {@link #getId() ID}.
   */
  public UiElementComposite(String id) {

    super(id);
  }

}
