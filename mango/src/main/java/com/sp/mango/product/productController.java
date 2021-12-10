package com.sp.mango.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("product.productController")
@RequestMapping("/product/*")
public class productController {
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return ".product.list";
	}
}
