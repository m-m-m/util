/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the implementation of the {@link UiBorderPanel} using swing.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiBorderPanelImpl<E extends AbstractUiElement> extends AbstractUiSingleComposite<E>
    implements UiBorderPanel<E> {

  /** @see #getSwingComponent() */
  private final JPanel panel;

  /** @see #getTitle() */
  private final TitledBorder border;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the {@link #getParent() parent} of this object (may
   *        be <code>null</code>).
   */
  public UiBorderPanelImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.panel = new JPanel(new GridLayout(1, 1));
    this.border = BorderFactory.createTitledBorder("");
    setBorderJustification();
    this.panel.setBorder(this.border);
    initialize();
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
  @Override
  public JComponent getSwingComponent() {

    return this.panel;
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
  public void setChild(E child) {

    super.setChild(child);
    this.panel.removeAll();
    this.panel.add(child.getSwingComponent());
  }

}
