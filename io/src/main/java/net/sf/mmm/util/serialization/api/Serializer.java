/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.serialization.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface to {@link #serialize(Object) serialize} and {@link #deserialize(byte[]) deserialize} arbitrary
 * Java objects. As a default implementation we offer {@link net.sf.mmm.util.serialization.base.JavaSerializer} but
 * there can be many alternative implementations like JSon, GWT RPC, etc. <br>
 * The call {@link #deserialize(byte[]) deserialize}({@link #serialize(Object) serialize}(o)) should have behave like
 * creating a deep-{@link Cloneable clone}. <br>
 * <br>
 * <b>ATTENTION:</b><br>
 * Not all objects can be {@link #serialize(Object) serialized}. By default they have to be compliant with
 * {@link java.io.Serializable}. However, specific implementations may have additional or alternate requirements. E.g.
 * for GWT (Google Web Toolkit) the object has to have a non-arg constructor (may be protected) and
 * <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=1054">shall not have final fields</a>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@ComponentSpecification
public interface Serializer {

  /**
   * Serializes (or marshals) the given {@link Object} to serialized data.
   *
   * @param object is the {@link Object} to serialize. May be {@code null}.
   * @return the serialized data (payload) representing the given {@link Object}. We use {@code byte[]} to allow
   *         arbitrary data including any binary format. However, this can also be a {@link String} encoded e.g. in
   *         UTF-8.
   */
  byte[] serialize(Object object);

  /**
   * De-serializes (or un-marshals) the given {@link #serialize(Object) serialized data} back to the corresponding
   * {@link Object}.
   *
   * @param data is the {@link #serialize(Object) serialized data}.
   * @return the
   */
  Object deserialize(byte[] data);

}
