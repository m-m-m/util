/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.reflection.access.DataFieldAccessor;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.BooleanEnum;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AnnotationFilter;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;

/**
 * This is an extension of {@link DataClassLoaderStAX} that also allows to load {@link DataClass}es from the
 * native Java implementations of entities.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataClassLoaderNative extends AbstractDataClassLoader {

  /** The XML tag name defining an entity by classname. */
  public static final String XML_TAG_ENTITY = "entity";

  /**
   * The XML attribute name for the {@link Class#getName() classname}of the entity.
   */
  public static final String XML_ATR_ENTITY_CLASS = "class";

  /** The XML tag name defining the entities by package. */
  public static final String XML_TAG_ENTITIES = "entities";

  /** The XML attribute name for the package containing the entities. */
  public static final String XML_ATR_ENTITIES_PACKAGE = "package";

  /**
   * @see #loadClassRecursive(Class, Context)
   */
  private PojoDescriptorBuilder methodDescriptorBuilder;

  /** @see #getRootEntity() */
  private Class<? extends DataObject> rootEntity;

  /**
   * A filter that only accepts types that are annotated as {@link DataClassAnnotation}.
   */
  private final Filter<Class<?>> entityFilter;

  /** The XML input factory used to create the parser. */
  private final XMLInputFactory factory;

  /** @see #setConfigurationResource(DataResource) */
  private DataResource configurationResource;

  /** @see #setClassResolver(ClassResolver) */
  private ClassResolver classResolver;

  /** @see #getStaxUtil() */
  private StaxUtil staxUtil;

  /**
   * The constructor.
   * 
   * @param dataReflectionService
   */
  public DataClassLoaderNative(AbstractMutableDataReflectionService dataReflectionService) {

    super(dataReflectionService);
    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.initialize();
    this.methodDescriptorBuilder = builder;
    this.rootEntity = AbstractDataObject.class;
    this.entityFilter = new AnnotationFilter(DataClassAnnotation.class);
    this.factory = XMLInputFactory.newInstance();
    this.classResolver = ClassResolver.CLASS_FOR_NAME_RESOLVER;
    this.configurationResource = new ClasspathResource(DataReflectionService.XML_MODEL_LOCATION);
  }

  /**
   * @return the staxUtil
   */
  public StaxUtil getStaxUtil() {

    if (this.staxUtil == null) {
      this.staxUtil = StaxUtilImpl.getInstance();
    }
    return this.staxUtil;
  }

  /**
   * @param staxUtil the staxUtil to set
   */
  @Resource
  public void setStaxUtil(StaxUtil staxUtil) {

    this.staxUtil = staxUtil;
  }

  /**
   * This method sets (overrides) the resource pointing to the configuration data.
   * 
   * @param configurationResource the configurationResource to set
   */
  public void setConfigurationResource(DataResource configurationResource) {

    this.configurationResource = configurationResource;
  }

  /**
   * @param classResolver the classResolver to set
   */
  public void setClassResolver(ClassResolver classResolver) {

    this.classResolver = classResolver;
  }

  /**
   * @return the rootEntity
   */
  public Class<? extends DataObject> getRootEntity() {

    return this.rootEntity;
  }

  /**
   * @param rootEntity the rootEntity to set
   */
  public void setRootEntity(Class<? extends DataObject> rootEntity) {

    this.rootEntity = rootEntity;
  }

  /**
   * This method loads the content-model configuration and parses it.
   * 
   * @param context is where to collect the configured classes.
   */
  public void parseConfiguration(Context context) {

    try {
      InputStream in = this.configurationResource.openStream();
      try {
        XMLStreamReader xmlReader = this.factory.createXMLStreamReader(in);
        int eventType = xmlReader.nextTag();
        assert (eventType == XMLStreamConstants.START_ELEMENT);
        assert (DataReflectionService.XML_TAG_ROOT.equals(xmlReader.getLocalName()));
        eventType = xmlReader.nextTag();
        while (eventType == XMLStreamConstants.START_ELEMENT) {
          parseConfiguration(xmlReader, context);
          eventType = xmlReader.nextTag();
        }
        xmlReader.close();
      } finally {
        in.close();
      }
    } catch (Exception e) {
      // TODO: exception handling!!!
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * This method parses a child-element of the root-element of the content-model configuration.
   * 
   * @param xmlReader is the reading pointing to the start of the child-element.
   * @param context is where to add the configured classes to.
   * @throws XMLStreamException if an error occurred while parsing the XML.
   */
  protected void parseConfiguration(XMLStreamReader xmlReader, Context context) throws XMLStreamException {

    try {
      ReflectionUtil reflectionUtil = getReflectionUtil();
      String tagName = xmlReader.getLocalName();
      if (XML_TAG_ENTITY.equals(tagName)) {
        String className = xmlReader.getAttributeValue(null, XML_ATR_ENTITY_CLASS);
        Class entityClass = this.classResolver.resolveClass(className);
        loadClassRecursive(entityClass, context);
      } else if (XML_TAG_ENTITIES.equals(tagName)) {
        String packageName = xmlReader.getAttributeValue(null, XML_ATR_ENTITIES_PACKAGE);
        Set<String> classNames = reflectionUtil.findClassNames(packageName, true);
        Set<Class<?>> entityClasses = reflectionUtil.loadClasses(classNames, this.entityFilter);
        for (Class entityClass : entityClasses) {
          loadClassRecursive(entityClass, context);
        }
      }
      getStaxUtil().skipOpenElement(xmlReader);
    } catch (Exception e) {
      // TODO: exception handling!!!
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataClass<? extends DataObject> loadClasses() {

    Context context = new Context();
    parseConfiguration(context);
    AbstractDataClass<? extends DataObject> rootClass = context.getDataClass(DataObject.CLASS_ID);
    if (rootClass == null) {
      // TODO: NLS + Details/Explain
      throw new IllegalArgumentException("Root-class NOT found!");
    }
    loadFieldsRecursive(rootClass);

    return rootClass;
  }

  /**
   * This method "loads" the {@link DataClass} of the entity represented by the given <code>javaClass</code>.
   * It will recursively create the super-classes accordingly.
   * 
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()}.
   * @param javaClass is the entity to load.
   * @param context is where the {@link DataClass}es are added that have already been created.
   * @return the {@link DataClass} for the given <code>javaClass</code>.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <CLASS extends DataObject> AbstractDataClass<CLASS> loadClassRecursive(Class<CLASS> javaClass, Context context) {

    DataClassAnnotation contentClassAnnotation = javaClass.getAnnotation(DataClassAnnotation.class);
    if (contentClassAnnotation == null) {
      // TODO: NLS
      throw new DataReflectionException("Illegal entity class: missing " + DataClassAnnotation.class.getName());
    }
    // id
    Long classId = Long.valueOf(contentClassAnnotation.id());
    // name
    String name = contentClassAnnotation.title();
    if ((name == null) || (name.length() == 0)) {
      name = javaClass.getSimpleName();
    }
    boolean createSuperClass = true;
    AbstractDataClass<CLASS> dataClass;
    AbstractDataClass<? extends DataObject> existingClass;
    if (classId.longValue() == DataId.OBJECT_ID_ILLEGAL) {
      existingClass = context.getDataClass(name);
      classId = null;
    } else {
      existingClass = context.getDataClass(classId.longValue());
    }
    if (existingClass == null) {
      // the class has NOT yet been loaded - create it
      int modifiers = javaClass.getModifiers();
      boolean isSystem = false;
      boolean isFinal = Modifier.isFinal(modifiers);
      boolean isAbstract = Modifier.isAbstract(modifiers);
      boolean isExtendable = !isFinal;
      if (isSystem) {
        Boolean extendable = contentClassAnnotation.isExtendable().getValue();
        if (extendable != null) {
          isExtendable = extendable.booleanValue();
        }
      }
      DataClassModifiers contentClassModifiers = DataClassModifiersBean.getInstance(isSystem, isFinal, isAbstract,
          isExtendable);
      boolean deleted = javaClass.isAnnotationPresent(Deprecated.class);
      dataClass = getDataReflectionService().createDataClass(classId, name, null, contentClassModifiers, javaClass,
          deleted);
      context.put(dataClass);
    } else {
      // the class has already been loaded: check!
      if (classId != null) {
        if (!classId.equals(existingClass.getId())) {
          throw new ObjectMismatchException(classId, existingClass.getId(), existingClass, DataObject.FIELD_NAME_ID);
        }
      }
      Class<? extends DataObject> type = existingClass.getJavaClass();
      if ((type != null) && (javaClass != type)) {
        throw new ObjectMismatchException(javaClass, type, existingClass, DataClass.FIELD_NAME_JAVA_CLASS);
      }
      dataClass = (AbstractDataClass<CLASS>) existingClass;
      if (dataClass.getJavaClass() == null) {
        dataClass.setJavaClass(javaClass);
      }
      if (dataClass.getSuperClass() != null) {
        // this can only happen if the class was created outside (in a subclass)
        createSuperClass = false;
      }
      // TODO
    }

    if (createSuperClass) {
      // root-class ?
      if (!this.rootEntity.equals(javaClass)) {
        // find super-class that is entity
        Class superClass = javaClass.getSuperclass();
        while (!superClass.isAnnotationPresent(DataClassAnnotation.class)) {
          superClass = superClass.getSuperclass();
          if (superClass == null) {
            // error in class hierarchy
            throw new ObjectMismatchException(Object.class, this.rootEntity, javaClass, "superClass");
          }
        }
        AbstractDataClass superContentClass = loadClassRecursive(superClass, context);
        dataClass.setSuperClass(superContentClass);
        superContentClass.addSubClass(dataClass);
      }
    }
    return dataClass;
  }

  /**
   * This method "loads" the {@link DataField field}s of the given <code>contentClass</code>. It acts
   * recursive on all sub-classes and therefore loads the fields for the complete class-tree starting from the
   * given <code>contentClass</code>.
   * 
   * @param contentClass is the class for which the fields should be loaded.
   */
  protected void loadFieldsRecursive(AbstractDataClass<? extends DataObject> contentClass) {

    Class<? extends DataObject> type = contentClass.getJavaClass();
    AbstractDataClass<? extends DataObject> superClass = contentClass.getSuperClass();
    PojoDescriptor<? extends DataObject> methodPojoDescriptor;
    if (type == null) {
      throw new IllegalStateException("TODO");
    }
    methodPojoDescriptor = this.methodDescriptorBuilder.getDescriptor(type);
    for (PojoPropertyDescriptor methodPropertyDescriptor : methodPojoDescriptor.getPropertyDescriptors()) {
      PojoPropertyAccessor accessor = methodPropertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
      try {
        boolean declareField = true;
        DataFieldAnnotation contentFieldAnnotation = null;
        if (accessor == null) {
          // no getter: ignore
          declareField = false;
        } else {
          Method getter = (Method) accessor.getAccessibleObject();
          contentFieldAnnotation = getter.getAnnotation(DataFieldAnnotation.class);
          if (contentFieldAnnotation == null) {
            // getter NOT properly annotated: ignore
            declareField = false;
          } else if (superClass != null) {
            Class<?> declaringClass = accessor.getDeclaringClass();
            Class<? extends DataObject> superType = superClass.getJavaClass();
            if (!superType.isAssignableFrom(declaringClass) || (superType.equals(declaringClass))) {
              // getter inherited from super entity: ignore
              declareField = false;
            }
          }
        }
        if (declareField) {
          long fieldId = contentFieldAnnotation.id();
          String name = contentFieldAnnotation.title();
          if ((name == null) || (name.length() == 0)) {
            name = accessor.getName();
          }
          if (fieldId == DataId.OBJECT_ID_ILLEGAL) {

          } else {

          }
          int modifiers = accessor.getModifiers();
          boolean isSystem = contentClass.getModifiers().isSystem();
          if (isSystem && fieldId >= DataId.OBJECT_ID_MINIMUM_CUSTOM) {
            // TODO
            throw new NlsIllegalArgumentException("");
          }
          boolean isFinal = Modifier.isFinal(modifiers);

          boolean isStatic = Modifier.isStatic(modifiers);
          switch (contentFieldAnnotation.isStatic()) {
            case TRUE:
              isStatic = true;
              break;
            case FALSE:
              if (isStatic) {
                throw new NlsIllegalArgumentException("TODO: static field (" + name + ") has to be static!");
              }
              break;
            default :
              // nothing to do for NULL
          }
          boolean isTransient = contentFieldAnnotation.isTransient().isTrue();
          boolean isInherited = contentFieldAnnotation.isInheritedFromParent();
          boolean isReadOnly = true;
          PojoPropertyAccessor writeAccessor = methodPropertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
          if ((writeAccessor != null) && (Modifier.isPublic(writeAccessor.getModifiers()))) {
            isReadOnly = false;
          }
          if (contentFieldAnnotation.isReadOnly() != BooleanEnum.NULL) {
            if (contentFieldAnnotation.isReadOnly().isTrue()) {
              isReadOnly = true;
            } else {
              if (!isReadOnly) {

              }
            }
          }
          DataFieldModifiers contentFieldModifiers = DataFieldModifiersBean.getInstance(isSystem, isFinal, isReadOnly,
              isStatic, isTransient, isInherited);
          boolean isDeleted = accessor.getAccessibleObject().isAnnotationPresent(Deprecated.class);
          GenericType<?> fieldType = accessor.getPropertyType();
          // Class<?> fieldClass = accessor.getPropertyClass();
          // if (fieldClass.isPrimitive()) {
          // fieldType = getReflectionUtil().getNonPrimitiveType(fieldClass);
          // }
          AbstractDataField contentField = getDataReflectionService().createDataField(fieldId, name, contentClass,
              fieldType, contentFieldModifiers, isDeleted);
          contentField.setAccessor(getFieldAccessor(methodPropertyDescriptor));
          contentClass.addDeclaredField(contentField);
        }
      } catch (Exception e) {
        throw new DataReflectionException(e, "Error loading field '" + accessor.getName() + "' of class "
            + contentClass);
      }
    }
    // recursive invocation
    for (AbstractDataClass subClass : contentClass.getSubClasses()) {
      loadFieldsRecursive(subClass);
    }
  }

  /**
   * This method gets (creates) the {@link DataFieldAccessor} for the field given by
   * <code>methodPropertyDescriptor</code>.
   * 
   * @param propertyDescriptor is the {@link PojoPropertyDescriptor} of the field.
   * @return the field accessor.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected DataFieldAccessor<? extends DataObject, ?> getFieldAccessor(PojoPropertyDescriptor propertyDescriptor) {

    PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
    PojoPropertyAccessorOneArg setter = propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
    return new DataFieldAccessorPojo(getter.getPropertyClass(), getter, setter);
  }

}
