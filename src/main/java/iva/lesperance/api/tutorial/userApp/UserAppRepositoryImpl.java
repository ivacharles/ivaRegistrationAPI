package iva.lesperance.api.tutorial.userApp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserAppRepositoryImpl implements UserAppRepository{

    private final JdbcTemplate jdbcTemplate;
    private final String getOneUserSQL = "SELECT * FROM userApp.user WHERE userUsername = ?;";
    private final String getOneRoleSQL = "SELECT * FROM userApp.role WHERE roleName = ?;";
    private final String getAllRoles4userSQL = "SELECT * FROM userApp.role WHERE roleName = ?;";
    private final String deleteUserSQL = "DELETE FROM userApp WHERE userUsername = ?;";
    private final String saveOneUserSQL = "INSERT INTO userApp.user (userFirstName, userLastName, userUsername, userPhoneNumber, userPwd, userLastPwd, userCreated) VALUES (?,?,?,?,?,?,?);";
    private final String saveOneUserRoleSQL = "INSERT INTO userApp.role (userRole) values (?);";
    private final String updateOneUserSQL = "UPDATE userApp.user SET userFirstName=?, userLastName=?, userUsername=?, userPhoneNumber=?, userPwd=?, userUpdated=? WHERE userID=?;";
    private final String update2EnableUserSQL = "UPDATE userApp.user SET isUserEnabled= true WHERE userLastName=?;";
    private final String addRole2UserSQL = "INSERT INTO userApp.user_and_role (userUsername, roleName) values (?,?);";


    @Override
    public Optional<UserApp> findUserByUsername(String username) {
        List<UserApp> users = jdbcTemplate.query(getOneUserSQL,  new UserAppMapper(), username);
        if(users.size() ==1) {
            UserApp user = users.get(0);
            user.setUserAppRoles(getRoles4User(user.getUsername()));
            return Optional.of(user);
        }
        else
            return Optional.empty();
    }

    @Override
    public boolean saveUser(UserApp userApp) {
            return jdbcTemplate.update(saveOneUserSQL, userApp.getFName(), userApp.getLName(), userApp.getUsername(),
                    userApp.getPhoneNumber(), userApp.getPassword(), userApp.getOldPassword(), Timestamp.valueOf(userApp.getUserCreated())) == 1;
    }

    @Override
    public boolean updateUser(int userID, UserApp userApp) {
            return jdbcTemplate.update(updateOneUserSQL, userApp.getFName(), userApp.getLName(), userApp.getUsername(),
                    userApp.getPhoneNumber(), userApp.getPassword(), userApp.getUserUpdated(), userID) == 1;
    }

    @Override
    public boolean deleteUser(String username) {
        return jdbcTemplate.update(deleteUserSQL, new UserAppMapper(), username) ==1;
    }

    @Override
    public List<UserApp> getUsers() {
        return new ArrayList<>(); //jdbcTemplate.query(getAllUsersSQL, new UserAppMapper());
    }

    @Override
    public boolean saveRole(UserAppRole role) {
        return jdbcTemplate.update(saveOneUserRoleSQL, role.getUserRole()) ==1;
    }

    @Override
    public Optional<UserAppRole> findRole(String roleName) {
        List<UserAppRole> roles = jdbcTemplate.query(getOneRoleSQL, new UserAppRoleMapper(), roleName);
        if(roles.size() == 1) {
            return Optional.ofNullable(roles.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public boolean addRole2User(String username, String roleName) {
        Optional<UserApp> user = findUserByUsername(username);
        Optional<UserAppRole> role = findRole(roleName);
        if(user.isEmpty() || role.isEmpty()){
            throw new UsernameNotFoundException(String.format("This username: %s or role: %s cannot be found", username,role));
        }else {
            return jdbcTemplate.update(addRole2UserSQL, user.get().getUsername(), role.get().getUserRole()) ==1;
        }
    }

    @Override
    public List<UserAppRole> getRoles4User(String username) {
        return jdbcTemplate.query(getAllRoles4userSQL,  new UserAppRoleMapper(), username);
    }

    @Override
    public boolean enableUserApp(String username) {
        return jdbcTemplate.update(update2EnableUserSQL, username) == 1;
    }


}
