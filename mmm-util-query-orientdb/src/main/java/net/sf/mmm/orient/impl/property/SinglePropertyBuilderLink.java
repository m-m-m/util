/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#LINK}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
@Named
public class SinglePropertyBuilderLink extends SinglePropertyBuilderLinkBase<Link/* <? extends OrientBean> */> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderLink() {
    super();
  }

  @Override
  public OType getType() {

    return OType.LINK;
  }

  @Override
  public Class<Link> getValueClass() {

    return Link.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends WritableProperty<Link>> getPropertyType(OProperty oProperty) {

    return (Class) LinkProperty.class;
  }

  @Override
  protected GenericType getValueType(Class<? extends OrientBean> beanClass) {

    return LinkProperty.createLinkType(beanClass);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected OClass getLinkedClass(WritableProperty<Link> property) {

    GenericType<? extends Link> type = property.getType();
    assert (type.getRetrievalClass() == Link.class);
    int typeCount = type.getTypeArgumentCount();
    if (typeCount == 1) {
      GenericType<?> linkedType = type.getTypeArgument(0);
      return getBeanMapper().getOClass((Class) linkedType.getRetrievalClass());
    }
    return null;
  }

}
