/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Container;

import javax.swing.JDialog;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This class is the implementation of the UIDialog interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDialogImpl extends AbstractUiWindowSwing<JDialog> implements UiDialog {

  /** @see #getAdapter() */
  private final UiWindowAdapterSwingDialog adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parent is the parent of this object (may be <code>null</code>).
   * @param jDialog is the swing dialog to wrap.
   */
  public UiDialogImpl(UiFactorySwing uiFactory, UiWindow parent, JDialog jDialog) {

    super(uiFactory);
    this.adapter = new UiWindowAdapterSwingDialog(this, jDialog);
    setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWindowAdapterSwingDialog getAdapter() {

    return this.adapter;
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
  public boolean isModal() {

    return getDelegate().isModal();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    Container topDelegate = (Container) ((AbstractUiElement) newComposite).getAdapter()
        .getToplevelDelegate();
    getDelegate().setContentPane(topDelegate);
    doSetComposite(newComposite);
  }

}
