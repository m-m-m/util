/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

import java.io.Serializable;

/**
 * This is the marker interface for a single remote invocation. For RPC style it is a
 * {@link java.lang.reflect.Method method} invocation of a
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}. For command style it is the
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface RemoteInvocationCall extends Serializable {

  // nothing to add...

}
