/* $Id$ */
package net.sf.mmm.ui.toolkit.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;

/**
 * This is the interface for the UI factory. It is used to create the parts of
 * the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFactory extends UIWriteDisposed {

  /**
   * This method gets the default display object.
   * 
   * @return the default display.
   */
  UIDisplay getDisplay();

  /**
   * This method creates a new resizeable frame.
   * 
   * @param title
   *        is the title for the frame to create.
   * @return the created frame.
   */
  UIFrame createFrame(String title);

  /**
   * This method creates a new frame.
   * 
   * @param title
   *        is the title for the frame to create.
   * @param resizeable -
   *        if <code>true</code> the frame will be
   *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable() resizeable}
   * @return the created frame.
   */
  UIFrame createFrame(String title, boolean resizeable);

  /**
   * This method creates a new workbench. This feature may not be supported
   * properly by all implementations. Especially only one workbench should be
   * created. Multiple calls to this method may return the same object.
   * 
   * @param title
   *        is the title of the workbench to create.
   * @return the created workbench.
   */
  UIWorkbench createWorkbench(String title);

  /**
   * This method creates a new regular button.
   * 
   * @see #createButton(String, ButtonStyle)
   * 
   * @param text
   *        is the text that explains the action triggered by the button.
   * @return the created button.
   */
  UIButton createButton(String text);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param icon
   *        is the picture symbolizing the action triggered by the button. It
   *        will be shown inside the button.
   * @param style
   *        is the style of the button.
   * @return the created button.
   */
  UIButton createButton(UIPicture icon, ButtonStyle style);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param text
   *        is the text that explains the action triggered by the button. It
   *        will be shown inside the button.
   * @param icon
   *        is the picture symbolizing the action triggered by the button. It
   *        will be shown inside the button.
   * @param style
   *        is the style of the button.
   * @return the created button.
   */
  UIButton createButton(String text, UIPicture icon, ButtonStyle style);

  /**
   * This method creates a new button.
   * 
   * @param action
   *        is the action to be represented as button.
   * @return the created button.
   */
  UIButton createButton(Action action);

  /**
   * This method creates a new button. According to the style this can be a
   * radio-, toggle-, check- or a regular button.
   * 
   * @param text
   *        is the text that explains the action triggered by the button. It
   *        will be shown inside the button.
   * @param style
   *        is the style of the button.
   * @return the created button.
   */
  UIButton createButton(String text, ButtonStyle style);

  /**
   * This method creates a new panel without a border.
   * 
   * @param orientation
   *        is the orientation of the child-components in the panel.
   * @return the created panel.
   */
  UIPanel createPanel(Orientation orientation);

  /**
   * This method creates a new {@link UIPanel panel} with a border.
   * 
   * @param orientation
   *        is the orientation of the child-components in the panel.
   * @param borderTitle
   *        is the label of the panels border.
   * @return the created panel.
   */
  UIPanel createPanel(Orientation orientation, String borderTitle);

  /**
   * This method creates a {@link UIDecoratedComponent decorated component} that
   * bundles the given <code>component</code> together with a
   * <code>decorator</code>. The result can be easily
   * {@link UIPanel#addComponent(UIComponent) added} to a {@link UIPanel panel}.
   * 
   * @param <D>
   *        is the templated type of the <code>decorator</code>.
   * @param <C>
   *        is the templated type of the <code>component</code>.
   * 
   * @param decorator
   *        is the decorating component.
   * @param component
   *        is the main component.
   * @return the decorated component.
   */
  <D extends UIComponent, C extends UIComponent> UIDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component);

  /**
   * This method creates a {@link UIDecoratedComponent decorated component} that
   * bundles the given <code>component</code> together with a
   * {@link UILabel label}. The result can be easily
   * {@link UIPanel#addComponent(UIComponent) added} to a {@link UIPanel panel}.
   * 
   * @param <C>
   *        is the templated type of the <code>component</code>.
   * 
   * @param label
   *        is the label text.
   * @param component
   *        is the component.
   * @return the labeled component.
   */
  <C extends UIComponent> UIDecoratedComponent<UILabel, C> createLabeledComponent(String label,
      C component);

  /**
   * This method creates a {@link UIDecoratedComponent decorated component} that
   * puts the given <code>components</code> into a
   * {@link Orientation#HORIZONTAL horizontal} {@link UIPanel panel} and bundles
   * it together with a {@link UILabel label}. The result can be easily
   * {@link UIPanel#addComponent(UIComponent) added} to a {@link UIPanel panel}.
   * 
   * @param label
   *        is the label text.
   * @param components
   *        are the components (should be at least two to make sense).
   * @return the labeled component.
   */
  UIDecoratedComponent<UILabel, UIPanel> createLabeledComponents(String label,
      UIComponent... components);

  /**
   * This method creates a new scroll-panel.
   * 
   * @return the created scroll-panel.
   */
  UIScrollPanel createScrollPanel();

  /**
   * This method creates a new scroll-panel with the given child inside.
   * 
   * @param child
   *        is the child contained in the scroll-panel.
   * @return the created scroll-panel.
   */
  UIScrollPanel createScrollPanel(UIComposite child);

  /**
   * This method creates a new split panel.
   * 
   * @param orientation
   *        is the orientation of the child-components in the panel.
   * 
   * @return the created split panel.
   */
  UISplitPanel createSplitPanel(Orientation orientation);

  /**
   * This method creates a new tabbed panel.
   * 
   * @return the created tabbed panel.
   */
  UITabbedPanel createTabbedPanel();

  /**
   * This method creates a list with single-selection.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model defining the the selectable elements.
   * @return the created list.
   */
  <E> UIList<E> createList(UIListModel<E> model);

  /**
   * This method creates a list.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model defining the the selectable elements.
   * @param multiSelection
   *        is the value of the
   *        {@link net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection() multi-selection-flag}.
   * @return the created list.
   */
  <E> UIList<E> createList(UIListModel<E> model, boolean multiSelection);

  /**
   * This method creates a combo-box that is NOT editable.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model defining the the selectable elements.
   * @return the created combo-box.
   */
  <E> UIComboBox<E> createComboBox(UIListModel<E> model);

  /**
   * This method creates a combo-box.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model defining the the selectable elements.
   * @param editable
   *        is the (initial) value of the
   *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable() editable-flag}.
   * @return the created combo-box.
   */
  <E> UIComboBox<E> createComboBox(UIListModel<E> model, boolean editable);

  /**
   * This method creates a tree. It will have single-selection mode.
   * 
   * @return the created tree.
   */
  UITree<?> createTree();

  /**
   * This method creates a tree.
   * 
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items of the
   *        tree, else ony one.
   * @return the created tree.
   */
  UITree<?> createTree(boolean multiSelection);

  /**
   * This method creates a tree.
   * 
   * @param <N>
   *        is the templated type of the tree-nodes.
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items of the
   *        tree, else ony one.
   * @param model
   *        is the model defining the content of the tree.
   * @return the created tree.
   */
  <N> UITree<N> createTree(boolean multiSelection, UITreeModel<N> model);

  /**
   * This method creates a table. It will have single-selection mode.
   * 
   * @return the created table.
   */
  UITable<?> createTable();

  /**
   * This method creates a table.
   * 
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items/cells of
   *        the table, else ony one.
   * @return the created table.
   */
  UITable<?> createTable(boolean multiSelection);

  /**
   * This method creates a table.
   * 
   * @param <C>
   *        is the templated type of the objects in the table cells.
   * 
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items/cells of
   *        the table, else ony one.
   * @param model
   *        is the model defining the content of the table.
   * @return the created table.
   */
  <C> UITable<C> createTable(boolean multiSelection, UITableModel<C> model);

  /**
   * This method creates a new label. The label is initially empty (has no
   * text).
   * 
   * @return the created label.
   */
  UILabel createLabel();

  /**
   * This method creates a new label.
   * 
   * @param text
   *        is the initial {@link UILabel#getText() label-text}.
   * @return the created label.
   */
  UILabel createLabel(String text);

  /**
   * This method creates a new text-field.
   * 
   * @return the created text-field.
   */
  UITextField createTextField();

  /**
   * This method creates a new text-field.
   * 
   * @param editable
   *        is the {@link UITextField#isEditable() editable flag} of the text
   *        field.
   * @return the created text-field.
   */
  UITextField createTextField(boolean editable);

  /**
   * This method creates a new spin-box.
   * 
   * @param <E>
   *        is the templated type of the list elements.
   * @param model
   *        is the model defining the the selectable elements.
   * @return the created spin-box.
   */
  <E> UISpinBox<E> createSpinBox(UIListModel<E> model);

  /**
   * This method creates a new horizontal slide-bar.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model for the slide bar. Use
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel} for a
   *        regular slide-bar.
   * @return the created slide-bar.
   */
  <E> UISlideBar<E> createSlideBar(UIListModel<E> model);

  /**
   * This method creates a new slide-bar.
   * 
   * @param <E>
   *        is the templated type of the elements that can be selected with the
   *        widget.
   * @param model
   *        is the model for the slide bar. Use
   *        {@link net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel} for a
   *        regular slide-bar.
   * @param orientation
   *        defines the axis along which the slide-bar is oriented.
   * @return the created slide-bar.
   */
  <E> UISlideBar<E> createSlideBar(UIListModel<E> model, Orientation orientation);

  /**
   * This method creates a new progress-bar.
   * 
   * @return the new progress-bar.
   */
  UIProgressBar createProgressBar();

  /**
   * This method creates a new progress-bar.
   * 
   * @param orientation
   *        defines the axis along which the progress-bar is oriented.
   * @return the new progress-bar.
   */
  UIProgressBar createProgressBar(Orientation orientation);

  /**
   * This method creates a date editor.
   * 
   * @return the new date-editor.
   */
  UIDateEditor createDateEditor();

  /**
   * This method creates a new file-download.
   * 
   * @param access
   *        gives access to the file that can be downloaded.
   * @return the new file-download.
   */
  UIFileDownload createFileDownload(FileAccess access);

  /**
   * This method create a new file-upload.
   * 
   * @return the new file-upload.
   */
  UIFileUpload createFileUpload();

  /**
   * This method creates a picture.
   * 
   * @param imageUrl
   *        is the URL pointing to the according image data.
   * @return the picture object for the image at the given URL.
   * @throws IOException
   *         on I/O error opening or reading data from the given URL.
   */
  UIPicture createPicture(URL imageUrl) throws IOException;

  /**
   * This method creates a picture.
   * 
   * @param imageFile
   *        is the image file.
   * @return the picture object for the image in the given file.
   * @throws IOException
   *         on I/O error opening or reading data from the given file.
   */
  UIPicture createPicture(File imageFile) throws IOException;

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component
   *        is the component that is to be printed.
   * @return the according action.
   */
  Action createPrintAction(UIComponent component);

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component
   *        is the component that is to be printed.
   * @param actionName
   *        is the {@link Action#getName() name} of the print action.
   * @return the according action.
   */
  Action createPrintAction(UIComponent component, String actionName);

  /**
   * This method creates an action that prints the given component if its
   * {@link Action#getActionListener() listener} is invoked.
   * 
   * @param component
   *        is the component that is to be printed.
   * @param actionName
   *        is the {@link Action#getName() name} of the print action.
   * @param jobName
   *        is the name of the print job.
   * @return the according action.
   */
  Action createPrintAction(UIComponent component, String actionName, String jobName);

}
