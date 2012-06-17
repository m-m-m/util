/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTabbedPanelImpl<CHILD extends AbstractUiElement<? extends JComponent>> extends
    AbstractUiMultiCompositeSwing<JTabbedPane, CHILD> implements UiTabPanel<CHILD> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiTabbedPanelImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JTabbedPane());
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(CHILD component, String title) {

    getDelegate().add(title, component.getAdapter().getDelegate());
    doAddChild(component);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(CHILD component, String title, int position) {

    getDelegate().insertTab(title, null, component.getAdapter().getDelegate(), null, position);
    doInsertChild(component, position);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD removeChild(int position) {

    CHILD c = super.removeChild(position);
    getDelegate().remove(position);
    return c;
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
  public Orientation getOrientation() {

    return Orientation.HORIZONTAL;
  }

}
