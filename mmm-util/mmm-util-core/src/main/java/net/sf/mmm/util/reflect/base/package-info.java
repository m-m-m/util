/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the base-implementations of the 
 * {@link net.sf.mmm.util.reflect.api Reflection-Util API}.
 * <a name="documentation"/><h2>Reflection-Util Base</h2>
 * This package provides simple implementations of the 
 * {@link net.sf.mmm.util.reflect.api.ClassResolver} interface that can be used
 * by end-users.<br>
 * The {@link net.sf.mmm.util.reflect.base.AbstractGenericType} is an abstract
 * base-implementation of the {@link net.sf.mmm.util.reflect.api.GenericType} 
 * interface. However end-users should create instances of 
 * {@link net.sf.mmm.util.reflect.api.GenericType} via 
 * {@link net.sf.mmm.util.reflect.api.ReflectionUtil}.
 * Further this package provides {@link net.sf.mmm.util.filter.api.Filter}s for 
 * end-users such as {@link net.sf.mmm.util.reflect.base.AnnotationFilter} and 
 * {@link net.sf.mmm.util.reflect.base.AssignableFromFilter} that only 
 * {@link net.sf.mmm.util.filter.api.Filter#accept(Object) accept}  
 * {@link java.lang.Class}es with specific reflective attributes.
 */
package net.sf.mmm.util.reflect.base;

