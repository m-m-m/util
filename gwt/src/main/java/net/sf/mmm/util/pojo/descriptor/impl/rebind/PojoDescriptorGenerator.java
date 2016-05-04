/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderLimited;
import net.sf.mmm.util.pojo.descriptor.impl.AbstractPojoDescriptorImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoPropertyDescriptorImpl;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.AbstractPojoPropertyAccessorGetMethod;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.AbstractPojoPropertyAccessorSetMethod;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.TypeNotFoundException;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.reflect.base.SimpleGenericTypeLimited;

/**
 * This is the {@link net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator incremental GWT generator} to
 * generate implementations of {@link PojoDescriptor} for a particular {@link net.sf.mmm.util.pojo.api.Pojo POJO}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PojoDescriptorGenerator extends AbstractPojoDescriptorGenerator {

  /**
   * The constructor.
   */
  public PojoDescriptorGenerator() {

    super();
  }

  @Override
  protected String createClassName(JClassType inputType) {

    return inputType.getName() + "_PojoDescriptorGwt";
  }

  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(TypedProperty.class.getName());
    sourceComposerFactory.addImport(AbstractPojoDescriptorImpl.class.getName());
    sourceComposerFactory.addImport(SimpleGenericTypeLimited.class.getName());
    sourceComposerFactory.addImport(AbstractPojoDescriptorBuilderLimited.class.getName());
    sourceComposerFactory.addImport(AbstractPojoPropertyAccessorGetMethod.class.getName());
    sourceComposerFactory.addImport(AbstractPojoPropertyAccessorSetMethod.class.getName());
    sourceComposerFactory.addImport(PojoPropertyAccessorNonArgMode.class.getName());
    sourceComposerFactory.addImport(PojoPropertyAccessorOneArgMode.class.getName());
    sourceComposerFactory.addImport(PojoPropertyDescriptorImpl.class.getName());
  }

  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.setSuperclass(AbstractPojoDescriptorImpl.class.getSimpleName());
  }

  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    // get property descriptors for input type...
    PojoDescriptor<?> pojoDescriptor = getPojoDescriptor(inputType);

    // generate Constructor
    generateConstructor(sourceWriter, simpleName, inputType, pojoDescriptor, context);

    // generate "newInstance" method
    generateMethodNewInstance(inputType, sourceWriter, pojoDescriptor);

  }

  /**
   * Generates the method {@link AbstractPojoDescriptorImpl#newInstance()}.
   *
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link com.google.gwt.core.client.GWT#create(Class)}.
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the source code
   *        to.
   * @param pojoDescriptor is the {@link PojoDescriptor}.
   */
  private void generateMethodNewInstance(JClassType inputType, SourceWriter sourceWriter,
      PojoDescriptor<?> pojoDescriptor) {

    generateSourcePublicMethodDeclaration(sourceWriter, inputType.getQualifiedSourceName(), "newInstance", "",
        false);

    if (inputType.isInterface() != null) {
      sourceWriter.print("throw new ");
      sourceWriter.println(InstantiationFailedException.class.getName());
      sourceWriter.println("(getPojoClass());");
    } else {
      sourceWriter.print("return new ");
      sourceWriter.print(inputType.getQualifiedSourceName());
      sourceWriter.println("();");
    }

    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * Generates the constructor of the {@link Class} to generate.
   *
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the source code
   *        to.
   * @param simpleName is the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link com.google.gwt.core.client.GWT#create(Class)}.
   * @param pojoDescriptor is the {@link PojoDescriptor}.
   * @param context is the {@link GeneratorContext}.
   */
  protected void generateConstructor(SourceWriter sourceWriter, String simpleName, JClassType inputType,
      PojoDescriptor<?> pojoDescriptor, GeneratorContext context) {

    generateSourcePublicConstructorDeclaration(sourceWriter, simpleName);
    sourceWriter.print("super(new ");
    sourceWriter.print(SimpleGenericTypeLimited.class.getSimpleName());
    sourceWriter.print("(");
    sourceWriter.print(inputType.getName());
    sourceWriter.print(".class), ");
    sourceWriter.print(AbstractPojoDescriptorBuilderLimited.class.getSimpleName());
    sourceWriter.println(".getInstance());");

    // local variable for property descriptor
    sourceWriter.print(PojoPropertyDescriptorImpl.class.getSimpleName());
    sourceWriter.println(" propertyDescriptor;");

    JClassType superType = getConfiguration().getSupportedSuperType(inputType, context.getTypeOracle());
    StatefulPropertyGenerator state = new StatefulPropertyGenerator(sourceWriter, superType);

    for (PojoPropertyDescriptor propertyDescriptor : pojoDescriptor.getPropertyDescriptors()) {
      state.generatePropertyDescriptorBlock(propertyDescriptor);
    }
    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * This method determines the property descriptors of the {@link net.sf.mmm.util.pojo.api.Pojo} identified by
   * {@code inputType}.
   *
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link com.google.gwt.core.client.GWT#create(Class)}.
   * @return the {@link PojoDescriptor} for the given {@code inputType}.
   */
  private static PojoDescriptor<?> getPojoDescriptor(JClassType inputType) {

    PojoDescriptorBuilder descriptorBuilder = PojoDescriptorBuilderFactoryImpl.getInstance()
        .createPublicMethodDescriptorBuilder();
    Class<?> inputClass;
    try {
      inputClass = Class.forName(inputType.getQualifiedSourceName());
    } catch (ClassNotFoundException e) {
      throw new TypeNotFoundException(inputType.getQualifiedSourceName());
    }
    PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(inputClass);
    return descriptor;
  }

  @Override
  public long getVersionId() {

    return 1;
  }

  /**
   * This is a stateful class to {@link #generatePropertyDescriptorBlock(PojoPropertyDescriptor) generate} a
   * {@link PojoPropertyDescriptor}.
   */
  protected static class StatefulPropertyGenerator {

    /** @see #PojoDescriptorGenerator.StatefulPropertyGenerator(SourceWriter, JClassType) */
    private final SourceWriter sourceWriter;

    /** @see #PojoDescriptorGenerator.StatefulPropertyGenerator(SourceWriter, JClassType) */
    private final JClassType superType;

    /** @see #PojoDescriptorGenerator.StatefulPropertyGenerator(SourceWriter, JClassType) */
    private final PojoDescriptor<?> superDescriptor;

    private boolean superDescriptorBlockGenerated;

    private boolean superPropertyVariableDeclated;

    private boolean superPropertyDescriptorBlockGenerated;

    /**
     * The constructor.
     *
     * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the source code
     *        to.
     * @param superType is the supported super-type for which the descriptor may be reused in the generated code or
     *        {@code null} for none.
     */
    public StatefulPropertyGenerator(SourceWriter sourceWriter, JClassType superType) {

      super();
      this.sourceWriter = sourceWriter;
      this.superType = superType;
      if (superType == null) {
        this.superDescriptor = null;
      } else {
        this.superDescriptor = getPojoDescriptor(superType);
      }
    }

    /**
     * This method generates the source-code block to get the {@link PojoDescriptor} for the {@code superType} so things
     * can be reused from super-classes to reduce memory consumption.
     */
    protected void generateSuperDescriptorBlockIfNotAlreadyDone() {

      if (this.superDescriptorBlockGenerated) {
        return;
      }
      this.sourceWriter.print(PojoDescriptor.class.getSimpleName());
      this.sourceWriter.print("<?> superDescriptor = getPojoDescriptorBuilder().getDescriptor(");
      this.sourceWriter.print(this.superType.getQualifiedSourceName());
      this.sourceWriter.println(".class);");
      this.superDescriptorBlockGenerated = true;
    }

    /**
     * This method generates the source-code block to get the {@link PojoPropertyDescriptor} for the {@code superType}
     * so things can be reused from super-classes to reduce memory consumption.
     *
     * @param propertyDescriptor is the {@link PojoPropertyDescriptor}.
     */
    protected void generateSuperPropertyDescriptorBlockIfNotAlreadyDone(
        PojoPropertyDescriptor propertyDescriptor) {

      if (this.superPropertyDescriptorBlockGenerated) {
        return;
      }
      generateSuperDescriptorBlockIfNotAlreadyDone();
      if (!this.superPropertyVariableDeclated) {
        this.sourceWriter.print(PojoPropertyDescriptor.class.getSimpleName());
        this.sourceWriter.print("<?> ");
        this.superPropertyVariableDeclated = true;
      }
      this.sourceWriter.print("superPropertyDescriptor = superDescriptor.getPropertyDescriptor(\"");
      this.sourceWriter.print(propertyDescriptor.getName());
      this.sourceWriter.println("\");");
      this.superPropertyDescriptorBlockGenerated = true;
    }

    /**
     * Generates the source-code to rebuild the given {@link PojoPropertyDescriptor}.
     *
     * @param propertyDescriptor is the {@link PojoPropertyDescriptor}.
     */
    public void generatePropertyDescriptorBlock(PojoPropertyDescriptor propertyDescriptor) {

      this.superPropertyDescriptorBlockGenerated = false;
      PojoPropertyDescriptor superPropertyDescriptor = null;
      if (this.superDescriptor != null) {
        superPropertyDescriptor = this.superDescriptor.getPropertyDescriptor(propertyDescriptor.getName());
        if (superPropertyDescriptor == propertyDescriptor) {
          // generate reuse of entire property descriptor...
          generateSuperPropertyDescriptorBlockIfNotAlreadyDone(propertyDescriptor);
          this.sourceWriter.println("addPropertyDescriptor(superPropertyDescriptor);");
          return;
        }
      }

      this.sourceWriter.print("propertyDescriptor = getOrCreatePropertyDescriptor(\"");
      this.sourceWriter.print(propertyDescriptor.getName());
      this.sourceWriter.println("\");");

      // generate getter support
      generateAccessorStatement(propertyDescriptor, PojoPropertyAccessorNonArgMode.GET, superPropertyDescriptor);

      // generate setter support
      generateAccessorStatement(propertyDescriptor, PojoPropertyAccessorOneArgMode.SET, superPropertyDescriptor);
    }

    /**
     * Generates the source-code of the statement to register a {@link PojoPropertyAccessor}.
     *
     * @param propertyDescriptor is the {@link PojoPropertyDescriptor} to generate in the source.
     * @param superPropertyDescriptor is the {@link PojoPropertyDescriptor} of the super-type or {@code null} if NOT
     *        available.
     * @param mode is the {@link PojoPropertyAccessorMode} - either {@link PojoPropertyAccessorNonArgMode#GET} or
     *        {@link PojoPropertyAccessorOneArgMode#SET}.
     */
    protected void generateAccessorStatement(PojoPropertyDescriptor propertyDescriptor,
        PojoPropertyAccessorMode<?> mode, PojoPropertyDescriptor superPropertyDescriptor) {

      PojoPropertyAccessor accessor = propertyDescriptor.getAccessor(mode);
      if (accessor == null) {
        // accessor not available, nothing to do here...
        return;
      }
      boolean accessorNotReused = true;
      if (superPropertyDescriptor != null) {
        PojoPropertyAccessor superAccessor = superPropertyDescriptor.getAccessor(mode);
        if (superAccessor == accessor) {
          // generate reuse of accessor
          generateSuperPropertyDescriptorBlockIfNotAlreadyDone(propertyDescriptor);

          this.sourceWriter.print("propertyDescriptor.putAccessor(superPropertyDescriptor.getAccessor(");
          if (mode.isReading()) {
            this.sourceWriter.print(PojoPropertyAccessorNonArgMode.class.getSimpleName());
            this.sourceWriter.print(".GET");
          } else {
            this.sourceWriter.print(PojoPropertyAccessorOneArgMode.class.getSimpleName());
            this.sourceWriter.print(".SET");
          }
          this.sourceWriter.println("));");
          accessorNotReused = false;
        }
      }
      if (accessorNotReused) {
        generateAccessorRegistrationStatement(accessor);
      }
    }

    /**
     * Generates the source-code of the statement to register a {@link PojoPropertyAccessor}.
     *
     * @param accessor is the {@link PojoPropertyAccessor}.
     */
    protected void generateAccessorRegistrationStatement(PojoPropertyAccessor accessor) {

      this.sourceWriter.print("propertyDescriptor.putAccessor(new ");
      if (accessor.getMode().isReading()) {
        this.sourceWriter.print(AbstractPojoPropertyAccessorGetMethod.class.getSimpleName());
      } else {
        this.sourceWriter.print(AbstractPojoPropertyAccessorSetMethod.class.getSimpleName());
      }
      this.sourceWriter.print("(\"");
      this.sourceWriter.print(accessor.getName());
      this.sourceWriter.print("\", new ");
      this.sourceWriter.print(SimpleGenericTypeLimited.class.getSimpleName());
      this.sourceWriter.print("(");
      Class<?> propertyClass = accessor.getPropertyClass();
      this.sourceWriter.print(propertyClass.getName());
      this.sourceWriter.print(".class), ");
      this.sourceWriter.print(accessor.getDeclaringClass().getName());
      this.sourceWriter.println(".class) {");
      this.sourceWriter.indent();
      if (accessor.getMode().isReading()) {
        // getter
        this.sourceWriter.println("public Object invoke(Object pojo) {");
        this.sourceWriter.indent();
        this.sourceWriter.print("return ((");
        this.sourceWriter.print(accessor.getDeclaringClass().getName());
        this.sourceWriter.print(") pojo).");
        this.sourceWriter.print(accessor.getAccessibleObjectName());
        this.sourceWriter.println("();");
        this.sourceWriter.outdent();
        this.sourceWriter.println("}");
      } else {
        // setter
        this.sourceWriter.println("public Object invoke(Object pojo, Object value) {");
        this.sourceWriter.indent();
        this.sourceWriter.print("((");
        this.sourceWriter.print(accessor.getDeclaringClass().getName());
        this.sourceWriter.print(") pojo).");
        this.sourceWriter.print(accessor.getAccessibleObjectName());
        this.sourceWriter.print("((");
        if (propertyClass.isPrimitive()) {
          this.sourceWriter
              .print(ReflectionUtilImpl.getInstance().getNonPrimitiveType(propertyClass).getSimpleName());
        } else {
          this.sourceWriter.print(propertyClass.getName());
        }
        this.sourceWriter.println(")value);");
        this.sourceWriter.println("return null;");
        this.sourceWriter.outdent();
        this.sourceWriter.println("}");
      }
      this.sourceWriter.outdent();
      this.sourceWriter.println("});");
    }

  }

}
