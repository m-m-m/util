/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataEntityView entity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityView.CLASS_ID, title = DataEntityView.CLASS_TITLE)
public interface DataEntity extends DataEntityView, DataObject {

  /**
   * This method sets the {@link #getProxyTarget() proxy target}.
   * 
   * @param proxyTarget is the {@link DataEntity} this entity will delegate to.
   */
  void setProxyTarget(DataEntity proxyTarget);

}
