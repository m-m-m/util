/* $Id: UIComponent.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.Dimension;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.impl.awt.UIAwtNode;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponentIF} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIComponent extends UIAwtNode implements UIComponentIF {

    /** the disposed flag */
    private boolean disposed;

    /** the (mimimum) size */
    private final Dimension size;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIComponent(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.disposed = false;
        this.size = new Dimension();
    }

    /**
     * This method gets the unwrapped swing component that is the top ancestor
     * of this component.
     * 
     * @return the unwrapped swing component.
     */
    public abstract JComponent getSwingComponent();

    /**
     * This method gets the unwrapped component that represents the active part
     * of this component. This method is used by methods such as setEnabled()
     * and setTooltipText(). It can be overriden if the implemented component is
     * build out of multiple swing components and the top ancestor is not the
     * active component (e.g. a {@link javax.swing.JTree} is the active
     * component and a {@link javax.swing.JScrollPane} is the
     * {@link #getSwingComponent() top-ancestor}).
     * 
     * @return the active unwrapped swing component.
     */
    protected JComponent getActiveSwingComponent() {

        return getSwingComponent();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF#getTooltipText()
     * {@inheritDoc}
     */
    public String getTooltipText() {

        return getActiveSwingComponent().getToolTipText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIComponentIF#setTooltipText(java.lang.String)
     * {@inheritDoc}
     */
    public void setTooltipText(String tooltip) {

        getActiveSwingComponent().setToolTipText(tooltip);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIComponentIF#setEnabled(boolean)
     * {@inheritDoc}
     */
    public void setEnabled(boolean enabled) {

        getActiveSwingComponent().setEnabled(enabled);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabledIF#isEnabled()
     * {@inheritDoc}
     */
    public boolean isEnabled() {

        return getActiveSwingComponent().isEnabled();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#setId(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    public void setId(String newId) {

        super.setId(newId);
        getSwingComponent().setName(newId);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        return getSwingComponent().getHeight();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        return getSwingComponent().getWidth();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * {@inheritDoc}
     */
    public boolean isResizeable() {

        // TODO:
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * {@inheritDoc}
     */
    public void setSize(int width, int height) {

        if (isResizeable()) {
            // getSwingComponent().setSize(width, height);
            this.size.height = height;
            this.size.width = width;
            getSwingComponent().setMinimumSize(this.size);
        }
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

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredHeight()
     * {@inheritDoc}
     */
    public int getPreferredHeight() {

        return (int) getActiveSwingComponent().getPreferredSize().getHeight();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredWidth()
     * {@inheritDoc}
     */
    public int getPreferredWidth() {

        return (int) getActiveSwingComponent().getPreferredSize().getWidth();
    }

}