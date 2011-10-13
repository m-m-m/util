/* $Id: UIDateEditorImpl.java 976 2011-03-02 21:36:59Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.util.Date;
import java.util.Locale;

import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

import com.toedter.calendar.JDateChooser;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDateEditorImpl extends AbstractUiWidgetSwing<JDateChooser> implements UiDateBox {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiDateEditorImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JDateChooser());
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(Date newDate) {

    getAdapter().getDelegate().setDate(newDate);
  }

  /**
   * {@inheritDoc}
   */
  public Date getValue() {

    return getAdapter().getDelegate().getDate();
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

    getAdapter().getDelegate().setLocale(newLocale);
  }

  /**
   * {@inheritDoc}
   */
  public Locale getLocale() {

    return getAdapter().getDelegate().getLocale();
  }

}
