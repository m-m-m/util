/* $Id: UISwtNode.java 955 2011-02-16 23:10:16Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view;

import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;
import net.sf.mmm.ui.toolkit.impl.swt.SwtListenerAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This is the abstract base implementation for all SWT
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode ui-nodes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiSwtNode extends AbstractUiNode {

  /** the ui factory */
  private final UiFactorySwt factory;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UiSwtNode(UiFactorySwt uiFactory, AbstractUiNode parentObject) {

    super(uiFactory, parentObject);
    this.factory = uiFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwt getFactory() {

    return this.factory;
  }

  /**
   * This method creates a new SWT listener that adapts the events.
   * 
   * @return the listener adapter.
   */
  protected Listener createSwtListener() {

    return new SwtListenerAdapter(this);
  }

}
