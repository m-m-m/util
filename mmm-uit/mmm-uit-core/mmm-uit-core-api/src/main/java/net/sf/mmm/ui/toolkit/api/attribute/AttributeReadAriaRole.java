/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getAriaRole() ARIA role} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAriaRole {

  /**
   * A message with important, and usually time-sensitive, information. See related
   * {@link #ARIA_ROLE_ALERT_DIALOG alertdialog} and {@link #ARIA_ROLE_STATUS status}.
   */
  String ARIA_ROLE_ALERT = "alert";

  /**
   * A type of {@link #ARIA_ROLE_DIALOG dialog} that contains an alert message, where initial focus goes to an
   * element within the dialog. See related {@link #ARIA_ROLE_ALERT alert} and {@link #ARIA_ROLE_DIALOG
   * dialog}.
   */
  String ARIA_ROLE_ALERT_DIALOG = "alertdialog";

  /**
   * A region declared as a web application, as opposed to a web {@link #ARIA_ROLE_DOCUMENT document}.
   */
  String ARIA_ROLE_APPLICATION = "application";

  /**
   * A section of a page that consists of a composition that forms an independent part of a
   * {@link #ARIA_ROLE_DOCUMENT document}, page, or site.
   */
  String ARIA_ROLE_ARTICLE = "article";

  /**
   * A region that contains mostly site-oriented content, rather than page-specific content.
   */
  String ARIA_ROLE_BANNER = "banner";

  /**
   * An {@link #ARIA_ABSTRACT_ROLE_INPUT input} that allows for user-triggered actions when clicked or
   * pressed. See related {@link #ARIA_ROLE_LINK link}.
   */
  String ARIA_ROLE_BUTTON = "button";

  /**
   * A checkable {@link #ARIA_ABSTRACT_ROLE_INPUT input} that has three possible values: true, false, or
   * mixed.
   */
  String ARIA_ROLE_CHECKBOX = "checkbox";

  /**
   * A cell containing header information for a column.
   */
  String ARIA_ROLE_COLUMN_HEADER = "columnheader";

  /**
   * A presentation of a {@link #ARIA_ABSTRACT_ROLE_SELECT select}; usually similar to a
   * {@link #ARIA_ROLE_TEXTBOX textbox} where users can type ahead to select an option, or type to enter
   * arbitrary text as a new item in the list. See related {@link #ARIA_ROLE_LISTBOX listbox}.
   */
  String ARIA_ROLE_COMBOBOX = "combobox";

  /**
   * Abstract: A form of {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that performs an action but does not
   * receive input data.
   */
  String ARIA_ABSTRACT_ROLE_COMMAND = "command";

  /**
   * A supporting section of the {@link #ARIA_ROLE_DOCUMENT document}, designed to be complementary to the
   * main content at a similar level in the DOM hierarchy, but remains meaningful when separated from the main
   * content.
   */
  String ARIA_ROLE_COMPLEMENTARY = "complementary";

  /**
   * Abstract: A {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that may contain navigable descendants or owned
   * children.
   */
  String ARIA_ABSTRACT_ROLE_COMPOSITE = "composite";

  /**
   * A large perceivable region that contains information about the parent {@link #ARIA_ROLE_DOCUMENT
   * document}.
   */
  String ARIA_ROLE_CONTENT_INFO = "contentinfo";

  /**
   * A definition of a term or concept.
   */
  String ARIA_ROLE_DEFINITION = "definition";

  /**
   * A dialog is an application {@link #ARIA_ABSTRACT_ROLE_WINDOW window} that is designed to interrupt the
   * current processing of an application in order to prompt the user to enter information or require a
   * response. See related {@link #ARIA_ROLE_ALERT_DIALOG alertdialog}.
   */
  String ARIA_ROLE_DIALOG = "dialog";

  /**
   * A list of references to members of a group, such as a static table of contents.
   */
  String ARIA_ROLE_DIRECTORY = "directory";

  /**
   * A region containing related information that is declared as document content, as opposed to a web
   * {@link #ARIA_ROLE_APPLICATION application}.
   */
  String ARIA_ROLE_DOCUMENT = "document";

  /**
   * A landmark region that contains a collection of items and objects that, as a whole, combine to create a
   * form. See related {@link #ARIA_ROLE_SEARCH search}.
   */
  String ARIA_ROLE_FORM = "form";

  /**
   * A grid is an interactive control which contains cells of tabular data arranged in rows and columns, like
   * a table.
   */
  String ARIA_ROLE_GRID = "grid";

  /**
   * A cell in a {@link #ARIA_ROLE_GRID grid} or {@link #ARIA_ROLE_TREE_GRID treegrid}.
   */
  String ARIA_ROLE_GRID_CELL = "gridcell";

  /**
   * A set of user interface objects which are not intended to be included in a page summary or table of
   * contents by assistive technologies.
   */
  String ARIA_ROLE_GROUP = "group";

  /**
   * A heading for a section of the page.
   */
  String ARIA_ROLE_HEADING = "heading";

  /**
   * A container for a collection of elements that form an image.
   */
  String ARIA_ROLE_IMG = "img";

  /**
   * Abstract: A generic type of {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that allows user input.
   */
  String ARIA_ABSTRACT_ROLE_INPUT = "input";

  /**
   * A region of the page intended as a navigational landmark.
   */
  String ARIA_ABSTRACT_ROLE_LANDMARK = "landmark";

  /**
   * An interactive reference to an internal or external resource that, when activated, causes the user agent
   * to navigate to that resource. See related {@link #ARIA_ROLE_BUTTON button}.
   */
  String ARIA_ROLE_LINK = "link";

  /**
   * A group of non-interactive list items. See related {@link #ARIA_ROLE_LISTBOX listbox}.
   */
  String ARIA_ROLE_LIST = "list";

  /**
   * A {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that allows the user to select one or more items from a list
   * of choices. See related {@link #ARIA_ROLE_COMBOBOX combobox} and {@link #ARIA_ROLE_LIST list}.
   */
  String ARIA_ROLE_LISTBOX = "listbox";

  /**
   * A single item in a {@link #ARIA_ROLE_LIST list} or {@link #ARIA_ROLE_DIRECTORY directory}.
   */
  String ARIA_ROLE_LIST_ITEM = "listitem";

  /**
   * A type of live region where new information is added in meaningful order and old information may
   * disappear. See related {@link #ARIA_ROLE_MARQUEE marquee}.
   */
  String ARIA_ROLE_LOG = "log";

  /**
   * The main content of a {@link #ARIA_ROLE_DOCUMENT document}.
   */
  String ARIA_ROLE_MAIN = "main";

  /**
   * A type of live region where non-essential information changes frequently. See related
   * {@link #ARIA_ROLE_LOG log}.
   */
  String ARIA_ROLE_MARQUEE = "marquee";

  /**
   * Content that represents a mathematical expression.
   */
  String ARIA_ROLE_MATH = "math";

  /**
   * A type of {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that offers a list of choices to the user.
   */
  String ARIA_ROLE_MENU = "menu";

  /**
   * A presentation of {@link #ARIA_ROLE_MENU menu} that usually remains visible and is usually presented
   * horizontally.
   */
  String ARIA_ROLE_MENU_BAR = "menubar";

  /**
   * An option in a group of choices contained by a {@link #ARIA_ROLE_MENU menu} or
   * {@link #ARIA_ROLE_MENU_BAR menubar}.
   */
  String ARIA_ROLE_MENU_ITEM = "menuitem";

  /**
   * A checkable {@link #ARIA_ROLE_MENU_ITEM menuitem} that has three possible values: true, false, or mixed.
   */
  String ARIA_ROLE_MENU_ITEM_CHECKBOX = "menuitemcheckbox";

  /**
   * A checkable {@link #ARIA_ROLE_MENU_ITEM menuitem} in a group of menuitemradio roles, only one of which
   * can be checked at a time.
   */
  String ARIA_ROLE_MENU_ITEM_RADIO = "menuitemradio";

  /**
   * A collection of navigational elements (usually links) for navigating the {@link #ARIA_ROLE_DOCUMENT
   * document} or related documents.
   */
  String ARIA_ROLE_NAVIGATION = "navigation";

  /**
   * A section whose content is parenthetic or ancillary to the main content of the resource.
   */
  String ARIA_ROLE_NOTE = "note";

  /**
   * A selectable item in a select list (e.g. {@link #ARIA_ROLE_LIST list}, {@link #ARIA_ROLE_LISTBOX listbox}
   * , or {@link #ARIA_ROLE_COMBOBOX combobox}).
   */
  String ARIA_ROLE_OPTION = "option";

  /**
   * An element whose implicit native role semantics will not be mapped to the accessibility API.
   */
  String ARIA_ROLE_PRESENTATION = "presentation";

  /**
   * An element that displays the progress status for tasks that take a long time.
   */
  String ARIA_ROLE_PROGRESS_BAR = "progressbar";

  /**
   * A checkable {@link #ARIA_ABSTRACT_ROLE_INPUT input} in a group of radio roles, only one of which can be
   * checked at a time.
   */
  String ARIA_ROLE_RADIO = "radio";

  /**
   * A group of {@link #ARIA_ROLE_RADIO radio} buttons.
   */
  String ARIA_ROLE_RADIO_GRPOUP = "radiogroup";

  /**
   * Abstract: An {@link #ARIA_ABSTRACT_ROLE_INPUT input} representing a range of values that can be set by
   * the user.
   */
  String ARIA_ABSTRACT_ROLE_RANGE = "range";

  /**
   * A large perceivable section of a web page or {@link #ARIA_ROLE_DOCUMENT document}, that the author feels
   * is important enough to be included in a page summary or table of contents, for example, an area of the
   * page containing live sporting event statistics.
   */
  String ARIA_ROLE_REGION = "region";

  /**
   * Abstract: The base role from which all other roles in this taxonomy inherit.
   */
  String ARIA_ABSTRACT_ROLE_ROLETYPE = "roletype";

  /**
   * A row of {@link #ARIA_ROLE_GRID_CELL cells} in a {@link #ARIA_ROLE_GRID grid}.
   */
  String ARIA_ROLE_ROW = "row";

  /**
   * A group containing one or more {@link #ARIA_ROLE_ROW row} elements in a {@link #ARIA_ROLE_GRID grid}.
   */
  String ARIA_ROLE_ROW_GROUP = "rowgroup";

  /**
   * A cell containing header information for a {@link #ARIA_ROLE_ROW row} in a {@link #ARIA_ROLE_GRID grid}.
   */
  String ARIA_ROLE_ROW_HEADER = "rowheader";

  /**
   * A graphical object that controls the scrolling of content within a viewing area, regardless of whether
   * the content is fully displayed within the viewing area.
   */
  String ARIA_ROLE_SCROLLBAR = "scrollbar";

  /**
   * A landmark region that contains a collection of items and objects that, as a whole, combine to create a
   * search facility. See related {@link #ARIA_ROLE_FORM form}.
   */
  String ARIA_ROLE_SEARCH = "search";

  /**
   * Abstract: A renderable structural containment unit in a {@link #ARIA_ROLE_DOCUMENT document} or
   * {@link #ARIA_ROLE_APPLICATION application}.
   */
  String ARIA_ABSTRACT_ROLE_SECTION = "section";

  /**
   * Abstract: A structure that labels or summarizes the topic of its related section.
   */
  String ARIA_ABSTRACT_ROLE_SECTION_HEAD = "sectionhead";

  /**
   * Abstract: A form {@link #ARIA_ABSTRACT_ROLE_WIDGET widget} that allows the user to make selections from a
   * set of choices.
   * 
   * @see #ARIA_ROLE_LIST
   * @see #ARIA_ROLE_LISTBOX
   * @see #ARIA_ROLE_CHECKBOX
   * @see #ARIA_ROLE_RADIO_GRPOUP
   */
  String ARIA_ABSTRACT_ROLE_SELECT = "select";

  /**
   * A divider that separates and distinguishes sections of content or groups of {@link #ARIA_ROLE_MENU_ITEM
   * menuitems}.
   */
  String ARIA_ROLE_SEPARATOR = "separator";

  /**
   * A user {@link #ARIA_ABSTRACT_ROLE_INPUT input} where the user selects a value from within a given
   * {@link #ARIA_ABSTRACT_ROLE_RANGE range}.
   */
  String ARIA_ROLE_SLIDER = "slider";

  /**
   * A form of {@link #ARIA_ABSTRACT_ROLE_RANGE range} that expects the user to select from among discrete
   * choices.
   */
  String ARIA_ROLE_SPIN_BUTTON = "spinbutton";

  /**
   * A container whose content is advisory information for the user but is not important enough to justify an
   * {@link #ARIA_ROLE_ALERT alert}, often but not necessarily presented as a status bar. See related
   * {@link #ARIA_ROLE_ALERT alert}.
   */
  String ARIA_ROLE_STATUS = "status";

  /**
   * Abstract: A document structural element.
   */
  String ARIA_ABSTRACT_ROLE_STRUCTURE = "structure";

  /**
   * A grouping label providing a mechanism for selecting the tab content that is to be rendered to the user.
   */
  String ARIA_ROLE_TAB = "tab";

  /**
   * A list of tab elements, which are references to {@link #ARIA_ROLE_TAB_PANEL tabpanel} elements.
   */
  String ARIA_ROLE_TAB_LIST = "tablist";

  /**
   * A container for the resources associated with a tab, where each tab is contained in a
   * {@link #ARIA_ROLE_TAB_LIST tablist}.
   */
  String ARIA_ROLE_TAB_PANEL = "tabpanel";

  /**
   * {@link #ARIA_ABSTRACT_ROLE_INPUT Input} that allows free-form text as its value.
   */
  String ARIA_ROLE_TEXTBOX = "textbox";

  /**
   * A type of live region containing a numerical counter which indicates an amount of elapsed time from a
   * start point, or the time remaining until an end point.
   */
  String ARIA_ROLE_TIMER = "timer";

  /**
   * A collection of commonly used function buttons represented in compact visual form.
   */
  String ARIA_ROLE_TOOLBAR = "toolbar";

  /**
   * A contextual popup that displays a description for an element.
   */
  String ARIA_ROLE_TOOLTIP = "tooltip";

  /**
   * A type of list that may contain sub-level nested groups that can be collapsed and expanded.
   */
  String ARIA_ROLE_TREE = "tree";

  /**
   * A grid whose rows can be expanded and collapsed in the same manner as for a {@link #ARIA_ROLE_TREE tree}.
   */
  String ARIA_ROLE_TREE_GRID = "treegrid";

  /**
   * An option item of a {@link #ARIA_ROLE_TREE tree}. This is an element within a tree that may be expanded
   * or collapsed if it contains a sub-level group of {@link #ARIA_ROLE_TREE_ITEM treeitems}.
   */
  String ARIA_ROLE_TREE_ITEM = "treeitem";

  /**
   * Abstract: An interactive component of a graphical user interface (GUI).
   */
  String ARIA_ABSTRACT_ROLE_WIDGET = "widget";

  /**
   * Abstract: A browser or {@link #ARIA_ROLE_APPLICATION application} window.
   */
  String ARIA_ABSTRACT_ROLE_WINDOW = "window";

  /**
   * This method gets the <em>ARIA role</em> of this object. This attribute is defined by <a
   * href="http://www.w3.org/WAI/intro/aria.php">WAI-ARIA</a> and aims to support accessibility for rich
   * Internet applications (RIA).<br/>
   * See <code>ARIA_ROLE_*</code> constants (e.g. {@link #ARIA_ROLE_COMBOBOX}) for the defined roles. Please
   * note that <code>ARIA_ABSTRACT_ROLE_*</code> constants are just for documentation purpose and should never
   * be assigned as actual role.
   * 
   * @see AttributeReadAltText#getAltText()
   * 
   * @return the aria role or <code>null</code> if NOT set.
   */
  String getAriaRole();

}
