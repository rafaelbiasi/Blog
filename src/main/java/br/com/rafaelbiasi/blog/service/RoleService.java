package br.com.rafaelbiasi.blog.service;

import br.com.rafaelbiasi.blog.model.Role;

/**
 * Defines the contract for services managing {@link Role} entities.
 * This interface extends {@link EntityService} to provide role-specific operations,
 * such as saving role entities. An {@link Role} typically represents a role or permission
 * assigned to users or accounts within the system.
 */
public interface RoleService extends EntityService<Role> {

    /**
     * Saves an {@link Role} entity to the persistent store.
     * This method can be used for creating new role or updating existing ones.
     * The implementation should ensure that the role is persisted and properly managed
     * within the underlying data storage mechanism.
     *
     * @param role the {@link Role} entity to save
     * @return the saved {@link Role} entity, including any generated or modified fields
     */
    Role save(Role role);

}
