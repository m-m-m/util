/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

/**
 * A {@link PojoPropertyPath} represents a path to a simple property of a
 * {@link net.sf.mmm.util.pojo.api.Pojo}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0 (moved from 3.0.0)
 */
public interface PojoPropertyPath {

  /**
   * The character that separates the {@link #getSegment() segments} of a {@link #getPojoPath() PojoPath}. The
   * value ( {@value} ) will never change. It is NOT necessary to use this constant to construct a
   * {@link PojoPropertyPath}.
   */
  char SEPARATOR = '.';

  /**
   * This method gets the actual {@link net.sf.mmm.util.pojo.api.Pojo} path represented by this object. As an
   * example this method may return {@code "foo.bar.property"}.
   *
   * @return the actual {@link PojoPropertyPath}.
   */
  String getPojoPath();

  /**
   * This method gets the parent-path of this path. <br>
   * E.g. if this path represents {@code "foo.bar.property"} then this method would return {@code "foo.bar"}.
   *
   * @return the parent-path or {@code null} if this is the root-segment.
   */
  String getParentPath();

  /**
   * This method gets the last segment of this current {@link net.sf.mmm.util.pojo.api.Pojo} path. E.g. if
   * this path represents {@code "foo.bar.property"} then this method would return {@code "property"}.
   *
   * @return the last segment.
   */
  String getSegment();

}
