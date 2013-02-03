/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryAdvanced;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
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
import net.sf.mmm.util.nls.api.IllegalCaseException;
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

  /** @see #getFactory() */
  private UiWidgetFactory factory;

  /** @see #getEnumProvider() */
  private EnumProvider enumProvider;

  /**
   * The constructor.
   */
  public UiWidgetFactoryAdvancedImpl() {

    super();
  }

  /**
   * @return the {@link UiWidgetFactory} instance.
   */
  protected UiWidgetFactory getFactory() {

    return this.factory;
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
  }

  /**
   * @param factory is the factory to set
   */
  @Inject
  public void setFactory(UiWidgetFactory factory) {

    getInitializationState().requireNotInitilized();
    this.factory = factory;
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
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(String label) {

    UiWidgetButton widget = this.factory.create(UiWidgetButton.class);
    widget.setLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel createLabel(String label) {

    UiWidgetLabel widget = this.factory.create(UiWidgetLabel.class);
    widget.setLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTextField createTextField(String label) {

    UiWidgetTextField widget = this.factory.create(UiWidgetTextField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetIntegerField createIntegerField(String label) {

    UiWidgetIntegerField widget = this.factory.create(UiWidgetIntegerField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLongField createLongField(String label) {

    UiWidgetLongField widget = this.factory.create(UiWidgetLongField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetComboBox<VALUE> createComboBox(String label, final EnumDefinition<VALUE, ?> enumDefinition) {

    final UiWidgetComboBox<VALUE> widget = this.factory.create(UiWidgetComboBox.class);
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
        splitPanel = this.factory.create(UiWidgetHorizontalSplitPanel.class);
        break;
      case VERTICAL:
        splitPanel = this.factory.create(UiWidgetVerticalSplitPanel.class);
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
