/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIScrollPanel extends UIComposite implements UIScrollPanelIF {

    /** the scroll-panel or <code>null</code> if this is a regular panel */
    private final JScrollPane scrollPanel;

    /** the child component */
    private UIComponent childComponent;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIScrollPanel(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.scrollPanel = new JScrollPane();
        this.childComponent = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    @Override
    public JComponent getSwingComponent() {

        return this.scrollPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF#setComponent(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * 
     */
    public void setComponent(UICompositeIF child) {

        if (this.childComponent != null) {
            setParent(this.childComponent, null);
        }
        this.childComponent = (UIComponent) child;
        if (this.childComponent != null) {
            this.scrollPanel.setViewportView(this.childComponent.getSwingComponent());
            System.out.println(this.childComponent.getSwingComponent().getPreferredSize());
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponentCount()
     * 
     */
    public int getComponentCount() {

        return 1;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * 
     */
    public UIComponentIF getComponent(int index) {

        if (index == 0) {
            return this.childComponent;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#refresh()
     * 
     */
    @Override
    public void refresh() {

        this.scrollPanel.invalidate();
        this.scrollPanel.updateUI();
        this.scrollPanel.repaint();
    }

}
