/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleKey;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterManagerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.rebind.format.PropertiesFormat;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the GWT {@link Generator} for generation of the true
 * {@link net.sf.mmm.util.nls.api.NlsBundleFactory} implementation as well as according {@link NlsBundle}
 * implementations.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class NlsResourceBundleGenerator extends Generator {

  /** The name of the variable with the GWT i18n interface. */
  private static final String VARIABLE_GWT_I18N = "GWT_I18N";

  /** The name of the variable with the {@link NlsDependenciesImpl}. */
  private static final String VARIABLE_NLS_DEPENDENCIES = "NLS_DEPENDENCIES";

  /** The suffix for the generated resource-bundle class. */
  private static final String SUFFIX_CLASS = "_GwtImpl";

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

    String packageName = AbstractNlsBundleFactoryGwt.class.getPackage().getName();
    String simpleName = NlsBundle.class.getSimpleName() + "GwtImpl";
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    // import statements
    sourceComposerFactory.addImport(AbstractNlsBundleFactoryGwt.class.getName());

    sourceComposerFactory.setSuperclass(AbstractNlsBundleFactoryGwt.class.getSimpleName());
    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      // generate constructor...
      sourceWriter.print("public ");
      sourceWriter.print(simpleName);
      sourceWriter.println("() {");
      sourceWriter.indent();
      sourceWriter.println("super();");
      sourceWriter.println("Class bundleInterface;");
      // find all subclasses of NlsBundle
      TypeOracle typeOracle = context.getTypeOracle();
      JClassType bundleClass = typeOracle.findType(NlsBundle.class.getName());
      for (JClassType type : typeOracle.getTypes()) {
        if ((type.isAssignableTo(bundleClass)) && (!type.equals(bundleClass))) {
          String bundleClassName = generateBundleClass(type, logger, context);

          sourceWriter.print("bundleInterface = ");
          sourceWriter.print(type.getQualifiedSourceName());
          sourceWriter.println(".class;");

          sourceWriter.print("register(bundleInterface, new ");
          sourceWriter.print(bundleClassName);
          sourceWriter.println("());");
        }
      }
      sourceWriter.outdent();
      sourceWriter.println("}");
      // end constructor

      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the GWT variant of the NLS-bundle.
   * 
   * @param bundleClass is the {@link JClassType class} of the {@link NlsBundle} to generate.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the name of the generated class.
   */
  private String generateBundleClass(JClassType bundleClass, TreeLogger logger, GeneratorContext context) {

    String packageName = bundleClass.getPackage().getName();
    String simpleName = bundleClass.getName() + SUFFIX_CLASS;
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);

    sourceComposerFactory.addImplementedInterface(bundleClass.getQualifiedSourceName());
    // import statements
    String i18nInterface = generateBundleInterface(bundleClass, logger, context);
    sourceComposerFactory.addImport(Constants.class.getName());
    sourceComposerFactory.addImport(GWT.class.getName());
    sourceComposerFactory.addImport(Map.class.getName());
    sourceComposerFactory.addImport(HashMap.class.getName());
    sourceComposerFactory.addImport(NlsBundle.class.getName());
    sourceComposerFactory.addImport(NlsMessage.class.getName());
    sourceComposerFactory.addImport(NlsMessageImpl.class.getName());
    sourceComposerFactory.addImport(NlsDependencies.class.getName());
    sourceComposerFactory.addImport(NlsFormatterManagerImpl.class.getName());

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
      sourceWriter.println();

      // generate nls dependencies singleton field
      sourceWriter.print("private static final ");
      sourceWriter.print(NlsDependencies.class.getSimpleName());
      sourceWriter.print(" ");
      sourceWriter.print(VARIABLE_NLS_DEPENDENCIES);
      sourceWriter.println(";");
      sourceWriter.println("static {");
      sourceWriter.indent();
      sourceWriter.print(NlsFormatterManagerImpl.class.getSimpleName());
      sourceWriter.print(" formatterManager = new ");
      sourceWriter.print(NlsFormatterManagerImpl.class.getSimpleName());
      sourceWriter.println("();");
      sourceWriter.println("formatterManager.initialize();");
      sourceWriter.print(VARIABLE_NLS_DEPENDENCIES);
      sourceWriter.println(" = formatterManager.getNlsDependencies();");
      sourceWriter.outdent();
      sourceWriter.println("}");
      sourceWriter.println();

      // generate methods of bundle
      for (JMethod method : bundleClass.getOverridableMethods()) {
        sourceWriter.print("public ");
        sourceWriter.print(NlsMessage.class.getSimpleName());
        sourceWriter.print(" ");
        sourceWriter.print(method.getName());
        sourceWriter.print("(");
        boolean firstParameter = true;
        for (JParameter parameter : method.getParameters()) {
          if (firstParameter) {
            firstParameter = false;
          } else {
            sourceWriter.print(", ");
          }
          sourceWriter.print(parameter.getType().getQualifiedSourceName());
          sourceWriter.print(" ");
          sourceWriter.print(parameter.getName());
        }
        sourceWriter.println(") {");
        sourceWriter.indent();
        sourceWriter.print("String message = ");
        sourceWriter.print(VARIABLE_GWT_I18N);
        sourceWriter.print(".");
        sourceWriter.print(method.getName());
        sourceWriter.println("();");
        sourceWriter.println("Map<String, Object> arguments = new HashMap<String, Object>();");
        for (JParameter parameter : method.getParameters()) {
          String name;
          Named namedAnnotation = parameter.getAnnotation(Named.class);
          if (namedAnnotation == null) {
            name = parameter.getName();
          } else {
            name = namedAnnotation.value();
          }
          sourceWriter.print("arguments.put(\"");
          sourceWriter.print(escape(name));
          sourceWriter.print("\", ");
          sourceWriter.print(parameter.getName());
          sourceWriter.println(");");
        }
        sourceWriter.print("return new ");
        sourceWriter.print(NlsMessageImpl.class.getSimpleName());
        sourceWriter.print("(message, arguments, ");
        sourceWriter.print(VARIABLE_NLS_DEPENDENCIES);
        sourceWriter.println(");");
        sourceWriter.outdent();
        sourceWriter.println("}");
        sourceWriter.println();
      }
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the GWT-i18n-interface for the NLS-bundle.
   * 
   * @param bundleClass is the {@link JClassType class} of the {@link NlsBundle} to generate.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the name of the generated class.
   */
  private String generateBundleInterface(JClassType bundleClass, TreeLogger logger, GeneratorContext context) {

    String packageName = bundleClass.getPackage().getName();
    String simpleName = bundleClass.getName() + SUFFIX_INTERFACE;
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    sourceComposerFactory.makeInterface();
    // import statements
    sourceComposerFactory.addImport(Constants.class.getName());
    sourceComposerFactory.addImport(Generate.class.getCanonicalName());

    sourceComposerFactory.addImplementedInterface(Constants.class.getSimpleName());

    // @Generate annotation
    StringBuilder annotationBuffer = new StringBuilder();
    annotationBuffer.append("@");
    annotationBuffer.append(Generate.class.getSimpleName());
    annotationBuffer.append("(format = \"");
    annotationBuffer.append(PropertiesFormat.class.getName());
    annotationBuffer.append("\", fileName = \"");
    annotationBuffer.append(bundleClass.getSimpleSourceName());
    annotationBuffer.append("\")");

    sourceComposerFactory.addAnnotationDeclaration("@" + Generate.class.getSimpleName() + "(format = \""
        + PropertiesFormat.class.getName() + "\", fileName = \"" + "BasisDialogMessages" + "\")");

    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      // generate methods for fields of bundle
      for (JMethod method : bundleClass.getOverridableMethods()) {
        JType returnType = method.getReturnType();
        if (!NlsMessage.class.getName().equals(returnType.getQualifiedSourceName())) {
          throw new IllegalCaseException(returnType.getQualifiedSourceName());
        }

        NlsBundleMessage messageAnnotation = method.getAnnotation(NlsBundleMessage.class);
        String message;
        if (messageAnnotation == null) {
          message = "TODO";
        } else {
          message = messageAnnotation.value();
        }
        // generate message annotation
        sourceWriter.print("@DefaultStringValue(\"");
        sourceWriter.print("X" + escape(message));
        sourceWriter.println("\")");

        NlsBundleKey keyAnnotation = method.getAnnotation(NlsBundleKey.class);
        if (keyAnnotation != null) {
          // generate key annotation
          sourceWriter.print("@Key(\"");
          sourceWriter.print(escape(keyAnnotation.value()));
          sourceWriter.println("\")");
        }
        // generate method
        sourceWriter.print("String ");
        sourceWriter.print(method.getName());
        sourceWriter.println("();");

        sourceWriter.println();

      }
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }
}
