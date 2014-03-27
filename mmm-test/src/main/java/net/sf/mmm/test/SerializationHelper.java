/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class SerializationHelper {

  /**
   * Construction forbidden.
   */
  private SerializationHelper() {

    super();
  }

  /**
   * Method to {@link #serialize(Serializable)} and {@link #deserialize(byte[])} an {@link Object} to create a
   * clone and test serialization.
   * 
   * @param <S> is the generic type of the <code>serializable</code> {@link Object}.
   * @param serializable is the {@link Object} to {@link #serialize(Serializable) serialize}.
   * @return the {@link #deserialize(byte[]) de-serialized} clone.
   */
  @SuppressWarnings("unchecked")
  public static <S extends Serializable> S reserialize(S serializable) {

    byte[] data = serialize(serializable);
    return (S) deserialize(data);
  }

  /**
   * @param serializedObject is a serialized {@link Object} as byte-array.
   * @return the de-serialized {@link Object}.
   */
  public static Object deserialize(byte[] serializedObject) {

    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(serializedObject);
      ObjectInputStream ois = new ObjectInputStream(bais);
      Object result = ois.readObject();
      ois.close();
      return result;
    } catch (Exception e) {
      throw new RuntimeException("Failed to deserialize!", e);
    }
  }

  /**
   * @param serializable is the {@link Serializable} {@link Object} to serialize.
   * @return the given <code>serializable</code> as byte-array.
   */
  public static byte[] serialize(Serializable serializable) {

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(serializable);
      oos.close();
      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Failed to serialize!", e);
    }
  }

}
