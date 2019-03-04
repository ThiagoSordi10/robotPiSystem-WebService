package br.com.thiago.robotPi.dto;


import java.util.List;


import br.com.thiago.robotPi.model.User;

public class UserSync {

	private final List<User> users;
	private User user;

	public UserSync(List<User> users) {
		this.users = users;
	}

	public UserSync(User user) {
		this.users = null;
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

}
