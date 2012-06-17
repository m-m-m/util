/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOrientation;
import net.sf.mmm.ui.toolkit.api.common.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a slice-panel. A panel is a composite UI component
 * that can aggregate multiple other UI components. <br>
 * The aim of this approach is as follows:
 * <ul>
 * <li>there is only one generic layout-manager</li>
 * <li>it should look and behave the same for all of the (graphical) UI
 * implementations (esp. Swing and SWT)</li>
 * <li>it is designed to be simple to use and to understand</li>
 * <li>it is designed to satisfy the needs of predefined layouts (that you
 * design manually) as well as dynamic layouts (where components can be added
 * and removed dynamically during runtime)</li>
 * </ul>
 * Of course there are compromises to make! The concept is as follows:
 * <ul>
 * <li>the layout can order the components only horizontal or vertical (for
 * table structures with multiple columns and rows, multiple horizontal
 * slice-panels have to be placed in a vertical one (or vice versa)).</li>
 * <li>all components build a linear list, where horizontal layout works from
 * the left to the right and vertical layout from top to bottom.</li>
 * <li>for each component it can be specified, if the height and/or weight of
 * the component is maximized.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 * @deprecated - use dedicated panels instead.
 */
@Deprecated
public interface UiSlicePanel<CHILD extends UiElement> extends UiExtendableComposite<CHILD>, AttributeWriteOrientation {

  /** the type of this object */
  String TYPE = "Panel";

  /**
   * {@inheritDoc}
   * 
   * If the panel has horizontal layout, the component will appear on the right.
   * If the panel has vertical layout, the component will appear at the bottom.
   * It will use {@link LayoutConstraints#DEFAULT} as constraints.
   */
  void addChild(CHILD component);

  /**
   * This method adds the given component to the end of the panels component
   * list. If the panel has horizontal layout, the component will appear on the
   * right. If the panel has vertical layout, the component will appear at the
   * bottom.
   * 
   * @param component is the component to add. The given component instance must
   *        be created by the same factory.
   * @param constraints are the constraints that define the layout of the
   *        component in this panel.
   */
  void addChild(CHILD component, LayoutConstraints constraints);

  /**
   * This method adds the given component at the given position.
   * 
   * @param component is the component to add. The given component instance must
   *        be created by the same factory.
   * @param constraints are the constraints that define the layout of the
   *        component in this panel.
   * @param position is the index position where the given component will be
   *        inserted.
   */
  void insertChild(CHILD component, LayoutConstraints constraints, int position);

}
