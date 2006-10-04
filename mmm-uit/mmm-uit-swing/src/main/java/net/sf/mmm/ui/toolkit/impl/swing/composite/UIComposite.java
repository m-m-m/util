/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponentIF} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIComposite extends UIComponent implements UICompositeIF {

    /** the titled border of this composite */
    private TitledBorder border;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIComposite(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.border = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIComponentIF#setEnabled(boolean)
     * 
     */
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);
        for (int i = 0; i < getComponentCount(); i++) {
            getComponent(i).setEnabled(enabled);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitleIF#setBorderTitle(java.lang.String)
     * 
     */
    public void setBorderTitle(String borderTitle) {

        if (borderTitle == null) {
            if (this.border != null) {
                getSwingComponent().setBorder(BorderFactory.createEmptyBorder());
                this.border = null;
            }
        } else {
            if (this.border == null) {
                this.border = BorderFactory.createTitledBorder(borderTitle);
                getSwingComponent().setBorder(this.border);
            } else {
                this.border.setTitle(borderTitle);
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitleIF#getBorderTitle()
     * 
     */
    public String getBorderTitle() {

        if (this.border == null) {
            return null;
        } else {
            return this.border.getTitle();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#refresh()
     * 
     */
    @Override
    public void refresh() {

        super.refresh();
        int count = getComponentCount();
        for (int componentIndex = 0; componentIndex < count; componentIndex++) {
            getComponent(componentIndex).refresh();
        }
    }
}