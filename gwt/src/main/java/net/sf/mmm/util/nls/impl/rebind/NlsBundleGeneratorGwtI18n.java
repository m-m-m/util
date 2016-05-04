/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.rebind.format.PropertiesFormat;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsBundleKey;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.base.NlsBundleHelper;
import net.sf.mmm.util.reflect.api.ClassName;
import net.sf.mmm.util.reflect.api.TypeNotFoundException;

/**
 * This is the GWT {@link com.google.gwt.core.ext.Generator} for generation of {@link net.sf.mmm.util.nls.api.NlsBundle}
 * implementations.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleGeneratorGwtI18n extends AbstractNlsBundleGenerator {

  /** The name of the variable with the GWT i18n interface. */
  private static final String VARIABLE_GWT_I18N = "GWT_I18N";

  /**
   * The constructor.
   */
  public NlsBundleGeneratorGwtI18n() {

    super();
  }

  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    super.generateImportStatements(inputType, logger, sourceComposerFactory, context);
    sourceComposerFactory.addImport(Constants.class.getName());
    sourceComposerFactory.addImport(GWT.class.getName());
  }

  @Override
  protected void generateFields(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JClassType bundleClass) {

    super.generateFields(sourceWriter, logger, context, bundleClass);

    // generate i18n interface singleton field
    sourceWriter.print("private static final ");
    String i18nInterface = generateBundleInterface(bundleClass, logger, context);
    sourceWriter.print(i18nInterface);
    sourceWriter.print(" ");
    sourceWriter.print(VARIABLE_GWT_I18N);
    sourceWriter.print(" = GWT.create(");
    sourceWriter.print(i18nInterface);
    sourceWriter.println(".class);");
    sourceWriter.println();
  }

  @Override
  protected void generateMethodMessageBlock(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      String methodName) {

    sourceWriter.print(VARIABLE_MESSAGE);
    sourceWriter.print(" = ");
    sourceWriter.print(VARIABLE_GWT_I18N);
    sourceWriter.print(".");
    sourceWriter.print(methodName);
    sourceWriter.println("();");
  }

  /**
   * This method generates the GWT-i18n-interface for the NLS-bundle.
   *
   * @param bundleClass is the {@link JClassType class} of the {@link net.sf.mmm.util.nls.api.NlsBundle} to generate.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the name of the generated class.
   */
  @SuppressWarnings({ "rawtypes" })
  private String generateBundleInterface(JClassType bundleClass, TreeLogger logger, GeneratorContext context) {

    Class bundleJavaClass;
    String bundleName = bundleClass.getQualifiedSourceName();
    try {
      bundleJavaClass = Class.forName(bundleName);
    } catch (ClassNotFoundException e) {
      throw new TypeNotFoundException(bundleName);
    }
    @SuppressWarnings("unchecked")
    ClassName bundleClassName = NlsBundleHelper.getInstance().getQualifiedLocation(bundleJavaClass);
    String packageName = bundleClassName.getPackageName();
    String simpleName = bundleClassName.getSimpleName();
    if (bundleClassName.getName().equals(bundleName)) {
      logger.log(TreeLogger.ERROR, getClass().getSimpleName() + ": Illegal NlsBundle '" + bundleName
          + "' - has to end with suffix 'Root'. Localization will not work!");
      simpleName = simpleName + "_Interface";
    }
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName,
        simpleName);
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
    annotationBuffer.append("\")");

    sourceComposerFactory.addAnnotationDeclaration(annotationBuffer.toString());

    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      // generate methods for fields of bundle
      for (JMethod method : bundleClass.getOverridableMethods()) {
        JType returnType = method.getReturnType();
        if (!isLookupMethod(method)) {
          if (!NlsMessage.class.getName().equals(returnType.getQualifiedSourceName())) {
            throw new IllegalCaseException(returnType.getQualifiedSourceName());
          }
          NlsBundleMessage messageAnnotation = method.getAnnotation(NlsBundleMessage.class);
          if (messageAnnotation != null) {
            String message = messageAnnotation.value();
            // generate message annotation
            sourceWriter.print("@DefaultStringValue(\"");
            sourceWriter.print(escape(message));
            sourceWriter.println("\")");
          }

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

      }
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  @Override
  public long getVersionId() {

    return 1;
  }

}
