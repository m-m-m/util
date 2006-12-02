/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;

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

  /** the synchron access to the {@link org.eclipse.swt.widgets.Composite} */
  private final SyncCompositeAccess syncAccess;

  /** the layout-manager */
  private final DecoratingLayoutManager layoutManager;
  
  /** @see #getComponent() */
  private C component;

  /** @see #getDecorator() */
  private D decorator;

  /**
   * @param uiFactory
   * @param parentObject
   * @param borderTitle
   */
  public UIDecoratedComponentImpl(UIFactorySwt uiFactory, UISwtNode parentObject, String borderTitle) {

    super(uiFactory, parentObject, borderTitle);
    this.layoutManager = new DecoratingLayoutManager(this);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
    this.syncAccess.setLayout(this.layoutManager);
    this.component = null;
    this.decorator = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.composite.AbstractUIComposite#getActiveSyncAccess()
   */
  @Override
  public AbstractSyncCompositeAccess getActiveSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.composite.AbstractUIComposite#getOrientation()
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
   * @see net.sf.mmm.ui.toolkit.impl.swt.composite.AbstractUIComposite#getComponent(int)
   */
  @Override
  public AbstractUIComponent getComponent(int index) {

    if (index == 0) {
      return (AbstractUIComponent) getDecorator();
    } else if (index == 1) {
      return (AbstractUIComponent) getComponent();
    } else {
      throw new IndexOutOfBoundsException("Illegal index (" + index + ") must be 0 or 1!");
    }
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setComponent(C newComponent) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newComponent;
    if (this.component != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.component;
      oldComponent.removeFromParent();
    }
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.component = newComponent;
    abstractComponent.getSyncAccess().setParentAccess(this.syncAccess);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setDecorator(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setDecorator(D newDecorator) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newDecorator;
    if (this.decorator != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.decorator;
      oldComponent.removeFromParent();
    }
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.decorator = newDecorator;
    abstractComponent.getSyncAccess().setParentAccess(this.syncAccess);
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
