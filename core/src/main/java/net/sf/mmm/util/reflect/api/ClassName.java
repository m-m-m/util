/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

/**
 * This is a simple identifier for a {@link Class}. Unlike {@link Class} it can also identify non existent classes (e.g.
 * that should be generated). On the other hand passing the {@link Class#getName() qualified classname} as
 * {@link String} around for such purpose may lead to additional parsing e.g. to extract the {@link #getSimpleName()
 * simple name}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ClassName {

  private final String packageName;

  private final ClassName enclosingClass;

  private final String simpleName;

  /**
   * The constructor.
   *
   * @param packageName - see {@link #getPackageName()}.
   * @param simpleName - see {@link #getSimpleName()}.
   */
  public ClassName(String packageName, String simpleName) {

    this(packageName, simpleName, null);
  }

  /**
   * The constructor.
   *
   * @param packageName - see {@link #getPackageName()}.
   * @param simpleName - see {@link #getSimpleName()}.
   * @param enclosingClass - see {@link #getEnclosingClass()}.
   */
  public ClassName(String packageName, String simpleName, ClassName enclosingClass) {

    super();
    this.packageName = packageName;
    this.enclosingClass = enclosingClass;
    this.simpleName = simpleName;
    if (this.enclosingClass != null) {
      assert (this.enclosingClass.packageName.equals(packageName));
    }
  }

  /**
   * The constructor.
   *
   * @param type is the {@link Class} to represent.
   */
  public ClassName(Class<?> type) {

    super();
    this.packageName = type.getPackage().getName();
    this.simpleName = type.getSimpleName();
    Class<?> enclosing = type.getEnclosingClass();
    if (enclosing == null) {
      this.enclosingClass = null;
    } else {
      this.enclosingClass = new ClassName(enclosing);
    }
  }

  /**
   * @return the {@link Package#getName() name} of the {@link Package} {@link Class#getPackage() of} the {@link Class}.
   */
  public String getPackageName() {

    return this.packageName;
  }

  /**
   * @return the {@link Class#getEnclosingClass() enclosing} {@link ClassName} or {@code null} if for a top-level
   *         {@link Class}.
   */
  public ClassName getEnclosingClass() {

    return this.enclosingClass;
  }

  /**
   * @return the {@link Class#getSimpleName() simple name} of the {@link Class}.
   */
  public String getSimpleName() {

    return this.simpleName;
  }

  /**
   * @return the {@link Class#getName() name} of the {@link Class}.
   */
  public String getName() {

    return getName('$');
  }

  /**
   * @return the {@link Class#getCanonicalName() canonical name} of the {@link Class}.
   */
  public String getCanonicalName() {

    return getName('.');
  }

  /**
   * @param enclosingSeparator the separator for enclosing types ('.' or '$').
   * @return the qualified name.
   */
  private String getName(char enclosingSeparator) {

    StringBuilder buffer = new StringBuilder(this.packageName);
    if (buffer.length() > 0) {
      buffer.append('.');
    }
    if (this.enclosingClass != null) {
      appendEnclosing(buffer, enclosingSeparator, this.enclosingClass);
    }
    buffer.append(this.simpleName);
    return buffer.toString();
  }

  /**
   * {@link StringBuilder#append(String) Appends} the {@link #getEnclosingClass() enclosing class} in proper order
   * (reverse hierarchy).
   *
   * @param buffer the {@link StringBuilder} to {@link StringBuilder#append(String) append} to.
   * @param enclosingSeparator the separator for enclosing types ('.' or '$').
   * @param enclosing the {@link #getEnclosingClass() enclosing class}.
   */
  private void appendEnclosing(StringBuilder buffer, char enclosingSeparator, ClassName enclosing) {

    if (enclosing.enclosingClass != null) {
      appendEnclosing(buffer, enclosingSeparator, enclosing.enclosingClass);
    }
    buffer.append(enclosing.simpleName);
    buffer.append(enclosingSeparator);
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    int offset = 0;
    if (this.enclosingClass != null) {
      offset = this.enclosingClass.hashCode();
    }
    result = prime * result + offset;
    offset = 0;
    if (this.packageName != null) {
      offset = this.packageName.hashCode();
    }
    result = prime * result + offset;
    offset = 0;
    if (this.simpleName != null) {
      offset = this.simpleName.hashCode();
    }
    result = prime * result + offset;
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ClassName other = (ClassName) obj;
    if (this.enclosingClass == null) {
      if (other.enclosingClass != null) {
        return false;
      }
    } else if (!this.enclosingClass.equals(other.enclosingClass)) {
      return false;
    }
    if (this.packageName == null) {
      if (other.packageName != null) {
        return false;
      }
    } else if (!this.packageName.equals(other.packageName)) {
      return false;
    }
    if (this.simpleName == null) {
      if (other.simpleName != null) {
        return false;
      }
    } else if (!this.simpleName.equals(other.simpleName)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return getName();
  }
}
