/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;

/**
 * This is a test runner that tests the various UIFactorySwing implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITestRunner2 {

  /**
   * This method runs the demo test.
   * 
   * @param factory is the actual factory implementation to use.
   */
  private static void runTest(UiFactory factory) {

    System.out.println(factory);
    System.out.println(factory.getDisplay());
    // factory.setScriptOrientation(ScriptOrientation.RIGHT_TO_LEFT);
    /*
     * final UIWorkbench workbench = factory.createWorkbench("Workbench");
     * workbench.setMaximized(true); workbench.setVisible(true); final UIFrame
     * frame = workbench.createFrame("TestFrame", true);
     */
    final UiFrame frame = factory.createFrame("MyApplication", true);
    frame.setComposite(UIDemoBuilder.createTabbedPanel(factory));
    UIDemoBuilder.createMenus(frame);
    frame.setSize(600, 500);
    frame.centerWindow();
    frame.setVisible(true);
    // while (workbench.isVisible()) {
    while (frame.isVisible()) {
      factory.getDisplay().dispatch();
    }
    frame.dispose();
    factory.dispose();
  }

  /**
   * This method holds the main code to run this class. It will be called when
   * the class is started as java application.
   * 
   * @param args are the commandline arguments.
   */
  public static void main(String[] args) {

    String title = UITestRunner2.class.getSimpleName();
    runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing(title));
    runTest(new net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt(title));
  }

}
