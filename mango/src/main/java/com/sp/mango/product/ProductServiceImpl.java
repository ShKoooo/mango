package com.sp.mango.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;

@Service("product.productServiceImpl")
public class ProductServiceImpl implements ProductService{
	@Autowired
	private CommonDAO dao;
	@Autowired
	private FileManager fileManager;
	
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
		// TODO Auto-generated method stub
		return null;
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

}
