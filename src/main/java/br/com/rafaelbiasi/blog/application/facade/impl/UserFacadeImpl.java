package br.com.rafaelbiasi.blog.application.facade.impl;

import br.com.rafaelbiasi.blog.application.data.RoleData;
import br.com.rafaelbiasi.blog.application.data.UserData;
import br.com.rafaelbiasi.blog.application.facade.UserFacade;
import br.com.rafaelbiasi.blog.application.mapper.RoleMapper;
import br.com.rafaelbiasi.blog.application.mapper.UserMapper;
import br.com.rafaelbiasi.blog.core.domain.model.PageRequestModel;
import br.com.rafaelbiasi.blog.core.domain.model.RegistrationResponseModel;
import br.com.rafaelbiasi.blog.core.domain.model.UserModel;
import br.com.rafaelbiasi.blog.core.domain.service.RoleService;
import br.com.rafaelbiasi.blog.core.domain.service.UserService;
import br.com.rafaelbiasi.blog.infrastructure.util.SqidsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Page<UserData> findAll(Pageable pageable) {
		requireNonNull(pageable, "The Pageable has a null value.");
		val map = userService.findAll(PageRequestModel.of(
				pageable.getPageNumber(),
				pageable.getPageSize()
		)).map(userMapper::toData);
		val pageRequestModel = map.getPageable();
		return new PageImpl<>(map.content(), PageRequest.of(
				pageRequestModel.pageNumber(),
				pageRequestModel.pageSize()
		), map.total());
	}

	@Override
	public Optional<UserData> findByCode(String code) {
		requireNonNull(code, "The Code has a null value.");
		return userService.findById(SqidsUtil.decodeId(code))
				.map(userMapper::toData);
	}

	@Override
	public Optional<UserData> findOneByEmail(final String email) {
		requireNonNull(email, "The E-mail has a null value.");
		return userService.findOneByEmail(email)
				.map(userMapper::toData);
	}

	@Override
	public Optional<UserData> findOneByUsername(final String username) {
		requireNonNull(username, "The Username has a null value.");
		return userService.findOneByUsername(username)
				.map(userMapper::toData);
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
	public RegistrationResponseModel checkEmailAndUsernameExists(final UserData userData) {
		requireNonNull(userData, "The User has a null value.");
		return userService.checkEmailAndUsernameExists(userMapper.toModel(userData));
	}

	@Override
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

	private UserData update(final UserData userData, final UserModel user) {
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
