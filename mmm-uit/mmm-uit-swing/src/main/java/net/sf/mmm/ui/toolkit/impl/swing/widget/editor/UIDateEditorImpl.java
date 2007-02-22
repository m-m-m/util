/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget.editor;

import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;

import com.toedter.calendar.JDateChooser;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.widget.AbstractUIWidget;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor} interface
 * using Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditorImpl extends AbstractUIWidget implements UIDateEditor {

  /** the date chooser */
  private final JDateChooser dateChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIDateEditorImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.dateChooser = new JDateChooser();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.dateChooser;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor#setDate(java.util.Date)
   */
  public void setDate(Date newDate) {

    this.dateChooser.setDate(newDate);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor#getDate()
   */
  public Date getDate() {

    return this.dateChooser.getDate();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteLocale#setLocale(java.util.Locale)
   */
  public void setLocale(Locale newLocale) {

    this.dateChooser.setLocale(newLocale);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadLocale#getLocale()
   */
  public Locale getLocale() {

    return this.dateChooser.getLocale();
  }

}
