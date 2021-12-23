<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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
		var f = document.pReportForm;
		var query = "gNum=${dto.gNum}&page=${page}&gpcNum=${gcNum}"
		
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


</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 기프티콘 자세히 보기 </h3>
		</div>
		
		<div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>기프티콘 거래</h2>
                        <span>기프티콘 거래로 아름다운 세상 만들어요~</span>
                    </div> <!-- /.section-header -->
                </div> <!-- /.row -->
                <div class="project-detail row">
                    <div class="project-infos col-md-12">
                        <div class="box-content">
                            <h2 class="project-title">${dto.gSubject}</h2>
                            <span class="project-subtitle">${dto.userNickName}</span>
                            <span>${dto.manner} ℃</span>
                            <span>조회수 : ${dto.gHitCount}</span>
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
                            
                            	 <li>${dto.gPrice}원
                            	 	<c:if test="${dto.gIsProposable=='T'}">
                                		<a href="">가격 제안하기</a>
                                	</c:if>
                                	<c:if test="${dto.gIsProposable=='F'}">
                                		가격 제안 불가
                                	</c:if>
                            	 </li>
                                 <li>
                                	<c:if test="${dto.gStatus=='판매중'}">
                                		<i class="fa fa-envelope-o"></i><a href="">거래 쪽지 보내기</a>
                                	</c:if>
                                	<c:if test="${dto.gStatus=='예약중'}">
                                		<i class="bi bi-calendar-check-fill"></i><span>다른 회원이 예약중입니다.</span>
                                	</c:if>
                                	<c:if test="${dto.gStatus=='거래완료'}">
                                		<i class="bi bi-check2-circle"></i><span>판매완료</span>
                                	</c:if>
                                </li>
                           
                            </ul>
                        </div>
                        
                        <div class="box-content">
                        <c:if test="${dto.gStatus !='거래완료'}">
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
									            <c:forEach var="vo" items="${listPreport}">							
													<input type="radio" name="rpReasonNum" value="${vo.rpReasonNum}">${vo.rpReasonName}<br>
												</c:forEach>
									          </div>
									          <div class="mb-3">
									            <label for="message-text" class="col-form-label">자세한 사유</label>
									            <textarea class="form-control" id="message-text" name="repPrdContent"></textarea>
									          </div>
									      </div>
									      <div class="modal-footer">
									        <button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
									        <button type="button" class="repbtn btn btn-primary" onclick="sendReport();">Send</button>
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
					    			<button type="button" class="btn btn-light" onclick="updateDate();">끌어올리기</button>
					    		</c:when>
				    		</c:choose>
					    </c:if>		
	                        <span style="float: right;">
	                        	<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/gifty/list?${query}';">리스트</button>
	                        </span>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	 </div>
</div>