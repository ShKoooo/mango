package com.sp.mango.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sp.mango.product.ProductService;

@Controller("home.homeController")
@RequestMapping("/home/*")
public class HomeController {
	@Autowired
	private ProductService service;
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return "/";
	}
}
