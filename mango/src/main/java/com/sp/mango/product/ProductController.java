package com.sp.mango.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("product.productController")
@RequestMapping("/product/*")
public class ProductController {
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return ".product.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String write(Model model) throws Exception {
		model.addAttribute("mode", "write");
		
		return ".product.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit() throws Exception {
		
		return "redirect:/product/list";
	}
	
	
	
	public String article() throws Exception {
		
		return ".product.article";
	}
}
