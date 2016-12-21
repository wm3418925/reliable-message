<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<title>消息列表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-10 column">
				<div class="toolbar">
					<form id="queryForm" class="form-inline" role="form">
						<div class="form-group">
							<label>消息类型:</label>
							<select id="messageStatus">
								<option value="MessageStatus_unconfirmed">MessageStatus_unconfirmed</option>
								<option value="MessageStatus_confirmed">MessageStatus_confirmed</option>
								<option value="MessageStatus_sending">MessageStatus_sending</option>
								<option value="MessageStatus_dead" selected>MessageStatus_dead</option>
								<option value="MessageStatus_end">MessageStatus_end</option>
							</select>
						</div>
						<div class="form-group">
							<div id="dataMsg"  hidden ></div>
						</div>
					</form>
				</div>

				<table id="messageTable" class="table-striped table-hover" data-mobile-responsive="true" data-toolbar=".toolbar">
				</table>
			</div>
		</div>
	</div>

<!--公共部分-->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/util.js"></script>
<!--表格部分 -->
<script src="js/bootstrap-table.js"></script>
<script src="js/bootstrap-table-zh-CN.js"></script>
<!--私有部分-->
<script src="js/index.js"></script>
</body>
</html>