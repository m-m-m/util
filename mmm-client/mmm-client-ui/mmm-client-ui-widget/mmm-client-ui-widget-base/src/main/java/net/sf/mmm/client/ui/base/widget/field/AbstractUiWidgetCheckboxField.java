/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterCheckboxField;

/**
 * This is the abstract base implementation of {@link UiWidgetCheckboxField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetCheckboxField<ADAPTER extends UiWidgetAdapterCheckboxField> extends
    AbstractUiWidgetField<ADAPTER, Boolean, Boolean> implements UiWidgetCheckboxField {

  /** @see #getLabel() */
  private String title;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetCheckboxField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Boolean> getValueClass() {

    return Boolean.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.title != null) {
      adapter.setTitle(this.title);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.title = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitle(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.title == null) {
      return super.toString();
    }
    return super.toString() + "[" + this.title + "]";
  }

}
