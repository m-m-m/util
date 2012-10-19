/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerObserver;
import net.sf.mmm.ui.toolkit.api.widget.UiConfiguration;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link UiWidgetFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying
 *        {@link #getNativeWidget(UiWidgetRegular) widgets}.
 */
public abstract class AbstractUiWidgetFactory<NATIVE_WIDGET> extends AbstractLoggableComponent implements
    UiWidgetFactory<NATIVE_WIDGET>, AttributeWriteHandlerObserver {

  /** @see #getHandlerObserver() */
  private UiHandlerObserver handlerObserver;

  /** @see #getModeChanger() */
  private UiModeChanger modeChanger;

  /** @see #getConfiguration() */
  private final UiConfiguration configuration;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactory() {

    this(new UiConfigurationDefault());
  }

  /**
   * The constructor.
   * 
   * @param configuration is the custom {@link #getConfiguration() configuration} to use.
   */
  public AbstractUiWidgetFactory(UiConfiguration configuration) {

    super();
    this.configuration = configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NATIVE_WIDGET getNativeWidget(UiWidgetRegular widget) {

    NlsNullPointerException.checkNotNull(UiWidgetRegular.class, widget);
    AbstractUiWidget<?> abstractWidget = AbstractUiWidget.asAbstractWidget(widget);
    NATIVE_WIDGET nativeWidget = (NATIVE_WIDGET) abstractWidget.getWidgetAdapter().getWidget();
    return nativeWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiHandlerObserver getHandlerObserver() {

    return this.handlerObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHandlerObserver(UiHandlerObserver handlerObserver) {

    this.handlerObserver = handlerObserver;
  }

  /**
   * @return the {@link UiModeChanger} instance to use.
   */
  public UiModeChanger getModeChanger() {

    if (this.modeChanger == null) {
      this.modeChanger = new UiModeChangerImpl();
    }
    return this.modeChanger;
  }

  /**
   * @param modeChanger is the new {@link UiModeChanger} to use.
   */
  public void setModeChanger(UiModeChanger modeChanger) {

    if (this.modeChanger != null) {
      getLogger().warn("Replacing mode changer {} with {}", new Object[] { this.modeChanger, modeChanger });
    }
    this.modeChanger = modeChanger;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiConfiguration getConfiguration() {

    return this.configuration;
  }

}
