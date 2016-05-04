/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionUtil;
import net.sf.mmm.util.version.impl.VersionUtilImpl;

/**
 * This is the implementation of {@link AbstractSimpleDatatypeAttributeConverter} for {@link VersionIdentifier}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@SuppressWarnings("unused")
@Converter(autoApply = true)
public class VersionIdentifierAttributeConverter
    extends AbstractSimpleDatatypeAttributeConverter<VersionIdentifier, String> implements
    // https://hibernate.atlassian.net/browse/HHH-8854
    AttributeConverter<VersionIdentifier, String> {

  private  VersionUtil versionUtil;

  /**
   * The constructor.
   */
  public VersionIdentifierAttributeConverter() {

    this(VersionUtilImpl.getInstance());
  }

  /**
   * The constructor.
   *
   * @param versionUtil is the {@link VersionUtil} to use for creating versions.
   */
  public VersionIdentifierAttributeConverter(VersionUtil versionUtil) {

    super();
    this.versionUtil = versionUtil;
  }

  @Override
  public VersionIdentifier convertToEntityAttribute(String value) {

    return this.versionUtil.createVersionIdentifier(value);
  }

}
