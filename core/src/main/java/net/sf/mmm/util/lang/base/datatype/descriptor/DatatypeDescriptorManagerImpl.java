/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.ConcurrentHashMapFactory;
import net.sf.mmm.util.component.api.NotInitializedException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is the implementation of {@link DatatypeDescriptorManager}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeDescriptorManagerImpl extends AbstractLoggableComponent implements DatatypeDescriptorManager {

  private static DatatypeDescriptorManagerImpl instance;

  /** Lazy filled {@link Map} for {@link DatatypeDescriptor}. */
  private final Map<Class<?>, DatatypeDescriptor<?>> datatypeDescriptorMap;

  private DatatypeDetector datatypeDetector;

  private ReflectionUtil reflectionUtil;

  private StringUtil stringUtil;

  /**
   * The constructor.
   */
  public DatatypeDescriptorManagerImpl() {

    this(ConcurrentHashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param mapFactory is the {@link MapFactory} used to create internal maps.
   */
  public DatatypeDescriptorManagerImpl(MapFactory<?> mapFactory) {

    super();
    this.datatypeDescriptorMap = mapFactory.createGeneric(64);
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(String.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Integer.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Long.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Double.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Float.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Boolean.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Character.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Short.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(Byte.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(BigDecimal.class));
    registerDatatypeDescriptor(new DatatypeDescriptorAtomicJavaDatatype<>(BigInteger.class));
  }

  /**
   * This method gets the singleton instance of this {@link DatatypeDescriptorManager}. <br>
   * Will only work after this class has been properly initialized (by an
   * {@link net.sf.mmm.util.component.api.IocContainer}). This method shall only be used for classes that can
   * not be managed by an {@link net.sf.mmm.util.component.api.IocContainer} e.g. for
   * {@link javax.xml.bind.annotation.adapters.XmlAdapter}s. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static DatatypeDescriptorManager getInstance() {

    if (instance == null) {
      // you have to initialize your IoC container first...
      throw new NotInitializedException();
    }
    return instance;
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    } else {
      getLogger().warn("Duplicate instantiation!");
    }
  }

  /**
   * @param datatypeDetector is the instance of {@link DatatypeDetector} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDetector(DatatypeDetector datatypeDetector) {

    getInitializationState().requireNotInitilized();
    this.datatypeDetector = datatypeDetector;
  }

  /**
   * @param reflectionUtil is the instance of {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @param stringUtil is the instance of {@link StringUtil} to {@link Inject}.
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * @param datatypeDescriptors is the {@link List} of {@link DatatypeDescriptor}s to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptors(List<DatatypeDescriptor<?>> datatypeDescriptors) {

    for (DatatypeDescriptor<?> descriptor : datatypeDescriptors) {
      registerDatatypeDescriptor(descriptor);
    }
  }

  /**
   * @param descriptor is the {@link DatatypeDescriptor} to register.
   */
  protected void registerDatatypeDescriptor(DatatypeDescriptor<?> descriptor) {

    Class<?> datatype = descriptor.getDatatype();
    DatatypeDescriptor<?> existing = this.datatypeDescriptorMap.get(datatype);
    if (existing == descriptor) {
      getLogger().info("Ignoring duplicate descriptor for {}", datatype);
    } else {
      if (existing != null) {
        throw new DuplicateObjectException(descriptor, datatype, existing);
      }
      this.datatypeDescriptorMap.put(descriptor.getDatatype(), descriptor);
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> DatatypeDescriptor<T> getDatatypeDescriptor(Class<T> inputDatatype) throws IllegalArgumentException {

    NlsNullPointerException.checkNotNull("datatype", inputDatatype);
    Class<T> datatype = this.reflectionUtil.getNonPrimitiveType(inputDatatype);
    if (Enum.class.isAssignableFrom(datatype) && !datatype.isEnum()) {
      // Jep! This can happen in case of an enum item that has a body
      // Java is sometimes insane
      datatype = (Class) datatype.getSuperclass();
    }
    DatatypeDescriptor<T> datatypeDescriptor = (DatatypeDescriptor<T>) this.datatypeDescriptorMap.get(datatype);
    if (datatypeDescriptor == null) {
      datatypeDescriptor = createDatatypeDescriptor(datatype);
      this.datatypeDescriptorMap.put(datatype, datatypeDescriptor);
    }
    return datatypeDescriptor;
  }

  /**
   * Creates a generic {@link DatatypeDescriptor} for the given {@link net.sf.mmm.util.lang.api.Datatype}
   * {@link Class} dynamically.
   *
   * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @param datatype is the {@link Class} reflecting the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @return the new {@link DatatypeDescriptor} instance.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <T> DatatypeDescriptor<T> createDatatypeDescriptor(Class<T> datatype) {

    if (datatype.isArray()) {
      throw new IllegalArgumentException("Array is no datatype: " + datatype);
    }
    if (datatype.isInterface()) {
      throw new IllegalArgumentException(
          "Interface is no datatype (unless you register your own DatatypeDetector): " + datatype);
    }
    if (SimpleDatatype.class.isAssignableFrom(datatype)) {
      return new DatatypeDescriptorSimpleDatatype(datatype, this.reflectionUtil);
    }
    if (datatype.isEnum()) {
      return new DatatypeDescriptorEnum(datatype, this.stringUtil);
    }
    if (Modifier.isAbstract(datatype.getModifiers())) {
      throw new IllegalArgumentException(
          "Abstract class is no datatype (unless you register your own DatatypeDetector): " + datatype);
    }
    if (this.datatypeDetector.isJavaStandardDatatype(datatype)) {
      // fallback...
      return new DatatypeDescriptorAtomicJavaDatatype<>(datatype);
    }
    throw new IllegalCaseException(datatype.getName());
  }
}
