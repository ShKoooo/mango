package com.sp.mango.product;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.mango.common.FileManager;
import com.sp.mango.member.MemberSessionInfo;

@Controller("product.productController")
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value = "list")
	public String list() throws Exception {
		
		return ".product.list";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String writeForm(Model model, HttpSession session) throws Exception {
		List<Product> listCategory = service.listCategory();

		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		List<MemberAddr> listMemberAddr = service.listMemberAddr(info.getUserId());
		
		
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("listMemberAddr", listMemberAddr);
		model.addAttribute("mode", "write");
		
		return ".product.write";
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String writeSubmit(Product dto, HttpSession session) throws Exception {
		String root = session.getServletContext().getRealPath("/");
		String path = root + "uploads" + File.separator + "product";
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		try {
			dto.setUserId(info.getUserId());

						
//			// 유저의 지역(동네)정보 가져오기
//			Product readAreaNum = service.readMemberArea(info.getUserId());
//			dto.setAreaNum(readAreaNum.getAreaNum());
			
//			위도 경도 어떻게 넣지.
//			dto.setpLat(readAreaNum.getpLat());
//			dto.setpLon(readAreaNum.getpLon());
			
			service.insertProduct(dto, path);
		} catch (Exception e) {
		}
		
		
		return "redirect:/product/list";
	}
	
	
	
	public String article() throws Exception {
		
		return ".product.article";
	}
}
