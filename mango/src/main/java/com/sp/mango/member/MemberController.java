package com.sp.mango.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
		} else if (uri.indexOf("mango/images/slide") >= 0) {	// 임시 처리
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
		
		System.out.println("::::" + chkWay + " : "+ userParam);
		
		int result = service.countMemberByParam(map);
		
		if (result > 0) p = "false";
		
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("passed",p);
		
		return model;
	}
}
