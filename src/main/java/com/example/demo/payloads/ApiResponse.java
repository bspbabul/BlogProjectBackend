package com.example.demo.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ApiResponse {

	private String message;
	private boolean success;
}
