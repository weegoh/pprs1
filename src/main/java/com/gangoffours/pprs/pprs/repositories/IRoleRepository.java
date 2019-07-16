package com.gangoffours.pprs.pprs.repositories;

import java.util.Collection;

import com.gangoffours.pprs.pprs.models.Role;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Scope(value = "request")
@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
    Collection<Role> findAll();
}