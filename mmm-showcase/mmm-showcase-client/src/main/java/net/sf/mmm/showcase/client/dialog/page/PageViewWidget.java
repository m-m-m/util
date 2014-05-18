/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.page;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.base.dialog.AttributeReadSlot;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;
import net.sf.mmm.client.ui.base.model.UiTreeNodeModel;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomPanel;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;
import net.sf.mmm.util.collection.base.TreeNodeSimple;

/**
 * This is the view of the {@link PageDialogController}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PageViewWidget extends UiWidgetCustomPanel<UiWidgetHorizontalPanel> implements AttributeReadSlot {

  /** @see #getSlot(DialogSlot) */
  private UiWidgetSlot mainSlot;

  /** @see #getNavigationTree() */
  private UiWidgetTree<TreeNodeSimple<PageInfo>> navigationTree;

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public PageViewWidget(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetHorizontalPanel.class));

    UiWidgetFactory factory = context.getWidgetFactory();
    UiTreeNodeModel<PageInfo> treeModel = new UiTreeNodeModel<>();
    TreeNodeSimple<PageInfo> rootNode = new TreeNodeSimple<>(new PageInfo("Features", DialogConstants.PLACE_HOME));
    rootNode.addChildValue(new PageInfo("Widgets", ShowcaseDialogConstants.PLACE_WIDGETS));
    rootNode.addChildValue(new PageInfo("Layout", null));
    rootNode.addChildValue(new PageInfo("Editor", null));
    rootNode.addChildValue(new PageInfo("Validation", null));
    rootNode.addChildValue(new PageInfo("Data-Binding", null));
    this.navigationTree = factory.createTree(treeModel, "Feature-Tree");
    this.navigationTree.setValue(rootNode);
    this.navigationTree.getSize().setMinimumHeight(LengthUnit.PIXEL.newLength(600));
    this.navigationTree.getSize().setMinimumWidth(LengthUnit.PIXEL.newLength(200));
    this.navigationTree.addSelectionHandler(new UiHandlerEventSelection<TreeNodeSimple<PageInfo>>() {

      @Override
      public void onSelectionChange(UiEventSelectionChange<TreeNodeSimple<PageInfo>> event) {

        TreeNodeSimple<PageInfo> selectedValue = PageViewWidget.this.navigationTree.getSelectedValue();
        if (selectedValue != null) {
          DialogPlace place = selectedValue.getValue().getPlace();
          if (place != null) {
            DialogManager dialogManager = getContext().getContainer().get(DialogManager.class);
            dialogManager.navigateTo(place);
          }
        }
      }
    });
    getDelegate().addChild(this.navigationTree);
    this.mainSlot = factory.create(UiWidgetSlot.class);
    getDelegate().addChild(this.mainSlot);
  }

  /**
   * @return the navigationTree
   */
  public UiWidgetTree<TreeNodeSimple<PageInfo>> getNavigationTree() {

    return this.navigationTree;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetSlot getSlot(DialogSlot slot) {

    if (DialogConstants.SLOT_PAGE_MAIN.equals(slot)) {
      return this.mainSlot;
    }
    return null;
  }

}
