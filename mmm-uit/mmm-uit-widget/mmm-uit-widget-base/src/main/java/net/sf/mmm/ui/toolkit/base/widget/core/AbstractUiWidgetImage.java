/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetRegularAtomic;
import net.sf.mmm.ui.toolkit.base.widget.core.adapter.UiWidgetAdapterImage;

/**
 * This is the abstract base implementation of {@link UiWidgetImage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetImage<ADAPTER extends UiWidgetAdapterImage<?>> extends
    AbstractUiWidgetRegularAtomic<ADAPTER> implements UiWidgetImage {

  /** @see #getAltText() */
  private String altText;

  /** @see #getUrl() */
  private String url;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetImage(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.url != null) {
      setUrl(adapter);
    }
    if (this.altText != null) {
      adapter.setAltText(this.altText);
    }
  }

  /**
   * @param adapter is the {@link #getWidgetAdapter() widget adapter}.
   */
  private void setUrl(ADAPTER adapter) {

    String absoluteUrl;
    if (this.url.startsWith("http://")) {
      absoluteUrl = this.url;
    } else if (this.url.startsWith("https://")) {
      absoluteUrl = this.url;
    } else if (this.url.startsWith("/")) {
      absoluteUrl = this.url;
    } else {
      absoluteUrl = adapter.createAbsoluteImageUrl(this.url);
    }
    adapter.setUrl(absoluteUrl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {

    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    this.url = url;
    if (hasWidgetAdapter()) {
      setUrl(getWidgetAdapter());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAltText() {

    return this.altText;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAltText(String altText) {

    this.altText = altText;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setAltText(altText);
    }
  }

}
