/* $Id$ */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.state.UIReadBorderTitle;
import net.sf.mmm.ui.toolkit.api.state.UIReadOrientation;

/**
 * This is the interface of a UI component that contains other UI components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIComposite extends UIComponent, UIReadBorderTitle, UIReadOrientation {

  /**
   * This method gets the number of sub-components in this composite component.
   * 
   * @return the component count.
   */
  int getComponentCount();

  /**
   * This method gets the sub-component at the given index.
   * 
   * @param index
   *        is the position of the requested sub-component.
   * @return the component at the given index.
   */
  UIComponent getComponent(int index);

}
