/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is a container for a {@link CliOption} together with additional associated information.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionContainer extends CliParameterContainer {

  /** @see #getOption() */
  private final CliOption option;

  /**
   * The constructor.
   *
   * @param option is the {@link #getOption() option}.
   * @param setter is the {@link #getSetter() setter}.
   * @param getter is the {@link #getGetter() getter}.
   * @param validator is the {@link #getValidator() validator}.
   */
  public CliOptionContainer(CliOption option, PojoPropertyAccessorOneArg setter, PojoPropertyAccessorNonArg getter,
      ValueValidator<?> validator) {

    super(setter, getter, validator);
    this.option = option;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getName() {

    return this.option.name();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Annotation getParameterAnnotation() {

    return this.option;
  }

  /**
   * This method gets the actual {@link CliOption}.
   *
   * @return the {@link CliOption}.
   */
  public CliOption getOption() {

    return this.option;
  }

  /**
   * This method determines if the {@link #getOption() option} is a trigger. Trigger means that the type of
   * the {@link #getSetter() setter} is a primitive boolean and the option is never followed by a value on the
   * commandline.
   *
   * @return <code>true</code> if trigger, <code>false</code> otherwise.
   */
  public boolean isTrigger() {

    return boolean.class.equals(getSetter().getPropertyClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CliContainerStyle getContainerStyle(CliStyle cliStyle) {

    CliContainerStyle style = this.option.containerStyle();
    if (style == CliContainerStyle.DEFAULT) {
      style = cliStyle.containerStyle();
    }
    return style;
  }

}
