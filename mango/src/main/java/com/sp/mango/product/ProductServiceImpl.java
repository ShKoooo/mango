package com.sp.mango.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.dao.CommonDAO;

@Service("product.productServiceImpl")
public class ProductServiceImpl implements ProductService{
	@Autowired
	private CommonDAO dao;
//	@Autowired
//	private FileManager fileManager;
	
	@Override
	public void insertProduct(Product dto, String pathname) throws Exception {
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
	public void updateProduct(Product dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product dto, String pathname) throws Exception {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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
	public int dataCount() {
		int result = 0;
		
		try {
			result = dao.selectOne("product.dataCount");
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
	public boolean userProductLiked(Map<String, Object> map) {
		boolean result = false;
		
		try {
			Product dto = dao.selectOne("product.userProductLiked", map);
			if(dto != null) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}



}
