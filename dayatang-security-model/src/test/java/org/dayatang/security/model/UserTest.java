package org.dayatang.security.model;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User instance = new User("abcd");
	private Actor grantGroup = new UserGroup("grantFather");
	private Actor parentGroup = new UserGroup("parent");
	private Actor group1 = new UserGroup("group1");
	private Actor group2 = new UserGroup("group2");
	private Actor group3 = new UserGroup("group3");
	
	
	@Before
	public void beforeTest() {
	}
	
	@Test
	public void test() {
	}

}
