package com.sp.mango.village.ad;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.sp.mango.village.BusinessAddr;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.Reply;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

@Controller("village.ad.VillageAdController")
@RequestMapping("/village/ad/*")
public class VillageAdController {

	@Autowired
	private VillageAdService service;
	@Autowired
	private MyUtil myUtil;
	
	
	@RequestMapping(value = "list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(value = "maLat", defaultValue="0") double maLat,
			@RequestParam(value = "maLon", defaultValue = "0") double maLon,
			@RequestParam(value = "areaNum", defaultValue="0") String areaNum,
			HttpServletRequest req,
			Model model) throws Exception {
		
		HttpSession session = req.getSession();
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		int rows = 8;
		int total_page = 0;
		int dataCount = 0;
		String userId = "";
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("info", info!=null);
		
		List<MemberAddr> listMemberAddr = null;
		int memAddrCount = 0;
		
		map.put("maLat", maLat);
		map.put("maLon", maLon);
		
		if (info!=null) {
			map.put("membership", info.getMembership());
			listMemberAddr = service.listMemberAddr(info.getUserId());
			memAddrCount = service.memAddrCount(info.getUserId());
			userId = info.getUserId();
			
			if(listMemberAddr.size() > 0 && maLat == 0 && maLon == 0) {
				map.put("maLat", listMemberAddr.get(0).getaLat());
				map.put("maLon", listMemberAddr.get(0).getaLon());
			}
		}
		
		map.put("userId", userId);
		
		dataCount = service.dataCount(map);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		// 다른 사람이 게시글 삭제하면 전체 페이지 변환하는 부분
		if(total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		// 글 리스트
		List<VillageAd> list = null;
		
		if (info != null) {
			list = service.memberListBoard(map);
		}
		
		// ckeditor에서 첨부한 이미지를 썸네일로 불러오기
		for(VillageAd dto : list) {
			List<String> imgs = myUtil.getImgSrc(dto.getContent());
			
			if(imgs != null && imgs.size() > 0) {
				dto.setThumbnail(imgs.get(0));
			} else {
				dto.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		// 리스트 번호
		int listNum, n = 0;
		for (VillageAd dto : list) {
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
			
			Map<String, Object> rplyMap = new HashMap<String, Object>();
			rplyMap.put("vNum", dto.getvNum());
			dto.setReplyCount(service.replyCount(rplyMap));
		}
		
		String query = "";
		String listUrl = cp + "/village/ad/list";
		String articleUrl = cp + "/village/ad/article?page="+current_page;
		if (keyword.length()!=0) {
			query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		if(query.length() != 0) {
			listUrl = cp + "/village/ad/list?"+query;
			articleUrl = cp + "/village/ad/article?page="+current_page + "&" + query;
		}
		
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		BusinessAddr bsdto = service.businessAddr(info.getUserId());
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("memAddrCount", memAddrCount);
		
		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		model.addAttribute("areaNum", areaNum);
		
		model.addAttribute("businessAddr", bsdto);
		
		return ".village.ad.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(
			Model model, HttpSession session) throws Exception {

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		BusinessAddr bsdto = service.businessAddr(info.getUserId());

		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "write");
		model.addAttribute("businessAddr", bsdto);
		
		return ".village.ad.write";
	}
	
	@RequestMapping(value = "village/ad/write", method = RequestMethod.POST)
	public String writeSubmit(VillageAd dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.insertBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/village/ad/list";
	}
	
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public String article(@RequestParam int vNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session,
			Model model) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		keyword = URLDecoder.decode(keyword, "utf-8");
		
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + 
					"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.updateHitCount(vNum);
		
		VillageAd dto = service.readBoard(vNum);
		if(dto == null) {
			return "redirect:/village/ad/list?" + query;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("vNum", vNum);
		
		List<VillageReport> listVreport = null;
		List<ReplyReport> listVRreport = null;
		
		if(info != null) {
			map.put("userId", info.getUserId());
			
			listVreport = service.listVreport();
		}
		
		if(info != null) {
			map.put("userId", info.getUserId());
			
			listVRreport = service.listVRreport();
		}
		
		BusinessAddr bsdto = service.businessAddr(info.getUserId());
		
		model.addAttribute("dto", dto);

		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("listVreport", listVreport);
		model.addAttribute("listVRreport", listVRreport);
		model.addAttribute("businessAddr", bsdto);
		
		return ".village.ad.article";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@RequestParam int vNum,
			@RequestParam String page,
			HttpSession session,
			Model model) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		VillageAd dto = service.readBoard(vNum);
		if(dto == null || ! info.getUserId().equals(dto.getUserId())) {
			return "redirect:/village/ad/list?page=" + page;
		}
		
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("mode", "update");
		
		return ".village.ad.write";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(VillageAd dto,
			@RequestParam String page,
			HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.updateBoard(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/village/ad/list?page="+page;
	}
	
	@RequestMapping(value = "delete")
	public String delete(@RequestParam int vNum,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page="+page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition +"&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}
		
		service.deleteBoard(vNum, info.getUserId(), info.getMembership());
		
		return "redirect:/village/ad/list?"+query;
	}
	
	@RequestMapping(value = "insertBoardLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertBoardLike(@RequestParam int vNum,
			@RequestParam boolean userLiked,
			HttpSession session) {
		String state = "true";
		int boardLikeCount = 0;
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("vNum", vNum);
		paramMap.put("userId", info.getUserId());
		
		try {
			if(userLiked) {
				service.deleteBoardLike(paramMap);
			} else {
				service.insertBoardLike(paramMap);
			}
		} catch (DuplicateKeyException e) {
			state = "liked";
		} catch (Exception e) {
			state = "false";
		}
		
		boardLikeCount = service.boardLikeCount(vNum);
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		model.put("boardLikeCount", boardLikeCount);
		
		return model;
	}
	
	@RequestMapping(value = "listReply")
	public String listReply(@RequestParam int vNum,
			@RequestParam(value="pageNo", defaultValue="1") int current_page,
			Model model) throws Exception {
		
		int rows = 5;
		int total_page = 0;
		int dataCount = 0;
		
		Map<String, Object> map = new HashMap<>();
		map.put("vNum", vNum);
		
		dataCount = service.replyCount(map);
		total_page = myUtil.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Reply> listReply = service.listReply(map);
		
		for(Reply dto : listReply) {
			dto.setVrContent(dto.getVrContent().replaceAll("\n", "<br>"));
		}
		
		String paging = myUtil.pagingMethod(current_page, total_page, "listPage");
		
		model.addAttribute("listReply", listReply);
		model.addAttribute("pageNo", current_page);
		model.addAttribute("replyCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		return "village/ad/listReply";
	}
	
	@RequestMapping(value = "insertReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertReply(Reply dto, HttpSession session) {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		String state = "true";
		
		try {
			dto.setUserId(info.getUserId());
			service.insertReply(dto);
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("state", state);
		return model;
	}
	
	@RequestMapping(value = "deleteReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteReply(@RequestParam Map<String, Object> paramMap) {
		String state = "true";
		
		try {
			service.deleteReply(paramMap);
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("state", state);
		return map;
	}
	
	@RequestMapping(value = "listReplyAnswer")
	public String listReplyAnswer(@RequestParam int vrAnswer, Model model) throws Exception {
		List<Reply> listReplyAnswer = service.listReplyAnswer(vrAnswer);
		
		for(Reply dto : listReplyAnswer) {
			dto.setVrContent(dto.getVrContent().replaceAll("\n", "<br>"));
		}
		
		model.addAttribute("listReplyAnswer", listReplyAnswer);
		return "village/ad/listReplyAnswer";
	}
	
	@RequestMapping(value = "countReplyAnswer")
	@ResponseBody
	public Map<String, Object> countReplyAnswer(@RequestParam(value="vrAnswer") int vrAnswer) {
		int count = service.replyAnswerCount(vrAnswer);
		
		Map<String, Object> model = new HashMap<>();
		model.put("count", count);
		return model;
	}
	
	@RequestMapping(value = "insertReplyLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertReplyLike(@RequestParam Map<String, Object> paramMap,
			HttpSession session) {
		String state = "true";
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		Map<String, Object> model = new HashMap<>();
		
		try {
			paramMap.put("userId", info.getUserId());
			service.insertReplyLike(paramMap);
		} catch (DuplicateKeyException e) {
			state = "liked";
		} catch (Exception e) {
			state = "false";
		}
		
		Map<String, Object> countMap = service.replyLikeCount(paramMap);
		
		int likeCount = ((BigDecimal) countMap.get("LIKECOUNT")).intValue();
		int disLikeCount = ((BigDecimal) countMap.get("DISLIKECOUNT")).intValue();
		
		model.put("likeCount", likeCount);
		model.put("dislikeCount", disLikeCount);
		model.put("state", state);
		
		return model;
	}
	
	@RequestMapping(value = "countReplyLike", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countReplyLike(@RequestParam Map<String, Object> paramMap,
			HttpSession session) {
		
		Map<String, Object> countMap = service.replyLikeCount(paramMap);
		int likeCount = ((BigDecimal) countMap.get("LIKECOUNT")).intValue();
		int disLikeCount = ((BigDecimal) countMap.get("DISLIKECOUNT")).intValue();
		
		Map<String, Object> model = new HashMap<>();
		model.put("likeCount", likeCount);
		model.put("disLikeCount", disLikeCount);
		
		return model;
	}
	
	@RequestMapping(value = "report")
	public String insertVreport(VillageReport dto, 
			@RequestParam int vNum,
			@RequestParam String page,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.insertVreport(dto);
		} catch (Exception e) {
		}
		
		return "redirect:/village/ad/article?page="+page+"&vNum="+vNum;
	}
	
	@RequestMapping(value = "reportReply")
	public String insertVRreport(ReplyReport dto,
			@RequestParam int vNum,
			@RequestParam String page,
			@RequestParam int vreplyNum,
			HttpSession session) throws Exception {
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());
			service.insertVRreport(dto); // TODO
		} catch (Exception e) {
		}
		
		return "redirect:/village/ad/article?page="+page+"&vNum="+vNum;
	}
}
