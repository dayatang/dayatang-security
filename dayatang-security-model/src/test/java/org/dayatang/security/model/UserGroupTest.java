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
		grantGroup.addActor(parentGroup);
		parentGroup.addActor(group1);
		//parentGroup.addActor(group2);
		group1.addActor(user);
	}
	
	@Test
	public void getGroups() {
		Set<UserGroup> groups = grantGroup.getGroups();
		assertTrue(groups.contains(parentGroup));
		assertFalse(groups.contains(group1));
		groups = parentGroup.getGroups();
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
		assertTrue(parentGroup.containsActor(group1));
		assertTrue(group1.containsActor(user));
		assertTrue(parentGroup.containsActor(user));
	}
	
	@Test
	public void removeActor() {
		parentGroup.removeActor(group1);
		assertFalse(parentGroup.containsActor(group1));
	}

}
