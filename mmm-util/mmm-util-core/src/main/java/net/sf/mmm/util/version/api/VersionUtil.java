/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This is the interface for a collection of utility functions that help to deal with versions. E.g. you can
 * {@link #createVersionIdentifier(String) parse} a a version {@link String} to a {@link VersionIdentifier}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@ComponentSpecification
public interface VersionUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.version.api.VersionUtil";

  /**
   * This method parses the given <code>versionString</code> and builds an according {@link VersionIdentifier}
   * instance. It delegates to {@link #createFormatter(String, boolean)} using <code>false</code> for
   * <code>normalizeFormat</code>.
   * 
   * @param versionString is the {@link VersionIdentifier#toString() string representation} of a
   *        {@link VersionIdentifier}.
   * @return the parsed {@link VersionIdentifier}.
   * @throws NlsParseException if the given <code>versionString</code> is invalid and could not be parsed.
   */
  VersionIdentifier createVersionIdentifier(String versionString) throws NlsParseException;

  /**
   * This method parses the given <code>versionString</code> and builds an according {@link VersionIdentifier}
   * instance.
   * 
   * @param versionString is the {@link VersionIdentifier#toString() string representation} of a
   *        {@link VersionIdentifier}.
   * @param normalizeFormat - if <code>true</code> the returned {@link VersionIdentifier} will return a
   *        normalized {@link VersionIdentifier#toString() string representation} that may differ from the
   *        given <code>versionString</code>. Otherwise the original <code>versionString</code> will be used.
   * @return the parsed {@link VersionIdentifier}.
   * @throws NlsParseException if the given <code>versionString</code> is invalid and could not be parsed.
   */
  VersionIdentifier createVersionIdentifier(String versionString, boolean normalizeFormat) throws NlsParseException;

  /**
   * Variant of {@link #createFormatter(String, boolean)} with <code>strict</code> mode.
   * 
   * @see #createFormatter(String, boolean)
   * 
   * @param formatPattern is the format string.
   * @return the according {@link VersionIdentifierFormatter}.
   */
  VersionIdentifierFormatter createFormatter(String formatPattern);

  /**
   * This method creates a {@link VersionIdentifierFormatter} for the given <code>formatPattern</code>.
   * Similar to {@link java.text.SimpleDateFormat} the format pattern is parsed and there are particular
   * <em>letter symbols</em> that have a specific meaning.
   * <table border="1">
   * <tr>
   * <th>letter</th>
   * <th>meaning</th>
   * <th>comment</th>
   * </tr>
   * <tr>
   * <td>V</td>
   * <td>{@link VersionIdentifier#getVersionSegment(int) version segments}</td>
   * <td>Needs to be of the form <code>V&lt;separator&gt;[{&lt;min&gt;,&lt;max&gt;,&lt;padding&gt;}]</code>
   * where <code>&lt;separator&gt;</code> is a character used to separate the
   * {@link VersionIdentifier#getVersionSegment(int) version segments} (typically the dot sign '.'). Further,
   * <code>&lt;min&gt;</code> and <code>&lt;max&gt;</code> are the minimum and maximum number of
   * {@link VersionIdentifier#getVersionSegment(int) segments} to format and <code>&lt;padding&gt;</code> is
   * the minimum number of digits to {@link net.sf.mmm.util.lang.api.StringUtil#padNumber(long, int) pad} each
   * {@link VersionIdentifier#getVersionSegment(int) segment}.</td>
   * </tr>
   * <tr>
   * <td>P</td>
   * <td>{@link VersionIdentifier#getPhase() phase}</td>
   * <td>The {@link DevelopmentPhase#getTitle() official phase name} - typically you want to use "A" instead.
   * May be followed by <code>{&lt;max&gt;}</code> to limit (truncate) to a maximum number of characters.</td>
   * </tr>
   * <tr>
   * <td>A</td>
   * <td>{@link VersionIdentifier#getPhaseAlias() alias}</td>
   * <td>The phase alias. May be followed by <code>{&lt;max&gt;}</code> to limit (truncate) to a maximum
   * number of characters.</td>
   * </tr>
   * <tr>
   * <td>N</td>
   * <td>{@link VersionIdentifier#getPhaseNumber() phase number}</td>
   * <td>The {@link VersionIdentifier#getPhaseNumber() phase number}. May be followed by
   * <code>{&lt;padding&gt;}</code> to {@link net.sf.mmm.util.lang.api.StringUtil#padNumber(long, int) pad} to
   * a minimum number of digits.</td>
   * </tr>
   * <tr>
   * <td>S</td>
   * <td>{@link VersionIdentifier#isSnapshot() snapshot} indicator</td>
   * <td>The {@link VersionIdentifier#isSnapshot() snapshot} indicator. May be followed by
   * <code>{&lt;indicator&gt;}</code> to override the {@link VersionIdentifier#SNAPSHOT default snapshot
   * indicator} with {&lt;indicator&gt;}.</td>
   * </tr>
   * <tr>
   * <td>L</td>
   * <td>{@link VersionIdentifier#getLabel() label}</td>
   * <td>The label. May be followed by <code>{&lt;max&gt;}</code> to limit (truncate) to a maximum number of
   * characters.</td>
   * </tr>
   * <tr>
   * <tr>
   * <td>R</td>
   * <td>{@link VersionIdentifier#getRevision() revision}</td>
   * <td>The revision. May be followed by <code>{&lt;padding&gt;}</code> to
   * {@link net.sf.mmm.util.lang.api.StringUtil#padNumber(long, int) pad} to a minimum number of digits.</td>
   * </tr>
   * <tr>
   * <td>T</td>
   * <td>{@link VersionIdentifier#getTimestamp() timestamp}</td>
   * <td>The timestamp by default in basic {@link net.sf.mmm.util.date.api.Iso8601Util ISO-8601} format. May
   * be followed by <code>{&lt;format&gt;}</code> to use the given format instead as
   * {@link java.text.SimpleDateFormat} pattern (e.g. "T{yyyy-MM-dd}").</td>
   * </tr>
   * </table>
   * As all ASCII letters are reserved for existing letter symbol definitions (see table above) or further
   * extensions, you need to enclose static text in braces (e.g. "(rev)" to represent the static
   * {@link String} "rev" as infix for a format pattern).<br>
   * Further, infix text (no matter if enclosed in braces or not) is omitted if the following letter symbol is
   * resolved as undefined. To prevent this add the dollar character ($) to terminate a static infix that
   * should always occur.
   * 
   * <table border="1">
   * <tr>
   * <th>{@link VersionIdentifier#getVersionSegmentCount() segment count}</th>
   * <th>{@link VersionIdentifier#getVersionMajorSegment() major}</th>
   * <th>{@link VersionIdentifier#getVersionMinorSegment() minor}</th>
   * <th>{@link VersionIdentifier#getVersionMilliSegment() milli}</th>
   * <th>{@link VersionIdentifier#getVersionMicroSegment() micro}</th>
   * <th>{@link VersionIdentifier#getPhase() phase}</th>
   * <th>{@link VersionIdentifier#getPhaseAlias() alias}</th>
   * <th>{@link VersionIdentifier#getPhaseNumber() phase number}</th>
   * <th>{@link VersionIdentifier#isSnapshot() snapshot}</th>
   * <th>{@link VersionIdentifier#getLabel() label}</th>
   * <th>{@link VersionIdentifier#getRevision() revision}</th>
   * <th>{@link VersionIdentifier#getTimestamp timestamp}</th>
   * <th>formatPattern</th>
   * <th>{@link VersionIdentifierFormatter#format(VersionIdentifier) format}</th>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>1</td>
   * <td>2</td>
   * <td>3</td>
   * <td>4</td>
   * <td>null</td>
   * <td>null</td>
   * <td>null</td>
   * <td>false</td>
   * <td>null</td>
   * <td>null</td>
   * <td>null</td>
   * <td>"V.-AN-S-L-(rev)R-T"</td>
   * <td>"1.2.3.4-GA"</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>1</td>
   * <td>2</td>
   * <td>3</td>
   * <td>4</td>
   * <td>"{@link DevelopmentPhase#UPDATE update}"</td>
   * <td>"SR"</td>
   * <td>2</td>
   * <td>false</td>
   * <td>null</td>
   * <td>654321</td>
   * <td>null</td>
   * <td>"V.-AN-S-L-(rev)R-T"</td>
   * <td>"1.2.3.4-SR2-rev654321"</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>1</td>
   * <td>2</td>
   * <td>3</td>
   * <td>4</td>
   * <td>"{@link DevelopmentPhase#UPDATE update}"</td>
   * <td>"SR"</td>
   * <td>2</td>
   * <td>true</td>
   * <td>kassiopeia</td>
   * <td>654321</td>
   * <td>31.12.1999 23:59:59 UTC</td>
   * <td>"V.-AN-S-L-(rev)R-T"</td>
   * <td>"1.2.3.4-SR2-SNAPSHOT-kassiopeia-rev654321-19991231T23:59:59Z"</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>1</td>
   * <td>2</td>
   * <td>3</td>
   * <td>4</td>
   * <td>"{@link DevelopmentPhase#UPDATE update}"</td>
   * <td>"SR"</td>
   * <td>2</td>
   * <td>true</td>
   * <td>null</td>
   * <td>654321</td>
   * <td>31.12.1999 23:59:59 UTC</td>
   * <td>"V.{0,3,2}P{2}N{2}-S{snap}_$-L-(rev)R-T"</td>
   * <td>"01.02.03up02-snap_-rev654321-19991231T23:59:59Z"</td>
   * </tr>
   * </table>
   * 
   * @param formatPattern is the format string.
   * @param strict - if <code>true</code> then the given <code>formatPattern</code> has to include at least
   *        the {@link VersionIdentifier#getVersionSegment(int) version segments}, the
   *        {@link VersionIdentifier#getPhase() phase} (in any form including alias),
   *        {@link VersionIdentifier#getPhaseNumber() phase number}, and
   *        {@link VersionIdentifier#isSnapshot() snapshot}.
   * @return the according {@link VersionIdentifierFormatter}.
   */
  VersionIdentifierFormatter createFormatter(String formatPattern, boolean strict);

}
