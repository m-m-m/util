/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.types.Alignment;
import net.sf.mmm.ui.toolkit.api.types.Filling;
import net.sf.mmm.ui.toolkit.api.types.Orientation;
import net.sf.mmm.ui.toolkit.api.types.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.view.composite.Insets;
import net.sf.mmm.ui.toolkit.api.view.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.feature.MaximumSizer;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.base.model.DefaultUIListModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeNode;
import net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class UIDemoBuilder {

  /**
   * The constructor.
   */
  public UIDemoBuilder() {

    super();
  }

  public static UiTabPanel createTabbedPanel(UiFactory factory) {

    UiTabPanel tabbedPanel = factory.createTabbedPanel();
    UiSlicePanel panel = factory.createPanel(Orientation.VERTICAL);
    final MaximumSizer sizer = new MaximumSizer(true, false);
    addEditorProperty(panel, "First name:", factory.createTextField(), sizer);
    addEditorProperty(panel, "Last name:", factory.createTextField(), sizer);
    addEditorProperty(panel, "Login:", factory.createTextField(), sizer);
    addEditorProperty(panel, "Phone:", factory.createTextField(), sizer);
    addEditorProperty(panel, "Room:", factory.createTextField(), sizer);
    UiSlicePanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);
    LayoutConstraints constraints = new LayoutConstraints(Alignment.CENTER, Filling.NONE, 0,
        Insets.SMALL_SPACE_HORIZONTAL);
    buttonPanel.addChild(factory.createButton("Create"), constraints);
    buttonPanel.addChild(factory.createButton("Cancel"), constraints);
    panel.addChild(buttonPanel, LayoutConstraints.FIXED_HORIZONTAL);
    tabbedPanel.addChild(panel, "home");
    tabbedPanel.addChild(createLayoutPanel(factory), "search");
    tabbedPanel.addChild(createSplitPanel(factory), "view");
    tabbedPanel.insertChild(createEditorPanel(factory), "editor", 1);
    tabbedPanel.addChild(createTablePanel(factory), "table");
    tabbedPanel.addChild(createModelPanel(factory), "model");
    return tabbedPanel;
  }

  public static void addEditorProperty(UiSlicePanel editorPanel, String labelText,
      UiElement component, MaximumSizer sizer) {

    UiFactory factory = editorPanel.getFactory();
    UiSlicePanel fieldEditorPanel = factory.createPanel(Orientation.HORIZONTAL);
    UiLabel label = factory.createLabel(labelText);
    sizer.add(label);
    fieldEditorPanel.addChild(label, new LayoutConstraints(Alignment.LEFT, Filling.NONE, 0,
        Insets.SMALL_SPACE_HORIZONTAL, sizer));
    fieldEditorPanel.addChild(component,
        new LayoutConstraints(Alignment.CENTER, Filling.HORIZONTAL));
    editorPanel.addChild(fieldEditorPanel, new LayoutConstraints(Alignment.CENTER,
        Filling.HORIZONTAL));

  }

  public static UiComposite createEditorPanel(final UiFactory factory) {

    final UiSlicePanel editorPanel = factory.createPanel(Orientation.VERTICAL);
    final MaximumSizer sizer = new MaximumSizer(true, false);
    addEditorProperty(editorPanel, "Name:", factory.createTextField(), sizer);
    addEditorProperty(editorPanel, "Quality-Ranking:", factory.createTextField(), sizer);
    NumericUIRangeModel sbModel = new NumericUIRangeModel();
    sbModel.setMaximumValue(50);
    sbModel.setMinimumValue(-10);
    UiSpinBox<Integer> spinBox = factory.createSpinBox(sbModel);
    spinBox.setEditable(true);
    addEditorProperty(editorPanel, "Port:", spinBox, sizer);
    final NumericUIRangeModel maxSlideModel = new NumericUIRangeModel(2, 100);
    final UiSlideBar maxSlideBar = factory.createSlideBar(maxSlideModel);
    final NumericUIRangeModel slideModel = new NumericUIRangeModel(0, 7);
    final UiSlideBar slideBar = factory.createSlideBar(slideModel);
    final UiProgressBar progressBar = factory.createProgressBar();
    maxSlideBar.addActionListener(new UIActionListener() {

      /**
       * {@inheritDoc}
       */
      public void invoke(UiNode source, ActionType action) {

        int index = maxSlideBar.getSelectedIndex();
        Integer maxSelection = maxSlideModel.getElement(index);
        slideModel.setMaximumValue(maxSelection.intValue());
      }

    });
    slideBar.addActionListener(new UIActionListener() {

      /**
       * {@inheritDoc}
       */
      public void invoke(UiNode source, ActionType action) {

        progressBar.setProgress(slideBar.getSelectedIndex());
      }

    });
    addEditorProperty(editorPanel, "Maximum:", maxSlideBar, sizer);
    addEditorProperty(editorPanel, "Ranking:", slideBar, sizer);
    progressBar.setProgress(70);
    addEditorProperty(editorPanel, "Progress:", progressBar, sizer);

    // TODO: this is a stupid and unix specific example!
    SimpleFileAccess access = new SimpleFileAccess(new File("/etc/mtab"));
    UiFileDownload download = factory.createFileDownload(access);
    addEditorProperty(editorPanel, "BLOB:", download, sizer);

    UiImage icon = null;
    try {
      // TODO add icon as resource and load it this way!
      String iconPath = UIDemoBuilder.class.getPackage().getName().replace('.', '/') + "/icon.png";
      URL iconUrl = Thread.currentThread().getContextClassLoader().getResource(iconPath);
      icon = factory.createPicture(iconUrl);
    } catch (IOException e) {
      e.printStackTrace();
    }

    UiDateBox dateEditor = factory.createDateEditor();
    addEditorProperty(editorPanel, "Date:", dateEditor, sizer);

    UiButton imageButton = factory.createButton("Icon", icon, ButtonStyle.DEFAULT);
    addEditorProperty(editorPanel, "IconButton:", imageButton, sizer);
    imageButton.addActionListener(new UIActionListener() {

      /**
       * {@inheritDoc}
       */
      public void invoke(UiNode source, ActionType action) {

        UiButton testButton = factory.createButton("Test");
        addEditorProperty(editorPanel, "Extra long special greedy Label:", testButton, sizer);
      }
    });

    // editorPanel.addComponent(createTreePanel(factory));
    return factory.createScrollPanel(editorPanel);
  }

  public static UiSplitPanel createSplitPanel(UiFactory factory) {

    UiSplitPanel splitPanel = factory.createSplitPanel(Orientation.HORIZONTAL);
    splitPanel.setTopOrLeftComponent(createTreePanel(factory));
    splitPanel.setBottomOrRightComponent(createListPanel(factory, createDemoListModel()));
    return splitPanel;
  }

  public static UiSlicePanel createLayoutPanel(final UiFactory factory) {

    final UiSlicePanel panel = factory.createPanel(Orientation.HORIZONTAL, "Panel");
    panel.addChild(UIDemoBuilder.createListPanel(factory, createDemoListModel()));
    panel.addChild(UIDemoBuilder.createTreePanel(factory));
    panel.addChild(UIDemoBuilder.createRadioPanel(factory));
    UiButton button = factory.createButton("Flip");
    button.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        // panel.setOrientation(panel.getOrientation().getMirrored());
        ScriptOrientation so;
        if (factory.getScriptOrientation() == ScriptOrientation.LEFT_TO_RIGHT) {
          so = ScriptOrientation.RIGHT_TO_LEFT;
        } else {
          so = ScriptOrientation.LEFT_TO_RIGHT;
        }
        factory.setScriptOrientation(so);
      }
    });
    panel.addChild(button, new LayoutConstraints(Alignment.TOP_RIGHT, Filling.NONE, 1));
    return panel;
  }

  public static UiSlicePanel createTablePanel(UiFactory factory) {

    final UiSlicePanel tablePanel = factory.createPanel(Orientation.VERTICAL, "Table");

    final UiTable table = factory.createTable();
    UISimpleTableModel model = new UISimpleTableModel(10, 5);
    model.initColumnNames();
    model.initCells();
    table.setModel(model);
    tablePanel.addChild(table);

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

  public static UiSlicePanel createListPanel(UiFactory factory,
      final DefaultUIListModel<String> listModel) {

    final UiSlicePanel listPanel = factory.createPanel(Orientation.VERTICAL, "List");

    final UiComboBox<String> combo = factory.createComboBox(listModel);
    listPanel.addChild(combo, new LayoutConstraints(Alignment.CENTER, Filling.HORIZONTAL, 0.0));

    final UiList<String> list = factory.createList(listModel);
    listPanel.addChild(list);
    listModel.addElement("!");

    final UiSlicePanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

    final UiButton addButton = factory.createButton("add");
    addButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        String text = combo.getValue();
        int index = list.getSelectedIndex();
        if (index == -1) {
          index = 0;
        }
        listModel.addElement(text, index);
      }
    });
    final UiButton removeButton = factory.createButton("remove");
    removeButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        int index = list.getSelectedIndex();
        if (index != -1) {
          listModel.removeElement(index);
        }
      }
    });

    buttonPanel.addChild(addButton);
    buttonPanel.addChild(removeButton);

    listPanel.addChild(buttonPanel, LayoutConstraints.FIXED_NONE);

    return listPanel;

  }

  public static void createMenus(final UiWindow frame) {

    UiMenuBar menubar = frame.getMenuBar();
    // file menu
    UiMenu fileMenu = menubar.addMenu("File");
    UIActionListener loadAction = new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        frame.showMessage("You selected load", "Hi", MessageType.INFO);
      }

    };
    fileMenu.addItem("Load", loadAction);
    fileMenu.addSeparator();
    UiMenu subMenu = fileMenu.addSubMenu("Submenu");
    UIActionListener checkAction = new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        if (action == ActionType.SELECT) {
          UiMenuItem item = (UiMenuItem) source;
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
    fileMenu.addItem(frame.getFactory().createPrintUiAction(frame.getComposite(), "Print map"));

    // dummy
    menubar.addMenu("Cusomer");
    menubar.addMenu("Contract");
    menubar.addMenu("Product");

    // Admin menu
    UIActionListener dummy = new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

      }
    };
    UiMenu adminMenu = menubar.addMenu("Admin");
    UiMenu usersMenu = adminMenu.addSubMenu("Users");
    usersMenu.addItem("Search", dummy);
    usersMenu.addItem("Create", dummy);
    usersMenu.addItem("Manage permissions", dummy);
    UiMenu permissionsMenu = adminMenu.addSubMenu("Permissions");
    permissionsMenu.addItem("Search", dummy);
    permissionsMenu.addItem("Create", dummy);
    UiMenu mdMenu = adminMenu.addSubMenu("MasterData");

    // radio menu
    UiMenu radioMenu = menubar.addMenu("Help");
    final UiMenuItem[] colors = new UiMenuItem[] { radioMenu.addItem("blue", ButtonStyle.RADIO),
        radioMenu.addItem("green", ButtonStyle.RADIO), radioMenu.addItem("red", ButtonStyle.RADIO) };
    radioMenu.addSeparator();
    UIActionListener colorAction = new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        String color = "none";
        for (int i = 0; i < colors.length; i++) {
          if (colors[i].isSelected()) {
            color = colors[i].getValue();
          }
        }
        frame.showMessage("You have chosen the color: " + color, "Color", MessageType.INFO);
      }
    };
    radioMenu.addItem("color", colorAction);

  }

  public static UiSlicePanel createTreePanel(UiFactory factory) {

    final UiSlicePanel treePanel = factory.createPanel(Orientation.VERTICAL, "Tree");

    final UiTree tree = factory.createTree(false);
    final DefaultUITreeModel<String> treeModel = new DefaultUITreeModel<String>("root");
    DefaultUITreeNode<String> child1 = treeModel.getRoot().createChildNode("child");
    child1.createChildNode("sub-child");
    treeModel.getRoot().createChildNode("child2");
    tree.setModel(treeModel);
    treePanel.addChild(tree);

    final UiSlicePanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);

    final UiButton addButton = factory.createButton("add");
    addButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
        if (selection != null) {
          selection.createChildNode(selection.toString() + ".child."
              + selection.getChildNodeCount());
        }
      }
    });
    final UiButton removeButton = factory.createButton("remove");
    removeButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
        if (selection != null) {
          selection.removeFromParent();
        }
      }
    });

    LayoutConstraints buttonConstraints = new LayoutConstraints(Alignment.CENTER, Filling.VERTICAL,
        0, new Insets(4, 0, 4, 2));
    buttonPanel.addChild(addButton, buttonConstraints);
    buttonPanel.addChild(removeButton, buttonConstraints);

    treePanel.addChild(buttonPanel, LayoutConstraints.FIXED_NONE);

    return treePanel;
  }

  public static UiSlicePanel createModelPanel(UiFactory factory) {

    final UiSlicePanel modelPanel = factory.createPanel(Orientation.VERTICAL,
        "Model-View-Controller");
    final DefaultUIListModel<String> listModel = createDemoListModel();

    // add combo-box
    final UiComboBox<String> combo = factory.createComboBox(listModel, true);
    final UiSlicePanel buttonPanel = factory.createPanel(Orientation.HORIZONTAL);
    final UiButton addButton = factory.createButton("add");
    final UiButton removeButton = factory.createButton("remove");
    buttonPanel.addChild(addButton, LayoutConstraints.FIXED_HORIZONTAL);
    buttonPanel.addChild(removeButton, LayoutConstraints.FIXED_HORIZONTAL);
    buttonPanel.addChild(combo);
    modelPanel.addChild(buttonPanel, LayoutConstraints.FIXED_HORIZONTAL);

    // spin-box
    UiSpinBox<String> spinBox = factory.createSpinBox(listModel);
    modelPanel.addChild(spinBox, LayoutConstraints.FIXED_HORIZONTAL);

    // slide-bar
    UiSlideBar slideBar = factory.createSlideBar(listModel);
    modelPanel.addChild(slideBar, LayoutConstraints.FIXED_HORIZONTAL);

    // add list
    final UiList<String> list = factory.createList(listModel);
    modelPanel.addChild(list);

    addButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        String text = combo.getValue();
        int index = list.getSelectedIndex();
        if (index < 0) {
          index = 0;
        }
        listModel.addElement(text, index);
      }
    });
    removeButton.addActionListener(new UIActionListener() {

      public void invoke(UiNode source, ActionType action) {

        int index = list.getSelectedIndex();
        if (index != -1) {
          listModel.removeElement(index);
        }
      }
    });

    return modelPanel;
  }

  public static UiSlicePanel createRadioPanel(UiFactory factory) {

    UiSlicePanel radioPanel = factory.createPanel(Orientation.VERTICAL, "Radios");
    UiButton rb1 = factory.createButton("selection 1", ButtonStyle.RADIO);
    radioPanel.addChild(rb1);
    UiButton rb2 = factory.createButton("selection 2", ButtonStyle.RADIO);
    radioPanel.addChild(rb2);
    return radioPanel;
  }

}
