package com.sp.mango.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;
import com.sp.mango.mypage.Note;

@Service("product.productService")
public class ProductServiceImpl implements ProductService{
	@Autowired
	private CommonDAO dao;
//	@Autowired
//	private FileManager fileManager;
	
	@Override
	public void insertProduct(Product dto) throws Exception {
		try {
			// 글 업로드
			int seq = dao.selectOne("product.seq");
			dto.setpNum(seq);
			
			dao.insertData("product.insertProduct", dto);
			
//			// 이미지 파일 업로드
//			if(! dto.getSelectFile().isEmpty()) {
//				for(MultipartFile mf : dto.getSelectFile()) {
//					String saveFileName = fileManager.doFileUpload(mf, pathname);
//					if(saveFileName == null) {
//						continue;
//					}
//					
//					dto.setpImgSaveFileName(saveFileName);
//					
//					insertImgFile(dto);
//				}
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateProduct(Product dto) throws Exception {
		// 게시글 수정
		try {
			dao.updateData("product.updateProduct", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteProduct(int pNum) throws Exception {
		// 게시글 삭제
		try {
			dao.deleteData("product.deleteProduct", pNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Product> listProduct(Map<String, Object> map) {
		List<Product> list = null;
		
		try {
			list = dao.selectList("product.listProduct", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Product readProduct(int pNum) {
		Product dto = null;
		
		// 게시물 가져오기
		try {
			dto = dao.selectOne("product.readProduct", pNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public void insertImgFile(Product dto) throws Exception {
		try {
			dao.insertData("product.insertImgFile", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Product readImgFile(int pImgNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImgFile(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> listImgFile(int pNum) {
		List<Product> listImgFile = null;
		
		try {
			listImgFile = dao.selectList("product.listImgFile", pNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listImgFile;
	}

	@Override
	public List<Product> listCategory() {
		List<Product> listCategory = null;
		
		try {
			listCategory = dao.selectList("product.listCategory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listCategory;
	}

	@Override
	public List<MemberAddr> listMemberAddr(String userId) {
		List<MemberAddr> listMemberAddr = null;
		
		try {
			listMemberAddr = dao.selectList("product.listMemberAddr", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listMemberAddr;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.selectOne("product.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int memAddrCount(String userId) {
		int result = 0;
		
		try {
			result = dao.selectOne("product.memAddrCount", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Product> memberListProduct(Map<String, Object> map) {
		List<Product> memberList = null;
		
		try {
			memberList = dao.selectList("product.memberListProduct", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberList;
	}
	
	@Override
	public List<Product> popularList(Map<String, Object> map) {
		List<Product> popularList = null;
		
		try {
			popularList = dao.selectList("product.popularList", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return popularList;
	}

	@Override
	public void updateHitCount(int pNum) throws Exception {
		// 조회수 증가
		try {
			dao.updateData("product.updateHitCount", pNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public boolean userProductWished(Map<String, Object> map) {
		boolean result = false;
		
		try {
			Product dto = dao.selectOne("product.userProductWished", map);
			if(dto != null) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void insertProductWish(Map<String, Object> map) throws Exception {
		try {
			dao.insertData("product.insertProductWish", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteProductWish(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("product.deleteProductWish", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}

	@Override
	public int productWishCount(int pNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("product.productWishCount", pNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int pWishCount(int pNum) {
		int result = 0;
		
		try {
			result = dao.selectOne("product.pWishCount", pNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void updateSoldDate(Product dto) throws Exception {
		try {
			dao.updateData("product.updateSoldDate", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<ProductReport> listPreport() {
		// 신고항목 리스트
		List<ProductReport> listPreport = null;
		
		try {
			listPreport = dao.selectList("product.listPreport");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listPreport;
	}

	@Override
	public void insertPreport(ProductReport dto) throws Exception {
		// 신고 등록
		try {
			dao.insertData("product.insertPreport", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateDate(int pNum, String userId) throws Exception {
		// 끌어올리기 기능
		try {
			Product dto = readProduct(pNum);
			if(dto == null || (! dto.getUserId().equals(userId))) {
				return;
			}	
					
			dao.updateData("product.updateDate", pNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String pUpOkDate(int pNum) {
		String result = "";
		
		try {
			result = dao.selectOne("product.pUpOkDate", pNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void sendMsg(Note notedto) throws Exception {
		try {
			dao.insertData("product.sendMsg", notedto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String userImgSaveFileName(int pNum) {
		
		String userImgSaveFileName = "";
		// List <String>
		
		try {
			// userImgSaveFileName = dao.selectOne("product.userProfile", pNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userImgSaveFileName;
	}
	
	@Override
	public void insertPreview(Preview dto) throws Exception {
		try {
			dao.insertData("product.insertPreview", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Product> receiveNoteList(String userId) {
		// 받은 쪽지 리스트
		List<Product> receiveNoteList = null;
		
		try {
			receiveNoteList = dao.selectList("product.receiveNoteList", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return receiveNoteList;
	}

	@Override
	public void insertMyAccount(Product dto) throws Exception {
		try {
			dao.insertData("product.insertMyAccount1", dto);
			dao.insertData("product.insertMyAccount2", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void updatePstatus(int pNum) throws Exception {
		// 거래완료로 변경
		try {
			dao.updateData("product.updatePstatus", pNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void insertBook(Product dto) throws Exception {
		// 예약중
		try {
			dao.insertData("product.insertBook", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Product> selectBook(int pNum) {
		// 예약내역
		List<Product> selectBook = null;
		
		try {
			selectBook = dao.selectList("product.selectBook", pNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectBook;
	}


	
	
}
