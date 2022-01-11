package com.sp.mango.mypage;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		
		int hasAlreadyBlocked = service.myBlockCount(map);
		if (hasAlreadyBlocked > 0) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저가 이미 차단 목록에 있습니다. <br>";
			msg += "다른 닉네임을 입력하거나 먼저 차단 목록에서 삭제해 주세요. <br>";
			String goBack = "/mypage/mypick";
			
			reAttr.addFlashAttribute("title","등록 실패");
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
		
		int hasAlreadyPicked = service.myPickCount(map);
		if (hasAlreadyPicked > 0) {
			String msg = "닉네임 ["+keyword+"] 에 해당하는 유저가 이미 관심 목록에 있습니다. <br>";
			msg += "다른 닉네임을 입력하시거나 먼저 관심 목록에서 삭제해 주세요. <br>";
			String goBack = "/mypage/myblock";
			
			reAttr.addFlashAttribute("title","차단 실패");
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
		
		reAttr.addFlashAttribute("message","차단 성공");
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
	
	// 쪽지
	@RequestMapping(value="note")
	public String note(
			HttpSession session,
			Model model,
			@RequestParam(defaultValue="") String condition,
			@RequestParam(defaultValue="") String keyword
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		String userId = memberInfo.getUserId();
		int blockNum = service.getBlockCount(userId);
		
		Map<String, Object> listMap1 = new HashMap<String, Object>();
		listMap1.put("userId", userId);
		listMap1.put("condition", condition);
		listMap1.put("keyword", keyword);
		listMap1.put("blockNum", blockNum);
		
		List<Note> senderList = service.listNoteSender(listMap1);
		List<Note> receiverList = service.listNoteReceiver(listMap1);
		
		List<NoteIU> iuList = new ArrayList<NoteIU>();
		List<Note> rawList = new ArrayList<Note>();
		
		// senderList 나에게 보낸 사람 : sender=You, receiver=Me
		for (int i=0; i<senderList.size(); i++) {
			NoteIU idxIUdto = new NoteIU();
			
			idxIUdto.setMeId(userId);
			idxIUdto.setMeNick(memberInfo.getUserNickName());
			idxIUdto.setYouId(senderList.get(i).getSendId());
			idxIUdto.setYouNick(senderList.get(i).getUserNickName());
			
			iuList.add(idxIUdto);
		}
		
		// receiverList 나로부터 받은 사람 : sender=Me, receiver=You
		for (int i=0; i<receiverList.size(); i++) {
			NoteIU idxIUdto = new NoteIU();
			
			idxIUdto.setMeId(userId);
			idxIUdto.setMeNick(memberInfo.getUserNickName());
			idxIUdto.setYouId(receiverList.get(i).getReceiveId());
			idxIUdto.setYouNick(receiverList.get(i).getUserNickName());
			
			iuList.add(idxIUdto);				
		}
		
		Collections.sort(iuList, new IUComparator()); // 닉네임대로 정렬 먼저 한 이후 아래에서 시간순서대로 정렬함.
			// 초 단위의 같은시간대에 여러 데이터 입력 시에 이 부분이 없으면 오류 (*버그) 발생.
		
		for (int i=0; i<iuList.size(); i++) {
			NoteIU iuDto = iuList.get(i);
			
			Map<String, Object> listMap2 = new HashMap<String, Object>();
			listMap2.put("meId", iuDto.getMeId());
			listMap2.put("youId", iuDto.getYouId());
			listMap2.put("condition", condition);
			listMap2.put("keyword", keyword);
			
			Note noteDto = service.readNoteLastTime(listMap2);
			noteDto.setMeId(iuDto.getMeId());
			noteDto.setMeNick(iuDto.getMeNick());
			noteDto.setYouId(iuDto.getYouId());
			noteDto.setYouNick(iuDto.getYouNick());
			
			double hourPassed = noteDto.getRecentDPlus();
			int timePassed = 0;
			String timeMsg = "";
			
			if (hourPassed < 1) {	// 1일보다 적을 때
				hourPassed = hourPassed * 24;	// 일 -> 시간
				if (hourPassed < 1) {	// 1시간보다 작을 때
					hourPassed = hourPassed * 60;	// 시간 -> 분
					if (hourPassed < 1) {	// 1분보다 작을 때
						timePassed = (int)(hourPassed * 60);
						timeMsg = timePassed+" 초 전";
					} else {
						timePassed = (int)hourPassed;
						timeMsg = timePassed+" 분 전";
					}					
				} else {
					timePassed = (int)hourPassed;
					timeMsg = timePassed+" 시간 전";
				}
				
			} else {				// 하루 이상
				if (hourPassed/7 >= 1) {	// 1주보다 많을 때
					if (hourPassed/362.25 >= 1) {	// 1년보다 많을 때
						timePassed = (int) (hourPassed/365.25);
						timeMsg = timePassed+" 년 전";
					} else {	// 주 단위
						timePassed = (int) (hourPassed/7);
						timeMsg = timePassed+" 주 전";					}
				} else {	// 일 단위
					timePassed = (int)hourPassed;
					timeMsg = timePassed+" 일 전";
				}
			}
			
			noteDto.setTimeMsg(timeMsg);
			
			if (noteDto.getNoteContent().length() >= 15) {
				noteDto.setNoteContent(noteDto.getNoteContent().substring(0,15)+"...");
			}
			
			Map<String, Object> notReadMap = new HashMap<String, Object>();
			notReadMap.put("meId", noteDto.getMeId());
			notReadMap.put("youId", noteDto.getYouId());
			
			noteDto.setNotReadCount(service.countNotReadEachNote(notReadMap));
			
			rawList.add(noteDto);
		}
		
		Collections.sort(rawList, new NoteTimeComparator());
		
		for (int i=1; i<rawList.size(); i++) {
			if (rawList.get(i).getYouId().equals(rawList.get(i-1).getYouId())) {
				rawList.remove(i);
			}
		}
		
		// Collections.sort(rawList, new NoteTimeComparator());
		
		model.addAttribute("noteFriendList",rawList);
		
		return ".mypage.note";
	}
	
	@RequestMapping(value="notenote")
	public String notenote (
			HttpSession session,
			Model model,
		 	@RequestParam String youNick,
		 	@RequestParam(defaultValue = "false") String gomain,
		 	HttpServletRequest req,
		 	final RedirectAttributes reAttr
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		
		// GET방식일 경우
		if (req.getMethod().equalsIgnoreCase("get")) {
			youNick = URLDecoder.decode(youNick,"utf-8");
		}
		
		String userId = memberInfo.getUserId();
		String youId = service.readUserIdByNickName(youNick);
		
		List<BlockedUser> blockList = service.listBlockedUser(userId);
		for (BlockedUser dto : blockList) {
			if (dto.getTarget_id().equals(youId)) {
				String msg = "닉네임 ["+youNick+"] 에 해당하는 유저가 이미 차단 목록에 있습니다. <br>";
				msg += "쪽지를 보내려면 먼저 차단을 해제해 주세요. <br>";
				String goBack = "/mypage/note";
				
				reAttr.addFlashAttribute("title","전송 오류");
				reAttr.addFlashAttribute("message",msg);
				reAttr.addFlashAttribute("goBack",goBack);
				
				return "redirect:/member/complete";
			}
		}
		
		// 상대방이 보낸 메시지를 읽음으로 표시
		Map<String, Object> readMap = new HashMap<String, Object>();
		readMap.put("meId", userId);
		readMap.put("youId", youId);
		service.updateNoteReadDate(readMap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meId", userId);
		map.put("youId", youId);
		
		List<Note> list = service.listNoteNote(map);
		
		for (Note dto:list) {
			// 엔터 br태그로 교체
			dto.setNoteContent(dto.getNoteContent().replaceAll("\n", "<br>"));
			
			// timeMsg 세팅
			double hourPassed = dto.getRecentDPlus();
			int timePassed = 0;
			String timeMsg = "";
			
			if (hourPassed < 1) {	// 1일보다 적을 때
				hourPassed = hourPassed * 24;	// 일 -> 시간
				if (hourPassed < 1) {	// 1시간보다 작을 때
					hourPassed = hourPassed * 60;	// 시간 -> 분
					if (hourPassed < 1) {	// 1분보다 작을 때
						timePassed = (int)(hourPassed * 60);
						timeMsg = timePassed+" 초 전";
					} else {
						timePassed = (int)hourPassed;
						timeMsg = timePassed+" 분 전";
					}					
				} else {
					timePassed = (int)hourPassed;
					timeMsg = timePassed+" 시간 전";
				}
				
			} else {				// 하루 이상
				if (hourPassed/7 >= 1) {	// 1주보다 많을 때
					if (hourPassed/362.25 >= 1) {	// 1년보다 많을 때
						timePassed = (int) (hourPassed/365.25);
						timeMsg = timePassed+" 년 전";
					} else {	// 주 단위
						timePassed = (int) (hourPassed/7);
						timeMsg = timePassed+" 주 전";					}
				} else {	// 일 단위
					timePassed = (int)hourPassed;
					timeMsg = timePassed+" 일 전";
				}
			}
			
			dto.setTimeMsg(timeMsg);
		}
		
		model.addAttribute("youNick",youNick);
		model.addAttribute("list",list);
		model.addAttribute("youId",youId);
		model.addAttribute("gomain",gomain);
		
		return ".mypage.notenote";
	}
	
	@RequestMapping(value="sendNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendNote (
			@RequestParam String sendId,
			@RequestParam String receiveId,
			@RequestParam String content,
			HttpSession session
			) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		
		if (!sendId.equals(userId)) {
			model.put("state", "false");
			model.put("message", "올바르지 않은 접근입니다.");
			return model;
		}
		
		if (sendId.equals(receiveId)) {
			model.put("state", "false");
			model.put("message", "자기 자신에게는 쪽지를 전송할 수 없습니다.");
			return model;
		}
		
		List<BlockedUser> blockList = service.listBlockedUser(userId);
		for (BlockedUser dto:blockList) {
			if (dto.getTarget_id().equals(receiveId)) {
				model.put("state", "false");
				model.put("message", "차단한 유저에게는 전송할 수 없습니다.");
				return model;
			}
		}
		
		try {
			Map<String, Object> mapperMap = new HashMap<String, Object>();
			mapperMap.put("sendId", sendId);
			mapperMap.put("receiveId", receiveId);
			mapperMap.put("noteContent", content);
			
			service.insertNote(mapperMap);
			
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		model.put("state","true");
		model.put("message", "메시지 전송 성공");
		return model;
	}
	
	@RequestMapping(value="sendNoteAtMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendNoteAtMain(
			@RequestParam String sendId,
			@RequestParam String targetNickName,
			@RequestParam String content,
			HttpSession session
			) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		if (!userId.equals(sendId)) {
			model.put("state", "unAuthorizedAccess"); 		// ErrCode 01
			return model;
		}
		
		try {
			String receiveId = service.readUserIdByNickName(targetNickName);
			
			if (receiveId==null || receiveId.equals("")) {
				model.put("state","nickNameNotFound");
				return model;
			}
			
			if (sendId.equals(receiveId)) {
				model.put("state", "selfSendErr");
				return model;
			}
			
			List<BlockedUser> blockList = service.listBlockedUser(userId);
			for (BlockedUser dto:blockList) {
				if (dto.getTarget_id().equals(receiveId)) {
					model.put("state", "blockReceiverException");
					return model;
				}
			}
			
			Map<String, Object> mapperMap = new HashMap<String, Object>();
			mapperMap.put("sendId", sendId);
			mapperMap.put("receiveId", receiveId);
			mapperMap.put("noteContent", content);
			
			service.insertNote(mapperMap);
			
		} catch (Exception e) {
			model.put("state", "error"); 					// ErrCode 99
			return model;
		}
		
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping(value="deleteNoteMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteNoteMsg(
			@RequestParam String noteNumStr,
			@RequestParam String sendId,
			@RequestParam String receiveId,
			@RequestParam String meId,
			HttpSession session
			) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		if (!userId.equals(meId)) {
			model.put("state", "unAuthorizedAccess");
			return model;
		}
		
		try {
			int noteNum = Integer.parseInt(noteNumStr);
			
			Map<String, Object> mapperMap = new HashMap<String, Object>();
			mapperMap.put("noteNum", noteNum);
			
			if (meId.equals(sendId)) {
				service.updateSendDelete(mapperMap);
			} else if (meId.equals(receiveId)) {
				service.updateReceiveDelete(mapperMap);
			}
		} catch (NumberFormatException e) {
			model.put("state", "numberFormatException");
			return model;
		} catch (Exception e) {
			model.put("state", "error");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping(value="deleteNoteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteNoteUser(
			HttpSession session,
			@RequestParam String meId,
			@RequestParam String youId
			) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		if (!userId.equals(meId)) {
			model.put("state", "unAuthorizedAccess");
			return model;
		}
		
		try {
			Map<String, Object> sendMap = new HashMap<String, Object>();
			sendMap.put("isSendOrRec", "isSendDelete");
			sendMap.put("sendId",meId);
			sendMap.put("receiveId",youId);
			
			Map<String, Object> recMap = new HashMap<String, Object>();
			recMap.put("isSendOrRec", "isRecDelete");
			recMap.put("sendId",youId);
			recMap.put("receiveId",meId);
			
			service.updateUserAllDelete(sendMap);
			service.updateUserAllDelete(recMap);
		} catch (Exception e) {
			model.put("state", "error");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping(value="deleteEntireMyNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteEntireMyNote(
			HttpSession session,
			@RequestParam String meId
			) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		if (!userId.equals(meId)) {
			model.put("state", "unAuthorizedAccess");
			return model;
		}
		
		try {
			Map<String, Object> sendMap = new HashMap<String, Object>();
			sendMap.put("meId", meId);
			sendMap.put("isSendOrRec", "isSendDelete");
			sendMap.put("sendOrRecId", "sendId");
			
			Map<String, Object> recMap = new HashMap<String, Object>();
			recMap.put("meId", meId);
			recMap.put("isSendOrRec", "isRecDelete");
			recMap.put("sendOrRecId", "receiveId");
			
			service.updateEntireMyNoteDelete(sendMap);
			service.updateEntireMyNoteDelete(recMap);
		} catch (Exception e) {
			model.put("state", "error");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping("myrating")
	public String myrating(
			HttpSession session,
			Model model,
			HttpServletRequest req,
			@RequestParam(defaultValue = "all") String typeName,
			@RequestParam(value="page", defaultValue="1") int current_page
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		String userId = memberInfo.getUserId();
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		if (typeName.equals("product")) {
			dataCount = service.countProductRating(userId);
		} else if (typeName.equals("giftycon")) {
			dataCount = service.countGiftyRating(userId);
		} else {		// all
			dataCount = service.countRating(userId);
		}
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		
		List<Rating> ratingList = null;
		
		String query = "typeName="+typeName;
		String listUrl = cp + "/mypage/myrating";
		if (typeName != null) {
			listUrl+="?"+query;
		}
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		Double avgPrd = 0.0;
		String avgPrdS = "";
		int idxPrd = 0;
		Double avgMan = 0.0;
		String avgManS = "";
		int idxMan = 0;
		
		try {
			if (typeName.equals("product")) {
				ratingList = service.listProductRating(map);
			} else if (typeName.equals("giftycon")) {
				ratingList = service.listGiftyRating(map);
			} else {		// all
				ratingList = service.listRating(map);				
			}
			
			for (Rating dto : ratingList) {
				if (dto.getPrdStar() != null) {
					idxPrd++;
					avgPrd += (double)dto.getPrdStar();
				}
				if (dto.getMannerStar() != null) {
					idxMan++;
					avgMan += (double)dto.getMannerStar();
				}
			}
			
			if (idxPrd > 0) {
				avgPrd = (double)(avgPrd/idxPrd);
				avgPrdS = String.format("%.1f", avgPrd);
			}
			
			if (idxMan > 0) {
				avgMan = (double)(avgMan/idxMan);
				avgManS = String.format("%.1f", avgMan);
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		model.addAttribute("ratingList",ratingList);
		model.addAttribute("avgPrdStar",avgPrdS);
		model.addAttribute("avgManStar",avgManS);
		model.addAttribute("typeName",typeName);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		return ".mypage.myrating";
	}
	
	@RequestMapping("yourpage")
	public String yourpage(
			HttpSession session,
			@RequestParam String userNickName,
			Model model,
			HttpServletRequest req,
			@RequestParam(defaultValue = "all") String typeName,
			@RequestParam(value="page", defaultValue="1") int current_page
			) throws Exception {
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {	// 비회원은 확인 불가
			return "redirect:/member/login";
		}
		
		userNickName = URLDecoder.decode(userNickName,"UTF-8");
		
		String userId = service.readUserIdByNickName(userNickName);
		if (userId == null || userId.equals("")) {
			return "redirect:/error/error";
		}
		
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
		
		// 유저 평가 불러오기
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		if (typeName.equals("product")) {
			dataCount = service.countProductRating(userId);
		} else if (typeName.equals("giftycon")) {
			dataCount = service.countGiftyRating(userId);
		} else {		// all
			dataCount = service.countRating(userId);
		}
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		
		List<Rating> ratingList = null;
		
		String query = "typeName="+typeName;
		String listUrl = cp + "/mypage/myrating";
		if (typeName != null) {
			listUrl+="?"+query;
		}
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		Double avgPrd = 0.0;
		String avgPrdS = "";
		int idxPrd = 0;
		Double avgMan = 0.0;
		String avgManS = "";
		int idxMan = 0;
		
		try {
			if (typeName.equals("product")) {
				ratingList = service.listProductRating(map);
			} else if (typeName.equals("giftycon")) {
				ratingList = service.listGiftyRating(map);
			} else {		// all
				ratingList = service.listRating(map);				
			}
			
			for (Rating dto : ratingList) {
				if (dto.getPrdStar() != null) {
					idxPrd++;
					avgPrd += (double)dto.getPrdStar();
				}
				if (dto.getMannerStar() != null) {
					idxMan++;
					avgMan += (double)dto.getMannerStar();
				}
			}
			
			if (idxPrd > 0) {
				avgPrd = (double)(avgPrd/idxPrd);
				avgPrdS = String.format("%.1f", avgPrd);
			}
			
			if (idxMan > 0) {
				avgMan = (double)(avgMan/idxMan);
				avgManS = String.format("%.1f", avgMan);
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		model.addAttribute("ratingList",ratingList);
		model.addAttribute("avgPrdStar",avgPrdS);
		model.addAttribute("avgManStar",avgManS);
		model.addAttribute("typeName",typeName);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		model.addAttribute("mannerDto",mannerDto);
		model.addAttribute("userNick",userNickName);
		return ".mypage.yourpage";
	}
	
	@RequestMapping("activity")
	public String activity(
			HttpSession session,
			@RequestParam(defaultValue = "true") String product,
			@RequestParam(defaultValue = "true") String giftycon,
			@RequestParam(defaultValue = "true") String vbbs,
			@RequestParam(defaultValue = "true") String vbbsReply,
			@RequestParam(value="page", defaultValue="1") int current_page,
			Model model,
			HttpServletRequest req
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		String userId = memberInfo.getUserId();
		
		if (current_page < 1) {
			current_page = 1;
		}
		
		if (!product.equals("true")&&
				!giftycon.equals("true")&&
				!vbbs.equals("true")&&
				!vbbsReply.equals("true") ) {
			product = giftycon = vbbs = vbbsReply = "true";
		}
		
		String cp = req.getContextPath();
		
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("product", product);
			map.put("giftycon", giftycon);
			map.put("vbbs", vbbs);
			map.put("vbbsReply", vbbsReply);
			
			int rows = 10;
			int total_page = 0;
			
			int countActivity = service.countActivity(map);
			
			if (countActivity != 0) {
				total_page = myUtil.pageCount(rows, countActivity);
			}
			
			if (total_page < current_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			String listUrl = cp + "/mypage/activity";
			String query = "product="+product+"&giftycon="+giftycon+"&vbbs="+vbbs+"&vbbsReply="+vbbsReply;
			listUrl += "?"+query;
			String paging = myUtil.paging(current_page, total_page, listUrl);
			map.put("start",start);
			map.put("end",end);
			List<Activity> list = service.listActivity(map);
			
			for(Activity dto:list) {
				if (dto.getSubject().length()>16) {
					dto.setSubject(dto.getSubject().substring(0, 16)+"...");
				}
			}
			
			model.addAttribute("countActivity", countActivity);
			model.addAttribute("list", list);
			model.addAttribute("dataCount", countActivity);
			model.addAttribute("page", current_page);
			model.addAttribute("total_page", total_page);
			model.addAttribute("paging", paging);
			
			model.addAttribute("product",product);
			model.addAttribute("giftycon",giftycon);
			model.addAttribute("vbbs",vbbs);
			model.addAttribute("vbbsReply",vbbsReply);
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return ".mypage.activity";
	}
	
	@RequestMapping("account")
	public String account(
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="true") String chkSel,
			@RequestParam(defaultValue="true") String chkBuy,
			@RequestParam(defaultValue="true") String chkPrd,
			@RequestParam(defaultValue="true") String chkGfc,
			@RequestParam(defaultValue="false") String acStartDate,
			@RequestParam(defaultValue="false") String acEndDate,
			HttpSession session,
			Model model,
			HttpServletRequest req
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		String userId = memberInfo.getUserId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		
		if (!chkSel.equals("true")&&!chkBuy.equals("true")) {
			chkSel = chkBuy = "true";
		}
		
		if (!chkPrd.equals("true")&&!chkGfc.equals("true")) {
			chkPrd = chkGfc = "true";
		}
		
		List<Account> list = null;
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		String listUrl = "";
		String paging = "";
		
		try {
			String chkbs = "";
			String chkpg = "";
			if(!chkSel.equals(chkBuy)) {
				if (chkSel.equals("true")) {
					chkbs="sell";
				} else { 
					chkbs = "buy";
				}
				map.put("chkbs", chkbs);
			}
			if(!chkPrd.equals(chkGfc)) {
				if (chkPrd.equals("true")) {
					chkpg="product";
				} else {
					chkpg = "gifty";
				}
				map.put("chkpg", chkpg);
			}
			
			if (!acStartDate.equals("false")) {
				map.put("acStartDate", acStartDate);
			}
			if (!acEndDate.equals("false")) {
				map.put("acEndDate", acEndDate);
			}
			
			System.out.println(":::: buy or sell : "+chkbs);
			System.out.println(":::: prd or gifty : "+chkpg);
			
			dataCount = service.countAccount(map);
			
			if (dataCount != 0) {
				total_page = myUtil.pageCount(rows, dataCount);
			}
			
			if (total_page < current_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			map.put("start",start);
			map.put("end",end);
						
			listUrl = cp + "/mypage/account";
			String query = "chkSel="+chkSel+"&chkBuy="+chkBuy+"&chkPrd="+chkPrd+"&chkGfc="+chkGfc;
			listUrl += "?"+query;
			paging = myUtil.paging(current_page, total_page, listUrl);
			
			list = service.listAccount(map);
			
			for (Account dto : list) {
				if (dto.getUserId().equals(dto.getSellerId())) {
					dto.setBsType("sell");
				} else {
					dto.setBsType("buy");
				}
				
				if (dto.getSubject().length()> 11) {
					dto.setSubject(dto.getSubject().substring(0,11)+"...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		model.addAttribute("dataCount",dataCount);
		model.addAttribute("list",list);
		model.addAttribute("paging",paging);
		model.addAttribute("listUrl",listUrl);
		model.addAttribute("chkSel",chkSel);
		model.addAttribute("chkBuy",chkBuy);
		model.addAttribute("chkPrd",chkPrd);
		model.addAttribute("chkGfc",chkGfc);
		model.addAttribute("page", current_page);
		if (!acStartDate.equals("false")) {
			acStartDate = acStartDate.substring(0,4)+"-"
					+acStartDate.substring(4,6)+"-"
					+acStartDate.substring(6);
			model.addAttribute("acStartDate",acStartDate);
		}
		if (!acEndDate.equals("false")) {
			acEndDate = acEndDate.substring(0,4)+"-"
					+acEndDate.substring(4,6)+"-"
					+acEndDate.substring(6);
			model.addAttribute("acEndDate",acEndDate);
		}
		
		return ".mypage.account";
	}
	
	@RequestMapping("report")
	public String reportForm(
			HttpSession session,
			Model model,
			HttpServletRequest req,
			@RequestParam String userNick
			) throws Exception {
		userNick = URLDecoder.decode(userNick,"UTF-8");
		List<RepMemberReason> list = null;
		
		try {
			list = service.listRepMemReason();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		model.addAttribute("reasonList",list);
		model.addAttribute("userNick",userNick);
		return ".mypage.report";
	}
	
	@RequestMapping("reportSubmit")
	public String reportSubmit(
			@RequestParam String userNick,
			@RequestParam String content,
			@RequestParam int reasonNum,
			final RedirectAttributes reAttr,
			HttpSession session,
			HttpServletRequest req
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userId = memberInfo.getUserId();
		
		String encodedNick = userNick;
		
		if (req.getMethod().equalsIgnoreCase("get")) {
			userNick = URLDecoder.decode(userNick,"utf-8");
			content = URLDecoder.decode(content,"utf-8");
		}
		
		String target_id = service.readUserIdByNickName(userNick);
		if (target_id == null || target_id.equals("")) {
			String goBack = "/mypage/yourpage?userNickName=" +encodedNick;
			reAttr.addFlashAttribute("title","신고 실패");
			reAttr.addFlashAttribute("message","신고 접수를 실패했습니다.<br>관리자에게 문의하세요.");
			reAttr.addFlashAttribute("goBack",goBack);
			return "redirect:/mypage/complete";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNick", userNick);
		map.put("content", content);
		map.put("reasonNum", reasonNum);
		map.put("userId", userId);
		map.put("target_id", target_id);
		
		try {
			service.insertReportMember(map);
		} catch (Exception e) {
			e.printStackTrace();
			String goBack = "/mypage/yourpage?userNickName=" +encodedNick;
			reAttr.addFlashAttribute("title","신고 실패");
			reAttr.addFlashAttribute("message","신고 접수를 실패했습니다.<br>관리자에게 문의하세요.");
			reAttr.addFlashAttribute("goBack",goBack);
			return "redirect:/mypage/complete";
		}
		
		String goBack = "/mypage/yourpage?userNickName="+encodedNick;
		reAttr.addFlashAttribute("title","신고 완료");
		reAttr.addFlashAttribute("message","신고 접수가 완료되었습니다");
		reAttr.addFlashAttribute("goBack",goBack);
		return "redirect:/mypage/complete";
	}
	
	@RequestMapping("complete")
	public String complete (
			@ModelAttribute("message") String message
			) {
		if (message == null || message.length() == 0 ) return "redirect:/";
		
		return ".mypage.complete";
	}
	
	@RequestMapping(value = "notReadTotalNoteCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> notReadTotalNoteCount(HttpSession session) throws Exception {
		String state = "false";
		int notReadNote = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
			
			if (info == null) {
				map.put("state", "false");
				map.put("notReadNote", 0);
				
				return map;
			}
			
			int blockNum = service.getBlockCount(info.getUserId());

			Map<String, Object> countNoteMap = new HashMap<String, Object>();
			countNoteMap.put("blockNum", blockNum);
			countNoteMap.put("meId", info.getUserId());
			
			notReadNote = service.countNotReadNote(countNoteMap);
			state = "true";
		} catch (Exception e) {
		}
		
		map.put("state", state);
		map.put("notReadNote", notReadNote);
		
		return map;
	}
}

class IUComparator implements Comparator<NoteIU> {

	@Override
	public int compare(NoteIU o1, NoteIU o2) {
		if (o1.getYouNick().compareTo(o2.getYouNick())>0) return 1;
		if (o1.getYouNick().compareTo(o2.getYouNick())<0) return -1;
		return 0;
	}
	
}

class NoteTimeComparator implements Comparator<Note> {

	@Override
	public int compare(Note o1, Note o2) {
		if (o1.getRecentDPlus() > o2.getRecentDPlus()) return 1;
		if (o1.getRecentDPlus() < o2.getRecentDPlus()) return -1;
		return 0;
	}
	
}

class NoteTimeRevComparator implements Comparator<Note> {

	@Override
	public int compare(Note o1, Note o2) {
		if (o1.getRecentDPlus() > o2.getRecentDPlus()) return 1;
		if (o1.getRecentDPlus() < o2.getRecentDPlus()) return -1;
		return 0;
	}
	
}

class NoteNickComparator implements Comparator<Note> {

	@Override
	public int compare(Note o1, Note o2) {
		if (o1.getYouNick().compareTo(o2.getYouNick())>0) return 1;
		if (o1.getYouNick().compareTo(o2.getYouNick())<0) return -1;
		return 0;
	}
	
}
