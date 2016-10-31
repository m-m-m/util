/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

/**
 * This is the interface that a {@link Bean} may implement when providing a {@link #hash() custom implementation} of
 * {@link #hashCode()}. <br/>
 * <b>Note:</b></br>
 * It is not technically required to implement this interface in your {@link Bean}. It is already sufficient if you
 * proved a default method with the same signature as {@link #hash()}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface CustomHashCode {

  /**
   * Override with default method implementation in your interface to provide custom implementation of
   * {@link #hashCode()}.
   *
   * @return the {@link #hashCode()} of this object.
   */
  int hash();

}
