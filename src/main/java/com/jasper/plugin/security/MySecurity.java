package com.jasper.plugin.security;

import java.util.Set;

public interface MySecurity {
    String getPassword(String username);
    Set<String> getRoleNameSet(String username);
    Set<String> getPermissionNameSet(String roleName);
}
