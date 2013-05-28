/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import java.util.Collection;

import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderLimited;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorImpl;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.AbstractPojoPropertyAccessorGetMethod;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.AbstractPojoPropertyAccessorSetMethod;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.reflect.api.TypeNotFoundException;
import net.sf.mmm.util.reflect.base.SimpleGenericTypeLimited;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the {@link AbstractIncrementalGenerator incremental GWT generator} to generate implementations of
 * {@link PojoDescriptor} for a particular {@link net.sf.mmm.util.pojo.api.Pojo POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PojoDescriptorGenerator extends AbstractIncrementalGenerator {

  /**
   * The constructor.
   */
  public PojoDescriptorGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String createClassName(JClassType inputType) {

    return inputType.getName() + "_PojoDescriptorGwt";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(TypedProperty.class.getName());
    sourceComposerFactory.addImport(PojoDescriptorImpl.class.getName());
    sourceComposerFactory.addImport(SimpleGenericTypeLimited.class.getName());
    sourceComposerFactory.addImport(AbstractPojoDescriptorBuilderLimited.class.getName());
    sourceComposerFactory.addImport(AbstractPojoPropertyAccessorGetMethod.class.getName());
    sourceComposerFactory.addImport(AbstractPojoPropertyAccessorSetMethod.class.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.setSuperclass(PojoDescriptorImpl.class.getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    // get property descriptors for input type...
    Collection<? extends PojoPropertyDescriptor> propertyDescriptors = getPropertyDescriptors(inputType);

    // generate Constructor
    generateConstructor(sourceWriter, simpleName, inputType, propertyDescriptors);

    // generate "getPropertyValidator" method
    // generateMethodGetPropertyValidator(inputType, sourceWriter, propertyDescriptors);

  }

  /**
   * Generates the constructor of the {@link Class} to generate.
   * 
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the
   *        source code to.
   * @param simpleName is the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param propertyDescriptors is the {@link Collection} with the {@link PojoPropertyDescriptor}s.
   */
  private void generateConstructor(SourceWriter sourceWriter, String simpleName, JClassType inputType,
      Collection<? extends PojoPropertyDescriptor> propertyDescriptors) {

    generateSourcePublicMethodBlock(sourceWriter, simpleName);
    sourceWriter.print("super(new ");
    sourceWriter.print(SimpleGenericTypeLimited.class.getSimpleName());
    sourceWriter.print("(");
    sourceWriter.print(inputType.getName());
    sourceWriter.print(".class), ");
    sourceWriter.print(AbstractPojoDescriptorBuilderLimited.class.getSimpleName());
    sourceWriter.println(".getInstance());");

    for (PojoPropertyDescriptor propertyDescriptor : propertyDescriptors) {
      PojoPropertyAccessorNonArg getAccessor = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
      // getter available?
      if (getAccessor != null) {
        generateAccessorRegistrationStatement(sourceWriter, getAccessor);
      }
      PojoPropertyAccessorOneArg setAccessor = propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
      // setter available?
      if (setAccessor != null) {
        generateAccessorRegistrationStatement(sourceWriter, setAccessor);
      }
    }
    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * Generates the source-code of the statement to register a {@link PojoPropertyAccessor}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param accessor is the {@link PojoPropertyAccessor}.
   */
  private void generateAccessorRegistrationStatement(SourceWriter sourceWriter, PojoPropertyAccessor accessor) {

    sourceWriter.print("getOrCreatePropertyDescriptor(\"");
    sourceWriter.print(accessor.getName());
    sourceWriter.print("\").putAccessor(new ");
    if (accessor.getMode().isReading()) {
      sourceWriter.print(AbstractPojoPropertyAccessorGetMethod.class.getSimpleName());
    } else {
      sourceWriter.print(AbstractPojoPropertyAccessorSetMethod.class.getSimpleName());
    }
    sourceWriter.print("(\"");
    sourceWriter.print(accessor.getName());
    sourceWriter.print("\", new ");
    sourceWriter.print(SimpleGenericTypeLimited.class.getSimpleName());
    sourceWriter.print("(");
    sourceWriter.print(accessor.getPropertyClass().getName());
    sourceWriter.print(".class), ");
    sourceWriter.print(accessor.getDeclaringClass().getName());
    sourceWriter.println(".class) {");
    sourceWriter.indent();
    if (accessor.getMode().isReading()) {
      // getter
      sourceWriter.println("public Object invoke(Object pojo) {");
      sourceWriter.indent();
      sourceWriter.print("return ((");
      sourceWriter.print(accessor.getDeclaringClass().getName());
      sourceWriter.print(") pojo).");
      sourceWriter.print(accessor.getAccessibleObjectName());
      sourceWriter.println("();");
      sourceWriter.outdent();
      sourceWriter.println("}");
    } else {
      // setter
      sourceWriter.println("public Object invoke(Object pojo, Object value) {");
      sourceWriter.indent();
      sourceWriter.print("((");
      sourceWriter.print(accessor.getDeclaringClass().getName());
      sourceWriter.print(") pojo).");
      sourceWriter.print(accessor.getAccessibleObjectName());
      sourceWriter.print("((");
      sourceWriter.print(accessor.getPropertyClass().getName());
      sourceWriter.println(")value);");
      sourceWriter.println("return null;");
      sourceWriter.outdent();
      sourceWriter.println("}");
    }
    sourceWriter.outdent();
    sourceWriter.println("});");
  }

  /**
   * This method determines the property descriptors of the {@link net.sf.mmm.util.pojo.api.Pojo} identified
   * by <code>inputType</code>.
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @return the {@link Collection} with the {@link PojoPropertyDescriptor}s.
   */
  private Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors(JClassType inputType) {

    PojoDescriptorBuilder descriptorBuilder = PojoDescriptorBuilderFactoryImpl.getInstance()
        .createPublicMethodDescriptorBuilder();
    Class<?> inputClass;
    try {
      inputClass = Class.forName(inputType.getQualifiedSourceName());
    } catch (ClassNotFoundException e) {
      throw new TypeNotFoundException(inputType.getQualifiedSourceName());
    }
    PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(inputClass);
    Collection<? extends PojoPropertyDescriptor> propertyDescriptors = descriptor.getPropertyDescriptors();
    return propertyDescriptors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getVersionId() {

    return 2;
  }

}
