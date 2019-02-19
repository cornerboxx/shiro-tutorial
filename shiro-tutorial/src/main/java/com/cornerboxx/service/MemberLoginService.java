package com.cornerboxx.service;

import com.cornerboxx.model.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MemberLoginService {

    private Connection connection;

    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/shirodb";
    private static final String DBUSER = "root";
    private static final String PASSWORD = "root";
    private PreparedStatement pstmt;

    public MemberLoginService() {
        this.connectionDataBase();
    }

    public Member get(String mid) {
        Member vo = null;
        try {
            String sql = "select mid,password from member where mid=?";
            this.pstmt = this.connection.prepareStatement(sql);
            this.pstmt.setString(1, mid);
            ResultSet rs = this.pstmt.executeQuery();
            if (rs.next()) {
                vo = new Member();
                vo.setMid(rs.getString(1));
                vo.setPassword(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * 根据用户名查询出永不对应的所有的角色数据
     *
     * @param mid
     * @return
     */
    public Set<String> listRolesByMember(String mid) {
        Set<String> allRoles = new HashSet<String>();
        try {
            String sql = "select flag from role where rid in"
                    + "(select rid from member_role where mid=?)";
            this.pstmt = this.connection.prepareStatement(sql);
            this.pstmt.setString(1, mid);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                allRoles.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRoles;
    }

    /**
     * 根据用户名查询出所有的权限数据
     *
     * @param mid
     * @return
     */
    public Set<String> listActionByMember(String mid) {
        Set<String> allAction = new HashSet<String>();
        try {
            String sql = "select flag from action where actid in"
                    + "(select actid from role_action where rid"
                    + " in(select rid from member_role where mid=?))";
            this.pstmt = this.connection.prepareStatement(sql);
            this.pstmt.setString(1, mid);
            ResultSet rs = this.pstmt.executeQuery();
            while (rs.next()) {
                allAction.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAction;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectionDataBase() {
        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}