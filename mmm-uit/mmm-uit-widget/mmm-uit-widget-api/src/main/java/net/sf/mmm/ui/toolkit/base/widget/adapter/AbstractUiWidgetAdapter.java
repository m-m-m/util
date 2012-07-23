/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadAltText;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadImage;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadLabel;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadUrl;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

/**
 * This is the abstract base implementation of {@link UiWidgetAdapter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidgetAdapter<WIDGET> implements UiWidgetAdapter<WIDGET>, AttributeReadAltText,
    AttributeReadUrl, AttributeReadLabel, AttributeReadTitle<String>, AttributeReadImage<UiWidgetImage> {

  /** @see #getWidget() */
  private final WIDGET widget;

  /**
   * The constructor.
   */
  public AbstractUiWidgetAdapter() {

    super();
    this.widget = createWidget();
  }

  /**
   * This method creates the {@link #getWidget() underlying widget}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is called from the constructor but implemented in sub-classes. You should NOT access or even
   * modify member variables as they are NOT set at this point (even final members). However, this design is
   * done by intention instead of passing the widget as constructor argument to give more flexibility by
   * overriding and also for potential lazy initialization of the widget.
   * 
   * @return a new instance of the {@link #getWidget() underlying widget}.
   */
  protected abstract WIDGET createWidget();

  /**
   * {@inheritDoc}
   */
  @Override
  public WIDGET getWidget() {

    return this.widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAltText() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    throw new NlsUnsupportedOperationException();
  }

}
