/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This is the implementation of the {@link UiBorderPanel} using swing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public class UiBorderPanelImpl<CHILD extends AbstractUiElement<? extends JComponent>> extends
    AbstractUiSingleCompositeSwing<JPanel, CHILD> implements UiBorderPanel<CHILD> {

  /** @see #getTitle() */
  private final TitledBorder border;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   */
  public UiBorderPanelImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JPanel(new GridLayout(1, 1)));
    this.border = BorderFactory.createTitledBorder("");
    setBorderJustification();
    getDelegate().setBorder(this.border);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * This method sets the justification of the border according to the
   * script-orientation.
   */
  public void setBorderJustification() {

    if (getFactory().getScriptOrientation().isLeftToRight()) {
      this.border.setTitleJustification(TitledBorder.LEFT);
    } else {
      this.border.setTitleJustification(TitledBorder.RIGHT);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.border.getTitle();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    this.border.setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(CHILD child) {

    super.setChild(child);
    JPanel delegate = getDelegate();
    delegate.removeAll();
    delegate.add(child.getAdapter().getDelegate());
  }

}
