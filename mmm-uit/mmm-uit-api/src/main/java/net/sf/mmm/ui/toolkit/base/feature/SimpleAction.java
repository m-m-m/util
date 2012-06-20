/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.view.UiImage;

/**
 * This is a simple implementation of the {@link net.sf.mmm.ui.toolkit.api.feature.UiAction} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SimpleAction extends AbstractAction {

  /** the {@link #getActionListener() listener} */
  private UiEventListener listener;

  /**
   * The constructor.
   * 
   * @param actionListener is the {@link #getActionListener() listener}.
   * @param displayName is the {@link #getName() name}.
   */
  public SimpleAction(UiEventListener actionListener, String displayName) {

    this(actionListener, displayName, null);
  }

  /**
   * The constructor.
   * 
   * @param actionListener is the {@link #getActionListener() listener}.
   * @param displayName is the {@link #getName() name}.
   * @param displayIcon is the {@link #getIcon() icon}.
   */
  public SimpleAction(UiEventListener actionListener, String displayName, UiImage displayIcon) {

    super(displayName);
    this.listener = actionListener;
  }

  /**
   * This method sets the {@link #getActionListener() listener}.
   * 
   * @param newListener is the new listener to set.
   */
  public void setActionListener(UiEventListener newListener) {

    this.listener = newListener;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiEventListener getActionListener() {

    return this.listener;
  }

}
