package com.sp.mango.village.qna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.village.MemberAddr;
import com.sp.mango.village.ReplyReport;
import com.sp.mango.village.VillageReport;

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

	// 비회원 혹은 1개 주소 등록 회원의 전체 글보기 리스트
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
	
	// 주소 2개 등록한 회원의 반경 5km 게시글 리스트
	@Override
	public List<VillageQna> memberListBoard(Map<String, Object> map) {
		List<VillageQna> memberList = null;
		
		try {
			memberList = dao.selectList("qna.memberListBoard", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberList;
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
	public int memberDataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.memberDataCount", map);
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
	public void updateBoard(VillageQna dto) throws Exception {
		try {
			dao.updateData("qna.updateBoard", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBoard(int vNum, String userId, int membership) throws Exception {
		try {
			VillageQna dto = readBoard(vNum);
			if(dto == null || (membership < 51 && ! dto.getUserId().equals(userId))) {
				return;
			}
			
			dao.deleteData("qna.deleteBoard", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("qna.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMemberAddr;
	}
	
	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	@Override
	public void insertBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("qna.insertBoardLike",map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteBoardLike(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("qna.deleteBoardLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	@Override
	public int boardLikeCount(int vNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.boardLikeCount", vNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean userBoardLiked(Map<String, Object> map) {
		boolean result = false;
		try {
			VillageQna dto = dao.selectOne("qna.userBoardLiked", map);
			if(dto != null) {
				result = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertReply(Reply dto) throws Exception {
		try {
			dao.insertData("qna.insertReply", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Reply> listReply(Map<String, Object> map) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("qna.listReply", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.replyCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("qna.deleteReply", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Reply> listReplyAnswer(int vrAnswer) {
		List<Reply> list = null;
		
		try {
			list = dao.selectList("qna.listReplyAnswer", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int replyAnswerCount(int vrAnswer) {
		int result = 0;
		
		try {
			result = dao.selectOne("qna.replyAnswerCount", vrAnswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void insertReplyLike(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("qna.insertReplyLike", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Map<String, Object> replyLikeCount(Map<String, Object> map) {
		Map<String, Object> countMap = null;
		
		try {
			countMap = dao.selectOne("qna.replyLikeCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countMap;
	}

	@Override
	public List<VillageReport> listVreport() {
		List<VillageReport> listVreport = null;
		
		try {
			listVreport = dao.selectList("qna.listVreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVreport;
	}

	@Override
	public void insertVreport(VillageReport dto) throws Exception {
		try {
			dao.insertData("qna.insertVreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<ReplyReport> listVRreport() {
		List<ReplyReport> listVRreport = null;
		
		try {
			listVRreport = dao.selectList("qna.listVRreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listVRreport;
	}

	@Override
	public void insertVRreport(ReplyReport dto) throws Exception {
		try {
			dao.insertData("qna.insertVRreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	





}
