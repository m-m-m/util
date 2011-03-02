/* $Id: AbstractUIPanel.java 971 2011-02-28 21:55:00Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This class is the implementation of {@link UiSimplePanel} using swing.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSimplePanelImpl<E extends AbstractUiElement> extends AbstractUiMultiComposite<E>
    implements UiSimplePanel<E> {

  /** @see #getOrientation() */
  private Orientation orientation;

  /** @see #getSwingComponent() */
  private final JPanel panel;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the {@link #getOrientation() orientation}.
   */
  public UiSimplePanelImpl(UIFactorySwing uiFactory, Orientation orientation) {

    super(uiFactory);
    this.orientation = orientation;
    this.panel = new JPanel();
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
  public void addChild(E component) {

    this.panel.add(component.getSwingComponent());
    doAddChild(component);
    this.panel.updateUI();
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E component, int index) {

    this.panel.add(component.getSwingComponent(), index);
    doInsertChild(component, index);
    this.panel.updateUI();
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return this.orientation;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

}
