package com.sp.mango.mypage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sp.mango.member.MemberAddr;
import com.sp.mango.member.MemberSessionInfo;

@Controller("mypage.mypageController")
@RequestMapping(value="/mypage/*")
public class MypageController {
	@Autowired
	private MypageService service;
	
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
		
		MannerProfile mannerDto = service.readMannerProfile(userId);
		if (mannerDto == null) {
			mannerDto = new MannerProfile();
			mannerDto.setMannerStar(0);
			mannerDto.setProductStar(0);
			mannerDto.setMinusDeg(0);
		}
		mannerDto.setMannerDeg(mannerDto.getMannerStar(), mannerDto.getProductStar(), mannerDto.getMinusDeg());
		
		List<MemberAddr> addrList = service.listMemberAddr(userId);
		
		model.addAttribute("userId",userId);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("mannerDto",mannerDto);
		model.addAttribute("addrList",addrList);
		
		return ".mypage.main";
	}
}
