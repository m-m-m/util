/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Dialog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractSyncDialogAccess<DELEGATE extends Dialog> extends
    AbstractSyncObjectAccess<Dialog> {

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Dialog#setText(String)
   * text} of the dialog.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /** the text of this dialog */
  private String text;

  /** the synchronous access to the parent */
  private AbstractSyncControlAccess<? extends Control> parentAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the widget.
   */
  public AbstractSyncDialogAccess(UiFactorySwt uiFactory, UiNode node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.text = null;
  }

  /**
   * This method gets the parent shell of this dialog.
   * 
   * @return the parent shell or <code>null</code> if parent is NOT set.
   */
  public Shell getParent() {

    if (this.parentAccess == null) {
      return null;
    } else {
      return this.parentAccess.getDelegate().getShell();
    }
  }

  /**
   * This method sets the parent sync-access of the dialog. If the parent
   * {@link AbstractSyncControlAccess#getDelegate() exists}, it will be set as
   * parent of the dialog. Else if the control does NOT yet exist, the parent
   * will be set on {@link #create() creation}.
   * 
   * @param newParentAccess is the synchronous access to the new parent
   */
  public void setParentAccess(AbstractSyncControlAccess<? extends Control> newParentAccess) {

    if (getDelegate() == null) {
      this.parentAccess = newParentAccess;
      if (this.parentAccess.getDelegate() != null) {
        assert (checkReady());
        create();
      }
    } else {
      // parent of dialog can not be changed!
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_TEXT) {
      getDelegate().setText(this.text);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    if (this.text != null) {
      getDelegate().setText(this.text);
    }
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Dialog#getText() text}
   * of the dialog.
   * 
   * @return the text of this dialog or <code>null</code> if no text is set.
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Dialog#setText(String)
   * text} of the dialog.
   * 
   * @param dialogText is the text to set.
   */
  public void setText(String dialogText) {

    assert (checkReady());
    this.text = dialogText;
    invoke(OPERATION_SET_TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public Dialog getToplevelDelegate() {

    return getDelegate();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return true;
  }

}
