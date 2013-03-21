/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the key component to invoke one or multiple
 * {@link net.sf.mmm.service.api.RemoteInvocationService}s from the client-side. It should be used as
 * illustrated in the following example:
 * 
 * <pre>
 * {@link RemoteInvocationServiceCaller} caller = getServiceCaller();
 * {@link RemoteInvocationServiceQueue} queue = caller.{@link #newQueue()};
 * {@link RemoteInvocationServiceCallback}{@literal<MyResult>} callback = new {@link RemoteInvocationServiceCallback}{@literal<>}() {
 *   public void {@link RemoteInvocationServiceCallback#onSuccess(java.io.Serializable, boolean) onSuccess}(MyResult result, boolean complete) {
 *     handleResult(result);
 *   }
 *   public void {@link RemoteInvocationServiceCallback#onFailure(Throwable, boolean) onFailure}({@link Throwable} error, boolean complete) {
 *     handleError(error);
 *   }
 * };
 * MyService myService = queue.{@link RemoteInvocationServiceQueue#getServiceClient(Class, Class, RemoteInvocationServiceCallback) getServiceClient}(MyService.class, MyResult.class, callback);
 * myService.myMethod(myArgument1, myArgument2);
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
 * server, it will be passed to
 * {@link RemoteInvocationServiceCallback#onSuccess(java.io.Serializable, boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface RemoteInvocationServiceCaller {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.service.api.client.RemoteInvocationServiceCaller";

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
