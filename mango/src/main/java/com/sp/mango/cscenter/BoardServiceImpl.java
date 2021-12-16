package com.sp.mango.cscenter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;

@Service("cscenter.boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private CommonDAO dao;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public List<Board> listBoard(Map<String, Object> map) {
		List<Board> list = null;
		
		try {
			
			list = dao.selectList("cscenter.listBoard", map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int dataCount() {
		int result = 0;
		
		try {
			result = dao.selectOne("cscenter.dataCount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Board readBoard(int num) {
		Board dto = null;
		
		try {
			dto = dao.selectOne("cscenter.readBoard", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public Board preReadBoard(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board nextReadBoard(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertBoard(Board dto, String pathname) throws Exception {
		try {
			
			String saveFilename = fileManager.doFileUpload(dto.getSelectFile(), pathname);
			if(saveFilename != null) {
				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(dto.getSelectFile().getOriginalFilename());
			}
			dao.insertData("cscenter.insertBoard", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updateBoard(Board dto, String pathname) throws Exception {
		try {
			
			String saveFilename = fileManager.doFileUpload(dto.getSelectFile(), pathname);
			if (saveFilename != null) {
				if (dto.getSaveFilename() != null && dto.getSaveFilename().length() != 0) {
					fileManager.doFileDelete(dto.getSaveFilename(), pathname);
				}

				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(dto.getSelectFile().getOriginalFilename());
			}
			
			dao.updateData("cscenter.updateBoard", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteBoard(int num, String pathname, String userId) throws Exception {
		try {
			Board dto = readBoard(num);
			if(dto == null) {
				return;
			}
			
			dao.deleteData("cscenter.deleteBoard", num);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}



	
}
