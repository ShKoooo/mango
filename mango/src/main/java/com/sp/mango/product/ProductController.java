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
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		map.put("pcNum", pcNum);
		
		int dataCount = service.dataCount(map);
		int total_page = 0;

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
		
		
		// 리스트
		List<Product> list = null;
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		list = service.memberListProduct(map);
		if(info != null) { 
			listMemberAddr = service.listMemberAddr(info.getUserId());	
			memAddrCount = service.memAddrCount(info.getUserId());
			// 회원 주소 선택 창에서.
			// opt가 0(디폴트값)이고, listMemberAddr(주소리스트)가 있으면 
			// listMemberAddr에 값은 있는데 opt 값이 없으면 주소리스트의 가장 위에 있는 값을 선택한다.
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		

		String listUrl = cp + "/product/list";
		String articleUrl = cp + "/product/article?group="+pcNum+"&page="+current_page;
				
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		model.addAttribute("pcNum", pcNum);
		
		model.addAttribute("paging", paging);
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("opt", opt);

		return ".product.list";
	}
	
/*	
	// AJAX - Map을 JSON으로 변환 반환
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "page", defaultValue = "2") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt
			) throws Exception {
		
		int rows = 6;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		map.put("pcNum", pcNum);

		int dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Product> list = service.memberListProduct(map);		
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);

		model.put("list", list);
		
		return model;
	}
*/	
	
	
	
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
//		String root = session.getServletContext().getRealPath("/");
//		String path = root + "uploads" + File.separator + "product";
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertProduct(dto);
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
	
	// 게시글 수정
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session,
			Model model) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		Product dto = service.readProduct(pNum);
		if(dto == null || ! info.getUserId().equals(dto.getUserId())) {
			return "redirect:/product/list?page=" + page + "&group=" + pcNum;
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("group", pcNum);
		
		return ".product.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Product dto,
			@RequestParam int pcNum,
			@RequestParam String page,
			HttpSession session
			) throws Exception {

		try {
			service.updateProduct(dto);
		} catch (Exception e) {
		}

		return "redirect:/product/list?page=" + page + "&group=" + pcNum;
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