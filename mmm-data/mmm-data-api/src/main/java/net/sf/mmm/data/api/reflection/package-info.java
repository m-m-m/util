/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for the reflection of {@link net.sf.mmm.data.api.DataObject
 * data-objects}.
 * <a name="documentation"/><h2>Data Reflection API</h2>
 * This package contains the API for the reflection of
 * {@link net.sf.mmm.data.api.DataObject data-objects}. The reflection is
 * provided in advance to the java reflection API for the following reasons:
 * <ul>
 *   <li><b>Dynamic Typing</b><br/>
 *   The {@link net.sf.mmm.data.api.entity.DataEntity} model also allows
 *   dynamic typing. In this case there are types not available as dedicated
 *   java-classes. Such types will not be available for java reflection.</li>
 *   <li><b>Migration</b><br/>
 *   This reflection API allows the assignment of unique IDs to classes and
 *   fields. If the {@link net.sf.mmm.data.api.entity.DataEntity} model has
 *   changed the current model can be compared with the persistent previous
 *   version and migration can be supported.</li>
 *   <li><b>Easier to use</b><br/>
 *   The java reflection API is very low level, complex and error prune.
 *   Instead, this API is designed to be more simple.</li>
 * </ul>
 *
 * A {@link net.sf.mmm.data.api.reflection.DataClass} represents a particular
 * {@link net.sf.mmm.data.api.DataObject} and gives generic access to
 * instances of this type. The main entry point to get started is the component
 * {@link net.sf.mmm.data.api.reflection.DataReflectionService}.
 */
package net.sf.mmm.data.api.reflection;

