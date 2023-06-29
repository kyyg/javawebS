package com.spring.javawebS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javawebS.pagination.PageProcess;
import com.spring.javawebS.service.DbShopService;
import com.spring.javawebS.vo.DbCartVO;
import com.spring.javawebS.vo.DbOptionVO;
import com.spring.javawebS.vo.DbProductVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {
	
	@Autowired
	DbShopService dbShopService;
	
	@Autowired
	PageProcess pageProcess;

	/* 아래로 관리자에서의 처리부분들~~~~ */
	
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
	
	// 대분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categoryMainDelete", method = RequestMethod.POST)
	public String categoryMainDeleteGet(DbProductVO vo) {
		// 현재 대분류가 속해있는 하위항목이 있는지를 체크한다.
		DbProductVO middleVO = dbShopService.getCategoryMiddleOne(vo);
		
		if(middleVO != null) return "0";
		dbShopService.setCategoryMainDelete(vo.getCategoryMainCode());  // 대분류항목 삭제
		
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
	
	// 중분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categoryMiddleDelete", method = RequestMethod.POST)
	public String categoryMiddleDeleteGet(DbProductVO vo) {
		// 현재 중분류가 속해있는 하위항목이 있는지를 체크한다.
		DbProductVO subVO = dbShopService.getCategorySubOne(vo);
		
		if(subVO != null) return "0";
		dbShopService.setCategoryMiddleDelete(vo.getCategoryMiddleCode());  // 소분류항목 삭제
		
		return "1";
	}
	
  // 소분류 등록하기
	@ResponseBody
	@RequestMapping(value = "/categorySubInput", method = RequestMethod.POST)
	public String categorySubInputPost(DbProductVO vo) {
		// 기존에 같은 소분류명이 있는지 체크?
		DbProductVO productVO = dbShopService.getCategorySubOne(vo);
		
		if(productVO != null) return "0";
		dbShopService.setCategorySubInput(vo);		// 중분류항목 저장
		return "1";
	}
	
	// 소분류 삭제하기
	@ResponseBody
	@RequestMapping(value = "/categorySubDelete", method = RequestMethod.POST)
	public String categorySubDeleteGet(DbProductVO vo) {
		// 현재 소분류가 속해있는 하위항목인 상품이 있는지를 체크한다.
		DbProductVO productVO = dbShopService.getDbProductOne(vo.getCategorySubCode());
		
		if(productVO != null) return "0";
		dbShopService.setCategorySubDelete(vo.getCategorySubCode());  // 소분류항목 삭제
		
		return "1";
	}
	
	// 상품 등록을 위한 등록폼 보여주기
	@RequestMapping(value = "/dbProduct", method = RequestMethod.GET)
	public String dbProducGet(Model model) {
		List<DbProductVO> mainVos = dbShopService.getCategoryMain();
		model.addAttribute("mainVos", mainVos);
		return "admin/dbShop/dbProduct";
	}
	
	// 중분류 선택시에 소분류항목들 가져오기
	@ResponseBody
	@RequestMapping(value = "/categorySubName", method = RequestMethod.POST)
	public List<DbProductVO> categorySubNamePost(String categoryMainCode, String categoryMiddleCode) {
		return dbShopService.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
	// 소분류 선택시에 상품명(모델명) 가져오기
	@ResponseBody
	@RequestMapping(value = "/categoryProductName", method = RequestMethod.POST)
	public List<DbProductVO> categoryProductNamePost(String categoryMainCode, String categoryMiddleCode, String categorySubCode) {
		return dbShopService.getCategoryProductName(categoryMainCode, categoryMiddleCode, categorySubCode);
	}
	
	// 관리자 상품등록시에 ckeditor에 그림을 올린다면 dbShop폴더에 저장되고, 저장된 파일을 브라우저 textarea상자에 보여준다. 
	@ResponseBody
	@RequestMapping("/imageUpload")
	public void imageUploadGet(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String originalFilename = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		originalFilename = sdf.format(date) + "_" + originalFilename;
		
		byte[] bytes = upload.getBytes();
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/");
		OutputStream outStr = new FileOutputStream(new File(uploadPath + originalFilename));
		outStr.write(bytes);
		
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/dbShop/" + originalFilename;
		out.println("{\"originalFilename\":\""+originalFilename+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");
		
		out.flush();
		outStr.close();
	}
	
	// 상품 등록을 위한 등록시키기
	@RequestMapping(value = "/dbProduct", method = RequestMethod.POST)
	public String dbProducPost(MultipartFile file, DbProductVO vo) {
		// 이미지파일 업로드시에 ckeditor폴더에서 product폴더로 복사작업처리....(dbShop폴더에서 'dbShop/product'로)
		dbShopService.imgCheckProductInput(file, vo);
		
		return "redirect:/message/dbProductInputOk";
	}
	
	// 등록된 상품 모두 보여주기(관리자화면에서 보여주는 처리부분이다.) - 상품의 소분류명(subTitle)도 함께 출력시켜준다.
	@RequestMapping(value = "/dbShopList", method = RequestMethod.GET)
	public String dbShopListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		// 소분류명을 가져온다.
		List<DbProductVO> subTitleVOS = dbShopService.getSubTitle();
		model.addAttribute("subTitleVOS", subTitleVOS);
		model.addAttribute("part", part);
		
		// 전체 상품리스트 가져오기
		List<DbProductVO> productVOS = dbShopService.getDbShopList(part);
		model.addAttribute("productVOS", productVOS);
		
		return "admin/dbShop/dbShopList";
	}
	
	// 관리자에서 진열된 상품을 클릭하였을경우에 해당 상품의 상세내역 보여주기
	@RequestMapping(value="/dbShopContent", method=RequestMethod.GET)
	public String dbShopContentGet(Model model, int idx) {
		DbProductVO productVO = dbShopService.getDbShopProduct(idx);	   // 1건의 상품 정보를 불러온다.
		List<DbOptionVO> optionVOS = dbShopService.getDbShopOption(idx); // 해당 상품의 모든 옵션 정보를 가져온다.
		model.addAttribute("productVO", productVO);
		model.addAttribute("optionVOS", optionVOS);
		
		return "admin/dbShop/dbShopContent";
	}
	
	// 옵션 등록창 보여주기(관리자 왼쪽메뉴에서 선택시 처리)
	@RequestMapping(value = "/dbOption", method = RequestMethod.GET)
	public String dbOptionGet(Model model) {
		List<DbProductVO> mainVos = dbShopService.getCategoryMain();
		model.addAttribute("mainVos", mainVos);
		
		return "admin/dbShop/dbOption";
	}
	
	// 옵션 등록창 보여주기(관리자 상품상세보기에서 호출시 처리)
	@RequestMapping(value = "/dbOption2", method = RequestMethod.GET)
	public String dbOption2Get(Model model, String productName) {
		DbProductVO vo = dbShopService.getProductInfor(productName);
		List<DbOptionVO> optionVOS = dbShopService.getOptionList(vo.getIdx());
		model.addAttribute("vo", vo);
		model.addAttribute("optionVOS", optionVOS);
		
		return "admin/dbShop/dbOption2";
	}
	
	// 옵션 등록후 다시 옵션 등록창 보여주기(옵션을 1개씩 등록할때는 사용하면 편리하나, 여러개를 동적폼으로 만들었을때는 aJax를 사용하지 못한다.
	/*
	@ResponseBody
	@RequestMapping(value = "/dbOption2Input", method = RequestMethod.POST)
	public String dbOption2InputGet(DbOptionVO vo) {
		dbShopService.setDbOptionInput(vo);
		return "";
	}
	*/
	
	// 옵션 등록창에서, 상품을 선택하면 선택된 상품의 상세설명을 가져와서 뿌리기
	@ResponseBody
	@RequestMapping(value="/getProductInfor", method = RequestMethod.POST)
	public DbProductVO getProductInforPost(String productName) {
		return dbShopService.getProductInfor(productName);
	}
	
	// 옵션등록창에서 '옵셔보기'버튼클릭시에 해당 제품의 모든 옵션을 보여주기
	@ResponseBody
	@RequestMapping(value="/getOptionList", method = RequestMethod.POST)
	public List<DbOptionVO> getOptionListPost(int productIdx) {
		return dbShopService.getOptionList(productIdx);
	}
	
	// 옵션 기록사항들을 등록하기
	@RequestMapping(value="/dbOption", method=RequestMethod.POST)
	public String dbOptionPost(Model model, DbOptionVO vo, String[] optionName, int[] optionPrice,
			@RequestParam(name="flag", defaultValue = "", required=false) String flag) {
		for(int i=0; i<optionName.length; i++) {
			
			int optionCnt = dbShopService.getOptionSame(vo.getProductIdx(), optionName[i]);
			if(optionCnt != 0) continue;
			
			// 동일한 옵션이 없으면 vo에 set시킨후 옵션테이블에 등록시킨다.
			vo.setProductIdx(vo.getProductIdx());
			vo.setOptionName(optionName[i]);
			vo.setOptionPrice(optionPrice[i]);
			
			dbShopService.setDbOptionInput(vo);
		}
		if(!flag.equals("option2")) return "redirect:/message/dbOptionInputOk";
		else {
			model.addAttribute("temp", vo.getProductName());
			return "redirect:/message/dbOptionInput2Ok";
		}
	}
	
	// 옵션 등록창에서 옵션리스트를 확인후 필요없는 옵션항목을 삭제처리..
	@ResponseBody
	@RequestMapping(value="/optionDelete", method = RequestMethod.POST)
	public String optionDeletePost(int idx) {
		dbShopService.setOptionDelete(idx);
		return "";
	}
	
	// 주문관리.......
	
	
	
	
	
	
	
	// ---------------------------------------------------------------------------
	
	/* 아래로 사용자(고객)에서의 처리부분들~~~~ */
	
	// 등록된 상품 보여주기(사용자(고객)화면에서 보여주기)
	@RequestMapping(value="/dbProductList", method = RequestMethod.GET)
	public String dbProductListGet(
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			Model model) {
		List<DbProductVO> subTitleVos = dbShopService.getSubTitle();
		model.addAttribute("subTitleVos", subTitleVos);
		model.addAttribute("part", part);
		
		List<DbProductVO> productVos = dbShopService.getDbShopList(part);
		model.addAttribute("productVos", productVos);
		return "dbShop/dbProductList";
	}
	
  // 진열된 상품클릭시 해당상품의 상세정보 보여주기(사용자(고객)화면에서 보여주기)
	@RequestMapping(value="/dbProductContent", method=RequestMethod.GET)
	public String dbProductContentGet(int idx, Model model) {
		DbProductVO productVo = dbShopService.getDbShopProduct(idx);			// 상품의 상세정보 불러오기
		List<DbOptionVO> optionVos = dbShopService.getDbShopOption(idx);	// 옵션의 모든 정보 불러오기
		
		model.addAttribute("productVo", productVo);
		model.addAttribute("optionVos", optionVos);
		
		return "dbShop/dbProductContent";
	}
	
	// 상품상세정보보기창에서 '장바구니'버튼, '주문하기'버튼을 클릭하면 모두 이곳을 거쳐서 이동처리했다.
	// '장바구니'버튼에서는 '다시쇼핑하기'처리했고, '주문하기'버튼에서는 '주문창'으로 보내도록 처리했다.
	@RequestMapping(value="/dbProductContent", method=RequestMethod.POST)
	public String dbProductContentPost(DbCartVO vo, HttpSession session, String flag) {
		// DBCartVO(vo) : 사용자가 선택한 품목(기본품목+옵션)의 정보를 담고 있는 VO
		// DBCartVO(resVo) : 사용자가 기존에 장바구니에 담아놓은적이 있는 품목(기본품목+옵션)의 정보를 담고 있는 VO
		String mid = (String) session.getAttribute("sMid");
		DbCartVO resVo = dbShopService.getDbCartProductOptionSearch(vo.getProductName(), vo.getOptionName(), mid);
		if(resVo != null) {		// 기존에 구매한적이 있었다면 '현재 구매한 내역'과 '기존 장바구니의 수량'을 합쳐서 'Update'시켜줘야한다.
			String[] voOptionNums = vo.getOptionNum().split(",");
			String[] resOptionNums = resVo.getOptionNum().split(",");
			int[] nums = new int[99];
			String strNums = "";
			for(int i=0; i<voOptionNums.length; i++) {
				nums[i] += (Integer.parseInt(voOptionNums[i]) + Integer.parseInt(resOptionNums[i]));
				strNums += nums[i];
				if(i < nums.length - 1) strNums += ",";
			}
			vo.setOptionNum(strNums);
			dbShopService.dbShopCartUpdate(vo);
		}		// 처음 구매하는 제품이라면 장바구니에 insert시켜준다.
		else {
			dbShopService.dbShopCartInput(vo);
		}
		
		if(flag.equals("order")) {
			return "redirect:/message/cartOrderOk";
		}
		else {
			return "redirect:/message/cartInputOk";
		}
	}
	
	// 장바구니에 담겨있는 모든 상품들의 내역을 보여주기-주문 전단계(장바구니는 DB에 들어있는 자료를 바로 불러와서 처리하면 된다.)
	@RequestMapping(value="/dbCartList", method=RequestMethod.GET)
	public String dbCartGet(HttpSession session, DbCartVO vo, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<DbCartVO> vos = dbShopService.getDbCartList(mid);
		
		if(vos.size() == 0) {
			return "redirect:/message/cartEmpty";
		}
		
		model.addAttribute("cartListVOS", vos);
		return "dbShop/dbCartList";
	}
	
	// 장바구니안에서 삭제시키고자 할 품목을 '구매취소'버튼 눌렀을때 처리
	@ResponseBody
	@RequestMapping(value="/dbCartDelete", method=RequestMethod.POST)
	public String dbCartDeleteGet(int idx) {
		dbShopService.dbCartDelete(idx);
		return "";
	}
	
}
