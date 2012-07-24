/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetButton;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetAtomicWithLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterButton;

/**
 * This is the abstract base implementation of {@link UiWidgetButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetButton<ADAPTER extends UiWidgetAdapterButton<?>> extends
    AbstractUiWidgetAtomicWithLabel<ADAPTER> implements UiWidgetButton {

  /** @see #getImage() */
  private UiWidgetImage image;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetButton(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.image != null) {
      adapter.setImage(this.image);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    return this.image;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    if (this.image == image) {
      return;
    }
    this.image = image;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setImage(image);
    }
  }

}
