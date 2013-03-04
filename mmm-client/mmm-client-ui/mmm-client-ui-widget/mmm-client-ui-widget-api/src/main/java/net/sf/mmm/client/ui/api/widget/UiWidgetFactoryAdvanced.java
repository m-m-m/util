/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetSplitPanel;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * While {@link UiWidgetFactory} allows to create any {@link UiWidget} via a generic and stable API this
 * interface defines a higher-level factory that offers more comfort. So {@link UiWidgetFactory} is the
 * low-level factory to abstract from the underlying implementation this factory is technology independent and
 * offers more comfort to the end-user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetFactoryAdvanced {

  /**
   * This method creates a new {@link UiWidgetButton}.
   * 
   * @see #createButton(UiHandlerPlain)
   * 
   * @param label is the {@link UiWidgetButton#getLabel() label}.
   * @param clickHandler is the {@link UiHandlerEventClick} invoked if the button is clicked.
   * @return the new widget instance.
   */
  UiWidgetButton createButton(String label, UiHandlerEventClick clickHandler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerPlain}. E.g. passing an
   * instance of {@link net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave} will create a save-button
   * that invokes {@link net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave#onSave(Object)} if clicked.<br/>
   * <b>ATTENTION:</b><br/>
   * This method will fail with an exception if the given <code>handler</code> implements more than one known
   * {@link UiHandlerPlain} interface. Use {@link #createButton(Class, UiHandlerPlain)} to prevent this.
   * 
   * @param handler is the {@link UiHandlerPlain} instance.
   * @return the new widget instance.
   */
  UiWidgetButton createButton(UiHandlerPlain handler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerPlain}. In advance to
   * {@link #createButton(UiHandlerPlain)} this method can be used for an handler implementation that realizes
   * multiple {@link UiHandlerPlain} interfaces as the proper one to choose is identified by
   * <code>handlerType</code>.
   * 
   * @param <HANDLER> is the generic type of the {@link UiHandlerPlain}.
   * 
   * @param handlerType is the {@link Class} reflecting the {@link UiHandlerPlain} interface to create a
   *        button for.
   * @param handler is the {@link UiHandlerPlain} instance.
   * @return the new widget instance.
   */
  <HANDLER extends UiHandlerPlain> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerPlain}. In advance to
   * {@link #createButton(UiHandlerPlain)} this method can be used for an handler implementation that realizes
   * multiple {@link UiHandlerPlain} interfaces as the proper one to choose is identified by
   * <code>handlerType</code>.
   * 
   * @param <HANDLER> is the generic type of the {@link UiHandlerPlain}.
   * 
   * @param handlerType is the {@link Class} reflecting the {@link UiHandlerPlain} interface to create a
   *        button for.
   * @param handler is the {@link UiHandlerPlain} instance.
   * @param preventConfirmationPopup - some {@link UiHandlerPlain plain handlers} represent operations like
   *        {@link net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainDelete#onDelete(Object)} that should be
   *        confirmed by the user to prevent accidental invocations. In such case the returned
   *        {@link UiWidgetButton} will by itself open a confirmation popup allowing to cancel to operation.
   *        This will make your life easier. However, if you want to customize the popup with contextual
   *        information (e.g. "Are you sure you want to delete the 23 selected documents?") you can implement
   *        that inside the given <code>handler</code> and prevent the default popup by providing
   *        <code>true</code> here. Use <code>false</code> for the default behavior.
   * @return the new widget instance.
   */
  <HANDLER extends UiHandlerPlain> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler,
      boolean preventConfirmationPopup);

  /**
   * This method creates a new {@link UiWidgetLabel}.
   * 
   * @param label is the {@link UiWidgetLabel#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLabel createLabel(String label);

  /**
   * This method creates a new {@link UiWidgetImage}.
   * 
   * @param url is the {@link UiWidgetImage#getUrl() URL}.
   * @param altText is the {@link UiWidgetImage#getAltText() alternative text}.
   * @return the new widget instance.
   */
  UiWidgetImage createImage(String url, String altText);

  /**
   * This method creates a new {@link UiWidgetTextField}.
   * 
   * @param label is the {@link UiWidgetTextField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetTextField createTextField(String label);

  /**
   * This method creates a new {@link UiWidgetIntegerField}.
   * 
   * @param label is the {@link UiWidgetIntegerField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetIntegerField createIntegerField(String label);

  /**
   * This method creates a new {@link UiWidgetLongField}.
   * 
   * @param label is the {@link UiWidgetLongField#getFieldLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLongField createLongField(String label);

  /**
   * This method creates a new {@link UiWidgetComboBox}.
   * 
   * @param <VALUE> is the generic type of the available selection options.
   * 
   * @param label is the {@link UiWidgetLongField#getFieldLabel() label}.
   * @param enumDefinition is the {@link EnumDefinition} identifying the available
   *        {@link UiWidgetComboBox#getOptions() options}. These may be loaded asynchronous via
   *        {@link net.sf.mmm.util.lang.api.EnumProvider}.
   * @return the new widget instance.
   */
  <VALUE> UiWidgetComboBox<VALUE> createComboBox(String label, EnumDefinition<VALUE, ?> enumDefinition);

  /**
   * This method creates a new {@link UiWidgetSplitPanel}.
   * 
   * @param orientation - {@link Orientation#HORIZONTAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel} or
   *        {@link Orientation#VERTICAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel}.
   * @param children are the {@link UiWidgetSplitPanel#getChild(String) children}. You need to provide at
   *        least two children.
   * @return the new {@link UiWidgetSplitPanel}.
   */
  UiWidgetSplitPanel createSplitPanel(Orientation orientation, UiWidgetRegular... children);

}
