package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.mypage.Note;


@Service("gifty.giftyService")
public class GiftyServiceImpl implements GiftyService {

	@Autowired
	private CommonDAO dao;
	
	
	
	@Override
	public void insertGifty(Gifty dto) throws Exception {
		try {
			
			int giftyconSeq = dao.selectOne("gifty.giftyconSeq");
			dto.setgNum(giftyconSeq);
			
			dao.insertData("gifty.insertGifty", dto);
			
			// dao.insertData("gifty.insertGiftyImg", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Gifty> listGifty(Map<String, Object> map) {
		List<Gifty> list = null;
		
		try {
			list = dao.selectList("gifty.listGifty", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("gifty.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Gifty readGifty(int gnum) {
		Gifty dto = null;
		
		try {
			dto = dao.selectOne("gifty.readGifty", gnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateHitCount(int gnum) throws Exception {
		try {
			dao.updateData("gifty.updateHitCount", gnum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateGifty(Gifty dto) throws Exception {
		try {
			dao.updateData("gifty.updateGifty", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteGifty(int gnum, String userId) throws Exception {
		try {
			Gifty dto = readGifty(gnum);
			if(dto==null)
				return;
			
			dao.deleteData("gifty.deleteGifty", gnum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Gifty> listGcategory() {
		List<Gifty> listGcategory = null;
		
		try {
			listGcategory = dao.selectList("gifty.listGcategory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listGcategory;
	}

	@Override
	public void insertGwish(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("gifty.insertGwish", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Map<String, Object> listGwish(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int gwishCount(int gNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("gifty.gwishCount", gNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int giftyWishCount(int gNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("gifty.giftyWishCount", gNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteGwish(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("gifty.deleteGwish", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean userGwished(Map<String, Object> map) {
		boolean result = false;
		try {
			Gifty dto = dao.selectOne("gifty.userGwished", map);
			if(dto != null) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void updateSdate(int gnum, String userId) throws Exception {
		try {
			Gifty dto = readGifty(gnum);
			if(dto == null || (! dto.getUserId().equals(userId))) {
				return;
			}
			
			
			dao.updateData("gifty.updateSdate", gnum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<GiftyReport> listGreport() {
		// 기프티 신고리스트
		List<GiftyReport> listGreport = null;
		
		try {
			listGreport = dao.selectList("gifty.listGreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listGreport;
	}

	@Override
	public void insertGreport(GiftyReport dto) throws Exception {
		// 기프티 신고 인서트
		try {
			dao.insertData("gifty.insertGreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateDate(int gnum, String userId) throws Exception {
		// 끌어올리기 기능
		
		try {
			Gifty dto = readGifty(gnum);
			if(dto == null || (! dto.getUserId().equals(userId))) {
				return;
			}
			
			dao.updateData("gifty.updateDate", gnum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public String gUpOkDate(int gnum) {
		String result = "";
		
		try {
			result = dao.selectOne("gifty.gUpOkDate", gnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void sendMsg(Note dto) throws Exception {
		try {
			dao.insertData("gifty.sendMsg", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void getUserId(int gnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Gifty> listPop(Map<String, Object> map) {
		List<Gifty> list = null;
		
		try {
			list = dao.selectList("gifty.listPop", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String userImgSaveFileName(int gNum) throws Exception {
		String userImgSaveFileName = "";
		
		try {
			//userImgSaveFileName = dao.selectOne("gifty.userPic", gNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return userImgSaveFileName;
	}

	@Override
	public void insertMyAccount(Gifty dto) throws Exception {
		try {
			dao.insertData("gifty.insertMyAccount1", dto);
			dao.insertData("gifty.insertMyAccount2", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Gifty> listTargetId(Map<String, Object> map) {
		List<Gifty> list = null;
		
		try {
			list = dao.selectList("gifty.listTargetId", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 리뷰 작성 입력
	@Override
	public void insertGreview(Greview dto) throws Exception {
		try {
			dao.insertData("gifty.insertGreview", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateGstatus(int gNum) throws Exception {
		try {
			dao.updateData("gifty.updateGstatus", gNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void insertBook(Gifty dto) throws Exception {
		try {
			dao.insertData("gifty.insertBook", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Gifty> selectBook(int gNum) {
		List<Gifty> bookList = null;
		
		try {
			bookList = dao.selectList("gifty.selectBook", gNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}

}
