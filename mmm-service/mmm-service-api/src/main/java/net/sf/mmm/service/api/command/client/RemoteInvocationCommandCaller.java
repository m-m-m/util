/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.command.client;

import net.sf.mmm.service.api.client.RemoteInvocationCaller;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the key component to invoke one or multiple
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}s from the client-side. The most simple usage
 * is given in the following example:
 * 
 * <pre>
 * {@link RemoteInvocationCommandCaller} caller = getCommandCaller();
 * MyCommand command = new MyCommand(payload);
 * caller.{@link RemoteInvocationCommandCaller#callCommand(net.sf.mmm.service.api.command.RemoteInvocationCommand,
 * java.util.function.Consumer, java.util.function.Consumer)
 * callCommand}(command, this::onSuccess, this::onFailure);
 * </pre>
 * 
 * However, there are advanced features such as queuing command calls and send them in a single technical
 * request. Therefore you should use {@link RemoteInvocationCommandQueue} as illustrated by this example:
 * 
 * <pre>
 * {@link RemoteInvocationCommandCaller} caller = getCommandCaller();
 * {@link RemoteInvocationCommandQueue} queue = caller.{@link #newQueue()};
 * MyCommand command = new MyCommand(payload);
 * queue.{@link RemoteInvocationCommandQueue#setDefaultFailureCallback(java.util.function.Consumer) setDefaultFailureCallback}(this::onFailure);
 * queue.{@link RemoteInvocationCommandQueue#callCommand(net.sf.mmm.service.api.command.RemoteInvocationCommand, java.util.function.Consumer)
 * callCommand}(command, this::onSuccess);
 * ...
 * queue.{@link RemoteInvocationCommandQueue#commit() commit()};
 * </pre>
 * 
 * This might look a little complex on the first view, but it allows very powerful but quite easy usage of
 * asynchronous service invocations. In the example above a specific command is created that represents a
 * particular service invocation. This command is recorded by the {@link RemoteInvocationCommandQueue}. It
 * will be send to the server when the top-level queue is {@link RemoteInvocationCommandQueue#commit()
 * committed}. When the result is received from the server, it will be
 * {@link java.util.function.Consumer#accept(Object) passed} to the provided success callback function. <br/>
 * <b>NOTE:</b><br/>
 * If you are a java version less than 1.8, you can NOT use lambdas. However, we have created a backport for
 * {@link java.util.function} in <code>mmm-util-backport-java.util.function</code> that allows you to use this
 * code by providing {@link java.util.function.Consumer} instances via anonymous classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface RemoteInvocationCommandCaller extends AbstractRemoteInvocationCommandCaller,
    RemoteInvocationCaller<RemoteInvocationCommandQueue> {

  // nothing to add...

}
