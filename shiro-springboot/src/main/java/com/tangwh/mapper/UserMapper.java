package com.tangwh.mapper;

import com.tangwh.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    public User queryByname(String name);


    }


