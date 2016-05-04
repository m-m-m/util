/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.dummy;

/**
 * This is the abstract base class for an entry transfer object.
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractEto<ID> {

  private ID id;

  /**
   * The constructor.
   */
  public AbstractEto() {

    super();
  }

  /**
   * @return the unique ID of this ETO.
   */
  public ID getId() {

    return this.id;
  }

  /**
   * @param id the new value of {@link #getId()}.
   */
  public void setId(ID id) {

    this.id = id;
  }

}
