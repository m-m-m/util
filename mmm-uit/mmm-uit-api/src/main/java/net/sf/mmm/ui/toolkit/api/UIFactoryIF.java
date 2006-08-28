/* $Id: UIFactoryIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.model.UITableModelIF;
import net.sf.mmm.ui.toolkit.api.model.UITreeModelIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UITableIF;
import net.sf.mmm.ui.toolkit.api.widget.UITextFieldIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF;

/**
 * This is the interface for the UI factory. It is used to create the parts of
 * the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFactoryIF extends UIWriteDisposedIF {

    /**
     * This method gets the default display object.
     * 
     * @return the default display.
     */
    UIDisplayIF getDisplay();

    /**
     * This method creates a new resizeable frame.
     * 
     * @param title
     *        is the title for the frame to create.
     * @return the created frame.
     */
    UIFrameIF createFrame(String title);

    /**
     * This method creates a new frame.
     * 
     * @param title
     *        is the title for the frame to create.
     * @param resizeable -
     *        if <code>true</code> the frame will be
     *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable() resizeable}
     * @return the created frame.
     */
    UIFrameIF createFrame(String title, boolean resizeable);

    /**
     * This method creates a new workbench. This feature may not be supported
     * properly by all implementations. Especially only one workbench should be
     * created. Multiple calls to this method may return the same object.
     * 
     * @param title
     *        is the title of the workbench to create.
     * @return the created workbench.
     */
    UIWorkbenchIF createWorkbench(String title);

    /**
     * This method creates a new regular button.
     * 
     * @see #createButton(String, ButtonStyle)
     * 
     * @param text
     *        is the text that explains the action triggered by the button.
     * @return the created button.
     */
    UIButtonIF createButton(String text);

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
    UIButtonIF createButton(UIPictureIF icon, ButtonStyle style);

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
    UIButtonIF createButton(String text, UIPictureIF icon, ButtonStyle style);

    /**
     * This method creates a new button.
     * 
     * @param action
     *        is the action to be represented as button.
     * @return the created button.
     */
    UIButtonIF createButton(ActionIF action);

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
    UIButtonIF createButton(String text, ButtonStyle style);

    /**
     * This method creates a new panel without a border.
     * 
     * @param orientation
     *        is the orientation of the child-components in the panel.
     * @return the created panel.
     */
    UIPanelIF createPanel(Orientation orientation);

    /**
     * This method creates a new panel with a border.
     * 
     * @param orientation
     *        is the orientation of the child-components in the panel.
     * @param borderTitle
     *        is the label of the panels border.
     * @return the created panel.
     */
    UIPanelIF createPanel(Orientation orientation, String borderTitle);

    /**
     * This method creates a new scroll-panel.
     * 
     * @return the created scroll-panel.
     */
    UIScrollPanelIF createScrollPanel();

    /**
     * This method creates a new scroll-panel with the given child inside.
     * 
     * @param child
     *        is the child contained in the scroll-panel.
     * @return the created scroll-panel.
     */
    UIScrollPanelIF createScrollPanel(UICompositeIF child);

    /**
     * This method creates a new split panel.
     * 
     * @param orientation
     *        is the orientation of the child-components in the panel.
     * 
     * @return the created split panel.
     */
    UISplitPanelIF createSplitPanel(Orientation orientation);

    /**
     * This method creates a new tabbed panel.
     * 
     * @return the created tabbed panel.
     */
    UITabbedPanelIF createTabbedPanel();

    /**
     * This method creates a list with single-selection.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model defining the the selectable elements.
     * @return the created list.
     */
    <E> UIListIF<E> createList(UIListModelIF<E> model);

    /**
     * This method creates a list.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model defining the the selectable elements.
     * @param multiSelection
     *        is the value of the
     *        {@link net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF#isMultiSelection() multi-selection-flag}.
     * @return the created list.
     */
    <E> UIListIF<E> createList(UIListModelIF<E> model, boolean multiSelection);

    /**
     * This method creates a combo-box that is NOT editable.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model defining the the selectable elements.
     * @return the created combo-box.
     */
    <E> UIComboBoxIF<E> createComboBox(UIListModelIF<E> model);

    /**
     * This method creates a combo-box.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model defining the the selectable elements.
     * @param editable
     *        is the (initial) value of the
     *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteEditableIF#isEditable() editable-flag}.
     * @return the created combo-box.
     */
    <E> UIComboBoxIF<E> createComboBox(UIListModelIF<E> model, boolean editable);

    /**
     * This method creates a tree. It will have single-selection mode.
     * 
     * @return the created tree.
     */
    UITreeIF createTree();

    /**
     * This method creates a tree.
     * 
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple items of the
     *        tree, else ony one.
     * @return the created tree.
     */
    UITreeIF createTree(boolean multiSelection);

    /**
     * This method creates a tree.
     * 
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple items of the
     *        tree, else ony one.
     * @param model
     *        is the model defining the content of the tree.
     * @return the created tree.
     */
    UITreeIF createTree(boolean multiSelection, UITreeModelIF model);

    /**
     * This method creates a table. It will have single-selection mode.
     * 
     * @return the created table.
     */
    UITableIF createTable();

    /**
     * This method creates a table.
     * 
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple items/cells of
     *        the table, else ony one.
     * @return the created table.
     */
    UITableIF createTable(boolean multiSelection);

    /**
     * This method creates a table.
     * 
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple items/cells of
     *        the table, else ony one.
     * @param model
     *        is the model defining the content of the table.
     * @return the created table.
     */
    UITableIF createTable(boolean multiSelection, UITableModelIF model);

    /**
     * This method creates a new label. The label is initially empty (has no
     * text).
     * 
     * @return the created label.
     */
    UILabelIF createLabel();

    /**
     * This method creates a new label.
     * 
     * @param text
     *        is the initial {@link UILabelIF#getText() label-text}.
     * @return the created label.
     */
    UILabelIF createLabel(String text);

    /**
     * This method creates a new text-field.
     * 
     * @return the created text-field.
     */
    UITextFieldIF createTextField();

    /**
     * This method creates a new spin-box.
     * 
     * @param <E>
     *        is the templated type of the list elements.
     * @param model
     *        is the model defining the the selectable elements.
     * @return the created spin-box.
     */
    <E> UISpinBoxIF<E> createSpinBox(UIListModelIF<E> model);

    /**
     * This method creates a new horizontal slide-bar.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model for the slide bar. Use
     *        {@link net.sf.mmm.ui.toolkit.base.model.UINumericRangeModel} for a
     *        regular slide-bar.
     * @return the created slide-bar.
     */
    <E> UISlideBarIF<E> createSlideBar(UIListModelIF<E> model);

    /**
     * This method creates a new slide-bar.
     * 
     * @param <E>
     *        is the templated type of the elements that can be selected with
     *        the widget.
     * @param model
     *        is the model for the slide bar. Use
     *        {@link net.sf.mmm.ui.toolkit.base.model.UINumericRangeModel} for a
     *        regular slide-bar.
     * @param orientation
     *        defines the axis along which the slide-bar is oriented.
     * @return the created slide-bar.
     */
    <E> UISlideBarIF<E> createSlideBar(UIListModelIF<E> model, Orientation orientation);

    /**
     * This method creates a new progress-bar.
     * 
     * @return the new progress-bar.
     */
    UIProgressBarIF createProgressBar();

    /**
     * This method creates a new progress-bar.
     * 
     * @param orientation
     *        defines the axis along which the progress-bar is oriented.
     * @return the new progress-bar.
     */
    UIProgressBarIF createProgressBar(Orientation orientation);

    /**
     * This method creates a date editor.
     * 
     * @return the new date-editor.
     */
    UIDateEditorIF createDateEditor();

    /**
     * This method creates a new file-download.
     * 
     * @param access
     *        gives access to the file that can be downloaded.
     * @return the new file-download.
     */
    UIFileDownloadIF createFileDownload(FileAccessIF access);

    /**
     * This method create a new file-upload.
     * 
     * @return the new file-upload.
     */
    UIFileUploadIF createFileUpload();

    /**
     * This method creates a picture.
     * 
     * @param imageUrl
     *        is the URL pointing to the according image data.
     * @return the picture object for the image at the given URL.
     * @throws IOException
     *         on I/O error opening or reading data from the given URL.
     */
    UIPictureIF createPicture(URL imageUrl) throws IOException;

    /**
     * This method creates a picture.
     * 
     * @param imageFile
     *        is the image file.
     * @return the picture object for the image in the given file.
     * @throws IOException
     *         on I/O error opening or reading data from the given file.
     */
    UIPictureIF createPicture(File imageFile) throws IOException;

    /**
     * This method creates an action that prints the given component if its
     * {@link ActionIF#getActionListener() listener} is invoked.
     * 
     * @param component
     *        is the component that is to be printed.
     * @return the according action.
     */
    ActionIF createPrintAction(UIComponentIF component);

    /**
     * This method creates an action that prints the given component if its
     * {@link ActionIF#getActionListener() listener} is invoked.
     * 
     * @param component
     *        is the component that is to be printed.
     * @param actionName
     *        is the {@link ActionIF#getName() name} of the print action.
     * @return the according action.
     */
    ActionIF createPrintAction(UIComponentIF component, String actionName);

    /**
     * This method creates an action that prints the given component if its
     * {@link ActionIF#getActionListener() listener} is invoked.
     * 
     * @param component
     *        is the component that is to be printed.
     * @param actionName
     *        is the {@link ActionIF#getName() name} of the print action.
     * @param jobName
     *        is the name of the print job.
     * @return the according action.
     */
    ActionIF createPrintAction(UIComponentIF component, String actionName, String jobName);

}