package com.spring.javawebS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.DbProductVO;

public interface DbShopDAO {

	public List<DbProductVO> getCategoryMain();

	public void setCategoryMainInput(@Param("vo") DbProductVO vo);

	public DbProductVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName")  String categoryMainName);

	public List<DbProductVO> getCategoryMiddle();

	public DbProductVO getCategoryMiddleOne(@Param("vo") DbProductVO vo);

	public void setCategoryMiddleInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategorySub();

	public List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	public DbProductVO getCategorySubOne(@Param("vo") DbProductVO vo);

	public void setCategorySubInput(@Param("vo") DbProductVO vo);

}
