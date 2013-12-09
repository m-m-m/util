/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the available strategies for caching instances of a {@link DataClass}. For choosing the
 * proper strategy please consider the maximum amount of instances and the changeability of the instances.<br/>
 * E.g. entities with a fixed number of less than 100 instances that never change are perfect candidates for
 * {@link #FULLY_IMUTABLE strong} caching.
 * 
 * @see DataClass#getCachingStrategy()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum DataClassCachingStrategy implements SimpleDatatype<String> {

  /**
   * Strategy to cache all instances of the according {@link DataClass} in memory both on server AND on client
   * side for fast access. No updates are performed to the cache so the application has to be restarted if
   * instances have changed or have been added.<br/>
   * <b>ATTENTION:</b><br/>
   * Only use this strategy if you have a low and fixed number of immutable instances for your
   * {@link DataClass}. A typical example is an entity for the countries of the world.
   */
  FULLY_IMUTABLE("FI", "Fully-immutable"),

  /**
   * Strategy to cache all instances of the according {@link DataClass} in memory both on server AND on client
   * side for fast access. Instances are checked for updates frequently on server side via
   * {@link net.sf.mmm.data.api.DataObjectView#getModificationTimestamp() modification timestamp}. On change
   * the server cache is updated and clients will receive updates on next request.<br/>
   * <b>ATTENTION:</b><br/>
   * Only use this strategy if you have a low and limited number of instances for your {@link DataClass}.
   * Typical examples are lists of classifiers, tags, or genres.
   */
  FULLY_MUTABLE("FM", "Fully-mutable"),

  /**
   * Strategy to cache instances of the according {@link DataClass} in memory both on server AND on client
   * side for fast access. Instances are checked for updates frequently on server side via
   * {@link net.sf.mmm.data.api.DataObjectView#getModificationTimestamp() modification timestamp}. On change
   * the according entries are invalidated from server cache and clients will receive updates on next request.<br/>
   */
  DEFAULT("D", "default"),

  /**
   * Strategy to cache instances of the according {@link DataClass} in memory on server only. The client will
   * retrieve according data whenever required in a new request.
   */
  SERVER("S", "Server"),

  /**
   * Never cache any instance of the according {@link DataClass}.
   */
  NONE("N", "none");

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #getTitle()}.
   */
  private DataClassCachingStrategy(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
