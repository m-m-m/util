/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This enum contains the available modes for the selection of items from a container widget (list, tree,
 * etc.).
 * 
 * @see net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectedValue
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum SelectionMode {

  /**
   * The selection mode for a single selection. By clicking on the item of a list or tree, that item will be
   * selected and visually highlighted. The previous selection will be replaced. Typically if you hold the
   * [control] key and click the selected item again, it will be deselected so that the selection is empty
   * (nothing is selected).
   */
  SINGLE_SELECTION,

  /**
   * The selection mode for selection of multiple items. The best usability for this mode is provided by
   * having checkboxes in front of every item. The user can select or deselect items by checking or unchecking
   * the according checkbox.<br/>
   * A simplified variant works without checkboxes and allows to extend the selection by holding the [control]
   * key and clicking on additional items. In that variant additionally when holding the [shift] key and and
   * clicking on an item, the including range from the first to the last items clicked is selected.
   */
  MULTIPLE_SELECTION,

}
