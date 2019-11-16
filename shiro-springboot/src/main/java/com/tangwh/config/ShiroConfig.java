package com.tangwh.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean 第三步 3 由下而上

    @Bean
    public ShiroFilterFactoryBean getshiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加Shiro内置过滤器
        /**
         *  级别
         *  anon: 无需认证就可以访问
         *  authc: 必须认证了才能访问
         *  user: 必须使用记住我功能才能访问
         *  perms:拥有对某个资源的的权限才能访问
         *  role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权                         权限 的样本
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");


        //登录拦截
//        filterMap.put("/user/add", "authc");
//        filterMap.put("/user/update", "authc");
        filterMap.put("/user/*", "authc");

        bean.setFilterChainDefinitionMap(filterMap);


        //如果没有权限 要跳转的登录页面
        bean.setLoginUrl("/tologin");

        //如果没有是授权 要跳转的未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    //DefaultWebSecurityManager 第二步2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getdefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    //创建realmd对象  第一步:1
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }


    //整合ShiroDialect:用来整合Shiro 和 Thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
