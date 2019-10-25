package com.wd.mapper;

import com.wd.entity.User;

import java.util.List;


/**
 * 用户的持久层接口
 */
public interface UserMapper {

    /**
     * 查询所有操作,用时获取用户下所有账户信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);


}
