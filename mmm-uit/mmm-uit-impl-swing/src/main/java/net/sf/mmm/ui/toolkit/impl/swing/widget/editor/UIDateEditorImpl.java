/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget.editor;

import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;

import com.toedter.calendar.JDateChooser;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.widget.AbstractUIWidget;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIDateEditorImpl extends AbstractUIWidget implements UiDateBox {

  /** the date chooser */
  private final JDateChooser dateChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIDateEditorImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
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
