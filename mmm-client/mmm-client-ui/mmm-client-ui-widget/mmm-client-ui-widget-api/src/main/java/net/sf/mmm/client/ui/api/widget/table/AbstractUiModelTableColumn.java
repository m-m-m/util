/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.Comparator;

import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base implementation of {@link UiModelTableColumn}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiModelTableColumn<ROW, CELL> implements UiModelTableColumn<ROW, CELL> {

  /** @see #getId() */
  private String id;

  /** @see #getTitle() */
  private String title;

  /**
   * The constructor.
   * 
   * @param id - see {@link #getId()}.
   * @param title - see {@link #getTitle()}.
   */
  public AbstractUiModelTableColumn(String id, String title) {

    super();
    this.id = id;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * @param title is the new value of {@link #getTitle()}.
   */
  protected void setTitle(String title) {

    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyAccessor<ROW, CELL> getPropertyAccessor() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Comparator<CELL> getSortComparator() {

    // TODO Auto-generated method stub
    return null;
  }

}
