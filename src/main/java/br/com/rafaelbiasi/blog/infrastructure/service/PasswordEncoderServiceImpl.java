package br.com.rafaelbiasi.blog.infrastructure.service;

import br.com.rafaelbiasi.blog.core.service.PasswordEncoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

	private final PasswordEncoder passwordEncoder;

	@Override
	public String encode(String password) {
		return passwordEncoder.encode(password);
	}
}
