/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.bean;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.lang.api.Id;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for an {@link EntityBean} used with OrientDB. Simply create a sub-interface of this
 * {@link OrientBean} interface for each {@link com.orientechnologies.orient.core.metadata.schema.OClass OrientDB class}
 * you want to map. Within the interface define a {@link WritableProperty property}-method for each
 * {@link com.orientechnologies.orient.core.metadata.schema.OProperty OrientDB property} you want to access in a
 * type-safe way. You can also access all properties via {@link BeanAccess#getProperty(String)}. <br/>
 * <b>NOTE:</b><br/>
 * Never implement an {@link OrientBean} interface. See {@link Bean} and {@code OrientBeanRepository.newEntity()} for
 * further details.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientBean extends EntityBean {

  /** {@link BeanAccess#getPropertyNameForAlias(String) alias} for {@link #Id()}. */
  String PROPERTY_ALIAS_ID = "@rid";

  /** {@link BeanAccess#getPropertyNameForAlias(String) alias} for {@link #Version()}. */
  String PROPERTY_ALIAS_VERSION = "@version";

  @Named(PROPERTY_ALIAS_ID)
  @Override
  WritableProperty<Id<?>> Id();

  @Named(PROPERTY_ALIAS_VERSION)
  @Override
  int getVersion();

}
