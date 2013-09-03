/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.RichTextFeature;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRichTextField;

/**
 * This is the abstract base implementation of {@link UiWidgetRichTextField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetRichTextField<ADAPTER extends UiWidgetAdapterRichTextField> extends
    AbstractUiWidgetTextAreaFieldBase<ADAPTER> implements UiWidgetRichTextField {

  /** @see #setAvailableFeatures(RichTextFeature...) */
  private RichTextFeature[] availableFeatures;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetRichTextField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.availableFeatures != null) {
      adapter.setAvailableFeatures(this.availableFeatures);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAvailableFeatures(RichTextFeature... features) {

    this.availableFeatures = features;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setAvailableFeatures(features);
    }
  }

}
