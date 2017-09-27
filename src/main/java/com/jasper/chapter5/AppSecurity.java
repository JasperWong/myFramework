package com.jasper.chapter5;

import com.jasper.framework.helper.DatabaseHelper;
import com.jasper.plugin.security.MySecurity;

import java.util.Set;

public class AppSecurity implements MySecurity{

    @Override
    public String getPassword(String username) {
        String sql="SELECT password FROM user WHERE username =?";
        return DatabaseHelper.query(sql,username);
    }

    @Override
    public Set<String> getRoleNameSet(String username) {
        return null;
    }

    @Override
    public Set<String> getPermissionNameSet(String roleName) {
        return null;
    }
}
