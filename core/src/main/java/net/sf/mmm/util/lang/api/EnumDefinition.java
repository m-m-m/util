/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for the definition of an {@link EnumType}. <br>
 * As an example we can think of an instance of this interface called <em>country</em> defined as {@link EnumDefinition}
 * {@literal <Iso2CountryCode, Void>}. This defines an enumeration based on the type {@code Iso2CountryCode} that has no
 * {@link #getCategory() category}. Next, we define another instance of this interface called {@code state} defined as
 * {@link EnumDefinition} {@literal <StateDatatype, Iso2CountryCode>}. The method {@link #getCategory()} will return the
 * <em>country</em> instance we defined earlier. This means that a state is classified by a county. <br>
 * <b>ATTENTION:</b><br>
 * The {@link #getValue() value} is used as identifier key and has to be unique.
 *
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category} or {@link Void} for none.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EnumDefinition<TYPE, CATEGORY> extends SimpleDatatype<String> {

  /**
   * @return the {@link EnumDefinition} this {@link EnumDefinition} is categorized by or {@code null} for no category
   *         (then {@literal <CATEGORY>} shall be bound to {@link Void}).
   */
  EnumDefinition<CATEGORY, ?> getCategory();

  /**
   * This method gets the {@link Formatter} used to {@link Formatter#format(Object) format} instances of the enum as
   * {@link String} for end-users. By default {@link net.sf.mmm.util.lang.base.FormatterToString} can be used. However,
   * e.g. for the type {@link Boolean} you might want to display as "yes" or "no" and might have an alternative
   * {@link EnumDefinition} for {@link Boolean} that displays as "all" or "none". Further, also I18N (see
   * {@link net.sf.mmm.util.nls.api.NlsMessage}) can be addressed by this {@link Formatter}.<br/>
   * <b>ATTENTION:</b><br>
   * A {@link EnumDefinition} may be used with or without allowing a {@code null} value. Therefore, this
   * {@link Formatter} should be able to properly format {@code null} (see
   * {@link net.sf.mmm.util.lang.base.AbstractFormatter#formatNull()}). E.g. a {@link Boolean} option in a search form
   * may be optional and displayed as "any".
   *
   * @return the {@link Formatter}.
   */
  Formatter<TYPE> getFormatter();

  /**
   * @return the {@link EnumType} of this {@link EnumDefinition}.
   */
  Class<TYPE> getEnumType();

  /**
   * @return {@code true} if {@link EnumType}-instances of {@link #getEnumType() this type} can be added or removed,
   *         {@code false} otherwise (e.g. if implemented by a {@link Enum}).
   */
  boolean isMutable();

  /**
   * @return {@code true} if all {@link EnumType}-instances of {@link #getEnumType() this type} can cached (default) or
   *         {@code false} if there are too many of them (e.g. for entities like cities of the world).
   */
  boolean isCachable();

  /**
   * {@inheritDoc}
   *
   * <br>
   * <b>ATTENTION:</b><br>
   * The value is used as identifier for this {@link #getEnumType() enum type} and has to be unique for
   * {@link EnumProvider#getEnumDefinitions() all available enumerations}.
   */
  @Override
  String getValue();

}
