/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.component.api.ComponentSpecification;

// CHECKSTYLE:OFF (javadoc)
/**
 * This is the interface for the key component to invoke one or multiple
 * {@link net.sf.mmm.service.api.RemoteInvocationService}s from the client-side. The most simple usage is
 * given in the following example:
 * 
 * <pre>
 * {@link RemoteInvocationServiceCaller} caller = getServiceCaller();
 * MyService myService = caller.{@link RemoteInvocationServiceCaller#getServiceClient(Class, Class, java.util.function.Consumer, java.util.function.Consumer)
 * getServiceClient}(MyService.class, MyResult.class, this::onSuccess, failure -> failure.printStackTrace());
 * myService.myMethod(myArgument1, myArgument2);
 * </pre>
 * 
 * However, there are advanced features such as queuing service calls and send them in a single technical
 * request. Therefore you should use {@link RemoteInvocationServiceQueue} as illustrated by this example:
 * 
 * <pre>
 * {@link RemoteInvocationServiceCaller} caller = getServiceCaller();
 * {@link RemoteInvocationServiceQueue} queue = caller.{@link #newQueue()};
 * MyService myService = queue.{@link RemoteInvocationServiceQueue#getServiceClient(Class, Class, java.util.function.Consumer, java.util.function.Consumer)
 * getServiceClient}(MyService.class, MyResult.class, this::onSuccess, this::onFailure);
 * myService.myMethod(myArgument1, myArgument2);
 * ...
 * queue.{@link RemoteInvocationServiceQueue#commit() commit()};
 * </pre>
 * 
 * This might look a little complex on the first view, but it allows very powerful but quite easy usage of
 * asynchronous service invocations. In the example above a client-stub <code>myService</code> for the
 * service-interface <code>MyService</code> is created. On this you can directly invoke the actual service
 * method, here <code>myMethod</code> with the according arguments as you would do for a synchronous
 * invocation. However, the method invocation will always return a dummy result (<code>null</code> for
 * non-primitive return types) that you should ignore. Instead the method invocation including its arguments
 * is recorded by the {@link RemoteInvocationServiceQueue}. It will be send to the server when the top-level
 * queue is {@link RemoteInvocationServiceQueue#commit() committed}. When the result is received from the
 * server, it will be {@link Consumer#accept(Object) passed to a callback function}. <br/>
 * <b>NOTE:</b><br/>
 * If you are a java version less than 1.8, you can NOT use lambdas. However, we have created a backport for
 * {@link java.util.function} in <code>mmm-util-backport-java.util.function</code> that allows you to use this
 * code by providing {@link Consumer} instances via anonymous classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// CHECKSTYLE:ON
@ComponentSpecification
public interface RemoteInvocationServiceCaller extends AbstractRemoteInvocationServiceCaller {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.service.api.client.RemoteInvocationServiceCaller";

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This is a convenience method for simplicity. It will create an implicit
   * {@link RemoteInvocationServiceQueue queue} that gets {@link RemoteInvocationServiceQueue#commit()
   * committed} automatically after a service method has been called on the returned client-stub.
   */
  @Override
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

  /**
   * This method gets the current {@link RemoteInvocationServiceQueue}. This is the last queue that has been
   * {@link #newQueue() created} and is still {@link RemoteInvocationServiceQueue#isOpen() open}.<br/>
   * <b>NOTE:</b><br/>
   * This method is only for specific scenarios. You should typically use {@link #newQueue()} instead, to
   * retrieve a queue and perform service invocations.
   * 
   * @return the current {@link RemoteInvocationServiceQueue} or <code>null</code> if no queue is open.
   */
  RemoteInvocationServiceQueue getCurrentQueue();

  /**
   * This method opens a {@link RemoteInvocationServiceQueue} that collects method-invocations on a
   * {@link net.sf.mmm.service.api.RemoteInvocationService}.
   * 
   * @see #newQueue(RemoteInvocationServiceQueueSettings)
   * 
   * @return the new {@link RemoteInvocationServiceQueue}.
   */
  RemoteInvocationServiceQueue newQueue();

  /**
   * This method opens a {@link RemoteInvocationServiceQueue} that collects method-invocations on a
   * {@link net.sf.mmm.service.api.RemoteInvocationService}.
   * 
   * @see #newQueue(RemoteInvocationServiceQueueSettings)
   * 
   * @param id is the {@link RemoteInvocationServiceQueue#getId() identifier} of the new
   *        {@link RemoteInvocationServiceQueue queue}.
   * @return the new {@link RemoteInvocationServiceQueue}.
   */
  RemoteInvocationServiceQueue newQueue(String id);

  /**
   * This method opens a {@link RemoteInvocationServiceQueue} that collects method-invocations on a
   * {@link net.sf.mmm.service.api.RemoteInvocationService}. If a {@link RemoteInvocationServiceQueue} is
   * {@link #getCurrentQueue() currently} {@link RemoteInvocationServiceQueue#isOpen() open} this method will
   * create a child-queue of that queue.
   * 
   * @param settings are the {@link RemoteInvocationServiceQueueSettings}.
   * @return the new {@link RemoteInvocationServiceQueue}.
   */
  RemoteInvocationServiceQueue newQueue(RemoteInvocationServiceQueueSettings settings);

}
