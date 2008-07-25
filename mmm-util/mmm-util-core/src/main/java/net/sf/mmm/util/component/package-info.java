/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains useful classes for implementing components.
 * <h2>Component Utilities</h2>
 * With JSR 250 (commons-annotations) it is very easy to write components and
 * therefore component-oriented applications.<br>
 * This package provides some common runtime exceptions for typical errors
 * of components (or whatever you call it).<br>
 * Additionally {@link net.sf.mmm.util.component.base.InitializationState}
 * can be used to implement a component that requires strict
 * {@link javax.annotation.PostConstruct initialization} and wants to ensure 
 * that some methods have to be called before and others after initialization.
 */
package net.sf.mmm.util.component;

