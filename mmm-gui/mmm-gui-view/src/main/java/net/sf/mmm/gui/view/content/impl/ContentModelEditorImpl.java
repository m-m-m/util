/* $Id$ */
package net.sf.mmm.gui.view.content.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.gui.model.content.api.ContentClassFieldTableManager;
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

/**
 * This is the implementation of the {@link ContentModelEditorView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelEditorImpl implements ContentModelEditorView {

  /** @see #setContentModelService(ContentModelService) */
  private ContentModelService modelService;

  /** @see #setContentClassTreeModel(UITreeModel) */
  private UITreeModel<ContentClass> classTreeModel;

  /** @see #setFieldTableManager(ContentClassFieldTableManager) */
  private ContentClassFieldTableManager fieldTableManager;

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
  public void setContentModelService(ContentModelService contentModelService) {

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

    // field table
    final UITable<Object> table = uiFactory.createTable(false, this.fieldTableManager
        .getFieldTableModel(this.modelService.getRootClass()));

    // ########### content-class stuff ################
    
    // class editor panel
    UIPanel classEditorPanel = uiFactory.createPanel(Orientation.VERTICAL, "Class");

    final UITextField textId = uiFactory.createTextField(false);
    classEditorPanel.addComponent(uiFactory.createLabeledComponent("Id:", textId),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UITextField textName = uiFactory.createTextField(true);
    classEditorPanel.addComponent(uiFactory.createLabeledComponent("Name:", textName),
        LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    final UIButton checkSystem = uiFactory.createButton("system", ButtonStyle.CHECK);
    checkSystem.setEnabled(false);
    final UIButton checkExtendable = uiFactory.createButton("extendable", ButtonStyle.CHECK);
    checkExtendable.setEnabled(false);
    classEditorPanel.addComponent(uiFactory.createLabeledComponents("Flags:", checkSystem,
        checkExtendable), LayoutConstraints.FIXED_HORIZONTAL_INSETS);
    UIButton radioNormal = uiFactory.createButton("normal", ButtonStyle.RADIO);
    UIButton radioFinal = uiFactory.createButton("final", ButtonStyle.RADIO);
    UIButton radioAbstract = uiFactory.createButton("abstract", ButtonStyle.RADIO);
    classEditorPanel.addComponent(uiFactory.createLabeledComponents("Flags2:", radioNormal,
        radioAbstract, radioFinal), LayoutConstraints.FIXED_HORIZONTAL_INSETS);

    // class tree
    final UITree<ContentClass> classTree = uiFactory.createTree(false, this.classTreeModel);
    classTree.addActionListener(new UIActionListener() {

      public void invoke(UINode source, ActionType action) {

        if (action == ActionType.SELECT) {
          ContentClass contentClass = classTree.getSelection();
          table.setModel(ContentModelEditorImpl.this.fieldTableManager.getFieldTableModel(contentClass));
          textId.setText(contentClass.getId().toString());
          textName.setText(contentClass.getName());
          checkSystem.setSelected(contentClass.getModifiers().isSystem());
          checkExtendable.setSelected(contentClass.getModifiers().isExtendable());
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
    splitPanel.setBottomOrRightComponent(table);
    
    return splitPanel;
  }

}
