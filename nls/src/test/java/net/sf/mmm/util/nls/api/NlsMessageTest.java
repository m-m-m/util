/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.test.TestValues;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.CompareOperator;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.AbstractNlsTemplate;
import net.sf.mmm.util.nls.base.AbstractNlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceNoElseConditionException;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceOnlyElseConditionException;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.text.api.Justification;
import net.sf.mmm.util.text.api.UnicodeUtil;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link NlsMessage} and {@link NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsMessageTest extends Assertions {

  protected NlsMessageFactory getMessageFactory() {

    return NlsAccess.getFactory();
  }

  protected NlsBundleFactory getBundleFactory() {

    return NlsAccess.getBundleFactory();
  }

  protected NlsTemplateResolver createResolver(AbstractResourceBundle... bundles) {

    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(bundles);
    resolver.initialize();
    return resolver;
  }

  /**
   * This method tests the {@link net.sf.mmm.util.nls.api.NlsMessage} using a custom resolver.
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
    NlsMessage testMessage = getMessageFactory().create(msg, key, arg);
    assertThat(testMessage.getInternationalizedMessage()).isEqualTo(msg);
    assertThat(testMessage.getArgument(key)).isEqualTo(arg);
    assertThat(testMessage.getMessage()).isEqualTo(hello + arg + suffix);
    AbstractNlsTemplateResolver translatorDe = new AbstractNlsTemplateResolver() {

      @Override
      public NlsTemplate resolveTemplate(String internationalizedMessage) {

        if (internationalizedMessage.equals(msg)) {
          return new GermanTemplate(msgDe);
        }
        return null;
      }
    };
    translatorDe.initialize();
    assertThat(testMessage.getLocalizedMessage(Locale.GERMAN, translatorDe)).isEqualTo(helloDe + arg + suffix);
  }

  @Test
  public void testCascadedMessage() {

    final String integer = "integer";
    final String integerDe = "Ganze Zahl";
    String keyMin = "min";
    String keyMax = "max";
    final String real = "real[{" + keyMin + "},{" + keyMax + "}]";
    final String realDe = "relle Zahl[{" + keyMin + "},{" + keyMax + "}]";
    NlsMessageFactory factory = getMessageFactory();
    NlsMessage simpleMessageInteger = factory.create(integer);
    NlsMessage simpleMessageReal = factory.create(real, keyMin, Double.valueOf(-5), keyMax, Double.valueOf(5));

    String keyExpected = "expectedType";
    String keyActual = "actualType";
    final String err = "The given value must be of the type \"{" + keyExpected + "}\" but has the type \"{" + keyActual + "}\"!";
    final String errDe = "Der angegebene Wert muss vom Typ \"{" + keyExpected + "}\" sein, hat aber den Typ \"{" + keyActual + "}\"!";
    NlsMessage cascadedMessage = factory.create(err, keyExpected, simpleMessageInteger, keyActual, simpleMessageReal);
    AbstractNlsTemplateResolver translatorDe = new AbstractNlsTemplateResolver() {

      @Override
      public NlsTemplate resolveTemplate(String internationalizedMessage) {

        if (internationalizedMessage.equals(integer)) {
          return new GermanTemplate(integerDe);
        } else if (internationalizedMessage.equals(real)) {
          return new GermanTemplate(realDe);
        } else if (internationalizedMessage.equals(err)) {
          return new GermanTemplate(errDe);
        }
        return null;
      }

    };
    translatorDe.initialize();
    String msgDe = cascadedMessage.getLocalizedMessage(Locale.GERMAN, translatorDe);
    assertThat(msgDe).isEqualTo("Der angegebene Wert muss vom Typ \"Ganze Zahl\" sein, hat aber den Typ \"relle Zahl[-5,5]\"!");
  }

  @Test
  public void testMessageWithEnumTranslation() {

    // given
    NlsBundleFactory bundleFactory = getBundleFactory();
    NlsBundleTestRoot bundle = bundleFactory.createBundle(NlsBundleTestRoot.class);
    Integer value = Integer.valueOf(42);

    // when + then
    CompareOperator cmp = CompareOperator.GREATER_OR_EQUAL;
    NlsMessage message = bundle.errorIllegalValue(cmp, value);
    assertThat(message.getLocalizedMessage(Locale.ROOT)).isEqualTo("The value has to be greater or equal to '42'!");
    assertThat(message.getLocalizedMessage(Locale.GERMAN)).isEqualTo("Der Wert muss größer oder gleich '42' sein!");
    assertThat(message.getLocalizedMessage(Locale.FRENCH)).isEqualTo("La valeur doit être supérieur ou égal à '42'!");
    assertThat(message.getLocalizedMessage(TestValues.SPANISH)).isEqualTo("¡El valor tiene que estar mayor qué o igual a '42'!");
  }

  @Test
  public void testMessageDefaultResolver() {

    NlsMessage msg = getMessageFactory().create(MyResourceBundle.MSG_WELCOME, "name", "Paul");
    assertThat(msg.getMessage()).isEqualTo("Welcome \"Paul\"!");
    assertThat(msg.getLocalizedMessage(Locale.GERMAN)).isEqualTo("Willkommen \"Paul\"!");
  }

  @Test
  public void testMessageCustomResolver() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    NlsMessage msg = getMessageFactory().create(MyResourceBundle.MSG_WELCOME, "name", "Paul");
    assertThat(msg.getMessage()).isEqualTo("Welcome \"Paul\"!");
    assertThat(msg.getLocalizedMessage(Locale.GERMAN, resolver)).isEqualTo("Willkommen \"Paul\"!");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_DATE date format}.
   */
  @Test
  public void testMessageTypeDate() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    String iso8601String = "1999-12-31T23:59:59+01:00";
    Date date = Iso8601UtilImpl.getInstance().parseDate(iso8601String);
    NlsMessageFactory factory = getMessageFactory();
    NlsMessage msg = factory.create(MyResourceBundle.MSG_TEST_DATE, "date", date);
    // Make os/locale independent...
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+01:00"));
    String dateString = formatDate(date, AbstractNlsMessage.LOCALE_ROOT);
    assertThat(msg.getMessage()).isEqualTo("Date formatted by locale: " + dateString + ", by ISO-8601: " + iso8601String
        + " and by custom pattern: 1999.12.31-23:59:59+0100!");
    Locale german = Locale.GERMAN;
    String dateStringDe = formatDate(date, german);
    assertThat(msg.getLocalizedMessage(german, resolver)).isEqualTo("Datum formatiert nach Locale: " + dateStringDe + ", nach ISO-8601: "
        + iso8601String + " und nach individueller Vorlage: 1999.12.31-23:59:59+0100!");
    // test custom format
    String customFormat = "yyyyMMdd";
    msg = factory.create("{date,date," + customFormat + "}", "date", date);
    String expected = new SimpleDateFormat(customFormat).format(date);
    // expected="19991231"
    assertThat(msg.getMessage()).isEqualTo(expected);

    String[] types = new String[] { NlsFormatterManager.TYPE_DATE, NlsFormatterManager.TYPE_TIME, NlsFormatterManager.TYPE_DATETIME };
    String[] styles = new String[] { NlsFormatterManager.STYLE_SHORT, NlsFormatterManager.STYLE_MEDIUM, NlsFormatterManager.STYLE_LONG,
    NlsFormatterManager.STYLE_FULL, null };
    int[] dateStyles = new int[] { DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL, DateFormat.MEDIUM };
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
        msg = factory.create(arg, date);
        DateFormat dateFormat;
        if (type == NlsFormatterManager.TYPE_DATE) {
          dateFormat = DateFormat.getDateInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_TIME) {
          dateFormat = DateFormat.getTimeInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_DATETIME) {
          dateFormat = DateFormat.getDateTimeInstance(dateStyle, dateStyle, locale);
        } else {
          throw new IllegalStateException(type);
        }
        String localizedMessage = msg.getLocalizedMessage(locale);
        expected = dateFormat.format(date);
        assertThat(localizedMessage).as("wrong result for message: " + arg + "!").isEqualTo(expected);
        // double check!
        if (type != NlsFormatterManager.TYPE_DATETIME) {
          expected = new MessageFormat(arg, locale).format(new Object[] { date });
          assertThat(localizedMessage).as("wrong result for message: " + arg + "!").isEqualTo(expected);
        }
      }
    }
  }

  private String formatDate(Date date, Locale locale) {

    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale).format(date);
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_NUMBER number format}.
   */
  @Test
  public void testMessageTypeNumber() {

    MyResourceBundle myRB = new MyResourceBundle();
    NlsTemplateResolver resolver = createResolver(myRB);
    Number number = new Double(0.42);
    NlsMessage msg = getMessageFactory().create(MyResourceBundle.MSG_TEST_NUMBER, "value", number);
    assertThat(msg.getMessage().replace(UnicodeUtil.NO_BREAK_SPACE, ' ').replace("42 %", "42%"))
        .isEqualTo("Number formatted by default: 0.42, as percent: 42%, as currency: \u00a4 0.42 and by custom pattern: #0.42!");
    assertThat(msg.getLocalizedMessage(Locale.GERMANY, resolver).replace(UnicodeUtil.NO_BREAK_SPACE, ' ').replace("42 %", "42%"))
        .isEqualTo("Zahl formatiert nach Standard: 0,42, in Prozent: 42%, als Währung: 0,42 \u20ac und nach individueller Vorlage: #0,42!");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_TYPE type format}.
   */
  @Test
  public void testMessageTypeType() throws Exception {

    Method method = GenericClass.class.getMethod("get", ReflectionUtilLimited.NO_PARAMETERS);
    Type type = method.getGenericReturnType();
    String key = "key";
    NlsMessage msg;
    msg = getMessageFactory().create("{" + key + ",type}", key, type);
    assertThat(msg.getMessage()).isEqualTo("java.util.Map");
    msg = getMessageFactory().create("{" + key + ",type,short}", key, type);
    assertThat(msg.getMessage()).isEqualTo("Map");
    msg = getMessageFactory().create("{" + key + ",type,medium}", key, type);
    assertThat(msg.getMessage()).isEqualTo("java.util.Map");
    msg = getMessageFactory().create("{" + key + ",type,long}", key, type);
    assertThat(msg.getMessage())
        .isEqualTo("java.util.Map<java.util.List<? extends String>, java.util.List<java.util.Map<? extends Object, ? super VARIABLE[]>>>");
    msg = getMessageFactory().create("{" + key + ",type,full}", key, type);
    assertThat(msg.getMessage()).isEqualTo(
        "java.util.Map<java.util.List<? extends java.lang.String>, java.util.List<java.util.Map<? extends java.lang.Object, ? super VARIABLE[]>>>");
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
    NlsMessageFactory factory = getMessageFactory();
    msg = factory.create(template, key, Boolean.TRUE);
    assertThat(msg.getMessage()).isEqualTo("foo");
    msg = factory.create(template, key, Boolean.FALSE);
    assertThat(msg.getMessage()).isEqualTo("bar");

    // numeric choice
    template = "{" + key + ",choice,(?==1)'one'(?>1)'many'(?<0)'negative'(else)'zero'}";
    msg = factory.create(template, key, 1);
    assertThat(msg.getMessage()).isEqualTo("one");
    msg = factory.create(template, key, 2);
    assertThat(msg.getMessage()).isEqualTo("many");
    msg = factory.create(template, key, -1);
    assertThat(msg.getMessage()).isEqualTo("negative");
    msg = factory.create(template, key, 0);
    assertThat(msg.getMessage()).isEqualTo("zero");

    // date choice
    template = "{" + key + ",choice,(?==2010-01-31T23:59:59Z)'special day'(?>2010-01-31T23:59:59Z)'after'(else)\"before\"}";
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
    calendar.set(2010, Calendar.JANUARY, 31, 23, 59, 59);
    calendar.set(Calendar.MILLISECOND, 0);
    msg = factory.create(template, key, calendar);
    assertThat(msg.getMessage()).isEqualTo("special day");
    calendar.add(Calendar.SECOND, 1);
    msg = factory.create(template, key, calendar.getTime());
    assertThat(msg.getMessage()).isEqualTo("after");
    calendar.add(Calendar.MINUTE, -1);
    msg = factory.create(template, key, calendar);
    assertThat(msg.getMessage()).isEqualTo("before");

    // string choice
    template = "{" + key + ",choice,(?=='hello')'magic'(?>'hello')'after'(else)'before'}";
    msg = factory.create(template, key, "hello");
    assertThat(msg.getMessage()).isEqualTo("magic");
    msg = factory.create(template, key, "hella");
    assertThat(msg.getMessage()).isEqualTo("before");
    msg = factory.create(template, key, "hellp");
    assertThat(msg.getMessage()).isEqualTo("after");

    // test quotation-symbol
    template = "{" + key + ",choice,(?=='a\"''b')\"a'\"\"b\"(else)''''}";
    msg = factory.create(template, key, "a\"'b");
    assertThat(msg.getMessage()).isEqualTo("a'\"b");

    // test nested choice
    String key2 = "key2";
    String key3 = "key3";
    template = "{" + key + ",choice,(?==true)'foo'(else){" + key2 + ",choice,(?==true)'bar'(else){" + key3 + "}}}";
    msg = factory.create(template, key, Boolean.TRUE);
    assertThat(msg.getMessage()).isEqualTo("foo");
    msg = factory.create(template, key, Boolean.FALSE, key2, Boolean.TRUE);
    assertThat(msg.getMessage()).isEqualTo("bar");
    msg = factory.create(template, key, Boolean.FALSE, key2, Boolean.FALSE, key3, key3);
    assertThat(msg.getMessage()).isEqualTo(key3);

    // test missing else
    try {
      factory.create("{key,choice,(?==true)'foo'}", key, Boolean.TRUE).getMessage();
      ExceptionHelper.failExceptionExpected();
    } catch (Exception e) {
      ExceptionHelper.assertCause(e, NlsFormatterChoiceNoElseConditionException.class);
    }

    // test only else
    try {
      factory.create("{key,choice,(else)'foo'}", key, Boolean.TRUE).getMessage();
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
    NlsMessageFactory factory = getMessageFactory();
    NlsMessage msg = factory.create("{" + key + "{0+4}}", key, value);
    assertThat(msg.getMessage()).isEqualTo("0042");
    // left
    msg = factory.create("{" + key + "{.-4}}", key, value);
    assertThat(msg.getMessage()).isEqualTo("42..");
    // center
    msg = factory.create("{" + key + "{_~11}}", key, value);
    assertThat(msg.getMessage()).isEqualTo("____42_____");
    // combined
    msg = factory.create("Value {" + key + ",number,currency{_+15}}", key, value);
    String message = msg.getLocalizedMessage(Locale.GERMANY);
    message = message.replace(UnicodeUtil.NO_BREAK_SPACE, ' ');
    assertThat(message).isEqualTo("Value ________42,00 €");
  }

  /**
   * A very stupid and insane template implementation for testing.
   */
  private static class GermanTemplate extends AbstractNlsTemplate {

    private static final long serialVersionUID = 1L;

    private final String msgDe;

    public GermanTemplate(String msgDe) {

      super();
      this.msgDe = msgDe;
    }

    @Override
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
