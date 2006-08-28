/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UIDisplayIF;
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
import net.sf.mmm.ui.toolkit.impl.awt.UIDevice;
import net.sf.mmm.ui.toolkit.impl.awt.UIDisplay;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIPanel;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIButton;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UILabel;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIList;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITable;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITextField;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITree;
import net.sf.mmm.ui.toolkit.impl.swing.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbench;

/**
 * This class is the implementation of the UIFactoryIF interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFactory extends UIAbstractFactory {

    /** the default display */
    private UIDisplay display;
    
    /**
     * The dummy constructor.
     * 
     * This constructor may be used for testing if an instance is required for
     * the default display without using the
     * {@link net.sf.mmm.ui.toolkit.api.UIServiceIF UIServiceIF}.
     */
    public UIFactory() {

        super();
        GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        UIDevice uiDevice = new UIDevice(defaultDevice);
        this.display = new UIDisplay(this, uiDevice, defaultDevice.getDefaultConfiguration());
    }

    /**
     * The constructor.
     * 
     * @param uiDevice
     *        is the device the display of this factory belongs to.
     * @param graphicConfiguration
     *        is the graphics configuration for the diplay to represent.
     */
    public UIFactory(UIDevice uiDevice, GraphicsConfiguration graphicConfiguration) {

        super();
        this.display = new UIDisplay(this, uiDevice, graphicConfiguration);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#dispatch()
     * {@inheritDoc}
     */
    public void dispatch() {

    // nothing to do here!
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#getDisplay()
     * {@inheritDoc}
     */
    public UIDisplayIF getDisplay() {

        return this.display;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#getDisplay()
     * 
     * @return the AWT display.
     */
    public GraphicsConfiguration getAwtGc() {

        return this.display.getGraphicsConfiguration();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFrame(java.lang.String,
     *      boolean)
     * {@inheritDoc}
     */
    public UIFrameIF createFrame(String title, boolean resizeable) {

        return new UIFrame(this, null, title, resizeable);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.UIPictureIF,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
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
     * {@inheritDoc}
     */
    public UIPanelIF createPanel(Orientation orientation, String text) {

        UIPanel panel = new UIPanel(this, null, orientation);
        panel.setBorderTitle(text);
        return panel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createScrollPanel(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * {@inheritDoc}
     */
    public UIScrollPanelIF createScrollPanel(UICompositeIF child) {

        UIScrollPanelIF scrollPanel = new UIScrollPanel(this, null);
        scrollPanel.setComponent(child);
        return scrollPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createList(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      boolean)
     * {@inheritDoc}
     */
    public <E> UIListIF<E> createList(UIListModelIF<E> model, boolean multiSelection) {

        UIList<E> list = new UIList<E>(this, null);
        list.setMultiSelection(multiSelection);
        if (model != null) {
            list.setModel(model);
        }
        return list;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createComboBox(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      boolean)
     * {@inheritDoc}
     */
    public <E> UIComboBoxIF<E> createComboBox(UIListModelIF<E> model, boolean editableFlag) {

        UIComboBoxIF<E> comboBox = new UIComboBox<E>(this, null);
        comboBox.setEditable(editableFlag);
        if (model != null) {
            comboBox.setModel(model);
        }
        return comboBox;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTree(boolean,
     *      net.sf.mmm.ui.toolkit.api.model.UITreeModelIF)
     * {@inheritDoc}
     */
    public UITreeIF createTree(boolean multiSelection, UITreeModelIF model) {

        UITree tree = new UITree(this, null);
        tree.setMultiSelection(multiSelection);
        if (model != null) {
            tree.setModel(model);
        }
        return tree;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTable(boolean,
     *      net.sf.mmm.ui.toolkit.api.model.UITableModelIF)
     * {@inheritDoc}
     */
    public UITableIF createTable(boolean multiSelection, UITableModelIF model) {

        UITable table = new UITable(this, null);
        // table.setMultiSelection(multiSelection);
        if (model != null) {
            table.setModel(model);
        }
        return table;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createLabel(java.lang.String)
     * {@inheritDoc}
     */
    public UILabelIF createLabel(String text) {

        UILabelIF label = new UILabel(this, null);
        label.setText(text);
        return label;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTextField()
     * {@inheritDoc}
     */
    public UITextFieldIF createTextField() {

        return new UITextField(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSpinBox(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public <E> UISpinBoxIF<E> createSpinBox(UIListModelIF<E> model) {

        return new UISpinBox<E>(this, null, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFileDownload(net.sf.mmm.ui.toolkit.api.feature.FileAccessIF)
     * {@inheritDoc}
     */
    public UIFileDownloadIF createFileDownload(FileAccessIF access) {

        return new UIFileDownload(this, null, access);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFileUpload()
     * {@inheritDoc}
     */
    public UIFileUploadIF createFileUpload() {

        return new UIFileUpload(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPicture(java.net.URL)
     * {@inheritDoc}
     */
    public UIPictureIF createPicture(URL imageUrl) {

        return new UIPicture(this, imageUrl);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTabbedPanel()
     * {@inheritDoc}
     */
    public UITabbedPanelIF createTabbedPanel() {

        return new UITabbedPanel(this, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSplitPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public UISplitPanelIF createSplitPanel(Orientation orientation) {

        return new UISplitPanel(this, null, orientation);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSlideBar(net.sf.mmm.ui.toolkit.api.model.UIListModelIF,
     *      net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public <E> UISlideBarIF<E> createSlideBar(UIListModelIF<E> model, Orientation orientation) {

        return new UISlideBar<E>(this, null, orientation, model);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createProgressBar(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public UIProgressBarIF createProgressBar(Orientation orientation) {

        return new UIProgressBar(this, null, orientation);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createWorkbench(java.lang.String)
     * {@inheritDoc}
     */
    public UIWorkbenchIF createWorkbench(String title) {

        return new UIWorkbench(this, title, true);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public ActionIF createPrintAction(UIComponentIF component, String actionName, String jobName) {

        return new PrintAction((UIComponent) component, actionName, jobName);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createDateEditor()
     * {@inheritDoc}
     */
    public UIDateEditorIF createDateEditor() {

        return new UIDateEditor(this, null);
    }

}