/* $Id$ */
package net.sf.mmm.gui.view.content.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.gui.model.content.api.ContentClassFieldTableManager;
import net.sf.mmm.gui.model.content.api.FieldTableModel;
import net.sf.mmm.gui.view.content.api.ContentModelEditorView;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.MessageType;

/**
 * This is the implementation of the {@link ContentModelEditorView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelEditorImpl implements ContentModelEditorView {

  /** @see #setContentModelService(ContentModelService) */
  private MutableContentModelService modelService;

  /** @see #setContentClassTreeModel(UITreeModel) */
  private UITreeModel<ContentClass> classTreeModel;

  /** @see #setFieldTableManager(ContentClassFieldTableManager) */
  private ContentClassFieldTableManager fieldTableManager;

  /** the current field table model */
  private FieldTableModel fieldTableModel;

  /**
   * The constructor.
   */
  public ContentModelEditorImpl() {

    super();
  }

  /**
   * This method sets
   * 
   * @param contentClassTreeModel
   */
  @Resource(name = "ContentClassTreeModel")
  public void setContentClassTreeModel(UITreeModel<ContentClass> contentClassTreeModel) {

    this.classTreeModel = contentClassTreeModel;
  }

  /**
   * 
   * @param contentModelService
   */
  @Resource
  public void setContentModelService(MutableContentModelService contentModelService) {

    this.modelService = contentModelService;
  }

  /**
   * This method injects the {@link ContentClassFieldTableManager} required by
   * this component.
   * 
   * @param tableManager
   */
  @Resource
  public void setFieldTableManager(ContentClassFieldTableManager tableManager) {

    this.fieldTableManager = tableManager;
  }

  /**
   * This method initializes the component.
   */
  @PostConstruct
  public void initialize() {

    if (this.modelService == null) {
      // throw new Resou
    }

  }

  /**
   * @see net.sf.mmm.gui.view.content.api.ContentModelEditorView#create(UIFactory)
   */
  public UIComposite create(UIFactory uiFactory) {

    // ########### content-field stuff ################

    UIPanel fieldEditorPanel = uiFactory.createPanel(Orientation.VERTICAL, "Field");
    final UITextField fieldIdText = uiFactory.createTextField(false);
    fieldEditorPanel.addComponent(uiFactory.createLabeledComponent("Id:", fieldIdText),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UITextField fieldNameText = uiFactory.createTextField(true);
    fieldEditorPanel.addComponent(uiFactory.createLabeledComponent("Name:", fieldNameText),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton fieldSystemCheckbox = uiFactory.createButton("system", ButtonStyle.CHECK);
    fieldSystemCheckbox.setEnabled(false);
    final UIButton fieldExtendableCheckbox = uiFactory
        .createButton("extendable", ButtonStyle.CHECK);
    fieldExtendableCheckbox.setEnabled(false);
    fieldEditorPanel.addComponent(uiFactory.createLabeledComponents("Flags:", fieldSystemCheckbox,
        fieldExtendableCheckbox), LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton fieldAddButton = uiFactory.createButton("add", ButtonStyle.DEFAULT);
    final UIButton fieldUpdateButton = uiFactory.createButton("update", ButtonStyle.DEFAULT);
    final UIButton fieldRemoveButton = uiFactory.createButton("remove", ButtonStyle.DEFAULT);
    fieldUpdateButton.setEnabled(false);
    fieldRemoveButton.setEnabled(false);
    UIPanel fieldButtonPanel = uiFactory.createPanel(Orientation.HORIZONTAL);
    fieldButtonPanel.addComponent(fieldAddButton, LayoutConstraints.SCALED_NO_FILL);
    fieldButtonPanel.addComponent(fieldUpdateButton, LayoutConstraints.SCALED_NO_FILL);
    fieldButtonPanel.addComponent(fieldRemoveButton, LayoutConstraints.SCALED_NO_FILL);
    fieldEditorPanel.addComponent(fieldButtonPanel, LayoutConstraints.SCALED_HORIZONTAL);

    // field table
    this.fieldTableModel = this.fieldTableManager.getFieldTableModel(this.modelService
        .getRootClass());
    final UITable<Object> table = uiFactory.createTable(false, this.fieldTableModel);
    table.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {

        if ((action == ActionType.SELECT) || (action == ActionType.DESELECT)) {
          boolean enabled = false;
          int rowIndex = table.getSelectedIndex();
          if (rowIndex >= 0) {
            ContentField contentField = ContentModelEditorImpl.this.fieldTableModel
                .getField(rowIndex);
            fieldIdText.setText(contentField.getId().toString());
            fieldNameText.setText(contentField.getName());
            enabled = !contentField.getModifiers().isSystem();
          } else {
            fieldIdText.setText("");
            fieldNameText.setText("");
          }
          fieldUpdateButton.setEnabled(enabled);
          fieldRemoveButton.setEnabled(enabled);
        }
      }

    });

    // field panel
    UIPanel fieldPanel = uiFactory.createPanel(Orientation.VERTICAL);
    fieldPanel.addComponent(fieldEditorPanel, LayoutConstraints.FIXED_HORIZONTAL);
    fieldPanel.addComponent(table);

    // ########### content-class stuff ################

    // class editor panel
    UIPanel classEditorPanel = uiFactory.createPanel(Orientation.VERTICAL, "Class");

    final UITextField classIdText = uiFactory.createTextField(false);
    classEditorPanel.addComponent(uiFactory.createLabeledComponent("Id:", classIdText),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UITextField classNameText = uiFactory.createTextField(true);
    classEditorPanel.addComponent(uiFactory.createLabeledComponent("Name:", classNameText),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton checkSystem = uiFactory.createButton("system", ButtonStyle.CHECK);
    checkSystem.setEnabled(false);
    final UIButton classExtendableCheckbox = uiFactory.createButton("extendable", ButtonStyle.CHECK);
    classExtendableCheckbox.setEnabled(false);
    classEditorPanel.addComponent(uiFactory.createLabeledComponents("Flags:", checkSystem,
        classExtendableCheckbox), LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    UIButton classNormalRadio = uiFactory.createButton("normal", ButtonStyle.RADIO);
    UIButton classFinalRadio = uiFactory.createButton("final", ButtonStyle.RADIO);
    UIButton classAbstractRadio = uiFactory.createButton("abstract", ButtonStyle.RADIO);
    classEditorPanel.addComponent(uiFactory.createLabeledComponents("Flags2:", classNormalRadio,
        classAbstractRadio, classFinalRadio), LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton classAddButton = uiFactory.createButton("add", ButtonStyle.DEFAULT);
    final UIButton classUpdateButton = uiFactory.createButton("update", ButtonStyle.DEFAULT);
    final UIButton classRemoveButton = uiFactory.createButton("remove", ButtonStyle.DEFAULT);
    classAddButton.setEnabled(false);
    classUpdateButton.setEnabled(false);
    classRemoveButton.setEnabled(false);
    UIPanel classButtonPanel = uiFactory.createPanel(Orientation.HORIZONTAL);
    classButtonPanel.addComponent(classAddButton, LayoutConstraints.SCALED_NO_FILL);
    classButtonPanel.addComponent(classUpdateButton, LayoutConstraints.SCALED_NO_FILL);
    classButtonPanel.addComponent(classRemoveButton, LayoutConstraints.SCALED_NO_FILL);
    classEditorPanel.addComponent(classButtonPanel, LayoutConstraints.SCALED_HORIZONTAL);
    // class tree
    final UITree<ContentClass> classTree = uiFactory.createTree(false, this.classTreeModel);
    classTree.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {

        if ((action == ActionType.SELECT) || (action == ActionType.DESELECT)) {

          ContentClass contentClass = classTree.getSelection();
          if (contentClass != null) {
            ContentModelEditorImpl.this.fieldTableModel = ContentModelEditorImpl.this.fieldTableManager
                .getFieldTableModel(contentClass);
            table.setModel(ContentModelEditorImpl.this.fieldTableModel);
            classIdText.setText(contentClass.getId().toString());
            classNameText.setText(contentClass.getName());
            checkSystem.setSelected(contentClass.getModifiers().isSystem());
            classExtendableCheckbox.setSelected(contentClass.getModifiers().isExtendable());
            if (ContentModelEditorImpl.this.fieldTableModel.getRowCount() > 0) {
              table.setSelectedIndex(0);
            } else {
              table.setSelectedIndex(-1);
            }
            boolean enabled = !contentClass.getModifiers().isSystem();
            classUpdateButton.setEnabled(enabled);
            classRemoveButton.setEnabled(enabled);
            fieldAddButton.setEnabled(enabled);
            classAddButton.setEnabled(contentClass.getModifiers().isExtendable());
          }
        }
      }
    });
    classAddButton.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {
        if (action == ActionType.SELECT) {
          ContentClass contentClass = classTree.getSelection();
          if (contentClass != null) {
            String name = classNameText.getText();
            try {
              ContentModelEditorImpl.this.modelService.createClass(contentClass, name, ClassModifiersImpl.NORMAL);              
            } catch (ContentModelException e) {
              classTree.getParentWindow().showMessage(e.getMessage(), "Error", MessageType.ERROR);
            }
          }
        }
      }
    });

    // class panel
    UIPanel classPanel = uiFactory.createPanel(Orientation.VERTICAL);
    classPanel.addComponent(classEditorPanel, LayoutConstraints.FIXED_HORIZONTAL);
    classPanel.addComponent(classTree);

    // main panel
    UISplitPanel splitPanel = uiFactory.createSplitPanel(Orientation.HORIZONTAL);
    splitPanel.setTopOrLeftComponent(classPanel);
    splitPanel.setDividerPosition(0.5);
    splitPanel.setBottomOrRightComponent(fieldPanel);

    return splitPanel;
  }

}
