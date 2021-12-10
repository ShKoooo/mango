package com.sp.mango.village;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("village.VillageController")
@RequestMapping("/village/*")
public class VillageController {

	@RequestMapping(value="list")
	public String list() throws Exception {
		
		return ".village.list";
	}
}
