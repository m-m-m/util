/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.component.api.InitializationState;

/**
 * This is a container for a {@link CliMode} together with additional associated information.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliModeContainer implements CliModeObject {

  private  final String id;

  private  final CliMode mode;

  private  final Class<?> annotatedClass;

  private  final Set<CliModeContainer> extendedModes;

  private  InitializationState state;

  /**
   * The constructor for a dummy instance.
   *
   * @param id is the {@link #getId() ID}.
   */
  public CliModeContainer(String id) {

    this(null, null, id);
  }

  /**
   * The constructor.
   *
   * @param mode is the {@link #getMode() mode}.
   * @param annotatedClass is the {@link #getAnnotatedClass() annotated class}.
   */
  public CliModeContainer(CliMode mode, Class<?> annotatedClass) {

    this(mode, annotatedClass, mode.id());
  }

  /**
   * The constructor.
   *
   * @param mode is the {@link #getMode() mode}.
   * @param annotatedClass is the {@link #getAnnotatedClass() annotated class}.
   * @param id is the {@link #getId() ID}.
   */
  private CliModeContainer(CliMode mode, Class<?> annotatedClass, String id) {

    super();
    this.mode = mode;
    this.id = id;
    this.annotatedClass = annotatedClass;
    this.extendedModes = new HashSet<>();
    this.extendedModes.add(this);
    this.state = InitializationState.UNINITIALIZED;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public String getTitle() {

    if (this.mode != null) {
      String title = this.mode.title();
      if (title.length() > 0) {
        return title;
      }
    }
    return this.id;
  }

  @Override
  public CliMode getMode() {

    return this.mode;
  }

  @Override
  public Class<?> getAnnotatedClass() {

    return this.annotatedClass;
  }

  /**
   * This method gets the {@link Set} of {@link CliModeObject modes} that are {@link CliMode#parentIds() extended} by
   * this {@link #getMode() mode}.
   *
   * @return the extended {@link CliModeObject modes} including this {@link CliModeObject mode} itself.
   */
  public Set<CliModeContainer> getExtendedModes() {

    return this.extendedModes;
  }

  @Override
  public boolean isAncestorOf(CliModeObject childMode) {

    if (childMode != this) {
      return this.extendedModes.contains(childMode);
    }
    return false;
  }

  @Override
  public boolean isDescendantOf(CliModeObject parentMode) {

    return parentMode.isAncestorOf(this);
  }

  /**
   * @return the state
   */
  public InitializationState getState() {

    return this.state;
  }

  /**
   * @param state is the state to set
   */
  public void setState(InitializationState state) {

    this.state = state;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (this.annotatedClass != null) {
      sb.append(this.annotatedClass.getSimpleName());
    }
    sb.append('@');
    sb.append(CliMode.class.getSimpleName());
    sb.append("(id=\"");
    sb.append(this.id);
    sb.append("\")");
    return sb.toString();
  }

}
