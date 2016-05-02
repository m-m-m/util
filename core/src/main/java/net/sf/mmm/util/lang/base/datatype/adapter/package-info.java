/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides generic adapters to map datatypes for JPA, JSON, and JAXB.
 * <a name="documentation"></a><h2>Lang Base Datatype Adapter</h2>
 * This package contains classes to add support for custom {@link net.sf.mmm.util.lang.api.Datatype}s
 * in various technologies like JPA (hibernate), JSON (jackson) and JAXB. For your datatype
 * you only have to ensure support via {@link net.sf.mmm.util.lang.api.DatatypeDescriptorManager} what is
 * given out-of-the-box if you implement {@link net.sf.mmm.util.lang.api.SimpleDatatype}. For composed
 * {@link net.sf.mmm.util.lang.api.Datatype}s you can implement your own {@link net.sf.mmm.util.lang.api.DatatypeDescriptor}. <br>
 * The if you once configure the adapters provided here in your application, you have generic support for all of your datatypes.
 */
package net.sf.mmm.util.lang.base.datatype.adapter;

