/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains base-implementation of component-util.
 * <h2>Component-Util Base</h2>
 * Provides {@link net.sf.mmm.util.component.base.InitializationState}
 * that can be used to implement a component that requires strict
 * {@link javax.annotation.PostConstruct initialization} and wants to ensure 
 * that some methods have to be called before and others after initialization.<br>
 * For components of this project that do NOT have to inherit from anything else,
 * this package offers {@link AbstractComponent} and {@link AbstractLoggable} to
 * make writing components easier.
 */
package net.sf.mmm.util.component.base;

