/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.api.event;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.util.event.ChangeEvent;
import net.sf.mmm.util.event.ChangeEventType;

/**
 * This class represents an {@link net.sf.mmm.util.event.Event event} that
 * notifies a
 * {@link net.sf.mmm.configuration.api.event.ConfigurationChangeListener listener}
 * about a {@link net.sf.mmm.configuration.api.Configuration configuration} that
 * has changed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationChangeEvent implements ChangeEvent {

  /** @see #getType() */
  private final ChangeEventType type;

  /** @see #getCause() */
  private final Configuration cause;

  /** @see #getTopCause() */
  private final Configuration topCause;

  /**
   * The constructor.
   * 
   * @param changedConfiguration is the {@link #getCause() configuration} that
   *        has been added, removed or updated.
   * @param changeType is the {@link #getType() type} of the change.
   * @param topConfiguration is the {@link #getTopCause() top-cause} of this
   *        event.
   */
  public ConfigurationChangeEvent(Configuration changedConfiguration, ChangeEventType changeType,
      Configuration topConfiguration) {

    super();
    this.type = changeType;
    this.cause = changedConfiguration;
    this.topCause = topConfiguration;
    assert (this.type == ChangeEventType.UPDATE) ? (this.cause == this.topCause) : (this.cause
        .getAncestorDistance(this.topCause) == 1);
  }

  /**
   * This method gets the type of this event. An {@link ChangeEventType#UPDATE update}
   * means that the {@link Configuration#getValue() value} of the
   * {@link #getCause() changed configuration} has been modified.<br>
   * An {@link ChangeEventType#ADD add} or {@link ChangeEventType#REMOVE remove} means that the
   * {@link #getCause() changed configuration} has been added or removed from
   * the {@link #getTopCause()}.
   * 
   * @see net.sf.mmm.util.event.ChangeEvent#getType()
   */
  public ChangeEventType getType() {

    return this.type;
  }

  /**
   * This method gets the configuration that has {@link #getType() changed}
   * (been added, removed or updated).
   * 
   * @return the changed configuration.
   */
  public Configuration getCause() {

    return this.cause;
  }

  /**
   * This method gets the top-level configuration affected by the change. This
   * will be the parent configuration where the
   * {@link #getCause() changed configuration} has been {@link ChangeEventType#ADD added}
   * or {@link ChangeEventType#REMOVE removed} or the
   * {@link #getCause() changed configuration} itself in case of an
   * {@link ChangeEventType#UPDATE update}.
   * 
   * @return the top-level configuration affected by the change.
   */
  public Configuration getTopCause() {

    return this.topCause;
  }

}
