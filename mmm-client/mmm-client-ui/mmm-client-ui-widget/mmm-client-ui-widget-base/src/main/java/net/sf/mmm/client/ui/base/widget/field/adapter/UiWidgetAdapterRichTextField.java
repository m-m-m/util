/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field.adapter;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterRichTextField extends UiWidgetAdapterTextAreaFieldBase {

  /**
   * @see net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField
   * 
   * @param features are the available {@link RichTextFeature}s.
   */
  void setAvailableFeatures(RichTextFeature... features);

}
