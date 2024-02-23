package br.com.rafaelbiasi.blog.service.impl;

import br.com.rafaelbiasi.blog.model.Role;
import br.com.rafaelbiasi.blog.repository.RoleRepository;
import br.com.rafaelbiasi.blog.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {

    private RoleService roleService;
    private AutoCloseable closeable;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        //GIVEN
        closeable = MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findById() {
        //GIVEN
        Role role = Role.builder().id(1L).build();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        //WHEN
        Optional<Role> roleResponse = roleService.findById(1L);
        //THEN
        Assertions.assertTrue(roleResponse.isPresent());
        Assertions.assertEquals(role, roleResponse.get());
        verify(roleRepository).findById(1L);
    }

    @Test
    void save() {
        //GIVEN
        Role role = Role.builder().id(1L).build();
        when(roleRepository.save(role)).thenReturn(role);
        //WHEN
        Role roleResponse = roleService.save(role);
        //THEN
        Assertions.assertEquals(role, roleResponse);
        verify(roleRepository).save(role);
    }

    //@Test
    void template() {
        //GIVEN
        //WHEN
        //THEN
    }
}