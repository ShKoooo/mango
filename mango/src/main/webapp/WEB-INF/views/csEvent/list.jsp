<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${list.size() > 0}">
	<div class="accordion accordion-flush mt-2" id="accordionFlush">
		<c:forEach var="dto" items="${list}" varStatus="status">
			<c:if test="${dto.show == 'y' || sessionScope.member.membership > 50}">
				<div class="accordion-item" style="border: none;">
					<h2 class="accordion-header mb-1 border" id="flush-heading-${status.index}">
						<button class="accordion-button collapsed bg-light" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse-${status.index}" aria-expanded="false" aria-controls="flush-collapse-${status.index}">
							${dto.subject}
						</button>
					</h2>
					<div id="flush-collapse-${status.index}" class="accordion-collapse collapse" aria-labelledby="flush-heading-${status.index}" data-bs-parent="#accordionFlush">
						<div class="accordion-body">
	
							<div class="row border-bottom pb-1">이벤트 기간 : ${dto.start_date} ~ ${dto.end_date} </div>
							<div class="row p-2">
								${dto.content}
							</div>
							<c:if test="${sessionScope.member.membership > 50}">
								<div class="row py-1">
									<div class="col text-end">
										<p>글 공개 여부 : ${dto.show=="y" ? "공개" : "비공개"}</p>
										<a href="#" onclick="javascript:location.href='${pageContext.request.contextPath}/csEvent/update?num=${dto.num}&pageNo=${pageNo}';">수정</a>&nbsp;|
										<a href="#" onclick="deleteFaq('${dto.num}', '${pageNo}');">삭제</a>
									</div>
								</div>
							</c:if>
	
						</div>
					</div>
				</div>	
			</c:if>
		</c:forEach>
	</div>
</c:if>
 
<div class="page-box">
	${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
</div>

<div class="row py-3">

	
	<div class="col text-end">
		<c:if test="${sessionScope.member.membership > 50}">
			<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/csEvent/write';">글올리기</button>
		</c:if>
	</div>
</div>
