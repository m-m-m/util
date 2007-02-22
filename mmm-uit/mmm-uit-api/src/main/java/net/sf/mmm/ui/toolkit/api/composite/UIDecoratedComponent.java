/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

import net.sf.mmm.ui.toolkit.api.UIComponent;

/**
 * This is the interface for a simple {@link UIComposite composite} containing
 * two {@link UIComponent components}. The
 * {@link #getDecorator() first component} will be layed out with a
 * {@link Filling#NONE fixed} size on the top/left, the
 * {@link #getComponent() other component} will be {@link Filling#BOTH scaled}
 * and located at the bottom/right.<br>
 * The intention of this composite is to simplify the layout management.
 * 
 * @param <D>
 *        is the templated type of the
 *        {@link #getDecorator() decorating component}.
 * @param <C>
 *        is the templated type of the {@link #getComponent() main component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIDecoratedComponent<D extends UIComponent, C extends UIComponent> extends
    UIComposite {

  /** the type of this object */
  String TYPE = "DecoratedComponent";

  /**
   * This method gets the decorating component (e.g. a
   * {@link net.sf.mmm.ui.toolkit.api.widget.UILabel label}) that is layed out
   * with a {@link Filling#NONE fixed} size on the top/left.
   * 
   * @return the decorating component.
   */
  D getDecorator();

  /**
   * This method gets the main component (e.g. a
   * {@link net.sf.mmm.ui.toolkit.api.widget.UITextField text-field} that will
   * be {@link Filling#BOTH scaled} and located at the bottom/right.
   * 
   * @return the main component.
   */
  C getComponent();

  /**
   * This method sets the {@link #getDecorator() decorator}.
   * 
   * @param newDecorator is the new decorator to set.
   */
  void setDecorator(D newDecorator);

  /**
   * This method sets the {@link #getComponent() main component}.
   * 
   * @param newComponent is the new component to set.
   */
  void setComponent(C newComponent);
  
}
