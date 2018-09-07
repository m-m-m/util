/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl.pojo1;

/**
 * This is some code only used for {@link net.sf.mmm.util.value.impl.ComposedValueConverterTest}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@SuppressWarnings("all")
public class SubPojoImpl implements SubPojo {

  private FooEnum foo;

  private int integer;

  private String string;

  @Override
  public FooEnum getFoo() {

    return this.foo;
  }

  public void setFoo(FooEnum foo) {

    this.foo = foo;
  }

  @Override
  public int getInteger() {

    return this.integer;
  }

  public void setInteger(int integer) {

    this.integer = integer;
  }

  @Override
  public String getString() {

    return this.string;
  }

  public void setString(String string) {

    this.string = string;
  }

}
