/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;
import java.util.Collection;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the generic transfer-object containing all {@link RemoteInvocationServiceCall invocation calls}
 * that shall be processed in a separate transaction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceTransactionalCalls implements Serializable, AttributeReadTitle<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 4382650741266686356L;

  /** @see #getCalls() */
  private RemoteInvocationServiceCall[] calls;

  /**
   * The constructor for (de)serialization.
   */
  protected RemoteInvocationServiceTransactionalCalls() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public RemoteInvocationServiceTransactionalCalls(RemoteInvocationServiceCall... calls) {

    super();
    this.calls = calls;
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public RemoteInvocationServiceTransactionalCalls(Collection<RemoteInvocationServiceCall> calls) {

    this(calls.toArray(new RemoteInvocationServiceCall[calls.size()]));
  }

  /**
   * @return an array with the {@link RemoteInvocationServiceCall}s (invocations).
   */
  public RemoteInvocationServiceCall[] getCalls() {

    return this.calls;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return getClass().getSimpleName() + "@" + this.calls.length;
  }

}
