package com.sp.mango.mypage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sp.mango.member.Business;
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
		
		model.addAttribute("userId",userId);
		model.addAttribute("userNickName",userNickName);
		model.addAttribute("mannerDto",mannerDto);
		model.addAttribute("addrList",addrList);
		model.addAttribute("hasBusiness",hasBusiness);		// 비즈니스 보유중인지 체크 : true/false : 없으면 신규등록버튼, 있으면 수정버튼, 주소처럼 리스트 형태로 표시..
		model.addAttribute("businessDto",businessDto);
		
		
		return ".mypage.main";
	}
}
