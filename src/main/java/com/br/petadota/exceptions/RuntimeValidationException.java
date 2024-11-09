package com.br.petadota.exceptions;


/***
 * Exceção personalizada para validação de regras de negócio ou campos únicos.
 */
public class RuntimeValidationException extends RuntimeException {

	
	private static final long serialVersionUID = -5665361042787556089L;
	private final String timestamp;
	private final String message;
	private final Boolean sucesso;

	public RuntimeValidationException(String message) {
		this.timestamp = java.time.LocalDateTime.now().toString();
		this.message = message;
		this.sucesso = false;
	}

	/**
	 * Exceção personalizada para indicar erro de autenticação ou autorização.
	 * 
	 * Esta exceção será lançada quando uma tentativa de acessar um recurso
	 * protegido for feita sem as credenciais adequadas ou com credenciais
	 * inválidas.
	 */
	public static class UnauthorizedException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		private final String message;

		public UnauthorizedException(String message) {
			super(message);
			this.message = message;
		}

		public UnauthorizedException(String message, Throwable cause) {
			super(message, cause);
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}
	}

	public String getTimestamp() {
		return timestamp;
	}


	@Override
	public String getMessage() {
		return message;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

}

