package org.dayatang.security.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UserGroupTest {
	private User user = new User("abcd");
	private UserGroup grantGroup = new UserGroup("grantFather");
	private UserGroup parentGroup = new UserGroup("parent");
	private UserGroup group1 = new UserGroup("group1");
	private UserGroup group2 = new UserGroup("group2");

	
	@Before
	public void beforeTest() {
        parentGroup.setParent(grantGroup);
        group1.setParent(parentGroup);
		//parentGroup.addActor(group2);
		group1.addUser(user);
	}

    @Ignore
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

    @Ignore
	@Test
	public void addActor() {
		assertTrue(group1.contains(user));
		assertTrue(parentGroup.contains(user));
	}

}
