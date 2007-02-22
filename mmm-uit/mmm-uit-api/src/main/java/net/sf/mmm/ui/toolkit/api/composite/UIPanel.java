/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.state.UIWriteOrientation;

/**
 * This is the interface for a panel. A panel is a composite UI component that
 * can aggregate multiple other UI components. <br>
 * The aim of this approach is as follows:
 * <ul>
 * <li>there is only one generic layoutmanager</li>
 * <li>it should look and behave the same for all of the (graphical) UI
 * implemenetations (esp. Swing and SWT)</li>
 * <li>it is designed to be simple to use and to understand</li>
 * <li>it is designed to satisfy the needs of predefined layouts (that you
 * design manually) as well as dynamic layouts (where components can be added
 * and removed dynamically during runtime)</li>
 * </ul>
 * Of course there are compromises to make! The concept is as follows:
 * <ul>
 * <li>the layout can order the components only horizontal or vertial (for
 * table structures with multiple columns and rows, multiple horizontal layouted
 * panels have to be placed in a vertical layouted one (or vice versa)).</li>
 * <li>all components build a linear list, where horizontal layout works from
 * the left to the right and vertical layout from top to bottom.</li>
 * <li>for each component it can be specified, if the height and/or weight of
 * the component is maximized.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIPanel extends UIComposite, UIWriteOrientation {

  /** the type of this object */
  String TYPE = "Panel";

  /**
   * This method adds the given component to the end of the panels component
   * list. If the panel has horizontal layout, the component will appear on the
   * right. If the panel has vertical layout, the component will appear at the
   * bottom. It will use {@link LayoutConstraints#DEFAULT} as constraints.
   * 
   * @param component
   *        is the component to add. The given component instance must be
   *        created by the same factory.
   */
  void addComponent(UIComponent component);

  /**
   * This method adds the given component to the end of the panels component
   * list. If the panel has horizontal layout, the component will appear on the
   * right. If the panel has vertical layout, the component will appear at the
   * bottom.
   * 
   * @param component
   *        is the component to add. The given component instance must be
   *        created by the same factory.
   * @param constraints
   *        are the constraints that define the layout of the component in this
   *        panel.
   */
  void addComponent(UIComponent component, LayoutConstraints constraints);

  /**
   * This method adds the given component at the given position.
   * 
   * @param component
   *        is the component to add. The given component instance must be
   *        created by the same factory.
   * @param constraints
   *        are the constraints that define the layout of the component in this
   *        panel.
   * @param position
   *        is the index position where the given component will be inserted.
   */
  void addComponent(UIComponent component, LayoutConstraints constraints, int position);

  /**
   * This method removes the given component from this panel.
   * 
   * @param component
   *        is the component to remove.
   */
  void removeComponent(UIComponent component);

  /**
   * This method removes the component at the given index from this panel.
   * 
   * @param index
   *        is the position of the component to remove.
   */
  void removeComponent(int index);

}
