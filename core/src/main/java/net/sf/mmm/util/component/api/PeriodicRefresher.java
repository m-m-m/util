/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This is the interface for a component that periodically {@link Refreshable#refresh() refreshes}
 * {@link #addRefreshable(Refreshable) registered} {@link Refreshable}s. The refresh of a {@link Refreshable}
 * will happen until it is {@link #removeRefreshable(Refreshable) de-registered}.
 *
 * @deprecated will be removed in some future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Deprecated
public interface PeriodicRefresher {

  /**
   * This method will register the given {@code refreshable} so it will be {@link Refreshable#refresh()
   * refreshed} periodically. <br>
   * A common implementation will use a central thread that is started if the first {@link Refreshable} is
   * added here. <br>
   * <b>ATTENTION:</b><br>
   * Please be careful with automatic refreshes and only add a {@link Refreshable} if this is really desired
   * and its implementation of {@link Refreshable#refresh()} is fast or only takes performance if something
   * that rarely changes has been modified. <br>
   * Further the given {@link Refreshable} has to have a proper implementation of
   * {@link Object#equals(Object)} and {@link Object#hashCode()} as it may be stored in a
   * {@link java.util.Collection}.
   *
   * @param refreshable is the {@link Refreshable} to register and refresh periodically.
   */
  void addRefreshable(Refreshable refreshable);

  /**
   * This method removes the given {@code refreshable} from this {@link PeriodicRefresher}. This will
   * typically happen when the according {@link Refreshable} shall be {@link java.io.Closeable#close() closed}
   * or disposed. <br>
   * If the given {@code searchEngine} has never been {@link #addRefreshable(Refreshable) registered}, this
   * method has no effect.
   *
   * @param refreshable is the {@link Refreshable} to de-register.
   */
  void removeRefreshable(Refreshable refreshable);

}
