/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIDesktopPanel;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF} interface using Swing as
 * the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIWorkbench extends UIFrame implements UIWorkbenchIF {

    /** the workbench pane */
    private final UIDesktopPanel workbench;
    
    /**
     * The constructor.
     *
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param title
     *        is the {@link #getTitle() title} of the frame.
     * @param resizeable -
     *        if <code>true</code> the frame will be {@link #isResizeable() resizeable}.
     */
    public UIWorkbench(UIFactory uiFactory, String title, boolean resizeable) {

        super(uiFactory, null, title, resizeable);
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
        //JFrame.setDefaultLookAndFeelDecorated(true);
        //Toolkit.getDefaultToolkit().setDynamicLayout(true);
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
        this.workbench = new UIDesktopPanel(uiFactory, this);
        super.setComposite(this.workbench);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return UIWorkbenchIF.TYPE;
    }

    /**
     * This method gets the desktop panel of this workbench.
     * 
     * @return the desktop panel.
     */
    public UIDesktopPanel getDesktopPanel() {
        
        return this.workbench;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#setComposite(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * {@inheritDoc}
     */
    public void setComposite(UICompositeIF newComposite) {

        //TODO...
        throw new IllegalArgumentException("This method is not applicable for " + UIWorkbench.class);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF#createFrame(java.lang.String, boolean)
     * {@inheritDoc}
     */
    public UIFrameIF createFrame(String title, boolean resizeable) {

        UIInternalFrame internalFrame = new UIInternalFrame((UIFactory) getFactory(), this, title, resizeable);
        this.workbench.add(internalFrame);
        return internalFrame;
    }

}
