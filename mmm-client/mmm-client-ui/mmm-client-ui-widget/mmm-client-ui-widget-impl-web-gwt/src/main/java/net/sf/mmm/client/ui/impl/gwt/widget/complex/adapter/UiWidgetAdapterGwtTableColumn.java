/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.common.Size;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.client.ui.gwt.widgets.CssDivWidget;
import net.sf.mmm.client.ui.gwt.widgets.CssIconWidget;
import net.sf.mmm.client.ui.gwt.widgets.TableCellAtomic;
import net.sf.mmm.client.ui.gwt.widgets.TableCellHeaderAtomic;
import net.sf.mmm.client.ui.gwt.widgets.handler.AbstractDragAndDropHandler;
import net.sf.mmm.client.ui.gwt.widgets.handler.AbstractMouseDragHandler;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;
import net.sf.mmm.util.lang.api.SortOrder;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterTableColumn} using GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTableColumn extends UiWidgetAdapterGwtWidget<TableCellHeaderAtomic> implements
    UiWidgetAdapterTableColumn {

  /** Required for layout due to HTML obstracles. */
  private FlowPanel divContainer;

  /** @see #getHeaderLabel() */
  private Label headerLabel;

  /** @see #setSortOrder(SortOrder) */
  private CssIconWidget sortIcon;

  /** @see #setResizable(boolean) */
  private CssDivWidget resizeWidget;

  /** @see #getBodyWidthCell() */
  private final TableCellAtomic bodyWidthCell;

  /** @see #getDataTableAdapter() */
  private final UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter;

  /** @see #setResizable(boolean) */
  private boolean resizable;

  /** @see #setReorderable(boolean) */
  private boolean reorderable;

  /** @see #getEditWidget() */
  private UiWidgetWithValue<?> editWidget;

  /**
   * The constructor.
   *
   * @param dataTableAdapter is the {@link #getDataTableAdapter() data table adapter}.
   */
  public UiWidgetAdapterGwtTableColumn(UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter) {

    this(dataTableAdapter, null);
  }

  /**
   * The constructor.
   *
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   * @param dataTableAdapter is the {@link #getDataTableAdapter() data table adapter}.
   */
  public UiWidgetAdapterGwtTableColumn(UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter,
      TableCellHeaderAtomic toplevelWidget) {

    super(toplevelWidget);
    this.dataTableAdapter = dataTableAdapter;
    this.bodyWidthCell = new TableCellAtomic();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TableCellHeaderAtomic createToplevelWidget() {

    final TableCellHeaderAtomic tableCellHeader = new TableCellHeaderAtomic();
    this.divContainer = new FlowPanel();
    this.divContainer.setStyleName(CssStyles.TABLE_HEADER);
    tableCellHeader.setChild(this.divContainer);
    this.headerLabel = new Label();
    this.headerLabel.setStyleName(CssStyles.LABEL);
    this.divContainer.add(this.headerLabel);
    this.sortIcon = new CssIconWidget(CssStyles.SORT_ICON_NONE);
    this.divContainer.add(this.sortIcon);
    this.resizeWidget = new CssDivWidget(CssStyles.COLUMN_RESIZER);

    ResizeHandler resizeHandler = new ResizeHandler();
    resizeHandler.register(this.resizeWidget, this);

    ReorderHandler reorderHandler = new ReorderHandler();
    reorderHandler.register(tableCellHeader, this);

    tableCellHeader.sinkEvents(Event.ONCLICK);

    ClickHandler handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        int relativeX = event.getRelativeX(UiWidgetAdapterGwtTableColumn.this.resizeWidget.getElement());
        if (relativeX >= 0) {
          // ignore click event caused from resizing...
          return;
        }
        UiWidgetTableColumn<?, ?> tableColumn = getUiWidgetTyped();
        if (tableColumn.isSortable()) {
          tableColumn.sort();
        }
      }

    };
    this.divContainer.add(this.resizeWidget);
    tableCellHeader.addDomHandler(handler, ClickEvent.getType());
    return tableCellHeader;
  }

  /**
   * @return the bodyWidthCell
   */
  public TableCellAtomic getBodyWidthCell() {

    return this.bodyWidthCell;
  }

  /**
   * @return the headerLabel
   */
  public Label getHeaderLabel() {

    return this.headerLabel;
  }

  /**
   * @return the dataTableAdapter
   */
  public UiWidgetAdapterGwtAbstractDataTable<?> getDataTableAdapter() {

    return this.dataTableAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSortOrder(SortOrder sortOrder) {

    String style;
    if (sortOrder == null) {
      style = CssStyles.SORT_ICON_NONE;
    } else if (sortOrder == SortOrder.ASCENDING) {
      style = CssStyles.SORT_ICON_ASCENDING;
    } else {
      style = CssStyles.SORT_ICON_DESCENDING;
    }
    this.sortIcon.setStyleName(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.headerLabel.setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    this.resizable = resizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReorderable() {

    return this.reorderable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReorderable(boolean reorderable) {

    this.reorderable = reorderable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSortable() {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSortable(boolean sortable) {

    this.sortIcon.setVisible(sortable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getLength(LengthProperty property) {

    if (property == LengthProperty.MAX_WIDTH) {
      // actual length comes from UiWidget, if we end up here, it is not set, see setLength...
      return Length.MAX;
    } else {
      return super.getLength(property);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLength(LengthProperty property, Length length) {

    if (property == LengthProperty.MAX_WIDTH) {
      // workaround: for correct overflow we must not set max with in DOM/CSS
      return;
    }
    super.setLength(property, length);
    if (property == LengthProperty.WIDTH) {
      super.setLength(LengthProperty.MAX_WIDTH, length);
      this.headerLabel.setWidth(length.toString());
      String bodyColumnWidth;
      if (length.getUnit() == LengthUnit.PIXEL) {
        bodyColumnWidth = length.getValue();
      } else {
        bodyColumnWidth = getWidthInPixel() + LengthUnit.PIXEL.getValue();
      }
      this.bodyWidthCell.setWidth(bodyColumnWidth);
    }
  }

  /**
   * @return the {@link UiWidgetWithValue} used for inline-editing and as template to create the view widgets
   *         in the cells of this column.
   */
  public UiWidgetWithValue<?> getEditWidget() {

    return this.editWidget;
  }

  /**
   * @param editWidget is the editWidget to set
   */
  public void setEditWidget(UiWidgetWithValue<?> editWidget) {

    this.editWidget = editWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractUiWidgetTableColumn<?, ?, ?> getUiWidgetTyped() {

    return (AbstractUiWidgetTableColumn<?, ?, ?>) getUiWidget();
  }

  /**
   * Handler for the reorder behavior of this column.
   */
  private class ReorderHandler extends AbstractDragAndDropHandler {

    /** The data format used to store the drag information in DND events. */
    private static final String DATA_FORMAT = "text/plain";

    /**
     * The constructor.
     */
    public ReorderHandler() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActive() {

      if (!UiWidgetAdapterGwtTableColumn.this.reorderable) {
        return false;
      }
      return super.isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrop(DropEvent event) {

      super.onDrop(event);
      String dragId = event.getData(DATA_FORMAT);
      AbstractUiWidgetAbstractDataTable<?, ?, ?, ?> dataTable = getUiWidgetTyped().getDataTable();
      dataTable.dragColumn(dragId, getUiWidget().getId());
      // UiWidgetTableColumn<?, ?> draggedColumn = dataTable.getColumnById(dragId);
      // Log.info("Dropping " + draggedColumn.getId() + " to " +
      // GwtUtil.getInstance().getId(getToplevelWidget()));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDragOver(DragOverEvent event) {

      AbstractUiWidgetTableColumn<?, ?, ?> dragOverColumn = getUiWidgetTyped();
      AbstractUiWidgetAbstractDataTable<?, ?, ?, ?> dataTable = dragOverColumn.getDataTable();
      if (dragOverColumn.isReorderable()) {
        String dragId = event.getData(DATA_FORMAT);
        AbstractUiWidgetTableColumn<?, ?, ?> draggedColumn = dataTable.getColumnById(dragId, false);
        if (draggedColumn == dragOverColumn) {
          dragOverColumn = null;
        }
        super.onDragOver(event);
      } else {
        dragOverColumn = null;
      }
      AbstractUiWidgetTableColumn<?, ?, ?> previousDragOverColumn = dataTable.setDragOverColumn(dragOverColumn);
      if (previousDragOverColumn != dragOverColumn) {
        updateDragOver(previousDragOverColumn, false);
        updateDragOver(dragOverColumn, true);
      }
    }

    /**
     * Updates the style of the given <code>dragOverColumn</code>.
     *
     * @param dragOverColumn is the {@link AbstractUiWidgetTableColumn} that is dragged over and shall be
     *        updated.
     * @param addStyle - <code>true</code> to add drag over style, <code>false</code> to remove.
     */
    private void updateDragOver(AbstractUiWidgetTableColumn<?, ?, ?> dragOverColumn, boolean addStyle) {

      if (dragOverColumn != null) {
        Widget headerWidget = getToplevelWidget(dragOverColumn);
        if (addStyle) {
          headerWidget.addStyleName(CssStyles.DRAG_OVER);
        } else {
          headerWidget.removeStyleName(CssStyles.DRAG_OVER);
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDragEnd(DragEndEvent event) {

      AbstractUiWidgetAbstractDataTable<?, ?, ?, ?> dataTable = getUiWidgetTyped().getDataTable();
      AbstractUiWidgetTableColumn<?, ?, ?> previousDragOverColumn = dataTable.setDragOverColumn(null);
      updateDragOver(previousDragOverColumn, false);
    }
  }

  /**
   * Handler for the resize behavior of this column.
   */
  private class ResizeHandler extends AbstractMouseDragHandler {

    /** The width range of this column. */
    private ColumnWidthRange widthRange;

    /** @see #initializeOnMouseDown(MouseDownEvent) */
    private AbstractUiWidgetTableColumn<?, ?, ?> nextColumn;

    /** @see #initializeOnMouseDown(MouseDownEvent) */
    private boolean nextInitialized;

    /** The width range of the next column. */
    private ColumnWidthRange nextWidthRange;

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isActive() {

      if (!UiWidgetAdapterGwtTableColumn.this.resizable) {
        return true;
      }
      return super.isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeOnMouseDown(MouseDownEvent event) {

      super.initializeOnMouseDown(event);
      this.widthRange = new ColumnWidthRange(getUiWidget());
      this.nextInitialized = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMouseMove(int deltaX, int deltaY, NativeEvent nativeEvent) {

      int newDeltaX = this.widthRange.clip(deltaX);
      // double newWidth = clipWidth(calculatedWidth, getUiWidget());

      if (nativeEvent.getShiftKey()) {
        if (!this.nextInitialized) {
          this.nextColumn = determineNextColumn();
          this.nextInitialized = true;
        }
        if ((this.nextWidthRange == null) && (this.nextColumn != null)) {
          this.nextWidthRange = new ColumnWidthRange(this.nextColumn);
        }
        if (this.nextWidthRange != null) {
          newDeltaX = this.nextWidthRange.clip(newDeltaX);
          this.nextColumn.getSize().setWidthInPixel(this.nextWidthRange.initialWidth - newDeltaX);
        }
      } else {
        this.nextWidthRange = null;
      }
      getUiWidget().getSize().setWidthInPixel(this.widthRange.initialWidth + newDeltaX);
    }

    /**
     * @return determines the column next to this on the right.
     */
    private AbstractUiWidgetTableColumn<?, ?, ?> determineNextColumn() {

      int nextColumnIndex = getUiWidgetTyped().getCurrentIndex() + 1;
      for (AbstractUiWidgetTableColumn<?, ?, ?> column : UiWidgetAdapterGwtTableColumn.this.dataTableAdapter
          .getColumns()) {
        int currentIndex = column.getCurrentIndex();
        if (currentIndex == nextColumnIndex) {
          return column;
        }
      }
      // there is no next column, return previous one...
      return null;
    }
  }

  /**
   * Container for the range of the width of a column.
   */
  protected static class ColumnWidthRange {

    /** The minimum value allowed for deltaX (typically negative). */
    private final int minDx;

    /** The maximum value allowed for deltaX (typically positive). */
    private final int maxDx;

    /** The initial width of the column. */
    private final double initialWidth;

    /**
     * The constructor.
     *
     * @param widget is the actual column widget.
     */
    public ColumnWidthRange(UiWidget widget) {

      super();
      Size size = widget.getSize();
      this.initialWidth = size.getWidthInPixel() - 1;
      double minWidth = LengthProperty.MIN_WIDTH.getLengthInPixel(size, size);
      this.minDx = (int) (minWidth - this.initialWidth); // typically negative...
      double maxWidth = LengthProperty.MAX_WIDTH.getLengthInPixel(size, size);
      if (maxWidth == Double.MAX_VALUE) {
        this.maxDx = Integer.MAX_VALUE;
      } else {
        this.maxDx = (int) (maxWidth - this.initialWidth);
      }
    }

    /**
     * @return the minDx
     */
    public int getMinDx() {

      return this.minDx;
    }

    /**
     * @return the maxDx
     */
    public int getMaxDx() {

      return this.maxDx;
    }

    /**
     * @return the initialWidth
     */
    public double getInitialWidth() {

      return this.initialWidth;
    }

    /**
     * Clips the given <code>deltaX</code> value according to {@link #getMinDx() minDx} and
     * {@link #getMaxDx() maxDx}.
     *
     * @param deltaX is the current <code>deltaX</code> to clip.
     * @return the given <code>deltaX</code> value if within the given range, otherwise if it violates the
     *         {@link #getMinDx() minDx} or {@link #getMaxDx() maxDx} constraint, {@link #getMinDx() minDx} or
     *         {@link #getMaxDx() maxDx} value is returned instead.
     */
    public int clip(int deltaX) {

      if (deltaX < this.minDx) {
        return this.minDx;
      } else if (deltaX > this.maxDx) {
        return this.maxDx;
      } else {
        return deltaX;
      }
    }

  }

}
