package com.sp.mango.gifty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String writeForm() throws Exception {
		
		return ".gifty.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit() throws Exception {
		
		return "redirect:/gifty/list";
	}
	
	@RequestMapping(value = "article")
	public String article() throws Exception {
		
		return ".gifty.article";
	}
}
