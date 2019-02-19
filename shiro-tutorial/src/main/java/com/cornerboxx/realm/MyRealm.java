package com.cornerboxx.realm;

import com.cornerboxx.model.Member;
import com.cornerboxx.service.MemberLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登录认证的方法需要先执行，需要用他来判断登录的用户信息是否合法
        String username = (String)token.getPrincipal();
        MemberLoginService memberLoginService =  new MemberLoginService();
        Member vo = memberLoginService.get(username);
        memberLoginService.close();
        if(vo == null) {
            throw new UnknownAccountException("该用户名称不存在");
        }else {
            String password = new String((char[])token.getCredentials());
            if(vo.getPassword().equals(password)) {
                AuthenticationInfo auth = new SimpleAuthenticationInfo(username, password,"memberRealm");
                return auth;
            }else {
                throw new IncorrectCredentialsException("密码错误！");
            }
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        MemberLoginService memberLoginService =  new MemberLoginService();
        authenticationInfo.setRoles(memberLoginService.listRolesByMember(username));
        authenticationInfo.setStringPermissions(memberLoginService.listActionByMember(username));
        memberLoginService.close();
        return authenticationInfo;
    }



}