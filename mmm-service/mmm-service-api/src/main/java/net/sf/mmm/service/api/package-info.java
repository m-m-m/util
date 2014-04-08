/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for simple remote invocations to communicate between client and server.
 * <a name="documentation"/><h2>Service API</h2>
 * This package and it sub-packages contain the API for remote invocations. The API (and implementations)
 * provide a solution for simple but flexible and powerful remote invocations with focus on asynchronous processing on
 * clients (e.g. web-clients using Google Web Toolkit, native JavaFx clients, etc.).
 * <h3>The Problem</h3>
 * In order to communicate between client and server there are various decisions to make and problems to solve:
 * <ul>
 * <li>Which communication protocol (e.g. REST, SOAP, etc.) and payload format (JSON, XML, BSON, hessian, etc.) should be used?
 * This will heavily depend on your client. In case you have multiple clients you might need to implement all your
 * services for different protocols.</li>
 * <li>How to establish RPC-style (Remote Procedure Call) services with REST? How to map a business oriented processing
 * operation to the REST scheme?</li>
 * <li>How to map from your raw java service implementation to the payload format (marshalling and unmarshalling)?</li>
 * <li>How to secure your services and protect against attacks such as CSRF? Regular REST is recommending submission of request parameters
 * for read operations as part of the URL (path or get-parameter) leading to unencrypted transmission even with HTTPS.</li>
 * <li>How to deal with exceptions? What about exposure of sensitive information via exceptions in production systems vs.
 * development environment? How to ensure that internal exceptions can be de-serialized/un-marshaled on the client
 * without dependency on the entire server code?</li>
 * <li>How coarse- or fine-grained should service operations be designed? Too find-grained methods may cause larger overhead
 * if multiple remote invocations (requests) have to be performed while too coarse-grained methods may prevent proper reusage.</li>
 * <li>How to deal with cross-cutting concerns (that may even arise after a lot of services already have been implemented?</li>
 * <li>How to deal with BLOBs? If we send them as <code>byte[]</code> you will end up with {@link java.lang.OutOfMemoryError}s
 * on the server side as all concurrent BLOBs are loaded entirely in main memory. Instead a streaming API like
 * {@link java.io.OutputStream} is required. But how should this work in a limited environment such as a GWT client?</li>
 * </ul>
 * For the different protocols and formats very technical solutions with a high flexibility exist.
 * For example with JAX-RS we get code like this:
 * <pre>
 * {@literal @Path}("/disposition/{dispositionId}/calculateTotal")
 * {@literal @POST}
 * {@literal @Consumes}("application/json")
 * {@literal @Produces}("application/json")
 * public Amount calculateTotal({@literal @PathParam("dispositionId")} Long dispositionId, State dispositionState);
 * </pre>
 * You can see that there are a lot of considerations to care about. Also such approach is leading to redundancies if
 * you have a large amount of services. Even worse, if your client is also written in Java the calling code is not
 * directly related to the service operation. You have to manually ensure consistency so everything fits and works together.
 * The latter is better with JAX-WS but SOAP is also causing a lot of problems and limitations.
 * Also during development and maintenance it is hard to trace calls from the client to the server side implementation.<br/>
 * Solutions like JAX-RS and JAX-WS are cool and reasonable for external and interoperable services consumed by third-party
 * vendors. However, for <em>internal services</em> (services dedicated to clients or other applications within the same
 * application landscape typically under control of the same vendor) this is causing a lot of unnecessary effort and problems.
 * Approaches like <code>spring-remoting</code> make your life a lot easier but still require you to configure every service
 * on server- and client-side. Also it does not address all the questions we listed above.
 * <h3>The Solution</h3>
 * In case you want to realize <em>internal services</em> then <code>mmm-service</code> will make your life a lot easier.
 * Technically it is based on existing solutions like <code>spring-remoting</code> (<code>HttpInvoker</code>, <code>hessian</code>),
 * GWT-RPC, and others. Conceptional it defines a generic service via which all remote invocations are send and that
 * has to be setup and configured only once. All services you define and implement are declared via
 * {@link net.sf.mmm.util.component.api.Cdi CDI} and therefore automatically registered on the server side.
 * This offers the following advantages:
 * <ul>
 * <li>Developers can simply concentrate on designing the service API and implementation. No extra effort is required to
 * export the service and make it available. Only requirements are that you inherit a marker interface and that all the
 * parameters and return types of your service API are {@link java.io.Serializable serializable} (If you want to support
 * GWT-RPC this has the additional implication that fields are not declared as final and a non-arg constructor is is
 * present - but does not have to be public).</li>
 * <li>If server and client are both written in Java (what is the focused scenario) you can directly call the service
 * API on the client. Everything remains traceable, type-safe, and participates in refactorings. The client only sees the
 * API and should not know the implementation.</li>
 * <li>Cross-cutting concerns like {@link net.sf.mmm.util.lang.api.concern.Security} are already build-in.
 * If you have both secured and public service operations, you can configure how this is detected.
 * By default JavaEE security annotations (JSR250) as well as <code>spring-security</code> are supported.</li>
 * <li>On the client-side you can create a {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue queue}
 * that collects invocations and allows to bundle them into a single request to save overhead and boost your performance.
 * The queuing of invocations can be configured independently of the code that actually triggers the remote invocations.</li>
 * <li>Services that have been created can be made available via different protocols without changing the services.
 * If you have multiple clients that require different protocols, you can configure your server to support these protocolls
 * in parallel without extra effort. On the client side you simply choose the proper implementation of this
 * <code>mmm-service</code> API for the desired protocol.</li>
 * <li>Exceptions are properly {@link net.sf.mmm.util.exception.api.ExceptionUtil#convertForClient(Throwable) "obfuscated"}
 * according to the executing environment.</li>
 * <li>Everything is designed in an open and flexible way. You can simply replace specific aspects. Also you can write
 * implementations of this API for additional protocols and payload formats.</li>
 * <li>Everything integrates seamless with <code>mmm-client</code> but can also be used standalone.</li>
 * </ul>
 * There are two supported styles available that you can choose from:
 * <ul>
 * <li><b>RPC style</b><br/>
 * Here you simply create an interface for your remote service that inherits from the marker interface
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}. Further, you create an server-side implementation for
 * that interface. If you are using {@link net.sf.mmm.util.component.api.Cdi CDI} you only have to annotate the
 * implementation with {@link javax.inject.Named} and that is all.<br/>
 * On the client side you will use {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} to
 * {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller#getServiceClient(Class, Class,
 * java.util.function.Consumer, java.util.function.Consumer) get a stub of your service interface}
 * while providing a callback that receives the result asynchronously and invoke a corresponding method on the stub.
 * This way you can use your favorite IDE to directly step from the client-side service call to the server-side service
 * implementation. For its simplicity and compliance with java service design this is the favorite approach.
 * Here is a simple (undocumented and stupid) example that will show "Hi John" in a popup window:
 * <pre>
 * public interface HelloWorldService extends {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} {
 *   String sayHi(String name);
 * }
 * {@literal @}{@link javax.inject.Named}
 * public class HelloWorldService extends AbstractRemoteInvocationService implements HelloWorldService {
 *   public String sayHi(String name) {
 *     return "Hi " + name;
 *   }
 * }
 * public class ClientComponent {
 *   public void doSomething() {
 *     {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} caller = getCaller();
 *     HelloWorldService service = caller.{@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller#getServiceClient(Class,
 *     Class, java.util.function.Consumer) getServiceClient}(HelloWorldService.class, String.class, message -> showPopup(message));
 *     service.sayHi("John");
 *   }
 * }
 * </pre>
 * </li>
 * <li><b>Command style</b><br/>
 * This approach is implementing the <em>command pattern</em>. Here you create implementations of the marker interface
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}s that represent a single service operation. This
 * corresponds to a method of a {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} in RPC style and is a
 * container for all method parameters. On the server-side you create a corresponding implementation of
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommandHandler}. If you are using {@link net.sf.mmm.util.component.api.Cdi CDI}
 * you only have to annotate the implementation with {@link javax.inject.Named} and that is all.<br/>
 * On the client side you will use {@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller} to
 * {@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller#callCommand(net.sf.mmm.service.api.command.RemoteInvocationCommand,
 * java.util.function.Consumer, java.util.function.Consumer) call a command}. This is entirely type-safe due to the
 * generic type {@literal <RESULT>} of {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}.
 * As a further advantage it allows better optimization of serialization (e.g. in GWT the compiler needs to know which
 * classes can be part of the invocation what can be any {@link java.io.Serializable} in RPC while in command style only
 * classes reachable from a {@link net.sf.mmm.service.api.command.RemoteInvocationCommand} are candidates). Finally it does
 * not require code generation (for client stubs) in GWT environment where no reflection (dynamic proxies) is available.
 * Here is a simple (undocumented and stupid) example that will show "Hi John" in a popup window:
 * <pre>
 * public class HelloWorldCommand implements {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}{@literal <String>} {
 *   private static final long serialVersionUID = 1L;
 *   private String name;
 *   public HelloWorldCommand(String name) {
 *     super();
 *     this.name = name;
 *   }
 *   public String getName() {
 *     return this.name;
 *   }
 * }
 * {@literal @}{@link javax.inject.Named}
 * public class HelloWorldCommandHandler implements {@link net.sf.mmm.service.api.command.RemoteInvocationCommandHandler}{@literal
 * <String, HelloWorldCommand>} {
 *   public String {@link net.sf.mmm.service.api.command.RemoteInvocationCommandHandler#handle(net.sf.mmm.service.api.command.RemoteInvocationCommand)
 *   handle}(HelloWorldCommand command) {
 *     return "Hi" + command.getName();
 *   }
 *   public Class{@literal <HelloWorldCommand>} {@link net.sf.mmm.service.api.command.RemoteInvocationCommandHandler#getCommandClass()} {
 *     return HelloWorldCommand.class;
 *   }
 * }
 * public class ClientComponent {
 *   public void doSomething() {
 *     {@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller} caller = getCaller();
 *     HelloWorldCommand command = new HelloWorldCommand("John");
 *     caller.{@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller#callCommand(
 *     net.sf.mmm.service.api.command.RemoteInvocationCommand, java.util.function.Consumer) callCommand}(command, message -> showPopup(message));
 *   }
 * }
 * </pre>
 * </li>
 * </ul>
 */
package net.sf.mmm.service.api;

