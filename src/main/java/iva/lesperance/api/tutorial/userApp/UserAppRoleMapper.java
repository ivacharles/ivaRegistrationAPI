package iva.lesperance.api.tutorial.userApp;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAppRoleMapper implements RowMapper<UserAppRole> {
    @Override
    public UserAppRole mapRow(ResultSet rs, int i) throws SQLException {
        UserAppRole role = new UserAppRole();
        role.setRoleID(rs.getInt("roleID"));
        role.setUserRole(rs.getString("roleName"));
        return role;
    }
}
