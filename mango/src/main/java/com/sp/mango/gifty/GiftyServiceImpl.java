package com.sp.mango.gifty;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;


@Service("gifty.giftyService")
public class GiftyServiceImpl implements GiftyService {

	@Autowired
	private CommonDAO dao;
	
	@Autowired
	private FileManager fileManager;
	
	@Override
	public void insertGifty(Gifty dto, String pathname) throws Exception {
		try {
			String saveFilename = fileManager.doFileUpload(dto.getSelectFile(), pathname);
			if(saveFilename != null) {
				dto.setgImgSaveFileName(saveFilename);
			}
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Gifty readGifty(int gnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateHitCount(int gnum) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updategifty(Gifty dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletegifty(int gnum, String pahtname, String userId) throws Exception {
		// TODO Auto-generated method stub
		
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

}
