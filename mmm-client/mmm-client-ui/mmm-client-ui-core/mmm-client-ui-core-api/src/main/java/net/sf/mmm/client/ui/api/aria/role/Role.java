/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaAtomic;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaBusy;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaControls;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaDescribedBy;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaDisabled;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaDropEffect;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaFlowTo;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaGrabbed;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaHasPopup;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaHidden;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaInvalid;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaLabel;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaLabelledBy;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaLive;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaOwns;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaRelevant;

/**
 * This interface represents the abstract {@link #WAI_ARIA} <a
 * href="http://www.w3.org/TR/wai-aria/roles#roletype">roletype</a>. For each particular role a sub-interface
 * is defined for type-safe support of the standard. The ARIA roles and attributes aim to support
 * {@link net.sf.mmm.client.ui.api.common.Accessibility} for rich Internet applications (RIA).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface Role extends AttributeWriteAriaAtomic, AttributeWriteAriaBusy, AttributeWriteAriaControls,
    AttributeWriteAriaDescribedBy, AttributeWriteAriaDisabled, AttributeWriteAriaDropEffect, AttributeWriteAriaFlowTo,
    AttributeWriteAriaGrabbed, AttributeWriteAriaHasPopup, AttributeWriteAriaHidden, AttributeWriteAriaInvalid,
    AttributeWriteAriaLabel, AttributeWriteAriaLabelledBy, AttributeWriteAriaLive, AttributeWriteAriaOwns,
    AttributeWriteAriaRelevant {

  /** The name of the <code>role</code> attribute. */
  String HTML_ATTRIBUTE_ARIA_ROLE = "role";

  /**
   * {@link #getName() Name} of {@link RoleAlert}.
   */
  String ARIA_ROLE_ALERT = "alert";

  /**
   * {@link #getName() Name} of {@link RoleAlertDialog}.
   */
  String ARIA_ROLE_ALERT_DIALOG = "alertdialog";

  /**
   * {@link #getName() Name} of {@link RoleApplication}.
   */
  String ARIA_ROLE_APPLICATION = "application";

  /**
   * {@link #getName() Name} of {@link RoleArticle}.
   */
  String ARIA_ROLE_ARTICLE = "article";

  /**
   * {@link #getName() Name} of {@link RoleBanner}.
   */
  String ARIA_ROLE_BANNER = "banner";

  /**
   * {@link #getName() Name} of {@link RoleButton}.
   */
  String ARIA_ROLE_BUTTON = "button";

  /**
   * {@link #getName() Name} of {@link RoleCheckbox}.
   */
  String ARIA_ROLE_CHECKBOX = "checkbox";

  /**
   * {@link #getName() Name} of {@link RoleColumnHeader}.
   */
  String ARIA_ROLE_COLUMN_HEADER = "columnheader";

  /**
   * {@link #getName() Name} of {@link RoleCombobox}.
   */
  String ARIA_ROLE_COMBOBOX = "combobox";

  /**
   * {@link #getName() Name} of {@link RoleComposite}.
   */
  String ARIA_ROLE_COMPOSITE = "composite";

  /**
   * {@link #getName() Name} of {@link RoleComplementary}.
   */
  String ARIA_ROLE_COMPLEMENTARY = "complementary";

  /**
   * {@link #getName() Name} of {@link RoleContentInfo}.
   */
  String ARIA_ROLE_CONTENT_INFO = "contentinfo";

  /**
   * {@link #getName() Name} of {@link RoleDefinition}.
   */
  String ARIA_ROLE_DEFINITION = "definition";

  /**
   * {@link #getName() Name} of {@link RoleDialog}.
   */
  String ARIA_ROLE_DIALOG = "dialog";

  /**
   * {@link #getName() Name} of {@link RoleDirectory}.
   */
  String ARIA_ROLE_DIRECTORY = "directory";

  /**
   * {@link #getName() Name} of {@link RoleDocument}.
   */
  String ARIA_ROLE_DOCUMENT = "document";

  /**
   * {@link #getName() Name} of {@link RoleForm}.
   */
  String ARIA_ROLE_FORM = "form";

  /**
   * {@link #getName() Name} of {@link RoleGrid}.
   */
  String ARIA_ROLE_GRID = "grid";

  /**
   * {@link #getName() Name} of {@link RoleGridCell}.
   */
  String ARIA_ROLE_GRID_CELL = "gridcell";

  /**
   * {@link #getName() Name} of {@link RoleGroup}.
   */
  String ARIA_ROLE_GROUP = "group";

  /**
   * {@link #getName() Name} of {@link RoleHeading}.
   */
  String ARIA_ROLE_HEADING = "heading";

  /**
   * {@link #getName() Name} of {@link RoleImg}.
   */
  String ARIA_ROLE_IMG = "img";

  /**
   * {@link #getName() Name} of {@link RoleLandmark}.
   */
  String ARIA_ROLE_LANDMARK = "landmark";

  /**
   * {@link #getName() Name} of {@link RoleLink}.
   */
  String ARIA_ROLE_LINK = "link";

  /**
   * {@link #getName() Name} of {@link RoleList}.
   */
  String ARIA_ROLE_LIST = "list";

  /**
   * {@link #getName() Name} of {@link RoleListbox}.
   */
  String ARIA_ROLE_LISTBOX = "listbox";

  /**
   * {@link #getName() Name} of {@link RoleListItem}.
   */
  String ARIA_ROLE_LIST_ITEM = "listitem";

  /**
   * {@link #getName() Name} of {@link RoleLog}.
   */
  String ARIA_ROLE_LOG = "log";

  /**
   * {@link #getName() Name} of {@link RoleMain}.
   */
  String ARIA_ROLE_MAIN = "main";

  /**
   * {@link #getName() Name} of {@link RoleMarquee}.
   */
  String ARIA_ROLE_MARQUEE = "marquee";

  /**
   * {@link #getName() Name} of {@link RoleMath}.
   */
  String ARIA_ROLE_MATH = "math";

  /**
   * {@link #getName() Name} of {@link RoleMenu}.
   */
  String ARIA_ROLE_MENU = "menu";

  /**
   * {@link #getName() Name} of {@link RoleMenuBar}.
   */
  String ARIA_ROLE_MENU_BAR = "menubar";

  /**
   * {@link #getName() Name} of {@link RoleMenuItem}.
   */
  String ARIA_ROLE_MENU_ITEM = "menuitem";

  /**
   * {@link #getName() Name} of {@link RoleMenuItemCheckbox}.
   */
  String ARIA_ROLE_MENU_ITEM_CHECKBOX = "menuitemcheckbox";

  /**
   * {@link #getName() Name} of {@link RoleMenuItemRadio}.
   */
  String ARIA_ROLE_MENU_ITEM_RADIO = "menuitemradio";

  /**
   * {@link #getName() Name} of {@link RoleNavigation}.
   */
  String ARIA_ROLE_NAVIGATION = "navigation";

  /**
   * {@link #getName() Name} of {@link RoleNote}.
   */
  String ARIA_ROLE_NOTE = "note";

  /**
   * {@link #getName() Name} of {@link RoleOption}.
   */
  String ARIA_ROLE_OPTION = "option";

  /**
   * {@link #getName() Name} of {@link RolePresentation}.
   */
  String ARIA_ROLE_PRESENTATION = "presentation";

  /**
   * {@link #getName() Name} of {@link RoleProgressBar}.
   */
  String ARIA_ROLE_PROGRESS_BAR = "progressbar";

  /**
   * {@link #getName() Name} of {@link RoleRadio}.
   */
  String ARIA_ROLE_RADIO = "radio";

  /**
   * {@link #getName() Name} of {@link RoleRadioGroup}.
   */
  String ARIA_ROLE_RADIO_GROUP = "radiogroup";

  /**
   * {@link #getName() Name} of {@link RoleRegion}.
   */
  String ARIA_ROLE_REGION = "region";

  /**
   * {@link #getName() Name} of {@link RoleRow}.
   */
  String ARIA_ROLE_ROW = "row";

  /**
   * {@link #getName() Name} of {@link RoleRowGroup}.
   */
  String ARIA_ROLE_ROW_GROUP = "rowgroup";

  /**
   * {@link #getName() Name} of {@link RoleRowHeader}.
   */
  String ARIA_ROLE_ROW_HEADER = "rowheader";

  /**
   * {@link #getName() Name} of {@link RoleScrollbar}.
   */
  String ARIA_ROLE_SCROLLBAR = "scrollbar";

  /**
   * {@link #getName() Name} of {@link RoleSearch}.
   */
  String ARIA_ROLE_SEARCH = "search";

  /**
   * {@link #getName() Name} of {@link RoleSectionHead}.
   */
  String ARIA_ROLE_SECTION_HEAD = "sectionhead";

  /**
   * {@link #getName() Name} of {@link RoleSeparator}.
   */
  String ARIA_ROLE_SEPARATOR = "separator";

  /**
   * {@link #getName() Name} of {@link RoleSlider}.
   */
  String ARIA_ROLE_SLIDER = "slider";

  /**
   * {@link #getName() Name} of {@link RoleSpinButton}.
   */
  String ARIA_ROLE_SPIN_BUTTON = "spinbutton";

  /**
   * {@link #getName() Name} of {@link RoleStatus}.
   */
  String ARIA_ROLE_STATUS = "status";

  /**
   * {@link #getName() Name} of {@link RoleTab}.
   */
  String ARIA_ROLE_TAB = "tab";

  /**
   * {@link #getName() Name} of {@link RoleTabList}.
   */
  String ARIA_ROLE_TAB_LIST = "tablist";

  /**
   * {@link #getName() Name} of {@link RoleTabPanel}.
   */
  String ARIA_ROLE_TAB_PANEL = "tabpanel";

  /**
   * {@link #getName() Name} of {@link RoleTextbox}.
   */
  String ARIA_ROLE_TEXTBOX = "textbox";

  /**
   * {@link #getName() Name} of {@link RoleTimer}.
   */
  String ARIA_ROLE_TIMER = "timer";

  /**
   * {@link #getName() Name} of {@link RoleToolbar}.
   */
  String ARIA_ROLE_TOOLBAR = "toolbar";

  /**
   * {@link #getName() Name} of {@link RoleTooltip}.
   */
  String ARIA_ROLE_TOOLTIP = "tooltip";

  /**
   * {@link #getName() Name} of {@link RoleTree}.
   */
  String ARIA_ROLE_TREE = "tree";

  /**
   * {@link #getName() Name} of {@link RoleTreeGrid}.
   */
  String ARIA_ROLE_TREE_GRID = "treegrid";

  /**
   * {@link #getName() Name} of {@link RoleTreeItem}.
   */
  String ARIA_ROLE_TREE_ITEM = "treeitem";

  /**
   * @see net.sf.mmm.client.ui.api.attribute.AttributeReadAriaRole#getAriaRole()
   * 
   * @return the name of this {@link Role}.
   */
  String getName();

}
