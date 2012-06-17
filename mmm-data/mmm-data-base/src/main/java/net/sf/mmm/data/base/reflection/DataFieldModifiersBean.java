/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalException;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalTransientMutableException;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalTransientStaticException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is the base implementation of the {@link DataFieldModifiers} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataFieldModifiersBean extends AbstractDataModifiersBean implements
    DataFieldModifiers {

  /** UID for serialization */
  private static final long serialVersionUID = -7486568372216293843L;

  /** The character representing the {@link #isStatic() static flag}. */
  protected static final char CHARACTER_STATIC = 'S';

  /** The character representing the {@link #isTransient() transient flag}. */
  protected static final char CHARACTER_TRANSIENT = 'T';

  /** The character representing the {@link #isReadOnly() read-only flag}. */
  protected static final char CHARACTER_READ_ONLY = 'R';

  /**
   * The character representing the {@link #isInheritedFromParent()
   * inherited-from-parent flag}.
   */
  protected static final char CHARACTER_INHERITED_FROM_PARENT = 'I';

  /** the modifier of a "normal" field */
  public static final DataFieldModifiersBean NORMAL = new DataFieldModifiersBean(false, false,
      false, false, false, false);

  /** the modifier of a read-only field */
  public static final DataFieldModifiersBean READ_ONLY = new DataFieldModifiersBean(false, false,
      true, false, false, false);

  /** the modifier of a final field */
  public static final DataFieldModifiersBean FINAL = new DataFieldModifiersBean(false, true, false,
      false, false, false);

  /** the modifier of a final and read-only field */
  public static final DataFieldModifiersBean FINAL_READONLY = new DataFieldModifiersBean(false,
      true, true, false, false, false);

  /** the modifier of a static field */
  public static final DataFieldModifiersBean STATIC = new DataFieldModifiersBean(false, false,
      false, true, false, false);

  /** the modifier of a static and final field */
  public static final DataFieldModifiersBean STATIC_FINAL = new DataFieldModifiersBean(false, true,
      false, true, false, false);

  /** the modifier of a static and immutable field */
  public static final DataFieldModifiersBean STATIC_READONLY = new DataFieldModifiersBean(false,
      false, true, true, false, false);

  /** the modifier of a static, final and read-only field */
  public static final DataFieldModifiersBean STATIC_FINAL_READONLY = new DataFieldModifiersBean(
      false, true, true, true, false, false);

  /** the modifier of a transient (and read-only) field */
  public static final DataFieldModifiersBean TRANSIENT = new DataFieldModifiersBean(false, false,
      true, false, true, false);

  /** the modifier of a transient field */
  public static final DataFieldModifiersBean FINAL_TRANSIENT = new DataFieldModifiersBean(false,
      true, true, false, true, false);

  /** the modifier of a system-field */
  public static final DataFieldModifiersBean SYSTEM = new DataFieldModifiersBean(true, false,
      false, false, false, false);

  /** the modifier of a final system-field */
  public static final DataFieldModifiersBean SYSTEM_FINAL = new DataFieldModifiersBean(true, true,
      false, false, false, false);

  /** the modifier of a final and read-only system-field */
  public static final DataFieldModifiersBean SYSTEM_FINAL_READONLY = new DataFieldModifiersBean(
      true, true, true, false, false, false);

  /** the modifier of a static system-field */
  public static final DataFieldModifiersBean SYSTEM_STATIC = new DataFieldModifiersBean(true,
      false, false, true, false, false);

  /** the modifier of a static and final system-field */
  public static final DataFieldModifiersBean SYSTEM_STATIC_FINAL = new DataFieldModifiersBean(true,
      true, false, true, false, false);

  /** the modifier of a static, final and read-only system-field */
  public static final DataFieldModifiersBean SYSTEM_STATIC_FINAL_READONLY = new DataFieldModifiersBean(
      true, true, true, true, false, false);

  /** the modifier of a transient system-field */
  public static final DataFieldModifiersBean SYSTEM_TRANSIENT = new DataFieldModifiersBean(true,
      false, true, false, true, false);

  /** the modifier of a final, transient system-field */
  public static final DataFieldModifiersBean SYSTEM_FINAL_TRANSIENT = new DataFieldModifiersBean(
      true, true, true, false, true, false);

  /** the modifier of a static and read-only system-field */
  public static final DataFieldModifiersBean SYSTEM_STATIC_READONLY = new DataFieldModifiersBean(
      true, false, true, true, false, false);

  /** the modifier of a read-only system-field */
  public static final DataFieldModifiersBean SYSTEM_READONLY = new DataFieldModifiersBean(true,
      false, true, false, false, false);

  // --------------------

  /** the modifier of a "normal" field */
  public static final DataFieldModifiersBean INHERITED = new DataFieldModifiersBean(false, false,
      false, false, false, true);

  /** the modifier of a read-only field */
  public static final DataFieldModifiersBean INHERITED_READ_ONLY = new DataFieldModifiersBean(
      false, false, true, false, false, true);

  /** the modifier of a final field */
  public static final DataFieldModifiersBean INHERITED_FINAL = new DataFieldModifiersBean(false,
      true, false, false, false, true);

  /** the modifier of a final and read-only field */
  public static final DataFieldModifiersBean INHERITED_FINAL_READONLY = new DataFieldModifiersBean(
      false, true, true, false, false, true);

  /** the modifier of a static field */
  public static final DataFieldModifiersBean INHERITED_STATIC = new DataFieldModifiersBean(false,
      false, false, true, false, true);

  /** the modifier of a static and final field */
  public static final DataFieldModifiersBean INHERITED_STATIC_FINAL = new DataFieldModifiersBean(
      false, true, false, true, false, true);

  /** the modifier of a static and immutable field */
  public static final DataFieldModifiersBean INHERITED_STATIC_READONLY = new DataFieldModifiersBean(
      false, false, true, true, false, true);

  /** the modifier of a static, final and read-only field */
  public static final DataFieldModifiersBean INHERITED_STATIC_FINAL_READONLY = new DataFieldModifiersBean(
      false, true, true, true, false, true);

  /** the modifier of a transient (and read-only) field */
  public static final DataFieldModifiersBean INHERITED_TRANSIENT = new DataFieldModifiersBean(
      false, false, true, false, true, true);

  /** the modifier of a transient field */
  public static final DataFieldModifiersBean INHERITED_FINAL_TRANSIENT = new DataFieldModifiersBean(
      false, true, true, false, true, true);

  /** the modifier of a system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM = new DataFieldModifiersBean(true,
      false, false, false, false, true);

  /** the modifier of a final system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_FINAL = new DataFieldModifiersBean(
      true, true, false, false, false, true);

  /** the modifier of a final and read-only system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_FINAL_READONLY = new DataFieldModifiersBean(
      true, true, true, false, false, true);

  /** the modifier of a static system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_STATIC = new DataFieldModifiersBean(
      true, false, false, true, false, true);

  /** the modifier of a static and final system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_STATIC_FINAL = new DataFieldModifiersBean(
      true, true, false, true, false, true);

  /** the modifier of a static, final and read-only system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_STATIC_FINAL_READONLY = new DataFieldModifiersBean(
      true, true, true, true, false, true);

  /** the modifier of a transient system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_TRANSIENT = new DataFieldModifiersBean(
      true, false, true, false, true, true);

  /** the modifier of a final, transient system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_FINAL_TRANSIENT = new DataFieldModifiersBean(
      true, true, true, false, true, true);

  /** the modifier of a static and read-only system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_STATIC_READONLY = new DataFieldModifiersBean(
      true, false, true, true, false, true);

  /** the modifier of a read-only system-field */
  public static final DataFieldModifiersBean INHERITED_SYSTEM_READONLY = new DataFieldModifiersBean(
      true, false, true, false, false, true);

  /** @see #isReadOnly() */
  private final boolean readOnlyFlag;

  /** @see #isStatic() */
  private final boolean staticFlag;

  /** @see #isTransient() */
  private final boolean transientFlag;

  /** @see #isInheritedFromParent() */
  private final boolean inheritedFromParentFlag;

  /**
   * The constructor.
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @param isInheritedFromParent is the value for
   *        {@link #isInheritedFromParent()}.
   */
  private DataFieldModifiersBean(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient, boolean isInheritedFromParent) {

    super(isSystem, isFinal);
    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient, isInheritedFromParent);
    this.readOnlyFlag = isReadOnly;
    this.staticFlag = isStatic;
    this.transientFlag = isTransient;
    this.inheritedFromParentFlag = isInheritedFromParent;
  }

  /**
   * This method gets the modifiers.
   * 
   * @param stringValue is the {@link #getValue() string value} representing the
   *        {@link DataFieldModifiers}.
   * @return the requested modifiers.
   */
  public static DataFieldModifiersBean getInstance(String stringValue) {

    boolean isSystem = false;
    boolean isFinal = false;
    boolean isReadOnly = false;
    boolean isStatic = false;
    boolean isTransient = false;
    boolean isInheritedFromParent = false;
    int index = stringValue.length() - 1;
    while (index >= 0) {
      char c = stringValue.charAt(index--);
      switch (c) {
        case CHARACTER_STATIC:
          if (isStatic) {
            throw new DuplicateObjectException(stringValue, Character.valueOf(CHARACTER_STATIC));
          }
          isStatic = true;
          break;
        case CHARACTER_TRANSIENT:
          if (isTransient) {
            throw new DuplicateObjectException(stringValue, Character.valueOf(CHARACTER_TRANSIENT));
          }
          isTransient = true;
          break;
        case CHARACTER_READ_ONLY:
          if (isReadOnly) {
            throw new DuplicateObjectException(stringValue, Character.valueOf(CHARACTER_READ_ONLY));
          }
          isReadOnly = true;
          break;
        case CHARACTER_INHERITED_FROM_PARENT:
          if (isInheritedFromParent) {
            throw new DuplicateObjectException(stringValue,
                Character.valueOf(CHARACTER_INHERITED_FROM_PARENT));
          }
          isInheritedFromParent = true;
          break;
        case CHARACTER_FINAL:
          if (isFinal) {
            throw new DuplicateObjectException(stringValue, Character.valueOf(CHARACTER_FINAL));
          }
          isFinal = true;
          break;
        case CHARACTER_SYSTEM:
          if (isSystem) {
            throw new DuplicateObjectException(stringValue, Character.valueOf(CHARACTER_SYSTEM));
          }
          isSystem = true;
          break;
        default :
          throw new IllegalCaseException(Character.toString(c));
      }
    }
    return getInstance(isSystem, isFinal, isReadOnly, isStatic, isTransient, isInheritedFromParent);
  }

  /**
   * This method gets the modifiers.
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @param isInheritedFromParent is the value for
   *        {@link #isInheritedFromParent()}.
   * @return the requested modifiers.
   */
  public static DataFieldModifiersBean getInstance(boolean isSystem, boolean isFinal,
      boolean isReadOnly, boolean isStatic, boolean isTransient, boolean isInheritedFromParent) {

    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient, isInheritedFromParent);
    if (isInheritedFromParent) {
      return getInstanceInherited(isSystem, isFinal, isReadOnly, isStatic, isTransient);
    } else {
      if (isSystem) {
        if (isTransient) {
          if (isFinal) {
            return SYSTEM_FINAL_TRANSIENT;
          } else {
            return SYSTEM_TRANSIENT;
          }
        } else {
          if (isFinal) {
            if (isReadOnly) {
              if (isStatic) {
                return SYSTEM_STATIC_FINAL_READONLY;
              } else {
                return SYSTEM_FINAL_READONLY;
              }
            } else {
              if (isStatic) {
                return SYSTEM_STATIC_FINAL;
              } else {
                return SYSTEM_FINAL;
              }
            }
          } else {
            if (isReadOnly) {
              if (isStatic) {
                return SYSTEM_STATIC_READONLY;
              } else {
                return SYSTEM_READONLY;
              }
            } else {
              if (isStatic) {
                return SYSTEM_STATIC;
              } else {
                return SYSTEM;
              }
            }
          }
        }
      } else {
        if (isTransient) {
          if (isFinal) {
            return FINAL_TRANSIENT;
          } else {
            return TRANSIENT;
          }
        } else {
          if (isFinal) {
            if (isReadOnly) {
              if (isStatic) {
                return STATIC_FINAL_READONLY;
              } else {
                return FINAL_READONLY;
              }
            } else {
              if (isStatic) {
                return STATIC_FINAL;
              } else {
                return FINAL;
              }
            }
          } else {
            if (isReadOnly) {
              if (isStatic) {
                return STATIC_READONLY;
              } else {
                return READ_ONLY;
              }
            } else {
              if (isStatic) {
                return STATIC;
              } else {
                return NORMAL;
              }
            }
          }
        }
      }
    }
  }

  /**
   * @see #getInstance(boolean, boolean, boolean, boolean, boolean, boolean)
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @return the requested modifiers.
   */
  private static DataFieldModifiersBean getInstanceInherited(boolean isSystem, boolean isFinal,
      boolean isReadOnly, boolean isStatic, boolean isTransient) {

    if (isSystem) {
      if (isTransient) {
        if (isFinal) {
          return INHERITED_SYSTEM_FINAL_TRANSIENT;
        } else {
          return INHERITED_SYSTEM_TRANSIENT;
        }
      } else {
        if (isFinal) {
          if (isReadOnly) {
            if (isStatic) {
              return INHERITED_SYSTEM_STATIC_FINAL_READONLY;
            } else {
              return INHERITED_SYSTEM_FINAL_READONLY;
            }
          } else {
            if (isStatic) {
              return INHERITED_SYSTEM_STATIC_FINAL;
            } else {
              return INHERITED_SYSTEM_FINAL;
            }
          }
        } else {
          if (isReadOnly) {
            if (isStatic) {
              return INHERITED_SYSTEM_STATIC_READONLY;
            } else {
              return INHERITED_SYSTEM_READONLY;
            }
          } else {
            if (isStatic) {
              return INHERITED_SYSTEM_STATIC;
            } else {
              return INHERITED_SYSTEM;
            }
          }
        }
      }
    } else {
      if (isTransient) {
        if (isFinal) {
          return INHERITED_FINAL_TRANSIENT;
        } else {
          return INHERITED_TRANSIENT;
        }
      } else {
        if (isFinal) {
          if (isReadOnly) {
            if (isStatic) {
              return INHERITED_STATIC_FINAL_READONLY;
            } else {
              return INHERITED_FINAL_READONLY;
            }
          } else {
            if (isStatic) {
              return INHERITED_STATIC_FINAL;
            } else {
              return INHERITED_FINAL;
            }
          }
        } else {
          if (isReadOnly) {
            if (isStatic) {
              return INHERITED_STATIC_READONLY;
            } else {
              return INHERITED_READ_ONLY;
            }
          } else {
            if (isStatic) {
              return INHERITED_STATIC;
            } else {
              return INHERITED;
            }
          }
        }
      }
    }
  }

  /**
   * This method validates the consistency of the modifier flags.
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @param isInheritedFromParent is the value for
   *        {@link #isInheritedFromParent()}.
   * @throws DataModifiersIllegalException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient, boolean isInheritedFromParent)
      throws DataModifiersIllegalException {

    if (isTransient) {
      if (!isReadOnly) {
        throw new DataModifiersIllegalTransientMutableException();
      }
      if (isStatic) {
        throw new DataModifiersIllegalTransientStaticException();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isReadOnly() {

    if (isTransient()) {
      return true;
    }
    return this.readOnlyFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransient() {

    return this.transientFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isStatic() {

    return this.staticFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInheritedFromParent() {

    return this.inheritedFromParentFlag;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    StringBuilder buffer = new StringBuilder();
    if (this.staticFlag) {
      buffer.append(CHARACTER_STATIC);
    }
    if (isFinal()) {
      buffer.append(CHARACTER_FINAL);
    }
    if (this.transientFlag) {
      buffer.append(CHARACTER_TRANSIENT);
    }
    if (this.readOnlyFlag) {
      buffer.append(CHARACTER_READ_ONLY);
    }
    if (this.inheritedFromParentFlag) {
      buffer.append(CHARACTER_INHERITED_FROM_PARENT);
    }
    if (isSystem()) {
      buffer.append(CHARACTER_SYSTEM);
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    StringBuilder result = new StringBuilder();
    if (isSystem()) {
      result.append("system");
    }
    if (isStatic()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("static");
    }
    if (isFinal()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("final");
    }
    if (isReadOnly()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("readonly");
    }
    if (isTransient()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("transient");
    }
    if (isInheritedFromParent()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("inherited");
    }

    return result.toString();
  }

}
