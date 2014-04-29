/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.security;

/**
 * This is a rewrite of {@link java.security.Principal} to allow access in GWT clients.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Principal {

  /**
   * @return the name of this principal (e.g. the login of a user).
   */
  String getName();
}
