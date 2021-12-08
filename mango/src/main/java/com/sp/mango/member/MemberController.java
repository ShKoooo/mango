package com.sp.mango.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		String uri = "redirect:/";
		
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
	public String memberForm () {
		return "";
	}
	
	@RequestMapping(value = "member", method = RequestMethod.POST)
	public String memberSubmit () {
		return "redirect:/";
	}
}
