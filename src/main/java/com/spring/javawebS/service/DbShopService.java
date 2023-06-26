package com.spring.javawebS.service;

import java.util.List;

import com.spring.javawebS.vo.DbProductVO;

public interface DbShopService {

	public List<DbProductVO> getCategoryMain();

	public void setCategoryMainInput(DbProductVO vo);

	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName);

	public List<DbProductVO> getCategoryMiddle();

	public DbProductVO getCategoryMiddleOne(DbProductVO vo);

	public void setCategoryMiddleInput(DbProductVO vo);

	public List<DbProductVO> getCategorySub();

	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode);

	public DbProductVO getCategorySubOne(DbProductVO vo);

	public void setCategorySubInput(DbProductVO vo);

}
