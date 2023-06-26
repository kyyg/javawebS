package com.spring.javawebS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.pagination.PageProcess;
import com.spring.javawebS.service.DbShopService;
import com.spring.javawebS.vo.DbProductVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {
	
	@Autowired
	DbShopService dbShopService;
	
	@Autowired
	PageProcess pageProcess;
	
	// 대/중/소분류 폼 보기
	@RequestMapping(value = "/dbCategory", method = RequestMethod.GET)
	public String dbCategoryGet(Model model) {
		List<DbProductVO> mainVOS = dbShopService.getCategoryMain();  // 대분류리스트
		List<DbProductVO> middleVOS = dbShopService.getCategoryMiddle();// 중분류리스트
		List<DbProductVO> subVOS = dbShopService.getCategorySub();// 소분류리스트
	
		model.addAttribute("mainVOS", mainVOS);
		model.addAttribute("middleVOS", middleVOS);
		model.addAttribute("subVOS", subVOS);
		
		return "admin/dbShop/dbCategory";
	}
	
	// 대분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categoryMainInput", method = RequestMethod.POST)
	public String categoryMainInputPost(DbProductVO vo) {
		// 기존에 같은 대분류명이 있는지 체크?
		DbProductVO productVO = dbShopService.getCategoryMainOne(vo.getCategoryMainCode(), vo.getCategoryMainName());
		
		if(productVO != null) return "0";
		dbShopService.setCategoryMainInput(vo);		// 대분류항목 저장
		return "1";
	}
	
	// 중분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleInput", method = RequestMethod.POST)
	public String categoryMiddleInputPost(DbProductVO vo) {
		// 기존에 같은 중분류명이 있는지 체크?
		DbProductVO productVO = dbShopService.getCategoryMiddleOne(vo);
		
		if(productVO != null) return "0";
		dbShopService.setCategoryMiddleInput(vo);		// 중분류항목 저장
		return "1";
	}
	
	// 대분류 선택시 중분류명 가져오기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleName", method = RequestMethod.POST)
	public List<DbProductVO> categoryMiddleNamePost(String categoryMainCode) {
//		List<DbProductVO> mainVOS = dbShopService.getCategoryMiddleName(categoryMainCode);
//		return mainVOS;
		return dbShopService.getCategoryMiddleName(categoryMainCode);
	}
	
  // 중분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categorySubInput", method = RequestMethod.POST)
	public String categorySubInputPost(DbProductVO vo) {
		// 기존에 같은 소분류명이 있는지 체크?
		DbProductVO productVO = dbShopService.getCategorySubOne(vo);
		
		if(productVO != null) return "0";
		dbShopService.setCategorySubInput(vo);		// 중분류항목 저장
		return "1";
	}
}
