package com.cybernetica.bj.server.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.cybernetica.bj.server.dao.UserDao;
import com.cybernetica.bj.server.exceptions.DaoException;
import com.cybernetica.bj.server.models.User;
import com.cybernetica.bj.server.models.UserAuthorities;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User findByUsername(String username) throws DaoException {
		Session session = getSession();
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("from com.cybernetica.bj.server.models.User where username=:username");
		query.setParameter("username", username);
		List<User> list = query.getResultList();
		if(list==null || list.size()<1)
			return null;
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T save(T entity) throws DaoException {
		User user =  (User) super.save(entity);
		UserAuthorities auth= new UserAuthorities();
		auth.setUsername(user.getUsername());
		auth.setAuthority("USER");
		super.save(auth);
		return (T) user;
	}

}
