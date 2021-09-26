package com.server.backend.repos;

import com.server.backend.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

    @Query("SELECT user FROM UserInfo user WHERE user.email like %?1")
    UserInfo getByEmail(String userEmail);

}
