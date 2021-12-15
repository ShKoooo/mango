package com.sp.mango.gifty;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.mango.member.MemberSessionInfo;



@Controller("gifty.giftyController")
@RequestMapping("/gifty/*")
public class GiftyController {
	@Autowired
	private GiftyService service;
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return ".gifty.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model) throws Exception {
		List<Gifty> listGcategory = service.listGcategory();
		
		model.addAttribute("listGcategory", listGcategory);
		model.addAttribute("mode", "write");
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Gifty dto, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo) session.getAttribute("member");
		
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + "uploads" + File.separator + "giftycon";
		
		try {
			
			dto.setUserId(info.getUserId());
			service.insertGifty(dto, pathname);
			
		} catch (Exception e) {
		}
		
		return "redirect:/gifty/list";
	}
	
	@RequestMapping(value = "article")
	public String article() throws Exception {
		
		return ".gifty.article";
	}
}
