package com.sp.mango.greview;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.mango.member.MemberSessionInfo;

@Controller("greview.reviewController")
@RequestMapping("/greview/*")
public class GreviewController {
	
	
	@RequestMapping(value = "write", method =  RequestMethod.GET)
	public String writeForm() throws Exception {
		
		return ".gifty.grwrite";
	}	
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Greview dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		try {
			dto.setBuyerId(info.getUserId());
			dto.setBuyerId(null);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/gifty/list";
	}
	
}
