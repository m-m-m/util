/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.bean;

import javax.inject.Named;

import net.sf.mmm.util.property.api.link.LinkProperty;

/**
 * This is the interface for an edge. An edge connects two instances of {@link Vertex}. It represents the predefined
 * {@link com.orientechnologies.orient.core.metadata.schema.OClass OrientDB Class} called {@code E}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named(Edge.NAME)
public interface Edge extends OrientBean {

  /**
   * The {@link net.sf.mmm.util.bean.api.BeanAccess#getSimpleName() name} corresponding to
   * {@link com.orientechnologies.orient.core.metadata.schema.OClass#getName()}.
   */
  String NAME = "E";

  /**
   * @return the {@link LinkProperty} with the incoming link where this {@link Edge} is coming from.
   */
  LinkProperty<? extends OrientBean> In();

  /**
   * @return the {@link LinkProperty} with the outgoing link where this {@link Edge} is going to.
   */
  LinkProperty<? extends OrientBean> Out();

}
