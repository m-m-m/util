/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

/**
 * This class represents an argument list. It is a container for an {@link java.lang.Object} array and can be used as
 * {@link java.util.Map#get(java.lang.Object) hash-key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class Arguments {

  /** the void signature for a non-arg method */
  public static final Arguments NO_ARGS = new Arguments(ReflectionUtilLimited.NO_ARGUMENTS);

  /** the wrapped signature */
  private final Object[] args;

  /** bleeding edge performance hack */
  private final int hash;

  /**
   * The constructor.
   * 
   * @param arguments are the signature to wrap.
   */
  public Arguments(Object... arguments) {

    super();
    this.args = arguments;
    int hashcode = 0;
    for (int i = 0; i < this.args.length; i++) {
      if (this.args[i] != null) {
        hashcode = (31 * hashcode) + this.args[i].hashCode();
      }
    }
    hashcode = hashcode << 2;
    hashcode = hashcode | this.args.length;
    this.hash = hashcode;
  }

  /**
   * This method gets the number of arguments.
   * 
   * @return the type count.
   */
  public int getArgumentCount() {

    return this.args.length;
  }

  /**
   * This method gets the argument at the given <code>position</code>.
   * 
   * @param position is the index of the requested type. This value must be in the range from <code>0</code> to
   *        <code>{@link #getArgumentCount()} - 1</code>.
   * @return the argument at the given index.
   */
  public Object getArgument(int position) {

    return this.args[position];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return this.hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj != null) && (getClass() == obj.getClass())) {
      Arguments other = (Arguments) obj;
      if (this.args.length == other.args.length) {
        for (int i = 0; i < this.args.length; i++) {
          if (this.args[i] != other.args[i]) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append('[');
    if (this.args.length > 0) {
      result.append('<');
      result.append(this.args[0]);
      for (int i = 1; i < this.args.length; i++) {
        result.append(">,<");
        result.append(this.args[i]);
      }
      result.append('>');
    }
    result.append(']');
    return result.toString();
  }

}
