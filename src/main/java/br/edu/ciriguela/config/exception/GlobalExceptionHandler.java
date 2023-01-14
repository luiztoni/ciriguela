package br.edu.ciriguela.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.ciriguela.config.locale.ErrorMessage;
import br.edu.ciriguela.config.locale.MessageProperties;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	private MessageProperties messageProperties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException exception) {
		String error = String.format(messageProperties.get(ErrorMessage.INTERNAL_SERVER_ERROR), exception.getMessage());
		LOGGER.error("BusinessException: [{}]", exception.getMessage());
		return ResponseEntity.internalServerError().body(new CustomErrorResponse(LocalDateTime.now(), 500, error));
	}

}
