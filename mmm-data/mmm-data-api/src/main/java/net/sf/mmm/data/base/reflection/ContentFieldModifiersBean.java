/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import net.sf.mmm.data.NlsBundleDataApi;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

/**
 * This is the base implementation of the {@link DataFieldModifiers}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentFieldModifiersBean extends AbstractContentModifiersBean implements
    DataFieldModifiers {

  /** uid for serialization */
  private static final long serialVersionUID = -7486568372216293843L;

  /** the modifier of a "normal" field */
  public static final ContentFieldModifiersBean NORMAL = new ContentFieldModifiersBean(false,
      false, false, false, false, false);

  /** the modifier of a read-only field */
  public static final ContentFieldModifiersBean READ_ONLY = new ContentFieldModifiersBean(false,
      false, true, false, false, false);

  /** the modifier of a final field */
  public static final ContentFieldModifiersBean FINAL = new ContentFieldModifiersBean(false, true,
      false, false, false, false);

  /** the modifier of a final and read-only field */
  public static final ContentFieldModifiersBean FINAL_READONLY = new ContentFieldModifiersBean(
      false, true, true, false, false, false);

  /** the modifier of a static field */
  public static final ContentFieldModifiersBean STATIC = new ContentFieldModifiersBean(false,
      false, false, true, false, false);

  /** the modifier of a static and final field */
  public static final ContentFieldModifiersBean STATIC_FINAL = new ContentFieldModifiersBean(false,
      true, false, true, false, false);

  /** the modifier of a static and immutable field */
  public static final ContentFieldModifiersBean STATIC_READONLY = new ContentFieldModifiersBean(
      false, false, true, true, false, false);

  /** the modifier of a static, final and read-only field */
  public static final ContentFieldModifiersBean STATIC_FINAL_READONLY = new ContentFieldModifiersBean(
      false, true, true, true, false, false);

  /** the modifier of a transient (and read-only) field */
  public static final ContentFieldModifiersBean TRANSIENT = new ContentFieldModifiersBean(false,
      false, true, false, true, false);

  /** the modifier of a transient field */
  public static final ContentFieldModifiersBean FINAL_TRANSIENT = new ContentFieldModifiersBean(
      false, true, true, false, true, false);

  /** the modifier of a system-field */
  public static final ContentFieldModifiersBean SYSTEM = new ContentFieldModifiersBean(true, false,
      false, false, false, false);

  /** the modifier of a final system-field */
  public static final ContentFieldModifiersBean SYSTEM_FINAL = new ContentFieldModifiersBean(true,
      true, false, false, false, false);

  /** the modifier of a final and read-only system-field */
  public static final ContentFieldModifiersBean SYSTEM_FINAL_READONLY = new ContentFieldModifiersBean(
      true, true, true, false, false, false);

  /** the modifier of a static system-field */
  public static final ContentFieldModifiersBean SYSTEM_STATIC = new ContentFieldModifiersBean(true,
      false, false, true, false, false);

  /** the modifier of a static and final system-field */
  public static final ContentFieldModifiersBean SYSTEM_STATIC_FINAL = new ContentFieldModifiersBean(
      true, true, false, true, false, false);

  /** the modifier of a static, final and read-only system-field */
  public static final ContentFieldModifiersBean SYSTEM_STATIC_FINAL_READONLY = new ContentFieldModifiersBean(
      true, true, true, true, false, false);

  /** the modifier of a transient system-field */
  public static final ContentFieldModifiersBean SYSTEM_TRANSIENT = new ContentFieldModifiersBean(
      true, false, true, false, true, false);

  /** the modifier of a final, transient system-field */
  public static final ContentFieldModifiersBean SYSTEM_FINAL_TRANSIENT = new ContentFieldModifiersBean(
      true, true, true, false, true, false);

  /** the modifier of a static and read-only system-field */
  public static final ContentFieldModifiersBean SYSTEM_STATIC_READONLY = new ContentFieldModifiersBean(
      true, false, true, true, false, false);

  /** the modifier of a read-only system-field */
  public static final ContentFieldModifiersBean SYSTEM_READONLY = new ContentFieldModifiersBean(
      true, false, true, false, false, false);

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
   * @see AbstractContentModifiersBean#AbstractContentModifiersBean(boolean,
   *      boolean)
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @param isInheritedFromParent is the value for
   *        {@link #isInheritedFromParent()}.
   */
  public ContentFieldModifiersBean(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient, boolean isInheritedFromParent) {

    super(isSystem, isFinal);
    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient, isInheritedFromParent);
    this.readOnlyFlag = isReadOnly;
    this.staticFlag = isStatic;
    this.transientFlag = isTransient;
    this.inheritedFromParentFlag = isInheritedFromParent;
  }

  /**
   * The copy constructor.
   * 
   * @param modifiers is the modifiers object to copy.
   */
  public ContentFieldModifiersBean(DataFieldModifiers modifiers) {

    this(modifiers.isSystem(), modifiers.isFinal(), modifiers.isReadOnly(), modifiers.isStatic(),
        modifiers.isTransient(), modifiers.isInheritedFromParent());
  }

  /**
   * This method gets the constant representation of this
   * {@link DataFieldModifiers} instance.
   * 
   * @see String#intern()
   * @see #getInstance(boolean, boolean, boolean, boolean, boolean, boolean)
   * 
   * @return the according singleton {@link DataFieldModifiers} instance.
   */
  public DataFieldModifiers intern() {

    return getInstance(isSystem(), isFinal(), this.readOnlyFlag, this.staticFlag,
        this.transientFlag, this.inheritedFromParentFlag);
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
  public static DataFieldModifiers getInstance(boolean isSystem, boolean isFinal,
      boolean isReadOnly, boolean isStatic, boolean isTransient, boolean isInheritedFromParent) {

    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient, isInheritedFromParent);
    if (isInheritedFromParent) {
      // TODO
      throw new NlsUnsupportedOperationException();
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
   * This method validates the consistency of the modifier flags.
   * 
   * @param isSystem is the value for {@link #isSystem()}.
   * @param isFinal is the value for {@link #isFinal()}.
   * @param isReadOnly is the value for {@link #isReadOnly()}.
   * @param isStatic is the value for {@link #isStatic()}.
   * @param isTransient is the value for {@link #isTransient()}.
   * @param isInheritedFromParent is the value for
   *        {@link #isInheritedFromParent()}.
   * @throws ContentModifiersIllegalException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient, boolean isInheritedFromParent)
      throws ContentModifiersIllegalException {

    if (isTransient) {
      if (!isReadOnly) {
        throw new ContentModifiersIllegalException(
            NlsBundleDataApi.ERR_MODIFIERS_TRANSIENT_MUTABLE);
      }
      if (isStatic) {
        throw new ContentModifiersIllegalException(
            NlsBundleDataApi.ERR_MODIFIERS_TRANSIENT_STATIC);
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
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
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

    return result.toString();
  }

}
