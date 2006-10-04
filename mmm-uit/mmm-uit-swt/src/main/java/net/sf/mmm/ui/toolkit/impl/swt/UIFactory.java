/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
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
import net.sf.mmm.ui.toolkit.base.UIAbstractFactory;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIPanel;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.impl.swt.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SwtWorkerThread;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIButton;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UILabel;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIList;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITable;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITextField;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITree;
import net.sf.mmm.ui.toolkit.impl.swt.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIWorkbench;

/**
 * This class is the implementation of the UIFactoryIF interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFactory extends UIAbstractFactory {

    /** the display */
    private UIDisplay display;

    /** the actual SWT display */
    private Display swtDisplay;

    /** the worker thread */
    private SwtWorkerThread worker;

    /**
     * The dummy constructor.
     * 
     * This constructor may be used for testing if an instance is required for
     * the default display without using the
     * {@link net.sf.mmm.ui.toolkit.api.UIServiceIF UIServiceIF}.
     */
    public UIFactory() {

        super();
        this.worker = new SwtWorkerThread();
        this.worker.start();
        this.swtDisplay = this.worker.getDisplay();
        Monitor m = this.worker.getMonitor();
        this.display = new UIDisplay(this, new UIDevice(m), this.swtDisplay);
    }

    /**
     * The constructor.
     * 
     * @param SWTDisplay
     *        is the display to use.
     */
    public UIFactory(Display SWTDisplay) {

        this(SWTDisplay, new UIDevice(SWTDisplay.getPrimaryMonitor()));
    }

    /**
     * The constructor.
     * 
     * @param SWTDisplay
     *        is the display to use.
     * @param uiDevice
     *        is the graphics device the display of this factory belongs to.
     */
    public UIFactory(Display SWTDisplay, UIDevice uiDevice) {

        super();
        this.swtDisplay = SWTDisplay;
        this.display = new UIDisplay(this, uiDevice, this.swtDisplay);
    }

    /**
     * This method gets the dummy parent used for UI objects that should have no
     * parent (<code>null</code>).
     * 
     * @return the SWT dummy parent.
     * @deprecated this hack should be removed!
     */
    public Shell getDummyParent() {

        return this.worker.getDummyParent();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFrame(java.lang.String,
     *      boolean)
     * 
     */
    public UIFrameIF createFrame(String title, boolean resizeable) {

        UIFrame frame = new UIFrame(UIFactory.this, null, resizeable);
        frame.setTitle(title);
        return frame;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * 
     */
    public UIButtonIF createButton(String text, UIPictureIF icon, ButtonStyle style) {

        UIButtonIF button = new UIButton(this, null, style);
        button.setText(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        return button;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation,
     *      java.lang.String)
     * 
     */
    public UIPanelIF createPanel(Orientation orientation, String borderTitle) {

        UIPanelIF panel = new UIPanel(this, null, borderTitle, orientation);
        return panel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createScrollPanel(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * 
     */
    public UIScrollPanelIF createScrollPanel(UICompositeIF child) {

        UIScrollPanelIF panel = new UIScrollPanel(this, null);
        if (child != null) {
            panel.setComponent(child);
        }
        return panel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSplitPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * 
     */
    public UISplitPanelIF createSplitPanel(Orientation orientation) {

        UISplitPanelIF splitPanel = new UISplitPanel(this, null, null, orientation);
        return splitPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createList(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      boolean)
     * 
     */
    public <E> UIListIF<E> createList(UIListModelIF<E> model, boolean multiSelection) {

        UIListIF<E> list = new UIList<E>(this, null, multiSelection, model);
        return list;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTree(boolean,
     *      net.sf.mmm.ui.toolkit.api.model.UITreeModelIF)
     * 
     */
    public UITreeIF createTree(boolean multiSelection, UITreeModelIF model) {

        UITree tree = new UITree(this, null, multiSelection);
        if (model != null) {
            tree.setModel(model);
        }
        return tree;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTable(boolean,
     *      net.sf.mmm.ui.toolkit.api.model.UITableModelIF)
     * 
     */
    public UITableIF createTable(boolean multiSelection, UITableModelIF model) {

        UITable table = new UITable(this, null, multiSelection);
        if (model != null) {
            table.setModel(model);
        }
        return table;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#getDisplay()
     * 
     */
    public UIDisplay getDisplay() {

        return this.display;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createLabel(java.lang.String)
     * 
     */
    public UILabelIF createLabel(String text) {

        UILabelIF label = new UILabel(this, null);
        label.setText(text);
        return label;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTextField()
     * 
     */
    public UITextFieldIF createTextField() {

        return new UITextField(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSpinBox(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * 
     */
    public <E> UISpinBoxIF<E> createSpinBox(UIListModelIF<E> model) {

        return new UISpinBox<E>(this, null, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFileDownload(net.sf.mmm.ui.toolkit.api.feature.FileAccessIF)
     * 
     */
    public UIFileDownloadIF createFileDownload(FileAccessIF access) {

        return new UIFileDownload(this, null, access);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFileUpload()
     * 
     */
    public UIFileUploadIF createFileUpload() {

        return new UIFileUpload(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPicture(java.net.URL)
     * 
     */
    public UIPictureIF createPicture(URL imageUrl) throws IOException {

        return new UIPicture(this, imageUrl);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createComboBox(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      boolean)
     * 
     */
    public <E> UIComboBoxIF<E> createComboBox(UIListModelIF<E> model, boolean editableFlag) {

        return new UIComboBox<E>(this, null, editableFlag, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTabbedPanel()
     * 
     */
    public UITabbedPanelIF createTabbedPanel() {

        return new UITabbedPanel(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSlideBar(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * 
     */
    public <E> UISlideBarIF<E> createSlideBar(UIListModelIF<E> model, Orientation orientation) {

        // TODO
        return new UISlideBar<E>(this, null, orientation, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createProgressBar(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * 
     */
    public UIProgressBarIF createProgressBar(Orientation orientation) {

        return new UIProgressBar(this, null, orientation, false);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createWorkbench(java.lang.String)
     * 
     */
    public UIWorkbenchIF createWorkbench(String title) {

        UIWorkbench workbench = new UIWorkbench(this);
        workbench.setTitle(title);
        return workbench;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      java.lang.String, java.lang.String)
     * 
     */
    public ActionIF createPrintAction(UIComponentIF component, String actionName, String jobName) {

        return new PrintAction((UIComponent) component, actionName, jobName);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createDateEditor()
     * 
     */
    public UIDateEditorIF createDateEditor() {

        return new UIDateEditor(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#dispose()
     * 
     */
    @Override
    public void dispose() {

        this.worker.dispose();
        super.dispose();
    }

    /**
     * This method converts the given <code>buttonStyle</code> to the
     * according {@link org.eclipse.swt.SWT} constant for a {@link MenuItem}.
     * 
     * @param buttonStyle
     *        is the button-style to convert.
     * @return the according SWT button style.
     */
    public static int convertButtonStyleForMenuItem(ButtonStyle buttonStyle) {

        int result = convertButtonStyle(buttonStyle);
        if (result == SWT.NORMAL) {
            return SWT.CASCADE;
        } else {
            return result;
        }
    }

    /**
     * This method converts the given <code>buttonStyle</code> to the
     * according {@link org.eclipse.swt.SWT} constant for a
     * {@link org.eclipse.swt.widgets.Button}.
     * 
     * @param buttonStyle
     *        is the button-style to convert.
     * @return the according SWT button style.
     */
    public static int convertButtonStyle(ButtonStyle buttonStyle) {

        switch (buttonStyle) {
            case DEFAULT:
                return SWT.DEFAULT;
            case CHECK:
                return SWT.CHECK;
            case RADIO:
                return SWT.RADIO;
            case TOGGLE:
                return SWT.TOGGLE;
            default :
                throw new IllegalArgumentException("Unknown style " + buttonStyle);
        }
    }

    /**
     * This method converts the given <code>orientation</code> to the
     * according {@link org.eclipse.swt.SWT} constant.
     * 
     * @param orientation
     *        is the orientation to convert.
     * @return the according SWT style.
     */
    public static int convertOrientation(Orientation orientation) {

        switch (orientation) {
            case HORIZONTAL:
                return SWT.HORIZONTAL;
            case VERTICAL:
                return SWT.VERTICAL;
            default :
                throw new IllegalArgumentException("Unknown orientation " + orientation);
        }
    }

}