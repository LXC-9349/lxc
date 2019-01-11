var cti = window.cti = {
	jsocket: null,
	reconnect_interval: 6000,
	keepalive_interval: 30000,
	keepalive_timer: null, // 心跳定时器
	reconnect_timer: null, // 重连定时器
	workerId: '', // 工号
	proxyServer: '', // 代理
	extNumber: '',//分机号
	corpIdentify: 'yunhus.com',
	connected: false,//表示是否连接到CTI服务了
	reconnected: false, // 是否是重连
	working: false,
	phoneOn: false,
	time: 0,
	timeInterval: null,
	time3: 0,
	timeInterval3: null,
	memberTime: [0, 0],
	memberTimeInterval: [],
	lastCallMemberId: null,
	lastCallPhone: null,
	phone: null,
	workStatus: 1,
	callingCount: 0,
	isConnected: false,
	calling: false,//是否在通话中
	loginStatus: false,
	callOutNumber: '',//呼出号码
	//重连CTI服务
	reconnect: function () {
		if (!cti.connected) {
			top.cti.connect(true);
		}
	},
	//心跳包
	keepAlive: function () {
		top.cti.sendSimpleOrder(99, cti.extNumber, null, null);
	},

	print: function (log) {
		$("#log").append(log);
		$("#log")[0].scrollTop = $("#log")[0].scrollHeight;
	},

	// 提醒内容
	tipsFun: function (msg) {
		$("#tip-wrap").append(msg);
		$("#tip-wrap")[0].scrollTop = $("#tip-wrap")[0].scrollHeight;
	},

	dateFun: function () {
		var time = new Date(),
			timeData = time.getFullYear() + '-' + (time.getMonth() - 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
		return timeData
	},

	// 连接处理
	connect: function (reconnected) {
		if (!!!cti.proxyServer || !!!cti.extNumber) return;
		cti.reconnected = reconnected;
		if (cti.reconnected) console.log("重连reconnect------");
		if (cti.jsocket != null) {
			cti.jsocket.close();
		}
		clearInterval(cti.reconnect_timer);
		cti.reconnect_timer = setInterval(cti.reconnect, 10 * 1000);
		cti.jsocket = jQuery.websocket({
			domain: cti.proxyServer,
			port: 345,
			protocol: 'websocket',

			// 服务器连接 - 对应connect方法
			onOpen: function (event) {
				cti.print("连接成功connect<br/>");
				// 发送连接分机号
				cti.sendCtiIP();
			},

			// 出错提示 - 对应onServerError方法
			onError: function (event) {
				console.log(event);
				cti.onServerError(event);
			},

			// 服务器线下监听 - 对应onServerEvent方法
			onMessage: function (msg) {
				if (msg) {
					var json = JSON.parse(msg);
					cti.print("接收信息Received：" + msg + "<br/>");
					cti.onServerEvent(json);
				}
			},
			onClose: function (event) {
				console.log('关闭连接')
				cti.print(event + "<br/>");
				cti.onServerError(event);
				cti.connected = false;
				clearInterval(cti.keepalive_timer);
				clearInterval(cti.reconnect_timer);
				cti.reconnect_timer = setInterval(cti.reconnect, 6 * 1000);
			}
		});
		return true;
	},

	// 发送cti服务器ip
	sendCtiIP: function () {
		var msg = "CONNECTING#" + cti.extNumber + "#" + cti.workerId + "#" + cti.corpIdentify;
		var text = "连接信息CONNECTING#分机号" + cti.extNumber + "#工号" + cti.workerId + "#域名" + cti.corpIdentify;
		if (cti.reconnected) {
			msg += "#R";
		}
		console.log('链接成功,发送内容')
		cti.jsocket.send(msg);
		cti.print("发送Send：" + text + "<br/>");
	},

	// 发送简单命令
	sendSimpleOrder: function (order, line, file, GType) {
		if (!order || !line) return false;
		var msg = '{"Order":' + order + ',"SlicNo":"' + line + '"';
		var text = '{"状态Order":' + order + ',"分机号SlicNo":"' + line + '"';
		if (file != null) {
			msg += ',"File":"' + file + '"';
		}
		if (GType != null) {
			msg += ',"GType":"' + GType + '"';
		}
		msg += '}';

		if ($('#tip-title').is(':hidden')) {
			$('#tip-title').show();
		}
		cti.tipsFun('<p class="p">开启: 开启心跳<span>    ' + cti.dateFun() + '</span></p>')
		console.log('<p class="p">开启: 开启心跳</p>')
		cti.jsocket.send(msg);
		cti.print("发送Send：" + text + "<br/>");
	},

	/**
	order 命令代号（必填）
	workerId 当前工号
	slicNo 坐席号
	phoneSkill 技能
	callPhone 电话号码
	memberId 会员ID
	dClass 成熟度
	bankInfo 银行信息 格式：{
        "APayType": 1, 
        "BPayMode": 1, 
        "CMoney": 1, 
        "DName": "test",
		"EBank": 25640, 
        "FMobile": "13632793761", 
        "GOrderId": "123005441"
    }
	*/
	sendOrder: function (order, workerId, slicNo, phoneSkill, callPhone, memberId, dClass, bankInfo) {
		if (!order || !slicNo) return false;
		var msg = '{';
		msg += '"Order":' + order;
		if (workerId != null) {
			msg += ',"WorkerId":"' + workerId + '"';
		}
		if (slicNo != null) {
			msg += ',"SlicNo":"' + slicNo + '"';
		}
		if (phoneSkill != null) {
			msg += ',"PhoneSkill":"' + phoneSkill + '"';
		}
		if (callPhone != null) {
			msg += ',"CallPhone":"' + callPhone + '"';
		}
		if (memberId != null) {
			msg += ',"MemberId":"' + memberId + '"';
		}
		if (dClass != null) {
			msg += ',"DClass":"' + dClass + '"';
		}
		if (bankInfo != null) {
			msg += ',"BankInfo":' + bankInfo;
		}
		if (order == 37) {//打电话时添加呼出号码参数  默认为空字符串
			msg += ',"Calleephone":"' + this.callOutNumber + '"';
		}
		msg += '}';
		cti.jsocket.send(msg);
		cti.print("发送Send：" + msg + "<br/>");
	},

	// 服务器推送信息
	onServerEvent: function (data) {
		console.log(data)
		var order = data.Order;
		if (order == null) return;//不带order的消息都不处理
		var result = (data.Result == 1 ? true : false);
		switch (parseInt(order, 10)) {
			case 98:
				if (!cti.connected) {
					top.cti.sendOrder(2, cti.workerId, cti.extNumber, cti.workerReceiveTel, null, null, null, null);
				}
				break;
			case 999:
				var info = '分机号已被使用！<br />分机号：' + data.SlicNo + '<br />使用人：' + data.WorkerId;
				top.iframeDialog.alert(info, null, null, function () {
					//
				}, null, true);
				break;
			case 1000:
				top.iframeDialog.alert("连接CTI服务失败，请稍候重试！");
				break;
			case 0://振铃（有振铃要有个弹窗提示----弹屏什么的）
				if (data.SlicNo == cti.extNumber) {
					cti.print("来电弹屏：" + data.CallPhone + "<br/>");
					cti.changeWorkStatus(0);
					cti.changeMemberStatus(data.CallPhone, 0, data.CalleePhone);
				}
				break;
			case 1://退出登陆
				if (result == true) {
					$('#citPause,#ctiListenLink,#ctiChangeLineLink').unbind('mousedown').unbind('mouseup').unbind('mouseover').unbind('mouseout');
					$('#citPause').removeClass('ctiButton').unbind('click');
					$('#ctiListenLink,#ctiChangeLineLink').unbind('click');
					$('#citPause span').text('未连接');
				}
				break;
			case 2://登陆
				if (result == true) {
					clearInterval(cti.reconnect_timer);
					cti.connected = true;
					//开启心跳定时器
					cti.keepalive_timer = setInterval(cti.keepAlive, cti.keepalive_interval);
					console.log('链接成功,把重连定时器清除')
					$('#citPause span').text('已连接');
					cti.isConnected = true;
					cti.loginStatus = true;
				} else {
					cti.connected = false;
					console.log('连接呼叫中心失败，请与支持部联系！');
					if (cti.jsocket != null) {
						cti.jsocket.close();
					} else {
						setTimeout(cti.reconnect, cti.reconnect_interval);
					}
				}

				break;
			case 10://摘机
				if (result == true) {
					cti.phoneOn = true;
					cti.calling = false;
					cti.callingCount++;
					cti.changeWorkStatus(2);
					cti.changeMemberStatus(data.CallPhone, 1);
				} else {
					alert('摘机失败');
				}
				break;
			case 28://暂停服务
				if (result == true) {
					cti.working = false;
					cti.calling = false;
					cti.changeWorkStatus(1);
					$('#citPause span').text('开始服务');
					$('#citPause span').addClass("spanCursor");
				} else {
					//alert('暂停服务失败');
				}
				break;
			case 29://继续服务
				if (result == true) {
					cti.working = true;
					cti.changeWorkStatus(1);
					$('#citPause span').text('暂停服务');
					$('#citPause span').addClass("spanCursor");
				} else {
					alert('继续服务失败');
				}
				break;
			case 38://通话开始
				if (result == true) {
					cti.calling = true;
					// 改变应该状态
					cti.changeMemberStatus(data.CallPhone, 2, data.MemberId);
					var intoCache = false;
					cti.lastCallMemberId = data.MemberId;
					cti.lastCallPhone = data.CallPhone;
					if (cti.workStatus != 4) {
						cti.changeWorkStatus(4);
						cti.timeInterval ? clearInterval(cti.timeInterval) : null;
						cti.timeInterval = setInterval(function () {
							$('#lcdSpan').html(formatTime(cti.time += 1));
						}, 1000);
					}
				} else {
					cti.calling = false;
					if ($.isFunction(cti.onFinishWait)) {
						cti.onFinishWait(false);
						cti.onFinishWait = null;
					}
					alert('通话失败');
				}
				break;
			case 39://用户挂机
				if (result == true) {
					cti.calling = false;
					if (cti.callingCount > 0) cti.callingCount--;
					cti.changeMemberStatus(data.CallPhone, 3);
				} else {
					alert('挂机失败');
				}
				break;
		}
	},

	// 出错处理，消息格式待定
	onServerError: function (errorMessage) {
		cti.isConnected = false;
		$('#citPause span').text('未连接');
	},

	// 改变员工状态 （不需要修改）
	changeWorkStatus: function (status) {
		if (cti.workStatus != status) {
			if (status == 0) {
				$('#workStatus img').attr('src', 'static/images/ring.gif');
				$('#workStatus img').attr('title', '有来电');
				$('#workStatus span').html('有来电');
			} else if (status == 1) {
				$('#workStatus img').attr('src', 'static/images/w_disconnection.gif');
				$('#workStatus span').html('未摘机');
				$('#workStatus img').attr('title', '未摘机');
			} else if (status == 2) {
				$('#workStatus img').attr('src', 'static/images/w_connection.gif');
				$('#workStatus span').html('已摘机');
				$('#workStatus img').attr('title', '已摘机');
			} else if (status == 3) {
				//alert('正在拨号');
				$('#workStatus img').attr('src', 'static/images/w_dialing.gif');
				$('#workStatus span').html('正在拨号');
				$('#workStatus img').attr('title', '正在拨号');
				$('#lcdSpan').html('00');
			} else if (status == 4) {
				$('#workStatus img').attr('src', 'static/images/w_calling.gif');
				$('#workStatus span').html('通话中');
				$('#workStatus img').attr('title', '通话中');
			}
			cti.workStatus = status;
		}
	},

	// 改变会员状态 （不需要修改）
	changeMemberStatus: function (phoneNum, status, callInLine) {
		// 原有的获取真实手机号码好像有问题？？？
		var realPhone = cti.getRealPhone(phoneNum);
		var memberStatus = $('#memberStatus_' + realPhone);
		cti.phone = realPhone;
		if (!memberStatus.html()) {
			memberStatus = $('td.memberStatus[status=0]').eq(0);
			memberStatus.attr('id', 'memberStatus_' + realPhone);
			if (memberStatus.next('td').attr('class') != 'callInfo') {
				memberStatus.after('<td class="callInfo"></td>');
			} else if (status < 2) {
				memberStatus.next('td.callInfo').html('').removeAttr('width');
			}
		}
		memberStatus.show();
		var callInfoTD = memberStatus.next('td.callInfo');
		if (status == 0) {
			memberStatus.attr('status', 0);
			memberStatus.find('img').attr('src', 'static/images/m_dialing.gif');
			memberStatus.find('span').html('正在拨号');
			callInfoTD.html('');
			var str = '<nobr>来电：<a id="phoneInQuery" href="/sale/query-allot.do?dp=1&phone=' + phoneNum + '&callInLine=' + callInLine + '">' + phoneNum + '</a></nobr> <nobr>线路：' + callInLine + '</nobr>';;
			callInfoTD.attr('width', 80).html(str);
			common.initIframeTabsLink($('#phoneInQuery'));
		} else if (status == 1) {
			memberStatus.attr('status', 1);
			callInfoTD.html('');
			memberStatus.find('img').attr('src', 'static/images/m_ring.gif');
			memberStatus.find('span').html('正在振铃');
			if (cti.callingCount < 2) {
				$('td.memberStatus[status=0]').next('td.callInfo').remove();
				$('td.memberStatus[status=0]').hide();
			}
		} else if (status == 2) {
			if (memberStatus.attr('status') == status) {
				return;//当状态未改变时，不用处理，直接返回。
			}
			var p = realPhone.replace(realPhone.substring(3, 7), '****');
			memberStatus.attr('status', 2);
			memberStatus.find('img').attr('src', 'static/images/m_calling.gif');
			memberStatus.find('span').html('通话中');
			var timeIntervalIndex = cti.memberTime[0] == 0 ? 0 : 1;
			var str = (callInfoTD.html() ? callInfoTD.html() : p) + '<br /><span class="timeSpan" id="timeSpan_' + realPhone + '" timeIntervalIndex="' + timeIntervalIndex + '">00</span>'
			callInfoTD.attr('width', 80).html(str);
			var html = '';
			cti.memberTimeInterval[timeIntervalIndex] = setInterval(function () {
				callInfoTD.find('span.timeSpan').html(formatTime(cti.memberTime[timeIntervalIndex] += 1));
			}, 1000);
			if (cti.callingCount == 2) {
				cti.timeInterval3 = setInterval(function () {
					cti.time3++;
					if (cti.time3 == 540) {
						iframeDialog.alert("三方通话时间已经超过9分钟了。", 300, 120);
					}
				}, 1000);
			}
		} else if (status == 3) {
			memberStatus.attr('status', 0);
			memberStatus.find('img').attr('src', 'static/images/m_disconnection.gif');
			memberStatus.find('span').html('已挂机');
			var timeIntervalIndex = memberStatus.next('td.callInfo').find('span.timeSpan').attr('timeIntervalIndex');
			if (cti.memberTimeInterval[timeIntervalIndex]) {
				clearInterval(cti.memberTimeInterval[timeIntervalIndex]);
				cti.memberTimeInterval[timeIntervalIndex] = null;
				cti.memberTime[timeIntervalIndex] = 0;
			}
			if (cti.timeInterval3) {
				clearInterval(cti.timeInterval3);
				cti.timeInterval3 = null;
				cti.time3 = 0;
			}
		}
	},

	setWorkerId: function (workerId) {
		cti.workerId = workerId;
	},

	setExtNumber: function (slicNo) {
		cti.extNumber = slicNo;
	},

	setProxyServer: function (proxyServer) {
		cti.proxyServer = proxyServer;
	},

	// 退出
	logout: function () {
		if (!this.connected) return;
		cti.sendOrder(1, this.workerId, this.extNumber, null, null, null, null, null);
	},

	// 开启服务
	work: function () {
		if (!this.connected) return;
		cti.sendOrder(29, this.workerId, this.extNumber, null, null, null, null, null);
	},

	// 获取真实的手机号码（要转成js实现，获取解析完了传过来）
	getRealPhone: function (s) {
		var p = s;
		if (s.length > 15) {
			p = s.replace('x', '');
			//p = $.ajax({url: "/getRealPhone.do?num="+p,async: false,cache: false}).responseText;
		}
		return p.replace(/^0+/, '');
	},

	// 断开连接
	disconnect: function () {
		this.sendOnHook();
		cti.jsocket.close();
	}
};