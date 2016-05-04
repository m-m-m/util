/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface used to {@link #build(Object) build} a {@link CliParser} from a given object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface CliParserBuilder {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.cli.api.CliParserBuilder";

  /**
   * This method builds a {@link CliParser} instance from the given {@link net.sf.mmm.util.pojo.api.Pojo}. The following
   * applies for the class reflecting this pojo:
   * <ul>
   * <li>it needs to have (declared or inherited) fields annotated with {@link CliOption} and / or {@link CliArgument}.
   * </li>
   * <li>It's class or one of it's super-classes may also be annotated with {@link CliClass}.</li>
   * <li>It is recommended to use the {@link net.sf.mmm.util.cli.api.AbstractMain main-program} itself, but it can be
   * any {@link net.sf.mmm.util.pojo.api.Pojo}.</li>
   * </ul>
   *
   *
   * @param pojo is the annotated object for the CLI-state.
   * @return the {@link CliParser} used to {@link CliParser#parseParameters(String...) parse} the commandline-arguments
   *         and potentially {@link CliParser#printHelp(Appendable) print the help}.
   */
  CliParser build(Object pojo);

}
