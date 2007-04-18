/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;

/**
 * This is a test runner that tests the various UIFactorySwing implementations.
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
    private static void runTest(UIFactory factory) {

        System.out.println(factory);
        System.out.println(factory.getDisplay());
        final UIWorkbench workbench = factory.createWorkbench("Workbench");
        workbench.setMaximized(true);
        workbench.setVisible(true);
        final UIFrame frame = workbench.createFrame("TestFrame", true);        
        frame.setComposite(UIDemoBuilder.createTabbedPanel(factory));
        UIDemoBuilder.createMenus(frame);        
        frame.setSize(600, 500);
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

        runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing());
        runTest(new net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt());
    }

}