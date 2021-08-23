package iva.lesperance.api.tutorial.userApp;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAppMapper implements RowMapper<UserApp> {
    @Override
    public UserApp mapRow(ResultSet rs, int i) throws SQLException {
        UserApp user = new UserApp();

        user.setId(rs.getLong("userID"));
        user.setFName(rs.getString("userFirstName"));
        user.setLName(rs.getString("userLastName"));
        user.setUsername(rs.getString("userUsername"));
        user.setPhoneNumber(rs.getString("userPhoneNumber"));
        user.setPassword(rs.getString("userPwd"));
        user.setOldPassword(rs.getString("userLastPwd"));
        user.setEnabled(rs.getBoolean("isUserEnabled"));
        user.setLocked(rs.getBoolean("isUserLocked"));
        user.setUserCreated(rs.getTimestamp("userCreated").toLocalDateTime());

        return user;
    }
}


