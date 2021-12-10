﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<script type="text/javascript">
function memberOk() {
	var f = document.memberForm;
	var mode = "${mode}";
	var id = f.userId.value;
	var nick = f.userNickName.value;
	var pwd = f.userPwd.value;
	var pwd2 = f.userPwd2.value;
	var name = f.userName.value;
	var birth = f.birth.value;
	
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(id) ) { 
		alert("아이디를 다시 입력 하세요. ");
		f.userId.focus();
		return;
	}
	
	if(mode === "member" && f.userIdValid.value === "false") {
		str = "아이디 중복 검사가 실행되지 않았습니다.";
		$("#userId").parent().find(".help-block1").html(str);
		f.userId.focus();
		return;
	}
}

// 아이디 중복 검사
function userIdCheck() {
	var userId=$("#userId").val();

	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(userId)) { 
		var str = "아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.";
		$("#userId").focus();
		$(".userId-box").find(".help-block1").html(str);
		return;
	}
	
	var url = "${pageContext.request.contextPath}/member/userDuplCheck";
	var query = "userParam=" + userId;
	query += "&chkWay=userId";
	
	console.log(url);
	console.log(query);
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			var passed = data.passed;

			if(passed === "true") {
				var str = "<span style='color:blue; font-weight: bold;'>" + userId + "</span> 아이디는 사용가능 합니다.";
				$(".userId-box").find(".help-block1").html(str);
				$("#userIdValid").val("true");
			} else {
				var str = "<span style='color:red; font-weight: bold;'>" + userId + "</span> 아이디는 사용할수 없습니다.";
				$(".userId-box").find(".help-block1").html(str);
				$("#userId").val("");
				$("#userIdValid").val("false");
				$("#userId").focus();
			}
		}
	});
}

// 닉네임 중복검사
function userNickCheck() {
	var userNick=$("#userNickName").val();
	
	var url = "${pageContext.request.contextPath}/member/userDuplCheck";
	var query = "userParam=" + userNick;
	query += "&chkWay=userNickName";
}


</script>

<div class="container">
	<div class="body-container">
		<div class="body-title">
			<h3>
				<c:if test="${mode=='member'}">
					<i class="icofont-users"></i> 회원가입				
				</c:if>
				<c:if test="${mode=='update'}">
					<i class="icofont-refresh"></i> 정보수정					
				</c:if>
			</h3>
		</div>
		
		<div class="alert alert-info" role="alert">
	        <i class="bi bi-person-check-fill"></i> 회원가입? 망설이지 말고 GO!
	    </div>
	    
	    <div class="body-main">
	    	<form name="memberForm" method="post">
	    		<div class="row mb-3">
	    			<label class="col-sm-2 col-form-label" for="userId">아이디</label>
	    			<div class="col-sm-10 userId-box">
	    				<div class="row">
	    					<div class="col-5 pe-1">
								<input type="text" name="userId" id="userId" class="form-control" value="${dto.userId}" 
										${mode=="update" ? "readonly='readonly' ":""}
										placeholder="아이디">
							</div>
							<div class="col-3 ps-1">
								<c:if test="${mode=='member'}">
									<button type="button" class="btn btn-light" onclick="userIdCheck();">아이디 중복 확인</button>
								</c:if>
							</div>
	    				</div>
						<c:if test="${mode=='member'}">
							<small class="form-control-plaintext help-block1">아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.</small>
						</c:if>
	    			</div>
	    		</div>
	    		
	    		<div class="row mb-3">
	    			<label class="col-sm-2 col-form-label" for="userNickName">닉네임</label> <!-- 나이 및 닉 변경기간 체크는 memberOk()에서.. -->
	    			<div class="col-sm-10 userId-box">
	    				<div class="row">
	    					<div class="col-5 pe-1">
    							<input type="text" name="userNickName" id="userNickName" class="form-control" value="${dto.userNickName}" placeholder="닉네임">
							</div>
							<div class="col-3 ps-1">
								<button type="button" class="btn btn-light" onclick="userNickCheck();">닉네임 중복 확인</button>
							</div>
	    				</div>
						<small class="form-control-plaintext help-block2">욕설을 사용하거나 기타 약관에 위배되는 닉네임은 운영자에 의해 임의 변경될 수 있음을 고지해 드립니다.</small>
	    			</div>
	    		</div>
	    		
	    		<div class="row mb-3">
					<label class="col-sm-2 col-form-label" for="userPwd">패스워드</label>
					<div class="col-sm-10">
			            <input type="password" name="userPwd" id="userPwd" class="form-control" autocomplete="off" placeholder="패스워드">
			            <small class="form-control-plaintext">패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</small>
			        </div>
			    </div>
			    
			    <div class="row mb-3">
			        <label class="col-sm-2 col-form-label" for="userPwd2">패스워드 확인</label>
			        <div class="col-sm-10">
			            <input type="password" name="userPwd2" id="userPwd2" class="form-control" autocomplete="off" placeholder="패스워드 확인">
			            <small class="form-control-plaintext">패스워드를 한번 더 입력해주세요.</small>
			        </div>
			    </div>
			    
			    <div class="row mb-3">
			        <label class="col-sm-2 col-form-label" for="userName">이름</label>
			        <div class="col-sm-10">
			            <input type="text" name="userName" id="userName" class="form-control" value="${dto.userName}" 
			            		${mode=="update" ? "readonly='readonly' ":""}
			            		placeholder="이름">
			        </div>
			    </div>
			 
			    <div class="row mb-3">
			        <label class="col-sm-2 col-form-label" for="birth">생년월일</label>
			        <div class="col-sm-10">
			            <input type="date" name="birth" id="birth" class="form-control" value="${dto.birth}" placeholder="생년월일">
			            <small class="form-control-plaintext">생년월일은 2000-01-01 형식으로 입력 합니다.</small>
			        </div>
			    </div>
			    
			    <div class="row mb-3">
			    	<label class="col-sm-2 col-form-label" for="selectEmail">이메일</label>
			    	<div class="col-sm-10 row">
						<div class="col-3 pe-0">
							<select name="selectEmail" id="selectEmail" class="form-select" onchange="changeEmail();">
								<option value="">선 택</option>
								<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
								<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
								<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
								<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
								<option value="direct">직접입력</option>
							</select>
						</div>
						
						<div class="col input-group">
							<input type="text" name="email1" class="form-control" maxlength="30" value="${dto.email1}" >
						    <span class="input-group-text p-1" style="border: none; background: none;">@</span>
							<input type="text" name="email2" class="form-control" maxlength="30" value="${dto.email2}" readonly="readonly">
						</div>		
	
			        </div>
			    </div>
			    
			    <div class="row mb-3">
			        <label class="col-sm-2 col-form-label" for="tel1">전화번호</label>
			        <div class="col-sm-10 row">
						<div class="col-sm-3 pe-2">
							<input type="text" name="tel1" id="tel1" class="form-control" value="${dto.tel1}" maxlength="3">
						</div>
						<div class="col-sm-1 px-1" style="width: 2%;">
							<p class="form-control-plaintext text-center">-</p>
						</div>
						<div class="col-sm-3 px-1">
							<input type="text" name="tel2" id="tel2" class="form-control" value="${dto.tel2}" maxlength="4">
						</div>
						<div class="col-sm-1 px-1" style="width: 2%;">
							<p class="form-control-plaintext text-center">-</p>
						</div>
						<div class="col-sm-3 ps-1">
							<input type="text" name="tel3" id="tel3" class="form-control" value="${dto.tel3}" maxlength="4">
						</div>
			        </div>
			    </div>
		
			    <div class="row mb-3">
			        <label class="col-sm-2 col-form-label" for="agree">약관 동의</label>
					<div class="col-sm-8" style="padding-top: 5px;">
						<input type="checkbox" id="agree" name="agree"
							class="form-check-input"
							checked="checked"
							style="margin-left: 0;"
							onchange="form.sendButton.disabled = !checked">
						<label class="form-check-label">
							<a href="#" class="text-decoration-none">이용약관</a>에 동의합니다.
						</label>
					</div>
			    </div>
			     
			    <div class="row mb-3">
			        <div class="text-center">
			            <button type="button" name="sendButton" class="btn btn-primary" onclick="memberOk();"> ${mode=="member"?"회원가입":"정보수정"} <i class="bi bi-check2"></i></button>
			            <button type="button" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/';"> ${mode=="member"?"가입취소":"수정취소"} <i class="bi bi-x"></i></button>
						<input type="hidden" name="userIdValid" id="userIdValid" value="false">
			        </div>
			    </div>
			
			    <div class="row">
					<p class="form-control-plaintext text-center">${message}</p>
			    </div>
	    	</form>
	    </div>
	</div>
</div>