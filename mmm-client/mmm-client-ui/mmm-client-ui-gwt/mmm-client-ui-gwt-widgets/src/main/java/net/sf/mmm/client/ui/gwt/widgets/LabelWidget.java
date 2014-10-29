/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.util.gwt.api.GwtUtil;

import com.google.gwt.aria.client.Id;
import com.google.gwt.aria.client.Property;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is what {@link com.google.gwt.user.client.ui.Label} should be but is not: A {@link Widget} for the
 * HTML element {@literal <label>}. To prevent name collisions we had to find a new name for this. <br>
 * <b>TODO:</b><br>
 * BiDi Support!
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LabelWidget extends AbstractWidget implements HasText {

  /**
   * The constructor.
   */
  public LabelWidget() {

    super();
    LabelElement labelElement = Document.get().createLabelElement();
    setElement(labelElement);
  }

  /**
   * The constructor.
   * 
   * @param text is the (initial) {@link #getText() text} of this label.
   */
  public LabelWidget(String text) {

    this();
    setText(text);
  }

  /**
   * @return the {@link LabelElement}.
   */
  protected LabelElement getLabelElement() {

    return getElement().cast();
  }

  /**
   * This method sets the {@link Widget} labelled by this {@link LabelWidget}. You should always associate the
   * input widget with a {@link LabelWidget} via this method for advanced usability and
   * {@link net.sf.mmm.util.lang.api.concern.Accessibility}. <br>
   * <b>ATTENTION:</b><br>
   * This method creates a bidirectional connection of this {@link LabelWidget} and the given
   * <code>labelledWidget</code> via {@link String} attributes pointing to each others IDs. If an ID is
   * changed afterwards this connection will break. If you are using this classes directly and not via
   * <code>UiWidgets</code> you have to take care of this and re-invoke this method in case an ID changes.
   * 
   * @see LabelElement#setHtmlFor(String)
   * 
   * @param labelledWidget is the {@link Widget} labelled by this {@link LabelWidget}.
   */
  public void setLabelledWidget(Widget labelledWidget) {

    GwtUtil gwtUtil = GwtUtil.getInstance();
    String widgetId = gwtUtil.getId(labelledWidget);
    getLabelElement().setHtmlFor(widgetId);
    String labelId = gwtUtil.getId(this);
    Property.LABELLEDBY.set(labelledWidget.getElement(), Id.of(labelId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getText() {

    return getElement().getInnerText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setText(String text) {

    // TODO DirectionEstimator/BiDi Support?
    getElement().setInnerText(text);
  }

}
