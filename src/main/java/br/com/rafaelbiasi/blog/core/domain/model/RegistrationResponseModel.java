package br.com.rafaelbiasi.blog.core.domain.model;

public record RegistrationResponseModel(
		boolean usernameExists,
		boolean emailExists) {

	public static RegistrationResponseModelBuilder builder() {
		return new RegistrationResponseModelBuilder();
	}

	public boolean success() {
		return !usernameExists && !emailExists;
	}

	public static class RegistrationResponseModelBuilder {

		private boolean usernameExists;
		private boolean emailExists;

		private RegistrationResponseModelBuilder() {
		}

		public RegistrationResponseModelBuilder usernameExists(boolean usernameExists) {
			this.usernameExists = usernameExists;
			return this;
		}

		public RegistrationResponseModelBuilder emailExists(boolean emailExists) {
			this.emailExists = emailExists;
			return this;
		}

		public RegistrationResponseModel build() {
			return new RegistrationResponseModel(this.usernameExists, this.emailExists);
		}

		public String toString() {
			return "RegistrationResponseModel.RegistrationResponseModelBuilder(usernameExists=" + this.usernameExists + ", emailExists=" + this.emailExists + ")";
		}
	}
}
