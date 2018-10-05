package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import models.User;

public class UserProvider extends SQLProvider<User> {
	private static final String TABLE_NAME = "users";
	//Implement a logger for every provider
	private static Logger logger = LogManager.getLogger(UserProvider.class);

	public UserProvider() {
		super();
	}

	@Override
	protected void initDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> selectAll() {
		List<User> users = null;
		try {
			users = new ArrayList<User>();
			
			statement = connection.createStatement();
			String query = "SELECT * FROM " + TABLE_NAME;
			logger.info("Executing " + query);
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				User user = new User(
					resultSet.getInt("id"),
					resultSet.getString("first_name"),
					resultSet.getString("last_name"),
					resultSet.getString("type"),
					resultSet.getString("email"),
					resultSet.getString("password")
				);
				
				users.add(user);
			}
			
			logger.debug("Retrieved " + users.size() + " users.");
		}catch(SQLException e) {
			logger.error("Failed to execute select all query for Table: " + TABLE_NAME);
		}
		
		
		
		return users;
	}

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(User item, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMultiple(int[] id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
