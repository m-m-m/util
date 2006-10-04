package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * This inner class is the thread that does the actual work for SWT.
 */
public class SwtWorkerThread extends Thread {

    /** the disposed flag */
    private boolean disposed;

    /** the display to use */
    private Display display;

    /** the monitor to use */
    private Monitor monitor;

    /** the dummy parent for new created widgets */
    private Shell dummyParent;

    /**
     * The constructor.
     */
    public SwtWorkerThread() {

        super();
        this.disposed = false;
    }

    /**
     * @see java.lang.Runnable#run()
     * 
     */
    public void run() {

        this.display = Display.getDefault();
        this.monitor = this.display.getPrimaryMonitor();
        this.dummyParent = new Shell(this.display);
        while (!this.disposed) {
            try {
                if (this.display.readAndDispatch()) {
                    if (!this.disposed) {
                        this.display.sleep();                        
                    }
                }
            } catch (SWTException e) {
                e.printStackTrace();
            }
        }
        this.display.dispose();
        System.out.println("SWT shutdown!");
        System.out.flush();
    }

    /**
     * This method gets the display of this worker.
     * 
     * @return the display
     */
    public Display getDisplay() {

        while (this.display == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return this.display;
    }

    /**
     * This method gets the monitor of this worker.
     * 
     * @return the monitor
     */
    public Monitor getMonitor() {

        while (this.monitor == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return this.monitor;
    }

    /**
     * This method gets the dummy parent.
     * 
     * @return the dummy parent
     * @deprecated This is a hack - it should be removed!
     */
    public Shell getDummyParent() {
        
        while (this.dummyParent == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }            
        }
        return this.dummyParent;
    }
    
    /**
     * This method stops this thread.
     */
    public void dispose() {
        
        this.disposed = true;
        interrupt();
    }
    
}