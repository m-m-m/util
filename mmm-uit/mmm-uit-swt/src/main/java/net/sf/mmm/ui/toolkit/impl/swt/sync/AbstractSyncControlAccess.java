package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This is the abstract base class used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Control}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSyncControlAccess extends AbstractSyncWidgetAccess {

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setParent(org.eclipse.swt.widgets.Composite) parent}
     */
    private static final String OPERATION_SET_PARENT = "setParent";

    /**
     * operation to get the
     * {@link org.eclipse.swt.widgets.Control#getBounds() bounds}
     */
    private static final String OPERATION_GET_BOUNDS = "getBounds";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setSize(int, int) size}
     */
    private static final String OPERATION_SET_SIZE = "setSize";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setLocation(int, int) location}
     */
    private static final String OPERATION_SET_LOCATION = "setLocation";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setToolTipText(String) tooltip-text}
     */
    private static final String OPERATION_SET_TOOLTIP = "setTooltip";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#getEnabled() "enabled-flag"}
     */
    private static final String OPERATION_SET_ENABLED = "setEnabled";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setVisible(boolean) "visible-flag"}
     */
    private static final String OPERATION_SET_VISIBLE = "setVisible";

    /**
     * operation to get the
     * {@link org.eclipse.swt.widgets.Control#isVisible() "visible-flag"}
     */
    private static final String OPERATION_IS_VISIBLE = "isVisible";

    /**
     * operation to {@link org.eclipse.swt.widgets.Control#pack() pack} the
     * control.
     */
    private static final String OPERATION_PACK = "pack";

    /**
     * operation to
     * {@link org.eclipse.swt.widgets.Control#computeSize(int, int) "compute size"}
     * of the control.
     */
    private static final String OPERATION_COMPUTE_SIZE = "computeSize";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setFont(org.eclipse.swt.graphics.Font) font}
     * of the control.
     */
    private static final String OPERATION_SET_FONT = "setFont";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setLayoutData(Object) "layout data"}
     * of the control.
     */
    private static final String OPERATION_SET_LAYOUT_DATA = "setLayoutData";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color) foreground-color}
     * of the control.
     */
    private static final String OPERATION_SET_FOREGROUND = "setForeground";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color) background-color}
     * of the control.
     */
    private static final String OPERATION_SET_BACKGROUND = "setBackground";

    /** the bounds to get */
    private Rectangle bounds;

    /** the size to set */
    private Point size;

    /** the location to set */
    private Point location;

    /** the enabled-flag */
    private boolean enabled;

    /** the visible-flag */
    private boolean visible;

    /** the tooltip text */
    private String tooltip;

    /** the width hint for compute-size */
    private int wHint;

    /** the height hint for compute-size */
    private int hHint;

    /** the computed size */
    private Point computedSize;

    /** the layout data */
    private Object layoutData;

    /** the synchron access to the parent */
    private AbstractSyncCompositeAccess parentAccess;

    /** the font of this control */
    private Font font;

    /** the foreground color (typically for text) */
    private Color foreground;

    /** the background color */
    private Color background;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the control.
     */
    public AbstractSyncControlAccess(UIFactory uiFactory, int swtStyle) {

        super(uiFactory, swtStyle);
        this.parentAccess = null;
        this.bounds = new Rectangle(0, 0, 0, 0);
        this.size = null;
        this.location = null;
        this.tooltip = null;
        this.computedSize = null;
        this.enabled = true;
        this.visible = true;
        this.wHint = 0;
        this.hHint = 0;
        this.layoutData = null;
        this.font = null;
        this.foreground = null;
        this.background = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#getSwtObject()
     * 
     */
    @Override
    public abstract Control getSwtObject();

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#performSynchron(String)
     * 
     */
    @Override
    protected void performSynchron(String operation) {

        if (operation == OPERATION_GET_BOUNDS) {
            this.bounds = getSwtObject().getBounds();
        } else if (operation == OPERATION_SET_LOCATION) {
            getSwtObject().setLocation(this.location);
        } else if (operation == OPERATION_SET_SIZE) {
            getSwtObject().setSize(this.size);
        } else if (operation == OPERATION_SET_ENABLED) {
            getSwtObject().setEnabled(this.enabled);
        } else if (operation == OPERATION_SET_TOOLTIP) {
            getSwtObject().setToolTipText(this.tooltip);
        } else if (operation == OPERATION_SET_VISIBLE) {
            getSwtObject().setVisible(this.visible);
            if (this.visible && (getSwtObject().getClass() == Shell.class)) {
                ((Shell) getSwtObject()).forceActive();
            }
        } else if (operation == OPERATION_IS_VISIBLE) {
            this.visible = getSwtObject().isVisible();
        } else if (operation == OPERATION_PACK) {
            getSwtObject().pack();
        } else if (operation == OPERATION_COMPUTE_SIZE) {
            this.computedSize = getSwtObject().computeSize(this.wHint, this.hHint);
        } else if (operation == OPERATION_SET_LAYOUT_DATA) {
            getSwtObject().setLayoutData(this.layoutData);
        } else if (operation == OPERATION_SET_PARENT) {
            getSwtObject().setParent(getParent());
        } else if (operation == OPERATION_SET_FONT) {
            getSwtObject().setFont(this.font);
        } else if (operation == OPERATION_SET_FOREGROUND) {
            getSwtObject().setForeground(this.foreground);
        } else if (operation == OPERATION_SET_BACKGROUND) {
            getSwtObject().setBackground(this.background);
        } else {
            super.performSynchron(operation);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#doCreateSynchron()
     * 
     */
    @Override
    protected void doCreateSynchron() {

        if (this.parentAccess != null) {
            if (this.parentAccess.getSwtObject() == null) {
                this.parentAccess.doCreateSynchron();
            }
            if (this.parentAccess.getSwtObject() != null) {
                super.doCreateSynchron();
            } else {
                new Exception("Warning: parent (" + this.parentAccess.getClass() + ") is empty!")
                        .printStackTrace();
            }
        } else {
            new Exception("Warning: parent is null!").printStackTrace();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#createSynchron()
     * 
     */
    @Override
    protected void createSynchron() {

        Control c = getSwtObject();
        if (this.layoutData != null) {
            c.setLayoutData(this.layoutData);
        }
        if (this.font == null) {
            this.font = c.getFont();
        } else {
            c.setFont(this.font);
        }
        if (this.foreground == null) {
            this.foreground = c.getForeground();
        } else {
            c.setForeground(this.foreground);
        }
        if (this.background == null) {
            this.background = c.getBackground();
        } else {
            c.setBackground(this.background);
        }
        if (this.tooltip != null) {
            c.setToolTipText(this.tooltip);
        }
        if (this.size != null) {
            c.setSize(this.size);
        }
        if (this.location != null) {
            c.setLocation(this.location);
        }
        if (!this.enabled) {
            c.setEnabled(this.enabled);
        }
        // TODO: pack & visible
        super.createSynchron();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#handleDisposed()
     * 
     */
    @Override
    protected void handleDisposed() {

        super.handleDisposed();
        this.visible = false;
        this.enabled = false;
    }

    /**
     * This method gets the {@link Control#getParent() parent} of this control.
     * 
     * @return the parent or <code>null</code> if NOT
     *         {@link #setParentAccess(AbstractSyncCompositeAccess) set} and
     *         {@link AbstractSyncWidgetAccess#create() created}.
     */
    public Composite getParent() {

        if (this.parentAccess == null) {
            return null;
        }
        return this.parentAccess.getSwtObject();
    }

    /**
     * This method gets the synchron access to the
     * {@link Control#getParent() parent} of this control.
     * 
     * @return the synchron access to the parent.
     */
    public AbstractSyncCompositeAccess getParentAccess() {

        return this.parentAccess;
    }

    /**
     * This method sets the parent sync-access of this control. If the parent
     * {@link AbstractSyncCompositeAccess#getSwtObject() exists}, it will be
     * set as parent of this control. Else if the control does NOT yet exist,
     * the parent will be set on {@link #create() creation}.
     * 
     * @param newParentAccess
     *        is the synchron access to the new parent
     */
    public void setParentAccess(AbstractSyncCompositeAccess newParentAccess) {

        this.parentAccess = newParentAccess;
        if (this.parentAccess.getSwtObject() != null) {
            if (getSwtObject() != null) {
                assert (checkReady());
                invoke(OPERATION_SET_PARENT);
            }
        }
    }

    /**
     * This method determines if the control can be created.
     * 
     * @return <code>true</code> if there is an ancestor that is already
     *         created, <code>false</code> otherwise.
     */
    protected boolean canCreate() {

        if (this.parentAccess == null) {
            return false;
        } else if (this.parentAccess.getSwtObject() != null) {
            return true;
        } else {
            return this.parentAccess.canCreate();
        }
    }

    /**
     * The {@link #setParentAccess(AbstractSyncCompositeAccess) parent} must be
     * set before this method is called.
     * 
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#create()
     */
    @Override
    public void create() {

        // if (canCreate()) {
        super.create();
        // }
    }

    /**
     * This method gets the {@link Control#getBounds() bounds} of the control.
     * 
     * @return the bounds of the control.
     */
    public Rectangle getBounds() {

        assert (checkReady());
        invoke(OPERATION_GET_BOUNDS);
        return this.bounds;
    }

    /**
     * This method sets the {@link Control#setSize(int, int) size} of the
     * control.
     * 
     * @param width
     *        is the width to set.
     * @param height
     *        is the height to set.
     */
    public void setSize(int width, int height) {

        assert (checkReady());
        if (this.size == null) {
            this.size = new Point(width, height);
        } else {
            this.size.x = width;
            this.size.y = height;
        }
        invoke(OPERATION_SET_SIZE);
    }

    /**
     * This method sets the {@link Control#setSize(int, int) size} of the
     * control.
     * 
     * @param newSize
     *        is the new size.
     */
    public void setSize(Point newSize) {

        if (newSize != null) {
            setSize(newSize.x, newSize.y);
        }
    }

    /**
     * This method sets the {@link Control#setLocation(int, int) location} of
     * the control.
     * 
     * @param x
     *        is the x-coordinate to set.
     * @param y
     *        is the y-coordinate to set.
     */
    public void setLocation(int x, int y) {

        assert (checkReady());
        if (this.location == null) {
            this.location = new Point(x, y);
        } else {
            this.location.x = x;
            this.location.y = y;
        }
        invoke(OPERATION_SET_LOCATION);
    }

    /**
     * This method sets the {@link Control#getEnabled() "enabled-flag"} of the
     * control.
     * 
     * @param enabledFlag
     *        is the new status of the enabled flag.
     */
    public void setEnabled(boolean enabledFlag) {

        if (this.enabled != enabledFlag) {
            assert (checkReady());
            this.enabled = enabledFlag;
            invoke(OPERATION_SET_ENABLED);
        }
    }

    /**
     * This method gets the {@link Control#getEnabled() "enabled-flag"} of the
     * control.
     * 
     * @return the enabled flag.
     */
    public boolean isEnabled() {

        // enabled-flag can NOT be modified externally (e.g. by
        // user-interaction).
        return this.enabled;
    }

    /**
     * This method sets the {@link Control#setToolTipText(String) tooltip-text}
     * of the control.
     * 
     * @param tooltipText
     *        is the new tooltip text to set.
     */
    public void setTooltip(String tooltipText) {

        assert (checkReady());
        this.tooltip = tooltipText;
        invoke(OPERATION_SET_TOOLTIP);
    }

    /**
     * This method gets the {@link Control#getToolTipText() tooltip-text} of the
     * control.
     * 
     * @return the tooltip text.
     */
    public String getTooltip() {

        // tooltip can NOT be modified externally (e.g. by user-interaction).
        return this.tooltip;
    }

    /**
     * This method sets the {@link Control#setVisible(boolean) visible-flag} of
     * the control.
     * 
     * @param isVisible
     *        is the visible flag to set.
     */
    public void setVisible(boolean isVisible) {

        assert (checkReady());
        this.visible = isVisible;
        invoke(OPERATION_SET_VISIBLE);
    }

    /**
     * This method gets the visible flag as set by {@link #setVisible(boolean)}.
     * This is NOT the same as the {@link Control#isVisible() visible-flag} of
     * the control.
     * 
     * @return the visible-flag.
     */
    public boolean isVisible() {

        assert (checkReady());
        invoke(OPERATION_IS_VISIBLE);
        return this.visible;
    }

    /**
     * This method {@link org.eclipse.swt.widgets.Control#pack() packs} the
     * control.
     */
    public void pack() {

        assert (checkReady());
        invoke(OPERATION_PACK);
    }

    /**
     * This method {@link Control#computeSize(int, int) computes} the size of
     * the control.
     * 
     * @return the computed size.
     */
    public Point computeSize() {

        return computeSize(SWT.DEFAULT, SWT.DEFAULT);
    }

    /**
     * This method {@link Control#computeSize(int, int) computes} the size of
     * the control.
     * 
     * @param widthHint
     *        the suggested width or {@link org.eclipse.swt.SWT#DEFAULT}.
     * @param heightHint
     *        the suggested height or {@link org.eclipse.swt.SWT#DEFAULT}.
     * @return the computed size.
     */
    public Point computeSize(int widthHint, int heightHint) {

        assert (checkReady());
        this.wHint = widthHint;
        this.hHint = heightHint;
        invoke(OPERATION_COMPUTE_SIZE);
        return this.computedSize;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Control#setLayoutData(Object) "layout data"}
     * of the control.
     * 
     * @param data
     *        is the layout-data to set.
     */
    public void setLayoutData(Object data) {

        assert (checkReady());
        this.layoutData = data;
        invoke(OPERATION_SET_LAYOUT_DATA);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Control#getFont() font} of the control.
     * 
     * @return the font of the control. May be <code>null</code> if the
     *         control has NOT been created.
     */
    public Font getFont() {

        return this.font;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Control#setFont(org.eclipse.swt.graphics.Font) font}
     * of the control.
     * 
     * @param newFont
     *        is the new font to set.
     */
    public void setFont(Font newFont) {

        assert (checkReady());
        this.font = newFont;
        invoke(OPERATION_SET_FONT);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Control#getForeground() foreground-color}
     * of the control.
     * 
     * @return the foreground of the control. May be <code>null</code> if the
     *         control has NOT been created.
     */
    public Color getForeground() {

        return this.foreground;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color) foreground-color}
     * of the control.
     * 
     * @param foregroundColor
     *        is the foreground-color to set.
     */
    public void setForeground(Color foregroundColor) {

        assert (checkReady());
        this.foreground = foregroundColor;
        invoke(OPERATION_SET_FOREGROUND);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Control#getBackground() background-color}
     * of the control.
     * 
     * @return the background of the control. May be <code>null</code> if the
     *         control has NOT been created.
     */
    public Color getBackground() {

        return this.background;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color) background-color}
     * of the control.
     * 
     * @param backgroundColor
     *        is the background-color to set.
     */
    public void setBackground(Color backgroundColor) {

        assert (checkReady());
        this.background = backgroundColor;
        invoke(OPERATION_SET_BACKGROUND);
    }

}