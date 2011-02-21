/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITabbedPanelImpl extends AbstractUIPanel implements UiTabPanel<UiElement> {

  /** the native swing component */
  private final JTabbedPane panel;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UITabbedPanelImpl(UIFactorySwing uiFactory, AbstractUiElement parentObject) {

    super(uiFactory, parentObject);
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
  public void addChild(UiElement component) {

    addChild(component, "Tab " + (getChildCount() + 1));
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(UiElement component, String title) {

    AbstractUiElement c = (AbstractUiElement) component;
    this.panel.add(title, c.getSwingComponent());
    setParent(c, this);
    doAddComponent(component);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(UiElement component, String title, int position) {

    AbstractUiElement c = (AbstractUiElement) component;
    this.panel.insertTab(title, null, c.getSwingComponent(), null, position);
    setParent(c, this);
    doAddComponent(position, component);
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
  public void insertChild(UiElement component, int index) {

    throw new IllegalStateException();
  }

}
