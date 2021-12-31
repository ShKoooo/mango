﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function searchWard() {
	var f = document.productSearchForm;
	f.submit();
}

</script>

	<div class="site-header container-fluid">
            <div class="top-header">
                <div class="logo col-md-6 col-sm-6">
                    <h1><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><em>망고</em>마켓</a></h1>
                </div>
                
         	<div class="container">
	         	<div class="row">
	         		<div class="col">
						<div class="d-flex justify-content-end" style="margin-top:15px">
							<c:choose>
								<c:when test="${empty sessionScope.member}">
									<div class="p-2">
										<a href="javascript:dialogLogin();" title="로그인"><i class="bi bi-lock"></i></a>
									</div>
									<div class="p-2">
										<a href="${pageContext.request.contextPath}/member/member" title="회원가입"><i class="bi bi-person-plus"></i></a>
									</div>	
								</c:when>
								<c:otherwise>
									<div class="p-2">
										<a href="${pageContext.request.contextPath}/member/logout" title="로그아웃"><i class="bi bi-unlock"></i></a>
									</div>					
									<div class="p-2">
										<a href="#" title="알림"><i class="bi bi-bell"></i></a>
									</div>
									<c:if test="${sessionScope.member.membership>50}">
										<div class="p-2">
											<a href="${pageContext.request.contextPath}/admin" title="관리자"><i class="bi bi-gear"></i></a>
										</div>					
									</c:if>
									<c:if test="${sessionScope.member.membership<50}">
										<div class="p-2">
											<a href="${pageContext.request.contextPath}/mypage/main" title="마이페이지">${sessionScope.member.userNickName} 님</a>
										</div>					
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
	         	</div>	
	         </div>		
            </div>
            
          
            <div class="main-header">
                <div class="row">
                    <div class="main-header-left col-md-3 col-sm-6 col-xs-8">
							<a id="search-icon" class="btn-left fa fa-search" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample"></a>
						<div class="collapse" id="collapseExample">
							<form class="d-flex" name="productSearchForm" method="post" action="${pageContext.request.contextPath}/search/productList">
								<input class="form-control me-2" type="text" name="searchKeyword" style="width:500px; height: 40px;"
   									 value="${searchKeyword}" placeholder=" 어떤 매물을 찾으시나요  ?" aria-label="Search">
								<button class="btn btn-outline-success mt-1" type="button" onclick="searchWard()" style="height:39px;">Search</button>
							</form>
						</div>
                    </div>
                    <div class="menu-wrapper col-md-9 col-sm-6 col-xs-4">
                        <a href="#" class="toggle-menu visible-sm visible-xs"><i class="fa fa-bars"></i></a>
                        <ul class="sf-menu hidden-xs hidden-sm">
                          
                            <li><a href="">사고팔기</a>
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/product/list">중고거래</a></li>
                                    <li><a href="${pageContext.request.contextPath}/gifty/list">기프티콘 거래</a></li>
                                </ul>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/village/list">동네 커뮤니티</a>
                            	<ul>
                            		<li><a href="${pageContext.request.contextPath}/village/qna/list">동네 질문</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/eat/list">동네 맛집</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/help/list">해주세요</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/with/list">같이해요</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/news/list">동네 소식</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/lost/list">분실실종</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/ad/list">여기올래?</a></li>
                            		<li><a href="${pageContext.request.contextPath}/village/forone/list">1인가구</a></li>
                            	</ul>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/cscenter/list">고객센터</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="responsive-menu">
                <ul>
                    <li><a href="#">사고팔기</a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/product/list">중고거래</a></li>
                            <li><a href="${pageContext.request.contextPath}/gifty/list">기프티콘 거래</a></li>
                        </ul>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/village/list">동네 커뮤니티</a></li>
                    <li><a href="${pageContext.request.contextPath}/cscenter/list">고객센터</a></li>
                </ul>
            </div>
	</div>