package com.sp.mango.inquiry;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;

@Controller("inquiry.inquiryController")
@RequestMapping("/inquiry/*")
public class InquiryController {
	
	@Autowired
	private InquiryService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value = "personalSend", method = RequestMethod.POST)
	public String personalSendSubmit(
			Inquiry dto,
			HttpSession session
			) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "inquiry";
		
		
		try {
			dto.setUserId(info.getUserId());
			
			service.insertPersonalSend(dto, pathname);// 여기 바꿔야함
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/cscenter/list";
	}

}
