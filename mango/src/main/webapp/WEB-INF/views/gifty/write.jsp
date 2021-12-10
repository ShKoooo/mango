<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 기프티콘 판매 등록 </h3>
		</div>
		
		<div class="body-main">
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="inputGroup-sizing-default">기프티콘 명</span>
			  <input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="inputGroup-sizing-default">기프티콘 유효기간</span>
			  <input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="inputGroup-sizing-default">기프티콘 가격</span>
			  <input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="inputGroupSelect01">기프티콘 현재상태</label>
			  <select class="form-select" id="inputGroupSelect01">
			    <option selected>선택</option>
			    <option value="1">판매중</option>
			    <option value="2">판매완료</option>
			    <option value="3">거래중</option>
			  </select>
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="inputGroupSelect01">기프티콘 카테고리</label>
			  <select class="form-select" id="inputGroupSelect01">
			    <option selected>선택</option>
			    <option value="1">카테고리</option>
			    <option value="2">가져와서</option>
			    <option value="3">뿌려줘야함</option>
			  </select>
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="inputGroupFile01">이미지 등록</label>
			  <input type="file" class="form-control" id="inputGroupFile01">
			</div>
			<div class="input-group">
			  <span class="input-group-text bg-warning">기프티콘 설명</span>
			  <textarea class="form-control" aria-label="With textarea"></textarea>
			</div>
			<button type="button" class="btn btn-warning">등록하기</button>
		</div>
	</div>
</div>