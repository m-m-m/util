/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import junit.framework.Assert;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.base.AbstractNlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.FormattedNlsTemplate;
import net.sf.mmm.util.nls.impl.NlsFormatterChoiceNoElseConditionException;
import net.sf.mmm.util.nls.impl.NlsFormatterChoiceOnlyElseConditionException;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.text.api.Justification;

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
   * This method tests the {@link net.sf.mmm.util.nls.api.NlsMessage} using a
   * custom resolver.
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
    Assert.assertEquals(helloDe + arg + suffix,
        testMessage.getLocalizedMessage(Locale.GERMAN, translatorDe));
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

  /**
   * Tests {@link NlsFormatterManager#TYPE_DATE date format}.
   */
  @Test
  public void testMessageTypeDate() {

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

    String[] types = new String[] { NlsFormatterManager.TYPE_DATE, NlsFormatterManager.TYPE_TIME,
        NlsFormatterManager.TYPE_DATETIME };
    String[] styles = new String[] { NlsFormatterManager.STYLE_SHORT,
        NlsFormatterManager.STYLE_MEDIUM, NlsFormatterManager.STYLE_LONG,
        NlsFormatterManager.STYLE_FULL, null };
    int[] dateStyles = new int[] { DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG,
        DateFormat.FULL, DateFormat.MEDIUM };
    Locale locale = Locale.GERMANY;
    for (String type : types) {
      for (int styleIndex = 0; styleIndex < styles.length; styleIndex++) {
        String style = styles[styleIndex];
        int dateStyle = dateStyles[styleIndex];
        String arg = "{0," + type;
        if (style != null) {
          arg = arg + "," + style;
        }
        arg = arg + "}";
        msg = NlsAccess.getFactory().create(arg, date);
        DateFormat dateFormat;
        if (type == NlsFormatterManager.TYPE_DATE) {
          dateFormat = DateFormat.getDateInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_TIME) {
          dateFormat = DateFormat.getTimeInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_DATETIME) {
          dateFormat = DateFormat.getDateTimeInstance(dateStyle, dateStyle, locale);
        } else {
          throw new IllegalCaseException(type);
        }
        String localizedMessage = msg.getLocalizedMessage(locale);
        expected = dateFormat.format(date);
        Assert.assertEquals("wrong result for message: " + arg + "!", expected, localizedMessage);
        // double check!
        if (type != NlsFormatterManager.TYPE_DATETIME) {
          expected = new MessageFormat(arg, locale).format(new Object[] { date });
          Assert.assertEquals("wrong result for message: " + arg + "!", expected, localizedMessage);
        }
      }
    }
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_NUMBER number format}.
   */
  @Test
  public void testMessageTypeNumber() {

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
   * Tests {@link NlsFormatterManager#TYPE_TYPE type format}.
   */
  @Test
  public void testMessageTypeType() throws Exception {

    Method method = GenericClass.class.getMethod("get", ReflectionUtil.NO_PARAMETERS);
    Type type = method.getGenericReturnType();
    String key = "key";
    NlsMessage msg;
    msg = NlsAccess.getFactory().create("{" + key + ",type}", key, type);
    Assert.assertEquals("java.util.Map", msg.getMessage());
    msg = NlsAccess.getFactory().create("{" + key + ",type,short}", key, type);
    Assert.assertEquals("Map", msg.getMessage());
    msg = NlsAccess.getFactory().create("{" + key + ",type,medium}", key, type);
    Assert.assertEquals("java.util.Map", msg.getMessage());
    msg = NlsAccess.getFactory().create("{" + key + ",type,long}", key, type);
    Assert.assertEquals(
        "java.util.Map<java.util.List<? extends String>, java.util.List<java.util.Map<? "
            + "extends Object, ? super VARIABLE[]>>>", msg.getMessage());
    msg = NlsAccess.getFactory().create("{" + key + ",type,full}", key, type);
    Assert.assertEquals("java.util.Map<java.util.List<? extends java.lang.String>, "
        + "java.util.List<java.util.Map<? extends java.lang.Object, ? super VARIABLE[]>>>",
        msg.getMessage());
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_NUMBER number format}.
   */
  @Test
  public void testMessageTypeChoice() {

    String key = "key";
    NlsMessage msg;
    String template;

    // boolean choice
    template = "{" + key + ",choice,(?==true)'foo'(else)'bar'}";
    msg = NlsAccess.getFactory().create(template, key, Boolean.TRUE);
    Assert.assertEquals("foo", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, Boolean.FALSE);
    Assert.assertEquals("bar", msg.getMessage());

    // numeric choice
    template = "{" + key + ",choice,(?==1)'one'(?>1)'many'(?<0)'negative'(else)'zero'}";
    msg = NlsAccess.getFactory().create(template, key, 1);
    Assert.assertEquals("one", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, 2);
    Assert.assertEquals("many", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, -1);
    Assert.assertEquals("negative", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, 0);
    Assert.assertEquals("zero", msg.getMessage());

    // date choice
    template = "{"
        + key
        + ",choice,(?==2010-01-31T23:59:59Z)'special day'(?>2010-01-31T23:59:59Z)'after'(else)\"before\"}";
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
    calendar.set(2010, Calendar.JANUARY, 31, 23, 59, 59);
    calendar.set(Calendar.MILLISECOND, 0);
    msg = NlsAccess.getFactory().create(template, key, calendar);
    Assert.assertEquals("special day", msg.getMessage());
    calendar.add(Calendar.SECOND, 1);
    msg = NlsAccess.getFactory().create(template, key, calendar.getTime());
    Assert.assertEquals("after", msg.getMessage());
    calendar.add(Calendar.MINUTE, -1);
    msg = NlsAccess.getFactory().create(template, key, calendar);
    Assert.assertEquals("before", msg.getMessage());

    // string choice
    template = "{" + key + ",choice,(?=='hello')'magic'(?>'hello')'after'(else)'before'}";
    msg = NlsAccess.getFactory().create(template, key, "hello");
    Assert.assertEquals("magic", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, "hella");
    Assert.assertEquals("before", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, "hellp");
    Assert.assertEquals("after", msg.getMessage());

    // test quotation-symbol
    template = "{" + key + ",choice,(?=='a\"''b')\"a'\"\"b\"(else)''''}";
    msg = NlsAccess.getFactory().create(template, key, "a\"'b");
    Assert.assertEquals("a'\"b", msg.getMessage());

    // test nested choice
    String key2 = "key2";
    String key3 = "key3";
    template = "{" + key + ",choice,(?==true)'foo'(else){" + key2 + ",choice,(?==true)'bar'(else){"
        + key3 + "}}}";
    msg = NlsAccess.getFactory().create(template, key, Boolean.TRUE);
    Assert.assertEquals("foo", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, Boolean.FALSE, key2, Boolean.TRUE);
    Assert.assertEquals("bar", msg.getMessage());
    msg = NlsAccess.getFactory().create(template, key, Boolean.FALSE, key2, Boolean.FALSE, key3,
        key3);
    Assert.assertEquals(key3, msg.getMessage());

    // test missing else
    try {
      NlsAccess.getFactory().create("{key,choice,(?==true)'foo'}", key, Boolean.TRUE).getMessage();
      ExceptionHelper.failExceptionExpected();
    } catch (Exception e) {
      ExceptionHelper.assertCause(e, NlsFormatterChoiceNoElseConditionException.class);
    }

    // test only else
    try {
      NlsAccess.getFactory().create("{key,choice,(else)'foo'}", key, Boolean.TRUE).getMessage();
      ExceptionHelper.failExceptionExpected();
    } catch (Exception e) {
      ExceptionHelper.assertCause(e, NlsFormatterChoiceOnlyElseConditionException.class);
    }
  }

  /**
   * Tests {@link NlsMessage message} with {@link Justification}.
   */
  @Test
  public void testMessageWithJustification() {

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

  /**
   * This is a dummy class to get a complex generic type.
   */
  private static class GenericClass<VARIABLE> {

    public Map<List<? extends String>, List<Map<?, ? super VARIABLE[]>>> get() {

      return null;
    }
  }
}
