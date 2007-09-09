/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import java.util.Date;

import net.sf.mmm.content.security.api.ContentUser;

/**
 * This is the interface for a lock. It has a boolean state that is either
 * {@link #isLocked() locked} or not. It can only be {@link #lock() locked} by
 * one {@link #getOwner() user} at a time.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Lock {

  /**
   * This method determines if the object is currently {@link #lock() locked}.
   * 
   * @return <code>true</code> if {@link #lock() locked}, <code>false</code>
   *         otherwise.
   */
  boolean isLocked();

  /**
   * This method tries to acquire this lock for the currently active user. After
   * the successful call of this method it is ensured that the lock has been set
   * for you and {@link #isLocked()} will return <code>true</code>.
   * 
   * @throws LockException if the lock could NOT be acquired (e.g. it already
   *         belongs to another user or the operation failed for technical
   *         reasons).
   */
  void lock() throws LockException;

  /**
   * This method tries to acquire this lock for the currently active user. After
   * the successful call of this method it is ensured that the lock has been set
   * for you and {@link #isLocked()} will return <code>true</code>.
   * 
   * @param timeout is the delay the lock will last in milliseconds. After this
   *        time the lock will be {@link #unlock() released} automatically.
   * @throws LockException if the lock could NOT be acquired (it already belongs
   *         to another user).
   */
  void lock(long timeout) throws LockException;

  /**
   * This method tries to unlock this lock. After the successful call of this
   * method it is ensured that the lock has been set for you and
   * {@link #isLocked()} will return <code>false</code>.
   * 
   * @throws LockException if the lock could NOT be released (it belongs to
   *         another user and you do NOT have permission to unlock).
   */
  void unlock() throws LockException;

  /**
   * This method gets the release-date of this lock.
   * 
   * @see #lock(long)
   * 
   * @return the date when this lock will be released automatically or
   *         <code>null</code> if currently NOT {@link #isLocked() locked} or
   *         the lock was {@link #lock() acquired without timeout}.
   */
  Date getReleaseDate();

  /**
   * This method gets the owner of the lock.
   * 
   * @return the owner or <code>null</code> if NOT {@link #isLocked() locked}.
   */
  ContentUser getOwner();

}
