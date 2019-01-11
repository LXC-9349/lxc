var apiUrl = { // api
	addTaskConfig: '/api/call/addTaskConfig',
	editCallTask: '/api/call/editCallTask',
	locaCallTask: '/api/call/locaCallTask',
	addTbscallconfig: '/api/call/addTbscallconfig',
	editTbsCallState: '/api/call/editTbsCallState' // 0 开启 1关闭
}
// $('#connect').click(function () { // 保存按钮
// 	if (!valueData()) {
// 		return false
// 	}
// 	var personArr = []
// 	var dataNum = $('.time-wrap label').length
// 	for (var i = 0; i < dataNum; i++) {
// 		var text = $('.time-wrap label:eq(' + i + ') input').val()
// 		if (text) {
// 			personArr.push($('.time-wrap label:eq(' + i + ') input').val())
// 		}
// 	}
// 	// console.log(personArr.toString())
// 	var dataObj = {
// 		taskId: $('input[name="taskId"]').val(),
// 		tbsInterfaceURL: $('input[name="tbsInterfaceURL"]').val(),
// 		appId: $('input[name="appId"]').val(),
// 		accessKey: $('input[name="accessKey"]').val(),
// 		callcolumn: $('input[name="callcolumn"]').val(),
// 		callqueue: $('input[name="callqueue"]').val(),
// 		address: $('input[name="address"]').val(),
// 		callnumber: $('select[name="callnumber"]').val()
// 	}

// 	$.ajax({
// 		url: apiUrl.addTaskConfig,
// 		type: 'POST',
// 		dateType:'json',
// 		data: {
// 			timeArr: personArr.toString(),
// 			taskConfig: JSON.stringify(dataObj)
// 		},
// 		success: function (res) {
// 			res = JSON.parse(res)
// 			// console.log(res)
// 			if (res.status == 100000) {
// 				alert('保存成功！')
// 				return false
// 			}
// 			alert('保存失败！')
// 		},
// 		error: function () {
// 			alert('保存失败！')
// 		}
// 	})
// })

// $('#edit-btn').click(function () { // 编辑按钮
// 	if (!valueData()) {
// 		return false
// 	}
// 	var personArr = []
// 	var dataNum = $('.time-wrap label').length
// 	for (var i = 0; i < dataNum; i++) {
// 		var text = $('.time-wrap label:eq(' + i + ') input').val()
// 		if (text) {
// 			personArr.push($('.time-wrap label:eq(' + i + ') input').val())
// 		}
// 	}
// 	// console.log(personArr.toString())
// 	var dataObj = {
// 		id: $('input[name="id"]').val(),
// 		taskId: $('input[name="taskId"]').val(),
// 		taskId: $('input[name="taskId"]').val(),
// 		tbsInterfaceURL: $('input[name="tbsInterfaceURL"]').val(),
// 		appId: $('input[name="appId"]').val(),
// 		accessKey: $('input[name="accessKey"]').val(),
// 		callcolumn: $('input[name="callcolumn"]').val(),
// 		callqueue: $('input[name="callqueue"]').val(),
// 		address: $('input[name="address"]').val(),
// 		callnumber: $('select[name="callnumber"]').val()
// 	}
// 	// console.log(JSON.stringify(dataObj))
// 	// return
// 	$.ajax({
// 		url: apiUrl.editCallTask,
// 		type: 'POST',
// 		dateType:'json',
// 		data: {
// 			timeArr: personArr.toString(),
// 			taskConfig: JSON.stringify(dataObj)
// 		},
// 		success: function (res) {
// 			res = JSON.parse(res)
// 			// console.log(res)
// 			if (res.status == 100000) {
// 				alert('编辑成功！')
// 				location.reload()
// 				return false
// 			}
// 			alert('编辑失败！')
// 		},
// 		error: function () {
// 			alert('编辑失败！')
// 		}
// 	})
// })

// // 获取呼叫中心配置
// $.ajax({
// 	url: apiUrl.locaCallTask,
// 	type: 'GET',
// 	dateType:'json',
// 	success: function (res) {
// 		res = JSON.parse(res)
// 		console.log(res)
// 		if (res.status == 100000) {
// 			return
// 			var data = res.data.calltask[0],
// 				time = res.data.callTime,
// 				dom = $('#form-wrap')
// 			if (data) {
// 				$('#connect').hide()
// 				dom.find('input[name="id"]').val(data.id)
// 			} else {
// 				$('#edit-btn').hide()
// 				return false
// 			}
// 			dom.find('input[name="taskId"]').val(data.taskId)
// 			dom.find('input[name="tbsInterfaceURL"]').val(data.tbsInterfaceURL)
// 			dom.find('input[name="appId"]').val(data.appId)
// 			dom.find('input[name="accessKey"]').val(data.accessKey)
// 			dom.find('input[name="callcolumn"]').val(data.callcolumn)
// 			dom.find('input[name="callqueue"]').val(data.callqueue)
// 			dom.find('input[name="address"]').val(data.address)
// 			dom.find('select option[value="'+ data.callnumber +'"]').attr('selected', 'selected')

// 			dataFun(data.createTime)

// 			for (var i = 0; i < time.length; i++) {
// 				if (i == 0) {
// 					var timeVal = dataFun(time[i].callStartTime) + ' - ' + dataFun(time[i].callEndTime)
// 					$('.data-time .time-wrap label:eq('+ i +') input').val(timeVal)
// 				} else {
// 					var htmlNum = $('.data-time .time-wrap label').length;
// 					var html = '<label><input type="text" readonly="readonly" value="'+ dataFun(time[i].callStartTime) + ' - ' + dataFun(time[i].callEndTime) +'" id="test'+ htmlNum +'" placeholder="选择呼叫时间段"/><i class="layui-icon layui-icon-close"></i></label>';
// 					$('.data-time .time-wrap').append(html);
// 					var laydate = ''
// 					layui.use('laydate', function(){
// 						laydate = layui.laydate;
// 						//日期时间范围
// 						laydate.render({
// 							elem: '#test'+ htmlNum +''
// 							,range: ' - '
// 							,type: 'datetime'
// 							,range: true
// 						});
// 					})
// 				}
// 			}
// 			return false
// 		}
// 	},
// 	error: function () {
// 	}
// })

// $(function () {
// 	var laydate = ''
// 	layui.use('laydate', function(){
// 		laydate = layui.laydate;
// 		//日期时间范围
// 		laydate.render({
// 			elem: '#test10'
// 			,range: ' - '
// 			,type: 'datetime'
// 			,range: true
// 		});
// 	})
// 	$('#add-data').click(function () { // 添加呼叫时间段
// 		var htmlNum = $('.data-time .time-wrap label').length;
// 		var html = '<label><input type="text" readonly="readonly" value="" id="test'+ htmlNum +'" placeholder="选择呼叫时间段"/><i class="layui-icon layui-icon-close"></i></label>';
// 		$('.data-time .time-wrap').append(html);
// 		//日期时间范围
// 		laydate.render({
// 			elem: '#test'+ htmlNum +''
// 			,type: 'datetime'
// 			,range: true
// 		});
// 	})
// 	$('.data-time .time-wrap label i').live('click', function (event) {
// 		event.stopPropagation();
// 		$(this).parents('label').remove();
// 	})
// })

// // 判断是否填写内容
// function valueData () {
// 	var callnumber = $('select[name="callnumber"]').val(),
// 		callqueue = $('input[name="callqueue"]').val(),
// 		tbsInterfaceURL = $('input[name="tbsInterfaceURL"]').val(),
// 		appId = $('input[name="appId"]').val(),
// 		accessKey = $('input[name="accessKey"]').val(),
// 		address = $('input[name="address"]').val(),
// 		callcolumn = $('input[name="callcolumn"]').val()
// 	if (callnumber < 0) {
// 		alert('请选择取号数量!')
// 		return false
// 	}
// 	if (!callqueue) {
// 		alert('请输入队列配置!')
// 		return false
// 	}
// 	if (!address) {
// 		alert('请输入配置中心接口!')
// 		return false
// 	}
// 	if (!tbsInterfaceURL) {
// 		alert('请输入土拨鼠请求地址!')
// 		return false
// 	}
// 	if (!appId) {
// 		alert('请输入appID!')
// 		return false
// 	}
// 	if (!accessKey) {
// 		alert('请输入key!')
// 		return false
// 	}
// 	if (!callcolumn) {
// 		alert('请输入坐席呼叫比列!')
// 		return false
// 	}
// 	return true
// }

// 时间转换
// function dataFun (time) {
// 	var timeData = new Date(time),
// 		getMonth = timeData.getMonth() + 1,
// 		getDay = timeData.getDate(),
// 		getHours = timeData.getHours(),
// 		getMinutes = timeData.getMinutes(),
// 		getSeconds = timeData.getSeconds()
// 	if (getMonth < 10) {
// 		getMonth = '0' + getMonth
// 	}
// 	if (getDay < 10) {
// 		getDay = '0' + getDay
// 	}
// 	if (getHours < 10) {
// 		getHours = '0' + getHours
// 	}
// 	if (getMinutes < 10) {
// 		getMinutes = '0' + getMinutes
// 	}
// 	if (getSeconds < 10) {
// 		getSeconds = '0' + getSeconds
// 	}
// 	var timeVal = timeData.getFullYear() + '-' + getMonth + '-' + getDay + ' ' + getHours + ':' + getMinutes + ':' + getSeconds
// 	// console.log(timeVal)
// 	return timeVal;
// }



// new add

// // 获取呼叫中心配置
$.ajax({
	url: apiUrl.locaCallTask,
	type: 'GET',
	dateType:'json',
	success: function (res) {
		res = JSON.parse(res)
		console.log(res)
		if (res.status == 100000) {
			var html = ''
			for (var i = 0; i < res.data.tbscalllist.length; i++) {
				// if (i === 0) {
				// 	$('.fw-adderss-wrap .callqueue:eq('+ i +')').val(res.data.tbscalllist[i].tbsqueue)
				// 	$('.fw-adderss-wrap .tbsInterfaceURL:eq('+ i +')').val(res.data.tbscalllist[i].tbsurl)
				// 	$('.fw-adderss-wrap .callcolumn:eq('+ i +')').val(res.data.tbscalllist[i].tbscallcolumn)
				// 	if (res.data.tbscalllist[i].state === 0) {
				// 		$('.fw-adderss-wrap .adder-list:eq('+ i +') .text-btn-oper').addClass('btn-show')
				// 	} else {
				// 		$('.fw-adderss-wrap .adder-list:eq('+ i +') .text-btn-close').addClass('btn-show')
				// 	}
				// } else {
					html = html + '<div class="adder-list" id="'+ res.data.tbscalllist[i].id +'" state="'+ res.data.tbscalllist[i].state +'">' +
						'<label><span class="span">队列配置：</span><input type="text" name="callqueue" class="callqueue" value="'+ res.data.tbscalllist[i].tbsqueue +'" placeholder="队列配置"/></label>' +
						' <label><span class="span">土拨鼠请求地址：</span><input type="text" name="tbsInterfaceURL" class="tbsInterfaceURL" value="'+ res.data.tbscalllist[i].tbsurl +'" placeholder="土拨鼠请求地址"/></label>' +
						' <label><span class="span">坐席呼叫比列：</span><input type="text" name="callcolumn" class="callcolumn" value="'+ res.data.tbscalllist[i].tbscallcolumn +'" placeholder="坐席呼叫比列"/></label>' +
						'<label>' +
							'<span class="text-ts text-btn-close '+ btnShow(res.data.tbscalllist[i].state) +'" title="点击开启">当前任务关闭中</span> ' +
							'<span class="text-ts text-btn-oper '+ btnHide(res.data.tbscalllist[i].state) +'" title="点击关闭">当前任务开启中</span>' +
						'</label>' +
						'<span class="close-btn" title="删除当前行">x</span>' +
					'</div>'
				// }
			}
			$('.fw-adderss-wrap').append(html)
			
			$('input[name="appId"]').val(res.data.calltask[0].appId)
			$('input[name="accessKey"]').val(res.data.calltask[0].accessKey)
			$('input[name="address"]').val(res.data.calltask[0].address)
		}
	},
	error: function () {
	}
})


// 保存服务认证配置
$('#btn-rz').click(function () {
	var dataObj = {
		appId: $('input[name="appId"]').val(),
		accessKey: $('input[name="accessKey"]').val(),
		address: $('input[name="address"]').val(),
	}
	console.log(dataObj)
	$.ajax({
		url: apiUrl.addTbscallconfig,
		type: 'POST',
		dateType:'json',
		data: dataObj,
		success: function (res) {
			res = JSON.parse(res)
			console.log(res)
			if (res.status == 100000) {
				alert('保存成功！')
				return false
			}
			alert('保存失败！')
		},
		error: function () {
			alert('保存失败！')
		}
	})
})

$('#add-fw-adderss').click(function () {
	var html = '<div class="adder-list">' +
			'<label><span class="span">队列配置：</span><input type="text" name="callqueue" class="callqueue" value="" placeholder="队列配置"/></label>' +
			' <label><span class="span">土拨鼠请求地址：</span><input type="text" name="tbsInterfaceURL" class="tbsInterfaceURL" value="" placeholder="土拨鼠请求地址"/></label>' +
			' <label><span class="span">坐席呼叫比列：</span><input type="text" name="callcolumn" class="callcolumn" value="" placeholder="坐席呼叫比列"/></label>' +
			'<label>' +
				'<span class="text-ts text-btn-close" title="点击开启">当前任务关闭中</span> ' +
				'<span class="text-ts text-btn-oper" title="点击关闭">当前任务开启中</span>' +
			'</label>' +
			'<span class="close-btn" title="删除当前行">x</span>' +
		'</div>'
	$('.fw-adderss-wrap').append(html)
})

$('.fw-adderss-wrap .close-btn').live('click', function () {
	$(this).parents('.adder-list').remove()
})

// 保存配置服务地址
$('#add-adder-btn').click(function () {
	var dataArr = []
	var callqueueArr = [],
		tbsInterfaceURLArr = [],
		callcolumnArr = [],
		stateArr = []
	for (var a = 0; a < $('.fw-adderss-wrap .adder-list').length; a++) {
		var callqueueVal = $('.fw-adderss-wrap .callqueue:eq('+ a +')').val(),
			tbsInterfaceURLVal = $('.fw-adderss-wrap .tbsInterfaceURL:eq('+ a +')').val(),
			callcolumnVal = $('.fw-adderss-wrap .callcolumn:eq('+ a +')').val(),
			stateArr = $('.fw-adderss-wrap .adder-list:eq('+ a +')').attr('state')
		if (!callqueueVal || !tbsInterfaceURLVal || !callcolumnVal) {
		} else {
			var obj = {
				callqueue: callqueueVal,
				tbsInterfaceURL: tbsInterfaceURLVal,
				callcolumn: callcolumnVal,
				state: stateArr
			}
			dataArr.push(obj)
		}
	}
	// if (dataArr.length < 1) {
	// 	alert('请至少填写完一行')
	// }
	var dataObj = {
		list: dataArr
	}
	console.log(dataArr)
	console.log(JSON.stringify(dataArr))
	
	// // 添加土拨鼠获取呼叫数据的地址
	$.ajax({
		url: apiUrl.addTbscallconfig,
		type: 'POST',
		dateType:'json',
		data:{tbsConfig: JSON.stringify(dataArr)},
		success: function (res) {
			res = JSON.parse(res)
			console.log(res)
			if (res.status == 100000) {
				alert('保存成功！')
				location.reload()
				return false
			}
			alert('保存失败！')
		},
		error: function () {
			alert('保存失败！')
		}
	})
})

// 开启任务
$('.text-btn-close').live('click', function () {
	var id = $(this).parents('.adder-list').attr('id')
	selectRw(0, id)
})
// 关闭任务
$('.text-btn-oper').live('click', function () {
	var id = $(this).parents('.adder-list').attr('id')
	selectRw(1, id)
})

function selectRw (state, id) {
	$.ajax({
		url: apiUrl.editTbsCallState,
		type: 'POST',
		dateType:'json',
		data: {
			id: id,
			state: state
		},
		success: function (res) {
			res = JSON.parse(res)
			console.log(res)
			if (res.status == 100000) {
				alert('切换成功！')
				location.reload()
				return false
			}
			alert('切换失败！')
		},
		error: function (err) {
			console.log(err)
			// alert('保存失败！')
		}
	})
}

function btnShow (show) {
	if (show === 1) {
		return 'btn-show'
	}
}
function btnHide (hide) {
	if (hide === 0) {
		return 'btn-show'
	}
}
