/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import com.orientechnologies.orient.core.metadata.schema.OProperty;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the abstract interface used to {@link #build(OProperty, OrientBean) build} missing properties.
 *
 * @see SinglePropertyBuilder
 *
 * @author hohwille
 * @since 1.0.0
 */
public abstract interface AbstractPropertyBuilder {

  /**
   * Ensures that the given {@link OProperty} is available in the the given {@link OrientBean}
   * {@link BeanPrototypeBuilder#createPrototype(Class, String, net.sf.mmm.util.bean.api.Bean...) prototype}.
   *
   * @see BeanAccess#getOrCreateProperty(String, net.sf.mmm.util.reflect.api.GenericType, Class)
   *
   * @param oProperty the {@link OProperty}.
   * @param prototype the {@link OrientBean}
   *        {@link BeanPrototypeBuilder#createPrototype(Class, String, net.sf.mmm.util.bean.api.Bean...) prototype}.
   * @return the {@link WritableProperty property} corresponding to the given {@link OProperty} that has been created or
   *         was already existing. May be {@code null} if the {@link OProperty#getType() property type} is not supported
   *         (e.g. if new version of OrientDB introduces new property type that has not yet been implemented here).
   */
  WritableProperty<?> build(OProperty oProperty, OrientBean prototype);

}