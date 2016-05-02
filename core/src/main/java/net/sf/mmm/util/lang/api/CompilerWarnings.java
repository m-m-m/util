/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This type contains the available string constants for the {@link SuppressWarnings} annotation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface CompilerWarnings {

  /** {@link SuppressWarnings Suppress} all warnings. */
  String ALL = "all";

  /** {@link SuppressWarnings Suppress} warnings about missing, wrong or incomplete javadoc. */
  String JAVADOC = "javadoc";

  /** {@link SuppressWarnings Suppress} warnings about types with generics used raw. */
  String RAWTYPES = "rawtypes";

  /** {@link SuppressWarnings Suppress} warnings about unchecked generic conversions. */
  String UNCHECKED = "unchecked";

  /** {@link SuppressWarnings Suppress} warnings about usage of deprecated code. */
  String DEPRECATION = "deprecation";

}
