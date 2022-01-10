package com.sp.mango.member;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

import com.sp.mango.mypage.MypageService;

@Controller("member.memberController")
@RequestMapping(value="/member/*")
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private MypageService mypageService;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String loginForm() {
		return ".member.login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String loginSubmit(
			@RequestParam String userId,
			@RequestParam String userPwd,
			HttpSession session,
			Model model
			) throws Exception {
		
		Member dto = service.loginMember(userId);
		if (dto == null) {
			model.addAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
			return ".member.login";
		}
		
		Integer loginFail = service.selectLoginFail(dto.getUserId());
		Integer userEnable = service.selectEnable(dto.getUserId());
		System.out.println(":::: Enable : "+userEnable);
		System.out.println(":::: userPwd : "+userPwd);
		System.out.println(":::: dto.getUserPwd() : "+dto.getUserPwd());
		
		if (loginFail == null) {
			service.updateDefaultLoginFail(userId);
		}
		if (userEnable == null) {
			service.updateDefaultEnable(userId);
		}
		if (loginFail >= 5) {
			service.updateLoginFail(userId);
			
			model.addAttribute("message", "비밀번호 오류 횟수 초과입니다.<br>관리자에게 문의하세요.");
			if (loginFail < 6) {
				Map<String, Object> enableMap = new HashMap<String, Object>();
				enableMap.put("userId", userId);
				enableMap.put("value", 0);
				service.updateEnable(enableMap);
				
				Map<String, Object> stateMap = new HashMap<String, Object>();
				stateMap.put("userId", userId);
				stateMap.put("memo", "Activated by: "+userId+"\n사유: 비밀번호 오류 횟수 초과");
				service.insertMemberState(stateMap);
			}
			
			return ".member.login";
		}
		if (userEnable < 1) {
			model.addAttribute("message", "차단된 계정입니다.<br>관리자에게 문의하세요.");
			return ".member.login";
		}
		
		if (!userPwd.contentEquals(dto.getUserPwd())) {
			service.updateLoginFail(userId);
			
			model.addAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
			return ".member.login";
		}
		
		service.updateDefaultLoginFail(userId);		// 정상 접근 시
		
		// 세션에 로그인정보 저장
		MemberSessionInfo info = new MemberSessionInfo();
		info.setUserId(dto.getUserId());
		info.setUserName(dto.getUserName());
		info.setUserNickName(dto.getUserNickName());
		info.setMembership(dto.getMembershipNum());
		
		session.setMaxInactiveInterval(30*60);	// 30분
		
		session.setAttribute("member", info);
		
		// 로그인 이전 URI로 이동 - LoginCheckInterceptor
		String uri = (String) session.getAttribute("preLoginURI");
		
		session.removeAttribute("preLoginURI");
		if (uri == null) { 
			uri = "redirect:/";
		} else if (uri.indexOf("/images/slide") >= 0) {	// 임시 처리
			uri = "redirect:/";
		} else if (uri.indexOf("/uploads/") >= 0) {	// 임시 처리 2
			uri = "redirect:/";
		} else {
			uri = "redirect:" + uri;
		}
		
		return uri;
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		// 세션에 저장된 정보 지우기
		session.removeAttribute("member");

		// 세션에 저장된 모든 정보 지우고, 세션초기화
		session.invalidate();

		return "redirect:/";
	}
	
	@RequestMapping(value = "member", method = RequestMethod.GET)
	public String memberForm (
			Model model
			) {
		
		model.addAttribute("mode","member");
		return ".member.member";
	}
	
	@RequestMapping(value = "member", method = RequestMethod.POST)
	public String memberSubmit (
			Member dto,
			final RedirectAttributes reAttr,
			Model model,
			HttpSession session
			) {
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "photo";
		
		try {
			dto.setUserTel(dto.getTel1(), dto.getTel2(), dto.getTel3());
			dto.setUserEmail(dto.getEmail1(),dto.getEmail2());
			
			// 유저 가입 및 매너프로필 신규등록
			service.insertMember(dto, path);
			mypageService.insertMannerProfile(dto.getUserId());
		} catch (Exception e) {
			model.addAttribute("mode","member");
			model.addAttribute("message","회원가입이 실패했습니다.");
			return ".member.member";
		}
		
		String msg =  dto.getUserNickName()+"님의 회원가입이 정상적으로 처리되었습니다.<br>";
		msg += "메인화면으로 이동하여 로그인하시기 바랍니다.<br>";
		
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("title","회원 가입");
		
		return "redirect:/member/complete";
	}
	
	@RequestMapping(value="pwd", method=RequestMethod.GET)
	public String pwdForm(
			String dropout,
			String mode,
			Model model
			) {
		
		if (dropout == null) {
			model.addAttribute("mode",mode);
		} else {
			model.addAttribute("mode","dropout");
		}			
		
		return ".member.pwd";
	}
	
	@RequestMapping(value="pwd", method=RequestMethod.POST)
	public String pwdSubmit(
			@RequestParam String userPwd,
			@RequestParam String mode,
			final RedirectAttributes reAttr,
			HttpSession session,
			Model model
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		Member dto = service.readMember(info.getUserId());
		
		if (dto == null) {
			session.invalidate();
			return "redirect:/";
		}
		
		if (!dto.getUserPwd().equals((userPwd))) {
			if (mode.equals("update")) {
				model.addAttribute("mode","update");
			} else {
				model.addAttribute("mode","dropout");
			}
			model.addAttribute("message","패스워드가 일치하지 않습니다.");
			return ".member.pwd";
		}
		
		if (mode.equals("dropout")) {
			
			service.deleteMember(info.getUserId());
			
			session.removeAttribute("member");
			session.invalidate();
			
			String msg = dto.getUserNickName() + "("+dto.getUserName()+") "
				+ " 님의 탈퇴처리가 정상적으로 처리되었습니다. <br>"
				+ "메인화면으로 이동하시기 바랍니다. <br>";
			
			reAttr.addFlashAttribute("title","회원 탈퇴");
			reAttr.addFlashAttribute("message",msg);
			
			return "redirect:/member/complete";
		}
		
		if (mode.equals("update")) {
			String nickChangeable = "false";
			MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
			
			if (memberInfo == null) return "redirect:/member/login";
			
			// String userId = memberInfo.getUserId();
			
			String[] telTel = dto.getUserTel().split("-");
			String[] mailMail = dto.getUserEmail().split("@");
			
			if (telTel.length == 3) {
				dto.setTel1(telTel[0]);
				dto.setTel2(telTel[1]);
				dto.setTel3(telTel[2]);
			}
			
			if (mailMail.length == 2) {
				dto.setEmail1(mailMail[0]);
				dto.setEmail2(mailMail[1]);
			}
			
			String [] nickUpdate_YMD = dto.getNickUpdate_Date().split("-");
			
			Calendar date1 = Calendar.getInstance();	// 닉변경 + 30일
			Calendar date2 = Calendar.getInstance();	// 오늘
			
			date1.set(Integer.parseInt(nickUpdate_YMD[0]),
					Integer.parseInt(nickUpdate_YMD[1]+1),
					Integer.parseInt(nickUpdate_YMD[2]+30));
			
			if (!date1.after(date2)) { // 닉변+30일이 오늘보다 미래가 아닐 때 (true)
				nickChangeable = "true";
			}
			
			
			model.addAttribute("dto",dto);
			model.addAttribute("mode","update");
			model.addAttribute("nickChangeable",nickChangeable);
			
			return ".member.member";
		} else {		// mode.equals("busnUpdate")
			MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
			
			Business businessDto = mypageService.readBusiness(memberInfo.getUserId());
			
			String[] telTel = businessDto.getBusTel().split("-");
			String[] mailMail = businessDto.getBusEmail().split("@");
			
			if (telTel.length == 3) {
				businessDto.setTel1(telTel[0]);
				businessDto.setTel2(telTel[1]);
				businessDto.setTel3(telTel[2]);
			}
			
			if (mailMail.length == 2) {
				businessDto.setEmail1(mailMail[0]);
				businessDto.setEmail2(mailMail[1]);
			}
			
			model.addAttribute("mode", "busnUpdate");
			model.addAttribute("dto", businessDto);
			return ".member.business";
		}		
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String updateSubmit(
			Member dto,
			final RedirectAttributes reAttr,
			Model model,
			HttpSession session
			) throws Exception {
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "photo";
		String goBack = "/mypage/main";
		reAttr.addFlashAttribute("goBack",goBack);
		
		try {
			dto.setUserTel(dto.getTel1(), dto.getTel2(), dto.getTel3());
			dto.setUserEmail(dto.getEmail1(),dto.getEmail2());
			
			service.updateMember(dto, path);
		} catch (Exception e) {
			String msg = dto.getUserNickName()+"님의 정보수정이 실패했습니다.";
			reAttr.addFlashAttribute("title","업체 등록 중복 방지");
			reAttr.addFlashAttribute("message",msg);
			
			return "redirect:/member/complete";
		}
		
		String msg =  dto.getUserNickName()+"님의 정보수정이 정상적으로 처리되었습니다.<br>";
		
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("title","회원 정보 수정");
		reAttr.addFlashAttribute("fin","true");
		
		return "redirect:/member/complete";
	}
	
	@RequestMapping(value="complete")
	public String complete(
			@ModelAttribute("message") String message
			) throws Exception {
		
		if (message == null || message.length() == 0 ) return "redirect:/";
		
		return ".member.complete";
	}
	
	@RequestMapping(value="userDuplCheck", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> duplCheck (
			@RequestParam String userParam,
			@RequestParam String chkWay			
			) throws Exception {
		String p = "true";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userParam", userParam);
		map.put("chkWay", chkWay);
		
		int result = service.countMemberByParam(map);
		
		if (result > 0) p = "false";
		
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("passed",p);
		
		return model;
	}
	
	@RequestMapping(value = "noAuthorized")
	public String noAuthrorized() {
		return ".member.noAuthorized";
	}
	
	@RequestMapping(value="business", method=RequestMethod.GET)
	public String businessForm(
			Model model
			) throws Exception {
		
		model.addAttribute("mode", "business");
		return ".member.business";
	}
	
	@RequestMapping(value="busDuplCheck", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> busDuplCheck (
			@RequestParam String userParam,
			@RequestParam String chkWay			
			) throws Exception {
		String p = "true";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userParam", userParam);
		map.put("chkWay", chkWay);
		
		int result = service.countBusnByParam(map);
		
		if (result > 0) p = "false";
		
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("passed",p);
		
		return model;
	}
	
	@RequestMapping(value="business", method=RequestMethod.POST)
	public String businessSubmit (
			Business dto,
			final RedirectAttributes reAttr,
			Model model,
			HttpSession session
			) {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String userNickName = memberInfo.getUserNickName();
		Integer areaNum;
		int hasAlreadyBusn = 0;
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "photo";
		
		try {
			dto.setUserId(memberInfo.getUserId());
			dto.setBusTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
			dto.setBusEmail(dto.getEmail1()+"@"+dto.getEmail2());
			
			// Business 중복등록 방지
			Map<String, Object> chkDuplmap = new HashMap<String, Object>();
			chkDuplmap.put("chkWay","userId");
			chkDuplmap.put("userParam",memberInfo.getUserId());
			hasAlreadyBusn = service.countBusnByParam(chkDuplmap);
			if (hasAlreadyBusn > 0) {
				String msg =  userNickName+"님은 이미 업체 등록을 하셨습니다.<br>";
				msg += "업체는 1개만 등록하실 수 있습니다. <br>";
				
				String goBack = "/mypage/main";
				reAttr.addFlashAttribute("title","업체 등록 중복 방지");
				reAttr.addFlashAttribute("message",msg);
				reAttr.addFlashAttribute("goBack",goBack);
				
				return "redirect:/member/complete";
			}
			
			// area 테이블에서 해당 bcode에 해당하는 areaNum 없으면 새로 입력
			areaNum = service.readAreaByBcode(dto.getBcodeCut());
			if (areaNum == null) {
				areaNum = service.getAreaSeqNum();
				dto.setAreaNum(areaNum);
				
				service.insertArea2(dto);
			} else {
				dto.setAreaNum(areaNum);
			}
			
			// Business 신규등록
			service.insertBusiness(dto, path);
		} catch (Exception e) {
			model.addAttribute("mode","member");
			model.addAttribute("message","업체 등록이 실패했습니다.");
			return ".member.business";
		}
		
		String msg =  userNickName+"님 ("+ dto.getBusNickName() +") 의 업체 등록이 정상적으로 처리되었습니다.<br>";
		
		String goBack = "/mypage/main";
		reAttr.addFlashAttribute("title","업체 등록");
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("goBack",goBack);
		reAttr.addFlashAttribute("fin","true");
		
		return "redirect:/member/complete";
	}
	
	@RequestMapping(value="busnUpdate", method=RequestMethod.POST)
	public String busnUpdateSubmit(
			Business dto,
			final RedirectAttributes reAttr,
			Model model,
			HttpSession session
			) throws Exception {
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		String msg = "";
		String goBack = "/mypage/main";
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "photo";
		
		try {
			dto.setBusTel(dto.getTel1()+"-"+dto.getTel2()+"-"+dto.getTel3());
			dto.setBusEmail(dto.getEmail1()+"@"+dto.getEmail2());
			dto.setUserId(memberInfo.getUserId());
			System.out.println(":::: "+dto.getProfileImg().toString());
			
			service.updateBusiness(dto, pathname);
		} catch (Exception e) {
			msg += dto.getBusNickName()+"님의 비즈니스 프로필 수정이 실패했습니다.";
			model.addAttribute("message", msg);
			reAttr.addFlashAttribute("title","비즈니스 프로필 수정");
			reAttr.addFlashAttribute("message",msg);
			reAttr.addFlashAttribute("goBack",goBack);
			
			return "redirect:/member/complete";
		}
		
		msg += dto.getBusNickName()+"님의 비즈니스 프로필 수정이 정상적으로 처리되었습니다.<br>";
		
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("title","비즈니스 프로필 수정");
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("goBack",goBack);
		reAttr.addFlashAttribute("fin","true");
		
		return "redirect:/member/complete";
	}
	
	@RequestMapping(value="address", method=RequestMethod.GET)
	public String addressForm(
			final RedirectAttributes reAttr,
			HttpSession session
			) throws Exception {
		
		int addrCount = 0;
		
		try {
			MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
			String userId = info.getUserId();
			
			addrCount = service.countMemberAddr(userId);
			
			if (addrCount >= 2) {
				String msg = info.getUserNickName()+ "님은 이미 2개의 주소를 등록하셨습니다.<br>"
					+ "새로 등록하려면 먼저 삭제를 진행해 주세요.";
				String goBack = "/mypage/main";
				
				reAttr.addFlashAttribute("title","주소 등록 불가");
				reAttr.addFlashAttribute("message",msg);
				reAttr.addFlashAttribute("goBack",goBack);
				
				return "redirect:/member/complete";
			}
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		
		return ".member.address";
	}
	
	@RequestMapping(value="address", method=RequestMethod.POST)
	public String addressSubmit(
			HttpSession session,
			MemberAddr dto,
			Model model,
			final RedirectAttributes reAttr
			) throws Exception {
		
		int addrCount = 0;
		Integer areaNum;
		
		try {
			MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
			String userId = info.getUserId();
			dto.setUserId(userId);
			
			addrCount = service.countMemberAddr(userId);
			
			if (addrCount >= 2) {
				String msg = info.getUserNickName()+ "님은 이미 2개의 주소를 등록하셨습니다.<br>"
					+ "새로 등록하려면 먼저 삭제를 진행해 주세요.";
				String goBack = "/mypage/main";
				
				reAttr.addFlashAttribute("title","주소 등록 불가");
				reAttr.addFlashAttribute("message",msg);
				reAttr.addFlashAttribute("goBack",goBack);
				
				return "redirect:/member/complete";
			}
			
			// area 테이블에서 해당 bcode에 해당하는 areaNum 없으면 새로 입력
			areaNum = service.readAreaByBcode(dto.getBcodeCut());
			if (areaNum == null) {
				areaNum = service.getAreaSeqNum();
				dto.setAreaNum(areaNum);
				
				service.insertArea(dto);
			} else {
				dto.setAreaNum(areaNum);
			}
			
			// 멤버주소 입력
			service.insertMemberAddr(dto);
			
		} catch (Exception e) {
			e.printStackTrace(); throw e;
		}
		
		return "redirect:/mypage/main";
	}
	
	@RequestMapping(value="deleteAddr", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAddr(
			@RequestParam Integer maNum
			) throws Exception {
		Map<String, Object> model = new HashMap<>();
		
		try {
			service.deleteMemberAddr(maNum);
		} catch (Exception e) {
			model.put("state", "false");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
	
	@RequestMapping(value="deleteBusiness", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBusiness(
			@RequestParam String userId,
			HttpSession session
			) throws Exception {
		Map<String, Object> model = new HashMap<>();
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "photo";
		
		try {
			service.deleteBusiness(userId, pathname);
		} catch (Exception e) {
			model.put("state", "false");
			return model;
		}
		
		model.put("state", "true");
		return model;
	}
}
