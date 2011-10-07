/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNodeAdapter;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.base.view.UiNodeAdapter} using swing. It adapts
 * an AWT {@link Component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public class UiNodeAdapterSwing<DELEGATE extends Component> extends AbstractUiNodeAdapter<DELEGATE>
    implements ChangeListener, ActionListener, ListSelectionListener, TreeSelectionListener {

  /** @see #getDelegate() */
  private final DELEGATE delegate;

  /** @see #getButtonGroup() */
  private ButtonGroup buttonGroup;

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiNodeAdapterSwing(UiNode node, DELEGATE delegate) {

    super(node);
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  public Component getToplevelDelegate() {

    return getDelegate();
  }

  /**
   * {@inheritDoc}
   */
  public DELEGATE getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public void setId(String newId) {

    getDelegate().setName(newId);
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return getDelegate().getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return getToplevelDelegate().isVisible();
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visible) {

    getToplevelDelegate().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return getDelegate().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getDelegate().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(ActionEvent e) {

    getNode().sendEvent(UiEventType.CLICK);
  }

  /**
   * {@inheritDoc}
   */
  public void stateChanged(ChangeEvent e) {

    getNode().sendEvent(UiEventType.VALUE_CHANGE);
  }

  /**
   * {@inheritDoc}
   */
  public void valueChanged(ListSelectionEvent e) {

    getNode().sendEvent(UiEventType.CLICK);
  }

  /**
   * {@inheritDoc}
   */
  public void valueChanged(TreeSelectionEvent e) {

    getNode().sendEvent(UiEventType.CLICK);
  }

  /**
   * This method gets the {@link ButtonGroup} for this widget.<br/>
   * This implementation always returns <code>null</code>. Override for
   * composites and windows.
   * 
   * @return the {@link ButtonGroup}.
   */
  public ButtonGroup getButtonGroup() {

    if (this.buttonGroup == null) {
      this.buttonGroup = new ButtonGroup();
    }
    return this.buttonGroup;
  }

}
