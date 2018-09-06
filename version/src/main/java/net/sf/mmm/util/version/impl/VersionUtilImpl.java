/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ConjunctionCharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.StaticFormatter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionIdentifierFormatter;
import net.sf.mmm.util.version.api.VersionUtil;

/**
 * This is the abstract base-implementation of the {@link VersionUtil} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionUtilImpl extends AbstractLoggableComponent implements VersionUtil {

  /** A {@link CharFilter} that accepts all but ASCII letters. */
  private static final CharFilter INFIX_FILTER = new ConjunctionCharFilter(Conjunction.NOR, CharFilter.ASCII_LETTER_FILTER,
      new ListCharFilter(true, '$', '(', ')', '{', '}'));

  /** A {@link CharFilter} that accepts common separators. */
  private static final CharFilter SEPARATOR_FILTER = new ListCharFilter(true, '.', '-', '_', '#', ',');

  /** A {@link CharFilter} that accepts all but separators and digits. */
  private static final CharFilter LETTER_FILTER = new ConjunctionCharFilter(Conjunction.NOR, SEPARATOR_FILTER, CharFilter.LATIN_DIGIT_FILTER);

  private static VersionUtil instance;

  private Iso8601Util iso8601Util;

  private Map<String, DevelopmentPhase> phaseMap;

  private Set<String> phasePrefixSet;

  private VersionIdentifierFormatter defaultFormatter;

  /**
   * The constructor.
   */
  public VersionUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link VersionUtilImpl}. <br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization. <br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the IoC container-framework of your choice. To wire up the dependent components everything is properly
   * annotated using annotations (JSR-250 and JSR-330). If your container does NOT support this, you should
   * consider using a better one.
   *
   * @return the singleton instance.
   */
  public static VersionUtil getInstance() {

    if (instance == null) {
      synchronized (VersionUtil.class) {
        if (instance == null) {
          VersionUtilImpl impl = new VersionUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * @return the iso8601Util
   */
  protected Iso8601Util getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * @return the phaseMappingTable
   */
  protected Map<String, DevelopmentPhase> getPhaseMap() {

    return this.phaseMap;
  }

  /**
   * @return the {@link Set} containing the prefix of the {@link Map#keySet() keys} before the first hyphen.
   */
  protected Set<String> getPhasePrefixSet() {

    return this.phasePrefixSet;
  }

  /**
   * @param phasePrefixSet is the phasePrefixSet to set
   */
  public void setPhasePrefixSet(Set<String> phasePrefixSet) {

    this.phasePrefixSet = phasePrefixSet;
  }

  /**
   * @param phaseMappingTable is the phaseMappingTable to set
   */
  public void setPhaseMap(Map<String, DevelopmentPhase> phaseMappingTable) {

    getInitializationState().requireNotInitilized();
    this.phaseMap = phaseMappingTable;
  }

  /**
   * @return the defaultFormatter
   */
  @Override
  public VersionIdentifierFormatter getDefaultFormatter() {

    return this.defaultFormatter;
  }

  /**
   * @param defaultFormatter is the defaultFormatter to set
   */
  public void setDefaultFormatter(VersionIdentifierFormatter defaultFormatter) {

    getInitializationState().requireNotInitilized();
    this.defaultFormatter = defaultFormatter;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
    if (this.defaultFormatter == null) {
      this.defaultFormatter = new DefaultVersionIdentifierFormatter(this.iso8601Util);
    }
    if (this.phaseMap == null) {
      this.phaseMap = createDefaultPhaseMap();
    }
    if (this.phasePrefixSet == null) {
      this.phasePrefixSet = new HashSet<>();
      for (String key : this.phaseMap.keySet()) {
        int index = key.indexOf('-');
        if (index > 0) {
          this.phasePrefixSet.add(key.substring(0, index));
        }
      }
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  /**
   * This method puts the given {@code phase} in the given {@code map} using normalized variants of the given
   * {@code key}.
   *
   * @param map is the {@link #getPhaseMap()}.
   * @param key is the key.
   * @param phase is the {@link DevelopmentPhase} to put.
   */
  protected void putPhase(Map<String, DevelopmentPhase> map, String key, DevelopmentPhase phase) {

    String newKey = key.toLowerCase(Locale.US);
    while (newKey != null) {
      DevelopmentPhase existingPhase = map.get(newKey);
      if ((existingPhase != null) && (existingPhase != phase)) {
        throw new IllegalStateException("Development phase '" + phase + "' has key '" + newKey + "' that is already mapped to '" + existingPhase + "'.");
      }
      map.put(newKey, phase);
      if (newKey.contains("-")) {
        newKey = newKey.replace("-", "");
      } else {
        newKey = null;
      }
    }
  }

  /**
   * This method creates the {@link #getPhaseMap() phase map} used as default.
   *
   * @return the {@link Map}.
   */
  protected Map<String, DevelopmentPhase> createDefaultPhaseMap() {

    Map<String, DevelopmentPhase> map = new HashMap<>();
    for (DevelopmentPhase phase : DevelopmentPhase.values()) {
      putPhase(map, phase.toString(), phase);
      putPhase(map, phase.getValue(), phase);
      for (String key : phase.getAlternatives()) {
        putPhase(map, key, phase);
      }
    }
    return map;
  }

  @Override
  public VersionIdentifier createVersionIdentifier(String versionString) {

    return createVersionIdentifier(versionString, false);
  }

  @Override
  public VersionIdentifier createVersionIdentifier(String versionString, boolean normalizeFormat) {

    List<Integer> versionSegmentList = new ArrayList<>();
    CharSequenceScanner scanner = new CharSequenceScanner(versionString);
    try {
      return createVersionIdentifier(versionString, normalizeFormat, versionSegmentList, scanner);
    } catch (Exception e) {
      throw new IllegalArgumentException(versionString, e);
    }
  }

  private VersionIdentifier createVersionIdentifier(String versionString, boolean normalizeFormat, List<Integer> versionSegmentList,
      CharSequenceScanner scanner) {

    String label = null;
    Date timestamp = null;
    Long revision = null;
    DevelopmentPhase phase = null;
    String phaseAlias = null;
    Integer phaseNumber = null;
    boolean snapshot = false;
    boolean segmentsCompleted = false;
    while (scanner.hasNext()) {
      scanner.skipWhile(SEPARATOR_FILTER);
      // snapshot
      if (scanner.expectStrict(VersionIdentifier.SNAPSHOT, true)) {
        if (snapshot) {
          throw new IllegalStateException("Duplicate SNAPSHOT found.");
        }
        snapshot = true;
      } else if (scanner.expectStrict("rev", true)) {
        // revision
        if (revision != null) {
          throw new IllegalStateException("Duplicate revision found (" + revision + ").");
        }
        segmentsCompleted = true;
        scanner.expectStrict("ision", true);
        scanner.expect('-');
        revision = Long.valueOf(scanner.readLong(19));
      }
      // number
      String numberString = scanner.readWhile(CharFilter.LATIN_DIGIT_FILTER);
      if (numberString.length() > 0) {
        timestamp = parseTimestamp(scanner, timestamp, numberString);
        if (timestamp == null) {
          // version segment...
          if (segmentsCompleted) {
            throw new IllegalStateException("Invalid segment: " + numberString);
          }
          Integer segment = Integer.valueOf(numberString);
          versionSegmentList.add(segment);
        }
      } else {
        String phaseOrLabel = scanner.readWhile(LETTER_FILTER);
        if (phaseOrLabel.length() > 0) {
          String phaseLowerCase = phaseOrLabel.toLowerCase(Locale.US);
          DevelopmentPhase currentPhase = this.phaseMap.get(phaseLowerCase);
          if (this.phasePrefixSet.contains(phaseLowerCase)) {
            if (scanner.forcePeek() == '-') {
              String prefix = phaseLowerCase + '-';
              for (String key : this.phaseMap.keySet()) {
                if (key.startsWith(prefix)) {
                  String rest = key.substring(phaseLowerCase.length());
                  if (scanner.expectStrict(rest, true)) {
                    currentPhase = this.phaseMap.get(key);
                    phaseOrLabel = key;
                    break;
                  }
                }
              }
            }
          }
          if (currentPhase == null) {
            if (label != null) {
              throw new IllegalStateException("Label '" + label + "' is a duplicate of '" + phaseOrLabel + "'.");
            }
            label = phaseOrLabel;
          } else {
            if (phase != null) {
              throw new IllegalStateException("Phase '" + phase + "' is a duplicate of '" + currentPhase + "'.");
            }
            phase = currentPhase;
            phaseAlias = phaseOrLabel;
            scanner.expect('-');
            String phaseNumberString = scanner.readWhile(CharFilter.LATIN_DIGIT_FILTER);
            if (phaseNumberString.length() > 0) {
              // if (phaseNumber != null) {...}
              phaseNumber = Integer.valueOf(phaseNumberString);
            }
          }
        }
      }
    }
    int[] versionSegments = new int[versionSegmentList.size()];
    for (int i = 0; i < versionSegments.length; i++) {
      versionSegments[i] = versionSegmentList.get(i).intValue();
    }
    VersionIdentifierImpl result = new VersionIdentifierImpl(versionString, label, timestamp, revision, phase, phaseAlias, phaseNumber, snapshot,
        versionSegments);
    if (normalizeFormat) {
      result.setStringRepresentation(this.defaultFormatter.format(result));
    }
    return result;
  }

  private Date parseTimestamp(CharSequenceScanner scanner, Date timestamp, String numberString) {

    // 19991231, 1999-12-31
    if ((numberString.length() >= 4) && (numberString.length() <= 14)) {
      if (numberString.startsWith("19") || numberString.startsWith("20") || numberString.startsWith("21")) {
        // longest ISO-8601 format is 28 characters
        // 1999-12-31T23:59:59+12:45:00
        // shortest format is 8 characters
        // 19991231
        StringBuffer buffer = new StringBuffer(numberString);
        buffer.append(scanner.peek(28 - numberString.length()));
        String dateString = buffer.toString();
        Matcher isoMatcher = Iso8601Util.PATTERN_ALL.matcher(dateString);
        if (isoMatcher.find()) {
          int end = isoMatcher.end();
          buffer.setLength(end);
          if (timestamp != null) {
            throw new IllegalStateException("Duplicate timestamp found (" + buffer + ").");
          }
          scanner.skip(end - numberString.length());
          return this.iso8601Util.parseDate(buffer.toString());
        } else {
          // support other date formats?
          throw new IllegalStateException("Illegal date format (" + dateString + ").");
        }
      }
    }
    return timestamp;
  }

  @Override
  public VersionIdentifierFormatter createFormatter(String formatPattern) {

    return createFormatter(formatPattern, true);
  }

  /**
   * @see #createFormatter(String, boolean)
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @param formatPattern is the format pattern.
   * @param infixBuffer is a {@link StringBuilder} containing the current infix.
   * @param status is the {@link FormatPatternStatus}.
   * @return the sub {@link Formatter} or {@code null} to continue parsing the infix.
   */
  protected Formatter<VersionIdentifier> parseSubFormatter(CharSequenceScanner scanner, String formatPattern, StringBuilder infixBuffer,
      FormatPatternStatus status) {

    char c = scanner.next();
    if (c == 'V') {
      char segment = scanner.forceNext();
      if (segment == '\0') {
        throw new IllegalStateException(formatPattern + "<separator>");
      }
      String segmentSeparator = Character.toString(segment);
      int minimumSegmentCount = 0;
      int maximumSegmentCount = Integer.MAX_VALUE;
      int segmentPadding = 0;
      if (scanner.expect('{')) {
        minimumSegmentCount = (int) scanner.readLong(10);
        scanner.require(',');
        maximumSegmentCount = (int) scanner.readLong(10);
        scanner.require(',');
        segmentPadding = (int) scanner.readLong(10);
        scanner.require('}');
      }
      status.versionSegmentsCount++;
      return new VersionIdentifierFormatterVersionSegments(infixBuffer.toString(), segmentSeparator, minimumSegmentCount, maximumSegmentCount, segmentPadding);
    } else if ((c == 'P') || (c == 'A') || (c == 'L')) {
      int maximumLength = 0;
      if (scanner.expect('{')) {
        maximumLength = (int) scanner.readLong(10);
        scanner.require('}');
      }
      if (c == 'P') {
        status.phaseCount++;
        return new VersionIdentifierFormatterPhase(infixBuffer.toString(), maximumLength);
      } else if (c == 'A') {
        status.phaseCount++;
        return new VersionIdentifierFormatterPhaseAlias(infixBuffer.toString(), maximumLength);
      } else {
        return new VersionIdentifierFormatterLabel(infixBuffer.toString(), maximumLength);
      }
    } else if ((c == 'R') || (c == 'N')) {
      int padding = 0;
      if (scanner.expect('{')) {
        padding = (int) scanner.readLong(10);
        scanner.require('}');
      }
      if (c == 'R') {
        return new VersionIdentifierFormatterRevision(infixBuffer.toString(), padding);
      } else {
        status.phaseNumberCount++;
        return new VersionIdentifierFormatterPhaseNumber(infixBuffer.toString(), padding);
      }
    } else if (c == 'T') {
      DateFormat dateFormat = null;
      if (scanner.expect('{')) {
        String datePattern = scanner.readUntil('}', false);
        if (datePattern == null) {
          scanner.require('}');
        }
        dateFormat = new SimpleDateFormat(datePattern);
      }
      return new VersionIdentifierFormatterTimestamp(this.iso8601Util, dateFormat, infixBuffer.toString());
    } else if (c == 'S') {
      String snapshotIndicator = VersionIdentifier.SNAPSHOT;
      if (scanner.expect('{')) {
        snapshotIndicator = scanner.readUntil('}', false);
        if (snapshotIndicator == null) {
          scanner.require('}');
        }
      }
      status.snapshotCount++;
      infixBuffer.append(snapshotIndicator);
      return new VersionIdentifierFormatterSnapshot(infixBuffer.toString());
    } else if (c == '$') {
      return new StaticFormatter<>(infixBuffer.toString());
    } else if ((c == '(') && (!status.inBrace)) {
      status.inBrace = true;
      return null;
    } else if ((c == ')') && (status.inBrace)) {
      status.inBrace = false;
      return null;
    } else {
      throw new IllegalStateException(Character.toString(c));
    }
  }

  @Override
  public VersionIdentifierFormatter createFormatter(String formatPattern, boolean strict) {

    try {
      CharSequenceScanner scanner = new CharSequenceScanner(formatPattern);
      List<Formatter<VersionIdentifier>> subFormatterList = new ArrayList<>();
      return createFormatter(formatPattern, strict, scanner, subFormatterList);
    } catch (Exception e) {
      throw new IllegalArgumentException(formatPattern, e);
    }
  }

  private VersionIdentifierFormatter createFormatter(String formatPattern, boolean strict, CharSequenceScanner scanner,
      List<Formatter<VersionIdentifier>> subFormatterList) {

    FormatPatternStatus status = new FormatPatternStatus();
    StringBuilder infixBuffer = new StringBuilder();
    while (scanner.hasNext()) {
      infixBuffer.append(scanner.readWhile(INFIX_FILTER));
      if (scanner.hasNext()) {
        Formatter<VersionIdentifier> formatter = parseSubFormatter(scanner, formatPattern, infixBuffer, status);
        if (formatter != null) {
          subFormatterList.add(formatter);
          infixBuffer.setLength(0);
        }
      } else if (infixBuffer.length() > 0) {
        subFormatterList.add(new StaticFormatter<VersionIdentifier>(infixBuffer.toString()));
      }
    }
    if (strict && !status.isStrict()) {
      throw new IllegalStateException("strict");
    }
    if (subFormatterList.size() == 0) {
      throw new IllegalStateException("No sub-formatter.");
    }
    @SuppressWarnings("unchecked")
    Formatter<VersionIdentifier>[] subFormatters = new Formatter[subFormatterList.size()];
    return new ComposedVersionIdentifierFormatter(subFormatterList.toArray(subFormatters));
  }

  /**
   * This inner class holds the status used to determine if a
   * {@link VersionUtilImpl#createFormatter(String, boolean) formatPattern} is {@link #isStrict() strict}.
   */
  protected static class FormatPatternStatus {

    /** {@code true} if in open brace ('('), {@code false} otherwise. */
    private boolean inBrace;

    /** Counter for version segments in format pattern. */
    private int versionSegmentsCount;

    /** Counter for phase in format pattern. */
    private int phaseCount;

    /** Counter for phase number in format pattern. */
    private int phaseNumberCount;

    /** Counter for snapshot indicator in format pattern. */
    private int snapshotCount;

    /**
     * @return {@code true} if valid for strict mode, {@code false} otherwise.
     */
    public boolean isStrict() {

      return ((this.versionSegmentsCount == 1) && (this.phaseCount == 1) && (this.phaseNumberCount == 1) && (this.snapshotCount == 1));
    }

  }
}
