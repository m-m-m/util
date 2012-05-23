/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.util.lang.api.Orientation;

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
    final UiWorkbench workbench = factory.getOrCreateWorkbench();
    UIDemoBuilder.createMenus(workbench);
    workbench.setSize(800, 1024);
    workbench.setVisible(true);
    UiSimplePanel panel2 = factory.createSimplePanel(Orientation.VERTICAL);
    UiButton button2 = factory.createButton("Button");
    panel2.addChild(factory.createLabel("Text"));
    panel2.addChild(button2);
    workbench.setComposite(panel2);
    final UiFrame frame = workbench.createFrame("TestFrame", true);
    UiSimplePanel panel = factory.createSimplePanel(Orientation.VERTICAL);
    UiButton button = factory.createButton("Button");
    // UiDecoratedComponent<UiLabel, UiButton> labeledButton =
    // factory.createLabeledComponent(
    // "Label:", button);
    // panel.addChild(labeledButton, LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    // UiTextField text = factory.createTextField();
    // UiDecoratedComponent<UiLabel, UiTextField> labeledText =
    // factory.createLabeledComponent(
    // "Label2:", text);
    // panel.addChild(labeledText, LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    panel.addChild(button);
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

    String title = UITestRunner3.class.getSimpleName();
    runTest(new net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing(title));
    runTest(new net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt(title));
  }

}
