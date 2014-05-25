/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectedValue;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.common.SelectionChoice;
import net.sf.mmm.client.ui.api.common.SelectionOperation;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;

/**
 * This is the interface for the {@link UiFeature features} of an object that offers
 * {@link #getSelectedValues() selection of values}. Additionally, it allows to
 * {@link #addSelectionHandler(UiHandlerEventSelection) add} and
 * {@link #removeSelectionHandler(UiHandlerEventSelection) remove} instances of
 * {@link UiHandlerEventSelection}.
 *
 * @param <VALUE> is the generic type of the selectable {@link #getSelectedValues() values}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureSelectedValue<VALUE> extends UiFeature, AttributeWriteSelectedValue<VALUE>,
    AttributeWriteSelectionMode {

  /**
   * This method adds the given {@link UiHandlerEventSelection} to this object.
   *
   * @param handler is the {@link UiHandlerEventSelection} to add.
   */
  void addSelectionHandler(UiHandlerEventSelection<VALUE> handler);

  /**
   * This method removes the given {@link UiHandlerEventSelection} from this object.
   *
   * @param handler is the {@link UiHandlerEventSelection} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addSelectionHandler(UiHandlerEventSelection) registered} and nothing has
   *         changed.
   */
  boolean removeSelectionHandler(UiHandlerEventSelection<VALUE> handler);

  /**
   * This method allows to change the selection of a logical {@link SelectionChoice} in a generic way. The
   * following table shows the combinations and their effect:
   * <table border="1">
   * <tr>
   * <th>choice</th>
   * <th>operation</th>
   * <th>setSelection(choice, operation)</th>
   * <th>{@link net.sf.mmm.client.ui.api.common.SelectionMode#MULTIPLE_SELECTION multi selection} only</th>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#ALL}</td>
   * <td>{@link SelectionOperation#SET}</td>
   * <td>selects all items.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#ALL}</td>
   * <td>{@link SelectionOperation#ADD}</td>
   * <td>same effect as above.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#ALL}</td>
   * <td>{@link SelectionOperation#REMOVE}</td>
   * <td>de-selects all items so none is selected.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#ALL}</td>
   * <td>{@link SelectionOperation#TOGGLE}</td>
   * <td>toggles the selection of all items so the selection gets inverted.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#FIRST}</td>
   * <td>{@link SelectionOperation#SET}</td>
   * <td>clears the selection and then selects the first item.</td>
   * <td>no</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#FIRST}</td>
   * <td>{@link SelectionOperation#ADD}</td>
   * <td>Adds the first item to the current {@link #getSelectedValues() selection}.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#FIRST}</td>
   * <td>{@link SelectionOperation#REMOVE}</td>
   * <td>Removes the first item from the current {@link #getSelectedValues() selection}.</td>
   * <td>no</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#FIRST}</td>
   * <td>{@link SelectionOperation#TOGGLE}</td>
   * <td>Toggles the selection of the first item.</td>
   * <td>no</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#LAST}</td>
   * <td>{@link SelectionOperation#SET}</td>
   * <td>clears the selection and then selects the last item.</td>
   * <td>no</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#LAST}</td>
   * <td>{@link SelectionOperation#ADD}</td>
   * <td>Adds the last item to the current {@link #getSelectedValues() selection}.</td>
   * <td>yes</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#LAST}</td>
   * <td>{@link SelectionOperation#REMOVE}</td>
   * <td>Removes the last item from the current {@link #getSelectedValues() selection}.</td>
   * <td>no</td>
   * </tr>
   * <tr>
   * <td>{@link SelectionChoice#LAST}</td>
   * <td>{@link SelectionOperation#TOGGLE}</td>
   * <td>Toggles the selection of the last item.</td>
   * <td>no</td>
   * </tr>
   * </table>
   *
   * @param choice is the {@link SelectionChoice} indicating the items to select or deselect.
   * @param operation is the {@link SelectionOperation} indicating what to do with the <code>choice</code>.
   * @return <code>true</code> if the operation was successful, <code>false</code> otherwise. If there are no
   *         items at all <code>true</code> is returned in case of {@link SelectionChoice#ALL} and
   *         <code>false</code> otherwise.
   */
  boolean setSelection(SelectionChoice choice, SelectionOperation operation);

  /**
   * This method determines if the given logical {@link SelectionChoice} is currently selected.
   *
   * @param choice is the {@link SelectionChoice} to check.
   * @return <code>true</code> if the items identified by the given <code>choice</code> are currently
   *         selected, <code>false</code> otherwise (if at least one of them is NOT selected).
   */
  boolean isSelected(SelectionChoice choice);

}
