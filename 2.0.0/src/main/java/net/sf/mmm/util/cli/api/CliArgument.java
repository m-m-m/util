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
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliArgument {

  /**
   * Symbolic name used for {@link #addCloseTo()} to identify the first
   * argument.
   */
  String ID_FIRST = "#first";

  /**
   * Symbolic name used for {@link #addCloseTo()} to identify the last argument.
   */
  String ID_LAST = "#last";

  /**
   * The ID of the argument used to identify the argument e.g. via
   * {@link #addCloseTo()}. The default is the empty string that is treated as
   * if the ID is set to the same value as {@link #name()}.
   */
  String id() default "";

  /**
   * The name of the argument used for help usage output.
   */
  String name();

  /**
   * Determines if this {@link CliArgument} should be add after (
   * <code>true</code>) or before (<code>false</code>) the {@link CliArgument
   * argument} identified by {@link #addCloseTo()}.
   */
  boolean addAfter() default true;

  /**
   * The {@link #name() name} of the argument where to add this
   * {@link CliArgument} in the list of arguments. The default is
   * {@link #ID_LAST}.
   * 
   * @see #ID_FIRST
   * @see #ID_LAST
   */
  String addCloseTo() default CliArgument.ID_LAST;

  /**
   * The mode of this argument.
   * 
   * @see CliOption#mode()
   */
  String mode() default CliMode.ID_DEFAULT;

  /**
   * The description of this argument for
   * {@link CliParser#printHelp(Appendable) help usage}. It should be an
   * {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   * internationalized message} that will be localized using
   * {@link net.sf.mmm.util.nls.api.NlsMessage}.
   * {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   * NLS-arguments} are "default" for the default value.
   */
  String usage();

  /**
   * The flag that indicates if this argument is required. The default value is
   * <code>true</code>. Use a value of <code>false</code> to make this argument
   * optional within it's {@link #mode() mode}.<br>
   * <b>ATTENTION:</b><br>
   * Avoid optional arguments and use {@link CliOption options} instead. If you
   * use optional arguments anyway, please note that potentially following
   * options also need to be optional then.
   * 
   * @see CliMode#parentIds()
   */
  boolean required() default true;

  /**
   * The {@link CliContainerStyle style} of this argument if it has a container
   * type. The default is {@link CliContainerStyle#DEFAULT}.
   * 
   * @see CliStyle#containerStyle()
   */
  CliContainerStyle containerStyle() default CliContainerStyle.DEFAULT;

}
