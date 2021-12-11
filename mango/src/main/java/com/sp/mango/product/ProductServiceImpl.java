package com.sp.mango.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
	public List<Product> listImgFile(int pImgNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
