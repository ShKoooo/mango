<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>

.img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	padding: 0;
	background-image: url("${pageContext.request.contextPath}/resources/images/note-person-icon2.png");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}

</style>

<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	$(".btnSendGiftyLike").click(function(){
		var $i = $(this).find("i");
		var userWished = $i.hasClass("bi-heart-fill");
		var msg = userWished ? "이 게시글 찜을 취소하겠습니까?" : "이 게시글을 찜하시겠습니까?";
		
		if(! confirm(msg)) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/gifty/insertGwish";
		var gnum = "${dto.gNum}";
		var query = "gNum="+gnum+"&userWished="+userWished;
		
		var fn = function(data) {
			var state = data.state;
			if(state==="true") {
				if( userWished ) {
					$i.removeClass("bi-heart-fill").addClass("bi-heart");
				} else {
					$i.removeClass("bi-heart").addClass("bi-heart-fill");
				}
				
				var count = data.gwishCount;
				$("#gwishCount").text(count);
			
			}  else if(state==="wished") {
				alert("게시글 찜은 한번만 가능합니다. !!!");
			} else if(state==="false") {
				alert("게시물 찜하기 처리가 실패했습니다. !!!");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
function deleteGifty() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    var query = "gNum=${dto.gNum}&${query}";
	    var url = "${pageContext.request.contextPath}/gifty/delete?" + query;
    	location.href = url;
    }
}
</c:if>


function sendReport() {
	if(confirm("한 번 신고한 상품/게시물은 신고를 철회할 수 없습니다.\n정말 신고하시겠습니까?")) {
		var f = document.gReportForm;
		var query = "gNum=${dto.gNum}&page=${page}&group=${group}"
		
		f.action = "${pageContext.request.contextPath}/gifty/report?" + query;
	    f.submit();
	    
	    alert("신고가 접수되었습니다.");
	}
}

$(document).ready(function(){
	// 신고 모달의 close를 눌렀을 시, 내용 초기화
	$("#closeBtn").click(function(){
		$("#gReportF").each(function(){
			this.reset();
		});
	});
});

function updateDate() {
	if(confirm("게시글을 끌어올리겠습니까? ")) {
	    var query = "gNum=${dto.gNum}";
	    var url = "${pageContext.request.contextPath}/gifty/updateDate?" + query;
    	location.href = url;
    }
	
}

$(function(){
	var today = new Date();
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth()+1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	
	var today2 = year + "-" + month + "-" + day;
	
	var afterday = "${gUpOkDate}";
	
	var target = document.getElementById('gUpOkBtn');
	if("${dto.gUp}" < 3) {
		target.disabled = false;
		$("#gUpOkBtn").click(function(){
			if(today2 < afterday){
				alert("끌어올리기는 첫 게시물 등록 3일 후부터 가능합니다.");
				target.disabled = true;
			} else {
				target.disabled = false;
				updateDate();
			}
			});
	}
});

$(function(){
	$(".soldout").on("click", function(e){
		if(confirm("게시글을 거래 완료로 변경하시겠습니까?(거래완료로 변경시 리뷰요청이 가능합니다.)")){
			$(this).attr('data-bs-toggle', 'modal');
		} else {
			$(this).removeAttr('data-bs-toggle', 'modal');
			return false;
		}
	});
});

function sendReview() {
		if(confirm("리뷰 요청 쪽지를 보내시겠습니까??\n *확인을 누르면 거래완료 처리가 됩니다.")) {
			var f = document.gReviewForm;
			var query = "gNum=${dto.gNum}&page=${page}&group=${group}"
			
			f.action = "${pageContext.request.contextPath}/gifty/reviewReq?" + query;
		    f.submit();
		    
		    alert("리뷰 요청 쪽지를 전송하였습니다!");
		
	}
}



		
function clickInfo() {
	alert("거래 쪽지를 전송했습니다! 쪽지함에서 확인해주세요~!");
}
		
		
function clickBook() {
	alert("이 게시글에대한 알림설정을 완료했습니다! 거래중으로 변경되면 쪽지를 보내드립니다~!");
}
		

</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 기프티콘 자세히 보기 </h3>
		</div>
		
		<div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header m-4">
                        <h2>기프티콘 거래</h2>
                        <span>기프티콘 거래로 아름다운 세상 만들어요~</span>
                    </div> <!-- /.section-header -->
                </div> <!-- /.row -->
                <div class="project-detail row">
                <!--
                	<div class="project-slider col-md-12">
                        <img width="1000px" height="600px" src="${dto.gImgSaveFileName}" alt="Slide 1">
                        <img src="" alt="Slide 2">
                        <img src="" alt="Slide 1">
                        <a href="#" class="slidesjs-previous slidesjs-navigation">&lt;</a> 
                        <a href="#" class="slidesjs-next slidesjs-navigation">&gt;</a>
                    </div>
                  -->  
                    <div class="project-infos col-md-12">
                        <div class="box-content">
                        	<div class="row mb-2">
                        		<div class="col-auto me-auto">
		                            <h2 class="project-title">${dto.gSubject}</h2>
                        		</div>
                        		<div class="col-auto text-right">
                        			<span>${dto.gRegdate}&nbsp;| &nbsp;조회수 : ${dto.gHitCount}</span>
                        		</div>
                        	</div>
                            <div class="row mb-2">
                            	<div class="col-md-3">
                            		<div class="row">
                            			<div class="col-auto me-1"> <!-- 왜이미지 안나와.. -->
				                            <a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${dto.userNickName}">
				                            	<c:if test="${not empty dto.userImgSaveFileName}">
													<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
														class="img-fluid img-thumbnail img-viewer">
												</c:if>
												<c:if test="${empty dto.userImgSaveFileName}">
													<img class="img-fluid img-thumbnail img-viewer">
												</c:if>
											</a>                            	
                            			</div>
                            			<div class="col-auto pt-2">
						                    <span class="project-subtitle sb-3"><a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${dto.userNickName}">${dto.userNickName}</a></span>
                            			</div>
                            		</div>
                            	</div>
                            	<div class="col-md-9">
                            		<div class="row">
                            			<div class="col-auto me-auto">
                            				&nbsp;
                            			</div>
                            			<div class="col-auto text-right">
				                            <span>${dto.manner} ℃</span>
				                            <div class="progress">
												<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: ${dto.manner}%;"></div>
											</div>
											  <span>매너온도</span>
                            			</div>
                            		</div>
                            		<%-- 
			                            <div style="float: right;">
			                            </div>
                            		--%>
                            	</div>
                            </div>
                            
                            <p class="editor">${dto.gContent}</p>
                            <ul class="project-meta">
                            	<c:if test="${! empty sessionScope.member && sessionScope.member.userId!=dto.userId}">
                                   <li>
                                      <button type="button" class="btn btn-outline-danger btnSendGiftyLike" title="좋아요" style="width: 43px;"><i class="bi ${userGwished ? 'bi-heart-fill':'bi-heart'}"></i></button>
                                   </li>
                                </c:if>
                                
                                <c:if test="${empty sessionScope.member || sessionScope.member.userId==dto.userId}">
                                   <li>
                                      <button type="button" class="btn btn-outline-danger btnSendGiftyLike" title="좋아요" style="width: 70px; color: red;" disabled="disabled"><i class="bi bi-heart-fill"></i>&nbsp;<span id="giftyWishCount">${giftyWishCount}</span></button>
                                   </li>
                                </c:if>
                            
                            	 <li>
                            	 	<c:if test="${dto.gPrice<=0}">
                            	 		무료나눔<span style="color: orange;"><i class="bi bi-suit-heart-fill"></i></span>
                            	 	</c:if>
                            	 	<c:if test="${dto.gPrice>0}">
                            	 		${dto.gPrice}원 /
	                            	 	<c:if test="${dto.gIsProposable eq 'T'}">
	                                		<a href="">가격 제안하기</a>
	                                	</c:if>
	                                	<c:if test="${dto.gIsProposable eq 'F'}">
	                                		가격 제안 불가
	                                	</c:if>
                            	 	</c:if>
                            	 </li>
                            	 <li>
                                	<span>유효기간 : ${dto.gExpdate}</span>
                               	 </li>
                                 <li>
                                	<c:if test="${dto.gStatus eq '판매중'}">
                                      <c:if test="${sessionScope.member.userId==dto.userId || empty sessionScope.member}">
                                         <i class="fa fa-envelope-o"></i>거래 쪽지 보내기
                                      </c:if>
                                      <c:if test="${sessionScope.member.userId!=dto.userId && ! empty sessionScope.member}">
                                		<i class="fa fa-envelope-o"></i><a href="${pageContext.request.contextPath}/gifty/sendMsg?gNum=${dto.gNum}">거래 쪽지 보내기</a>
                                      </c:if>
                                	</c:if>
                                	<c:if test="${dto.gStatus eq '예약중'}">
                                		<c:if test="${sessionScope.member.userId==dto.userId || empty sessionScope.member}">
                                			<i class="bi bi-calendar-check-fill"></i><span>다른 회원이 예약중입니다.</span>
                                		</c:if>
                                		<c:if test="${sessionScope.member.userId!=dto.userId && ! empty sessionScope.member}">
                                			<i class="bi bi-calendar-check-fill"></i><a onclick="clickBook();" href="${pageContext.request.contextPath}/gifty/insertBook?gNum=${dto.gNum}&page=${page}&group=${group}">다른 회원이 예약중입니다.(알림설정하기)</a>
                                		</c:if>
                                	</c:if>
                                	<c:if test="${dto.gStatus eq '거래완료'}">
                                		<i class="bi bi-check2-circle"></i><span>판매완료</span>
                                	</c:if>
                                </li>
                           
                            </ul>
                        </div>
                        
                        <div class="box-content">
                        <c:if test="${dto.gStatus ne '거래완료'}">
                        	<c:choose>
								<c:when test="${sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/gifty/update?gNum=${dto.gNum}&page=${page}&group=${group}';">수정</button>
								</c:when>
								<c:when test="${! empty sessionScope.member && sessionScope.member.userId!=dto.userId}">
									<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">신고</button>
									<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									      <div class="modal-header">
									        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
									        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									      </div>
									      <form name="gReportForm" id="gReportF" method="post">
									      <div class="modal-body">
									          <div class="mb-3">
									            <label for="recipient-name" class="col-form-label">신고 항목</label><br>
									            <!--  
									            <input type="text" class="form-control" id="recipient-name">
									            -->
									            <c:forEach var="vo" items="${listGreport}">							
													<input type="radio" name="rgReasonNum" value="${vo.rgReasonNum}">${vo.rgReasonName}<br>
												</c:forEach>
									          </div>
									          <div class="mb-3">
									            <label for="message-text" class="col-form-label">자세한 사유</label>
									            <textarea class="form-control" id="message-text" name="reqGiftyContent"></textarea>
									          </div>
									      </div>
									      <div class="modal-footer">
									        <button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">취소하기</button>
									        <button type="button" class="repbtn btn btn-primary" onclick="sendReport();">보내기</button>
									      </div>
									      </form>
									    </div>
									  </div>
									</div>
								</c:when>
							</c:choose>
					    	
							<c:choose>
					    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
					    			<button type="button" class="btn btn-light" onclick="deleteGifty();">삭제</button>
					    		<c:if test="${dto.gUp < 3}">
					    			<button type="button" class="btn btn-light" id="gUpOkBtn" >끌어올리기</button>
				    			</c:if>
					    		</c:when>
				    		</c:choose>
				    	</c:if>		
				    			<c:if test="${! empty sessionScope.member && sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn btn-primary"  style="background: #3c8bff; border-color: #3c8bff;" data-bs-toggle= "modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">거래완료</button>
									<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									      <div class="modal-header">
									        <h5 class="modal-title" id="exampleModalLabel">리뷰 요청하기</h5>
									        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
									      </div>
									      <form name="gReviewForm" id="gReviewF" method="post">
									      <div class="modal-body">
									          <div class="mb-3">
									            <label for="recipient-name" class="col-form-label">구매자 선택</label><span style="color: gray; font-size: 13px;">(최근 채팅을 주고받은 이웃 중 구매자를 선택해주세요!)</span><br>
									            <!--  
									            <input type="text" class="form-control" id="recipient-name">
									            -->
									            <br>
									            <select name="target_id" id="target_id">
								            	<option value="">선택</option>
									            <c:forEach var="dto" items="${listTargetId}">
									            	<option value="${dto.sendId}">${dto.userNickName}</option>
												</c:forEach>
								            </select>
									          </div>
									          <div class="mb-3">
								            <label for="message-text" class="col-form-label">판매 금액</label><span style="color: gray; font-size: 13px;"> (금액 변동시 직접 기입해주세요.)</span>
								           	<input type="text" name="income" class="form-control" id="recipient-name" value="${dto.gPrice}">
								          </div>
								          <span>*주의 : 리뷰 요청시 자동으로 해당 게시글은 거래완료 처리가 됩니다.</span>
								      </div>
								      <div class="modal-footer">
								        <button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">취소하기</button>
								        <button type="button" class="repbtn btn btn-primary" onclick="sendReview();">리뷰 요청하기</button>
								      </div>
									      </form>
									    </div>
									  </div>
									</div>
								</c:if>
	                        <span style='float: right;'>
	                        	<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/gifty/list?${query}';">리스트</button>
	                        </span>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	 </div>
</div>