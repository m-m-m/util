/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadOrientation;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a simple {@link UiComposite composite} containing two {@link UiElement
 * components}. The {@link #getDecorator() first component} will be layouted with a
 * {@link net.sf.mmm.ui.toolkit.api.common.Filling#NONE fixed} size on the top/left, the
 * {@link #getComponent() other component} will be {@link net.sf.mmm.ui.toolkit.api.common.Filling#BOTH
 * scaled} and located at the bottom/right.<br>
 * The intention of this composite is to simplify the layout management.
 * 
 * @param <D> is the templated type of the {@link #getDecorator() decorating component}.
 * @param <C> is the templated type of the {@link #getComponent() main component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 * @deprecated - nonsense, remove!
 */
@Deprecated
public interface UiDecoratedComponent<CHILD extends UiElement, D extends CHILD, C extends CHILD> extends
    UiComposite<CHILD>, AttributeReadOrientation {

  /** the type of this object */
  String TYPE = "DecoratedComponent";

  /**
   * This method gets the decorating component (e.g. a {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel
   * label}) that is layed out with a {@link net.sf.mmm.ui.toolkit.api.common.Filling#NONE fixed} size on the
   * top/left.
   * 
   * @return the decorating component.
   */
  D getDecorator();

  /**
   * This method gets the main component (e.g. a {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTextField
   * text-field} that will be {@link net.sf.mmm.ui.toolkit.api.common.Filling#BOTH scaled} and located at the
   * bottom/right.
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
