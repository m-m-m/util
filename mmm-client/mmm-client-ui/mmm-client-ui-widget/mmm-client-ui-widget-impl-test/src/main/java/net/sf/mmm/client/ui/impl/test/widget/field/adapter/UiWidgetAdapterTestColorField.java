/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.Color;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterColorField;

/**
 * This is the implementation of {@link UiWidgetAdapterColorField} for testing without native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestColorField extends UiWidgetAdapterTestField<Color, Color> implements
    UiWidgetAdapterColorField {

  /** @see #getOptions() */
  private List<Color> options;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestColorField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<Color> options) {

    this.options = options;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Color> getOptions() {

    return this.options;
  }

}
