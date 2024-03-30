package br.edu.ciriguela.config.locale;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageProperties {

  @Autowired
  private MessageSource messageSource;

  public String get(ErrorMessage message, Locale locale) {
    return messageSource.getMessage(message.toString(), null, locale);
  }

  public String get(ErrorMessage message) {
    return messageSource.getMessage(message.toString(), null, LocaleContextHolder.getLocale());
  }
  
}