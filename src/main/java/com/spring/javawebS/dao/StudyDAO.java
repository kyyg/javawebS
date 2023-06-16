package com.spring.javawebS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.MemberVO;

public interface StudyDAO {

	public List<MemberVO> getEmailList();

	public MemberVO getMemberMidSearch(@Param("name") String name);

	public ArrayList<MemberVO> getMemberMidSearch2(@Param("name") String name);

}
