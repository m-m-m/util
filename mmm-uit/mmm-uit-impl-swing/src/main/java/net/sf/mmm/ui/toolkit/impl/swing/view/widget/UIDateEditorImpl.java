/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

import com.toedter.calendar.JDateChooser;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIDateEditorImpl extends AbstractUiWidget implements UiDateBox {

  /** the date chooser */
  private final JDateChooser dateChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UIDateEditorImpl(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.dateChooser = new JDateChooser();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.dateChooser;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(Date newDate) {

    this.dateChooser.setDate(newDate);
  }

  /**
   * {@inheritDoc}
   */
  public Date getValue() {

    return this.dateChooser.getDate();
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void setLocale(Locale newLocale) {

    this.dateChooser.setLocale(newLocale);
  }

  /**
   * {@inheritDoc}
   */
  public Locale getLocale() {

    return this.dateChooser.getLocale();
  }

}
