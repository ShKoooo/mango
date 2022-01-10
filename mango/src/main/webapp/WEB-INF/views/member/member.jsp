<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	background-image: url("${pageContext.request.contextPath}/resources/images/add_photo.png");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}

.my-font-size {
	font-size: 14px;
}

.content-wrapper {
	background-color: #FFFFFF;
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
	var tel1 = f.tel1.value;
	var tel2 = f.tel2.value;
	var tel3 = f.tel3.value;
	
	if(mode === "member" && !/^[a-z][a-z0-9_]{4,9}$/i.test(id) ) {
		f.userId.focus();
		return;
	}
	
	if(mode === "member" && f.userIdValid.value === "false") {
		str = "아이디 중복 검사가 실행되지 않았습니다.";
		$("#userId").parents().find(".help-block1").html(str);
		f.userId.focus();
		return;
	}
	
	if(mode === "member" &&f.userNickValid.value === "false") {
		str = "닉네임 중복 검사가 실행되지 않았습니다.";
		$("#userNickName").parents().find(".help-block2").html(str);
		f.userNickName.focus();
		return;
	}
	
	if(mode === "member" &&f.userEmailValid.value === "false") {
		str = "이메일 중복 검사가 실행되지 않았습니다.";
		$("#email1").parents().find(".help-block3").html(str);
		f.email1.focus();
		return;
	}
	
	if(mode === "member" && !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(pwd) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.userPwd.focus();
		return;
	}

	if(pwd != pwd2 ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwd.focus();
        return;
	}
	
    if(mode === "member" && !/^[가-힣]{2,5}$/.test(name) ) {
        alert("이름을 다시 입력하세요. ");
        f.userName.focus();
        return;
    }

    if(mode === "member" && !birth ) {
        alert("생년월일를 입력하세요. ");
        f.birth.focus();
        return;
    }
    
    if(mode === "member" && !tel1 || !tel2 || !tel3 ) {
        alert("전화번호를 입력하세요. ");
        if (!tel1) {
        	f.tel1.focus(); return;
        }
        if (!tel2) {
        	f.tel2.focus(); return;
        }
        if (!tel3) { 
        	f.tel3.focus(); return;
        }
    }
    if(mode === "member" && !/^\d{2,3}$/.test(tel1) ) {
        alert("숫자만 가능합니다. ");
        f.tel1.focus();
        return;
    }

    if(mode === "member" && !/^\d{3,4}$/.test(tel2) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    if(mode === "member" && !/^\d{4}$/.test(tel3) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value.trim();
    if(mode === "member" && !str ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value.trim();
    if(mode === "member" && !str ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}";
    f.submit();
}

function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str != "direct") {
        f.email2.value = str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value = "";
        f.email2.readOnly = false;
        f.email1.focus();
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
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			var passed = data.passed;

			if(passed === "true") {
				var str = "<span style='color:blue; font-weight: bold;'>" + userNick + "</span> 닉네임은 사용가능 합니다.";
				$(".userNick-box").find(".help-block2").html(str);
				$("#userNickValid").val("true");
			} else {
				var str = "<span style='color:red; font-weight: bold;'>" + userNick + "</span> 닉네임은 이미 사용되고 있습니다.";
				$(".userNick-box").find(".help-block2").html(str);
				$("#userNickName").val("");
				$("#userNickValid").val("false");
				$("#userNickName").focus();
			}
		}
	});
}

// 이메일 중복검사
function userEmailCheck() {
	var email1 = $("#email1").val();
	var email2 = $("#email2").val();
	var userEmail = email1 + "%40" + email2; // @: &#64;
	
	var url = "${pageContext.request.contextPath}/member/userDuplCheck";
	var query = "userParam=" + userEmail;
	query += "&chkWay=userEmail";
	
	console.log(query);
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			var passed = data.passed;
			
			if(passed === "true") {
				var str = "<span style='color:blue; font-weight: bold;'>" + email1+"&#64;"+email2 + "</span> 이메일은 사용가능 합니다.";
				$(".userEmail-box").find(".help-block3").html(str);
				$("#userEmailValid").val("true");
			} else {
				var str = "<span style='color:red; font-weight: bold;'>" + email1+"&#64;"+email2 + "</span> 이메일은 이미 사용되고 있습니다.";
				$(".userEmail-box").find(".help-block3").html(str);
				$("#email1").val("");
				$("#email2").val("");
				$("#userEmailValid").val("false");
				$("#email1").focus();
			}
		}
	});
}

//수정필요
$(function() {
	var img = "${dto.userImgSaveFileName}";
	if( img ) { // 수정인 경우
		img = "${pageContext.request.contextPath}/uploads/photo/" + img;
		$(".memberForm .img-viewer").empty();
		$(".memberForm .img-viewer").css("background-image", "url("+img+")");
	}
	
	$(".memberForm .img-viewer").click(function(){
		$("form[name=memberForm] input[name=profileImg]").trigger("click"); 
	});
	
	$("form[name=memberForm] input[name=profileImg]").change(function(){
		var file=this.files[0];
		
		if(! file) {
			$(".memberForm .img-viewer").empty();
			if( img ) {
				img = "${pageContext.request.contextPath}/uploads/photo/" + img;
				$(".memberForm .img-viewer").css("background-image", "url("+img+")");
			} else {
				img = "${pageContext.request.contextPath}/resources/images/add_photo.png";
				$(".memberForm .img-viewer").css("background-image", "url("+img+")");
			}
			return false;
		}
		
		if(! file.type.match("image.*")) {
			this.focus();
			return false;
		}
		
		var reader = new FileReader();
		reader.onload = function(e) {
			$(".memberForm .img-viewer").empty();
			$(".memberForm .img-viewer").css("background-image", "url("+e.target.result+")");
		}
		reader.readAsDataURL(file);
	});
});

</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">	
			<div class="section-header">
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
		    
		    <div class="container">
		    	<form name="memberForm" class="memberForm" method="post" enctype="multipart/form-data">
		    		
		    		<div class="row mb-3">
				    	<label class="col-sm-2 col-form-label" for="selectProfileImg">프로필 사진</label>
				    	<div class="col-sm-10 row">
					    	<div class="img-viewer" ></div>
					    	<input type="file" id="selectProfileImg" name="profileImg" accept="image/*" class="form-control" style="display: none;">
				    	</div>
				    </div>
		    	
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
										<button type="button" class="btn btn-light my-font-size" onclick="userIdCheck();">아이디 중복 확인</button>
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
		    			<div class="col-sm-10 userNick-box">
		    				<div class="row">
		    					<div class="col-5 pe-1">
	    							<input type="text" name="userNickName" id="userNickName" class="form-control" value="${dto.userNickName}" placeholder="닉네임"
	    								${nickChangeable=="false" ? "readonly='readonly' ":""}>
								</div>
								<div class="col-3 ps-1">
									<c:if test="${mode=='member' || (mode=='update' && nickChangeable=='true')}">
										<button type="button" class="btn btn-light my-font-size" onclick="userNickCheck();">닉네임 중복 확인</button>
									</c:if>
								</div>
		    				</div>
							<small class="form-control-plaintext help-block2">
								<c:if test="${mode=='update'}">
									${nickChangeable=="true"?
										"욕설을 사용하거나 기타 약관에 위배되는 닉네임은 운영자에 의해 임의 변경될 수 있음을 고지해 드립니다.":
										"닉네임을 변경하고 30일이 지난 이후 변경할 수 있습니다."}
								</c:if>
								<c:if test="${mode=='member'}">
									욕설을 사용하거나 기타 약관에 위배되는 닉네임은 운영자에 의해 임의 변경될 수 있음을 고지해 드립니다.
								</c:if>
							</small>
		    			</div>
		    		</div>
		    		
		    		<div class="row mb-3">
						<label class="col-sm-2 col-form-label" for="userPwd">패스워드</label>
						<div class="col-sm-10">
				            <input type="password" name="userPwd" id="userPwd" class="form-control" autocomplete="off" placeholder="패스워드" value="${dto.userPwd}">
				            <small class="form-control-plaintext">패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</small>
				        </div>
				    </div>
				    
				    <div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="userPwd2">패스워드 확인</label>
				        <div class="col-sm-10">
				            <input type="password" name="userPwd2" id="userPwd2" class="form-control" autocomplete="off" placeholder="패스워드 확인" value="${dto.userPwd}">
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
				            <input type="date" name="birth" id="birth" class="form-control" value="${dto.birth}" placeholder="생년월일"
				            	${mode=="update" ? "readonly='readonly' ":""}>
				            <small class="form-control-plaintext">생년월일은 2000-01-01 형식으로 입력 합니다.</small>
				        </div>
				    </div>
				    
				    <div class="row mb-3">
				    	<label class="col-sm-2 col-form-label" for="selectEmail">이메일</label>
				    	<div class="col-sm-10 row userEmail-box">
							<div class="col-3 pe-0">
								<select name="selectEmail" id="selectEmail" class="form-select" onchange="changeEmail();" ${mode=="update" ? "readonly='readonly' ":""}>
									<option value="">선 택</option>
									<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
									<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
									<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
									<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
									<option value="direct">직접입력</option>
								</select>
							</div>
							
							<div class="col input-group">
								<input type="text" name="email1" id="email1" class="form-control" maxlength="30" value="${dto.email1}" 
									${mode=="update" ? "readonly='readonly' ":""}>
							    <span class="input-group-text p-1" style="border: none; background: none;">@</span>
								<input type="text" name="email2" id="email2" class="form-control" maxlength="30" value="${dto.email2}" readonly="readonly">
								<div class="col-3 ps-3">
									<c:if test="${mode=='member'}">
										<button type="button" class="btn btn-light my-font-size" onclick="userEmailCheck();">중복 확인</button>
									</c:if>
								</div>
							</div>
							
							<small class="form-control-plaintext help-block3">&nbsp;</small>	
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
				            <c:if test="${mode=='member'}">
					            <button type="button" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/';"> 가입취소 <i class="bi bi-x"></i></button>
				            </c:if>
				            <c:if test="${mode=='update'}">
					            <button type="button" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/mypage/main';"> 수정취소 <i class="bi bi-x"></i></button>
				            </c:if>
				            <c:if test="${mode=='member'}">
								<input type="hidden" name="userIdValid" id="userIdValid" value="false">
								<input type="hidden" name="userNickValid" id="userNickValid" value="false">
								<input type="hidden" name="userEmailValid" id="userEmailValid" value="false">
				            </c:if>
				            <c:if test="${mode=='update'}">
				            	<input type="hidden" name="userIdValid" id="userIdValid" value="true">
								<input type="hidden" name="userEmailValid" id="userEmailValid" value="true">
								<c:if test="${nickChangeable=='false'}">
									<input type="hidden" name="userEmailValid" id="userEmailValid" value="true">
								</c:if>
								<c:if test="${nickChangeable=='true'}">
									<input type="hidden" name="userEmailValid" id="userEmailValid" value="false">
								</c:if>
				            </c:if>
				            <c:if test=""></c:if>
				        </div>
				    </div>
				
				    <div class="row">
						<p class="form-control-plaintext text-center">${message}</p>
				    </div>
				    
				    <c:if test="${mode=='update'}">
						<input type="hidden" name="userImgSaveFileName" value="${dto.userImgSaveFileName}">
					</c:if>
		    	</form>
		    </div>
		</div>
	</div>
</div>