/* $Id: AbstractUIPanel.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiMultiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.nls.api.IllegalCaseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class is an abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiMultiComposite} using SWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiMultiCompositeSwt<DELEGATE extends Composite, CHILD extends AbstractUiElement<? extends Control>>
    extends AbstractUiMultiComposite<DELEGATE, CHILD> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMultiCompositeSwt(UiFactorySwt uiFactory) {

    super(uiFactory);
  }

  /**
   * This method converts the given {@link Orientation} to the according
   * {@link SWT} magic constant.
   * 
   * @param orientation is the {@link Orientation}.
   * @return the {@link SWT} constant.
   */
  protected int convertOrientation(Orientation orientation) {

    switch (orientation) {
      case HORIZONTAL:
        return SWT.HORIZONTAL;
      case VERTICAL:
        return SWT.VERTICAL;
      default :
        throw new IllegalCaseException(Orientation.class, orientation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwt getFactory() {

    return (UiFactorySwt) super.getFactory();
  }

}
