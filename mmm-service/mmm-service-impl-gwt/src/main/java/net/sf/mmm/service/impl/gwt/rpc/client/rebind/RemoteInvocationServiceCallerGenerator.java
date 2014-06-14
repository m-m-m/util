/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.gwt.rpc.client.rebind;

import java.io.PrintWriter;
import java.io.Serializable;

import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwtAsync;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.client.AbstractRemoteInvocationServiceClient;
import net.sf.mmm.service.impl.gwt.rpc.client.AbstractRemoteInvocationServiceCallerGwt;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the {@link AbstractIncrementalGenerator incremental GWT-generator} for generating the
 * {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} and according service-client stubs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille Split off separate Generator for Service-Stub!
public class RemoteInvocationServiceCallerGenerator extends AbstractIncrementalGenerator {

  /**
   * The constructor.
   */
  public RemoteInvocationServiceCallerGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getVersionId() {

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateImportStatements(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.addImport(RemoteInvocationService.class.getName());
    sourceComposerFactory.addImport(RemoteInvocationGenericServiceGwtAsync.class.getName());
    sourceComposerFactory.addImport(AbstractRemoteInvocationServiceCallerGwt.class.getName());
    // sourceComposerFactory.addImport(Inject.class.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassDeclaration(JClassType inputType, TreeLogger logger,
      ClassSourceFileComposerFactory sourceComposerFactory, GeneratorContext context) {

    sourceComposerFactory.setSuperclass(AbstractRemoteInvocationServiceCallerGwt.class.getSimpleName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void generateClassContents(JClassType inputType, TreeLogger logger, SourceWriter sourceWriter,
      String simpleName, GeneratorContext context) {

    generateSourcePublicConstructorDeclaration(sourceWriter, simpleName);

    // generate CDI constructor
    // sourceWriter.print("@");
    // sourceWriter.println(Inject.class.getSimpleName());
    // sourceWriter.print("public ");
    // sourceWriter.print(simpleName);
    // sourceWriter.print("(");
    // sourceWriter.print(RemoteInvocationGenericServiceGwtAsync.class.getSimpleName());
    // sourceWriter.print(" genericService");
    // sourceWriter.println(") {");
    // sourceWriter.indent();
    // sourceWriter.println("super(genericService);");

    // generate service clients and register in constructor...
    TypeOracle typeOracle = context.getTypeOracle();
    JClassType dabayServiceType = typeOracle.findType(RemoteInvocationService.class.getName());
    for (JClassType type : typeOracle.getTypes()) {
      if ((type.isAssignableTo(dabayServiceType)) && (!type.equals(dabayServiceType)) && (type.isInterface() != null)) {
        sourceWriter.print("registerService(");
        sourceWriter.print(type.getQualifiedSourceName());
        sourceWriter.print(".class, new ");
        sourceWriter.print(generateServiceClient(type, inputType.getPackage().getName(), logger, context));
        sourceWriter.println("());");
      }
    }
    generateSourceCloseBlock(sourceWriter);
  }

  /**
   * This method generates a service-client implementation of a {@link RemoteInvocationService}-interface
   * given by <code>serviceInterface</code>.
   * 
   * @param serviceInterface is the {@link RemoteInvocationService}-interface.
   * @param packageName is the {@link Package#getName() package name}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @return the qualified name of the generated class.
   */
  protected String generateServiceClient(JClassType serviceInterface, String packageName, TreeLogger logger,
      GeneratorContext context) {

    String simpleName = serviceInterface.getName() + "ClientImpl";
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);

    // imports
    sourceComposerFactory.addImport(RemoteInvocationService.class.getName());
    sourceComposerFactory.addImport(Serializable.class.getName());
    sourceComposerFactory.addImport(GenericRemoteInvocationRpcCall.class.getName());
    sourceComposerFactory.addImport(AbstractRemoteInvocationServiceClient.class.getName());

    sourceComposerFactory.addImplementedInterface(serviceInterface.getQualifiedSourceName());
    sourceComposerFactory.setSuperclass(AbstractRemoteInvocationServiceClient.class.getSimpleName());
    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);
      // generate constructor
      sourceWriter.print("public ");
      sourceWriter.print(simpleName);
      sourceWriter.println("() {");
      sourceWriter.indent();
      sourceWriter.println("super();");
      sourceWriter.outdent();
      sourceWriter.println("}");

      // generate service-interface methods to implement
      for (JMethod method : serviceInterface.getOverridableMethods()) {

        generateServiceClientMethod(serviceInterface, sourceWriter, method);
      }

      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * This method generates the implementation of a method for a service-client.
   * 
   * @param serviceInterface is the {@link RemoteInvocationService}-interface.
   * @param sourceWriter is the {@link SourceWriter}.
   * @param method is the {@link JMethod} to generate.
   */
  private void generateServiceClientMethod(JClassType serviceInterface, SourceWriter sourceWriter, JMethod method) {

    // generate method declaration...
    sourceWriter.print("public ");
    JType returnType = method.getReturnType();
    sourceWriter.print(returnType.getQualifiedSourceName());
    sourceWriter.print(" ");
    sourceWriter.print(method.getName());
    sourceWriter.print("(");
    String separator = "";
    JParameter[] parameters = method.getParameters();
    for (JParameter parameter : parameters) {
      if (separator.length() == 0) {
        separator = ", ";
      } else {
        sourceWriter.print(separator);
      }
      sourceWriter.print(parameter.getType().getQualifiedSourceName());
      sourceWriter.print(" ");
      sourceWriter.print(parameter.getName());
    }
    sourceWriter.println("){");

    // generate method body...
    sourceWriter.indent();

    // generate statement for argument array
    sourceWriter.print(Serializable.class.getSimpleName());
    sourceWriter.print("[] _arguments = new ");
    sourceWriter.print(Serializable.class.getSimpleName());
    sourceWriter.print("[");
    sourceWriter.print(Integer.toString(parameters.length));
    sourceWriter.println("];");

    String[] signatureArray = new String[parameters.length];
    // fill in arguments
    for (int i = 0; i < parameters.length; i++) {
      // assign argument statement
      JParameter parameter = parameters[i];
      sourceWriter.print("_arguments[");
      sourceWriter.print(Integer.toString(i));
      sourceWriter.print("] = ");
      sourceWriter.print(parameter.getName());
      sourceWriter.println(";");

      // assign argument type for signature
      signatureArray[i] = parameter.getType().getQualifiedSourceName();
    }

    // generate statement to create call
    sourceWriter.print(GenericRemoteInvocationRpcCall.class.getSimpleName());
    sourceWriter.print(" _call = new ");
    sourceWriter.print(GenericRemoteInvocationRpcCall.class.getSimpleName());
    sourceWriter.print("(");
    sourceWriter.print(serviceInterface.getQualifiedSourceName());
    sourceWriter.print(".class.getName(), \"");
    sourceWriter.print(method.getName());
    sourceWriter.print("\", ");
    sourceWriter.print(Integer.toString(GenericRemoteInvocationRpcCall.getSignature(signatureArray)));
    sourceWriter.println(", _arguments);");

    // add recorded call
    sourceWriter.print("addCall(_call, ");
    sourceWriter.print(returnType.getQualifiedSourceName());
    sourceWriter.println(".class);");

    // generate dummy return statement
    JPrimitiveType primitiveReturnType = returnType.isPrimitive();
    if (primitiveReturnType == null) {
      sourceWriter.println("return null;");
    } else {
      switch (primitiveReturnType) {
        case VOID:
          // nothing to return
          break;
        case BOOLEAN:
          sourceWriter.println("return false;");
          break;
        case BYTE:
        case DOUBLE:
        case FLOAT:
        case INT:
        case LONG:
        case SHORT:
          sourceWriter.println("return 0;");
          break;
        case CHAR:
          sourceWriter.println("return ' ';");
          break;
        default :
          throw new IllegalCaseException(JPrimitiveType.class, primitiveReturnType);
      }
    }
    sourceWriter.outdent();
    sourceWriter.println("}");
  }
}
