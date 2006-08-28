/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UITableIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractFactory implements UIFactoryIF {

    /** the disposed flag */
    private boolean disposed;
    
    /**
     * The constructor.
     */
    public UIAbstractFactory() {

        super();
        this.disposed = false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createFrame(java.lang.String)
     * {@inheritDoc}
     */
    public UIFrameIF createFrame(String title) {

        return createFrame(title, true);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createLabel()
     * {@inheritDoc}
     */
    public UILabelIF createLabel() {

        return createLabel("");
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(java.lang.String)
     * {@inheritDoc}
     */
    public UIButtonIF createButton(String text) {

        return createButton(text, ButtonStyle.DEFAULT);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * {@inheritDoc}
     */
    public UIButtonIF createButton(String text, ButtonStyle style) {

        return createButton(text, null, style);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(net.sf.mmm.ui.toolkit.api.UIPictureIF,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * {@inheritDoc}
     */
    public UIButtonIF createButton(UIPictureIF icon, ButtonStyle style) {

        return createButton(null, icon, style);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createButton(net.sf.mmm.ui.toolkit.api.feature.ActionIF)
     * {@inheritDoc}
     */
    public UIButtonIF createButton(ActionIF action) {

        UIButtonIF button = createButton(action.getName(), action.getStyle());
        button.addActionListener(action.getActionListener());
        UIPictureIF icon = action.getIcon();
        if (icon != null) {
            button.setIcon(icon);
        }
        String id = action.getId();
        if (id != null) {
            button.setId(id);
        }
        return button;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public UIPanelIF createPanel(Orientation orientation) {

        return createPanel(orientation, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPicture(java.io.File)
     * {@inheritDoc}
     */
    public UIPictureIF createPicture(File imageFile) throws IOException {

        try {
            return createPicture(imageFile.toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createSlideBar(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public <E> UISlideBarIF<E> createSlideBar(UIListModelIF<E> model) {

        return createSlideBar(model, Orientation.HORIZONTAL);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createList(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public <E> UIListIF<E> createList(UIListModelIF<E> model) {

        return createList(model, false);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createComboBox(net.sf.mmm.ui.toolkit.api.model.UIListModelIF)
     * {@inheritDoc}
     */
    public <E> UIComboBoxIF<E> createComboBox(UIListModelIF<E> model) {

        return createComboBox(model, false);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createProgressBar()
     * {@inheritDoc}
     */
    public UIProgressBarIF createProgressBar() {

        return createProgressBar(Orientation.HORIZONTAL);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createScrollPanel()
     * {@inheritDoc}
     */
    public UIScrollPanelIF createScrollPanel() {

        return createScrollPanel(null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTable()
     * {@inheritDoc}
     */
    public UITableIF createTable() {

        return createTable(false);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTable(boolean)
     * {@inheritDoc}
     */
    public UITableIF createTable(boolean multiSelection) {

        return createTable(multiSelection, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTree()
     * {@inheritDoc}
     */
    public UITreeIF createTree() {

        return createTree(false);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createTree(boolean)
     * {@inheritDoc}
     */
    public UITreeIF createTree(boolean multiSelection) {

        return createTree(multiSelection, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public ActionIF createPrintAction(UIComponentIF component) {

        // TODO: i18n
        return createPrintAction(component, "Print");
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIFactoryIF#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public ActionIF createPrintAction(UIComponentIF component, String actionName) {

        if (component == null) {
            throw new IllegalArgumentException("Component must NOT be null!");
        }
        return createPrintAction(component, actionName, actionName + " " + component.getId());
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#dispose()
     * {@inheritDoc}
     */
    public void dispose() {

        this.disposed = true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#isDisposed()
     * {@inheritDoc}
     */
    public boolean isDisposed() {

        return this.disposed;
    }

}
