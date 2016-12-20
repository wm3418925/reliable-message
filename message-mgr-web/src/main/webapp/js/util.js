function isIntegerStr(str) {
	return (/^-?\d+$/.test(str));
}
function idArrayToStr(idArray) {
	var s = "";
	for (var i=0; i<idArray.length; ++i) {
		if (isInteger(idArray[i]))
			s += idArray[i] + ",";
	}
	if (s.length > 0)
		s = s.substr(0, s.length-1);

	return s;
}

//深度复制对象
function deepCopy(source) {
	var result = {};
	for (var key in source) {
		result[key] = typeof source[key]==='object'? deepCopy(source[key]) : source[key];
	}
	return result;
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return decodeURI(r[2]); return null; //返回参数值
}


//扩展Date的format方法
Date.prototype.format = function (format) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S+": this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			var replaceWith;
			if (RegExp.$1.length == 1)
				replaceWith = o[k];
			else if (k == "S+")
				replaceWith = ("000" + o[k]).substr(("" + o[k]).length);
			else
				replaceWith = ("00"  + o[k]).substr(("" + o[k]).length);
			format = format.replace(RegExp.$1, replaceWith);
		}
	}
	return format;
}

// inputDateStr 必须与 inputFormat 一一对应且等长
function strToDate(inputDateStr, inputFormat) {
	var cur = new Date();
	var obj = {
		y: cur.getYear(),
		M: 1,
		d: 1,
		h: 0,
		m: 0,
		s: 0,
		S: 0
	};

	// 预处理, 删除format 中 yMdhmsS 之外的字符, 同时删除str对应的字符
	var str = "";
	var format = "";
	for (var i=0; i<inputFormat.length; ++i) {
		if ("yMdhmsS".indexOf(inputFormat.charAt(i)) >= 0) {
			str += inputDateStr[i];
			format += inputFormat[i];
		}
	}

	var startIdx=0, endIdx;
	while (startIdx < format.length) {
		var startChar = format.charAt(startIdx);
		endIdx = startIdx+1;
		while (endIdx < format.length && format.charAt(endIdx) == startChar)
			++endIdx;

		obj[startChar] = parseInt(str.substring(startIdx, endIdx));

		startIdx = endIdx;
	}

	return new Date(obj.y, obj.M - 1, obj.d, obj.h, obj.m, obj.s, obj.S);
}

/**
 *转换日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
	var pattern = "";
	if (isFull == true || isFull == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	} else {
		pattern = "yyyy-MM-dd";
	}
	return getFormatDate(date, pattern);
}
/**
 *转换当前日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatNowDate(isFull) {
	return getSmpFormatDate(new Date(), isFull);
}
/**
 *转换long值为日期字符串
 * @param l long值
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatDateByLong(l, isFull) {
	return getSmpFormatDate(new Date(l), isFull);
}
/**
 *转换long值为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */

function getFormatDateByLong(l, pattern) {
	return getFormatDate(new Date(l), pattern);
}
/**
 *转换日期对象为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
	if (date == undefined) {
		date = new Date();
	}
	if (pattern == undefined) {
		pattern = "yyyy-MM-dd hh:mm:ss";
	}
	return date.format(pattern);
}

/**
 * jquery cookie 操作函数
 * 1 获取 var value = $.cookie("cookieName");
 * 2 设置 $.cookie("cookieName", cookieValue);
 * 3 设置选项 $.cookie("cookieName", cookieValue, {"expires":expiresDayCount,"path":path,"domain":domain,"secure":true});
* */
jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};

function isArray(obj) {
	return Object.prototype.toString.call(obj) === '[object Array]';
}

function copyPropWithOutArray(des,src){
	for(var o in src){
		if(!isArray(o)){
			des[o]=src[o];
		}
	}
}


// 数据项数据比较
function dataNodeDataCompare(dataType, aStr, bStr) {
	var aValue;
	var bValue;
	if (dataType == 1) {
		return aStr.charCodeAt(0) - bStr.charCodeAt(0);
	} else if (dataType == 10 || dataType == 11) {
		aValue = parseFloat(aStr);
		bValue = parseFloat(bStr);
	} else if (dataType >= 2) {
		aValue = parseInt(aStr);
		bValue = parseInt(bStr);
	} else {
		return 0;
	}

	var interValue = aValue-bValue;
	if (interValue < 0)
		return -1;
	else if (interValue > 0)
		return 1;
	else
		return 0;
}
