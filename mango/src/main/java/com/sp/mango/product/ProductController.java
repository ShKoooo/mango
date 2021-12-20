package com.sp.mango.product;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DuplicateKeyException;
import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("product.productController")
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value = "list2")
	@ResponseBody
	public List<Product> list2(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			HttpServletRequest req,
			Model model
			) throws Exception {	
		// maLat, maLot의 defaultValue를 0을 준 이유는 0,0은 우리나라에 없는 좌표기도하고, 밑에 조건으로 쓰기위해...
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
		int dataCount = service.dataCount();
		int total_page = myUtil.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
				
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();

		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		// 회원이 선택한 회원 주소의 위도 경로도 map으로 넣기
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		// 리스트
		List<Product> list = null;
		
		if(info == null) { // 회원이 아니거나, 주소가 하나도 등록되어 있지 않을 때
			list = service.listProduct(map);
		} else if (info != null) { // 회원일 때. (주소가 하나라도 등록되어 있을 때)
			if(maLat==0 && maLon==0) {
				list = service.listProduct(map);
			} else {
				list = service.memberListProduct(map);	
			}
		}
		
		model.addAttribute("list", list);
		

		return list;
	}
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			HttpServletRequest req,
			Model model
			) throws Exception {	
		// maLat, maLot의 defaultValue를 0을 준 이유는 0,0은 우리나라에 없는 좌표기도하고, 밑에 조건으로 쓰기위해...
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
		int dataCount = service.dataCount();
		int total_page = myUtil.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
				
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();

		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		// 회원이 선택한 회원 주소의 위도 경로도 map으로 넣기
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		// 카테고리 넘버
		map.put("pcNum", pcNum);
		
		String articleUrl = cp + "/product/article?page=" + current_page;
		
		// 작업 결과를 json으로 전송
//		Map<String, Object> model = new HashMap<String, Object>();

		// 리스트
		List<Product> list = null;
//		List<Product> memberList = null;
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		if(info == null) { // 회원이 아니거나, 주소가 하나도 등록되어 있지 않을 때
			list = service.listProduct(map);
		} else if (info != null) { // 회원일 때. (주소가 하나라도 등록되어 있을 때)
			listMemberAddr = service.listMemberAddr(info.getUserId());	
			
			memAddrCount = service.memAddrCount(info.getUserId());
			if(maLat==0 && maLon==0) {
				list = service.listProduct(map);
			} else {
				list = service.memberListProduct(map);
			}
		}
		

		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);
//		model.addAttribute("memberList", memberList);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		
		
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		

		return ".product.list";
	}
	
	
	// AJAX - Map을 JSON으로 변환 반환
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "page", defaultValue = "2") int current_page
			// @RequestParam(defaultValue = "all") String condition,
			// @RequestParam(defaultValue = "") String keyword
			) throws Exception {
		
		int rows = 6;
		int dataCount = service.dataCount();
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Product> list = service.listProduct(map);		
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);

		model.put("list", list);
		
		return model;
	}
	
	
	
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model, HttpSession session) throws Exception {
		List<Product> listCategory = service.listCategory();

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "write");
		
		return ".product.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Product dto, HttpSession session) throws Exception {
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "product";
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertProduct(dto, path);
		} catch (Exception e) {
		}
		
		
		return "redirect:/product/list";
	}
	
	
	@RequestMapping(value = "article")
	public String article(
			@RequestParam int pNum,
			@RequestParam String page,
			HttpSession session,
			Model model
			) throws Exception {
		
		String query = "page=" + page;
		
		service.updateHitCount(pNum);
		
		// 글보기
		Product dto = service.readProduct(pNum);
		if(dto == null) {
			return "redirect:/product/list?" + query;
		}
		
		// 게시글 관심 여부
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pNum", pNum);
		
		boolean userProductWished = false;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		if(info != null) {
			map.put("userId", info.getUserId());
			userProductWished = service.userProductWished(map);
		}
		
		int productWishCount = service.productWishCount(pNum);
		
		
		model.addAttribute("dto", dto);
		model.addAttribute("userProductWished", userProductWished);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("productWishCount", productWishCount);
		
		return ".product.article";
	}
	
	// 게시글 관심 추가/삭제 : AJAX - JSON
	@RequestMapping(value = "insertProductWish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertProductWish(
			@RequestParam int pNum,
			@RequestParam boolean userWished,
			HttpSession session
			) {
		
		String state = "true";
		int productWishCount = 0;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
			
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pNum", pNum);
		paramMap.put("userId", info.getUserId());
		
		try {
			if(userWished) {
				service.deleteProductWish(paramMap);
			} else {
				service.insertProductWish(paramMap);
			}
			
		} catch (DuplicateKeyException e) {
			state = "wished";
		} catch (Exception e) {
			state = "false";
		}
		
		productWishCount = service.productWishCount(pNum);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("state", state);
		model.put("productWishCount", productWishCount);

		return model;
	}
}
