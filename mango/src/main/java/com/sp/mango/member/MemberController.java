package com.sp.mango.member;

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

@Controller("member.memberController")
@RequestMapping(value="/member/*")
public class MemberController {
	@Autowired
	private MemberService service;
	
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
			) {
		
		Member dto = service.loginMember(userId);
		if (dto == null || !userPwd.contentEquals(dto.getUserPwd())) {
			model.addAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
			return ".member.login";
		}
		
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
			Model model
			) {
		
		try {
			dto.setUserTel(dto.getTel1(), dto.getTel2(), dto.getTel3());
			dto.setUserEmail(dto.getEmail1(),dto.getEmail2());
			
			service.insertMember(dto);
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
	
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String updateForm(
			Model model,
			HttpSession session
			) throws Exception {
		
		String nickChangeable = "false";
		
		MemberSessionInfo memberInfo = (MemberSessionInfo) session.getAttribute("member");
		if (memberInfo == null) {
			return "redirect:/member/login";
		}
		
		String userId = memberInfo.getUserId();
		Member dto = service.readMember(userId);
		
		if (dto == null) {
			return "redirect:/";
		}
		
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
		
		model.addAttribute("mode","update");
		model.addAttribute("dto",dto);
		model.addAttribute("nickChangeable",nickChangeable);
		
		return ".member.member";
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String updateSubmit(
			Member dto,
			final RedirectAttributes reAttr,
			Model model
			) throws Exception {
		
		try {
			dto.setUserTel(dto.getTel1(), dto.getTel2(), dto.getTel3());
			dto.setUserEmail(dto.getEmail1(),dto.getEmail2());
			
			service.updateMember(dto);
		} catch (Exception e) {
			model.addAttribute("message", dto.getUserNickName()+"님의 정보수정이 실패했습니다.");
			// model.addAttribute("mode","update");
			// return ".member.member";
			
			return "redirect:/member/complete";
		}
		
		String msg =  dto.getUserNickName()+"님의 정보수정이 정상적으로 처리되었습니다.<br>";
		
		reAttr.addFlashAttribute("message",msg);
		reAttr.addFlashAttribute("title","회원 정보 수정");
		
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
	public Map<String,Object> duplCheck(
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
}
