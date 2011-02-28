/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UIDesktopPanel;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIWorkbenchImpl extends UIFrameImpl implements UiWorkbench {

  /** the workbench pane */
  private final UIDesktopPanel workbench;

  /** @see #setComposite(UiComposite) */
  private JPanel contentPane;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param title is the {@link #getTitle() title} of the frame.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link #isResizable() resizeable}.
   */
  public UIWorkbenchImpl(UIFactorySwing uiFactory, String title, boolean resizeable) {

    super(uiFactory, null, title, resizeable);
    // UIManager.put("swing.boldMetal", Boolean.FALSE);
    // JFrame.setDefaultLookAndFeelDecorated(true);
    // Toolkit.getDefaultToolkit().setDynamicLayout(true);
    this.workbench = new UIDesktopPanel(uiFactory, this);
    super.setComposite(this.workbench);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return UiWorkbench.TYPE;
  }

  /**
   * This method gets the desktop panel of this workbench.
   * 
   * @return the desktop panel.
   */
  public UIDesktopPanel getDesktopPanel() {

    return this.workbench;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    if (this.contentPane == null) {
      this.contentPane = new JPanel(new GridLayout(1, 1));
      this.contentPane.setSize(getWidth(), getHeight());
      JComponent swingComponent = this.workbench.getSwingComponent();
      swingComponent.add(this.contentPane);
      swingComponent.addComponentListener(new ResizeListener());
    } else if (this.contentPane.getComponentCount() > 0) {
      this.contentPane.removeAll();
    }
    JComponent jComponent = ((AbstractUiElement) newComposite).getSwingComponent();
    this.contentPane.add(jComponent);
    // TODO...
    // throw new IllegalArgumentException("This method is not applicable for " +
    // UIWorkbenchImpl.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame createFrame(String title, boolean resizeable) {

    UIInternalFrame internalFrame = new UIInternalFrame((UIFactorySwing) getFactory(), this, title,
        resizeable);
    this.workbench.add(internalFrame);
    getFactory().addWindow(internalFrame);
    return internalFrame;
  }

  /**
   * This listener for resizing the content pane.
   */
  private final class ResizeListener implements ComponentListener {

    /**
     * {@inheritDoc}
     */
    public void componentShown(ComponentEvent e) {

    }

    /**
     * {@inheritDoc}
     */
    public void componentResized(ComponentEvent e) {

      UIWorkbenchImpl.this.contentPane.setSize(getWidth(), getHeight());
    }

    /**
     * {@inheritDoc}
     */
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * {@inheritDoc}
     */
    public void componentHidden(ComponentEvent e) {

    }
  }

}
