package com.sp.mango.home;

import java.util.List;
import java.util.Map;

public interface HomeService {
	public List<Home> productPopularList(); // 중고매물 인기글 리스트
	public List<Home> giftyPopularList(); // 기프티콘 인기글 리스트
	public List<Home> villagePopularList(); // 동네커뮤 인기글 리스트
}
