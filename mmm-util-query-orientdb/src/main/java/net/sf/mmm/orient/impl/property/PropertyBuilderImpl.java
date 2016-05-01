/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link PropertyBuilder}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class PropertyBuilderImpl extends AbstractLoggableComponent implements PropertyBuilder {

  private final Map<OType, SinglePropertyBuilder<?>> type2builderMap;

  private final Map<Class<?>, SinglePropertyBuilder<?>> class2builderMap;

  private OrientBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public PropertyBuilderImpl() {
    super();
    this.type2builderMap = new HashMap<>();
    this.class2builderMap = new HashMap<>();
  }

  /**
   * @return the {@link OrientBeanMapper}.
   */
  protected OrientBeanMapper getBeanMapper() {

    return this.beanMapper;
  }

  /**
   * @param beanMapper is the OrientBeanMapper to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(OrientBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  /**
   * @param builders the {@link List} of {@link SinglePropertyBuilder}s to {@link Inject}.
   */
  @Inject
  public void setBuilders(List<SinglePropertyBuilder<?>> builders) {

    for (SinglePropertyBuilder<?> builder : builders) {
      registerBuilder(builder);
    }
  }

  /**
   * @param builder the {@link SinglePropertyBuilder} to register.
   */
  public void registerBuilder(SinglePropertyBuilder<?> builder) {

    registerBuilder(builder, false);
  }

  /**
   * @param builder the {@link SinglePropertyBuilder} to register.
   * @param allowOverride - {@code true} if the given {@link SinglePropertyBuilder} may override (replace) a previously
   *        {@link #registerBuilder(SinglePropertyBuilder, boolean) registered} one.
   */
  protected void registerBuilder(SinglePropertyBuilder<?> builder, boolean allowOverride) {

    getInitializationState().requireNotInitilized();
    if (builder instanceof SinglePropertyBuilderLinkBase) {
      if (this.beanMapper == null) {
        throw new ResourceMissingException("beanMapper");
      }
      ((SinglePropertyBuilderLinkBase<?>) builder).setBeanMapper(this.beanMapper);
    }
    if (builder instanceof AbstractComponent) {
      ((AbstractComponent) builder).initialize();
    }
    OType type = builder.getType();
    Objects.requireNonNull(type, "type");
    SinglePropertyBuilder<?> old = this.type2builderMap.put(type, builder);
    if ((old != null) && !allowOverride) {
      throw new DuplicateObjectException(builder, type, old);
    }
    Class<?> valueClass = builder.getValueClass();
    Objects.requireNonNull(valueClass, "valueClass");
    old = this.class2builderMap.put(valueClass, builder);
    if ((old != null) && !allowOverride) {
      throw new DuplicateObjectException(builder, type, old);
    }
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.type2builderMap.isEmpty()) {
      registerDefaults();
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    for (OType type : OType.values()) {
      if (this.type2builderMap.get(type) == null) {
        getLogger().warn(
            "Unsupported OrientDB property type {}. Please register corresponding SinglePropertyBuilder.", type);
      }
    }
  }

  /**
   * {@link #registerBuilder(SinglePropertyBuilder) Registers} the {@link SinglePropertyBuilder factories} for the
   * common default types.
   */
  protected void registerDefaults() {

    registerBuilder(new SinglePropertyBuilderString());
    registerBuilder(new SinglePropertyBuilderDouble());
    registerBuilder(new SinglePropertyBuilderFloat());
    registerBuilder(new SinglePropertyBuilderLong());
    registerBuilder(new SinglePropertyBuilderInteger());
    registerBuilder(new SinglePropertyBuilderShort());
    registerBuilder(new SinglePropertyBuilderByte());
    registerBuilder(new SinglePropertyBuilderAny());

    registerBuilder(new SinglePropertyBuilderLink());
    registerBuilder(new SinglePropertyBuilderLinkList());
  }

  @Override
  public SinglePropertyBuilder<?> getBuilder(OType type) {

    return this.type2builderMap.get(type);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> SinglePropertyBuilder<? super V> getBuilder(Class<V> type) {

    return (SinglePropertyBuilder<? super V>) this.class2builderMap.get(type);
  }

  @Override
  public WritableProperty<?> build(OProperty oProperty, OrientBean prototype) {

    OType type = oProperty.getType();
    SinglePropertyBuilder<?> builder = getBuilder(type);
    if (builder == null) {
      getLogger().debug("Ignoring property {} due to unsupported type {}.", oProperty.getFullName(), type);
      return null;
    }
    return builder.build(oProperty, prototype);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V> OProperty build(WritableProperty<V> property, OClass oClass) {

    GenericType<? extends V> type = property.getType();
    SinglePropertyBuilder<V> builder = (SinglePropertyBuilder<V>) getBuilder(type.getRetrievalClass());
    if (builder == null) {
      getLogger().debug("Ignoring property {} due to unsupported type {}.", property.getName(), type);
      return null;
    }
    return builder.build(property, oClass);
  }

}
