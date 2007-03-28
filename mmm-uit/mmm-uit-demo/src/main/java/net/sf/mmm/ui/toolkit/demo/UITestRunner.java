/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import java.io.File;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.base.model.DefaultUIListModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeModel;
import net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel;

/**
 * This is a test runner that tests the various UIFactorySwing implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class UITestRunner {

    /**
     * This method runs the demo test.
     * 
     * @param factory
     *        is the actual factory implementation to use.
     */
    private static void runTest(UIFactory factory) {

        System.out.println(factory);
        System.out.println(factory.getDisplay());
        final UIFrame frame = factory.createFrame("TestFrame");
        UIMenu fileMenu = frame.getMenuBar().addMenu("File");
        fileMenu.addItem("Load", new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                frame.showMessage("You selected load", "Hi", MessageType.INFO);
            }

        });
        fileMenu.addSeparator();
        UIMenu subMenu = fileMenu.addSubMenu("Submenu");
        subMenu.addItem("Test", new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                if (action == ActionType.SELECT) {
                    if (!frame.showQuestion("Did you mean to do that?", "Test")) {
                        ((UIMenuItem) source).setSelected(false);
                    }
                }
            }

        }, ButtonStyle.CHECK);
        /*
        final UISplitPanel splitPanel = factory.createSplitPanel(Orientation.HORIZONTAL);
        final DefaultUIListModel<String> listModel = new DefaultUIListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        UIComboBox comboBox = factory.createComboBox(listModel);
        UIList list = factory.createList(listModel);
        listModel.addElement("a");
        listModel.addElement("test");

        // list.setEnabled(false);
        splitPanel.setTopOrLeftComponent(comboBox);
        */
        /*
        UITree tree = factory.createTree(false);
        DefaultUITreeModel<String> treeModel = new DefaultUITreeModel<String>("root");
        treeModel.getRoot().createChildNode("child1");
        tree.setModel(treeModel);
        // tree.setEnabled(false);
        splitPanel.setBottomOrRightComponent(tree);
        // splitPanel.setEnabled(false);
        splitPanel.setDividerPosition(0.5);
         */
        UIScrollPanel scroll = factory.createScrollPanel();
        
        UISlicePanel panel = factory.createPanel(Orientation.VERTICAL);
        
        UIButton button = factory.createButton("Test");
        panel.addComponent(button);
        button = factory.createButton("Test2");
        panel.addComponent(button);
        UITextField text = factory.createTextField();
        panel.addComponent(text);
        UILabel label = factory.createLabel("Label");
        panel.addComponent(label);
        UISlideBar slider = factory.createSlideBar(new NumericUIRangeModel(0, 100));
        panel.addComponent(slider);
        FileAccess access = new SimpleFileAccess(new File("/etc/fstab"));
        UIFileDownload download = factory.createFileDownload(access);
        panel.addComponent(download);
        
        scroll.setComponent(panel);
        
        UITabbedPanel tabPanel = factory.createTabbedPanel();
        tabPanel.addComponent(scroll, "Scroll");
        frame.setComposite(tabPanel);
        
        final DefaultUIListModel<String> listModel = new DefaultUIListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        UIComboBox comboBox = factory.createComboBox(listModel);
        UIList list = factory.createList(listModel);
        panel.addComponent(comboBox);
        panel.addComponent(list);
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
     * @param args
     *        are the commandline arguments.
     */
    public static void main(String[] args) {

        runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing());
        //runTest(new net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt());
    }

}