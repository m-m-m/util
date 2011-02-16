/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteOrientation;

/**
 * This is the interface for a split panel. Such component is a special
 * composite that is split eigther horizontal or vertical. Therefore two
 * components can be added to this split panel. They appear eigther side-by-side
 * (if the
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation#getOrientation() orientation}
 * is {@link net.sf.mmm.ui.toolkit.api.composite.Orientation#HORIZONTAL}) or
 * top-to-bottom (if the
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation#getOrientation() orientation}
 * is {@link net.sf.mmm.ui.toolkit.api.composite.Orientation#VERTICAL}).<br>
 * The two components are separated by a split-bar that is orthogonal to the
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation#getOrientation() orientation}.
 * The user can change the position of the split-bar (increasing the size of the
 * one component and decreasing the size of the other) along the axis of the
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation#getOrientation() orientation}
 * if the split-panel is
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadEnabled#isEnabled() enabled}.<br>
 * It is undefined what will happen if you make the split panel visible before
 * you set the two components in the splitted slots of this panel.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UISplitPanel extends UIComposite, UiWriteOrientation {

  /** the type of this object */
  String TYPE = "SplitPanel";

  /**
   * This method sets the component placed to the top (horizontal orientation)
   * or the left (vertical orientation).
   * 
   * @param component is the component to add.
   */
  void setTopOrLeftComponent(UiElement component);

  /**
   * This method gets the component placed to the top (horizontal orientation)
   * or the left (vertical orientation).
   * 
   * @return the top or left component or <code>null</code> if the component
   *         has not been set.
   */
  UiElement getTopOrLeftComponent();

  /**
   * This method sets the component placed to the bottom (horizontal
   * orientation) or the right (vertical orientation).
   * 
   * @param component is the component to add.
   */
  void setBottomOrRightComponent(UiElement component);

  /**
   * This method gets the component placed to the bottom (horizontal
   * orientation) or the right (vertical orientation).
   * 
   * @return the bottom or right component or <code>null</code> if the
   *         component has not been set.
   */
  UiElement getBottomOrRightComponent();

  /**
   * This method sets the position of the divider used to split the panel.
   * 
   * @param proportion is a value in the range of 0.0 to 1.0. A value of 0.0
   *        will set the position of the divider to the top or left, a value of
   *        1.0 will set it to the bottom or right, a value of 0.5 will center
   *        the divider, etc.
   */
  void setDividerPosition(double proportion);

}
