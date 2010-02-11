/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.Assert;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.base.AbstractNlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.FormattedNlsTemplate;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;

import org.junit.Test;

/**
 * This is the test-case for {@link net.sf.mmm.util.nls.NlsMessageImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsMessageTest {

  /**
   * The constructor.
   */
  public NlsMessageTest() {

    super();
  }

  protected NlsTemplateResolver createResolver(AbstractResourceBundle... bundles) {

    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(bundles);
    resolver.initialize();
    return resolver;
  }

  /**
   * This method tests the {@link net.sf.mmm.util.nls.api.NlsMessage}
   * implementation ({@link net.sf.mmm.util.nls.NlsMessageImpl}).
   */
  @Test
  public void testMessage() {

    String hello = "Hello";
    String helloDe = "Hallo ";
    String arg = "Joelle";
    String suffix = "!";
    String key = "name";
    final String msg = hello + "{" + key + "}" + suffix;
    final String msgDe = helloDe + "{" + key + "}" + suffix;
    NlsMessage testMessage = NlsAccess.getFactory().create(msg, key, arg);
    Assert.assertEquals(msg, testMessage.getInternationalizedMessage());
    Assert.assertEquals(arg, testMessage.getArgument(key));
    Assert.assertEquals(hello + arg + suffix, testMessage.getMessage());
    AbstractNlsTemplateResolver translatorDe = new AbstractNlsTemplateResolver() {

      public NlsTemplate resolveTemplate(String internationalizedMessage) {

        if (internationalizedMessage.equals(msg)) {
          return new GermanTemplate(msgDe, getArgumentParser());
        }
        return null;
      }
    };
    translatorDe.initialize();
    Assert.assertEquals(helloDe + arg + suffix, testMessage.getLocalizedMessage(Locale.GERMAN,
        translatorDe));
  }

  @Test
  public void testCascadedMessage() {

    final String integer = "integer";
    final String integerDe = "Ganze Zahl";
    String keyMin = "min";
    String keyMax = "max";
    final String real = "real[{" + keyMin + "},{" + keyMax + "}]";
    final String realDe = "relle Zahl[{" + keyMin + "},{" + keyMax + "}]";
    NlsMessage simpleMessageInteger = NlsAccess.getFactory().create(integer);
    NlsMessage simpleMessageReal = NlsAccess.getFactory().create(real, keyMin, Double.valueOf(-5),
        keyMax, Double.valueOf(5));

    String keyExpected = "expectedType";
    String keyActual = "actualType";
    final String err = "The given value must be of the type \"{" + keyExpected
        + "}\" but has the type \"{" + keyActual + "}\"!";
    final String errDe = "Der angegebene Wert muss vom Typ \"{" + keyExpected
        + "}\" sein, hat aber den Typ \"{" + keyActual + "}\"!";
    NlsMessage cascadedMessage = NlsAccess.getFactory().create(err, keyExpected,
        simpleMessageInteger, keyActual, simpleMessageReal);
    AbstractNlsTemplateResolver translatorDe = new AbstractNlsTemplateResolver() {

      public NlsTemplate resolveTemplate(String internationalizedMessage) {

        if (internationalizedMessage.equals(integer)) {
          return new GermanTemplate(integerDe, getArgumentParser());
        } else if (internationalizedMessage.equals(real)) {
          return new GermanTemplate(realDe, getArgumentParser());
        } else if (internationalizedMessage.equals(err)) {
          return new GermanTemplate(errDe, getArgumentParser());
        }
        return null;
      }

    };
    translatorDe.initialize();
    String msgDe = cascadedMessage.getLocalizedMessage(Locale.GERMAN, translatorDe);
    Assert.assertEquals("Der angegebene Wert muss vom Typ \"Ganze Zahl\" sein, "
        + "hat aber den Typ \"relle Zahl[-5,5]\"!", msgDe);
  }

  @Test
  public void testMessageResolved() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    NlsMessage msg = NlsAccess.getFactory().create(MyResourceBundle.MSG_WELCOME, "Paul");
    Assert.assertEquals("Welcome \"Paul\"!", msg.getMessage());
    Assert.assertEquals("Willkommen \"Paul\"!", msg.getLocalizedMessage(Locale.GERMAN, resolver));
  }

  @Test
  public void testMessageFormatDate() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    Date date = Iso8601UtilImpl.getInstance().parseDate("1999-12-31T23:59:59+01:00");
    NlsMessage msg = NlsAccess.getFactory().create(MyResourceBundle.MSG_TEST_DATE, date);
    // Make os/locale independent...
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+01:00"));
    Assert
        .assertEquals(
            "Date formatted by locale: 12/31/99 11:59 PM, by ISO-8601: 1999-12-31T23:59:59+01:00 and by custom pattern: 1999.12.31-23:59:59+0100!",
            msg.getMessage());
    Assert
        .assertEquals(
            "Datum formatiert nach Locale: 31.12.99 23:59, nach ISO-8601: 1999-12-31T23:59:59+01:00 und nach individueller Vorlage: 1999.12.31-23:59:59+0100!",
            msg.getLocalizedMessage(Locale.GERMAN, resolver));
    // test custom format
    String customFormat = "yyyyMMdd";
    msg = NlsAccess.getFactory().create("{date,date," + customFormat + "}", "date", date);
    String expected = new SimpleDateFormat(customFormat).format(date);
    // expected="19991231"
    Assert.assertEquals(expected, msg.getMessage());
  }

  @Test
  public void testMessageFormatNumber() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    Number number = new Double(0.42);
    NlsMessage msg = NlsAccess.getFactory().create(MyResourceBundle.MSG_TEST_NUMBER, number);
    Assert
        .assertEquals(
            "Number formatted by default: 0.42, as percent: 42%, as currency: \u00a4 0.42 and by custom pattern: #0.42!",
            msg.getMessage());
    Assert
        .assertEquals(
            "Zahl formatiert nach Standard: 0,42, in Prozent: 42%, als Währung: 0,42 \u20ac und nach individueller Vorlage: #0,42!",
            msg.getLocalizedMessage(Locale.GERMANY, resolver));
  }

  /**
   * 
   */
  @Test
  public void testMessageFormatJustification() {

    String key = "value";
    Integer value = Integer.valueOf(42);

    // right
    NlsMessage msg = NlsAccess.getFactory().create("{" + key + "{0+4}}", key, value);
    Assert.assertEquals("0042", msg.getMessage());
    // left
    msg = NlsAccess.getFactory().create("{" + key + "{.-4}}", key, value);
    Assert.assertEquals("42..", msg.getMessage());
    // center
    msg = NlsAccess.getFactory().create("{" + key + "{_~11}}", key, value);
    Assert.assertEquals("____42_____", msg.getMessage());
    // combined
    msg = NlsAccess.getFactory().create("Value {" + key + ",number,currency{_+15}}", key, value);
    String message = msg.getLocalizedMessage(Locale.GERMANY);
    Assert.assertEquals("Value ________42,00 €", message);
  }

  /**
   * A very stupid and insane template implementation for testing.
   */
  private static class GermanTemplate extends FormattedNlsTemplate {

    private final String msgDe;

    public GermanTemplate(String msgDe, NlsArgumentParser argumentParser) {

      super(argumentParser);
      this.msgDe = msgDe;
    }

    /**
     * {@inheritDoc}
     */
    public String translate(Locale locale) {

      if ("de".equals(locale.getLanguage())) {
        return this.msgDe;
      }
      return null;
    }

  }
}
