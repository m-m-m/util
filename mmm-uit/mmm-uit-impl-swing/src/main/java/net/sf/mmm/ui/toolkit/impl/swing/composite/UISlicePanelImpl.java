/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.types.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.view.composite.UiDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UISlicePanelImpl extends AbstractUIPanel implements UiSlicePanel {

  /** the swing panel */
  private final JPanel panel;

  /** the layout manager */
  private final LayoutManager layout;

  /** the button group used for radio-buttons */
  private ButtonGroup buttonGroup;

  /** the sizer for {@link UIDecoratedComponentImpl} */
  private DecoratorSizer sizer;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   * @param orientation is the orientation for the layout of the panel.
   */
  public UISlicePanelImpl(UIFactorySwing uiFactory, UiNode parentObject, Orientation orientation) {

    super(uiFactory, parentObject);
    this.layout = new LayoutManager(uiFactory);
    this.layout.setOrientation(orientation);
    this.panel = new JPanel(this.layout);
    this.buttonGroup = null;
    this.sizer = null;
    initialize();
  }

  /**
   * This method gets the button group for this panel.
   * 
   * @return the button group.
   */
  protected ButtonGroup getButtonGroup() {

    if (this.buttonGroup == null) {
      this.buttonGroup = new ButtonGroup();
    }
    return this.buttonGroup;
  }

  /**
   * This method gets the sizer used to adjust
   * {@link UIDecoratedComponentImpl#getDecorator() decorators} for this panel.
   * 
   * @return the sizer.
   */
  protected DecoratorSizer getSizer() {

    if (this.sizer == null) {
      this.sizer = new DecoratorSizer();
    }
    return this.sizer;
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
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUIComponent removeChild(int index) {

    // synchronized (this) {
    this.panel.remove(index);
    AbstractUIComponent component = super.removeChild(index);
    JComponent swingComponent = component.getSwingComponent();
    if (swingComponent instanceof JRadioButton) {
      getButtonGroup().remove((JRadioButton) swingComponent);
    }
    return component;
    // }
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(UiElement component) {

    addComponent(component, LayoutConstraints.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(UiElement component, int index) {

    addComponent(component, LayoutConstraints.DEFAULT, index);
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UiElement component, LayoutConstraints constraints) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    // synchronized (this) {
    JComponent swingComponent = c.getSwingComponent();
    this.panel.add(swingComponent, constraints);
    if (swingComponent instanceof JRadioButton) {
      getButtonGroup().add((JRadioButton) swingComponent);
    }
    if (c.getType() == UiDecoratedComponent.TYPE) {
      UIDecoratedComponentImpl decoratedComponent = (UIDecoratedComponentImpl) component;
      getSizer().add(decoratedComponent);
      decoratedComponent.setDecoratorSizer(getSizer());
    }
    doAddComponent(c);
    setParent(c, this);
    // }
    // this.panel.updateUI();
    if (this.panel.isVisible()) {
      refresh(UIRefreshEvent.DEFAULT);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UiElement component, LayoutConstraints constraints, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    // synchronized (this) {
    JComponent swingComponent = c.getSwingComponent();
    this.panel.add(swingComponent, constraints, position);
    if (swingComponent instanceof JRadioButton) {
      getButtonGroup().add((JRadioButton) swingComponent);
    }
    doAddComponent(position, c);
    setParent(c, this);
    // }
    this.panel.updateUI();
  }

  /**
   * {@inheritDoc}
   */
  public void setOrientation(Orientation orientation) {

    this.layout.setOrientation(orientation);
    this.layout.layoutContainer(this.panel);
    this.panel.revalidate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Orientation getOrientation() {

    return this.layout.getOrientation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    this.layout.refreshCachedData();
    this.panel.updateUI();
  }

  /**
   * This inner class is a sizer used to adjust the decorators.
   */
  private static final class DecoratorSizer implements UiReadSize {

    /** the list of sizes to build maximum of */
    private final List<UiDecoratedComponent> sizeList;

    /**
     * The constructor.
     */
    public DecoratorSizer() {

      super();
      this.sizeList = new ArrayList<UiDecoratedComponent>();
    }

    /**
     * This method adds the given {@link UiDecoratedComponent decorated
     * component} to the size-list. The maximum width/height will be determined
     * over all sized in the list.
     * 
     * @param size is the size to add.
     */
    public void add(UiDecoratedComponent size) {

      this.sizeList.add(size);
    }

    /**
     * This method removes the given {@link UiDecoratedComponent decorated
     * component} from the size-list.
     * 
     * @param size is the size to remove.
     */
    public void remove(UiDecoratedComponent size) {

      this.sizeList.remove(size);
    }

    /**
     * {@inheritDoc}
     */
    public int getHeight() {

      int height = 0;
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UiReadPreferredSize size = this.sizeList.get(i).getDecorator();
        if (size != null) {
          int currentHeight = size.getPreferredHeight();
          if (currentHeight > height) {
            height = currentHeight;
          }
        }
      }
      return height;
    }

    /**
     * {@inheritDoc}
     */
    public int getWidth() {

      int width = 0;
      int count = this.sizeList.size();
      for (int i = 0; i < count; i++) {
        UiReadPreferredSize size = this.sizeList.get(i).getDecorator();
        if (size != null) {
          int currentWidth = size.getPreferredWidth();
          if (currentWidth > width) {
            width = currentWidth;
          }
        }
      }
      return width;
    }

  }

}
