package com.sp.mango.village.qna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("village.qna.villageQnaService")
public class VillageQnaServiceImpl implements VillageQnaService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertBoard(VillageQna dto, String pathname) throws Exception {
		
		try {
			dao.insertData("qna.insertBoard", dto);
			
			/* 이미지파일 업로드
			if(!dto.getSelectFile().isEmpty()) {
				for(MultipartFile mf : dto.getSelectFile()) {
					String vimageSaveFilename = FileManager.doFileUpload(mf, pathname);
					if (vimageSaveFilename == null) {
						continue;
					}
					
					dto.setVimageSaveFilename(vimageSaveFilename);
					dto.setVimageOrigFilename(mf.getOriginalFilename());
					
					insertImg(dto);
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<VillageQna> listBoard(Map<String, Object> map) {
		List<VillageQna> list = null;
		
		try {
			list = dao.selectList("qna.listBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public VillageQna readBoard(int vNum) {
		VillageQna dto = null;
		
		try {
			dto = dao.selectOne("qna.readBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void updateHitCount(int vNum) throws Exception {
		try {
			dao.updateData("qna.updateHitCount", vNum);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public VillageQna preReadBoard(Map<String, Object> map) {
		VillageQna dto = null;
		
		try {
			dto = dao.selectOne("qna.preReadBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public VillageQna nextReadBoard(Map<String, Object> map) {
		VillageQna dto = null;
		
		try {
			dto = dao.selectOne("qna.nextReadBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public void updateBoard(VillageQna dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBoard(VillageQna dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int boardLikeCount(int vNum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insertReply(Reply dto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int replyCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reply> listReplyAnswer(int answer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int replyAnswerCount(int answer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
