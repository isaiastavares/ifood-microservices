package br.com.ifood.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<String>> validateError(ConstraintViolationException ex) {
		return ResponseEntity.badRequest().body(ex.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage).collect(Collectors.toList()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> otherErrors(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParameters(MissingServletRequestParameterException ex) {
		String param = ex.getParameterName();
		return ResponseEntity.badRequest().body("Missing parameter: " + param);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String type = ex.getRequiredType().getSimpleName();
		String param = ex.getParameter().getParameterName();
		return ResponseEntity.badRequest().body("Wrong type for parameter: " + param + ". Expected type: " + type);
	}

}
