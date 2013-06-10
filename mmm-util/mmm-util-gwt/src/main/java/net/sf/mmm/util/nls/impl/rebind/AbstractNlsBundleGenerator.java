/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.rebind;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.base.NlsMessagePlain;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the abstract base implementation of a GWT {@link Generator} for rebinding the
 * {@link net.sf.mmm.util.nls.api.NlsBundleFactory} implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsBundleGenerator extends AbstractIncrementalGenerator {

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, JMethod) */
  protected static final String VARIABLE_MESSAGE = "nlsL10nMessage";

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, JMethod) */
  protected static final String VARIABLE_ARGUMENTS = "nlsArguments";

  /**
   * The constructor.
   */
  public AbstractNlsBundleGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(Map.class.getName());
    sourceComposerFactory.addImport(HashMap.class.getName());
    sourceComposerFactory.addImport(NlsBundle.class.getName());
    sourceComposerFactory.addImport(NlsMessage.class.getName());
    sourceComposerFactory.addImport(NlsMessagePlain.class.getName());
    sourceComposerFactory.addImport(NlsAccess.class.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImplementedInterface(inputType.getQualifiedSourceName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    // inputType is the NlsBundle-class
    generateFields(sourceWriter, logger, context, inputType);

    // generate methods of bundle
    for (JMethod method : inputType.getOverridableMethods()) {
      JType returnType = method.getReturnType();
      if (NlsMessage.class.getName().equals(returnType.getQualifiedSourceName())) {
        generateMethod(sourceWriter, logger, context, method);
      } else {
        throw new ObjectMismatchException(returnType.getSimpleSourceName(), NlsMessage.class,
            inputType.getQualifiedSourceName(), method.getName());
      }
    }
  }

  /**
   * Generates an implementation of an {@link NlsBundle}-method.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected void generateMethod(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context, JMethod method) {

    // method signature
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

    // method body...
    generateMethodBody(sourceWriter, logger, context, method);

    // end method
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.println();
  }

  /**
   * Generates an the body of an {@link NlsBundle}-method.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected void generateMethodBody(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JMethod method) {

    JParameter[] methodParameters = method.getParameters();
    if (methodParameters.length > 0) {

      sourceWriter.print("Map<String, Object> ");
      sourceWriter.print(VARIABLE_ARGUMENTS);
      sourceWriter.println(" = new HashMap<String, Object>();");

      // loop over parameters and generate code that puts the parameters into the arguments map
      for (JParameter parameter : methodParameters) {
        String name;
        Named namedAnnotation = parameter.getAnnotation(Named.class);
        if (namedAnnotation == null) {
          name = parameter.getName();
        } else {
          name = namedAnnotation.value();
        }
        sourceWriter.print(VARIABLE_ARGUMENTS);
        sourceWriter.print(".put(\"");
        sourceWriter.print(escape(name));
        sourceWriter.print("\", ");
        sourceWriter.print(parameter.getName());
        sourceWriter.println(");");
      }
    }

    generateMethodMessageBlock(sourceWriter, logger, context, method);

    // return NlsAccess.getFactory().create(message, arguments);
    if (methodParameters.length > 0) {
      sourceWriter.print("return ");
      sourceWriter.print(NlsAccess.class.getSimpleName());
      sourceWriter.print(".getFactory().create(");
      sourceWriter.print(VARIABLE_MESSAGE);
      sourceWriter.print(", ");
      sourceWriter.print(VARIABLE_ARGUMENTS);
      sourceWriter.println(");");
    } else {
      sourceWriter.print("return new ");
      sourceWriter.print(NlsMessagePlain.class.getSimpleName());
      sourceWriter.print("(");
      sourceWriter.print(VARIABLE_MESSAGE);
      sourceWriter.println(");");
    }
  }

  /**
   * Generates the block of the {@link NlsBundle}-method body that creates the code block with the
   * {@link String} variable {@link #VARIABLE_MESSAGE}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected abstract void generateMethodMessageBlock(SourceWriter sourceWriter, TreeLogger logger,
      GeneratorContext context, JMethod method);

  /**
   * Generates the (private) fields.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param bundleClass is the {@link JClassType} reflecting the {@link NlsBundle}.
   */
  protected void generateFields(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JClassType bundleClass) {

    // nothing by default

  }

}
