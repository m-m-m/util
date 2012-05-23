/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.common.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridRow;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.base.feature.UiFileAccessSimple;
import net.sf.mmm.ui.toolkit.base.model.DefaultUIListModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeModel;
import net.sf.mmm.ui.toolkit.base.model.DefaultUITreeNode;
import net.sf.mmm.ui.toolkit.base.model.NumericUIRangeModel;
import net.sf.mmm.util.lang.api.Orientation;

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
    UiGridPanel panel = factory.createGridPanel();
    addEditorProperty(panel, "First name:", factory.createTextField());
    addEditorProperty(panel, "Last name:", factory.createTextField());
    addEditorProperty(panel, "Login:", factory.createTextField());
    addEditorProperty(panel, "Phone:", factory.createTextField());
    addEditorProperty(panel, "Room:", factory.createTextField());
    UiSimplePanel buttonPanel = factory.createSimplePanel(Orientation.HORIZONTAL);
    buttonPanel.addChild(factory.createButton("Create"));
    buttonPanel.addChild(factory.createButton("Cancel"));
    UiGridRow buttonRow = panel.createRow();
    buttonRow.addChild(buttonPanel);
    buttonRow.getCellInfo(0).setColumnSpan(2);
    panel.addChild(buttonRow);
    tabbedPanel.addChild(panel, "home");
    tabbedPanel.addChild(createLayoutPanel(factory), "search");
    tabbedPanel.addChild(createSplitPanel(factory), "view");
    tabbedPanel.insertChild(createEditorPanel(factory), "editor", 1);
    tabbedPanel.addChild(createTablePanel(factory), "table");
    tabbedPanel.addChild(createModelPanel(factory), "model");
    return tabbedPanel;
  }

  public static void addEditorProperty(UiGridPanel editorPanel, String labelText,
      UiElement component) {

    UiFactory factory = editorPanel.getFactory();
    UiGridRow row = editorPanel.createRow();
    UiLabel label = factory.createLabel(labelText);
    row.addChild(label);
    row.addChild(component);
    editorPanel.addChild(row);
    // fieldEditorPanel.addChild(label, new LayoutConstraints(Alignment.LEFT,
    // Filling.NONE, 0,
    // Insets.SMALL_SPACE_HORIZONTAL, sizer));
    // fieldEditorPanel.addChild(component,
    // new LayoutConstraints(Alignment.CENTER, Filling.HORIZONTAL));
    // editorPanel.addChild(fieldEditorPanel, new
    // LayoutConstraints(Alignment.CENTER,
    // Filling.HORIZONTAL));

  }

  public static UiComposite createEditorPanel(final UiFactory factory) {

    final UiGridPanel editorPanel = factory.createGridPanel();
    addEditorProperty(editorPanel, "Name:", factory.createTextField());
    addEditorProperty(editorPanel, "Quality-Ranking:", factory.createTextField());
    NumericUIRangeModel sbModel = new NumericUIRangeModel();
    sbModel.setMaximumValue(50);
    sbModel.setMinimumValue(-10);
    UiSpinBox<Integer> spinBox = factory.createSpinBox(sbModel);
    spinBox.setEditable(true);
    addEditorProperty(editorPanel, "Port:", spinBox);
    final NumericUIRangeModel maxSlideModel = new NumericUIRangeModel(2, 100);
    final UiSlideBar maxSlideBar = factory.createSlideBar(maxSlideModel);
    final NumericUIRangeModel slideModel = new NumericUIRangeModel(0, 7);
    final UiSlideBar slideBar = factory.createSlideBar(slideModel);
    final UiProgressBar progressBar = factory.createProgressBar();
    maxSlideBar.addListener(new UiEventListener() {

      /**
       * {@inheritDoc}
       */
      public void onEvent(UiNode source, UiEventType action) {

        int index = maxSlideBar.getSelectedIndex();
        Integer maxSelection = maxSlideModel.getElement(index);
        slideModel.setMaximumValue(maxSelection.intValue());
      }

    });
    slideBar.addListener(new UiEventListener() {

      /**
       * {@inheritDoc}
       */
      public void onEvent(UiNode source, UiEventType action) {

        progressBar.setProgress(slideBar.getSelectedIndex());
      }

    });
    addEditorProperty(editorPanel, "Maximum:", maxSlideBar);
    addEditorProperty(editorPanel, "Ranking:", slideBar);
    progressBar.setProgress(70);
    addEditorProperty(editorPanel, "Progress:", progressBar);

    // TODO: this is a stupid and unix specific example!
    UiFileAccessSimple access = new UiFileAccessSimple("/etc/mtab");
    UiFileDownload download = factory.createFileDownload(access);
    addEditorProperty(editorPanel, "BLOB:", download);

    UiImage icon = null;
    // TODO add icon as resource and load it this way!
    String iconPath = UIDemoBuilder.class.getPackage().getName().replace('.', '/') + "/icon.png";
    icon = factory.createImage(new UiFileAccessSimple("src/main/resources/" + iconPath));

    UiDateBox dateEditor = factory.createDateEditor();
    addEditorProperty(editorPanel, "Date:", dateEditor);

    UiButton imageButton = factory.createButton("Icon", ButtonStyle.DEFAULT);
    imageButton.setImage(icon);
    addEditorProperty(editorPanel, "IconButton:", imageButton);
    imageButton.addListener(new UiEventListener() {

      /**
       * {@inheritDoc}
       */
      public void onEvent(UiNode source, UiEventType action) {

        UiButton testButton = factory.createButton("Test");
        addEditorProperty(editorPanel, "Extra long special greedy Label:", testButton);
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

  public static UiSimplePanel createLayoutPanel(final UiFactory factory) {

    // final UiSlicePanel panel = factory.createPanel(Orientation.HORIZONTAL,
    // "Panel");
    UiSimplePanel panel = factory.createSimplePanel(Orientation.HORIZONTAL);
    panel.addChild(UIDemoBuilder.createListPanel(factory, createDemoListModel()));
    panel.addChild(UIDemoBuilder.createTreePanel(factory));
    panel.addChild(UIDemoBuilder.createRadioPanel(factory));
    UiButton button = factory.createButton("Flip");
    button.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

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
    panel.addChild(button);
    return panel;
  }

  public static UiSimplePanel createTablePanel(UiFactory factory) {

    final UiSimplePanel tablePanel = factory.createSimplePanel(Orientation.VERTICAL);

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

  public static UiSimplePanel createListPanel(UiFactory factory,
      final DefaultUIListModel<String> listModel) {

    final UiSimplePanel listPanel = factory.createSimplePanel(Orientation.VERTICAL);

    final UiComboBox<String> combo = factory.createComboBox(listModel);
    listPanel.addChild(combo);

    final UiList<String> list = factory.createList(listModel);
    listPanel.addChild(list);
    listModel.addElement("!");

    final UiSimplePanel buttonPanel = factory.createSimplePanel(Orientation.HORIZONTAL);

    final UiButton addButton = factory.createButton("add");
    addButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        String text = combo.getValue();
        int index = list.getSelectedIndex();
        if (index == -1) {
          index = 0;
        }
        listModel.addElement(text, index);
      }
    });
    final UiButton removeButton = factory.createButton("remove");
    removeButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        int index = list.getSelectedIndex();
        if (index != -1) {
          listModel.removeElement(index);
        }
      }
    });

    buttonPanel.addChild(addButton);
    buttonPanel.addChild(removeButton);

    listPanel.addChild(buttonPanel);

    return listPanel;

  }

  public static void createMenus(final UiFrame frame) {

    UiMenuBar menubar = frame.getMenuBar();
    // file menu
    UiMenu fileMenu = menubar.addMenu("File");
    UiEventListener loadAction = new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        frame.showMessage("You selected load", "Hi", MessageType.INFO);
      }

    };
    fileMenu.addItem("Load", loadAction);
    fileMenu.addSeparator();
    UiMenu subMenu = fileMenu.addSubMenu("Submenu");
    UiEventListener checkAction = new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        if (action == UiEventType.CLICK) {
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
    UiEventListener dummy = new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

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
    UiEventListener colorAction = new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

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

  public static UiSimplePanel createTreePanel(UiFactory factory) {

    final UiSimplePanel treePanel = factory.createSimplePanel(Orientation.VERTICAL);

    final UiTree tree = factory.createTree(false);
    final DefaultUITreeModel<String> treeModel = new DefaultUITreeModel<String>("root");
    DefaultUITreeNode<String> child1 = treeModel.getRoot().createChildNode("child");
    child1.createChildNode("sub-child");
    treeModel.getRoot().createChildNode("child2");
    tree.setModel(treeModel);
    treePanel.addChild(tree);

    final UiSimplePanel buttonPanel = factory.createSimplePanel(Orientation.HORIZONTAL);

    final UiButton addButton = factory.createButton("add");
    addButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
        if (selection != null) {
          selection.createChildNode(selection.toString() + ".child."
              + selection.getChildNodeCount());
        }
      }
    });
    final UiButton removeButton = factory.createButton("remove");
    removeButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        DefaultUITreeNode<String> selection = (DefaultUITreeNode<String>) tree.getSelection();
        if (selection != null) {
          selection.removeFromParent();
        }
      }
    });

    buttonPanel.addChild(addButton);
    buttonPanel.addChild(removeButton);

    treePanel.addChild(buttonPanel);

    return treePanel;
  }

  public static UiSimplePanel createModelPanel(UiFactory factory) {

    final UiSimplePanel modelPanel = factory.createSimplePanel(Orientation.VERTICAL);
    final DefaultUIListModel<String> listModel = createDemoListModel();

    // add combo-box
    final UiComboBox<String> combo = factory.createComboBox(listModel, true);
    final UiSimplePanel buttonPanel = factory.createSimplePanel(Orientation.HORIZONTAL);
    final UiButton addButton = factory.createButton("add");
    final UiButton removeButton = factory.createButton("remove");
    buttonPanel.addChild(addButton);
    buttonPanel.addChild(removeButton);
    buttonPanel.addChild(combo);
    modelPanel.addChild(buttonPanel);

    // spin-box
    UiSpinBox<String> spinBox = factory.createSpinBox(listModel);
    modelPanel.addChild(spinBox);

    // slide-bar
    UiSlideBar slideBar = factory.createSlideBar(listModel);
    modelPanel.addChild(slideBar);

    // add list
    final UiList<String> list = factory.createList(listModel);
    modelPanel.addChild(list);

    addButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        String text = combo.getValue();
        int index = list.getSelectedIndex();
        if (index < 0) {
          index = 0;
        }
        listModel.addElement(text, index);
      }
    });
    removeButton.addListener(new UiEventListener() {

      public void onEvent(UiNode source, UiEventType action) {

        int index = list.getSelectedIndex();
        if (index != -1) {
          listModel.removeElement(index);
        }
      }
    });

    return modelPanel;
  }

  public static UiSimplePanel createRadioPanel(UiFactory factory) {

    UiSimplePanel radioPanel = factory.createSimplePanel(Orientation.VERTICAL);
    UiButton rb1 = factory.createButton("selection 1", ButtonStyle.RADIO);
    radioPanel.addChild(rb1);
    UiButton rb2 = factory.createButton("selection 2", ButtonStyle.RADIO);
    radioPanel.addChild(rb2);
    return radioPanel;
  }

}
