/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryAdvanced;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetSplitPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.lang.base.SimpleEnumProvider;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link UiWidgetFactoryAdvanced}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiWidgetFactoryAdvancedImpl extends AbstractLoggableComponent implements UiWidgetFactoryAdvanced {

  /** @see #getContext() */
  private UiContext context;

  /** @see #getEnumProvider() */
  private EnumProvider enumProvider;

  /** @see #getClientUiBundle() */
  private NlsBundleClientUiRoot clientUiBundle;

  /** @see #createButton(Class, UiHandlerPlain, boolean) */
  private Map<Class<? extends UiHandlerPlain>, UiSingleWidgetButtonFactory<?>> handlerType2ButtonFactoryMap;

  /**
   * The constructor.
   */
  public UiWidgetFactoryAdvancedImpl() {

    super();
    this.handlerType2ButtonFactoryMap = new HashMap<Class<? extends UiHandlerPlain>, UiSingleWidgetButtonFactory<?>>();
    registerButtonFactories();
  }

  /**
   * Registers the given <code>factory</code>.
   * 
   * @param factory is the {@link UiSingleWidgetButtonFactory} to register.
   */
  protected void registerButtonFactory(UiSingleWidgetButtonFactory<?> factory) {

    NlsNullPointerException.checkNotNull(UiSingleWidgetButtonFactory.class, factory);
    Class<? extends UiHandlerPlain> key = factory.getHandlerInterface();
    NlsNullPointerException.checkNotNull("factory.handlerInterface", key);
    UiSingleWidgetButtonFactory<?> duplicate = this.handlerType2ButtonFactoryMap.get(key);
    if (duplicate != null) {
      throw new DuplicateObjectException(factory, key, duplicate);
    }
    this.handlerType2ButtonFactoryMap.put(key, factory);
  }

  /**
   * This method {@link #registerButtonFactory(UiSingleWidgetButtonFactory) registers} all available
   * {@link UiSingleWidgetButtonFactory button-factories}.
   */
  protected void registerButtonFactories() {

    registerButtonFactory(new UiSingleWidgetButtonFactoryApprove());
    registerButtonFactory(new UiSingleWidgetButtonFactoryCancel());
    registerButtonFactory(new UiSingleWidgetButtonFactoryConfirm());
    registerButtonFactory(new UiSingleWidgetButtonFactoryDelete());
    registerButtonFactory(new UiSingleWidgetButtonFactoryDown());
    registerButtonFactory(new UiSingleWidgetButtonFactoryNext());
    registerButtonFactory(new UiSingleWidgetButtonFactoryOpen());
    registerButtonFactory(new UiSingleWidgetButtonFactoryPrevious());
    registerButtonFactory(new UiSingleWidgetButtonFactoryRemove());
    registerButtonFactory(new UiSingleWidgetButtonFactorySave());
    registerButtonFactory(new UiSingleWidgetButtonFactoryStartEdit());
    registerButtonFactory(new UiSingleWidgetButtonFactoryStopEdit());
    registerButtonFactory(new UiSingleWidgetButtonFactorySubmit());
    registerButtonFactory(new UiSingleWidgetButtonFactoryUp());
  }

  /**
   * @return the {@link UiContext} instance.
   */
  protected UiContext getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.enumProvider == null) {
      SimpleEnumProvider impl = new SimpleEnumProvider();
      impl.initialize();
      this.enumProvider = impl;
    }
    this.clientUiBundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
  }

  /**
   * @param context is the {@link UiContext} to {@link Inject}.
   */
  @Inject
  public void setContext(UiContext context) {

    getInitializationState().requireNotInitilized();
    this.context = context;
  }

  /**
   * @return the enumProvider
   */
  protected EnumProvider getEnumProvider() {

    return this.enumProvider;
  }

  /**
   * @param enumProvider is the enumProvider to set
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    getInitializationState().requireNotInitilized();
    this.enumProvider = enumProvider;
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  protected NlsBundleClientUiRoot getClientUiBundle() {

    return this.clientUiBundle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(String label, UiHandlerEventClick clickHandler) {

    UiWidgetButton widget = this.context.getWidgetFactory().create(UiWidgetButton.class);
    widget.setLabel(label);
    widget.addClickHandler(clickHandler);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(UiHandlerPlain handler) {

    return createButton(null, handler, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <HANDLER extends UiHandlerPlain> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler) {

    return createButton(handlerType, handler, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <HANDLER extends UiHandlerPlain> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler,
      final boolean preventConfirmationPopup) {

    UiSingleWidgetButtonFactory<?> buttonFactory;
    if (handlerType != null) {
      buttonFactory = this.handlerType2ButtonFactoryMap.get(handlerType);
    } else {
      buttonFactory = null;
      for (UiSingleWidgetButtonFactory<?> factory : this.handlerType2ButtonFactoryMap.values()) {
        if (factory.isInstance(handler)) {
          if (buttonFactory != null) {
            throw new DuplicateObjectException(handler, buttonFactory.getHandlerInterface(),
                factory.getHandlerInterface());
          }
          buttonFactory = factory;
        }
      }
    }
    if (buttonFactory == null) {
      String illegalCase;
      if (handlerType != null) {
        illegalCase = handlerType.toString();
      } else {
        illegalCase = handler.getClass().getName();
      }
      throw new IllegalCaseException(illegalCase);
    }
    return ((UiSingleWidgetButtonFactory<HANDLER>) buttonFactory).create(this.context, handler,
        preventConfirmationPopup);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage createImage(String url, String altText) {

    UiWidgetImage widget = this.context.getWidgetFactory().create(UiWidgetImage.class);
    widget.setUrl(url);
    widget.setAltText(altText);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel createLabel(String label) {

    UiWidgetLabel widget = this.context.getWidgetFactory().create(UiWidgetLabel.class);
    widget.setLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTextField createTextField(String label) {

    UiWidgetTextField widget = this.context.getWidgetFactory().create(UiWidgetTextField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetIntegerField createIntegerField(String label) {

    UiWidgetIntegerField widget = this.context.getWidgetFactory().create(UiWidgetIntegerField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLongField createLongField(String label) {

    UiWidgetLongField widget = this.context.getWidgetFactory().create(UiWidgetLongField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetComboBox<VALUE> createComboBox(String label, final EnumDefinition<VALUE, ?> enumDefinition) {

    final UiWidgetComboBox<VALUE> widget = this.context.getWidgetFactory().create(UiWidgetComboBox.class);
    widget.setFieldLabel(label);
    widget.setFormatter(enumDefinition.getFormatter());
    Runnable callback = new Runnable() {

      @Override
      public void run() {

        List<VALUE> enumValues = UiWidgetFactoryAdvancedImpl.this.enumProvider.getEnumValues(enumDefinition);
        widget.setOptions(enumValues);
      }
    };
    this.enumProvider.require(callback, enumDefinition);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetSplitPanel createSplitPanel(Orientation orientation, UiWidgetRegular... children) {

    if (children == null) {
      throw new NlsNullPointerException("children");
    }
    if (children.length < 2) {
      throw new NlsIllegalArgumentException(Integer.valueOf(children.length), "children.length");
    }
    UiWidgetSplitPanel splitPanel;
    switch (orientation) {
      case HORIZONTAL:
        splitPanel = this.context.getWidgetFactory().create(UiWidgetHorizontalSplitPanel.class);
        break;
      case VERTICAL:
        splitPanel = this.context.getWidgetFactory().create(UiWidgetVerticalSplitPanel.class);
        break;
      default :
        throw new IllegalCaseException(Orientation.class, orientation);
    }
    for (UiWidgetRegular child : children) {
      splitPanel.addChild(child);
    }
    return splitPanel;
  }

}
