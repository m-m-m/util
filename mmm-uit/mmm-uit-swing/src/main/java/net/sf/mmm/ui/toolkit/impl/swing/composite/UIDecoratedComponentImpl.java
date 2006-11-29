/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;


import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.state.UIReadSize;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent} interface
 * using Swing as the UI toolkit.
 * 
 * @param <D>
 *        is the templated type of the
 *        {@link #getDecorator() decorating component}.
 * @param <C>
 *        is the templated type of the {@link #getComponent() main component}.
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
   * @param uiFactory
   * @param parentObject
   */
  public UIDecoratedComponentImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.layoutManager = new DecoratingLayoutManager(this);
    this.panel = new JPanel(this.layoutManager);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.panel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.composite.AbstractUIComposite#getOrientation()
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#getComponent()
   */
  public C getComponent() {

    return this.component;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#getDecorator()
   */
  public D getDecorator() {

    return this.decorator;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setDecorator(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setDecorator(D newDecorator) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newDecorator;
    if (this.decorator != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.decorator;
      setParent(oldComponent, null);
      this.panel.remove(oldComponent.getSwingComponent());
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
   * @param sizer
   *        is the sizer to use or <code>null</code> to disable.
   */
  public void setDecoratorSizer(UIReadSize sizer) {

    this.layoutManager.setSizer(sizer);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponentCount()
   */
  public int getComponentCount() {

    return 2;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

}
