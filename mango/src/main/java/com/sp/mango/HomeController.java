package com.sp.mango;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.mango.common.MyUtil;
import com.sp.mango.home.Home;
import com.sp.mango.home.HomeService;

@Controller
public class HomeController {
	@Autowired
	private HomeService service;
	
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(value = "pcNum", defaultValue = "0") int pcNum,
			@RequestParam(value = "group", defaultValue = "0") int group,
			HttpSession session,
			HttpServletRequest req,
			Locale locale, Model model) {
		
		String cp = req.getContextPath();
		
		List<Home> productPopList = service.productPopularList();
		List<Home> giftyPopList = service.giftyPopularList();
		List<Home> villagePopList = service.villagePopularList();
		
		// 상품 썸내일 사진
	    for(Home dto : productPopList) {
	        List<String> imgs = myUtil.getImgSrc(dto.getpContent());
	        if(imgs != null && imgs.size() > 0) {
	           dto.setpImgSaveFileName(imgs.get(0));
	        } else {
	           dto.setpImgSaveFileName(cp+"/resources/images/noimage.png");
	        }
	    }
	    
	    // 깊티 썸내일 사진
	    for(Home dto : giftyPopList) {
			List<String> imgs = myUtil.getImgSrc(dto.getgContent());
			if(imgs != null && imgs.size() > 0) {
				dto.setgImgSaveFileName(imgs.get(0));
			} else {
				dto.setgImgSaveFileName(cp+"/resources/images/noimage.png");
			}
		}
	    
	    // 동네 커뮤 썸내일 사진
	    for(Home dto : villagePopList) {
			List<String> imgs = myUtil.getImgSrc(dto.getContent());
			if(imgs != null && imgs.size() > 0) {
				dto.setThumbnail(imgs.get(0));
			} else {
				dto.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		String pArticleUrl = cp + "/product/article?pcNum="+pcNum+"&page="+current_page;
		String gArticleUrl = cp +"/gifty/article?group="+group+"&page=" + current_page;
		String vArticleUrl = cp + "/village/ad/article?page="+current_page;
		
		model.addAttribute("productPopList", productPopList);
		model.addAttribute("giftyPopList", giftyPopList);
		model.addAttribute("villagePopList", villagePopList);
		
		model.addAttribute("pArticleUrl", pArticleUrl);
		model.addAttribute("gArticleUrl", gArticleUrl);
		model.addAttribute("vArticleUrl", vArticleUrl);
		
		return ".home";
	}
}
