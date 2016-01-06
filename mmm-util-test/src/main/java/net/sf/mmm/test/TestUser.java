/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * This is a simple user for testing. It acts as {@link java.security.Principal} and {@link Authentication}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TestUser implements Authentication {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** The {@link #getName() name} of {@link #DEFAULT_USER}. */
  public static final String DEFAULT_NAME = "testuser";

  /** The default instance of {@link TestUser} with {@link #DEFAULT_NAME}. */
  public static final TestUser DEFAULT_USER = new TestUser(DEFAULT_NAME);

  /** @see #getName() */
  private final String name;

  /** @see #isAuthenticated() */
  private boolean authenticated;

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name} (or login) of the user.
   */
  public TestUser(String name) {

    super();
    this.name = name;
    this.authenticated = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    TestUser other = (TestUser) obj;
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return Collections.EMPTY_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getCredentials() {

    return "password";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getDetails() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getPrincipal() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAuthenticated() {

    return this.authenticated;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    this.authenticated = isAuthenticated;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.name;
  }

}
