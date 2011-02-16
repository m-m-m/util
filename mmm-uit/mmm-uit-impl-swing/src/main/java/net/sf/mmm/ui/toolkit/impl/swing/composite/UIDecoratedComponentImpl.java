/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent} interface
 * using Swing as the UI toolkit.
 * 
 * @param <D> is the templated type of the
 *        {@link #getDecorator() decorating component}.
 * @param <C> is the templated type of the
 *        {@link #getComponent() main component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDecoratedComponentImpl<D extends UIComponent, C extends UIComponent> extends
    AbstractUIComposite implements UIDecoratedComponent<D, C> {

  /** @see #getSwingComponent() */
  private final JPanel panel;

  /** the layout-manager */
  private final DecoratingLayoutManager layoutManager;

  /** @see #getComponent() */
  private C component;

  /** @see #getDecorator() */
  private D decorator;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UIDecoratedComponentImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.layoutManager = new DecoratingLayoutManager(this);
    this.panel = new JPanel(this.layoutManager);
    this.component = null;
    this.decorator = null;
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
  @Override
  public Orientation getOrientation() {

    Orientation orientation = Orientation.HORIZONTAL;
    UINode parent = getParent();
    if ((parent != null) && (parent instanceof UIComposite)) {
      orientation = ((UIComposite) parent).getOrientation().getMirrored();
    }
    return orientation;
  }

  /**
   * {@inheritDoc}
   */
  public C getComponent() {

    return this.component;
  }

  /**
   * {@inheritDoc}
   */
  public D getDecorator() {

    return this.decorator;
  }

  /**
   * {@inheritDoc}
   */
  public UIComponent getComponent(int index) {

    if (index == 0) {
      return getDecorator();
    } else if (index == 1) {
      return getComponent();
    } else {
      throw new IndexOutOfBoundsException("Illegal index (" + index + ") must be 0 or 1!");
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setDecorator(D newDecorator) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newDecorator;
    if (this.decorator != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.decorator;
      // setParent(oldComponent, null);
      // this.panel.remove(oldComponent.getSwingComponent());
      oldComponent.removeFromParent();
    }
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.decorator = newDecorator;
    JComponent swingComponent = abstractComponent.getSwingComponent();
    this.panel.add(swingComponent);
  }

  /**
   * This method sets the sizer used to override the size of the
   * {@link #getDecorator() decorator}.
   * 
   * @param sizer is the sizer to use or <code>null</code> to disable.
   */
  public void setDecoratorSizer(UiReadSize sizer) {

    this.layoutManager.setSizer(sizer);
  }

  /**
   * {@inheritDoc}
   */
  public void setComponent(C newComponent) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newComponent;
    if (this.component != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.component;
      setParent(oldComponent, null);
      this.panel.remove(oldComponent.getSwingComponent());
    }
    this.component = newComponent;
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.component = newComponent;
    JComponent swingComponent = abstractComponent.getSwingComponent();
    this.panel.add(swingComponent);
  }

  /**
   * {@inheritDoc}
   */
  public int getComponentCount() {

    return 2;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

}
