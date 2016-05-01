/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.bean;

import javax.inject.Named;

/**
 * This is the interface for a vertex. It represents the predefined
 * {@link com.orientechnologies.orient.core.metadata.schema.OClass OrientDB Class} called {@code V}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named(Vertex.NAME)
public interface Vertex extends OrientBean {

  /**
   * The {@link net.sf.mmm.util.bean.api.BeanAccess#getSimpleName() name} corresponding to
   * {@link com.orientechnologies.orient.core.metadata.schema.OClass#getName()}.
   */
  String NAME = "V";

}
