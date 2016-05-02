/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.serialization.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.serialization.api.Serializer;

/**
 * This is the implementation of {@link Serializer} using the standard serialization mechanism of Java.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class JavaSerializer implements Serializer {

  /**
   * The constructor.
   */
  public JavaSerializer() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object deserialize(byte[] data) {

    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    try (ObjectInputStream ois = new ObjectInputStream(bais)) {
      return ois.readObject();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] serialize(Object object) {

    ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(object);
      oos.flush();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
    return baos.toByteArray();
  }

}
