/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.composite.Alignment;
import net.sf.mmm.ui.toolkit.api.composite.Filling;
import net.sf.mmm.ui.toolkit.api.composite.Insets;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBar;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIWindow;
import net.sf.mmm.ui.toolkit.base.feature.MaximumSizer;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.base.model.DefaultUIListModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeModel;
import net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeNode;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class UIDemoBuilder {

    /**
     * The constructor.
     */
    public UIDemoBuilder() {

        super();
    }

    public static UITabbedPanel createTabbedPanel(UIFactory factory) {

        UITabbedPanel tabbedPanel = factory.createTabbedPanel();
        tabbedPanel.addComponent(createLayoutPanel(factory), "layout");
        tabbedPanel.addComponent(createSplitPanel(factory), "split");
        tabbedPanel.addComponent(createEditorPanel(factory), "edior", 1);
        tabbedPanel.addComponent(createTablePanel(factory), "table");
        tabbedPanel.addComponent(createModelPanel(factory), "model");
        return tabbedPanel;
    }

    public static void addEditorProperty(UIPanel editorPanel, String labelText,
            UIComponent component, MaximumSizer sizer) {

        UIFactory factory = editorPanel.getFactory();
        UIPanel fieldEditorPanel = factory.createPanel(Orientation.HORIZONTAL);
        UILabel label = factory.createLabel(labelText);
        sizer.add(label);
        fieldEditorPanel.addComponent(label, new LayoutConstraints(Alignment.LEFT, Filling.NONE, 0,
                Insets.SMALL_SPACE_HORIZONTAL, sizer));
        fieldEditorPanel.addComponent(component, new LayoutConstraints(Alignment.CENTER,
                Filling.HORIZONTAL));
        editorPanel.addComponent(fieldEditorPanel, new LayoutConstraints(Alignment.CENTER,
                Filling.HORIZONTAL));

    }

    public static UIComposite createEditorPanel(final UIFactory factory) {

        final UIPanel editorPanel = factory.createPanel(Orientation.VERTICAL);
        final MaximumSizer sizer = new MaximumSizer(true, false);
        addEditorProperty(editorPanel, "Name:", factory.createTextField(), sizer);
        addEditorProperty(editorPanel, "Quality-Ranking:", factory.createTextField(), sizer);
        NumericUIRangeModel sbModel = new NumericUIRangeModel();
        sbModel.setMaximumValue(50);
        sbModel.setMinimumValue(-10);
        addEditorProperty(editorPanel, "Port:", factory.createSpinBox(sbModel), sizer);
        final NumericUIRangeModel maxSlideModel = new NumericUIRangeModel(2, 100);
        final UISlideBar maxSlideBar = factory.createSlideBar(maxSlideModel);
        final NumericUIRangeModel slideModel = new NumericUIRangeModel(0, 7);
        final UISlideBar slideBar = factory.createSlideBar(slideModel);
        final UIProgressBar progressBar = factory.createProgressBar();
        maxSlideBar.addActionListener(new UIActionListener() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListener#invoke(net.sf.mmm.ui.toolkit.api.UINode,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
     */
            public void invoke(UINode source, ActionType action) {

                int index = maxSlideBar.getSelectedIndex();
                Integer maxSelection = maxSlideModel.getElement(index);
                slideModel.setMaximumValue(maxSelection.intValue());
            }

        });
        slideBar.addActionListener(new UIActionListener() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListener#invoke(net.sf.mmm.ui.toolkit.api.UINode,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
     */
            public void invoke(UINode source, ActionType action) {

                progressBar.setProgress(slideBar.getSelectedIndex());
            }

        });
        addEditorProperty(editorPanel, "Maximum:", maxSlideBar, sizer);
        addEditorProperty(editorPanel, "Ranking:", slideBar, sizer);
        progressBar.setProgress(70);
        addEditorProperty(editorPanel, "Progress:", progressBar, sizer);

        // TODO: this is a stupid and unix specific example!
        SimpleFileAccess access = new SimpleFileAccess(new File("/etc/mtab"));
        UIFileDownload download = factory.createFileDownload(access);
        addEditorProperty(editorPanel, "BLOB:", download, sizer);

        UIPicture icon = null;
        try {
            // TODO add icon as resource and load it this way!
          String iconPath = UIDemoBuilder.class.getPackage().getName().replace('.', '/') + "/icon.png";
          URL iconUrl = Thread.currentThread().getContextClassLoader().getResource(iconPath);
            icon = factory.createPicture(iconUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        UIDateEditor dateEditor = factory.createDateEditor();
         addEditorProperty(editorPanel, "Date:", dateEditor, sizer);
        
        UIButton imageButton = factory.createButton("Icon", icon, ButtonStyle.DEFAULT);
        addEditorProperty(editorPanel, "IconButton:", imageButton, sizer);
        imageButton.addActionListener(new UIActionListener() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListener#invoke(net.sf.mmm.ui.toolkit.api.UINode,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
     */
            public void invoke(UINode source, ActionType action) {

                UIButton testButton = factory.createButton("Test");
                addEditorProperty(editorPanel, "Extra long special greedy Label:", testButton,
                        sizer);
            }
        });

        // editorPanel.addComponent(createTreePanel(factory));
        return factory.createScrollPanel(editorPanel);
    }

    public static UISplitPanel createSplitPanel(UIFactory factory) {

        UISplitPanel splitPanel = factory.createSplitPanel(Orientation.HORIZONTAL);
        splitPanel.setTopOrLeftComponent(createTreePanel(factory));
        splitPanel.setBottomOrRightComponent(createListPanel(factory, createDemoListModel()));
        return splitPanel;
    }

    public static UIPanel createLayoutPanel(UIFactory factory) {

        final UIPanel panel = factory.createPanel(Orientation.HORIZONTAL, "Panel");
        panel.addComponent(UIDemoBuilder.createListPanel(factory, createDemoListModel()));
        panel.addComponent(UIDemoBuilder.createTreePanel(factory));
        panel.addComponent(UIDemoBuilder.createRadioPanel(factory));
        UIButton button = factory.createButton("Flip");
        button.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                panel.setOrientation(panel.getOrientation().getMirrored());
            }
        });
        panel.addComponent(button, new LayoutConstraints(Alignment.TOP_RIGHT, Filling.NONE, 1));
        return panel;
    }

    public static UIPanel createTablePanel(UIFactory factory) {

        final UIPanel tablePanel = factory.createPanel(Orientation.VERTICAL, "Table");

        final UITable table = factory.createTable();
        UISimpleTableModel model = new UISimpleTableModel(10, 5);
        model.initColumnNames();
        model.initCells();
        table.setModel(model);
        tablePanel.addComponent(table);

        return tablePanel;
    }

    public static DefaultUIListModel<String> createDemoListModel() {

        DefaultUIListModel<String> listModel = new DefaultUIListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        listModel.addElement("a");
        listModel.addElement("test");
        return listModel;
    }

    public static UIPanel createListPanel(UIFactory factory,
            final DefaultUIListModel<String> listModel) {

        final UIPanel listPanel = factory.createPanel(Orientation.VERTICAL, "List");

        final UIComboBox<String> combo = factory.createComboBox(listModel);
        listPanel.addComponent(combo, new LayoutConstraints(Alignment.CENTER, Filling.HORIZONTAL,
                0.0));

        final UIList<String> list = factory.createList(listModel);
        listPanel.addComponent(list);
        listModel.addElement("!");

        final UIPanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

        final UIButton addButton = factory.createButton("add");
        addButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                String text = combo.getText();
                int index = list.getSelectedIndex();
                if (index == -1) {
                    index = 0;
                }
                listModel.addElement(text, index);
            }
        });
        final UIButton removeButton = factory.createButton("remove");
        removeButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                int index = list.getSelectedIndex();
                if (index != -1) {
                    listModel.removeElement(index);
                }
            }
        });

        buttonPanel.addComponent(addButton);
        buttonPanel.addComponent(removeButton);

        listPanel.addComponent(buttonPanel, LayoutConstraints.FIXED_NONE);

        return listPanel;

    }

    public static void createMenus(final UIWindow frame) {

        UIMenuBar menubar = frame.getMenuBar();
        // file menu
        UIMenu fileMenu = menubar.addMenu("File");
        UIActionListener loadAction = new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                frame.showMessage("You selected load", "Hi", MessageType.INFO);
            }

        };
        fileMenu.addItem("Load", loadAction);
        fileMenu.addSeparator();
        UIMenu subMenu = fileMenu.addSubMenu("Submenu");
        UIActionListener checkAction = new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                if (action == ActionType.SELECT) {
                    UIMenuItem item = (UIMenuItem) source;
                    boolean selection = item.isSelected();
                    String state = "uncheck";
                    if (selection) {
                        state = "check";
                    }
                    if (!frame.showQuestion("Do you want to " + state + "?", "Question")) {
                        item.setSelected(!selection);
                    }
                }
            }

        };
        subMenu.addItem("check", checkAction, ButtonStyle.CHECK);
        fileMenu.addSeparator();
        fileMenu.addItem(frame.getFactory().createPrintAction(frame.getComposite(), "Print map"));

        // radio menu
        UIMenu radioMenu = menubar.addMenu("Radio");
        final UIMenuItem[] colors = new UIMenuItem[] {
                radioMenu.addItem("blue", ButtonStyle.RADIO),
                radioMenu.addItem("green", ButtonStyle.RADIO),
                radioMenu.addItem("red", ButtonStyle.RADIO)};
        radioMenu.addSeparator();
        UIActionListener colorAction = new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                String color = "none";
                for (int i = 0; i < colors.length; i++) {
                    if (colors[i].isSelected()) {
                        color = colors[i].getText();
                    }
                }
                frame
                        .showMessage("You have choosen the color: " + color, "Color",
                                MessageType.INFO);
            }
        };
        radioMenu.addItem("color", colorAction);

    }

    public static UIPanel createTreePanel(UIFactory factory) {

        final UIPanel treePanel = factory.createPanel(Orientation.VERTICAL, "Tree");

        final UITree tree = factory.createTree(false);
        final DefaultUITreeModel<String> treeModel = new DefaultUITreeModel<String>("root");
        DefaultUITreeNode<String> child1 = treeModel.getRoot().createChildNode("child");
        child1.createChildNode("sub-child");
        treeModel.getRoot().createChildNode("child2");
        tree.setModel(treeModel);
        treePanel.addComponent(tree);

        final UIPanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

        final UIButton addButton = factory.createButton("add");
        addButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
                if (selection != null) {
                    selection.createChildNode(selection.toString() + ".child."
                            + selection.getChildNodeCount());
                }
            }
        });
        final UIButton removeButton = factory.createButton("remove");
        removeButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
                if (selection != null) {
                    selection.removeFromParent();
                }
            }
        });

        LayoutConstraints buttonConstraints = new LayoutConstraints(Alignment.CENTER,
                Filling.VERTICAL, 0, new Insets(4, 0, 4, 2));
        buttonPanel.addComponent(addButton, buttonConstraints);
        buttonPanel.addComponent(removeButton, buttonConstraints);

        treePanel.addComponent(buttonPanel, LayoutConstraints.FIXED_NONE);

        return treePanel;
    }

    public static UIPanel createModelPanel(UIFactory factory) {

        final UIPanel modelPanel = factory.createPanel(Orientation.VERTICAL,
                "Model-View-Controller");
        final DefaultUIListModel<String> listModel = createDemoListModel();

        // add combo-box
        final UIComboBox<String> combo = factory.createComboBox(listModel, true);
        final UIPanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);
        final UIButton addButton = factory.createButton("add");
        final UIButton removeButton = factory.createButton("remove");
        buttonPanel.addComponent(addButton, LayoutConstraints.FIXED_HORIZONTAL);
        buttonPanel.addComponent(removeButton, LayoutConstraints.FIXED_HORIZONTAL);
        buttonPanel.addComponent(combo);
        modelPanel.addComponent(buttonPanel, LayoutConstraints.FIXED_HORIZONTAL);

        // spin-box
        UISpinBox<String> spinBox = factory.createSpinBox(listModel);
        modelPanel.addComponent(spinBox, LayoutConstraints.FIXED_HORIZONTAL);

        // slide-bar
        UISlideBar slideBar = factory.createSlideBar(listModel);
        modelPanel.addComponent(slideBar, LayoutConstraints.FIXED_HORIZONTAL);

        // add list
        final UIList<String> list = factory.createList(listModel);
        modelPanel.addComponent(list);

        addButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                String text = combo.getText();
                int index = list.getSelectedIndex();
                if (index < 0) {
                    index = 0;
                }
                listModel.addElement(text, index);
            }
        });
        removeButton.addActionListener(new UIActionListener() {

            public void invoke(UINode source, ActionType action) {

                int index = list.getSelectedIndex();
                if (index != -1) {
                    listModel.removeElement(index);
                }
            }
        });

        return modelPanel;
    }

    public static UIPanel createRadioPanel(UIFactory factory) {

        UIPanel radioPanel = factory.createPanel(Orientation.VERTICAL, "Radios");
        UIButton rb1 = factory.createButton("selection 1", ButtonStyle.RADIO);
        radioPanel.addComponent(rb1);
        UIButton rb2 = factory.createButton("selection 2", ButtonStyle.RADIO);
        radioPanel.addComponent(rb2);
        return radioPanel;
    }

}
