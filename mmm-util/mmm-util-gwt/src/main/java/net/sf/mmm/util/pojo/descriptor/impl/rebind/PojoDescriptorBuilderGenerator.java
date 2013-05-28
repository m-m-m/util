/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderLimited;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorImpl;
import net.sf.mmm.util.transferobject.api.TransferObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the {@link AbstractIncrementalGenerator incremental GWT generator} to generate the implementation
 * of {@link PojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PojoDescriptorBuilderGenerator extends AbstractIncrementalGenerator {

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isCachedResultObsolete(CachedGeneratorResult cachedGeneratorResult, String typeName) {

    // we can not cache the result as we can never know if a new type has been added matching to
    // getMarkerClass()
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(GWT.class.getName());
    sourceComposerFactory.addImport(PojoDescriptorImpl.class.getName());

    sourceComposerFactory.addImport(AbstractPojoDescriptorBuilderLimited.class.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.setSuperclass(AbstractPojoDescriptorBuilderLimited.class.getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    // generate Constructor
    generateDefaultConstructor(sourceWriter, simpleName);

    generateMethodCreateDescriptor(sourceWriter, logger, simpleName, inputType, context);

  }

  /**
   * Generates the method {@link AbstractPojoDescriptorBuilderLimited#createDescriptor(Class)}.
   * 
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the
   *        source code to.
   * @param logger is the {@link TreeLogger}.
   * @param simpleName is the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param context is the {@link GeneratorContext}.
   */
  private void generateMethodCreateDescriptor(SourceWriter sourceWriter, TreeLogger logger, String simpleName,
      JClassType inputType, GeneratorContext context) {

    sourceWriter.print("public <POJO> ");
    sourceWriter.print(PojoDescriptorImpl.class.getSimpleName());
    sourceWriter.println("<POJO> createDescriptor(Class<POJO> pojoType) {");
    sourceWriter.indent();

    TypeOracle typeOracle = context.getTypeOracle();
    JClassType markerType = typeOracle.findType(getMarkerClass().getName());
    boolean typeFound = false;
    JClassType[] types = typeOracle.getTypes();
    for (JClassType type : types) {
      if (type.getQualifiedSourceName().startsWith("net.sf.mmm.app")) {
        logger.log(Type.INFO, type.getQualifiedSourceName());
      }
      if (type.isAssignableTo(markerType)) {
        if ((!type.equals(markerType)) && (type.isInterface() == null) && (!type.isAbstract())) {
          typeFound = true;
          sourceWriter.print("if (pojoType == ");
          sourceWriter.print(type.getQualifiedSourceName());
          sourceWriter.println(".class) {");
          sourceWriter.indent();
          sourceWriter.print("return GWT.create(");
          sourceWriter.print(type.getQualifiedSourceName());
          sourceWriter.println(".class);");
          sourceWriter.outdent();
          sourceWriter.print("} else ");
        }
      }
    }
    if (typeFound) {
      sourceWriter.println("{");
      sourceWriter.indent();
    } else {
      logger.log(Type.ERROR, "No type found for " + getMarkerClass());
    }
    sourceWriter.println("return super.createDescriptor(pojoType);");
    if (typeFound) {
      sourceWriter.outdent();
      sourceWriter.println("}");
    }
    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * This method gets the {@link Class} reflecting the class or interface used as markers for objects for
   * {@link net.sf.mmm.util.pojo.api.Pojo POJOs} where reflection should be supported via
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}.
   * 
   * @return the marker {@link Class}.
   */
  protected Class<?> getMarkerClass() {

    return TransferObject.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getVersionId() {

    return 1;
  }

}
