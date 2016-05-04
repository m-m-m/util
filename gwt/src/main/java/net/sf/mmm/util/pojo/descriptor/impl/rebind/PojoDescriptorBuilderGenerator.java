/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderLimited;
import net.sf.mmm.util.pojo.descriptor.impl.AbstractPojoDescriptorImpl;

/**
 * This is the {@link net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator incremental GWT generator} to
 * generate the implementation of {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PojoDescriptorBuilderGenerator extends AbstractPojoDescriptorGenerator {

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderGenerator() {

    super();
  }

  @Override
  protected boolean isCachedResultObsolete(CachedGeneratorResult cachedGeneratorResult, String typeName) {

    // we can not cache the result as we can never know if a new type has been added matching to
    // getMarkerClass()
    return true;
  }

  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(GWT.class.getName());
    sourceComposerFactory.addImport(AbstractPojoDescriptorImpl.class.getName());
    sourceComposerFactory.addImport(AbstractPojoDescriptorBuilderLimited.class.getName());
  }

  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.setSuperclass(AbstractPojoDescriptorBuilderLimited.class.getSimpleName());
  }

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
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the source code
   *        to.
   * @param logger is the {@link TreeLogger}.
   * @param simpleName is the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param context is the {@link GeneratorContext}.
   */
  private void generateMethodCreateDescriptor(SourceWriter sourceWriter, TreeLogger logger, String simpleName,
      JClassType inputType, GeneratorContext context) {

    sourceWriter.print("public <POJO> ");
    sourceWriter.print(AbstractPojoDescriptorImpl.class.getSimpleName());
    sourceWriter.println("<POJO> createDescriptor(Class<POJO> pojoType) {");
    sourceWriter.indent();

    PojoDescriptorGeneratorConfiguration configuration = getConfiguration();
    TypeOracle typeOracle = context.getTypeOracle();
    int typeCount = 0;
    JClassType[] types = typeOracle.getTypes();
    for (JClassType type : types) {
      if (configuration.isPojoTypeSupported(type, typeOracle)) {
        typeCount++;
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
    if (typeCount > 0) {
      sourceWriter.println("{");
      sourceWriter.indent();
      if (typeCount <= 3) {
        logger.log(Type.WARN, "Found only " + typeCount + " supported type(s)");
      } else {
        logger.log(Type.INFO, "Found " + typeCount + " supported types.");
      }
    } else {
      logger.log(Type.ERROR, "No type found for criteria: " + configuration.getPojoTypeDescription());
    }
    sourceWriter.println("return super.createDescriptor(pojoType);");
    if (typeCount > 0) {
      sourceWriter.outdent();
      sourceWriter.println("}");
    }
    generateSourceCloseBlock(sourceWriter);
  }

  @Override
  public long getVersionId() {

    return 1;
  }

}
