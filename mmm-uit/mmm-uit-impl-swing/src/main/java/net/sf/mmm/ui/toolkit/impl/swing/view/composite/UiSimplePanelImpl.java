/* $Id: AbstractUIPanel.java 971 2011-02-28 21:55:00Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This class is the implementation of {@link UiSimplePanel} using swing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public class UiSimplePanelImpl<CHILD extends AbstractUiElement<? extends JComponent>> extends
    AbstractUiMultiCompositeSwing<JPanel, CHILD> implements UiSimplePanel<CHILD> {

  /** @see #getOrientation() */
  private Orientation orientation;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the {@link #getOrientation() orientation}.
   */
  public UiSimplePanelImpl(UiFactorySwing uiFactory, Orientation orientation) {

    super(uiFactory, new JPanel());
    // TODO: layout manager!
    this.orientation = orientation;
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(CHILD component) {

    JPanel delegate = getDelegate();
    JComponent toplevelDelegate = (JComponent) component.getAdapter().getToplevelDelegate();
    delegate.add(toplevelDelegate);
    doAddChild(component);
    delegate.updateUI();
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(CHILD component, int index) {

    JPanel delegate = getDelegate();
    delegate.add(component.getAdapter().getDelegate(), index);
    doInsertChild(component, index);
    delegate.updateUI();
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
