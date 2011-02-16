/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadBorderTitle;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation;

/**
 * This is the interface of a UI component that contains other UI components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiComposite extends UiElement, UiReadBorderTitle, UiReadOrientation {

  /**
   * This method gets the number of sub-components in this composite component.
   * 
   * @return the component count.
   */
  int getComponentCount();

  /**
   * This method gets the sub-component at the given index.
   * 
   * @param index is the position of the requested sub-component.
   * @return the component at the given index.
   */
  UiElement getComponent(int index);

}
