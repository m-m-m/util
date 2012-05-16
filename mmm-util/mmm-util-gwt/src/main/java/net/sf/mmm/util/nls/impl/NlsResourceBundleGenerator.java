/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.PrintWriter;
import java.lang.reflect.Field;

import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.TypeNotFoundException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * TODO: this class ...
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class NlsResourceBundleGenerator extends Generator {

  /** TODO: javadoc. */
  private static final String VARIABLE_GWT_I18N = "GWT_I18N";

  /** The suffix for the generated resource-bundle class. */
  private static final String SUFFIX_CLASS = "_Class";

  /** The suffix for the generated resource-bundle interface. */
  private static final String SUFFIX_INTERFACE = "_Interface";

  /**
   * The constructor.
   * 
   */
  public NlsResourceBundleGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

    String packageName = NlsGwtSupport.class.getPackage().getName();
    String simpleName = NlsGwtSupport.class.getSimpleName() + "Impl";
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + typeName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    // import statements
    sourceComposerFactory.addImport(NlsGwtSupport.class.getName());

    sourceComposerFactory.addImplementedInterface(NlsGwtSupport.class.getName());
    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      sourceWriter.indent();
      // find all subclasses of AbstractResourceBundle
      TypeOracle typeOracle = context.getTypeOracle();
      JClassType bundleClass = typeOracle.findType(AbstractResourceBundle.class.getName());
      for (JClassType type : typeOracle.getTypes()) {
        if ((type.isAssignableTo(bundleClass)) && (!type.equals(bundleClass))) {
          generateBundleClass(type, logger, context);
        }
      }

      sourceWriter.outdent();
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the GWT variant of the NLS-bundle.
   * 
   * @param bundleClass is the {@link JClassType class} of the {@link AbstractResourceBundle} to generate.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the name of the generated class.
   */
  private String generateBundleClass(JClassType bundleClass, TreeLogger logger, GeneratorContext context) {

    String packageName = bundleClass.getPackage().getName();
    String simpleName = bundleClass.getName() + SUFFIX_CLASS;
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    // import statements
    String i18nInterface = bundleClass.getName() + SUFFIX_INTERFACE;
    sourceComposerFactory.addImport(Constants.class.getName());
    sourceComposerFactory.addImport(GWT.class.getName());

    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      // generate i18n interface singleton field
      sourceWriter.print("private static final ");
      sourceWriter.print(i18nInterface);
      sourceWriter.print(" ");
      sourceWriter.print(VARIABLE_GWT_I18N);
      sourceWriter.print(" = GWT.create(");
      sourceWriter.print(i18nInterface);
      sourceWriter.println(".class);");

      // generate fields of bundle
      for (JField field : bundleClass.getFields()) {
        if (field.isFinal() && field.isStatic()) {
          if (field.isPublic()) {
            sourceWriter.print("public ");
          } else if (field.isProtected()) {
            sourceWriter.print("protected ");
          } else if (field.isPrivate()) {
            sourceWriter.print("private ");
          }
          sourceWriter.print("static final ");
          sourceWriter.print(field.getType().getQualifiedSourceName());
          sourceWriter.print(" ");
          sourceWriter.print(field.getName());
          sourceWriter.print(" = ");
          sourceWriter.print(VARIABLE_GWT_I18N);
          sourceWriter.print(".");
          sourceWriter.print(field.getName());
          sourceWriter.println("();");
          sourceWriter.println();
        }
      }
      generateBundleInterface(bundleClass, sourceWriter, logger, context);
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the GWT-i18n-interface for the NLS-bundle.
   * 
   * @param bundleClass is the {@link JClassType class} of the {@link AbstractResourceBundle} to generate.
   * @param sourceWriter is the existing {@link SourceWriter} (interface is generated as inner type).
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   */
  private void generateBundleInterface(JClassType bundleClass, SourceWriter sourceWriter, TreeLogger logger,
      GeneratorContext context) {

    Class<?> bundleJavaClass;
    try {
      bundleJavaClass = Class.forName(bundleClass.getQualifiedSourceName());
    } catch (ClassNotFoundException e) {
      throw new TypeNotFoundException(bundleClass.getQualifiedSourceName());
    }
    String i18nInterface = bundleClass.getName() + SUFFIX_INTERFACE;
    sourceWriter.print("public interface ");
    sourceWriter.print(i18nInterface);
    sourceWriter.println(" extends Constants {");
    sourceWriter.indent();
    // generate methods for fields of bundle
    for (JField field : bundleClass.getFields()) {
      if (field.isFinal() && field.isStatic()) {
        String fieldType = field.getType().getQualifiedSourceName();
        Field javaField;
        try {
          javaField = bundleJavaClass.getField(field.getName());
        } catch (Exception e) {
          throw new ObjectNotFoundException(e, bundleJavaClass.getName() + "." + field.getName());
        }
        Object fieldValue;
        try {
          fieldValue = javaField.get(null);
        } catch (Exception e) {
          throw new AccessFailedException(e, javaField);
        }
        if ("java.lang.String".equals(fieldType)) {
          // generate annotation
          sourceWriter.print("@DefaultStringValue(\"");
          sourceWriter.print("X" + escape(fieldValue.toString()));
          sourceWriter.println("\")");
        } else {
          throw new IllegalCaseException(fieldType);
        }

        // generate method
        sourceWriter.print(fieldType);
        sourceWriter.print(" ");
        sourceWriter.print(field.getName());
        sourceWriter.println("();");
        sourceWriter.println();
      }
    }
    sourceWriter.outdent();

  }

}
