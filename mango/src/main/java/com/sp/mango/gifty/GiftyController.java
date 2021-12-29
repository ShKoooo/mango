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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.mango.common.MyUtil;
import com.sp.mango.greview.Greview;
import com.sp.mango.member.MemberSessionInfo;
import com.sp.mango.mypage.MypageService;
import com.sp.mango.mypage.Note;
import com.sp.mango.product.Product;



@Controller("gifty.giftyController")
@RequestMapping("/gifty/*")
public class GiftyController {
	@Autowired
	private GiftyService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private MypageService mypageservice;
	
	
	
	@RequestMapping(value = "list")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model,
			HttpSession session
			// , Member mdto
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
		// 얘를 어디서 가져오냐
				// map.put("blockedId", blockedId);
		
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
		// 얘를 어디서 가져오냐
		// map.put("blockedId", blockedId);
		
		dataCount = service.dataCount(map);
		int total_page = myUtil.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}
		List<Gifty> list = service.listGifty(map);
		
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
			@RequestParam(defaultValue = "0") int group,
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
		
		if(info != null) {
			map.put("userId", info.getUserId());
			userGwished = service.userGwished(map);
			
			listGreport = service.listGreport();
		}
		
		String userImgSaveFileName = service.userImgSaveFileName(gNum);
		int giftyWishCount = service.giftyWishCount(gNum);
		String gUpOkDate = service.gUpOkDate(gNum);
		
		List<String> imgs = myUtil.getImgSrc(dto.getgContent());
		if(imgs != null && imgs.size() > 0) {
			dto.setgImgSaveFileName(imgs.get(0));
		} else {
			dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
		}
		List<Gifty> listTargetId = service.listTargetId(map);
		
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
		model.addAttribute("userImgSaveFileName", userImgSaveFileName);
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
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(
			Gifty dto,
			@RequestParam String page,
			@RequestParam int group,
			@RequestParam int gNum,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			if(dto.getgStatus().equals("거래완료")) {
				service.updateSdate(dto);
			} else {
				service.updateGifty(dto);
			}
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list?group= "+ group +"&page=" + page;
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
			/*
			Date date = new Date();
			long gap;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date regDate = sdf.parse(dto.getgRegdate());
			gap = (date.getTime() - regDate.getTime()) / (1000*60*60);
			
			if(gap < 3) {
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.print("<script>alert('끌어올리기는 첫 등록 3일 후 가능합니다');</script>");
			} else {
			}
				*/
			service.updateDate(gNum, info.getUserId());
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list"; 
	}
	
	
	@RequestMapping("listPop")
	public String poplist(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "0") int group,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		List<Gifty> listGcategory = service.listGcategory();
		
		int dataCount;
		int rows = 6;
		int total_page = 0;
		String query ="";
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		map.put("group", group);
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		dataCount = service.dataCount(map);
		
		if(dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		List<Gifty> list = service.listPop(map);
		
		if (keyword.length() != 0) {
			query = "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		
		String cp = req.getContextPath();
		// String query = "rows=" + rows;
		// String listUrl = cp + "/gifty/list?group=" +group;
		String articleUrl = cp +"/gifty/article?group="+group+"&page=" + current_page + query;
		
		//if (query.length() != 0) {
		// listUrl += "?" + query;
		// articleUrl += "&" + query;
		//}
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
	
	@RequestMapping("sendMsg")
	public String sendMsg(
			Note dto,
			@RequestParam int gNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");	
		
		try {
			dto.setSendId(info.getUserId());
			
			Gifty vo = service.readGifty(gNum);
			String nContent = "안녕하세요 "+vo.getgSubject()+"를 구매하고 싶어서 쪽지남깁니다.";
			dto.setReceiveId(vo.getUserId());
			dto.setNoteContent(nContent);
			
		
			service.sendMsg(dto);
		} catch (Exception e) {

		}
		
		return "redirect:/mypage/note";
	}
	
	public String sendReview(
			Note noteDto,
			@RequestParam int gNum,
			@RequestParam String target_id,
			HttpSession session,
			Gifty dto
			) throws Exception {
		
		// MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");	
		noteDto.setSendId(dto.getUserId());
		
		String rContent = "안녕하세요 "+dto.getgSubject()+"에 대한 리뷰를 남겨주세요";
			rContent += "<a href='http://localhost:9090/mango/greview/write?gNum=${dto.gNum}'>리뷰작성하기</a>";// + <a href='http://localhost:9090/mango/greview/write?gNum=${dto.gNum}'>리뷰작성하기</a>
		noteDto.setReceiveId(target_id); // 설정 필요!!!!!!!!@@@@@@@@@@@@@@
		noteDto.setNoteContent(rContent);
	
		service.sendMsg(noteDto);
		
		return "redirect:/gifty/article?gNum="+gNum;
	}
	

	
/*
	@RequestMapping(value = "sellInfo", method = RequestMethod.POST)
	public String sellSubmit (
			@RequestParam String skeyword,
			@RequestParam int gNum,
			HttpSession session,
			Model model,
			final RedirectAttributes reAttr,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");			
		String userId = info.getUserId();
		
		// GET방식일 경우
		if (req.getMethod().equalsIgnoreCase("get")) {
			skeyword = URLDecoder.decode(skeyword,"utf-8");
			}
		
	
		String target_id = mypageservice.readUserIdByNickName(skeyword);
		if (target_id == null || target_id.equals("")) {
			String msg = "닉네임 ["+skeyword+"] 에 해당하는 유저는 존재하지 않습니다. <br>";
			msg += "닉네임을 정확하게 입력해 주세요. <br>";
			String goBack = "/gifty/sellInfo?gNum="+gNum;
		
			reAttr.addFlashAttribute("title","닉네임 입력 오류");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
	
		reAttr.addFlashAttribute("skeyword",skeyword);
		// model.addAttribute("skeyword", skeyword);
		
		int income;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("target_id", target_id);
		map.put("income", income);
		
		
		service.insertMyAccount(map);
		
		return "redirect:/gifty/sellInfo?gNum="+gNum;
	}
*/
	
	@RequestMapping(value = "sellInfo", method = RequestMethod.POST)
	public String sellSubmit (
			HttpSession session,
			@RequestParam int gNum,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");			
		String userId = info.getUserId();
		
		// GET방식일 경우
		//if (req.getMethod().equalsIgnoreCase("get")) {
			//skeyword = URLDecoder.decode(skeyword,"utf-8");
			//}
	
	
		
		
		return "redirect:/gifty/sellInfo?gNum="+gNum;
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
			dto.setUserId(info.getUserId());
			
			service.insertMyAccount(dto);
			
			noteDto.setSendId(dto.getUserId());
			Gifty vo = service.readGifty(gNum);
			String rContent = "안녕하세요 "+vo.getgSubject()+"에 대한 리뷰를 남겨주세요";
				rContent += "<a href='http://localhost:9090/mango/greview/write?gNum="+gNum+"'>리뷰작성하기</a>";// + <a href='http://localhost:9090/mango/greview/write?gNum=${dto.gNum}'>리뷰작성하기</a>
			noteDto.setReceiveId(dto.getTarget_id()); // 설정 필요!!!!!!!!@@@@@@@@@@@@@@
			noteDto.setNoteContent(rContent);
		
			service.sendMsg(noteDto);
			
			
		} catch (Exception e) {
		}
		
		
		return "redirect:/gifty/article?gNum=" + gNum + "&page=" + page + "&group=" + group;
	}
	
	@RequestMapping(value = "writeReview", method =  RequestMethod.GET)
	public String writeForm() throws Exception {
		
		
		
		return ".gifty.grwrite";
	}	
	
	@RequestMapping(value = "writeReview", method = RequestMethod.POST)
	public String writeSubmit(Greview dto,
			@RequestParam int gNum,
			Gifty giftydto,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setBuyerId(info.getUserId());
			
			giftydto = service.readGifty(gNum);
			dto.setSellerId(giftydto.getUserId());
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/gifty/list";
	}

	
}
