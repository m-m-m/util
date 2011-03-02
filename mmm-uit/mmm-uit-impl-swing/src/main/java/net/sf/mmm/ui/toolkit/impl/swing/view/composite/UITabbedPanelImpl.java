/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITabbedPanelImpl<E extends AbstractUiElement> extends AbstractUiMultiComposite<E>
    implements UiTabPanel<E> {

  /** the native swing component */
  private final JTabbedPane panel;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UITabbedPanelImpl(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.panel = new JTabbedPane();
    initialize();
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
  public void addChild(E component, String title) {

    this.panel.add(title, component.getSwingComponent());
    doAddChild(component);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E component, String title, int position) {

    this.panel.insertTab(title, null, component.getSwingComponent(), null, position);
    doInsertChild(component, position);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiElement removeChild(int position) {

    AbstractUiElement c = super.removeChild(position);
    this.panel.remove(position);
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
