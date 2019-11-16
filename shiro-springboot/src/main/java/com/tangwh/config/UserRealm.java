package com.tangwh.config;

import com.tangwh.pojo.User;
import com.tangwh.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


// 自定义的UserRealm  extend AuthorizingRealm

public class UserRealm  extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");

        //SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加权限
      //  info.addStringPermission("user:add");


        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal(); //拿到当前对象

        // 从数据库中拿到权限 给赋值 设置用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");


        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;

        //链接真实数据库
        User user = userService.queryByname(usertoken.getUsername());

        if (user==null){

      // 如果用户为null 则没有这个人
      return null;

  }
        // 获取用户Session
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);


//        if (!usertoken.getUsername().equals(name) ){
//            // 如果输入的用户名 和我们数据中不一致 抛出异常
//            return null;
//        }

        // 密码认证Shiro做
        // 密码加密 MD5研制加密                 当前对象
        return new SimpleAuthenticationInfo(user,user.getPwd(),"" ) {
        };
    }
}
