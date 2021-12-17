package com.sp.mango.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.mango.common.FileManager;
import com.sp.mango.common.dao.CommonDAO;


@Service("inquiry.inquiryService")
public class InquiryServiceImpl implements InquiryService {
	
	@Autowired
	private CommonDAO dao;
	
	@Autowired
	private FileManager fileManager;

	@Override
	public void insertPersonalSend(Inquiry dto, String pathname) throws Exception {
		try {
			
			String saveFilename = fileManager.doFileUpload(dto.getSelectFile(), pathname);
			if(saveFilename != null) {
				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(dto.getSelectFile().getOriginalFilename());
			}
			dao.insertData("inquiry.insertPersonalSend", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
		

}
