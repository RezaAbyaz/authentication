package com.company.authentication.service;

import com.company.authentication.entity.AppUser;

public interface IAppUserService {

    public AppUser findByUserName(String username);
}
