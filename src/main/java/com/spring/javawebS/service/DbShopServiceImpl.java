package com.spring.javawebS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.DbShopDAO;
import com.spring.javawebS.vo.DbProductVO;

@Service
public class DbShopServiceImpl implements DbShopService {

	@Autowired
	DbShopDAO dbShopDAO;

	@Override
	public List<DbProductVO> getCategoryMain() {
		return dbShopDAO.getCategoryMain();
	}

	@Override
	public void setCategoryMainInput(DbProductVO vo) {
		dbShopDAO.setCategoryMainInput(vo);
	}

	@Override
	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName) {
		return dbShopDAO.getCategoryMainOne(categoryMainCode, categoryMainName);
	}

	@Override
	public List<DbProductVO> getCategoryMiddle() {
		return dbShopDAO.getCategoryMiddle();
	}

	@Override
	public DbProductVO getCategoryMiddleOne(DbProductVO vo) {
		return dbShopDAO.getCategoryMiddleOne(vo);
	}

	@Override
	public void setCategoryMiddleInput(DbProductVO vo) {
		dbShopDAO.setCategoryMiddleInput(vo);
	}

	@Override
	public List<DbProductVO> getCategorySub() {
		return dbShopDAO.getCategorySub();
	}

	@Override
	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode) {
		return dbShopDAO.getCategoryMiddleName(categoryMainCode);
	}

	@Override
	public DbProductVO getCategorySubOne(DbProductVO vo) {
		return dbShopDAO.getCategorySubOne(vo);
	}

	@Override
	public void setCategorySubInput(DbProductVO vo) {
		dbShopDAO.setCategorySubInput(vo);
	}
	
}
