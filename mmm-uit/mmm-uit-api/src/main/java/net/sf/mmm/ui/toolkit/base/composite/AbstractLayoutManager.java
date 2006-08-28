/* $Id$ */
package net.sf.mmm.ui.toolkit.base.composite;

import net.sf.mmm.ui.toolkit.api.composite.Alignment;
import net.sf.mmm.ui.toolkit.api.composite.Filling;
import net.sf.mmm.ui.toolkit.api.composite.Insets;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF;

/**
 * This is the abstract base implementation of a manager for the layout of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanelIF panel}.<br>
 * The implementation assumes that for each panel an own layout-manager is
 * created and is therefore not thread-safe.
 * 
 * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
 *      LayoutConstraints)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLayoutManager {

    /** The number of spare entries added if the cache size is increased. */
    private static final int CACHE_SPARE_SIZE = 3;

    /**
     * If <code>true</code> the layout is vertical, if <code>false</code>
     * the layout is horizontal.
     */
    protected Orientation layoutOrientation;

    /**
     * The layout constraints of the componets. For a given array position the
     * constraint, childSize and childArea must belong to the same component.
     */
    protected LayoutConstraints[] constraints;

    /**
     * The preferred child sizes. If the {@link #layoutOrientation layout} is
     * {@link Orientation#VERTICAL}, {@link Size#width} and {@link Size#height}
     * must be swapped.
     * 
     * @see #constraints
     */
    protected Size[] childSizes;

    /**
     * The calculated (layouted) child areas. If the
     * {@link #layoutOrientation layout} is {@link Orientation#VERTICAL},
     * {@link Rectangle#x} and {@link Rectangle#y} as well as
     * {@link Rectangle#width} and {@link Rectangle#height} must be swapped.
     * 
     * @see #constraints
     */
    protected Rectangle[] childAreas;

    /**
     * The area of the panel to layout.
     */
    protected Rectangle parentArea;

    /** The calculated size for the panel. */
    protected Size size;

    /** the number of child-components currently cached */
    protected int childCount;

    /**
     * The constructor.
     */
    public AbstractLayoutManager() {

        super();
        this.layoutOrientation = Orientation.HORIZONTAL;
        this.parentArea = new Rectangle();
        this.size = new Size();
        this.constraints = new LayoutConstraints[0];
        this.childSizes = null;
        this.childAreas = null;
        this.childCount = 0;
    }

    /**
     * This method calculates the size of a
     * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanelIF panel}.
     * 
     * @return the calculated size for the panel.
     */
    public Size calculateSize() {

        this.size.width = 0;
        this.size.height = 0;
        for (int i = 0; i < this.childCount; i++) {
            // if with is 0, the component is NOT visible
            if (this.childSizes[i].width != 0) {
                if (this.childSizes[i].height > this.size.height) {
                    this.size.height = this.childSizes[i].height;
                }
                this.size.width += this.childSizes[i].width;
                if (this.layoutOrientation == Orientation.HORIZONTAL) {
                    this.size.width += this.constraints[i].insets.getHorizontalSpace();
                } else {
                    this.size.width += this.constraints[i].insets.getVerticalSpace();
                }
            }
        }
        if (this.layoutOrientation == Orientation.VERTICAL) {
            this.size.swap();
        }
        return this.size;
    }

    /**
     * This method calculates the layout.
     */
    public void calculateLayout() {

        if (this.constraints.length == 0) {
            // nothing to do...
            return;
        }
        int axisFixed = 0;
        double axisDynamic = 0;
        // pass 1
        for (int i = 0; i < this.childCount; i++) {
            // if with is 0, the component is NOT visible
            if (this.childSizes[i].width != 0) {
                // add insets as fixed space
                if (this.layoutOrientation == Orientation.HORIZONTAL) {
                    axisFixed += this.constraints[i].insets.getHorizontalSpace();
                } else {
                    axisFixed += this.constraints[i].insets.getVerticalSpace();
                }
                Filling axisFilling = this.constraints[i].filling.getPart(this.layoutOrientation);
                if ((this.constraints[i].weight == 0) || (Filling.NONE == axisFilling)) {
                    axisFixed += this.childSizes[i].width;
                } else {
                    axisDynamic += this.childSizes[i].width * this.constraints[i].weight;
                }
            }
        }

        // evaluate strategy
        int axisAvailable = this.parentArea.width;
        double fixscale = 1;
        double scale = 1;
        double axisPrefered = axisFixed + axisDynamic;
        if (axisPrefered == 0) {
            return;
        }
        /*
         * if (axisPrefered > axisAvailable) { fixscale = axisAvailable /
         * axisPrefered; scale = fixscale; } else { if (axisDynamic == 0) {
         * fixscale = axisAvailable / axisFixed; } else { scale = (axisAvailable -
         * axisFixed) / axisDynamic; } }
         */
        if (axisDynamic == 0) {
            if (axisAvailable < axisFixed) {
                // fixscale = axisAvailable / (double) axisFixed;
            }
        } else {
            scale = (axisAvailable - axisFixed) / axisDynamic;
        }

        // pass 2
        int x = this.parentArea.x;
        int y = this.parentArea.y;
        int childWidth;
        int width; // = parentArea.width;
        int height = this.parentArea.height;
        for (int i = 0; i < this.childCount; i++) {
            // if with is 0, the component is NOT visible
            if (this.childSizes[i].width != 0) {
                Filling filling = this.constraints[i].filling;
                Insets insets = this.constraints[i].insets;
                Alignment alignment = this.constraints[i].alignment;
                if (this.layoutOrientation == Orientation.VERTICAL) {
                    filling = filling.getMirrored();
                    insets = insets.getSwapped();
                    alignment = alignment.getMirrored();
                }
                boolean axisFill = (this.constraints[i].weight != 0);
                double w;
                if (axisFill && (filling == Filling.HORIZONTAL) || (filling == Filling.BOTH)) {
                    w = this.constraints[i].weight * scale;
                } else {
                    w = fixscale;
                }
                childWidth = this.childSizes[i].width + insets.getHorizontalSpace();
                width = (int) (childWidth * w);
                this.childAreas[i].x = x;
                this.childAreas[i].y = y + insets.top;

                // TODO: reduce insets if (width < clientAreas[i].width) ||
                // (height < clientAreas[i].height) ???

                // horizontal filling?
                if (width < childWidth) {
                    if (width > this.childAreas[i].width) {
                        int space = width - this.childSizes[i].width;
                        this.childAreas[i].x += (int) (insets.left * ((double) space / insets
                                .getHorizontalSpace()));
                    }
                    this.childAreas[i].width = width;
                } else if ((filling == Filling.HORIZONTAL) || (filling == Filling.BOTH)) {
                    this.childAreas[i].x += insets.left;
                    this.childAreas[i].width = width - insets.getHorizontalSpace();
                } else {
                    this.childAreas[i].width = this.childSizes[i].width;
                    Alignment hAlignment = alignment.getHorizontalPart();
                    int xSpace = width - this.childSizes[i].width;
                    if (hAlignment == Alignment.CENTER) {
                        this.childAreas[i].x += xSpace / 2;
                    } else if (hAlignment == Alignment.RIGHT) {
                        this.childAreas[i].x += xSpace;
                    }
                }

                // vertical filling?
                if ((height < this.childAreas[i].height) || (filling == Filling.VERTICAL)
                        || (filling == Filling.BOTH)) {
                    this.childAreas[i].height = height - insets.getVerticalSpace();
                } else {
                    this.childAreas[i].height = this.childSizes[i].height;
                    Alignment vAlignment = alignment.getVerticalPart();
                    int ySpace = height - this.childSizes[i].height;
                    if (vAlignment == Alignment.CENTER) {
                        this.childAreas[i].y += ySpace / 2;
                    } else if (vAlignment == Alignment.BOTTOM) {
                        this.childAreas[i].y += ySpace;
                    }
                }
                x += width;
            }
        }
    }

    /**
     * This method gets the orientation for this layout.
     * 
     * @return the orientation.
     */
    public Orientation getOrientation() {

        return this.layoutOrientation;
    }

    /**
     * This method sets the orientation for this layout.
     * 
     * @param orientation
     *        is the layoutOrientation to set.
     */
    public void setOrientation(Orientation orientation) {

        this.layoutOrientation = orientation;
    }

    /**
     * This method ensures that the cache has at least the given size.
     * 
     * @param componentCount
     *        is the required cache size.
     */
    protected void ensureCacheSize(int componentCount) {

        // refresh constraints
        if ((this.constraints == null) || (this.constraints.length < componentCount)) {
            this.constraints = new LayoutConstraints[componentCount + CACHE_SPARE_SIZE];
        }

        // refresh childSizes
        if ((this.childSizes == null) || (this.childSizes.length < componentCount)) {
            Size[] oldSizes = this.childSizes;
            this.childSizes = new Size[componentCount + CACHE_SPARE_SIZE];
            if (oldSizes != null) {
                System.arraycopy(oldSizes, 0, this.childSizes, 0, this.childCount);
            }
        }
        for (int i = this.childCount; i < componentCount; i++) {
            this.childSizes[i] = new Size();
        }
        this.childCount = componentCount;
    }

    /**
     * This method handles the size overriding. The width and/or height of the
     * given component size is overridden according to the given
     * {@link LayoutConstraints#size}.
     * 
     * @param constraintsSize
     *        is the {@link LayoutConstraints#size} that may override
     *        width/height.
     * @param componentSize
     *        is the size of the component. It will be manipulated as
     *        neccessary.
     */
    public static void overrideSize(UIReadSizeIF constraintsSize, Size componentSize) {

        int w = constraintsSize.getWidth();
        if (w > 0) {
            componentSize.width = w;
        }
        int h = constraintsSize.getHeight();
        if (h > 0) {
            componentSize.height = h;
        }
    }

}
