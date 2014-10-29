/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import java.util.List;

import net.sf.mmm.client.ui.api.handler.action.UiHandlerAction;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSection;
import net.sf.mmm.client.ui.api.widget.factory.AbstractUiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.factory.AbstractUiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetSplitPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This interface is the central API for the end-user to create widgets via factory. The key to get started is
 * the {@link net.sf.mmm.client.ui.api.UiContext} that gives access to this {@link UiWidgetFactory} via
 * {@link net.sf.mmm.client.ui.api.UiContext#getWidgetFactory()}. <br>
 * Besides fabrication there are also custom widgets at a higher level - see
 * <code>net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom</code> for details. <br>
 * While {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative} allows to create any
 * {@link UiWidget} via a generic and stable API this interface defines a higher-level factory that offers
 * more comfort. So {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative} is the low-level
 * factory to abstract from the underlying implementation this factory is the higher-level
 * {@link net.sf.mmm.util.component.api.Api API} for end-users and is implemented independent from the UI
 * toolkit. <br>
 * For generic access you can do:
 *
 * <pre>
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton} saveButton = factory.{@link #create(Class)
 * create}({@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton}.class);
 * saveButton.{@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton#setLabel(String) setLabel}("save");
 * saveButton.{@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton#addClickHandler(UiHandlerEventClick)
 * addClickHandler}(clickHandler);
 * </pre>
 *
 * However, it is a more comfortable to do:
 *
 * <pre>
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton} saveButton = factory.{@link #createButton(String, UiHandlerEventClick)
 * createButton}("save", clickHandler);
 * </pre>
 *
 * But you maybe also want to have a icon and a tooltip and {@link net.sf.mmm.util.nls.api.NlsMessage
 * NLS/I18N}. And you discover that you do not only need this code once in your application but all over the
 * place. Then you will be happy to see that you get all the comfort this way:
 *
 * <pre>
 * {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave} saveHandler = ...;
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton} saveButton = factory.{@link #createButton(UiHandlerAction) createButton}(saveHandler);
 * </pre>
 *
 * Now this is just the beginning of this awesome UI framework. You will discover that there are even much
 * higher level features such as e.g. the <em>editor pattern</em>. So see
 * <code>net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomEditor</code> for more.
 *
 * <b>ATTENTION:</b><br>
 * This is a {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extendable interface}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface UiWidgetFactory extends AbstractUiWidgetFactoryNative, AbstractUiWidgetFactoryDatatype {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.client.ui.api.widget.UiWidgetFactoryAdvanced";

  /**
   * This method creates a new {@link UiWidgetButton}.
   *
   * @see #createButton(UiHandlerAction)
   *
   * @param label is the {@link UiWidgetButton#getLabel() label}.
   * @param clickHandler is the {@link UiHandlerEventClick} invoked if the button is clicked.
   * @return the new widget instance.
   */
  UiWidgetButton createButton(String label, UiHandlerEventClick clickHandler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerAction}. E.g. passing an
   * instance of {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave} will create a save-button
   * that invokes
   * {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave#onSave(net.sf.mmm.client.ui.api.event.UiEvent)}
   * if clicked. <br>
   * <b>ATTENTION:</b><br>
   * This method will fail with an exception if the given <code>handler</code> implements more than one known
   * {@link UiHandlerAction} interface. Use {@link #createButton(Class, UiHandlerAction)} to prevent this.
   *
   * @param handler is the {@link UiHandlerAction} instance.
   * @return the new widget instance.
   */
  UiWidgetButton createButton(UiHandlerAction handler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerAction}. In advance to
   * {@link #createButton(UiHandlerAction)} this method can be used for an handler implementation that
   * realizes multiple {@link UiHandlerAction} interfaces as the proper one to choose is identified by
   * <code>handlerType</code>.
   *
   * @param <HANDLER> is the generic type of the {@link UiHandlerAction}.
   *
   * @param handlerType is the {@link Class} reflecting the {@link UiHandlerAction} interface to create a
   *        button for.
   * @param handler is the {@link UiHandlerAction} instance.
   * @return the new widget instance.
   */
  <HANDLER extends UiHandlerAction> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler);

  /**
   * This method creates a new {@link UiWidgetButton} for the given {@link UiHandlerAction}. In advance to
   * {@link #createButton(UiHandlerAction)} this method can be used for an handler implementation that
   * realizes multiple {@link UiHandlerAction} interfaces as the proper one to choose is identified by
   * <code>handlerType</code>.
   *
   * @param <HANDLER> is the generic type of the {@link UiHandlerAction}.
   *
   * @param handlerType is the {@link Class} reflecting the {@link UiHandlerAction} interface to create a
   *        button for.
   * @param handler is the {@link UiHandlerAction} instance.
   * @param preventConfirmationPopup - some {@link UiHandlerAction plain handlers} represent operations like
   *        {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionDelete#onDelete(net.sf.mmm.client.ui.api.event.UiEvent)}
   *        that should be confirmed by the user to prevent accidental invocations. In such case the returned
   *        {@link UiWidgetButton} will by itself open a confirmation popup allowing to cancel to operation.
   *        This will make your life easier. However, if you want to customize the popup with contextual
   *        information (e.g. "Are you sure you want to delete the 23 selected documents?") you can implement
   *        that inside the given <code>handler</code> and prevent the default popup by providing
   *        <code>true</code> here. Use <code>false</code> for the default behavior.
   * @return the new widget instance.
   */
  <HANDLER extends UiHandlerAction> UiWidgetButton createButton(Class<HANDLER> handlerType, HANDLER handler,
      boolean preventConfirmationPopup);

  /**
   * This method creates a new {@link UiWidgetLabel}.
   *
   * @param label is the {@link UiWidgetLabel#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLabel createLabel(String label);

  /**
   * This method creates a new {@link UiWidgetSection}.
   *
   * @param label is the {@link UiWidgetSection#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetSection createSection(String label);

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
   * @param label is the {@link UiWidgetTextField#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetTextField createTextField(String label);

  /**
   * This method creates a new {@link UiWidgetTextAreaField}.
   *
   * @param label is the {@link UiWidgetTextAreaField#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetTextAreaField createTextAreaField(String label);

  /**
   * This method creates a new {@link UiWidgetIntegerField}.
   *
   * @param label is the {@link UiWidgetIntegerField#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetIntegerField createIntegerField(String label);

  /**
   * This method creates a new {@link UiWidgetLongField}.
   *
   * @param label is the {@link UiWidgetLongField#getLabel() label}.
   * @return the new widget instance.
   */
  UiWidgetLongField createLongField(String label);

  /**
   * This method creates a new {@link UiWidgetComboboxField}.
   *
   * @param <VALUE> is the generic type of the available selection options.
   *
   * @param label is the {@link UiWidgetComboboxField#getLabel() label}.
   * @param options are the {@link UiWidgetComboboxField#getOptions() options} of the combobox. E.g.
   *        {@link Class#getEnumConstants() enum values} via
   *        <code>{@link java.util.Arrays#asList(Object...) Arrays.asList}(MyEnum.values())</code>.
   * @return the new widget instance.
   */
  <VALUE> UiWidgetComboboxField<VALUE> createComboBox(String label, List<VALUE> options);

  /**
   * This method creates a new {@link UiWidgetComboboxField}.
   *
   * @param <VALUE> is the generic type of the available selection options.
   *
   * @param label is the {@link UiWidgetLongField#getLabel() label}.
   * @param enumDefinition is the {@link EnumDefinition} identifying the available
   *        {@link UiWidgetComboboxField#getOptions() options}. These may be loaded asynchronous via
   *        {@link net.sf.mmm.util.lang.api.EnumProvider}.
   * @return the new widget instance.
   */
  <VALUE> UiWidgetComboboxField<VALUE> createComboBox(String label, EnumDefinition<VALUE, ?> enumDefinition);

  /**
   * Creates a new {@link UiWidgetTree} for the given <code>model</code>.
   *
   * @param <NODE> is the generic type of the tree nodes.
   * @param model is the {@link UiTreeModel}.
   * @param title is the {@link UiWidgetTree#getTitle() title} of the tree. See also
   *        {@link UiWidgetTree#isTitleVisible()}.
   * @return the new widget instance.
   */
  <NODE> UiWidgetTree<NODE> createTree(UiTreeModel<NODE> model, String title);

  /**
   * Creates a new {@link UiWidgetListTable} for the given <code>rowType</code>.
   *
   * @param <ROW> is the generic type of <code>rowType</code>.
   * @param rowType is the {@link Class} reflecting a row in the {@link UiWidgetListTable#getValue() value
   *        list}.
   * @return the new widget instance.
   */
  <ROW> UiWidgetListTable<ROW> createListTable(Class<ROW> rowType);

  /**
   * Creates a new {@link UiWidgetSplitPanel}.
   *
   * @param orientation - {@link Orientation#HORIZONTAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel} or
   *        {@link Orientation#VERTICAL} for
   *        {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel}.
   * @param children are the {@link UiWidgetSplitPanel#getChild(String) children}. You need to provide at
   *        least two children.
   * @return the new widget instance.
   */
  UiWidgetSplitPanel createSplitPanel(Orientation orientation, UiWidgetRegular... children);

  /**
   * Creates a new {@link UiWidgetPopup}.
   *
   * @param title is the {@link UiWidgetPopup#getTitle() title}.
   * @return the new widget instance.
   */
  UiWidgetPopup createPopup(String title);

  /**
   * Creates a new {@link UiWidgetPopup}.
   *
   * @param title is the {@link UiWidgetPopup#getTitle() title}.
   * @param closable is the value of {@link UiWidgetPopup#isClosable()}.
   * @param resizable is the value of {@link UiWidgetPopup#isResizable()}.
   * @param movable is the value of {@link UiWidgetPopup#isMovable()}.
   * @return the new widget instance.
   */
  UiWidgetPopup createPopup(String title, boolean closable, boolean resizable, boolean movable);

}
