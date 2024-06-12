package com.appsdeveloperblog.tutorials.junit.datalayer;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    List<UserEntity> findByLastNameStartsWith(String name);
}
