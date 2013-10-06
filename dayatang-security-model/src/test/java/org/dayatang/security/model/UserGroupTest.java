package org.dayatang.security.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class UserGroupTest {
	private User user = new User("abcd");
	private UserGroup grantGroup = new UserGroup("grantFather");
	private UserGroup parentGroup = new UserGroup("parent");
	private UserGroup group1 = new UserGroup("group1");
	private UserGroup group2 = new UserGroup("group2");

	
	@Before
	public void beforeTest() {
		grantGroup.addChildGroup(parentGroup);
		parentGroup.addChildGroup(group1);
		//parentGroup.addActor(group2);
		group1.addUser(user);
	}
	
	@Test
	public void getGroups() {
		Set<UserGroup> groups = grantGroup.getChildren();
		assertTrue(groups.contains(parentGroup));
		assertFalse(groups.contains(group1));
		groups = parentGroup.getChildren();
		assertTrue(groups.contains(group1));
		assertFalse(groups.contains(group2));
	}
	
	
	@Test
	public void getUsers() {
		assertFalse(grantGroup.getUsers().contains(user));
		assertFalse(parentGroup.getUsers().contains(user));
		assertTrue(group1.getUsers().contains(user));
	}
	
	@Test
	public void addActor() {
		assertTrue(group1.containsUser(user));
		assertTrue(parentGroup.containsUser(user));
	}
	
	@Test
	public void removeActor() {
		parentGroup.removeChildGroup(group1);
		assertFalse(parentGroup.getChildren().contains(group1));
	}

}
