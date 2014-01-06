/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiConfiguration;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.UiDispatcher;
import net.sf.mmm.client.ui.api.UiDisplay;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteEventObserver;
import net.sf.mmm.client.ui.api.binding.DatatypeDetector;
import net.sf.mmm.client.ui.api.handler.UiEventObserver;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.client.ui.base.aria.role.RoleFactoryImpl;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
import net.sf.mmm.client.ui.base.binding.UiDataBindingFactory;
import net.sf.mmm.util.component.api.ComponentContainer;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

import org.slf4j.Logger;

/**
 * This is the abstract base implementation of {@link UiContext}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiContext extends AbstractLoggableComponent implements UiContext,
    AttributeWriteEventObserver {

  /** @see #getEventObserver() */
  private UiEventObserver eventObserver;

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
  private UiWidgetFactory widgetFactory;

  /** @see #getPopupHelper() */
  private UiPopupHelper popupHelper;

  /** @see #getContainer() */
  private ComponentContainer container;

  /** @see #getDataBindingFactory() */
  private UiDataBindingFactory dataBindingFactory;

  /** @see #getAccessKeyBinding() */
  private UiAccessKeyBinding accessKeyBinding;

  /** @see #getDatatypeDetector() */
  private DatatypeDetector datatypeDetector;

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

    if (this.roleFactory == null) {
      this.roleFactory = new RoleFactoryImpl();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiEventObserver getEventObserver() {

    return this.eventObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEventObserver(UiEventObserver eventObserver) {

    this.eventObserver = eventObserver;
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

    getInitializationState().requireNotInitilized();
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

    getInitializationState().requireNotInitilized();
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

    getInitializationState().requireNotInitilized();
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

    getInitializationState().requireNotInitilized();
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

    getInitializationState().requireNotInitilized();
    this.roleFactory = roleFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetFactory getWidgetFactory() {

    return this.widgetFactory;
  }

  /**
   * @param widgetFactory is the {@link UiWidgetFactory} to {@link Inject}.
   */
  @Inject
  public void setWidgetFactory(UiWidgetFactory widgetFactory) {

    getInitializationState().requireNotInitilized();
    this.widgetFactory = widgetFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiPopupHelper getPopupHelper() {

    return this.popupHelper;
  }

  /**
   * @param popupHelper is the {@link UiPopupHelper} to {@link Inject}.
   */
  @Inject
  public void setPopupHelper(UiPopupHelper popupHelper) {

    getInitializationState().requireNotInitilized();
    this.popupHelper = popupHelper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComponentContainer getContainer() {

    return this.container;
  }

  /**
   * @param container is the {@link ComponentContainer} to {@link Inject}.
   */
  @Inject
  public void setContainer(ComponentContainer container) {

    getInitializationState().requireNotInitilized();
    this.container = container;
  }

  /**
   * @return the instance of {@link UiDataBindingFactory}.
   */
  public UiDataBindingFactory getDataBindingFactory() {

    return this.dataBindingFactory;
  }

  /**
   * @param dataBindingFactory is the {@link UiDataBindingFactory} to {@link Inject}.
   */
  @Inject
  public void setDataBindingFactory(UiDataBindingFactory dataBindingFactory) {

    getInitializationState().requireNotInitilized();
    this.dataBindingFactory = dataBindingFactory;
  }

  /**
   * @return the instance of {@link UiAccessKeyBinding}.
   */
  public UiAccessKeyBinding getAccessKeyBinding() {

    return this.accessKeyBinding;
  }

  /**
   * @param accessKeyBinding is the {@link UiAccessKeyBinding} to {@link Inject}.
   */
  @Inject
  public void setAccessKeyBinding(UiAccessKeyBinding accessKeyBinding) {

    getInitializationState().requireNotInitilized();
    this.accessKeyBinding = accessKeyBinding;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Logger getLogger() {

    return super.getLogger();
  }

  /**
   * @return the instance of {@link DatatypeDetector}.
   */
  public DatatypeDetector getDatatypeDetector() {

    return this.datatypeDetector;
  }

  /**
   * @param datatypeDetector is the datatypeDetector to set
   */
  @Inject
  public void setDatatypeDetector(DatatypeDetector datatypeDetector) {

    getInitializationState().requireNotInitilized();
    this.datatypeDetector = datatypeDetector;
  }

}
