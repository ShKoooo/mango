package com.sp.mango.admin.member;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sp.mango.common.MyUtil;

@Controller("admin.member.memberController")
@RequestMapping(value="/admin/member/*")
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value="list")
	public String list(
			Model model,
			HttpServletRequest req,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "false") String chkBlk,
			@RequestParam(defaultValue = "false") String chkBus,
			@RequestParam(defaultValue = "false") String chkRep,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String userNickName
			) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cp = req.getContextPath();
		
		int rows = 15;
		int total_page = 0;
		int dataCount = 0;
		
		String decodedNickName = URLDecoder.decode(userNickName,"UTF-8");
		
		map.put("chkBlk", chkBlk);
		map.put("chkBus", chkBus);
		map.put("chkRep", chkRep);
		map.put("userId", userId);
		map.put("userNickName", decodedNickName);
		
		dataCount = service.countMember(map);
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		map.put("start", start);
		map.put("end", end);
		
		List<Member> list = service.list(map);
		
		String query = "page="+current_page+"&chkBlk="+chkBlk;
		if (userId != null && !userId.equals("")) {
			query += "&userId="+userId;
		}
		if (userNickName != null && !userNickName.equals("")) {
			query += "&userNickName="+userNickName;
		}
		String listUrl = cp + "/admin/member/list";
		listUrl += "?"+query;
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		model.addAttribute("list",list);
		model.addAttribute("page", current_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		model.addAttribute("chkBlk", chkBlk);
		model.addAttribute("chkBus", chkBus);
		model.addAttribute("chkRep", chkRep);
		model.addAttribute("userId", userId);
		model.addAttribute("userNickName", userNickName);
		
		return ".admin.member.list";
	}
	
	@RequestMapping(value="id")
	public String id (
			Model model,
			HttpServletRequest req,
			@RequestParam String memberId,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "false") String chkBlk,
			@RequestParam(defaultValue = "false") String chkBus,
			@RequestParam(defaultValue = "false") String chkRep,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String userNickName
			) throws Exception {
		
		MemberID memberDto = null;
		List<MemberAddr> addrList = null;
		List<ReportMember> reportList = null;
		// List<MemberState> stateList = null;
		
		memberDto = service.readMember(memberId);
		addrList = service.listMemberAddr(memberId);
		reportList = service.listReportMember(memberId);
		// stateList = service.listMemberState(memberId);
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("memberDto",memberDto);
		model.addAttribute("addrList",addrList);
		model.addAttribute("reportList",reportList);
		// model.addAttribute("stateList",stateList);
		
		model.addAttribute("page", current_page);
		model.addAttribute("chkBlk", chkBlk);
		model.addAttribute("chkBus", chkBus);
		model.addAttribute("chkRep", chkRep);
		model.addAttribute("userId", userId);
		model.addAttribute("userNickName", userNickName);
		
		return ".admin.member.id";
	}
	
	@RequestMapping(value="updateState")
	public String updateState(
			Model model,
			HttpServletRequest req,
			@RequestParam String memberId,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "false") String chkBlk,
			@RequestParam(defaultValue = "false") String chkBus,
			@RequestParam(defaultValue = "false") String chkRep,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String userNickName
			) throws Exception {
		
		List<MemberState> stateList = null;
		List<MemberStateCode> stateCodeList = null;
		
		stateCodeList = service.listMemberStateCode();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String cp = req.getContextPath();
		
		int rows = 10;
		int total_page = 0;
		int dataCount = 0;
		
		dataCount = service.countMemberState(memberId);
		
		if (dataCount != 0) {
			total_page = myUtil.pageCount(rows, dataCount);
		}
		
		if (dataCount != 0 && total_page < current_page) {
			current_page = total_page;
		}
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		
		map.put("memberId", memberId);
		map.put("start", start);
		map.put("end", end);
		
		stateList = service.listMemberState(map);
		
		if (stateList != null) {
			for (MemberState dto : stateList) {
				dto.setMemo(dto.getMemo().replaceAll("\n", "<br>"));
			}
		}
		
		String query = "page="+current_page+"&chkBlk="+chkBlk+"&chkBus="+chkBus+"&chkRep="+chkRep;
		if (userId != null && !userId.equals("")) {
			query += "&userId="+userId;
		}
		if (userNickName != null && !userNickName.equals("")) {
			query += "&userNickName="+userNickName;
		}
		
		String listUrl = cp + "/admin/member/list";
		listUrl += "?"+query;
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		model.addAttribute("memberId", memberId);
		model.addAttribute("stateList",stateList);
		model.addAttribute("stateCodeList",stateCodeList);
		model.addAttribute("paging", paging);
		
		model.addAttribute("page", current_page);
		model.addAttribute("chkBlk", chkBlk);
		model.addAttribute("chkBus", chkBus);
		model.addAttribute("chkRep", chkRep);
		model.addAttribute("userId", userId);
		model.addAttribute("userNickName", userNickName);
		
		return ".admin.member.updateState";
	}
	
	@RequestMapping(value="updateSubmit")
	public String updateStateSubmit(
			final RedirectAttributes reAttr,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "false") String chkBlk,
			@RequestParam(defaultValue = "false") String chkBus,
			@RequestParam(defaultValue = "false") String chkRep,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String userNickName,
			@RequestParam String memberId,
			@RequestParam int msCodeNum,
			@RequestParam String memo,
			@RequestParam Double minusDeg
			) throws Exception {
		
		String decodedMemo = URLDecoder.decode(memo,"UTF-8");
		
		Map<String, Object> stateMap = new HashMap<String, Object>();
		stateMap.put("memberId", memberId);
		stateMap.put("memo", decodedMemo);
		stateMap.put("msCodeNum", msCodeNum);
		
		service.insertMemberState(stateMap);
		
		Map<String, Object> enableMap = new HashMap<String, Object>();
		int memberEnable = 1;
		if (msCodeNum == 400 || msCodeNum == 404) {
			memberEnable = 0;
			
			service.updateMemberLoginFail(memberId);
		}
		enableMap.put("memberId", memberId);
		enableMap.put("enable", memberEnable);
		
		service.updateMemberEnable(enableMap);
		
		Map<String, Object> degMap = new HashMap<String, Object>();
		degMap.put("memberId", memberId);
		degMap.put("minusDeg", minusDeg);
		
		service.updateMannerMinusDeg(degMap);
		
		reAttr.addAttribute("page",current_page);
		reAttr.addAttribute("chkBlk",chkBlk);
		reAttr.addAttribute("chkBus",chkBus);
		reAttr.addAttribute("chkRep",chkRep);
		reAttr.addAttribute("userId",userId);
		reAttr.addAttribute("userNickName",userNickName);
		reAttr.addAttribute("memberId",memberId);
		
		return "redirect:/admin/member/id";
	}
	
	@RequestMapping(value="clearReport")
	public String clearReport(
			final RedirectAttributes reAttr,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue = "false") String chkBlk,
			@RequestParam(defaultValue = "false") String chkBus,
			@RequestParam(defaultValue = "false") String chkRep,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String userNickName,
			@RequestParam String memberId,
			@RequestParam int repMemNum
			) throws Exception {
		
		try {
			service.updateReportChecked(repMemNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		reAttr.addAttribute("page",current_page);
		reAttr.addAttribute("chkBlk",chkBlk);
		reAttr.addAttribute("chkBus",chkBus);
		reAttr.addAttribute("chkRep",chkRep);
		reAttr.addAttribute("userId",userId);
		reAttr.addAttribute("userNickName",userNickName);
		reAttr.addAttribute("memberId",memberId);
		
		return "redirect:/admin/member/id";
	}
	
	// TODO : 타 페이지 (다른 Controller) 신고처리 --> 신고 들어온 게시물만 뿌리기!!, ORDER 게시물 번호 DESC
	// TODO : 목록 띄우고, 옆에 확인 표시만.
}
