package com.system.library.utils.exception.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionHandlerResModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3195733906115407159L;

	private String exceptionType;

	private String message;

	private List<Map<String, String>> details;
}
