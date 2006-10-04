/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITabbedPanel extends UIMultiComposite implements UITabbedPanelIF {

    /** the native swing component */
    private final JTabbedPane panel;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UITabbedPanel(UIFactory uiFactory, UIComponent parentObject) {

        super(uiFactory, parentObject);
        this.panel = new JTabbedPane();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    public @Override JComponent getSwingComponent() {

        return this.panel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      java.lang.String)
     * 
     */
    public void addComponent(UIComponentIF component, String title) {

        UIComponent c = (UIComponent) component; 
        this.panel.add(title, c.getSwingComponent());
        setParent(c, this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      java.lang.String, int)
     * 
     */
    public void addComponent(UIComponentIF component, String title, int position) {

        UIComponent c = (UIComponent) component;
        this.panel.insertTab(title, null, c.getSwingComponent(), null, position);
        setParent(c, this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * 
     */
    public void removeComponent(UIComponentIF component) {

        Component c = ((UIComponent) component).getSwingComponent();
        int position = this.panel.indexOfComponent(c);
        if (position >= 0) {
            removeComponent(position);            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF#removeComponent(int)
     * 
     */
    public void removeComponent(int position) {

        this.panel.remove(position);
        this.components.remove(position);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

}
