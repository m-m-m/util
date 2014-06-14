/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.aria.role.RoleAlert;
import net.sf.mmm.client.ui.api.aria.role.RoleAlertDialog;
import net.sf.mmm.client.ui.api.aria.role.RoleApplication;
import net.sf.mmm.client.ui.api.aria.role.RoleArticle;
import net.sf.mmm.client.ui.api.aria.role.RoleBanner;
import net.sf.mmm.client.ui.api.aria.role.RoleButton;
import net.sf.mmm.client.ui.api.aria.role.RoleCheckbox;
import net.sf.mmm.client.ui.api.aria.role.RoleColumnHeader;
import net.sf.mmm.client.ui.api.aria.role.RoleCombobox;
import net.sf.mmm.client.ui.api.aria.role.RoleComplementary;
import net.sf.mmm.client.ui.api.aria.role.RoleComposite;
import net.sf.mmm.client.ui.api.aria.role.RoleContentInfo;
import net.sf.mmm.client.ui.api.aria.role.RoleDefinition;
import net.sf.mmm.client.ui.api.aria.role.RoleDialog;
import net.sf.mmm.client.ui.api.aria.role.RoleDirectory;
import net.sf.mmm.client.ui.api.aria.role.RoleDocument;
import net.sf.mmm.client.ui.api.aria.role.RoleForm;
import net.sf.mmm.client.ui.api.aria.role.RoleGrid;
import net.sf.mmm.client.ui.api.aria.role.RoleGridCell;
import net.sf.mmm.client.ui.api.aria.role.RoleGroup;
import net.sf.mmm.client.ui.api.aria.role.RoleHeading;
import net.sf.mmm.client.ui.api.aria.role.RoleImg;
import net.sf.mmm.client.ui.api.aria.role.RoleLink;
import net.sf.mmm.client.ui.api.aria.role.RoleList;
import net.sf.mmm.client.ui.api.aria.role.RoleListItem;
import net.sf.mmm.client.ui.api.aria.role.RoleListbox;
import net.sf.mmm.client.ui.api.aria.role.RoleLog;
import net.sf.mmm.client.ui.api.aria.role.RoleMain;
import net.sf.mmm.client.ui.api.aria.role.RoleMarquee;
import net.sf.mmm.client.ui.api.aria.role.RoleMath;
import net.sf.mmm.client.ui.api.aria.role.RoleMenu;
import net.sf.mmm.client.ui.api.aria.role.RoleMenuBar;
import net.sf.mmm.client.ui.api.aria.role.RoleMenuItem;
import net.sf.mmm.client.ui.api.aria.role.RoleMenuItemCheckbox;
import net.sf.mmm.client.ui.api.aria.role.RoleMenuItemRadio;
import net.sf.mmm.client.ui.api.aria.role.RoleNavigation;
import net.sf.mmm.client.ui.api.aria.role.RoleNote;
import net.sf.mmm.client.ui.api.aria.role.RoleOption;
import net.sf.mmm.client.ui.api.aria.role.RolePresentation;
import net.sf.mmm.client.ui.api.aria.role.RoleProgressBar;
import net.sf.mmm.client.ui.api.aria.role.RoleRadio;
import net.sf.mmm.client.ui.api.aria.role.RoleRadioGroup;
import net.sf.mmm.client.ui.api.aria.role.RoleRegion;
import net.sf.mmm.client.ui.api.aria.role.RoleRow;
import net.sf.mmm.client.ui.api.aria.role.RoleRowGroup;
import net.sf.mmm.client.ui.api.aria.role.RoleRowHeader;
import net.sf.mmm.client.ui.api.aria.role.RoleScrollbar;
import net.sf.mmm.client.ui.api.aria.role.RoleSearch;
import net.sf.mmm.client.ui.api.aria.role.RoleSeparator;
import net.sf.mmm.client.ui.api.aria.role.RoleSlider;
import net.sf.mmm.client.ui.api.aria.role.RoleSpinButton;
import net.sf.mmm.client.ui.api.aria.role.RoleStatus;
import net.sf.mmm.client.ui.api.aria.role.RoleTab;
import net.sf.mmm.client.ui.api.aria.role.RoleTabList;
import net.sf.mmm.client.ui.api.aria.role.RoleTabPanel;
import net.sf.mmm.client.ui.api.aria.role.RoleTextbox;
import net.sf.mmm.client.ui.api.aria.role.RoleTimer;
import net.sf.mmm.client.ui.api.aria.role.RoleToolbar;
import net.sf.mmm.client.ui.api.aria.role.RoleTooltip;
import net.sf.mmm.client.ui.api.aria.role.RoleTree;
import net.sf.mmm.client.ui.api.aria.role.RoleTreeGrid;
import net.sf.mmm.client.ui.api.aria.role.RoleTreeItem;
import net.sf.mmm.util.exception.api.IllegalCaseException;

/**
 * This is the implementation of {@link RoleFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RoleFactoryImpl implements RoleFactory {

  /**
   * The constructor.
   */
  public RoleFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <ROLE extends Role> ROLE createRole(Class<ROLE> roleInterface) {

    AbstractRole result;
    // simple if else implementation to be GWT compatible
    if (RoleAlert.class.equals(roleInterface)) {
      result = new RoleAlertImpl();
    } else if (RoleAlertDialog.class.equals(roleInterface)) {
      result = new RoleAlertDialogImpl();
    } else if (RoleApplication.class.equals(roleInterface)) {
      result = new RoleApplicationImpl();
    } else if (RoleArticle.class.equals(roleInterface)) {
      result = new RoleArticleImpl();
    } else if (RoleBanner.class.equals(roleInterface)) {
      result = new RoleBannerImpl();
    } else if (RoleButton.class.equals(roleInterface)) {
      result = new RoleButtonImpl();
    } else if (RoleCheckbox.class.equals(roleInterface)) {
      result = new RoleCheckboxImpl();
    } else if (RoleColumnHeader.class.equals(roleInterface)) {
      result = new RoleColumnHeaderImpl();
    } else if (RoleCombobox.class.equals(roleInterface)) {
      result = new RoleComboboxImpl();
    } else if (RoleComplementary.class.equals(roleInterface)) {
      result = new RoleComplementaryImpl();
    } else if (RoleComposite.class.equals(roleInterface)) {
      result = new RoleCompositeImpl();
    } else if (RoleContentInfo.class.equals(roleInterface)) {
      result = new RoleContentInfoImpl();
    } else if (RoleDefinition.class.equals(roleInterface)) {
      result = new RoleDefinitionImpl();
    } else if (RoleDialog.class.equals(roleInterface)) {
      result = new RoleDialogImpl();
    } else if (RoleDirectory.class.equals(roleInterface)) {
      result = new RoleDirectoryImpl();
    } else if (RoleDocument.class.equals(roleInterface)) {
      result = new RoleDocumentImpl();
    } else if (RoleForm.class.equals(roleInterface)) {
      result = new RoleFormImpl();
    } else if (RoleGrid.class.equals(roleInterface)) {
      result = new RoleGridImpl();
    } else if (RoleGridCell.class.equals(roleInterface)) {
      result = new RoleGridCellImpl();
    } else if (RoleGroup.class.equals(roleInterface)) {
      result = new RoleGroupImpl();
    } else if (RoleHeading.class.equals(roleInterface)) {
      result = new RoleHeadingImpl();
    } else if (RoleImg.class.equals(roleInterface)) {
      result = new RoleImgImpl();
    } else if (RoleLink.class.equals(roleInterface)) {
      result = new RoleLinkImpl();
    } else if (RoleList.class.equals(roleInterface)) {
      result = new RoleListImpl();
    } else if (RoleListbox.class.equals(roleInterface)) {
      result = new RoleListboxImpl();
    } else if (RoleListItem.class.equals(roleInterface)) {
      result = new RoleListItemImpl();
    } else if (RoleLog.class.equals(roleInterface)) {
      result = new RoleLogImpl();
    } else if (RoleMain.class.equals(roleInterface)) {
      result = new RoleMainImpl();
    } else if (RoleMarquee.class.equals(roleInterface)) {
      result = new RoleMarqueeImpl();
    } else if (RoleMath.class.equals(roleInterface)) {
      result = new RoleMathImpl();
    } else if (RoleMenu.class.equals(roleInterface)) {
      result = new RoleMenuImpl();
    } else if (RoleMenuBar.class.equals(roleInterface)) {
      result = new RoleMenuBarImpl();
    } else if (RoleMenuItem.class.equals(roleInterface)) {
      result = new RoleMenuItemImpl();
    } else if (RoleMenuItemCheckbox.class.equals(roleInterface)) {
      result = new RoleMenuItemCheckboxImpl();
    } else if (RoleMenuItemRadio.class.equals(roleInterface)) {
      result = new RoleMenuItemRadioImpl();
    } else if (RoleNavigation.class.equals(roleInterface)) {
      result = new RoleNavigationImpl();
    } else if (RoleNote.class.equals(roleInterface)) {
      result = new RoleNoteImpl();
    } else if (RoleOption.class.equals(roleInterface)) {
      result = new RoleOptionImpl();
    } else if (RolePresentation.class.equals(roleInterface)) {
      result = new RolePresentationImpl();
    } else if (RoleProgressBar.class.equals(roleInterface)) {
      result = new RoleProgressBarImpl();
    } else if (RoleRadio.class.equals(roleInterface)) {
      result = new RoleRadioImpl();
    } else if (RoleRadioGroup.class.equals(roleInterface)) {
      result = new RoleRadioGroupImpl();
    } else if (RoleRegion.class.equals(roleInterface)) {
      result = new RoleRegionImpl();
    } else if (RoleRow.class.equals(roleInterface)) {
      result = new RoleRowImpl();
    } else if (RoleRowGroup.class.equals(roleInterface)) {
      result = new RoleRowGroupImpl();
    } else if (RoleRowHeader.class.equals(roleInterface)) {
      result = new RoleRowHeaderImpl();
    } else if (RoleScrollbar.class.equals(roleInterface)) {
      result = new RoleScrollbarImpl();
    } else if (RoleSearch.class.equals(roleInterface)) {
      result = new RoleSearchImpl();
    } else if (RoleSeparator.class.equals(roleInterface)) {
      result = new RoleSeparatorImpl();
    } else if (RoleSlider.class.equals(roleInterface)) {
      result = new RoleSliderImpl();
    } else if (RoleSpinButton.class.equals(roleInterface)) {
      result = new RoleSpinButtonImpl();
    } else if (RoleStatus.class.equals(roleInterface)) {
      result = new RoleStatusImpl();
    } else if (RoleTab.class.equals(roleInterface)) {
      result = new RoleTabImpl();
    } else if (RoleTabList.class.equals(roleInterface)) {
      result = new RoleTabListImpl();
    } else if (RoleTabPanel.class.equals(roleInterface)) {
      result = new RoleTabPanelImpl();
    } else if (RoleTextbox.class.equals(roleInterface)) {
      result = new RoleTextboxImpl();
    } else if (RoleTimer.class.equals(roleInterface)) {
      result = new RoleTimerImpl();
    } else if (RoleToolbar.class.equals(roleInterface)) {
      result = new RoleToolbarImpl();
    } else if (RoleTooltip.class.equals(roleInterface)) {
      result = new RoleTooltipImpl();
    } else if (RoleTree.class.equals(roleInterface)) {
      result = new RoleTreeImpl();
    } else if (RoleTreeGrid.class.equals(roleInterface)) {
      result = new RoleTreeGridImpl();
    } else if (RoleTreeItem.class.equals(roleInterface)) {
      result = new RoleTreeItemImpl();
    } else {
      throw new IllegalCaseException(roleInterface.getName());
    }
    return (ROLE) result;
  }

}
