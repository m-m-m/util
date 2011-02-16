/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteDisposed;
import net.sf.mmm.ui.toolkit.api.view.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.composite.UiDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabbedPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;

/**
 * This is the interface for the UI factory. It is used to create the parts of
 * the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UIFactoryRenamed extends UiWriteDisposed {

  /**
   * This method gets the locale currently applied to this factory. The locale
   * contains the language and country settings and is used for nationalization
   * of the GUI elements.
   * 
   * @return the locale of this factory.
   */
  Locale getLocale();

  /**
   * This method sets the locale of this factory. This will cause a
   * nationalization of all existing GUI elements created by this factory, that
   * are currently attached to a window. The nationalization will cause
   * translation of the texts (as long as you provide them as
   * {@link net.sf.mmm.util.nls.api.NlsMessage}) as well as the
   * {@link #getScriptOrientation() script-orientation}.
   * 
   * @param locale is the locale to set.
   */
  void setLocale(Locale locale);

  /**
   * This method gets the orientation of the users script. This is taken into
   * account when rendering texts as well as when calculating the layout.
   * Therefore menu-items or widgets located inside a panel may appear
   * left-to-right or vice versa according to the current script-orientation.
   * 
   * @return the script-orientation of the user.
   */
  ScriptOrientation getScriptOrientation();

  /**
   * This method sets the users script-orientation. You should set this value at
   * the beginning before creating your GUI with this factory. If you set this
   * option at a later time, all existing {@link UiObject GUI-Objects} created
   * by this factory will be refreshed. Depending on the implementation of this
   * toolkit this may be an expensive operation, needs to dispose and rebuild
   * all GUI elements and can cause the loss of data entered by the user.
   * 
   * @see #setLocale(Locale)
   * 
   * @param scriptOrientation is the orientation to set.
   */
  void setScriptOrientation(ScriptOrientation scriptOrientation);

  /**
   * This method gets the script-orientation of the design of the GUI. This
   * property allows the creator of the GUI to design in the orientation
   * according to his script so it feels native.<br>
   * If the {@link #getScriptOrientation() script-orientation} of the user is
   * different to the designed orientation the layout is automatically mirrored
   * accordingly.
   * 
   * @see #getScriptOrientation()
   * 
   * @return the script-orientation of the designer.
   */
  ScriptOrientation getDesignOrientation();

  /**
   * This method sets the
   * {@link #getDesignOrientation() designed script-orientation}. You should
   * set this value at the beginning before creating {@link UiNode UI nodes}
   * with this factory.
   * 
   * @param orientation is the script-orientation of the designer.
   */
  void setDesignOrientation(ScriptOrientation orientation);

  /**
   * This method gets the default display object.
   * 
   * @return the default display.
   */
  UIDisplay getDisplay();

  /**
   * This method creates a new resizeable frame.
   * 
   * @param title is the title for the frame to create.
   * @return the created frame.
   */
  UIFrame createFrame(String title);

  /**
   * This method creates a new frame.
   * 
   * @param title is the title for the frame to create.
   * @param resizeable - if <code>true</code> the frame will be
   *        {@link net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize#isResizeable() resizeable}
   * @return the created frame.
   */
  UIFrame createFrame(String title, boolean resizeable);

  /**
   * This method creates a new workbench. This feature may not be supported
   * properly by all implementations. Especially only one workbench should be
   * created. Multiple calls to this method may return the same object.
   * 
   * @param title is the title of the workbench to create.
   * @return the created workbench.
   */
  UIWorkbench createWorkbench(String title);

  /**
   * This method creates a new regular button.
   * 
   * @see #createButton(String, ButtonStyle)
   * 
   * @param text is the text that explains the action triggered by the button.
   * @return the created button.
   */
  UiButton createButton(String text);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param icon is the picture symbolizing the action triggered by the button.
   *        It will be shown inside the button.
   * @param style is the style of the button.
   * @return the created button.
   */
  UiButton createButton(UiImage icon, ButtonStyle style);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param text is the text that explains the action triggered by the button.
   *        It will be shown inside the button.
   * @param icon is the picture symbolizing the action triggered by the button.
   *        It will be shown inside the button.
   * @param style is the style of the button.
   * @return the created button.
   */
  UiButton createButton(String text, UiImage icon, ButtonStyle style);

  /**
   * This method creates a new button.
   * 
   * @param action is the action to be represented as button.
   * @return the created button.
   */
  UiButton createButton(Action action);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param text is the text that explains the action triggered by the button.
   *        It will be shown inside the button.
   * @param style is the style of the button.
   * @return the created button.
   */
  UiButton createButton(String text, ButtonStyle style);

  /**
   * This method creates a new panel without a border.
   * 
   * @param orientation is the orientation of the child-components in the panel.
   * @return the created panel.
   */
  UiSlicePanel createPanel(Orientation orientation);

  /**
   * This method creates a new {@link UiSlicePanel panel} with a border.
   * 
   * @param orientation is the orientation of the child-components in the panel.
   * @param borderTitle is the label of the panels border.
   * @return the created panel.
   */
  UiSlicePanel createPanel(Orientation orientation, String borderTitle);

  /**
   * This method creates a {@link UiDecoratedComponent decorated component} that
   * bundles the given <code>component</code> together with a
   * <code>decorator</code>. The result can be easily
   * {@link UiSlicePanel#addComponent(UiElement) added} to a
   * {@link UiSlicePanel panel}.
   * 
   * @param <D> is the templated type of the <code>decorator</code>.
   * @param <C> is the templated type of the <code>component</code>.
   * 
   * @param decorator is the decorating component.
   * @param component is the main component.
   * @return the decorated component.
   */
  <D extends UiElement, C extends UiElement> UiDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component);

  /**
   * This method creates a {@link UiDecoratedComponent decorated component} that
   * bundles the given <code>component</code> together with a
   * {@link UiLabel label}. The result can be easily
   * {@link UiSlicePanel#addComponent(UiElement) added} to a
   * {@link UiSlicePanel panel}.
   * 
   * @param <C> is the templated type of the <code>component</code>.
   * 
   * @param label is the label text.
   * @param component is the component.
   * @return the labeled component.
   */
  <C extends UiElement> UiDecoratedComponent<UiLabel, C> createLabeledComponent(String label,
      C component);

  /**
   * This method creates a {@link UiDecoratedComponent decorated component} that
   * puts the given <code>components</code> into a
   * {@link Orientation#HORIZONTAL horizontal} {@link UiSlicePanel panel} and
   * bundles it together with a {@link UiLabel label}. The result can be easily
   * {@link UiSlicePanel#addComponent(UiElement) added} to a
   * {@link UiSlicePanel panel}.
   * 
   * @param label is the label text.
   * @param components are the components (should be at least two to make
   *        sense).
   * @return the labeled component.
   */
  UiDecoratedComponent<UiLabel, UiSlicePanel> createLabeledComponents(String label,
      UiElement... components);

  /**
   * This method creates a new scroll-panel.
   * 
   * @return the created scroll-panel.
   */
  UiScrollPanel createScrollPanel();

  /**
   * This method creates a new scroll-panel with the given child inside.
   * 
   * @param child is the child contained in the scroll-panel.
   * @return the created scroll-panel.
   */
  UiScrollPanel createScrollPanel(UiComposite child);

  /**
   * This method creates a new split panel.
   * 
   * @param orientation is the orientation of the child-components in the panel.
   * 
   * @return the created split panel.
   */
  UiSplitPanel createSplitPanel(Orientation orientation);

  /**
   * This method creates a new tabbed panel.
   * 
   * @return the created tabbed panel.
   */
  UiTabbedPanel createTabbedPanel();

  /**
   * This method creates a list with single-selection.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model defining the the selectable elements.
   * @return the created list.
   */
  <E> UiList<E> createList(UiListMvcModel<E> model);

  /**
   * This method creates a list.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model defining the the selectable elements.
   * @param multiSelection is the value of the
   *        {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadMultiSelection#isMultiSelection() multi-selection-flag}.
   * @return the created list.
   */
  <E> UiList<E> createList(UiListMvcModel<E> model, boolean multiSelection);

  /**
   * This method creates a combo-box that is NOT editable.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model defining the the selectable elements.
   * @return the created combo-box.
   */
  <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model);

  /**
   * This method creates a combo-box.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model defining the the selectable elements.
   * @param editable is the (initial) value of the
   *        {@link net.sf.mmm.ui.toolkit.api.attribute.UiWriteEditable#isEditable() editable-flag}.
   * @return the created combo-box.
   */
  <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model, boolean editable);

  /**
   * This method creates a tree. It will have single-selection mode.
   * 
   * @return the created tree.
   */
  UiTree<?> createTree();

  /**
   * This method creates a tree.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items of the tree, else only one.
   * @return the created tree.
   */
  UiTree<?> createTree(boolean multiSelection);

  /**
   * This method creates a tree.
   * 
   * @param <N> is the templated type of the tree-nodes.
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items of the tree, else only one.
   * @param model is the model defining the content of the tree.
   * @return the created tree.
   */
  <N> UiTree<N> createTree(boolean multiSelection, UiTreeMvcModel<N> model);

  /**
   * This method creates a table. It will have single-selection mode.
   * 
   * @return the created table.
   */
  UiTable<?> createTable();

  /**
   * This method creates a table.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items/cells of the table, else only one.
   * @return the created table.
   */
  UiTable<?> createTable(boolean multiSelection);

  /**
   * This method creates a table.
   * 
   * @param <C> is the templated type of the objects in the table cells.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items/cells of the table, else only one.
   * @param model is the model defining the content of the table.
   * @return the created table.
   */
  <C> UiTable<C> createTable(boolean multiSelection, UiTableMvcModel<C> model);

  /**
   * This method creates a new label. The label is initially empty (has no
   * text).
   * 
   * @return the created label.
   */
  UiLabel createLabel();

  /**
   * This method creates a new label.
   * 
   * @param text is the initial {@link UiLabel#getText() label-text}.
   * @return the created label.
   */
  UiLabel createLabel(String text);

  /**
   * This method creates a new text-field.
   * 
   * @return the created text-field.
   */
  UiTextField createTextField();

  /**
   * This method creates a new text-field.
   * 
   * @param editable is the {@link UiTextField#isEditable() editable flag} of
   *        the text field.
   * @return the created text-field.
   */
  UiTextField createTextField(boolean editable);

  /**
   * This method creates a new spin-box.
   * 
   * @param <E> is the templated type of the list elements.
   * @param model is the model defining the the selectable elements.
   * @return the created spin-box.
   */
  <E> UiSpinBox<E> createSpinBox(UiListMvcModel<E> model);

  /**
   * This method creates a new horizontal slide-bar.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model for the slide bar. Use
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel} for a
   *        regular slide-bar.
   * @return the created slide-bar.
   */
  <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model);

  /**
   * This method creates a new slide-bar.
   * 
   * @param <E> is the templated type of the elements that can be selected with
   *        the widget.
   * @param model is the model for the slide bar. Use
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel} for a
   *        regular slide-bar.
   * @param orientation defines the axis along which the slide-bar is oriented.
   * @return the created slide-bar.
   */
  <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model, Orientation orientation);

  /**
   * This method creates a new progress-bar.
   * 
   * @return the new progress-bar.
   */
  UiProgressBar createProgressBar();

  /**
   * This method creates a new progress-bar.
   * 
   * @param orientation defines the axis along which the progress-bar is
   *        oriented.
   * @return the new progress-bar.
   */
  UiProgressBar createProgressBar(Orientation orientation);

  /**
   * This method creates a date editor.
   * 
   * @return the new date-editor.
   */
  UIDateEditor createDateEditor();

  /**
   * This method creates a new file-download.
   * 
   * @param access gives access to the file that can be downloaded.
   * @return the new file-download.
   */
  UiFileDownload createFileDownload(FileAccess access);

  /**
   * This method create a new file-upload.
   * 
   * @return the new file-upload.
   */
  UiFileUpload createFileUpload();

  /**
   * This method creates a picture.
   * 
   * @param imageUrl is the URL pointing to the according image data.
   * @return the picture object for the image at the given URL.
   * @throws IOException on I/O error opening or reading data from the given
   *         URL.
   */
  UiImage createPicture(URL imageUrl) throws IOException;

  /**
   * This method creates a picture.
   * 
   * @param imageFile is the image file.
   * @return the picture object for the image in the given file.
   * @throws IOException on I/O error opening or reading data from the given
   *         file.
   */
  UiImage createPicture(File imageFile) throws IOException;

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component is the component that is to be printed.
   * @return the according action.
   */
  Action createPrintAction(UiElement component);

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component is the component that is to be printed.
   * @param actionName is the {@link Action#getName() name} of the print action.
   * @return the according action.
   */
  Action createPrintAction(UiElement component, String actionName);

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component is the component that is to be printed.
   * @param actionName is the {@link Action#getName() name} of the print action.
   * @param jobName is the name of the print job.
   * @return the according action.
   */
  Action createPrintAction(UiElement component, String actionName, String jobName);

}
