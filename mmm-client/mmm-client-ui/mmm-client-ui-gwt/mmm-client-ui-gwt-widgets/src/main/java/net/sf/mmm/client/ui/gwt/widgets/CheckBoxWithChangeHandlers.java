/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * This class extends {@link CheckBox} to implement {@link HasChangeHandlers}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CheckBoxWithChangeHandlers extends CheckBox implements HasChangeHandlers {

  /**
   * The constructor.
   */
  public CheckBoxWithChangeHandlers() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setHTML(SafeHtml) HTML label}.
   */
  public CheckBoxWithChangeHandlers(SafeHtml label) {

    super(label);
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setText(String) text label}.
   */
  public CheckBoxWithChangeHandlers(String label) {

    super(label);
  }

  /**
   * The constructor.
   * 
   * @param element is the {@link #setElement(Element) label element}.
   */
  public CheckBoxWithChangeHandlers(Element element) {

    super(element);
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setHTML(SafeHtml) HTML label}.
   * @param dir the {@link Direction}.
   */
  public CheckBoxWithChangeHandlers(SafeHtml label, Direction dir) {

    super(label, dir);
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setHTML(SafeHtml) HTML label}.
   * @param directionEstimator the {@link #setDirectionEstimator(DirectionEstimator) direction estimator}.
   */
  public CheckBoxWithChangeHandlers(SafeHtml label, DirectionEstimator directionEstimator) {

    super(label, directionEstimator);
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setText(String) text label}.
   * @param dir the {@link Direction}.
   */
  public CheckBoxWithChangeHandlers(String label, Direction dir) {

    super(label, dir);
  }

  /**
   * The constructor.
   * 
   * @param label the {@link #setText(String) text label}.
   * @param directionEstimator the {@link #setDirectionEstimator(DirectionEstimator) direction estimator}.
   */
  public CheckBoxWithChangeHandlers(String label, DirectionEstimator directionEstimator) {

    super(label, directionEstimator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {

    return addDomHandler(handler, ChangeEvent.getType());
  }

}
