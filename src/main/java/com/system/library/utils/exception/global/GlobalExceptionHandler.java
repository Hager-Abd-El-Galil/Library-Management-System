package com.system.library.utils.exception.global;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.system.library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.library.utils.exception.enums.ApiExceptionEnum;
import com.system.library.utils.exception.impl.BusinessLogicViolationException;
import com.system.library.utils.exception.model.ExceptionHandlerResModel;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.GENERAL_ERROR.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.RUN_TIME_EXCEPTION.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ BusinessLogicViolationException.class })
	public ResponseEntity<Object> handleBusinessLogicException(BusinessLogicViolationException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.BUSINESS_CONSTRAINT_VIOLATION.name(), ex.getMessage(), ex.getDetails());
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.ACCESS_IS_DENIED.name(), ApiErrorMessageEnum.ACCESS_IS_DENIED.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.NULL_POINTER_EXCEPTION.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		List<Map<String, String>> details = new ArrayList<Map<String, String>>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage().toUpperCase());
			details.add(fieldErrorMap);
		}

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.BAD_REQUEST.name(), ApiErrorMessageEnum.METHOD_ARGUMENTS_NOT_VALID.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

		List<Map<String, String>> details = new ArrayList<Map<String, String>>();
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put(ex.getName(), ex.getMessage().toUpperCase());
		details.add(errorMap);

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.BAD_REQUEST.name(), ApiErrorMessageEnum.PARAMETER_TYPE_MISS_MATCH.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleNullPointerException(HttpRequestMethodNotSupportedException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.METHOD_NOT_ALLOWED.name(), ApiErrorMessageEnum.METHOD_NOT_ALLOWED.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

		List<Map<String, String>> details = new ArrayList<Map<String, String>>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put(violation.getPropertyPath().toString(), violation.getMessage().toUpperCase());
			details.add(errorMap);
		}

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.BAD_REQUEST.name(), ApiErrorMessageEnum.GENERAL_ERROR.name(), details);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public ResponseEntity<Object> incorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(),
				ApiErrorMessageEnum.INCORRECT_RESULT_SIZE_DATA_ACCESS.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleUpdatedElementNotFoundException(NoSuchElementException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.REFRENCE_ID_NOT_FOUND.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ CannotCreateTransactionException.class })
	public ResponseEntity<Object> createTransactionExceptionHandler(CannotCreateTransactionException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(), ApiErrorMessageEnum.JDBC_CONNECTON_ERROR.name(), null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<Object> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.MEDIA_TYPE_NOT_ACCEPTED.name(), ApiErrorMessageEnum.MEDIA_TYPE_NOT_ACCEPTED.name(),
				null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = new ExceptionHandlerResModel(
				ApiExceptionEnum.MEDIA_TYPE_NOT_SUPPORTED.name(), ApiErrorMessageEnum.MEDIA_TYPE_NOT_SUPPORTED.name(),
				null);
		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

//	Need to Complete
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handlePersistenceException(DataIntegrityViolationException ex) {

		ExceptionHandlerResModel exceptionHandlerResModel = null;
		Throwable rootCause = ex.getRootCause();

		if (rootCause != null && (rootCause instanceof SQLIntegrityConstraintViolationException
				|| rootCause instanceof SQLException)) {
			int exceptionCode = ((SQLException) rootCause).getErrorCode();
			exceptionHandlerResModel = SQLIntegrityConstraintViolation(rootCause.getMessage(), exceptionCode);

		} else if (rootCause instanceof ConstraintViolationException) {
			exceptionHandlerResModel = new ExceptionHandlerResModel(ApiExceptionEnum.INTERNAL_SERVER_ERROR.name(),
					ApiErrorMessageEnum.DATABASE_CONSTRAINT_VIOLATION.name(), null);
		}

		logger.error("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(exceptionHandlerResModel, HttpStatus.UNPROCESSABLE_ENTITY);

	}

//	For SQL DATABASE
	private ExceptionHandlerResModel SQLIntegrityConstraintViolation(String exceptionMessage, int exceptionCode) {

		String message = null;
		String exceptionType = null;

		if (exceptionCode == 1062) {// Unique Key
			message = exceptionMessage.toUpperCase();
			exceptionType = ApiExceptionEnum.UNIQUE_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1048) {// Not Null
			message = exceptionMessage.toUpperCase();
			exceptionType = ApiExceptionEnum.INTERNAL_SERVER_ERROR.name();
		} else if (exceptionCode == 1452) {// Create And FK Not Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			exceptionType = ApiExceptionEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1451) {// Delete And Child Record Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			exceptionType = ApiExceptionEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionCode == 1406) {// Data too long for column
			message = exceptionMessage.toUpperCase();
			exceptionType = ApiExceptionEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		}

		return new ExceptionHandlerResModel(exceptionType, message, null);

	}

	private String getConstraintKeyFromExceptionMessage(String prefix, String exceptionMessage) {
		Matcher matcher = Pattern.compile(prefix + "(\\w+)").matcher(exceptionMessage);
		return matcher.find() ? matcher.group() : "";
	}

}
