package com.sp.mango.cscenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("cscenter.cscenterController")
@RequestMapping("/cscenter/*")
public class CscenterController {
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		
		return ".cscenter.list";
	}

}
