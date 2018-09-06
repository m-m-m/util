/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.base;

import java.io.IOException;
import java.util.Objects;

import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is a simple container class for a combination of a {@link #getName() (product) name} together with its
 * {@link #getVersion() version}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public abstract class AbstractNameVersion implements NameVersion {

  private final String version;

  /**
   * The constructor.
   *
   * @param version the {@link #getVersion() version}.
   */
  public AbstractNameVersion(String version) {

    super();
    this.version = version;
  }

  /**
   * @return the name of the {@link #getVersion() versioned} object (protocol, software product, etc.).
   *         Examples are "HTTP", "UPnP", "Mozilla", "FireFox", etc.
   */
  @Override
  public abstract String getName();

  /**
   * @return the version (e.g. "50", "5.0", "9A334", etc.)
   */
  @Override
  public String getVersion() {

    return this.version;
  }

  @Override
  public void write(Appendable appendable) {

    try {
      doWrite(appendable, false);
    } catch (IOException e) {
      throw new IllegalStateException("Error writing to Appendable.", e);
    }
  }

  /**
   * Called from {@link #write(Appendable)} or {@link #toString()} to write the serialized data of this
   * object.
   *
   * @param appendable the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   * @param fromToString - {@code true} if called from {@link #toString()}, {@code false} otherwise. Can be
   *        ignored if not relevant.
   * @throws IOException if an error occurred whilst writing the data.
   */
  protected void doWrite(Appendable appendable, boolean fromToString) throws IOException {

    appendable.append(getName());
    appendable.append(NAME_VERSION_SEPARATOR);
    appendable.append(this.version);
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(this.version);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    AbstractNameVersion other = (AbstractNameVersion) obj;
    if (!Objects.equals(this.version, other.version)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    try {
      doWrite(buffer, true);
    } catch (IOException e) {
      buffer.append(e.getMessage());
    }
    return buffer.toString();
  }

}
