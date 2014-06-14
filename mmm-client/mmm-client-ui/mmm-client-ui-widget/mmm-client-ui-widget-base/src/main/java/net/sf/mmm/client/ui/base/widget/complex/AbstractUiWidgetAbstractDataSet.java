/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionChoice;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.SelectionOperation;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataSet;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataSet;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable}.
 *
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Has to be either {@literal ITEM} or
 *        {@literal List<ITEM>}.
 * @param <ITEM> is the generic type of the items contained in this widget.
 * @param <ITEM_CONTAINER> is the generic type of the {@link #getItemContainer(Object) container} for the
 *        {@literal <ITEM>} objects. Typically a {@link ItemContainer}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractDataSet<ADAPTER extends UiWidgetAdapterAbstractDataSet<ITEM>, VALUE, ITEM, ITEM_CONTAINER>
    extends AbstractUiWidgetActive<ADAPTER, VALUE> implements UiWidgetAbstractDataSet<ITEM> {

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

  /** @see #getSelectedValues() */
  private final Set<ITEM_CONTAINER> selectedValues;

  /**
   * Temporary {@link Set} stored as instance for performance (as widgets are not really thread-safe anyways).
   *
   * @see #setSelectedValues(Collection)
   */
  private final Set<ITEM_CONTAINER> newSelectedValues;

  /** @see #getSummary() */
  private String summary;

  /** @see #getTitle() */
  private String title;

  /** @see #isTitleVisible() */
  private boolean titleVisible;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractDataSet(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.selectionMode = SelectionMode.SINGLE_SELECTION;
    this.selectedValues = new HashSet<ITEM_CONTAINER>();
    this.newSelectedValues = new HashSet<ITEM_CONTAINER>();
    this.title = "TODO: use setTitle(String)";
    this.titleVisible = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.selectionMode != null) {
      adapter.setSelectionMode(this.selectionMode);
    }
    if (this.summary != null) {
      adapter.setSummary(this.summary);
    }
    if (this.title != null) {
      adapter.setTitle(this.title);
    }
    if (this.titleVisible) {
      adapter.setTitleVisible(this.titleVisible);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSummary() {

    return this.summary;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSummary(String summary) {

    this.summary = summary;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSummary(summary);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.title = title;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitle(title);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTitleVisible() {

    return this.titleVisible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitleVisible(boolean titleVisible) {

    if (this.titleVisible == titleVisible) {
      return;
    }
    this.titleVisible = titleVisible;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitleVisible(titleVisible);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEditable(boolean editableFlag) {

    if (this.editable == editableFlag) {
      return;
    }
    if (hasWidgetAdapter()) {
      // TODO...
    }
    this.editable = editableFlag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    return this.selectionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    this.selectionMode = selectionMode;
    if ((this.selectionMode == SelectionMode.SINGLE_SELECTION) && (this.selectedValues.size() > 1)) {
      Iterator<ITEM_CONTAINER> iterator = this.selectedValues.iterator();
      // ITEM_CONTAINER firstSelection =
      iterator.next();
      while (iterator.hasNext()) {
        ITEM_CONTAINER rowContainer = iterator.next();
        doSetSelected(rowContainer, false);
        iterator.remove();
      }
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSelectionMode(selectionMode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSelectionHandler(UiHandlerEventSelection<ITEM> handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSelectionHandler(UiHandlerEventSelection<ITEM> handler) {

    return removeEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    return !this.selectedValues.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSelectionCount() {

    // this is only the default implementation and will be overridden more efficiently
    return this.selectedValues.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITEM getSelectedValue() {

    if (this.selectedValues.isEmpty()) {
      return null;
    }
    // TODO hohwille distinguish between 1 selected item and multi-selected items (e.g. for master/detail
    // panel usability, open details, etc.)
    return getItem(this.selectedValues.iterator().next());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ITEM> getSelectedValues() {

    List<ITEM> result = new ArrayList<ITEM>();
    for (ITEM_CONTAINER container : this.selectedValues) {
      result.add(getItem(container));
    }
    return result;
  }

  /**
   * Internal method to get the direct reference to the {@link #getSelectedValues() selected values}.
   *
   * @return the {@link Collection} with all currently selected item containers.
   */
  protected Collection<ITEM_CONTAINER> getSelectedValuesInternal() {

    return this.selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValue(ITEM selectedValue) {

    List<ITEM> selection;
    if (selectedValue == null) {
      selection = Collections.emptyList();
    } else {
      selection = Arrays.asList(selectedValue);
    }
    return setSelectedValues(selection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(Collection<ITEM> selectedItems) {

    return setSelectedValues(selectedItems, true);
  }

  /**
   * @see #setSelectedValues(Collection)
   *
   * @param selectedItems is the {@link Collection} with the {@link #getSelectedValues() values to select}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   * @return see {@link #setSelectedValues(Collection)}.
   */
  public boolean setSelectedValues(Collection<ITEM> selectedItems, boolean programmatic) {

    boolean success = true;
    switch (this.selectionMode) {
      case SINGLE_SELECTION:
        // determine item to select...
        ITEM selectionItem = null;
        ITEM_CONTAINER selectionItemContainer = null;
        if (!selectedItems.isEmpty()) {
          // in single selection mode, we only care for the first selection and ignore anything else...
          selectionItem = selectedItems.iterator().next();
          selectionItemContainer = getItemContainer(selectionItem);
        }

        // deselect current items, should be only one or no item...
        boolean selected = false;
        for (ITEM_CONTAINER itemContainer : this.selectedValues) {
          if (itemContainer == selectionItemContainer) {
            selected = true;
          } else {
            doSetSelected(itemContainer, false);
          }
        }

        // update selection...
        this.selectedValues.clear();
        if (selectionItemContainer == null) {
          success = selectedItems.isEmpty();
        } else {
          this.selectedValues.add(selectionItemContainer);
          if (!selected) {
            doSetSelected(selectionItemContainer, true);
          }
        }
        break;
      case MULTIPLE_SELECTION:
        this.newSelectedValues.clear();
        for (ITEM itemToSelect : selectedItems) {
          ITEM_CONTAINER itemContainer = getItemContainer(itemToSelect);
          if (itemContainer == null) {
            success = false;
          } else {
            this.newSelectedValues.add(itemContainer);
            boolean isNew = this.selectedValues.add(itemContainer);
            if (isNew) {
              doSetSelected(itemContainer, true);
            }
          }
        }
        // remove and deselect items not selected anymore...
        Iterator<ITEM_CONTAINER> selectionIterator = this.selectedValues.iterator();
        while (selectionIterator.hasNext()) {
          ITEM_CONTAINER itemContainer = selectionIterator.next();
          if (!this.newSelectedValues.contains(itemContainer)) {
            doSetSelected(itemContainer, false);
            selectionIterator.remove();
          }
        }
        break;
      default :
        break;
    }
    fireEvent(new UiEventSelectionChange<ITEM>(this, programmatic));
    return success;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelection(SelectionChoice choice, SelectionOperation operation) {

    NlsNullPointerException.checkNotNull(SelectionChoice.class, choice);
    NlsNullPointerException.checkNotNull(SelectionOperation.class, operation);
    boolean success = true;
    boolean suppressEvent = false;
    Collection<ITEM_CONTAINER> allItems = getAllAvailableItems();
    if (choice == SelectionChoice.ALL) {
      if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
        // this does not actually make sense but we try to keep this robust
        getLogger().warn("Inconsistency: SelectionChoice ALL invoked in single selection mode");
        if (allItems.isEmpty()) {
          suppressEvent = true;
        } else {
          ITEM_CONTAINER firstItemContainer = getFirstAvailableItem();
          setSelection(firstItemContainer, operation);
        }
      } else {
        if (operation == SelectionOperation.REMOVE) {
          // more efficient than else block...
          clearSelection(null);
        } else {
          for (ITEM_CONTAINER itemContainer : allItems) {
            setSelection(itemContainer, operation);
          }
        }
      }
    } else {
      if (allItems.isEmpty()) {
        success = false;
      } else {
        ITEM_CONTAINER selectedItemContainer;
        if (choice == SelectionChoice.FIRST) {
          selectedItemContainer = getFirstAvailableItem();
        } else if (choice == SelectionChoice.LAST) {
          selectedItemContainer = getLastAvailableItem();
        } else {
          throw new IllegalCaseException(SelectionChoice.class, choice);
        }
        if (operation == SelectionOperation.SET) {
          // clear current selection...
          clearSelection(selectedItemContainer);
        } else if ((operation == SelectionOperation.ADD) && (getSelectionMode() == SelectionMode.SINGLE_SELECTION)) {
          getLogger().warn("Inconsistency: SelectionOperation ADD invoked in single selection mode");
          clearSelection(selectedItemContainer);
        }
        setSelection(selectedItemContainer, operation);
      }
    }
    if (success && !suppressEvent) {
      fireEvent(new UiEventSelectionChange<ITEM>(this, true));
    }
    return success;
  }

  /**
   * Clears the {@link #getSelectedValues() current selection} except for the given <code>exclusion</code>.
   *
   * @param exclusion is the itemContainer to exclude (keep its selection untouched) or <code>null</code> for
   *        no exclusion.
   */
  protected void clearSelection(ITEM_CONTAINER exclusion) {

    Iterator<ITEM_CONTAINER> selectionIterator = this.selectedValues.iterator();
    while (selectionIterator.hasNext()) {
      ITEM_CONTAINER itemContainer = selectionIterator.next();
      if (itemContainer != exclusion) {
        doSetSelected(itemContainer, false);
        selectionIterator.remove();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelected(SelectionChoice choice) {

    if (choice == SelectionChoice.ALL) {
      return (getAllAvailableItems().size() == this.selectedValues.size());
    } else {
      ITEM_CONTAINER selectedItemContainer;
      if (choice == SelectionChoice.FIRST) {
        selectedItemContainer = getFirstAvailableItem();
      } else if (choice == SelectionChoice.LAST) {
        selectedItemContainer = getLastAvailableItem();
      } else {
        throw new IllegalCaseException(SelectionChoice.class, choice);
      }
      if (selectedItemContainer == null) {
        return false;
      } else {
        return this.selectedValues.contains(selectedItemContainer);
      }
    }
  }

  /**
   * Updates the selection of the given <code>itemContainer</code> according to the given
   * <code>operation</code>.
   *
   * @param itemContainer is the container with the item to update.
   * @param operation is the {@link SelectionOperation} to perform.
   * @return the new selection state of the <code>itemContainer</code>.
   */
  protected boolean setSelection(ITEM_CONTAINER itemContainer, SelectionOperation operation) {

    boolean selected;
    if (operation == SelectionOperation.TOGGLE) {
      selected = this.selectedValues.contains(itemContainer);
    } else if (operation == SelectionOperation.REMOVE) {
      selected = false;
    } else {
      selected = true;
    }
    doSetSelected(itemContainer, selected);
    if (selected) {
      this.selectedValues.add(itemContainer);
    } else {
      this.selectedValues.remove(itemContainer);
    }
    return selected;
  }

  /**
   * Called from adapter if an item ({@literal <ITEM>}) has been (de)selected.
   *
   * @param itemContainer is the container with the item to update.
   * @param selected - <code>true</code> if item has been selected, <code>false</code> if deselected.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  public void onItemSelection(ITEM_CONTAINER itemContainer, boolean selected, boolean programmatic) {

    boolean fireEvent;
    if (selected) {
      if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
        clearSelection(itemContainer);
      }
      fireEvent = this.selectedValues.add(itemContainer);
    } else {
      fireEvent = this.selectedValues.remove(itemContainer);
    }
    if (fireEvent) {
      fireEvent(new UiEventSelectionChange<ITEM>(this, programmatic));
    }
  }

  /**
   * Internal method to get the selection status of the item in the given <code>container</code>.
   *
   * @param container is the container with the item to check.
   * @return <code>true</code> if the item is selected, <code>false</code> otherwise.
   */
  protected boolean doGetSelected(ITEM_CONTAINER container) {

    return this.selectedValues.contains(container);
  }

  /**
   * Internal method to set the selection of the item in the given <code>container</code>. Shall not do
   * anything else (e.g. firing event).
   *
   * @param container is the container with the item to mark.
   * @param selected - <code>true</code> to select, <code>false</code> to deselect.
   */
  protected abstract void doSetSelected(ITEM_CONTAINER container, boolean selected);

  /**
   * Internal method to get the {@link Collection} with all items (currently) available in this widget. In
   * case of lazy loading only those that have already been loaded. <br/>
   * <b>ATTENTION:</b><br/>
   * The {@link Collection} must NOT be modified if retrieved via this method. Implementations will provide
   * separate <code>addItem</code> and <code>removeItem</code> methods.
   *
   * @return the {@link Collection} with all items (currently) available in this widget. In case of lazy
   *         loading only those that have already been loaded.
   */
  public abstract Collection<ITEM_CONTAINER> getAllAvailableItems();

  /**
   * @return the {@link SelectionChoice#FIRST first} of the {@link #getAllAvailableItems() available items}.
   */
  protected ITEM_CONTAINER getFirstAvailableItem() {

    Collection<ITEM_CONTAINER> items = getAllAvailableItems();
    if (items.isEmpty()) {
      return null;
    } else {
      return items.iterator().next();
    }
  }

  /**
   * @return the {@link SelectionChoice#LAST last} of the {@link #getAllAvailableItems() available items}.
   */
  protected abstract ITEM_CONTAINER getLastAvailableItem();

  /**
   * Gets the existing container for the given <code>item</code>. This method has to be very efficient. You
   * should use a {@link java.util.Map} for this purpose.
   *
   * @param item is the item to lookup.
   * @return the container for the given <code>item</code> or <code>null</code> if no such container exists.
   */
  protected abstract ITEM_CONTAINER getItemContainer(ITEM item);

  /**
   * This method gets the item in the given <code>container.</code>
   *
   * @param container is the container of the requested item.
   * @return the unwrapped item.
   */
  protected abstract ITEM getItem(ITEM_CONTAINER container);

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureSelectedValue<ITEM> asFeatureSelectedValue() {

    return this;
  }

}
