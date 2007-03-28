/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITabbedPanelImpl extends AbstractUIPanel implements UITabbedPanel {

  /** the native swing component */
  private final JTabbedPane panel;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITabbedPanelImpl(UIFactorySwing uiFactory, AbstractUIComponent parentObject) {

    super(uiFactory, parentObject);
    this.panel = new JTabbedPane();
  }

  /**
   * {@inheritDoc}
   */
  public @Override
  JComponent getSwingComponent() {

    return this.panel;
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component) {
  
    addComponent(component, "Tab " + (getComponentCount() + 1));
  }
  
  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component, String title) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    this.panel.add(title, c.getSwingComponent());
    setParent(c, this);
    this.components.add(component);
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component, String title, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    this.panel.insertTab(title, null, c.getSwingComponent(), null, position);
    setParent(c, this);
    this.components.add(position, component);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent removeComponent(int position) {

    AbstractUIComponent c = super.removeComponent(position);
    this.panel.remove(position);
    return c;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

}
