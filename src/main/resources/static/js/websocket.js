(function($) {  
    $.websocket = function(options) {  
        var defaults = {  
            domain: top.location.hostname,  
            port:3398,  
            protocol:""  
        };  
        var opts = $.extend(defaults,options);  
        var szServer = "ws://" + opts.domain + ":" + opts.port + "/" + opts.protocol;  
        var socket = null;  
        var bOpen = false;  
        var t1 = 0;   
        var t2 = 0;   
        var messageevent = {  
            onInit:function(){  
                if(!("WebSocket" in window) && !("MozWebSocket" in window)){
                	  
                    return false;  
                }  
                if(("MozWebSocket" in window)){
                	try{
                    socket = new MozWebSocket(szServer);}catch(e){
                	}
                }else{
                	try{
                    socket = new WebSocket(szServer);}catch(e){
                	}
                }  
                if(opts.onInit){  
                    opts.onInit();  
                }  
            },  
            onOpen:function(event){  
                bOpen = true;  
                if(opts.onOpen){  
                    opts.onOpen(event);  
                }  
            },  
            onSend:function(msg){  
                t1 = new Date().getTime();   
                if(opts.onSend){  
                    opts.onSend(msg);  
                }  
                socket.send(msg);  
            },  
            onMessage:function(msg){  
                t2 = new Date().getTime();   
                if(opts.onMessage){  
                    opts.onMessage(msg.data,t2 - t1);  
                }  
            },  
            onError:function(event){  
                if(opts.onError){  
                    opts.onError(event);  
                }  
            },  
            onClose:function(event){  
                if(opts.onClose){  
                    opts.onClose(event);  
                }  
                if(socket.close() != null){  
                    socket = null;  
                }  
            }  
        }  
  
        messageevent.onInit();
        if(socket != null){
	        socket.onopen = messageevent.onOpen;  
	        socket.onmessage = messageevent.onMessage;  
	        socket.onerror = messageevent.onError;  
	        socket.onclose = messageevent.onClose;  
        }
        this.send = function(pData){  
            if(bOpen == false){  
                return false;  
            }  
            messageevent.onSend(pData);  
            return true;  
        }  
        this.close = function(){  
            messageevent.onClose();  
        }  
        return this;  
    };  
})(jQuery);