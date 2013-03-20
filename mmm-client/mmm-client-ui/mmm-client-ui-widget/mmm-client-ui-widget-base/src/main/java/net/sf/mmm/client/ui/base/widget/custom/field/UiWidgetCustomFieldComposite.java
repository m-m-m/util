/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.field;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.client.ui.base.handler.event.FocusEventSender;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the abstract base class for a {@link UiWidgetCustomField custom field widget} that is composed out
 * of multiple {@link UiWidgetField field widgets} and potentially other widgets. Extend this class to create
 * a widget for a composite {@link net.sf.mmm.util.lang.api.Datatype}.<br/>
 * <b>ATTENTION:</b><br/>
 * This class assumes that you create and {@link #addChild(UiWidgetRegular) add} the child widgets in the
 * constructor (of your sub-class) and requires that at least one of them is a {@link UiWidgetField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically a custom
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomFieldComposite<VALUE, DELEGATE extends UiWidgetDynamicComposite<UiWidgetRegular>>
    extends UiWidgetCustomField<VALUE, DELEGATE> {

  /** The {@link List} of children that are field widgets. */
  private final List<UiWidgetField<?>> fieldList;

  /** @see #addFocusHandler(UiHandlerEventFocus) */
  private final FocusEventSender focusEventSender;

  /** The sub-field that currently has the focus or <code>null</code>. */
  private AttributeReadFocused focusField;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomFieldComposite(UiContext context, DELEGATE delegate) {

    super(context, delegate);
    this.fieldList = new LinkedList<UiWidgetField<?>>();
    this.focusEventSender = new FocusEventSender(this, getContext());
  }

  /**
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite#addChild(net.sf.mmm.client.ui.api.widget.UiWidget)
   * 
   * @param child is the child widget to add.
   */
  protected void addChild(UiWidgetRegular child) {

    if (child instanceof UiWidgetField<?>) {
      registerField((UiWidgetField<?>) child);
    }
    getDelegate().addChild(child);
  }

  /**
   * @param field is the {@link UiWidgetField} to register.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private void registerField(UiWidgetField<?> field) {

    this.fieldList.add(field);
    field.addChangeHandler(new UiHandlerEventValueChange() {

      @Override
      public void onValueChange(AttributeReadValue source, boolean programmatic) {

        requireChangeEventSender().onValueChange(UiWidgetCustomFieldComposite.this, programmatic);
      }
    });
    field.addFocusHandler(new UiHandlerEventFocus() {

      @Override
      public void onFocusChange(AttributeReadFocused source, boolean programmatic, boolean lost) {

        if (lost) {
          if (source == UiWidgetCustomFieldComposite.this.focusField) {
            UiWidgetCustomFieldComposite.this.focusField = null;
          }
        } else {
          UiWidgetCustomFieldComposite.this.focusField = source;
        }
        UiWidgetCustomFieldComposite.this.focusEventSender.onFocusChange(source, programmatic, lost);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetField<?> getFirstField() {

    return this.fieldList.get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFieldLabel() {

    return getFirstField().getFieldLabel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFieldLabel(String label) {

    getFirstField().setFieldLabel(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel getFieldLabelWidget() {

    return this.fieldList.get(0).getFieldLabelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    this.focusEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return this.focusEventSender.removeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused() {

    if (focused) {
      this.fieldList.get(0).setFocused();
    } else {
      if (this.focusField != null) {
        ((UiWidgetField<?>) this.focusField).setFocused();
      } else {
        this.fieldList.get(0).setFocused();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return (this.focusField != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeEventSender<VALUE> createChangeEventSender() {

    return new ChangeEventSender<VALUE>(this, getContext());
  }

}
