package com.hm.SSI.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.SSI.dao.IUserDao;
import com.hm.SSI.model.User;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class UserDaoImpl implements IUserDao {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<User> selectAllUser() throws Exception {
		return sqlMapClient.queryForList("user.selectAllUser");
	}

}
