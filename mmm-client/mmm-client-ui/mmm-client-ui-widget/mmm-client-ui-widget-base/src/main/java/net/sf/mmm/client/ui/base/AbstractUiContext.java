/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.UiDispatcher;
import net.sf.mmm.client.ui.api.UiDisplay;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.client.ui.api.handler.UiHandlerObserver;
import net.sf.mmm.client.ui.api.widget.UiConfiguration;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryAdvanced;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.client.ui.base.aria.role.RoleFactoryImpl;
import net.sf.mmm.client.ui.base.widget.UiConfigurationDefault;
import net.sf.mmm.client.ui.base.widget.UiModeChanger;
import net.sf.mmm.client.ui.base.widget.UiModeChangerImpl;
import net.sf.mmm.client.ui.base.widget.UiWidgetFactoryAdvancedImpl;
import net.sf.mmm.client.ui.base.widget.UiWidgetFactoryDatatypeSimple;
import net.sf.mmm.util.component.api.ComponentContainer;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link UiContext}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiContext extends AbstractLoggableComponent implements UiContext,
    AttributeWriteHandlerObserver {

  /** @see #getHandlerObserver() */
  private UiHandlerObserver handlerObserver;

  /** @see #getModeChanger() */
  private UiModeChanger modeChanger;

  /** @see #getConfiguration() */
  private UiConfiguration configuration;

  /** @see #getDispatcher() */
  private UiDispatcher dispatcher;

  /** @see #getDisplay() */
  private UiDisplay display;

  /** @see #getRoleFactory() */
  private RoleFactory roleFactory;

  /** @see #getWidgetFactory() */
  private UiWidgetFactory<?> widgetFactory;

  /** @see #getWidgetFactoryDatatype() */
  private UiWidgetFactoryDatatype widgetFactoryDatatype;

  /** @see #getWidgetFactoryAdvanced() */
  private UiWidgetFactoryAdvanced widgetFactoryAdvanced;

  /** @see #getContainer() */
  private ComponentContainer container;

  /**
   * The constructor.
   */
  public AbstractUiContext() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();

    if (this.configuration == null) {
      this.configuration = new UiConfigurationDefault();
    }

    if (this.modeChanger == null) {
      this.modeChanger = new UiModeChangerImpl();
    }

    if (this.roleFactory == null) {
      this.roleFactory = new RoleFactoryImpl();
    }

    if (this.widgetFactoryDatatype == null) {
      UiWidgetFactoryDatatypeSimple widgetFactoryDatatypeSimple = new UiWidgetFactoryDatatypeSimple();
      widgetFactoryDatatypeSimple.setContext(this);
      widgetFactoryDatatypeSimple.initialize();
      this.widgetFactoryDatatype = widgetFactoryDatatypeSimple;
    }
    if (this.widgetFactoryAdvanced == null) {
      UiWidgetFactoryAdvancedImpl widgetFactoryAdvancedImpl = new UiWidgetFactoryAdvancedImpl();
      widgetFactoryAdvancedImpl.setFactory(this.widgetFactory);
      widgetFactoryAdvancedImpl.initialize();
      this.widgetFactoryAdvanced = widgetFactoryAdvancedImpl;
    }
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

    return this.modeChanger;
  }

  /**
   * @param modeChanger is the new {@link UiModeChanger} to use.
   */
  @Inject
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

  /**
   * @param configuration is the configuration to set
   */
  @Inject
  public void setConfiguration(UiConfiguration configuration) {

    this.configuration = configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDispatcher getDispatcher() {

    return this.dispatcher;
  }

  /**
   * @param dispatcher is the dispatcher to set
   */
  @Inject
  public void setDispatcher(UiDispatcher dispatcher) {

    this.dispatcher = dispatcher;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDisplay getDisplay() {

    return this.display;
  }

  /**
   * @param display is the display to set
   */
  @Inject
  public void setDisplay(UiDisplay display) {

    this.display = display;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RoleFactory getRoleFactory() {

    return this.roleFactory;
  }

  /**
   * @param roleFactory is the {@link RoleFactory} instance to set (to inject).
   */
  @Inject
  public void setRoleFactory(RoleFactory roleFactory) {

    this.roleFactory = roleFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetFactory<?> getWidgetFactory() {

    return this.widgetFactory;
  }

  /**
   * @param widgetFactory is the widgetFactory to set
   */
  @Inject
  public void setWidgetFactory(UiWidgetFactory<?> widgetFactory) {

    getInitializationState().requireNotInitilized();
    this.widgetFactory = widgetFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetFactoryDatatype getWidgetFactoryDatatype() {

    return this.widgetFactoryDatatype;
  }

  /**
   * @param widgetFactoryDatatype is the widgetFactoryDatatype to set
   */
  @Inject
  public void setWidgetFactoryDatatype(UiWidgetFactoryDatatype widgetFactoryDatatype) {

    this.widgetFactoryDatatype = widgetFactoryDatatype;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetFactoryAdvanced getWidgetFactoryAdvanced() {

    return this.widgetFactoryAdvanced;
  }

  /**
   * @param widgetFactoryAdvanced is the widgetFactoryAdvanced to set
   */
  @Inject
  public void setWidgetFactoryAdvanced(UiWidgetFactoryAdvanced widgetFactoryAdvanced) {

    getInitializationState().requireNotInitilized();
    this.widgetFactoryAdvanced = widgetFactoryAdvanced;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComponentContainer getContainer() {

    return this.container;
  }

  /**
   * @param container is the {@link ComponentContainer} to set.
   */
  @Inject
  protected void setContainer(ComponentContainer container) {

    this.container = container;
  }

}
