package com.sp.mango.product;

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
import com.sp.mango.mypage.Note;

@Controller("product.productController")
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private MyUtil myUtil;	
	
	// 게시글 리스트
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(value = "isKeyword", defaultValue = "0") String isKeyword,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 6; // 한 화면에 보여주는 게시물 수
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", info!=null);
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		
		String userId = "";
		if (info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
			
			listMemberAddr = service.listMemberAddr(info.getUserId());
			memAddrCount = service.memAddrCount(info.getUserId());
			
			if(listMemberAddr.size() > 0 && maLat == 0 && maLon == 0) { 
				map.put("maLat", listMemberAddr.get(0).getaLat());
				map.put("maLon", listMemberAddr.get(0).getaLon());
				maLat = listMemberAddr.get(0).getaLat();
				maLon = listMemberAddr.get(0).getaLon();
			}
		}		
		map.put("pcNum", pcNum);
		map.put("userId", userId);
		
		map.put("searchKeyword", searchKeyword);
		map.put("isKeyword", isKeyword);
		
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
		List<Product> list = service.memberListProduct(map);
		
		if(info != null) { 
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		
		// 썸내일 사진
	    for(Product dto : list) {
	        List<String> imgs = myUtil.getImgSrc(dto.getpContent());
	        if(imgs != null && imgs.size() > 0) {
	           dto.setpImgSaveFileName(imgs.get(0));
	        } else {
	           dto.setpImgSaveFileName(cp+"/resources/images/noimage.png");
	        }
	    }

		String articleUrl = cp + "/product/article?pcNum="+pcNum+"&page="+current_page;
		
//		int productWishCount = service.productWishCount(pNum);
		
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		model.addAttribute("pcNum", pcNum);
		model.addAttribute("maLat", maLat);
		model.addAttribute("maLon", maLon);
		model.addAttribute("isKeyword", isKeyword);
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("opt", opt);
		
		model.addAttribute("modeList", "list");
//		model.addAttribute("productWishCount", productWishCount);

		return ".product.list";
	}
	
	
	// AJAX - Map을 JSON으로 변환 반환
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> morelist(
			@RequestParam(value = "page", defaultValue = "2") int current_page,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(value = "isKeyword", defaultValue = "0") String isKeyword,
			HttpServletRequest req,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		
		int rows = 6;
		int dataCount;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberAddr> listMemberAddr = null;
		
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		String userId = "";
		if (info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
		}		
		map.put("pcNum", pcNum);
		map.put("userId", userId);
		
		map.put("searchKeyword", searchKeyword);
		map.put("isKeyword", isKeyword);
		
		
		// 리스트
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
				
		dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}

		List<Product> list = service.memberListProduct(map);
		if(info != null) { 
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		
		// 썸내일 사진
	    for(Product dto : list) {
	        List<String> imgs = myUtil.getImgSrc(dto.getpContent());
	        if(imgs != null && imgs.size() > 0) {
	           dto.setpImgSaveFileName(imgs.get(0));
	        } else {
	           dto.setpImgSaveFileName(cp+"/resources/images/noimage.png");
	        }
	    }
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);
		model.put("pcNum", pcNum);
		model.put("list", list);
		
		return model;
	}
	
	// 게시글 수정
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
	
	// 게시글 보기
	@RequestMapping(value = "article")
	public String article(
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			@RequestParam(value = "maLat", defaultValue = "0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "opt", defaultValue = "0") int opt,
			@RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(value = "isPorular", defaultValue = "") String isPorular,
			HttpSession session,
			HttpServletRequest req,
			Model model,
			
			Note notedto
			) throws Exception {
		
		String cp = req.getContextPath();
		
		String query = "page=" + page + "&pcNum=" + pcNum;
		if(!searchKeyword.equals("")) {
			query+= "&searchKeyword=" + searchKeyword;
		}
		if(!isPorular.equals("")) {
			query+= "&isPorular=" + isPorular;
		}
			
		
		service.updateHitCount(pNum);
		
		// 글보기
		Product dto = service.readProduct(pNum);
		if(dto == null) {
			return "redirect:/product/list?" + query;
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pNum", pNum);		
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		// 게시글 관심 여부
		boolean userProductWished = false;
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		List<ProductReport> listPreport = null;
		List<Product> receiveNoteList = null;
		
		if(info != null) {
			map.put("userId", info.getUserId());
			userProductWished = service.userProductWished(map);
			
			String userId = info.getUserId();
			
			listPreport = service.listPreport();
			receiveNoteList = service.receiveNoteList(userId);
			
			query += "&maLat=" + maLat + "&maLon=" + maLon + "&opt=" + opt;
		}

		
		int productWishCount = service.productWishCount(pNum);
		String pUpOkDate = service.pUpOkDate(pNum);
		
		String userImgSaveFileName = service.userImgSaveFileName(pNum);
		
		model.addAttribute("dto", dto);
		model.addAttribute("userProductWished", userProductWished);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("productWishCount", productWishCount);
		model.addAttribute("listPreport", listPreport);
		model.addAttribute("pcNum", pcNum);
		model.addAttribute("pUpOkDate", pUpOkDate);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("isPorular", isPorular);
		model.addAttribute("userImgSaveFileName", userImgSaveFileName);
		model.addAttribute("receiveNoteList", receiveNoteList);
		
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
		
		List<Product> listCategory = service.listCategory();
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		Product dto = service.readProduct(pNum);
		if(dto == null || !info.getUserId().equals(dto.getUserId())) {
			return "redirect:/product/list?pcNum=" + pcNum + "&page=" + page;
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("pcNum", pcNum);
		
		return ".product.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Product dto,
			@RequestParam String page,
			@RequestParam int pcNum,
			@RequestParam int pNum,
			Note noteDto,
			HttpSession session
			) throws Exception {

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.updateProduct(dto);
			
			
			if(dto.getpStatus().equals("판매중")) {
				List<Product> selectBook = service.selectBook(pNum);
				
					for(Product vo : selectBook) {
						String bookAble = vo.getBookAble();
						
						if(bookAble.equals("T")) {
							noteDto.setSendId(dto.getUserId());
							String rContent = "안녕하세요. 알림 예약하신 <"+dto.getpSubject()+"> 게시글이 '판매중'으로 변경되었습니다.<br> 판매이웃에게 거래쪽지를 보내보세요!";
							noteDto.setReceiveId(vo.getUserId());
							noteDto.setNoteContent(rContent);
							
							service.sendMsg(noteDto);					
						}
					}
			}
			
		} catch (Exception e) {
		}

		return "redirect:/product/list?pcNum="+ pcNum + "&page=" + page;
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
	
	//게시글 삭제
	@RequestMapping(value = "delete")
	public String delete(@RequestParam int pNum,
			@RequestParam int pcNum,
			@RequestParam String page,
			HttpSession session) throws Exception {
	
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		String query = "pcNum=" + pcNum + "&page=" + page;
		
		Map<String, Object> map = new HashMap<>();
		map.put("pNum", pNum);
		map.put("userId", info.getUserId());
	
		service.deleteProduct(pNum);
		
		return "redirect:/product/list?" + query;
	}
	
	// 게시글 신고
	@RequestMapping(value = "report")
	public String reportSubmit(ProductReport dto,
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertPreport(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/product/article?pNum=" + pNum + "&page=" + page + "&pcNum=" + pcNum;
	}
	
	@RequestMapping("updateDate")
	public String updateDate(
			@RequestParam int pNum,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			service.updateDate(pNum, info.getUserId());
		} catch (Exception e) {
		}
		
		return "redirect:/product/list"; 
	}


	@RequestMapping("sendMsg")
	public String sendMsg(
			Note dto,
			@RequestParam int pNum,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");	
		
		try {
			dto.setSendId(info.getUserId());
			
			Product vo = service.readProduct(pNum);
			String nContent = "안녕하세요 "+vo.getpSubject()+"를 구매하고 싶어서 쪽지남깁니다.";
			dto.setReceiveId(vo.getUserId());
			dto.setNoteContent(nContent);
		
			service.sendMsg(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/mypage/note";
	}

	
	@RequestMapping("popular")
	public String popularList(
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
		map.put("info", info!=null);
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		
		if (info != null) {
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
			memAddrCount = service.memAddrCount(info.getUserId());
			
			if(listMemberAddr.size() > 0 && maLat == 0 && maLon == 0) { 
				map.put("maLat", listMemberAddr.get(0).getaLat());
				map.put("maLon", listMemberAddr.get(0).getaLon());
			}
		}		
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
		List<Product> list = service.popularList(map);
		
		if(info != null) { 
			if(opt == 0 && listMemberAddr.size() > 0) {
				opt = listMemberAddr.get(0).getAreaNum();
			}
		}
		
		// 썸내일 사진
	    for(Product dto : list) {
	        List<String> imgs = myUtil.getImgSrc(dto.getpContent());
	        if(imgs != null && imgs.size() > 0) {
	           dto.setpImgSaveFileName(imgs.get(0));
	        } else {
	           dto.setpImgSaveFileName(cp+"/resources/images/noimage.png");
	        }
	    }

		String isPorular = "1";
		// String listUrl = cp + "/product/list";
		String articleUrl = cp + "/product/article?pcNum="+pcNum+"&page="+current_page+"&isPorular="+isPorular;
				
		// String paging = myUtil.paging(current_page, total_page, listUrl);
		
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);

		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("list", list);
		model.addAttribute("pcNum", pcNum);
		
		// model.addAttribute("paging", paging);
		model.addAttribute("articleUrl", articleUrl);
		
		List<Product> listCategory = service.listCategory();
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("opt", opt);
		model.addAttribute("isPorular", isPorular);
		
		return ".product.popular";
	}
	
	@RequestMapping(value = "writeReview", method = RequestMethod.GET)
	public String writeReviewForm(
			@RequestParam int pNum,
			Model model
			) throws Exception {
		
		Product dto = service.readProduct(pNum);
		
		model.addAttribute("dto", dto);
		
		return ".product.prwrite";
	}
	
	@RequestMapping(value = "writeReview", method = RequestMethod.POST)
	public String writeReviewSubmit(
			Preview dto,
			@RequestParam int pNum,
			Product pdto,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setBuyerId(info.getUserId());
			
			pdto = service.readProduct(pNum);
			dto.setSellerId(pdto.getUserId());
			
			service.insertPreview(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/mypage/note";
	}
	
	// 리뷰 요청하기
	@RequestMapping(value = "reviewReq")
	public String reviewReq(Product dto,
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session,
			Note noteDto
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			service.updatePstatus(pNum);
			
			dto.setUserId(info.getUserId());
			
			service.insertMyAccount(dto);
			
			
			noteDto.setSendId(dto.getUserId());
			Product vo = service.readProduct(pNum);
			String rContent = "안녕하세요. <"+vo.getpSubject()+"> 거래에 대한 리뷰를 남겨주세요!! <br>";
				rContent += "<a href='http://localhost:9090/mango/product/writeReview?pNum="+pNum+"'><i class=\"bi bi-arrow-right-circle\"></i> 리뷰작성하기!</a>";
			noteDto.setReceiveId(dto.getTarget_id());
			noteDto.setNoteContent(rContent);
		
			service.sendMsg(noteDto);
			service.updateSoldDate(dto);
			
		} catch (Exception e) {
		}
		
		
		return "redirect:/product/article?pNum=" + pNum + "&page=" + page + "&pcNum=" + pcNum;
	}
	
	@RequestMapping("changeStatus")
	public String changeStatus(
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum
			) throws Exception {
		
		try {
			service.updatePstatus(pNum);
		} catch (Exception e) {
		}
		
		return "redirect:/product/article?pNum=" + pNum + "&page=" + page + "&pcNum=" + pcNum;
	}
	
	
	// 예약 설정하기
	@RequestMapping(value = "insertBook")
	public String insertBook(Product dto,
			@RequestParam int pNum,
			@RequestParam String page,
			@RequestParam int pcNum,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertBook(dto);
		} catch (Exception e) {
		}
		
		
		return "redirect:/product/article?pNum=" + pNum + "&page=" + page + "&pcNum=" + pcNum;
	}
}