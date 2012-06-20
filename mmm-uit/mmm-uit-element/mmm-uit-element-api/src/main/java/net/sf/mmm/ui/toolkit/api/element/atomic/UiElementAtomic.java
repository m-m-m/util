/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.element.atomic;

import net.sf.mmm.ui.toolkit.api.element.UiElement;
import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since X 03.05.2012
 */
public abstract class UiElementAtomic<VALUE, VIEW extends UiWidget<?>> extends UiElement<VALUE, VIEW> {

  /**
   * The constructor.
   * 
   * @param id
   */
  public UiElementAtomic(String id) {

    super(id);
  }

}
