/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to deal with (de)serialization.
 * <a name="documentation"/><h2>Util Serialization API</h2>
 * Java offers a seamless serialization mechanism that allows to serialize any
 * {@link java.io.Serializable serializable} object to binary data (<code>byte[]</code>) and vice versa.
 * However, there are alternative solutions to Java serialization that offer some advantages like
 * transparency by human-readable data (e.g. as JSON) or better performance (e.g. by (generated) custom code).
 * Therefore we offer a minimal abstraction of serialization via the
 * {@link net.sf.mmm.util.serialization.api.Serializer} interface.
 */
package net.sf.mmm.util.serialization.api;

