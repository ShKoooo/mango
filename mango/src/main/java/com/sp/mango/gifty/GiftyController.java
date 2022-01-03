package com.sp.mango.gifty;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;
import com.sp.mango.mypage.Note;



@Controller("gifty.giftyController")
@RequestMapping("/gifty/*")
public class GiftyController {
	@Autowired
	private GiftyService service;
	
	@Autowired
	private MyUtil myUtil;
	
	
	
	
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model,
			HttpSession session
			) throws Exception {
		String cp = req.getContextPath();
		
		List<Gifty> listGcategory = service.listGcategory();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int dataCount;
		int rows = 6;
		int total_page = 0;
		String query ="";
		String userId = "";
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		if(info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
		}
		
		// String blockedId = mdto.
		
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("userId", userId);

		
		dataCount = service.dataCount(map);
		
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		List<Gifty> list = service.listGifty(map);
		for(Gifty dto : list) {
			List<String> imgs = myUtil.getImgSrc(dto.getgContent());
			if(imgs != null && imgs.size() > 0) {
				dto.setgImgSaveFileName(imgs.get(0));
			} else {
				dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
			}

		}
		
		
		if (keyword.length() != 0) {
			query = "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		
		// String query = "rows=" + rows;
		// String listUrl = cp + "/gifty/list?group=" +group;
		String articleUrl = cp +"/gifty/article?group="+group+"&page=" + current_page + query;
		
		//if (query.length() != 0) {
		// listUrl += "?" + query;
		// articleUrl += "&" + query;
		//}
		
		//String userImgSaveFileName = mdto.getUserImgSaveFileName();
		
		//model.addAttribute("userImgSaveFileName", userImgSaveFileName);
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("rows", rows);
		model.addAttribute("group", group);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
		return ".gifty.list";
	}
	
	@RequestMapping(value = "morelist")
	@ResponseBody
	public Map<String, Object> moreList (
			@RequestParam(value = "page", defaultValue = "2") int current_page,
			@RequestParam(defaultValue = "0") int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			HttpSession session
			) throws Exception {
		String cp = req.getContextPath();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int rows = 6;
		int dataCount;
		String userId = "";
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("userId", userId);
		
		dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		List<Gifty> list = service.listGifty(map);
		for(Gifty dto : list) {
			List<String> imgs = myUtil.getImgSrc(dto.getgContent());
			if(imgs != null && imgs.size() > 0) {
				dto.setgImgSaveFileName(imgs.get(0));
			} else {
				dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
			}
		}
		
		String query = "";
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		Map<String, Object> model = new HashMap<>();
		
		model.put("dataCount", dataCount);
		model.put("total_page", total_page);
		model.put("page", current_page);
		model.put("group", group);
		model.put("list", list);
		model.put("condition", condition);
		model.put("keyword", keyword);
		model.put("query", query);
		
		return model;
	}
	
	@RequestMapping("listPop")
	public String poplist(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			HttpSession session,
			Model model
			) throws Exception {
		
		String cp = req.getContextPath();
		List<Gifty> listGcategory = service.listGcategory();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		int dataCount;
		int rows = 6;
		int total_page = 0;
		String query ="";
		String userId = "";
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(info != null) {
			userId = info.getUserId();
			map.put("membership", info.getMembership());
		}
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("userId", userId);
		
		dataCount = service.dataCount(map);
		
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		List<Gifty> list = service.listPop(map);
		for(Gifty dto : list) {
			List<String> imgs = myUtil.getImgSrc(dto.getgContent());
			if(imgs != null && imgs.size() > 0) {
				dto.setgImgSaveFileName(imgs.get(0));
			} else {
				dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
			}
		}
		
		if (keyword.length() != 0) {
			query = "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		String articleUrl = cp +"/gifty/article?group="+group+"&page=" + current_page + query;
		
	
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("total_page", total_page);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("rows", rows);
		model.addAttribute("group", group);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
		
		
		return ".gifty.poplist";
	}
	
	

	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {
		List<Gifty> listGcategory = service.listGcategory();
		
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("mode", "write");
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Gifty dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			
			dto.setUserId(info.getUserId());
			service.insertGifty(dto);
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list";
	}
	
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String article(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "0") String group,
			HttpSession session,
			HttpServletRequest req,
			Model model) throws Exception {
		
		String cp = req.getContextPath();
		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page + "&group=" + group;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(gNum);
		Gifty dto = service.readGifty(gNum);
		if(dto==null) {
			return "redirect:/gifty/list?" + query;
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gNum", gNum);
		
		boolean userGwished = false;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		List<GiftyReport> listGreport = null;
		List<Gifty> listTargetId = null;
		
		if(info != null) {
			map.put("userId", info.getUserId());
			userGwished = service.userGwished(map);
			
			listGreport = service.listGreport();
			listTargetId = service.listTargetId(map);
		}
		
		// String userImgSaveFileName = service.userImgSaveFileName(gNum);
		int giftyWishCount = service.giftyWishCount(gNum);
		String gUpOkDate = service.gUpOkDate(gNum);
		
		List<String> imgs = myUtil.getImgSrc(dto.getgContent());
		if(imgs != null && imgs.size() > 0) {
			dto.setgImgSaveFileName(imgs.get(0));
		} else {
			dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("group", group);
		model.addAttribute("userGwished", userGwished);
		model.addAttribute("giftyWishCount", giftyWishCount);
		model.addAttribute("listGreport", listGreport);
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		model.addAttribute("gUpOkDate", gUpOkDate);
		// model.addAttribute("userImgSaveFileName", userImgSaveFileName);
		model.addAttribute("listTargetId", listTargetId);
		
		return ".gifty.article";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(
			@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session,
			Model model) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Gifty dto = service.readGifty(gNum);
		if(dto==null || !info.getUserId().equals(dto.getUserId())) {
			return "redirect:/gifty/list?group="+ group+ "&page=" + page;
		}
		
		List<Gifty> listGcategory = service.listGcategory();
		
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("mode", "update");
		model.addAttribute("group", group);
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(
			Gifty dto,
			@RequestParam String page,
			@RequestParam int group,
			@RequestParam int gNum,
			HttpSession session,
			Note noteDto) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
	
		try {
			dto.setUserId(info.getUserId());
			service.updateGifty(dto);
			
			
			if(dto.getgStatus().equals("판매중")) {
				List<Gifty> listBook = service.selectBook(gNum);
				
				for(Gifty vo : listBook) {
					String bookAble = vo.getBookAble();
					
					if(bookAble.equals("T")) {
						noteDto.setSendId(dto.getUserId());
						String rContent = "안녕하세요. 알림 예약하신 <"+dto.getgSubject()+"> 게시글이 '판매중'으로 변경되었습니다.<br> 판매이웃에게 거래쪽지를 보내보세요!";
						noteDto.setReceiveId(vo.getUserId());
						noteDto.setNoteContent(rContent);
						
						service.sendMsg(noteDto);		
					}
				}
			} 
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list?group="+ group +"&page=" + page;
	}
	
	@RequestMapping(value = "delete")
	public String deleteGifty(@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		String query = "page=" + page + "&group=" + group;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		service.deleteGifty(gNum, info.getUserId());
		
		return "redirect:/gifty/list?" + query;
	}
	
	@RequestMapping(value = "insertGwish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertGwish(@RequestParam int gNum,
			@RequestParam boolean userWished,
			HttpSession session) {
		String state = "true";
		int giftyWishCount = 0;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("gNum", gNum);
		paramMap.put("userId", info.getUserId());
		
		try {
			if(userWished) {
				service.deleteGwish(paramMap);
			} else {
				service.insertGwish(paramMap);
			}
		} catch (DuplicateKeyException e) {
			state = "wished";
		} catch (Exception e) {
			state = "false";
		}
		
		giftyWishCount = service.giftyWishCount(gNum);
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		model.put("giftyWishCount", giftyWishCount);
		
		return model;
		
	}
	
	@RequestMapping(value = "report")
	public String reportSubmit(
			GiftyReport dto,
			@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertGreport(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/article?group=" + group +"&page=" + page + "&gNum=" + gNum; 
	}
	
	@RequestMapping("updateDate")
	public String updateDate(
			Gifty dto,
			@RequestParam int gNum,
			HttpServletResponse resp,
			HttpSession session
			) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			
			service.updateDate(gNum, info.getUserId());
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list"; 
	}
	
	
	
	@RequestMapping("sendMsg")
	public String sendMsg(
			Note dto,
			@RequestParam int gNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");	
		
		try {
			dto.setSendId(info.getUserId());
			
			Gifty vo = service.readGifty(gNum);
			String nContent = "안녕하세요 <"+vo.getgSubject()+"> 게시글 보고 구매 문의드립니다.";
			dto.setReceiveId(vo.getUserId());
			dto.setNoteContent(nContent);
			
		
			service.sendMsg(dto);
		} catch (Exception e) {

		}
		
		return "redirect:/mypage/note";
	}
	

	
	@RequestMapping(value = "reviewReq")
	public String reviewReq(Gifty dto,
			@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session,
			Note noteDto
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			service.updateGstatus(gNum);
			dto.setUserId(info.getUserId());
			service.insertMyAccount(dto);
			
			noteDto.setSendId(dto.getUserId());
			Gifty vo = service.readGifty(gNum);
			String rContent = "안녕하세요 <"+vo.getgSubject()+"> 거래에 대한 리뷰를 남겨주세요<br>";
				rContent += "<a href='http://localhost:9090/mango/gifty/writeReview?gNum="+gNum+"'><i class='fas fa-angle-double-right'></i>리뷰작성하기</a>";// + <a href='http://localhost:9090/mango/greview/write?gNum=${dto.gNum}'>리뷰작성하기</a>
			noteDto.setReceiveId(dto.getTarget_id()); // 설정 필요!!!!!!!!@@@@@@@@@@@@@@
			noteDto.setNoteContent(rContent);
		
			service.sendMsg(noteDto);
			service.updateSdate(gNum, dto.getUserId());
			
		} catch (Exception e) {
		}
		
		
		return "redirect:/gifty/article?gNum=" + gNum + "&page=" + page + "&group=" + group;
	}
	
	@RequestMapping(value = "writeReview", method =  RequestMethod.GET)
	public String writeForm(
			@RequestParam int gNum,
			Model model
			) throws Exception {
		Gifty dto = service.readGifty(gNum);
		
		model.addAttribute("dto", dto);
		
		return ".gifty.grwrite";
	}	
	
	@RequestMapping(value = "writeReview", method = RequestMethod.POST)
	public String writeReivewSubmit(Greview dto,
			@RequestParam int gNum,
			Gifty giftydto,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setBuyerId(info.getUserId());
			
			giftydto = service.readGifty(gNum);
			dto.setSellerId(giftydto.getUserId());
			
			service.insertGreview(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/mypage/note";
	}
	
	
	// 예약 받기 알림설정 인서트
	@RequestMapping(value = "insertBook")
	public String insertBook(
			Gifty dto,
			@RequestParam int gNum,
			@RequestParam String page,
			@RequestParam int group,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");

		try {
			dto.setUserId(info.getUserId());
			
			service.insertBook(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list?gNum=" + gNum + "&page=" + page + "&group=" + group;

		
	}
	
	
	
}
