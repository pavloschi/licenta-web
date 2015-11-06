package dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.User;

public class UserDaoImpl implements UserDao{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	
	public void setDataSource(DataSource ds) {
	    jdbcTemplate = new JdbcTemplate(ds);
	}

	
	@Override
	public void insert(User user) {

		
	}

	@Override
	public User findByUsername(String username) {
		
		String sql =  "SELECT * from users where username = ? limit 1";
		Object[] params = { username };
		int[] types = { Types.VARCHAR };
		User user = (User) jdbcTemplate.query(sql,params,types,new UserMapper());
		return user;
		
	}

	@Override
	public List<User> getAllUsers() {
		String sql =  "SELECT * from users ORDER BY username ASC";
		List<User> users = jdbcTemplate.query(sql,new UserMapper());
		return users;
	}
	
	static class UserMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			
			return user;
		}
			
	}
	
	

}
