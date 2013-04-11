/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.pattern;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetDynamicPanel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the regular implementation of the {@link AbstractUiWidgetCustomMasterDetail custom master/detail
 * widget}.
 * 
 * @param <ROW> is the generic type of a single row out of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetCustomTwoListSelection<ROW> extends
    UiWidgetCustomComposite<List<ROW>, UiWidgetRegular, UiWidgetDynamicPanel<UiWidgetRegular>> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  protected AbstractUiWidgetCustomTwoListSelection(UiContext context, UiWidgetDynamicPanel<UiWidgetRegular> delegate) {

    super(context, delegate);
  }

  /**
   * @return the source {@link UiWidgetListBase list} where to choose options from.
   */
  public abstract UiWidgetListBase<ROW> getSourceList();

  /**
   * @return the target {@link UiWidgetListBase list} where to add the selected options to.
   */
  public abstract UiWidgetListBase<ROW> getTargetList();

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<ROW> doGetValue(List<ROW> template, ValidationState state) throws RuntimeException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<ROW> createNewValue() {

    return new ArrayList<ROW>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(List<ROW> value, boolean forUser) {

    // TODO Auto-generated method stub

  }

}
