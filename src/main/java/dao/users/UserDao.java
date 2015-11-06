package dao.users;

import java.util.List;

import model.User;


public interface UserDao {
	
	public void insert(User user);
	
	public User findByUsername(String username);
	
	public List<User> getAllUsers();
}
