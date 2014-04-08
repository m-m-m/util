/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.command;

import net.sf.mmm.service.api.RemoteInvocationCall;

/**
 * This is the interface for a command that represents a specific operation of a
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}. It may be used as an alternative to
 * {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} via
 * {@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller}.
 *
 * @param <RESULT> is the generic type of the result of the operation represented by this command. Has to be
 *        {@link java.io.Serializable} but is not enforced via generic to support interfaces that do not
 *        extend {@link java.io.Serializable} like {@link java.util.List}. See
 *        {@link RemoteInvocationCommandHandler#handle(RemoteInvocationCommand)}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationCommand<RESULT /* extends Serializable */> extends RemoteInvocationCall {

  // nothing to add, just a marker interface...

}
