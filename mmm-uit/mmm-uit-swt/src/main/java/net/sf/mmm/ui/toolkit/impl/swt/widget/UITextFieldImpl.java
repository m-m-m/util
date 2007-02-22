/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTextAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITextField} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITextFieldImpl extends AbstractUIWidget implements UITextField {

  /** the synchron access to the text-field */
  private final SyncTextAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITextFieldImpl(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject);
    int style = SWT.DEFAULT;
    this.syncAccess = new SyncTextAccess(uiFactory, style);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable()
   */
  public boolean isEditable() {
  
    return this.syncAccess.isEditable();
  }
  
  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#setEditable(boolean)
   */
  public void setEditable(boolean editableFlag) {
  
    this.syncAccess.setEditable(editableFlag);
  }
  
}
