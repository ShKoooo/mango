<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
//		var msg = userWished ? "관심 상품 등록이 취소되었습니다. " : "관심 상품으로 등록됐습니다! ";
		
//		if(! confirm( msg )) {
//			return false;
//		}
		
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
                    <div class="project-slider col-md-12">
                        <img src="" alt="Slide 1">
                        <img src="" alt="Slide 2">
                        <img src="" alt="Slide 1">
                        <a href="#" class="slidesjs-previous slidesjs-navigation">&lt;</a> 
                        <a href="#" class="slidesjs-next slidesjs-navigation">&gt;</a>
                    </div> <!-- /.project-slider -->
                    
                    <div class="project-infos col-md-12">
                    
                        <div class="box-content">
                            <h2 class="project-title">${dto.pSubject}</h2>
                            <span class="project-subtitle">${dto.userNickName}</span>
                            <span>${dto.manner} ℃</span>
                            <p>${dto.pContent}</p>
                            <ul class="project-meta">
                                
                                <c:if test="${! empty sessionScope.member}">
	                                <li>
	                                	<button type="button" class="btn btn-outline-danger btnSendProductLike" title="좋아요" style="width: 43px;"><i class="bi ${userProductWished ? 'bi-heart-fill':'bi-heart'}"></i></button>
	                             	</li>
                             	</c:if>
                             	
                             	<c:if test="${empty sessionScope.member}">
	                                <li>
	                                	<button type="button" class="btn btn-outline-danger btnSendProductLike" title="좋아요" style="width: 70px; color: red;" disabled="disabled"><i class="bi bi-heart-fill"></i>&nbsp;<span id="productWishCount">${productWishCount}</span></button>
	                             	</li>
                             	</c:if>
                             	
                                <li>
                                	${dto.pPrice}원 /
                                	<c:if test="${dto.pIsProposable=='T'}">
                                		<a href="">가격 제안하기</a>
                                	</c:if>
                                	<c:if test="${dto.pIsProposable=='F'}">
                                		가격 제안 불가
                                	</c:if>
                                	
                                </li>
                                <li><i class="fa fa-envelope-o"></i><a href="">거래 쪽지 보내기</a></li>
                            </ul>
                        </div>
                        
                        <div class="box-content">
                        	<c:choose>
								<c:when test="${sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/product/update?pNum=${dto.pNum}&group=${dto.pcNum}&page=${page}';">수정</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-light" disabled="disabled">수정</button>
								</c:otherwise>
							</c:choose>
					    	
							<c:choose>
					    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
					    			<button type="button" class="btn btn-light" onclick="deleteProduct();">삭제</button>
					    		</c:when>
					    		<c:otherwise>
					    			<button type="button" class="btn btn-light" disabled="disabled">삭제</button>
					    		</c:otherwise>
				    		</c:choose>
                        </div>
                        
                        
                    </div>
                </div>
            </div>
	</div>