<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	padding: 0;
	background-image: url("${pageContext.request.contextPath}/resources/images/note-person-icon2.png");
	position: relative;
	/*
	z-index: 9999;
	*/
	background-repeat : no-repeat;
	background-size : cover;
}
</style>

<script type="text/javascript">
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

// 게시글 공감
$(function(){
	$(".btnSendProductLike").click(function(){
		var $i = $(this).find("i");
		var userWished = $i.hasClass("bi-heart-fill");
		
		var url="${pageContext.request.contextPath}/product/insertProductWish";
		var pNum="${dto.pNum}";
		var query="pNum="+pNum+"&userWished="+userWished;
		
		var fn = function(data){
			var state = data.state;
			if(state==="true") {
				if( userWished ) {
					$i.removeClass("bi-heart-fill").addClass("bi-heart");
				} else {
					$i.removeClass("bi-heart").addClass("bi-heart-fill");
				}
				
				var count = data.productWishCount;
				$("#productWishCount").text(count);
			} else if(state==="wished") {
				alert("게시글 공감은 한번만 가능합니다. !!!");
			} else if(state==="false") {
				alert("게시물 공감 여부 처리가 실패했습니다. !!!");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
function deleteProduct() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    var query = "pNum=${dto.pNum}&${query}";
	    var url = "${pageContext.request.contextPath}/product/delete?" + query;
    	location.href = url;
    }
}
</c:if>


function sendReport() {
	if(confirm("한 번 신고한 상품/게시물은 신고를 철회할 수 없습니다.\n정말 신고하시겠습니까?")) {
		var f = document.pReportForm;
		var query = "pNum=${dto.pNum}&page=${page}&pcNum=${pcNum}"
		
		f.action = "${pageContext.request.contextPath}/product/report?" + query;
	    f.submit();
	    
	    alert("신고가 접수되었습니다.");
	}
}

$(document).ready(function(){
	// 신고 모달의 close를 눌렀을 시, 내용 초기화
	$("#closeBtn").click(function(){
		$("#pReportF").each(function(){
			this.reset();
		});
	});
});

function updateDate() {
	if(confirm("게시글을 끌어올리겠습니까? ")) {
	    var query = "pNum=${dto.pNum}";
	    var url = "${pageContext.request.contextPath}/product/updateDate?" + query;
    	location.href = url;
    }
}

function clickListBtn() {
	var locationInfo = 'product/list';
	if(($("#searchKeyword").val() || "") != ""){
		locationInfo = 'search/productList';
	}else if(($("#isPorular").val() || "") != ""){
		locationInfo = 'product/popular';
	}
	
	var url = '${pageContext.request.contextPath}/' + locationInfo + '?${query}';

	location.href=url;
}

// 글을 작성한지 3일 후에 끌올 가능하게 만들기
$(function(){
	var today = new Date();
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth()+1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	
	var today2 = year + "-" + month + "-" + day;
	
	var afterday = "${pUpOkDate}";
	
	var target = document.getElementById('pUpOkBtn');
/*
	target.disabled = true;
	
	if(today2 >= afterday) {
		target.disabled = false;
	}
*/	
	if("${dto.pUp}" < 3) {
	target.disabled = false;
	   $("#pUpOkBtn").click(function(){
	      if(today2 < afterday){
	         alert("끌어올리기는 첫 게시물 등록 3일후부터 가능합니다.");
	         target.disabled = true;
	      } else {
	         target.disabled = false;
	         updateDate();
	      }
	   });
	}	   
});

function clickInfo() {
	alert("거래 쪽지를 전송했습니다! 쪽지함에서 확인해주세요~!");
}

</script>
    
    <div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>Project Detail</h2>
                        <span>Subtitle Goes Here</span>
                    </div>
                </div>
                <div class="project-detail row">
                	<!-- /.project-slider -->
                    <div class="project-slider col-md-12">
                        <img src="" alt="Slide 1">
                        <img src="" alt="Slide 2">
                        <img src="" alt="Slide 1">
                        <a href="#" class="slidesjs-previous slidesjs-navigation">&lt;</a> 
                        <a href="#" class="slidesjs-next slidesjs-navigation">&gt;</a>
                    </div>
                    
                    <div class="project-infos col-md-12">                    
                        <div class="box-content">
                        	<div>
	                            <span style="font-size: 20px;">${dto.pSubject}</span>
	                            <span style="float: right">조회수 : ${dto.pHitCount}</span>
                            </div>
                            
                            <div>
	                            <a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${dto.userNickName}">
	                            	<c:if test="${not empty dto.userImgSaveFileName}">
										<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
											class="img-fluid img-thumbnail img-viewer">
									</c:if>
									<c:if test="${empty dto.userImgSaveFileName}">
										<img
											class="img-fluid img-thumbnail img-viewer">
									</c:if>
								</a>
	                            <span>${dto.userNickName}</span> <br>
	                            <span class="project-subtitle">${dto.area2}&nbsp;${dto.area3}</span>
                            </div>
                            
                            <div style="float: right">
                            	<span>${dto.manner} ℃</span>
	                            <div class="progress" style="width: 110px; font-size: 10px;">
								  <div class="progress-bar progress-bar-striped bg-success progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: ${dto.manner}%">${dto.manner} ℃</div>
								</div>
	               
	                            <span>매너온도</span>
	                            <input type="hidden" id="searchKeyword" value="${searchKeyword}">
	                            <input type="hidden" id="isPorular" value="${isPorular}">
                            </div>
                            
                            <p>${dto.pContent}</p>
                            <ul class="project-meta">
                                
                                <c:if test="${! empty sessionScope.member && sessionScope.member.userId!=dto.userId}">
	                                <li>
	                                	<button type="button" class="btn btn-outline-danger btnSendProductLike" title="좋아요" style="width: 43px;"><i class="bi ${userProductWished ? 'bi-heart-fill':'bi-heart'}"></i></button>
	                             	</li>
                             	</c:if>
                             	
                             	<c:if test="${empty sessionScope.member || sessionScope.member.userId==dto.userId}">
	                                <li>
	                                	<button type="button" class="btn btn-outline-danger btnSendProductLike" title="좋아요" style="width: 70px; color: red;" disabled="disabled"><i class="bi bi-heart-fill"></i>&nbsp;<span id="productWishCount">${productWishCount}</span></button>
	                             	</li>
                             	</c:if>
                             	
                                <li>
                                	${dto.pPrice}원 /
                                	<c:if test="${dto.pIsProposable=='T'}">
                                		<a href="">가격 제안 가능</a>
                                	</c:if>
                                	<c:if test="${dto.pIsProposable=='F'}">
                                		가격 제안 불가
                                	</c:if>
                                	
                                </li>
                                <li>
                                	<c:if test="${dto.pStatus=='판매중'}">
                                		<i class="fa fa-envelope-o"></i><a onclick="clickInfo();" href="${pageContext.request.contextPath}/product/article?pNum=${dto.pNum}&page=${page}&pcNum=${pcNum}">거래 쪽지 보내기</a>
                                	</c:if>
                                	<c:if test="${dto.pStatus=='예약중'}">
                                		<i class="bi bi-calendar-check-fill"></i><a href="">거래가 예약된 매물입니다</a>
                                	</c:if>
                                	<c:if test="${dto.pStatus=='거래완료'}">
                                		<i class="bi bi-check2-circle"></i><span>판매완료</span>
                                	</c:if>
                                </li>
                            </ul>
                        </div>
                        
                        <div class="box-content">
                        <c:if test="${dto.pStatus !='거래완료'}">
                        	<c:choose>
								<c:when test="${sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/product/update?pNum=${dto.pNum}&pcNum=${dto.pcNum}&page=${page}';">수정</button>
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
									      <form name="pReportForm" id="pReportF" method="post">
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
					    			<button type="button" class="btn btn-light" onclick="deleteProduct();">삭제</button>
					    			
					    			<c:if test="${dto.pUp < 3}">
					    				<button type="button" id="pUpOkBtn" class="btn btn-light">끌어올리기</button>
					    			</c:if>
					    		</c:when>
				    		</c:choose>
				    	</c:if>
	                        <span style="float: right;">
	                        	<button type="button" class="btn btn-light" name="upButton" onclick="clickListBtn()">리스트</button>
	                        </span>
                        </div>
                    </div>
                  
                </div>
            </div>
	</div>