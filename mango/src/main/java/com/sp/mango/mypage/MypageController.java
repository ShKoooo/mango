package com.sp.mango.mypage;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.Business;
import com.sp.mango.member.MemberAddr;
import com.sp.mango.member.MemberSessionInfo;

@Controller("mypage.mypageController")
@RequestMapping(value="/mypage/*")
public class MypageController {
	@Autowired
	private MypageService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value = "main")
	public String main(
			HttpSession session,
			Model model
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
				
		String userId = memberInfo.getUserId();
		String userNickName = memberInfo.getUserNickName();
		
		String hasBusiness = "false";
		if (service.businessDuplCheck(userId)>0) hasBusiness = "true";
		
		MannerProfile mannerDto = service.readMannerProfile(userId);
		if (mannerDto == null) {
			mannerDto = new MannerProfile();
			mannerDto.setMannerStar(0);
			mannerDto.setProductStar(0);
			mannerDto.setMinusDeg(0);
			
			// mannerProfile 신규등록
			service.insertMannerProfile(userId);
		}
		mannerDto.setMannerDeg(mannerDto.getMannerStar(), mannerDto.getProductStar(), mannerDto.getMinusDeg());
		
		List<MemberAddr> addrList = service.listMemberAddr(userId);
		Business businessDto = service.readBusiness(userId);
		
		// 관심,차단유저 및 키워드 갯수 체크
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("keyword","");
		int countPick = service.myPickCount(map);
		int countBlock = service.myBlockCount(map);
		int countKeyword = service.myKeywordCount(map);
		
		model.addAttribute("userId",userId);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("mannerDto",mannerDto);
		model.addAttribute("addrList",addrList);
		model.addAttribute("hasBusiness",hasBusiness);		// 비즈니스 보유중인지 체크 : true/false : 없으면 신규등록버튼, 있으면 수정버튼, 주소처럼 리스트 형태로 표시..
		model.addAttribute("businessDto",businessDto);
		model.addAttribute("countPick",countPick);
		model.addAttribute("countBlock",countBlock);
		model.addAttribute("countKeyword",countKeyword);
		
		return ".mypage.main";
	}
	
	@RequestMapping(value = "mypick")
	public String mypick(
			HttpSession session,
			Model model,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="") String keyword,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		
		String cp = req.getContextPath();
		
		int rows = 5;
		int total_page = 0;
		int dataCount = 0;
		
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(":::: userId : " + memberInfo.getUserId());
		
		map.put("userId", memberInfo.getUserId());
		map.put("keyword", keyword);
		dataCount = service.myPickCount(map);
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터를 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<PickedUser> myPickedUserList = service.listMyPickedUser(map);
		
		String query = "";
		String listUrl = cp + "/mypage/mypick";
		if (keyword.length()!= 0) {
			query = "keyword="+keyword;
		}
		if (query.length()!= 0) {
			listUrl += "?"+query;
		}
		String paging = myUtil.paging(current_page, total_page, listUrl);
				
		String userId = memberInfo.getUserId();
		
		// 그냥 userId것만 갖고오는거 (백업용)
		List<PickedUser> pickedUserList = service.listPickedUser(userId);
		
		model.addAttribute("pickedUserList",pickedUserList);		// 백업용리스트
		model.addAttribute("myPickedUserList", myPickedUserList);	// 검색된리스트
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		
		return ".mypage.mypick";
	}
	
	@RequestMapping(value="insertPickedUser")
	public String insertPickedUser(
			@RequestParam String keyword,
			HttpSession session,
			Model model,
			final RedirectAttributes reAttr,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		
		// GET방식일 경우
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		String target_id = service.readUserIdByNickName(keyword);
		System.out.println(":::: "+target_id);
		
		if (target_id == null || target_id.equals("")) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저는 존재하지 않습니다. <br>";
			msg += "닉네임을 정확하게 입력해 주세요. <br>";
			String goBack = "/mypage/mypick";
			
			reAttr.addFlashAttribute("title","닉네임 입력 오류");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("keyword", keyword);
		
		int hasAlreadyNickName = service.myPickCount(map);
		if (hasAlreadyNickName > 0) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저가 이미 관심 목록에 있습니다. <br>";
			msg += "다른 닉네임을 입력해 주세요. <br>";
			String goBack = "/mypage/mypick";
			
			reAttr.addFlashAttribute("title","닉네임 중복");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		try {
			Map<String, Object> insertMap = new HashMap<String, Object>();
			insertMap.put("userId", userId);
			insertMap.put("target_id", target_id);
			
			service.insertPickedUser(insertMap);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		reAttr.addFlashAttribute("message","등록 성공");
		return "redirect:/mypage/mypick";
	}
	
	@RequestMapping(value="deleteData")
	@ResponseBody
	public Map<String, Object> deleteData(
			@RequestParam String seqNum,
			@RequestParam String tableName
			) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<>();
		
		try {
			map.put("seqNum", seqNum);
			map.put("tableName", tableName);
			String seqName = "";
			if (tableName.equalsIgnoreCase("pickedUser")) {
				seqName = "pickedNum";
			} else if (tableName.equalsIgnoreCase("blockedUser")) {
				seqName = "blockNum";
			} else if (tableName.equalsIgnoreCase("keyword")) {
				seqName = "keywordNum";
			}
			map.put("seqName",seqName);
			
			service.deleteMySelection(map);
		} catch (Exception e) {
			model.put("state", "false");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping(value = "myblock")
	public String myblock(
			HttpSession session,
			Model model,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="") String keyword,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		
		String cp = req.getContextPath();
		
		int rows = 5;
		int total_page = 0;
		int dataCount = 0;
		
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", memberInfo.getUserId());
		map.put("keyword", keyword);
		dataCount = service.myBlockCount(map);
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터를 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<BlockedUser> myBlockedUserList = service.listMyBlockedUser(map);
		
		String query = "";
		String listUrl = cp + "/mypage/myblock";
		if (keyword.length()!= 0) {
			query = "keyword="+keyword;
		}
		if (query.length()!= 0) {
			listUrl += "?"+query;
		}
		String paging = myUtil.paging(current_page, total_page, listUrl);
				
		String userId = memberInfo.getUserId();
		
		// 그냥 userId것만 갖고오는거 (백업용)
		List<BlockedUser> blockedUserList = service.listBlockedUser(userId);
		
		model.addAttribute("blockedUserList",blockedUserList);		// 백업용리스트
		model.addAttribute("myBlockedUserList", myBlockedUserList);	// 검색된리스트
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		
		return ".mypage.myblock";
	}
	
	@RequestMapping(value="insertBlockedUser")
	public String insertBlockedUser(
			@RequestParam String keyword,
			HttpSession session,
			Model model,
			final RedirectAttributes reAttr,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		
		// GET방식일 경우
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		String target_id = service.readUserIdByNickName(keyword);
		
		if (target_id == null || target_id.equals("")) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저는 존재하지 않습니다. <br>";
			msg += "닉네임을 정확하게 입력해 주세요. <br>";
			String goBack = "/mypage/myblock";
			
			reAttr.addFlashAttribute("title","닉네임 입력 오류");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("keyword", keyword);
		
		int hasAlreadyNickName = service.myBlockCount(map);
		if (hasAlreadyNickName > 0) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저가 이미 관심 목록에 있습니다. <br>";
			msg += "다른 닉네임을 입력해 주세요. <br>";
			String goBack = "/mypage/myblock";
			
			reAttr.addFlashAttribute("title","닉네임 중복");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		try {
			Map<String, Object> insertMap = new HashMap<String, Object>();
			insertMap.put("userId", userId);
			insertMap.put("target_id", target_id);
			
			service.insertBlockedUser(insertMap);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		reAttr.addFlashAttribute("message","등록 성공");
		return "redirect:/mypage/myblock";
	}
	
	@RequestMapping(value = "mykeyword")
	public String mykeyword(
			HttpSession session,
			Model model,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="") String keyword,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		
		String cp = req.getContextPath();
		
		int rows = 5;
		int total_page = 0;
		int dataCount = 0;
		
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		// 전체 페이지 수
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", memberInfo.getUserId());
		map.put("keyword", keyword);
		dataCount = service.myKeywordCount(map);
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		// 리스트에 출력할 데이터를 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		
		List<Keyword> myKeywordList = service.listMyKeyword(map);
		
		String query = "";
		String listUrl = cp + "/mypage/mykeyword";
		if (keyword.length()!= 0) {
			query = "keyword="+keyword;
		}
		if (query.length()!= 0) {
			listUrl += "?"+query;
		}
		String paging = myUtil.paging(current_page, total_page, listUrl);
				
		String userId = memberInfo.getUserId();
		
		// 그냥 userId것만 갖고오는거 (백업용)
		List<Keyword> keywordList = service.listKeyword(userId);
		
		model.addAttribute("keywordList",keywordList);		// 백업용리스트
		model.addAttribute("myKeywordList", myKeywordList);	// 검색된리스트
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
		
		return ".mypage.mykeyword";
	}
	
	@RequestMapping(value="insertKeyword")
	public String insertKeyword(
			@RequestParam String keyword,
			HttpSession session,
			Model model,
			final RedirectAttributes reAttr,
			HttpServletRequest req
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		
		// GET방식일 경우
		if (req.getMethod().equalsIgnoreCase("get")) {
			keyword = URLDecoder.decode(keyword,"utf-8");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("keyword", keyword);
		
		int hasAlreadyKeyword = service.myKeywordCount(map);
		if (hasAlreadyKeyword > 0) {
			String msg = "키워드 ["+keyword+"] 가 이미 관심 목록에 있습니다. <br>";
			msg += "다른 키워드를 입력해 주세요. <br>";
			String goBack = "/mypage/mykeyword";
			
			reAttr.addFlashAttribute("title","키워드 중복");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		try {
			Map<String, Object> insertMap = new HashMap<String, Object>();
			insertMap.put("userId", userId);
			insertMap.put("keyword", keyword);
			
			service.insertKeyword(insertMap);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		reAttr.addFlashAttribute("message","등록 성공");
		return "redirect:/mypage/mykeyword";
	}
}
