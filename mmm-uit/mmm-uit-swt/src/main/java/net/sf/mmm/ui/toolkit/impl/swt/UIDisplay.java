/* $Id: UIDisplay.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import net.sf.mmm.ui.toolkit.base.UIAbstractDisplay;

/**
 * This class is the implementation of the UIDisplayIF interface using AWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDisplay extends UIAbstractDisplay {

    /**
     * This inner class is used to get the display size.
     */
    private static class SizeGetter implements Runnable {

        /** the size */
        Rectangle size;

        /** the display */
        Display display;

        /**
         * The constructor.
         * 
         * @param d
         *        is the display
         */
        private SizeGetter(Display d) {

            this.display = d;
        }

        /**
         * @see java.lang.Runnable#run()
         * {@inheritDoc}
     */
        public void run() {

            this.size = this.display.getBounds();
        }

    }

    /** the SWT display */
    private final Display display;

    /** the task used to get the display size */
    private final SizeGetter sizeGetter;

    /** the ui factory */
    private final UIFactory factory;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param uiDevice
     *        is the device the display belongs to.
     * @param swtDisplay
     *        is the SWT display to wrap.
     */
    public UIDisplay(UIFactory uiFactory, UIDevice uiDevice, Display swtDisplay) {

        super(uiFactory, uiDevice);
        this.factory = uiFactory;
        this.display = swtDisplay;
        this.sizeGetter = new SizeGetter(this.display);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory()
     * {@inheritDoc}
     */
    @Override
    public UIFactory getFactory() {

        return this.factory;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        invokeSynchron(this.sizeGetter);
        return this.sizeGetter.size.width;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        invokeSynchron(this.sizeGetter);
        return this.sizeGetter.size.height;
    }

    /**
     * This method gets the unwrapped display object.
     * 
     * @return the SWT display object.
     */
    public Display getSwtDisplay() {

        return this.display;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#dispatch()
     * {@inheritDoc}
     */
    public void dispatch() {

        if (isDispatchThread()) {
            if (!this.display.readAndDispatch()) {
                this.display.sleep();
            }
        } else {
            // sleep some secs...
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#isDispatchThread()
     * {@inheritDoc}
     */
    public boolean isDispatchThread() {
    
        return (this.display.getThread() == Thread.currentThread());
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIDisplayIF#invokeAsynchron(java.lang.Runnable)
     * {@inheritDoc}
     */
    public void invokeAsynchron(Runnable task) {

        this.display.asyncExec(task);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractDisplay#doInvokeSynchron(java.lang.Runnable)
     * {@inheritDoc}
     */
    public void doInvokeSynchron(Runnable task) {

        this.display.syncExec(task);
    }

}
