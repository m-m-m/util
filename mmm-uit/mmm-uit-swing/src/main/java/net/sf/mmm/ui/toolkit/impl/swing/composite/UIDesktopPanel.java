/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIInternalFrame;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbench;

/**
 * This is the implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UICompositeIF} interface for the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWindowIF#getComposite() content} of
 * a {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDesktopPanel extends UIMultiComposite {

    /** the workbench pane */
    private final JDesktopPane workbench;

    /** the default layer used to add internal frames */
    private static final Integer DEFAULT_LAYER = new Integer(1);

    /**
     * The constructor.
     * 
     * @param uiFactory
     * @param parentObject
     */
    public UIDesktopPanel(UIFactory uiFactory, UIWorkbench parentObject) {

        super(uiFactory, parentObject);
        this.workbench = new JDesktopPane();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * 
     */
    @Override
    public JComponent getSwingComponent() {

        return this.workbench;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return "DesktopPanel";
    }

    /**
     * This method adds the given internal frame to this composite.
     * 
     * @param internalFrame is the frame to add.
     */
    public void add(UIInternalFrame internalFrame) {

        this.components.add(internalFrame);
        this.workbench.add(internalFrame.getSwingInternalFrame(), DEFAULT_LAYER);
    }

}
