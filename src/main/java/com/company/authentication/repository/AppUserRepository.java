package com.company.authentication.repository;

import com.company.authentication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    public AppUser findByUserName(String userName);
}
