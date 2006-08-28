/* $Id$ */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF;

/**
 * This is a test runner that tests the various UIFactory implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITestRunner2 {

    /**
     * This method runs the demo test.
     * 
     * @param factory
     *        is the actual factory implementation to use.
     */
    private static void runTest(UIFactoryIF factory) {

        System.out.println(factory);
        System.out.println(factory.getDisplay());
        final UIWorkbenchIF workbench = factory.createWorkbench("Workbench");
        workbench.setSize(800, 1024);
        workbench.setVisible(true);
        final UIFrameIF frame = workbench.createFrame("TestFrame", true);        
        frame.setComposite(UIDemoBuilder.createTabbedPanel(factory));
        UIDemoBuilder.createMenus(frame);        
        frame.setSize(500, 300);
        frame.centerWindow();
        frame.setVisible(true);
        while (workbench.isVisible()) {
            factory.getDisplay().dispatch();
        }
        frame.dispose();
        factory.dispose();
    }

    /**
     * This method holds the main code to run this class. It will be called when
     * the class is started as java application.
     * 
     * @param args
     *        are the commandline arguments.
     */
    public static void main(String[] args) {

        runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactory());
        runTest(new net.sf.mmm.ui.toolkit.impl.swt.UIFactory());
    }

}