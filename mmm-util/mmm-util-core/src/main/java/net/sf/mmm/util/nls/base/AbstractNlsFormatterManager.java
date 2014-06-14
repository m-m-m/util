/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterManagerImpl;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.text.api.Justification;
import net.sf.mmm.util.text.api.JustificationBuilder;
import net.sf.mmm.util.text.base.JustificationBuilderImpl;

/**
 * This is the abstract base implementation of the {@link NlsFormatterManager} interface.<br>
 * You should extend this class rather than directly implementing the {@link NlsFormatterManager} interface to
 * gain compatibility with further releases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatterManager extends AbstractLoggableComponent implements NlsFormatterManager,
    NlsArgumentParser {

  /** A char filter that accepts everything except ',' and '}'. */
  protected static final CharFilter NO_COMMA_OR_END_EXPRESSION = new ListCharFilter(false,
      NlsArgumentParser.FORMAT_SEPARATOR, NlsArgumentParser.END_EXPRESSION);

  /** A char filter that accepts everything except ',' and '}'. */
  protected static final CharFilter NO_EXPRESSION = new ListCharFilter(false, NlsArgumentParser.START_EXPRESSION,
      NlsArgumentParser.END_EXPRESSION);

  /** @see #getInstance() */
  private static AbstractNlsFormatterManager instance;

  /** @see #getJustificationBuilder() */
  private JustificationBuilder justificationBuilder;

  /**
   * The constructor.
   */
  public AbstractNlsFormatterManager() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link AbstractNlsFormatterManager}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static AbstractNlsFormatterManager getInstance() {

    if (instance == null) {
      new NlsFormatterManagerImpl().initialize();
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialized() {

    super.doInitialized();
    synchronized (AbstractNlsFormatterManager.class) {
      if (instance == null) {
        instance = this;
      } else if (instance != this) {
        getLogger().warn("Duplicate instances {} and {} (getInstance() vs. IoC)", instance, this);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public NlsArgument parse(CharSequenceScanner scanner) {

    String key = scanner.readWhile(CharFilter.IDENTIFIER_FILTER);
    char c = scanner.next();
    int index = scanner.getCurrentIndex();
    String formatType = null;
    NlsFormatter<?> formatter = null;
    if (c == NlsArgumentParser.FORMAT_SEPARATOR) {
      formatType = scanner.readWhile(NO_COMMA_OR_END_EXPRESSION);
      index = scanner.getCurrentIndex();
      c = scanner.forceNext();
      if (c == NlsArgumentParser.FORMAT_SEPARATOR) {
        index = scanner.getCurrentIndex();
        try {
          formatter = getSubFormatter(formatType, scanner);
        } catch (Exception e) {
          @SuppressWarnings("rawtypes")
          Class type = NlsFormatter.class;
          throw new NlsParseException(e, scanner.substring(index, scanner.getCurrentIndex()), type);
        }
        c = scanner.forceNext();
      } else {
        formatter = getFormatter(formatType);
      }
    }
    Justification justification = null;
    if (c == NlsArgumentParser.START_EXPRESSION) {
      String formatJustification = scanner.readUntil(NlsArgumentParser.END_EXPRESSION, false);
      justification = getJustificationBuilder().build(formatJustification);
      c = scanner.forceNext();
    }
    if (c != NlsArgumentParser.END_EXPRESSION) {
      throw new NlsParseException(scanner.substring(index, scanner.getCurrentIndex()), ""
          + NlsArgumentParser.END_EXPRESSION);
    }
    if (formatter == null) {
      formatter = getFormatter();
    }
    return new NlsArgument(key, formatter, justification);
  }

  /**
   * This method is like {@link #getFormatter(String, String)} but reads the
   * {@link AbstractNlsFormatterPlugin#getStyle() style} from the given scanner.
   * 
   * @param formatType is the type to be formatted.
   * @param scanner is the current {@link CharSequenceScanner} for parsing the style defining details of
   *        formatting.
   * @return the according {@link NlsFormatter}.
   */
  protected NlsFormatter<?> getSubFormatter(String formatType, CharSequenceScanner scanner) {

    String formatStyle = scanner.readWhile(NO_EXPRESSION);
    return getFormatter(formatType, formatStyle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.justificationBuilder == null) {
      this.justificationBuilder = JustificationBuilderImpl.getInstance();
    }
  }

  /**
   * This method gets the {@link JustificationBuilder} used to {@link JustificationBuilder#build(String)
   * build} {@link Justification}s.
   * 
   * @return the {@link JustificationBuilder}.
   */
  protected JustificationBuilder getJustificationBuilder() {

    return this.justificationBuilder;
  }

  /**
   * @param justificationBuilder is the justificationBuilder to set
   */
  @Inject
  public void setJustificationBuilder(JustificationBuilder justificationBuilder) {

    getInitializationState().requireNotInitilized();
    this.justificationBuilder = justificationBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public NlsFormatter<Object> getFormatter() {

    return (NlsFormatter<Object>) getFormatter(null);
  }

  /**
   * {@inheritDoc}
   */
  public NlsFormatter<?> getFormatter(String formatType) {

    return getFormatter(formatType, null);
  }

}
