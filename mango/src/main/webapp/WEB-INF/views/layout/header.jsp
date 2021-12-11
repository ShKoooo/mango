<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<div class="site-header container-fluid">
            <div class="top-header">
                <div class="logo col-md-6 col-sm-6">
                    <h1><a href="${pageContext.request.contextPath}/" style="text-decoration: none;"><em>망고</em>마켓</a></h1>
                </div>
                
         	<div class="container">
	         	<div class="row">
	         		<div class="col">
						<div class="d-flex justify-content-end">
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
						<p>
							<a id="search-icon" class="btn-left fa fa-search" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample"></a>
						</p>
						<div class="collapse" id="collapseExample">
							<form class="d-flex">
								<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
								<button class="btn btn-outline-success" type="submit">Search</button>
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
                            <li><a href="${pageContext.request.contextPath}/village/list">동네 커뮤니티</a></li>
                            <li><a href="${pageContext.request.contextPath}/cscenter/list">고객센터</a>
                                <ul>
                                    <li><a href="">자주하는 질문</a></li>
                                    <li><a href="">1:1 문의</a></li>
                                </ul>
                            </li>
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
                    <li><a href="${pageContext.request.contextPath}/cscenter/list">고객센터</a>
                        <ul>
                            <li><a href="">자주하는 질문</a></li>
                            <li><a href="">1:1 문의</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
	</div>