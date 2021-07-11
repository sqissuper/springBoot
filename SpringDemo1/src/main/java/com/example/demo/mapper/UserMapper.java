package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName:UserMapper
 * Package:com.example.demo.mapper
 * Description:
 *
 * @Author:HP
 * @date:2021/7/8 20:03
 */

@Mapper
public interface UserMapper {
    //注册功能
    public int addUser(User user);

}
