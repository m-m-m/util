/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import net.sf.mmm.util.lang.api.Id;

/**
 * Base implementation of a JPA {@link Entity}.
 *
 * @author hohwille
 */
@MappedSuperclass
public class AbstractEntity implements net.sf.mmm.util.bean.api.Entity {

  private transient Id<?> id;

  @javax.persistence.Id
  @GeneratedValue
  @Column(name = "id")
  private Long primaryKey;

  @Version
  private int version;

  /**
   * @return the primaryKey
   */
  public Long getPrimaryKey() {

    return this.primaryKey;
  }

  /**
   * @param primaryKey is the primaryKey to set
   */
  public void setPrimaryKey(Long primaryKey) {

    this.primaryKey = primaryKey;
  }

  @Override
  public Id<?> getId() {

    return this.id;
  }

  @Override
  public void setId(Id<?> id) {

    if (id == null) {
      this.primaryKey = null;
    } else {
      assert (id.getType() == getClass());
      this.primaryKey = Long.valueOf(id.getId());
    }
    this.id = id;
  }

  @Override
  public int getVersion() {

    return this.version;
  }

  @Override
  public void setVersion(int version) {

    this.version = version;
  }

}
