package com.cornerboxx;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class TestLoginDemo {

    public static void main(String[] args) {
        //取得Factory接口对象，主要的目的是通过配置文件加载文之中的信息，这些信息暂时不能成为认证信息
        //取得里面所保存的所有的认证数据信息
        SecurityManager securityManager = new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
        //利用一个专门的认证操作的处理类，实现认证处理的具体实现
        SecurityUtils.setSecurityManager(securityManager);
        //获取进行用户名和密码认证的接口对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken("admina", "hello"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(subject.isPermitted("member:add"));
    }

}