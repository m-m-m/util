/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.base.rebind;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import net.sf.mmm.util.nls.api.IllegalCaseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.IncrementalGenerator;
import com.google.gwt.core.ext.RebindMode;
import com.google.gwt.core.ext.RebindResult;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the abstract base implementation for {@link IncrementalGenerator}s for GWT environments, that
 * generates a {@link Class} from a single input type. If the input type has not been modified since the last
 * generation the {@link #isCachedResultObsolete(CachedGeneratorResult, String) generated result is reused by
 * default}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractIncrementalGenerator extends IncrementalGenerator {

  /**
   * The constructor.
   */
  public AbstractIncrementalGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RebindResult generateIncrementally(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {

    CachedGeneratorResult cachedGeneratorResult = context.getCachedGeneratorResult();
    if (cachedGeneratorResult != null) {
      boolean obsolete = isCachedResultObsolete(cachedGeneratorResult, typeName);
      if (!obsolete) {
        return new RebindResult(RebindMode.USE_ALL_CACHED, cachedGeneratorResult.getResultTypeName());
      }
    }
    TypeOracle typeOracle = context.getTypeOracle();
    JClassType inputType = typeOracle.findType(typeName);
    String resultType = generate(inputType, logger, context);
    return new RebindResult(RebindMode.USE_ALL_NEW, resultType);
  }

  /**
   * This method performs the actual generation of the {@link Class} to rebind.
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the fully qualified name of the generated class.
   */
  protected String generate(JClassType inputType, TreeLogger logger, GeneratorContext context) {

    String packageName = inputType.getPackage().getName();
    String simpleName = createClassName(inputType);
    logger.log(TreeLogger.DEBUG, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    // Import statements
    generateImportStatements(inputType, logger, sourceComposerFactory, context);

    // Class declaration
    generateClassDeclaration(inputType, logger, sourceComposerFactory, context);

    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      generateClassContents(inputType, logger, sourceWriter, simpleName, context);
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the import statements. It shall only perform invocations of
   * {@link ClassSourceFileComposerFactory#addImport(String)} to the given
   * {@link ClassSourceFileComposerFactory}.
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param logger is the {@link TreeLogger}.
   * @param sourceComposerFactory is the {@link ClassSourceFileComposerFactory}.
   * @param context is the {@link GeneratorContext}.
   */
  protected abstract void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context);

  /**
   * This method generates the additional things for the class declaration. This is the place where to invoke
   * methods like
   * <ul>
   * <li>{@link ClassSourceFileComposerFactory#setSuperclass(String)}</li>
   * <li>{@link ClassSourceFileComposerFactory#addImplementedInterface(String)}</li>
   * <li>{@link ClassSourceFileComposerFactory#addAnnotationDeclaration(String)}</li>
   * <li>{@link ClassSourceFileComposerFactory#makeInterface()}</li>
   * <li>{@link ClassSourceFileComposerFactory#setJavaDocCommentForClass(String)}</li>
   * </ul>
   * on the given {@link ClassSourceFileComposerFactory}. E.g.
   * 
   * <pre>
   * sourceComposerFactory.addImplementedInterface(inputType.getQualifiedSourceName());
   * </pre>
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param logger is the {@link TreeLogger}.
   * @param sourceComposerFactory is the {@link ClassSourceFileComposerFactory}.
   * @param context is the {@link GeneratorContext}.
   */
  protected abstract void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context);

  /**
   * This method generates the actual contents of the {@link Class} to generate.
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @param logger is the {@link TreeLogger}.
   * @param sourceWriter is the {@link SourceWriter} where to {@link SourceWriter#print(String) write} the
   *        source code to.
   * @param simpleName is the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * @param context is the {@link GeneratorContext}.
   */
  protected abstract void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context);

  /**
   * This method creates the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   * Typically derived from {@link JClassType#getName()} of the given <code>inputType</code> with a static
   * suffix or prefix that is specific enough to avoid clashing with existing classes.
   * 
   * @param inputType is the {@link JClassType} reflecting the input-type that triggered the generation via
   *        {@link GWT#create(Class)}.
   * @return the {@link Class#getSimpleName() simple name} of the {@link Class} to generate.
   */
  protected String createClassName(JClassType inputType) {

    return inputType.getName() + "_GwtImpl";

  }

  /**
   * Generates the the default constructor.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param simpleName is the {@link Class#getSimpleName() simple name}.
   */
  protected void generateDefaultConstructor(SourceWriter sourceWriter, String simpleName) {

    generateSourcePublicConstructorDeclaration(sourceWriter, simpleName);
    sourceWriter.println("super();");
    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * This method generates the source code for a public method or constructor including the opening brace and
   * indentation.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param methodName is the name of the method (or the {@link Class#getSimpleName() simple class name} for a
   *        constructor}.
   */
  protected final void generateSourcePublicConstructorDeclaration(SourceWriter sourceWriter, String methodName) {

    generateSourcePublicMethodDeclaration(sourceWriter, "", methodName, "", false);
  }

  /**
   * This method generates the source code for a public method declaration including the opening brace and
   * indentation.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param method is the {@link JMethod} to implement.
   */
  protected final void generateSourcePublicMethodDeclaration(SourceWriter sourceWriter, JMethod method) {

    StringBuilder arguments = new StringBuilder();
    for (JParameter parameter : method.getParameters()) {
      if (arguments.length() > 0) {
        arguments.append(", ");
      }
      arguments.append(parameter.getType().getQualifiedSourceName());
      arguments.append(" ");
      arguments.append(parameter.getName());
    }
    generateSourcePublicMethodDeclaration(sourceWriter, method.getReturnType().getQualifiedSourceName(),
        method.getName(), arguments.toString(), false);
  }

  /**
   * This method generates the source code for a public method or constructor including the opening brace and
   * indentation.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param returnType is the return type of the method.
   * @param methodName is the name of the method (or the {@link Class#getSimpleName() simple class name} for a
   *        constructor}.
   * @param arguments is the source line with the arguments to the method or constructor.
   * @param override - <code>true</code> if an {@link Override} annotation shall be added.
   */
  protected final void generateSourcePublicMethodDeclaration(SourceWriter sourceWriter, String returnType,
      String methodName, String arguments, boolean override) {

    if (override) {
      sourceWriter.println("@Override");
    }
    sourceWriter.print("public ");
    if (returnType != null) {
      sourceWriter.print(returnType);
      sourceWriter.print(" ");
    }
    sourceWriter.print(methodName);
    sourceWriter.print("(");
    sourceWriter.print(arguments);
    sourceWriter.println(") {");
    sourceWriter.indent();
  }

  /**
   * This method generates the source code to close a block (method body, if-block, while-block, etc.).
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   */
  protected final void generateSourceCloseBlock(SourceWriter sourceWriter) {

    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.println();
  }

  /**
   * This method determines whether a {@link CachedGeneratorResult} is obsolete or can be reused.
   * 
   * @param cachedGeneratorResult is the {@link CachedGeneratorResult}.
   * @param typeName is the full-qualified name of the {@link Class} to generate.
   * @return <code>true</code> if the {@link CachedGeneratorResult} is obsolete and has to be re-generated,
   *         <code>false</code> otherwise (if it can be reused).
   */
  protected boolean isCachedResultObsolete(CachedGeneratorResult cachedGeneratorResult, String typeName) {

    try {
      URL javaFileUrl = Thread.currentThread().getContextClassLoader()
          .getResource(typeName.replace('.', '/') + ".java");
      String protocol = javaFileUrl.getProtocol().toLowerCase();
      if ("file".equals(protocol)) {
        String urlString = URLDecoder.decode(javaFileUrl.getFile(), "UTF-8");
        File javaFile = new File(urlString);
        long lastModified = javaFile.lastModified();
        long timeGenerated = cachedGeneratorResult.getTimeGenerated();
        return (lastModified > timeGenerated);
      } else {
        throw new IllegalCaseException(protocol);
      }
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

}
