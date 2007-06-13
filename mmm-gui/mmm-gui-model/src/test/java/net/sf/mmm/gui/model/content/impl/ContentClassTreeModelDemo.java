/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.gui.model.content.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.impl.AbstractMutableContentModelService;
import net.sf.mmm.content.model.impl.ConfiguredModelService;
import net.sf.mmm.gui.model.content.impl.ContentClassFieldTableManagerImpl;
import net.sf.mmm.gui.model.content.impl.ContentClassTreeModel;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassTreeModelDemo {

  /**
   * The constructor.
   */
  public ContentClassTreeModelDemo() {

    super();
  }

  /**
   * The main method to run this class.
   * 
   * @param args are the command-line arguments.
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {

    UIFactory uiFactory = new UIFactorySwing();
    MutableContentModelService modelService = new ConfiguredModelService();
    ContentClassTreeModel classModel = new ContentClassTreeModel();
    classModel.setModelService(modelService);
    classModel.initialize();

    /*
     * ValueService valueService = new StaticValueServiceImpl(); ValueTypeModel
     * valueTypeModel = new ValueTypeModel();
     * valueTypeModel.setValueService(valueService);
     * valueTypeModel.initialize(); ValueTypeWidgetFactory widgetFactory = new
     * ValueTypeWidgetFactory(); widgetFactory.setModel(valueTypeModel);
     * UIComboBox<ValueManager> combo =
     * widgetFactory.createValueTypeCombo(uiFactory);
     */

    UISlicePanel panel = uiFactory.createPanel(Orientation.VERTICAL);

    final UITextField textId = uiFactory.createTextField(false);
    panel.addComponent(uiFactory.createLabeledComponent("Id:", textId),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UITextField textName = uiFactory.createTextField(true);
    panel.addComponent(uiFactory.createLabeledComponent("Name:", textName),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton checkSystem = uiFactory.createButton("system", ButtonStyle.CHECK);
    checkSystem.setEnabled(false);
    final UIButton checkExtendable = uiFactory.createButton("extendable", ButtonStyle.CHECK);
    checkExtendable.setEnabled(false);
    panel.addComponent(uiFactory.createLabeledComponents("Flags:", checkSystem, checkExtendable),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    UIButton radioNormal = uiFactory.createButton("normal", ButtonStyle.RADIO);
    UIButton radioFinal = uiFactory.createButton("final", ButtonStyle.RADIO);
    UIButton radioAbstract = uiFactory.createButton("abstract", ButtonStyle.RADIO);
    panel.addComponent(uiFactory.createLabeledComponents("Flags2:", radioNormal, radioAbstract,
        radioFinal), LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UITree<ContentClass> tree = uiFactory.createTree(false, classModel);
    UISplitPanel splitPanel = uiFactory.createSplitPanel(Orientation.HORIZONTAL);
    splitPanel.setTopOrLeftComponent(tree);
    UIFrame frame = uiFactory.createFrame("test");
    final ContentClassFieldTableManagerImpl tableFactory = new ContentClassFieldTableManagerImpl();
    tableFactory.setContentModelService(modelService);
    final UITable<Object> table = uiFactory.createTable(false, tableFactory
        .getFieldTableModel(modelService.getRootClass()));
    tree.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {

        if (action == ActionType.SELECT) {
          ContentClass contentClass = tree.getSelection();
          table.setModel(tableFactory.getFieldTableModel(contentClass));
          textId.setText(contentClass.getId().toString());
          textName.setText(contentClass.getName());
          checkSystem.setSelected(contentClass.getModifiers().isSystem());
          checkExtendable.setSelected(contentClass.getModifiers().isExtendable());
        }
      }
    });
    UISplitPanel splitPanelRight = uiFactory.createSplitPanel(Orientation.VERTICAL);
    splitPanelRight.setTopOrLeftComponent(panel);
    splitPanelRight.setBottomOrRightComponent(table);
    splitPanel.setBottomOrRightComponent(splitPanelRight);
    frame.setComposite(splitPanel);
    frame.setSize(600, 400);
    frame.centerWindow();
    frame.setVisible(true);
    splitPanel.setDividerPosition(0.5);
    splitPanelRight.setDividerPosition(0.5);
  }

}
