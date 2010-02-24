/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A {@link CliArgument} is used to annotate a property (member variable of some
 * class or setter-method) that should be set from a main-program via a
 * commandline argument. Unlike {@link CliOption} a {@link CliArgument} is NOT
 * triggered by its {@link #name() name} but is identified by its position. The
 * first commandline parameter that is no {@link CliOption option} or occurs
 * after the argument "--" is treated as the first argument and further
 * parameters can also only be {@link CliArgument arguments}. Additionally the
 * last {@link CliArgument} -property can have the type {@link java.util.List}
 * if a proper generic is used to declare the item-type.<br>
 * E.g. a program <code>Foo</code> may have one {@link CliArgument argument} of
 * type <code>{@link java.util.List}&lt;{@link java.io.File}&gt;</code> and you
 * invoke <code>Foo file1 file2 file3</code> then the property is set to a
 * {@link java.util.List} equivalent to
 * <code>{new File("file1"), new File("file2"), new File("file3")}</code>.<br>
 * <b>ATTENTION:</b><br>
 * Whenever possible prefer a {@link CliOption} over a {@link CliArgument}.
 * However having a single {@link CliArgument} is just fine. But having multiple
 * {@link CliArgument arguments} together in some {@link #mode() mode} can cause
 * maintenance trouble. This is only supported to avoid limitations (e.g. if you
 * want to implement something like "grep pattern [file]+") but NOT recommended.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliArgument {

  /** The maximum {@link #index()}. */
  Integer INDEX_MAX = Integer.valueOf(100);

  /** The minimum {@link #index()}. */
  Integer INDEX_MIN = Integer.valueOf(0);

  /**
   * The name of the argument used for help usage output.
   */
  String name();

  /**
   * The index of the argument relative to other {@link CliArgument arguments}
   * in the order of the command-line arguments. The value has to be in the
   * range from {@link #INDEX_MIN} to {@link #INDEX_MAX}. There should be no
   * other {@link CliArgument} with the same index and for each index from
   * {@link #INDEX_MIN} to this {@link #index() index} there should exist an
   * {@link CliArgument} annotation.
   */
  int index();

  /**
   * The mode of this argument.
   * 
   * @see CliOption#mode()
   */
  String mode() default CliMode.MODE_DEFAULT;

}
