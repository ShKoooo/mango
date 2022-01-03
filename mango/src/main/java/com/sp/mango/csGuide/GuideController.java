package com.sp.mango.csGuide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("csGuide.guideController")
@RequestMapping("/csGuide/*")
public class GuideController {
	
	
	@RequestMapping(value = "main")
	public String main(Model model) throws Exception {
		
		return ".csGuide.main";
	}
}
