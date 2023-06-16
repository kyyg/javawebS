package com.spring.javawebS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.BoardVO;

public interface BoardDAO {

	public int totRecCnt();

	public List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public int setBoardInput(@Param("vo") BoardVO vo);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public void setBoardContent(@Param("idx") int idx);

	public ArrayList<BoardVO> getPreNext(@Param("idx") int idx);

	
}