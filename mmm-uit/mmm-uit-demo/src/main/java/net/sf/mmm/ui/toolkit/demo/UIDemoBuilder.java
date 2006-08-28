/* $Id: UIDemoBuilder.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.demo;

import java.io.File;
import java.io.IOException;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.composite.Alignment;
import net.sf.mmm.ui.toolkit.api.composite.Filling;
import net.sf.mmm.ui.toolkit.api.composite.Insets;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanelIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.api.widget.UIListIF;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBarIF;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBoxIF;
import net.sf.mmm.ui.toolkit.api.widget.UITableIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditorIF;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIWindowIF;
import net.sf.mmm.ui.toolkit.base.feature.MaximumSizer;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.base.model.UIDefaultListModel;
import net.sf.mmm.ui.toolkit.base.model.UIDefaultTreeModel;
import net.sf.mmm.ui.toolkit.base.model.UINumericRangeModel;
import net.sf.mmm.ui.toolkit.base.model.UITreeNode;

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

    public static UITabbedPanelIF createTabbedPanel(UIFactoryIF factory) {

        UITabbedPanelIF tabbedPanel = factory.createTabbedPanel();
        tabbedPanel.addComponent(createLayoutPanel(factory), "layout");
        tabbedPanel.addComponent(createSplitPanel(factory), "split");
        tabbedPanel.addComponent(createEditorPanel(factory), "edior", 1);
        tabbedPanel.addComponent(createTablePanel(factory), "table");
        tabbedPanel.addComponent(createModelPanel(factory), "model");
        return tabbedPanel;
    }

    public static void addEditorProperty(UIPanelIF editorPanel, String labelText,
            UIComponentIF component, MaximumSizer sizer) {

        UIFactoryIF factory = editorPanel.getFactory();
        UIPanelIF fieldEditorPanel = factory.createPanel(Orientation.HORIZONTAL);
        UILabelIF label = factory.createLabel(labelText);
        sizer.add(label);
        fieldEditorPanel.addComponent(label, new LayoutConstraints(Alignment.LEFT, Filling.NONE, 0,
                Insets.SMALL_SPACE_HORIZONTAL, sizer));
        fieldEditorPanel.addComponent(component, new LayoutConstraints(Alignment.CENTER,
                Filling.HORIZONTAL));
        editorPanel.addComponent(fieldEditorPanel, new LayoutConstraints(Alignment.CENTER,
                Filling.HORIZONTAL));

    }

    public static UICompositeIF createEditorPanel(final UIFactoryIF factory) {

        final UIPanelIF editorPanel = factory.createPanel(Orientation.VERTICAL);
        final MaximumSizer sizer = new MaximumSizer(true, false);
        addEditorProperty(editorPanel, "Name:", factory.createTextField(), sizer);
        addEditorProperty(editorPanel, "Quality-Ranking:", factory.createTextField(), sizer);
        UINumericRangeModel sbModel = new UINumericRangeModel();
        sbModel.setMaximumValue(50);
        sbModel.setMinimumValue(-10);
        addEditorProperty(editorPanel, "Port:", factory.createSpinBox(sbModel), sizer);
        final UINumericRangeModel maxSlideModel = new UINumericRangeModel(2, 100);
        final UISlideBarIF maxSlideBar = factory.createSlideBar(maxSlideModel);
        final UINumericRangeModel slideModel = new UINumericRangeModel(0, 7);
        final UISlideBarIF slideBar = factory.createSlideBar(slideModel);
        final UIProgressBarIF progressBar = factory.createProgressBar();
        maxSlideBar.addActionListener(new UIActionListenerIF() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
             * {@inheritDoc}
     */
            public void invoke(UINodeIF source, ActionType action) {

                int index = maxSlideBar.getSelectedIndex();
                Integer maxSelection = maxSlideModel.getElement(index);
                slideModel.setMaximumValue(maxSelection.intValue());
            }

        });
        slideBar.addActionListener(new UIActionListenerIF() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
             * {@inheritDoc}
     */
            public void invoke(UINodeIF source, ActionType action) {

                progressBar.setProgress(slideBar.getSelectedIndex());
            }

        });
        addEditorProperty(editorPanel, "Maximum:", maxSlideBar, sizer);
        addEditorProperty(editorPanel, "Ranking:", slideBar, sizer);
        progressBar.setProgress(70);
        addEditorProperty(editorPanel, "Progress:", progressBar, sizer);

        // TODO: this is a stupid and unix specific example!
        SimpleFileAccess access = new SimpleFileAccess(new File("/etc/mtab"));
        UIFileDownloadIF download = factory.createFileDownload(access);
        addEditorProperty(editorPanel, "BLOB:", download, sizer);

        UIPictureIF icon = null;
        try {
            // TODO add icon as resource and load it this way!
            icon = factory.createPicture(new File(
                    "/usr/share/icons/crystalsvg/22x22/mimetypes/spreadsheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        UIDateEditorIF dateEditor = factory.createDateEditor();
         addEditorProperty(editorPanel, "Date:", dateEditor, sizer);
        
        UIButtonIF imageButton = factory.createButton("Icon", icon, ButtonStyle.DEFAULT);
        addEditorProperty(editorPanel, "IconButton:", imageButton, sizer);
        imageButton.addActionListener(new UIActionListenerIF() {

            /**
             * @see net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF,
             *      net.sf.mmm.ui.toolkit.api.event.ActionType)
             * {@inheritDoc}
     */
            public void invoke(UINodeIF source, ActionType action) {

                UIButtonIF testButton = factory.createButton("Test");
                addEditorProperty(editorPanel, "Extra long special greedy Label:", testButton,
                        sizer);
            }
        });

        // editorPanel.addComponent(createTreePanel(factory));
        return factory.createScrollPanel(editorPanel);
    }

    public static UISplitPanelIF createSplitPanel(UIFactoryIF factory) {

        UISplitPanelIF splitPanel = factory.createSplitPanel(Orientation.HORIZONTAL);
        splitPanel.setTopOrLeftComponent(createTreePanel(factory));
        splitPanel.setBottomOrRightComponent(createListPanel(factory, createDemoListModel()));
        return splitPanel;
    }

    public static UIPanelIF createLayoutPanel(UIFactoryIF factory) {

        final UIPanelIF panel = factory.createPanel(Orientation.HORIZONTAL, "Panel");
        panel.addComponent(UIDemoBuilder.createListPanel(factory, createDemoListModel()));
        panel.addComponent(UIDemoBuilder.createTreePanel(factory));
        panel.addComponent(UIDemoBuilder.createRadioPanel(factory));
        UIButtonIF button = factory.createButton("Flip");
        button.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                panel.setOrientation(panel.getOrientation().getMirrored());
            }
        });
        panel.addComponent(button, new LayoutConstraints(Alignment.TOP_RIGHT, Filling.NONE, 1));
        return panel;
    }

    public static UIPanelIF createTablePanel(UIFactoryIF factory) {

        final UIPanelIF tablePanel = factory.createPanel(Orientation.VERTICAL, "Table");

        final UITableIF table = factory.createTable();
        UISimpleTableModel model = new UISimpleTableModel(10, 5);
        model.initColumnNames();
        model.initCells();
        table.setModel(model);
        tablePanel.addComponent(table);

        return tablePanel;
    }

    public static UIDefaultListModel<String> createDemoListModel() {

        UIDefaultListModel<String> listModel = new UIDefaultListModel<String>();
        listModel.addElement("Hi");
        listModel.addElement("this");
        listModel.addElement("is");
        listModel.addElement("a");
        listModel.addElement("test");
        return listModel;
    }

    public static UIPanelIF createListPanel(UIFactoryIF factory,
            final UIDefaultListModel<String> listModel) {

        final UIPanelIF listPanel = factory.createPanel(Orientation.VERTICAL, "List");

        final UIComboBoxIF<String> combo = factory.createComboBox(listModel);
        listPanel.addComponent(combo, new LayoutConstraints(Alignment.CENTER, Filling.HORIZONTAL,
                0.0));

        final UIListIF<String> list = factory.createList(listModel);
        listPanel.addComponent(list);
        listModel.addElement("!");

        final UIPanelIF buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

        final UIButtonIF addButton = factory.createButton("add");
        addButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                String text = combo.getText();
                int index = list.getSelectedIndex();
                if (index == -1) {
                    index = 0;
                }
                listModel.addElement(text, index);
            }
        });
        final UIButtonIF removeButton = factory.createButton("remove");
        removeButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

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

    public static void createMenus(final UIWindowIF frame) {

        UIMenuBarIF menubar = frame.getMenuBar();
        // file menu
        UIMenuIF fileMenu = menubar.addMenu("File");
        UIActionListenerIF loadAction = new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                frame.showMessage("You selected load", "Hi", MessageType.INFO);
            }

        };
        fileMenu.addItem("Load", loadAction);
        fileMenu.addSeparator();
        UIMenuIF subMenu = fileMenu.addSubMenu("Submenu");
        UIActionListenerIF checkAction = new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                if (action == ActionType.SELECT) {
                    UIMenuItemIF item = (UIMenuItemIF) source;
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
        UIMenuIF radioMenu = menubar.addMenu("Radio");
        final UIMenuItemIF[] colors = new UIMenuItemIF[] {
                radioMenu.addItem("blue", ButtonStyle.RADIO),
                radioMenu.addItem("green", ButtonStyle.RADIO),
                radioMenu.addItem("red", ButtonStyle.RADIO)};
        radioMenu.addSeparator();
        UIActionListenerIF colorAction = new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

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

    public static UIPanelIF createTreePanel(UIFactoryIF factory) {

        final UIPanelIF treePanel = factory.createPanel(Orientation.VERTICAL, "Tree");

        final UITreeIF tree = factory.createTree(false);
        final UIDefaultTreeModel<String> treeModel = new UIDefaultTreeModel<String>("root");
        UITreeNode<String> child1 = treeModel.getRoot().createChildNode("child");
        child1.createChildNode("sub-child");
        treeModel.getRoot().createChildNode("child2");
        tree.setModel(treeModel);
        treePanel.addComponent(tree);

        final UIPanelIF buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

        final UIButtonIF addButton = factory.createButton("add");
        addButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                UITreeNode<String> selection = (UITreeNode<String>) tree.getSelection();
                if (selection != null) {
                    selection.createChildNode(selection.toString() + ".child."
                            + selection.getChildNodeCount());
                }
            }
        });
        final UIButtonIF removeButton = factory.createButton("remove");
        removeButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                UITreeNode<String> selection = (UITreeNode<String>) tree.getSelection();
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

    public static UIPanelIF createModelPanel(UIFactoryIF factory) {

        final UIPanelIF modelPanel = factory.createPanel(Orientation.VERTICAL,
                "Model-View-Controller");
        final UIDefaultListModel<String> listModel = createDemoListModel();

        // add combo-box
        final UIComboBoxIF<String> combo = factory.createComboBox(listModel, true);
        final UIPanelIF buttonPanel = factory.createPanel(Orientation.HORIZONTAL);
        final UIButtonIF addButton = factory.createButton("add");
        final UIButtonIF removeButton = factory.createButton("remove");
        buttonPanel.addComponent(addButton, LayoutConstraints.FIXED_HORIZONTAL);
        buttonPanel.addComponent(removeButton, LayoutConstraints.FIXED_HORIZONTAL);
        buttonPanel.addComponent(combo);
        modelPanel.addComponent(buttonPanel, LayoutConstraints.FIXED_HORIZONTAL);

        // spin-box
        UISpinBoxIF<String> spinBox = factory.createSpinBox(listModel);
        modelPanel.addComponent(spinBox, LayoutConstraints.FIXED_HORIZONTAL);

        // slide-bar
        UISlideBarIF slideBar = factory.createSlideBar(listModel);
        modelPanel.addComponent(slideBar, LayoutConstraints.FIXED_HORIZONTAL);

        // add list
        final UIListIF<String> list = factory.createList(listModel);
        modelPanel.addComponent(list);

        addButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                String text = combo.getText();
                int index = list.getSelectedIndex();
                if (index < 0) {
                    index = 0;
                }
                listModel.addElement(text, index);
            }
        });
        removeButton.addActionListener(new UIActionListenerIF() {

            public void invoke(UINodeIF source, ActionType action) {

                int index = list.getSelectedIndex();
                if (index != -1) {
                    listModel.removeElement(index);
                }
            }
        });

        return modelPanel;
    }

    public static UIPanelIF createRadioPanel(UIFactoryIF factory) {

        UIPanelIF radioPanel = factory.createPanel(Orientation.VERTICAL, "Radios");
        UIButtonIF rb1 = factory.createButton("selection 1", ButtonStyle.RADIO);
        radioPanel.addComponent(rb1);
        UIButtonIF rb2 = factory.createButton("selection 2", ButtonStyle.RADIO);
        radioPanel.addComponent(rb2);
        return radioPanel;
    }

}
