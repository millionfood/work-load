<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty success }">
	<div class="flash_box_success">
        <div class="flash_message_box">
            <span class="flash_message_title">성공</span>
            <span class="flash_message_content">${success }</span>
        </div>
	</div>
</c:if>
<c:if test="${not empty fail }">
	<div class="flash_box_fail">
        <div class="flash_message_box">
            <span class="flash_message_title">실패</span>
            <span class="flash_message_content">${fail }</span>
        </div>
	</div>
</c:if>