/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.rebind;

import java.io.PrintWriter;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.impl.AbstractNlsBundleFactoryGwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the GWT {@link Generator} for generation of the true
 * {@link net.sf.mmm.util.nls.api.NlsBundleFactory} implementation as well as according {@link NlsBundle}
 * implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleFactoryGenerator extends Generator {

  /**
   * The constructor.
   */
  public NlsBundleFactoryGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

    String packageName = AbstractNlsBundleFactoryGwt.class.getPackage().getName();
    String simpleName = "NlsBundleFactoryGwtImpl";
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);
    // import statements
    sourceComposerFactory.addImport(AbstractNlsBundleFactoryGwt.class.getName());
    sourceComposerFactory.addImport(GWT.class.getName());
    sourceComposerFactory.addImport(NlsBundle.class.getName());

    sourceComposerFactory.setSuperclass(AbstractNlsBundleFactoryGwt.class.getSimpleName());
    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      generateConstructor(sourceWriter, simpleName);

      generateMethodCreateBundle(sourceWriter, logger, context);

      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * Generates the constructor.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param simpleName is the {@link Class#getSimpleName() simple name}.
   */
  protected void generateConstructor(SourceWriter sourceWriter, String simpleName) {

    sourceWriter.print("public ");
    sourceWriter.print(simpleName);
    sourceWriter.println("() {");
    sourceWriter.indent();
    sourceWriter.println("super();");
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.println();
  }

  /**
   * Generates the <code>createBundle</code> method.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   */
  protected void generateMethodCreateBundle(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context) {

    // method declaration
    sourceWriter.print("public <BUNDLE extends ");
    sourceWriter.print(NlsBundle.class.getSimpleName());
    sourceWriter.println("> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {");
    sourceWriter.indent();

    // method body
    sourceWriter.println("BUNDLE bundle = getBundle(bundleInterface);");
    sourceWriter.println("if (bundle == null) {");
    sourceWriter.indent();
    // create and register
    generateBlockBundleCreation(sourceWriter, logger, context);
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("return bundle;");

    // end method...
    sourceWriter.outdent();
    sourceWriter.println("}");

  }

  /**
   * Generates the block that creates the {@link NlsBundle} instance lazily via {@link GWT#create(Class)}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   */
  protected void generateBlockBundleCreation(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context) {

    // find all subclasses of NlsBundle
    TypeOracle typeOracle = context.getTypeOracle();
    JClassType bundleClass = typeOracle.findType(NlsBundle.class.getName());
    JClassType[] types = typeOracle.getTypes();
    int bundleCount = 0;
    logger.log(Type.INFO, "Checking " + types.length + " types...");
    for (JClassType type : types) {
      if ((type.isAssignableTo(bundleClass)) && (!type.equals(bundleClass))) {
        logger.log(Type.INFO, "Found NlsBundle interface: " + type);

        sourceWriter.print("if (");
        sourceWriter.print(type.getQualifiedSourceName());
        sourceWriter.println(".class == bundleInterface) {");
        sourceWriter.indent();

        sourceWriter.print("bundle = GWT.create(");
        sourceWriter.print(type.getQualifiedSourceName());
        sourceWriter.println(".class);");

        sourceWriter.println("register(bundleInterface, bundle);");

        sourceWriter.outdent();
        sourceWriter.print("} else ");

        bundleCount++;
      }
    }
    sourceWriter.println("{");
    sourceWriter.indent();

    sourceWriter.print("throw new ");
    sourceWriter.print(IllegalStateException.class.getSimpleName());
    sourceWriter.println("(\"Undefined NlsBundle \" + bundleInterface.getName());");

    sourceWriter.outdent();
    sourceWriter.println("}");

    logger.log(Type.INFO, "Found " + bundleCount + " NlsBundle interface(s).");
  }

}
