package br.com.rafaelbiasi.blog;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

	private static final int strength = 12;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);


	public static void main(String[] args) {
		encode("resu");
		encode("nimda");
		encode("tseug");
	}

	private static void encode(String password) {
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println("Encoded Password: " + encodedPassword);
	}
}
