/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.base.feature.UiFileAccessSimple;
import net.sf.mmm.ui.toolkit.base.model.DefaultUIListModel;
import net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This is a test runner that tests the various UIFactorySwing implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class UITestRunner {

  /**
   * This method runs the demo test.
   * 
   * @param factory is the actual factory implementation to use.
   */
  private static void runTest(UiFactory factory) {

    System.out.println(factory);
    System.out.println(factory.getDisplay());
    final UiFrame frame = factory.createFrame("TestFrame");
    UiMenu fileMenu = frame.getMenuBar().addMenu("File");
    fileMenu.addItem("Load", new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        frame.showMessage("You selected load", "Hi", MessageType.INFO);
      }

    });
    fileMenu.addSeparator();
    UiMenu subMenu = fileMenu.addSubMenu("Submenu");
    subMenu.addItem("Test", new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        if (action == UiEventType.CLICK) {
          if (!frame.showQuestion("Did you mean to do that?", "Test")) {
            ((UiMenuItem) source).setSelected(false);
          }
        }
      }

    }, ButtonStyle.CHECK);
    /*
     * final UISplitPanel splitPanel =
     * factory.createSplitPanel(Orientation.HORIZONTAL); final
     * DefaultUIListModel<String> listModel = new DefaultUIListModel<String>();
     * listModel.addElement("Hi"); listModel.addElement("this");
     * listModel.addElement("is"); UIComboBox comboBox =
     * factory.createComboBox(listModel); UIList list =
     * factory.createList(listModel); listModel.addElement("a");
     * listModel.addElement("test"); // list.setEnabled(false);
     * splitPanel.setTopOrLeftComponent(comboBox);
     */
    /*
     * UITree tree = factory.createTree(false); DefaultUITreeModel<String>
     * treeModel = new DefaultUITreeModel<String>("root");
     * treeModel.getRoot().createChildNode("child1"); tree.setModel(treeModel);
     * // tree.setEnabled(false); splitPanel.setBottomOrRightComponent(tree); //
     * splitPanel.setEnabled(false); splitPanel.setDividerPosition(0.5);
     */
    UiScrollPanel scroll = factory.createScrollPanel();

    UiSimplePanel panel = factory.createSimplePanel(Orientation.VERTICAL);

    UiButton button = factory.createButton("Test");
    panel.addChild(button);
    button = factory.createButton("Test2");
    panel.addChild(button);
    UiTextField text = factory.createTextField();
    panel.addChild(text);
    UiLabel label = factory.createLabel("Label");
    panel.addChild(label);
    UiSlideBar slider = factory.createSlideBar(new NumericUIRangeModel(0, 100));
    panel.addChild(slider);
    UiFileAccess access = new UiFileAccessSimple("/etc/fstab");
    UiFileDownload download = factory.createFileDownload(access);
    panel.addChild(download);

    scroll.setChild(panel);

    UiTabPanel tabPanel = factory.createTabbedPanel();
    tabPanel.addChild(scroll, "Scroll");
    frame.setComposite(tabPanel);

    final DefaultUIListModel<String> listModel = new DefaultUIListModel<String>();
    listModel.addElement("Hi");
    listModel.addElement("this");
    listModel.addElement("is");
    UiComboBox comboBox = factory.createComboBox(listModel);
    UiList list = factory.createList(listModel);
    panel.addChild(comboBox);
    panel.addChild(list);
    listModel.addElement("a");
    listModel.addElement("test");

    frame.setSize(500, 300);
    frame.centerWindow();
    frame.setVisible(true);

    while (frame.isVisible()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    frame.dispose();
    factory.dispose();
    System.out.println("done...");
  }

  /**
   * This method holds the main code to run this class. It will be called when
   * the class is started as java application.
   * 
   * @param args are the commandline arguments.
   */
  public static void main(String[] args) {

    // runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing());
    runTest(new net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt(UITestRunner.class.getSimpleName()));
  }

}
