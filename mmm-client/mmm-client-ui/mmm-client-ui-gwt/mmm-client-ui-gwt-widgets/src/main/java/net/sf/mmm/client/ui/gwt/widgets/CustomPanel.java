/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base class for a custom {@link ComplexPanel panel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class CustomPanel extends ComplexPanel implements InsertPanel.ForIsWidget {

  /**
   * The constructor.
   */
  public CustomPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Widget w) {

    add(w, (Element) getElement());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void insert(Widget widget, int beforeIndex) {

    insert(widget, (Element) getElement(), beforeIndex, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void insert(IsWidget widget, int beforeIndex) {

    insert(asWidgetOrNull(widget), beforeIndex);
  }

}
