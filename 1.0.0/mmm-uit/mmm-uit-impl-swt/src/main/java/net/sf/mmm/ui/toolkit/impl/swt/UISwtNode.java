/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.base.AbstractUINode;

/**
 * This is the abstract base implementation for all SWT
 * {@link net.sf.mmm.ui.toolkit.api.UINode ui-nodes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UISwtNode extends AbstractUINode {

  /** the ui factory */
  private final UIFactorySwt factory;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UISwtNode(UIFactorySwt uiFactory, AbstractUINode parentObject) {

    super(uiFactory, parentObject);
    this.factory = uiFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UIFactorySwt getFactory() {

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
