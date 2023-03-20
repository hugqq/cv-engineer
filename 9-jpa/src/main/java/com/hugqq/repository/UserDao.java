package com.hugqq.repository;

import com.hugqq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * User Dao
 * </p>
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
