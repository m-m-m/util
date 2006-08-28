/* $Id: UITestRunner.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.demo;

import java.io.File;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UITextFieldIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.base.model.UIDefaultListModel;
import net.sf.mmm.ui.toolkit.base.model.UIDefaultTreeModel;
import net.sf.mmm.ui.toolkit.base.model.UINumericRangeModel;

/**
 * This is a test runner that tests the various UIFactory implementations.
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
    private static void runTest(UIFactoryIF factory) {

        System.out.println(factory);
        System.out.println(factory.getDisplay());
        final UIFrameIF frame = factory.createFrame("TestFrame");
        UIMenuIF fileMenu = frame.getMenuBar().addMenu("File");
        fileMenu.addItem("Load", new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                frame.showMessage("You selected load", "Hi", MessageType.INFO);
            }

        });
        fileMenu.addSeparator();
        UIMenuIF subMenu = fileMenu.addSubMenu("Submenu");
        subMenu.addItem("Test", new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                if (action == ActionType.SELECT) {
                    if (!frame.showQuestion("Did you mean to do that?", "Test")) {
                        ((UIMenuItemIF) source).setSelected(false);
                    }
                }
            }

        }, ButtonStyle.CHECK);
        /*
        final UISplitPanelIF splitPanel = factory.createSplitPanel(Orientation.HORIZONTAL);
        final UIDefaultListModel<String> listModel = new UIDefaultListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        UIComboBoxIF comboBox = factory.createComboBox(listModel);
        UIListIF list = factory.createList(listModel);
        listModel.addElement("a");
        listModel.addElement("test");

        // list.setEnabled(false);
        splitPanel.setTopOrLeftComponent(comboBox);
        */
        /*
        UITreeIF tree = factory.createTree(false);
        UIDefaultTreeModel<String> treeModel = new UIDefaultTreeModel<String>("root");
        treeModel.getRoot().createChildNode("child1");
        tree.setModel(treeModel);
        // tree.setEnabled(false);
        splitPanel.setBottomOrRightComponent(tree);
        // splitPanel.setEnabled(false);
        splitPanel.setDividerPosition(0.5);
         */
        UIScrollPanelIF scroll = factory.createScrollPanel();
        
        UIPanelIF panel = factory.createPanel(Orientation.VERTICAL);
        
        UIButtonIF button = factory.createButton("Test");
        panel.addComponent(button);
        button = factory.createButton("Test2");
        panel.addComponent(button);
        UITextFieldIF text = factory.createTextField();
        panel.addComponent(text);
        UILabelIF label = factory.createLabel("Label");
        panel.addComponent(label);
        UISlideBarIF slider = factory.createSlideBar(new UINumericRangeModel(0, 100));
        panel.addComponent(slider);
        FileAccessIF access = new SimpleFileAccess(new File("/etc/fstab"));
        UIFileDownloadIF download = factory.createFileDownload(access);
        panel.addComponent(download);
        
        scroll.setComponent(panel);
        
        UITabbedPanelIF tabPanel = factory.createTabbedPanel();
        tabPanel.addComponent(scroll, "Scroll");
        frame.setComposite(tabPanel);
        
        final UIDefaultListModel<String> listModel = new UIDefaultListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        UIComboBoxIF comboBox = factory.createComboBox(listModel);
        UIListIF list = factory.createList(listModel);
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

        //runTest(new net.sf.mmm.ui.toolkit.impl.swing.UIFactory());
        runTest(new net.sf.mmm.ui.toolkit.impl.swt.UIFactory());
    }

}