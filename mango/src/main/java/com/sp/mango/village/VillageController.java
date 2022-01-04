package com.sp.mango.village;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.mango.common.MyUtil;
import com.sp.mango.member.MemberSessionInfo;
import com.sp.mango.village.ad.VillageAd;
import com.sp.mango.village.ad.VillageAdService;
import com.sp.mango.village.eat.VillageEat;
import com.sp.mango.village.eat.VillageEatService;
import com.sp.mango.village.forone.VillageForone;
import com.sp.mango.village.forone.VillageForoneService;
import com.sp.mango.village.help.VillageHelp;
import com.sp.mango.village.help.VillageHelpService;
import com.sp.mango.village.lost.VillageLost;
import com.sp.mango.village.lost.VillageLostService;
import com.sp.mango.village.news.VillageNews;
import com.sp.mango.village.news.VillageNewsService;
import com.sp.mango.village.qna.VillageQna;
import com.sp.mango.village.qna.VillageQnaService;
import com.sp.mango.village.with.VillageWith;
import com.sp.mango.village.with.VillageWithService;

@Controller("village.VillageController")
@RequestMapping("/village/*")
public class VillageController {
	@Autowired
	private VillageQnaService qnaService;
	@Autowired
	private VillageEatService eatSrevice;
	@Autowired
	private VillageHelpService helpService;
	@Autowired
	private VillageWithService withService;
	@Autowired
	private VillageNewsService newsService;
	@Autowired
	private VillageLostService lostService;
	@Autowired
	private VillageAdService adService;
	@Autowired
	private VillageForoneService foroneService;
	
	@Autowired
	private MyUtil myUtil;
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Model model, HttpSession session) throws Exception {
		
		MemberSessionInfo info = (MemberSessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String userId = info.getUserId();
		
		Map<String, Object> map = new HashMap<>();
		map.put("start", 1);
		map.put("end",  10);
		map.put("userId", userId);
		
		
		
		List<VillageQna> listQna = qnaService.memberListBoard(map);
		List<VillageEat> listEat = eatSrevice.memberListBoard(map);
		List<VillageHelp> listHelp = helpService.memberListBoard(map);
		List<VillageWith> listWith = withService.memberListBoard(map);
		List<VillageNews> listNews = newsService.memberListBoard(map);
		

		model.addAttribute("listQna", listQna);
		model.addAttribute("listEat", listEat);
		model.addAttribute("listHelp", listHelp);
		model.addAttribute("listWith", listWith);
		model.addAttribute("listNews", listNews);
		
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("start", 1);
		map2.put("end", 3);
		map2.put("userId", userId);
		
		List<VillageLost> listLost = lostService.memberListBoard(map2);
		List<VillageAd> listAd = adService.memberListBoard(map2);
		List<VillageForone> listForone = foroneService.memberListBoard(map2);
		
		for(VillageLost lost : listLost) {
			List<String> lostImgs = myUtil.getImgSrc(lost.getContent());
			
			if(lostImgs != null && lostImgs.size() > 0) {
				lost.setThumbnail(lostImgs.get(0));
			} else {
				lost.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		for(VillageAd ad : listAd) {
			List<String> adImgs = myUtil.getImgSrc(ad.getContent());
			
			if(adImgs != null && adImgs.size() > 0) {
				ad.setThumbnail(adImgs.get(0));
			} else {
				ad.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		for(VillageForone ad : listForone) {
			List<String> foroneImgs = myUtil.getImgSrc(ad.getContent());
			
			if(foroneImgs != null && foroneImgs.size() > 0) {
				ad.setThumbnail(foroneImgs.get(0));
			} else {
				ad.setThumbnail(cp + "/resources/images/nothumb.png");
			}
		}
		
		
		
		model.addAttribute("listLost", listLost);
		model.addAttribute("listAd", listAd);
		model.addAttribute("listForone", listForone);
		
		return ".village.list";
	}

}
