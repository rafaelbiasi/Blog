package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.UserFacade;
import br.com.rafaelbiasi.blog.application.mapper.RoleMapper;
import br.com.rafaelbiasi.blog.application.mapper.UserMapper;
import br.com.rafaelbiasi.blog.core.model.User;
import br.com.rafaelbiasi.blog.core.service.RoleService;
import br.com.rafaelbiasi.blog.core.service.UserService;
import br.com.rafaelbiasi.blog.core.vo.RegistrationResponse;
import br.com.rafaelbiasi.blog.core.vo.SimplePage;
import br.com.rafaelbiasi.blog.core.vo.SimplePageRequest;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

	private final UserService userService;
	private final RoleService roleService;
	private final UserMapper userMapper;
	private final RoleMapper roleMapper;

	@Override
	public SimplePage<UserData> findAll(SimplePageRequest pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		return userService.findAll(pageable).map(userMapper::toData);
	}

	@Override
	public Optional<UserData> findByCode(String code) {
		requireNonNull(code, "The Code has a null value.");
		return userService.findById(SqidsUtil.decodeId(code)).map(userMapper::toData);
	}

	@Override
	public Optional<UserData> findOneByEmail(final String email) {
		requireNonNull(email, "The E-mail has a null value.");
		return userService.findOneByEmail(email).map(userMapper::toData);
	}

	@Override
	public Optional<UserData> findOneByUsername(final String username) {
		requireNonNull(username, "The Username has a null value.");
		return userService.findOneByUsername(username).map(userMapper::toData);
	}

	@Override
	public UserData save(final UserData userData) {
		requireNonNull(userData, "The User has a null value.");
		return ofNullable(userData.getCode())
				.map(SqidsUtil::decodeId)
				.flatMap(userService::findById)
				.map(user -> update(userData, user))
				.orElseGet(() -> create(userData));
	}

	@Override
	public void registerUser(final UserData userData) {
		requireNonNull(userData, "The User has a null value.");
		userService.registerUser(userMapper.toModel(userData));
	}

	@Override
	public RegistrationResponse checkEmailAndUsernameExists(final UserData userData) {
		requireNonNull(userData, "The User has a null value.");
		return userService.checkEmailAndUsernameExists(userMapper.toModel(userData));
	}

	@Override
	@PreAuthorize("@securityHelper.canDeleteUser(#code, authentication)")
	public boolean delete(final String code) {
		requireNonNull(code, "The Code has a null value.");
		val user = userService.findById(SqidsUtil.decodeId(code));
		user.ifPresent(userService::delete);
		return user.isPresent();
	}

	@Override
	public List<RoleData> listAllRoles() {
		return roleService.findAll().stream().map(roleMapper::toData).toList();
	}

	private UserData update(final UserData userData, final User user) {
		requireNonNull(userData, "The User has a null value.");
		userMapper.updateModelFromData(userData, user);
		val updatedPost = userService.save(user);
		return userMapper.toData(updatedPost);
	}

	private UserData create(final UserData userData) {
		requireNonNull(userData, "The User has a null value.");
		val createdPost = userService.save(userMapper.toModel(userData));
		return userMapper.toData(createdPost);
	}
}
