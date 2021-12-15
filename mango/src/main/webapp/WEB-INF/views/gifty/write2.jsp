<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>
<script type="text/javascript">
function sendOk() {
	var f = document.giftyForm;
	var str;
	
	str = f.gSubject.value.trim();
	if(!str) {
		alert("기프티콘 명을 입력하세요.");
		f.gSubject.focus();
		return;
	}
	
	str = f.gExpdate.value.trim();
	if(!str) {
		alert("기프티콘 만료일을 입력하세요.");
		f.gExpdate.focus();
		return;
	}
	
	str = f.gPrice.value.trim();
	if(!str) {
		alert("기프티콘 가격을 입력하세요.");
		f.gPrice.focus();
		return;
	}
	
	str = f.gContent.value.trim();
	if(!str) {
		alert("기프티콘 설명을 입력하세요.");
		f.gContent.focus();
		return;
	}
	
	str = f.gcNum.value;
	if(str == "0") {
		
	}
	
	
	
	f.action = "${pageContext.request.contextPath}/gifty/${mode}";
	f.submit();
	
	
}
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 기프티콘 판매 등록 </h3>
		</div>
		
		<div class="body-main">
		<form name="giftyForm" method="post" enctype="multipart/form-data">
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="gSubject">기프티콘 명</span>
			  <input type="text" class="form-control" name="gSubject" id="gSubject" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="gExpdate">기프티콘 유효기간</span>
			  <input type="text" class="form-control" name="gExpdate" id="gExpdate" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text bg-warning" id="gPrice">기프티콘 가격</span>
			  <input type="text" class="form-control" name="gPrice" id="gPrice" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="gStatus">기프티콘 현재상태</label>
			  <select class="form-select" name="gStatus" id="gStatus">
			    <option selected value="0">선택</option>
			    <option value="판매중">판매중</option>
			    <option value="판매완료">판매완료</option>
			    <option value="거래중">거래중</option>
			  </select>
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="gcNum">기프티콘 카테고리</label>
			  <select class="form-select" name="gcNum" id="gcNum">
			    <option selected value="0">선택</option>
			    <option value="1">카테고리</option>
			    <option value="2">가져와서</option>
			    <option value="3">뿌려줘야함</option>
			  </select>
			</div>
			<div class="input-group mb-3">
			  <label class="input-group-text bg-warning" for="selectFile">이미지 등록</label>
			  <input type="file" class="form-control" name="selectFile" id="selectFile">
			</div>
			<div class="input-group">
			  <span class="input-group-text bg-warning">기프티콘 설명</span>
			  <textarea class="form-control" name="gContent" id="gContent" aria-label="With textarea"></textarea>
			</div>
			<div class="text-center">
			<button type="button" class="btn btn-warning" onclick="sendOk();">${mode=="update"?"정보수정":"등록하기"}</button>
			</div>
		</form>
		</div>
	</div>
</div>