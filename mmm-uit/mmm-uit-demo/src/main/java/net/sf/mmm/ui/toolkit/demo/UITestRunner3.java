/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.types.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.window.UiWorkbench;

/**
 * This is a test runner that tests the various UIFactorySwing implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITestRunner3 {

  /**
   * This method runs the demo test.
   * 
   * @param factory is the actual factory implementation to use.
   */
  private static void runTest(UiFactory factory) {

    System.out.println(factory);
    System.out.println(factory.getDisplay());
    final UiWorkbench workbench = factory.createWorkbench("Workbench");
    workbench.setSize(800, 1024);
    workbench.setVisible(true);
    final UiFrame frame = workbench.createFrame("TestFrame", true);
    UiSlicePanel panel = factory.createPanel(Orientation.VERTICAL);
    // UiButton button = factory.createButton("Button");
    // UiDecoratedComponent<UiLabel, UiButton> labeledButton =
    // factory.createLabeledComponent(
    // "Label:", button);
    // panel.addChild(labeledButton, LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    // UiTextField text = factory.createTextField();
    // UiDecoratedComponent<UiLabel, UiTextField> labeledText =
    // factory.createLabeledComponent(
    // "Label2:", text);
    // panel.addChild(labeledText, LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    frame.setComposite(panel);
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
   * @param args are the commandline arguments.
   */
  public static void main(String[] args) {

    runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing());
    // runTest(new net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt());
  }

}
