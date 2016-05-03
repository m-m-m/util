/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.rebind;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.base.AbstractNlsBundleFactory;
import net.sf.mmm.util.nls.base.NlsMessagePlain;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the abstract base implementation of a GWT {@link com.google.gwt.core.ext.Generator} for rebinding
 * the {@link net.sf.mmm.util.nls.api.NlsBundleFactory} implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractNlsBundleGenerator extends AbstractIncrementalGenerator {

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, String) */
  protected static final String VARIABLE_MESSAGE = "nlsL10nMessage";

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, String) */
  protected static final String VARIABLE_ARGUMENTS = "nlsArguments";

  /**
   * The constructor.
   */
  public AbstractNlsBundleGenerator() {

    super();
  }

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

  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImplementedInterface(inputType.getQualifiedSourceName());
  }

  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    // inputType is the NlsBundle-class
    generateFields(sourceWriter, logger, context, inputType);

    // generate methods of bundle
    JMethod[] methods = inputType.getOverridableMethods();
    for (JMethod method : methods) {
      JType returnType = method.getReturnType();
      if (NlsMessage.class.getName().equals(returnType.getQualifiedSourceName())) {
        if (isLookupMethod(method)) {
          if (AbstractNlsBundleFactory.METHOD_NAME_LOOKUP.equals(method.getName())) {
            generateLookupMethod(sourceWriter, logger, context, method, methods);
          } else {
            logger.log(Type.WARN, "Ignoring illegal lookup method: " + method.getName());
          }
        } else {
          generateMethod(sourceWriter, logger, context, method);
        }
      } else {
        throw new ObjectMismatchException(returnType.getSimpleSourceName(), NlsMessage.class,
            inputType.getQualifiedSourceName(), method.getName());
      }
    }
  }

  /**
   * Generates the implementation of {@link NlsBundleWithLookup#getMessage(String, Map)}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundleWithLookup#getMessage(String, Map)}-method to generate an
   *        implementation for.
   * @param methods is the list of all declared methods of the bundle. Likely to be ignored but may be used to
   *        generate a switch statement with delegations.
   */
  protected void generateLookupMethod(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JMethod method, JMethod[] methods) {

    generateSourcePublicMethodDeclaration(sourceWriter, method);

    sourceWriter.print("String ");
    sourceWriter.print(VARIABLE_MESSAGE);
    sourceWriter.println("= null;");
    boolean addElse = false;
    for (JMethod currentMethod : methods) {
      if (!isLookupMethod(currentMethod)) {
        if (addElse) {
          sourceWriter.print(" else ");
        } else {
          addElse = true;
        }
        sourceWriter.print("if (methodName.equals(\"");
        String methodName = currentMethod.getName();
        sourceWriter.print(methodName);
        sourceWriter.println("\")) {");
        sourceWriter.indent();
        generateMethodMessageBlock(sourceWriter, logger, context, methodName);
        sourceWriter.outdent();
        sourceWriter.print("}");
      } else {
        assert (method == currentMethod);
      }
    }
    // methodName not found/present?
    sourceWriter.println();
    sourceWriter.print("if (");
    sourceWriter.print(VARIABLE_MESSAGE);
    sourceWriter.println("== null) {");
    sourceWriter.indent();
    sourceWriter.println("return null;");
    sourceWriter.outdent();
    sourceWriter.println("}");

    // generate message depending on arguments
    sourceWriter.print("if ((");
    sourceWriter.print(VARIABLE_ARGUMENTS);
    sourceWriter.print(" == null) || (");
    sourceWriter.print(VARIABLE_ARGUMENTS);
    sourceWriter.println(".isEmpty())) {");
    sourceWriter.indent();
    generateCreateMessageBlock(sourceWriter, false);
    sourceWriter.outdent();
    sourceWriter.println("} else {");
    sourceWriter.indent();
    generateCreateMessageBlock(sourceWriter, true);
    sourceWriter.outdent();
    sourceWriter.println("}");

    generateSourceCloseBlock(sourceWriter);
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

    generateSourcePublicMethodDeclaration(sourceWriter, method);
    generateMethodBody(sourceWriter, logger, context, method);
    generateSourceCloseBlock(sourceWriter);
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

    sourceWriter.print("String ");
    generateMethodMessageBlock(sourceWriter, logger, context, method.getName());

    generateCreateMessageBlock(sourceWriter, methodParameters.length > 0);
  }

  /**
   * Generates the source code block to create a new {@link NlsMessage}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param hasArguments - {@code true} if {@link NlsMessage#getArgument(String) arguments} are given,
   *        {@code false} otherwise.
   */
  private void generateCreateMessageBlock(SourceWriter sourceWriter, boolean hasArguments) {

    // return NlsAccess.getFactory().create(message, arguments);
    if (hasArguments) {
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
   * {@link String} variable {@link #VARIABLE_MESSAGE} excluding the {@link String} declaration.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param methodName is the name of the {@link NlsBundle}-method to generate.
   */
  protected abstract void generateMethodMessageBlock(SourceWriter sourceWriter, TreeLogger logger,
      GeneratorContext context, String methodName);

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

  /**
   * Determines if the given {@link JMethod} is declared in {@link NlsBundleWithLookup}.
   * 
   * @see NlsBundleWithLookup#getMessage(String, Map)
   * 
   * @param method is the {@link JMethod} to test.
   * @return {@code true} if the given method is from {@link NlsBundleWithLookup}, {@code false}
   *         otherwise.
   */
  protected boolean isLookupMethod(JMethod method) {

    return NlsBundleWithLookup.class.getName().equals(method.getEnclosingType().getQualifiedSourceName());
  }

}
