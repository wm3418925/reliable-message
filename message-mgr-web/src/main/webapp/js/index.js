// 显示提示信息
function divMsg(dom, result, classType, stayMilliSecond){
	$(dom).text(result);
	$(dom).addClass(classType);
	$(dom).show();

	if (null == stayMilliSecond || undefined == stayMilliSecond)
		stayMilliSecond = 6000;
	$(dom).fadeOut(stayMilliSecond);
}


function getInitialPageSize() {
	var cookiePageSize = $.cookie("warnPageSize");
	if (cookiePageSize)
		return parseInt(cookiePageSize);

	return 10;
}


var responseHandlerFunc = function(res) {
	return {"code":"0", "rows":res, "total":res.length};
}

var queryParamsFunc = function(params) {
	params["messageStatus"] = "MessageStatus_dead";
	params["orderBy"] = "id";
	params["desc"] = false;
	return params;
}

//表格展示部分
$("#messageTable").bootstrapTable({
	url:"message/page",
	method: "get", //请求方式（*）
	striped: true, //是否显示行间隔色
	cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	pagination: true, //是否显示分页（*）
	sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
	pageNumber: 1,
	contentType: "text/plain;charset=utf8",
	pageSize: getInitialPageSize(),
	pageList: [10, 15, 25, 50, 100, '所有'],
	smartDisplay: false,//智能显示分页按钮
	search: false,
	strictSearch: true,
	showColumns: true, //是否显示所有的列
	showRefresh: true, //是否显示刷新按钮
	minimumCountColumns: 2, //最少允许的列数
	clickToSelect: true, //是否启用点击选中行
	showToggle: true, //是否显示详细视图和列表视图的切换按钮
	cardView: false, //是否显示详细视图
	detailView: false, //是否显示父子表
	sortable: true, //是否启用排序
	queryParams: queryParamsFunc,
	responseHandler: responseHandlerFunc,
	columns:[[
		{field: 'id', title: '序号',visible:true,
			formatter: function (value, row, index) {
				return index+1;
			}},
		{field:'id',title:'id'},
		{field:'source',title:'发送者'},
		{field:'queue',title:'消息队列'},
		{field:'content',title:'消息内容'},
		{field:'retry',title:'重发次数'},
		{field:'status',title:'消息状态'},
		{field:'createTime',title:'创建时间'},
		{field:'updateTime',title:'更新时间'}
	]]
});

