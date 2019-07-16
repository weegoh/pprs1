package com.gangoffours.pprs.pprs.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import com.gangoffours.pprs.pprs.models.User;

@Scope(value = "request")
@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Collection<User> findAll();
}