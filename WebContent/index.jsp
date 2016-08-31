<%@page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF8"%>
<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta charset="utf-8">
        <title>WebSocket ChatRoom Test</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="Json Liang">
        <script src="./js/jquery-1.10.2.min.js"></script>
        <!-- Le styles -->
        <link href="./css/bootstrap.css" rel="stylesheet">
        <style type="text/css">
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #f5f5f5;
            }

            .form-signin {
                max-width: 300px;
                padding: 19px 29px 29px;
                margin: 0 auto 20px;
                background-color: #fff;
                border: 1px solid #e5e5e5;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            }

            .form-signin .form-signin-heading,.form-signin .checkbox {
                margin-bottom: 10px;
            }

            .form-signin input[type="text"],.form-signin input[type="password"] {
                font-size: 16px;
                height: auto;
                margin-bottom: 15px;
                padding: 7px 9px;
            }

            #chatroom {
                font-size: 16px;
                height: 40px;
                line-height: 40px;
                width: 300px;
            }

            .received {
                width: 160px;
                font-size: 10px;
            }
        </style>
        <link href="./css/bootstrap-responsive.css" rel="stylesheet">
        <script>
            var serviceUri ='ws://'+'<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>'+'/chat/';
            var timeSocketUri ='ws://'+'<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>'+'/time/';
            var webSocket;
            var timeSocket;
           // var serviceUri ="ws:// 192.168.0.2:8080/WebSocketDemo/chat/";
            var $nickName;
            var $message;
            var $chatWindow;
            var room = '';
            var $time;
            
            function onMessageReceived(evt){
                var msg = JSON.parse(evt.data);
                console.log(JSON.stringify(msg));
                console.log("message : " + msg.message );
                var $messageLine = $('<tr><td class = "received">' + msg.received
                                   + '</td><td class = "user label label-info">' + msg.sender
                                   + '</td><td class = "message badge">' + msg.message
                                   + '</td></tr>');
                $chatWindow.append($messageLine);
            }
            
            function sendMessage(){
                var msg = '{"message":"'+ $message.val() + '","sender":"'
                          + $nickName.val() + '","received":""}';
                webSocket.send(msg);
                $message.val('').focus();
            }
            
            function connectToChatserver(){
                room = $('#chatroom option:selected').val();
                var nickname_id = $('#nickname').val();
                webSocket = new WebSocket(serviceUri + room);
                webSocket.onmessage = onMessageReceived;
                webSocket.onopen = enterRoom;
                timeSocket = new WebSocket(timeSocketUri + nickname_id);
                timeSocket.onmessage = onTime;
               // enterRoom();
            }
            
            function leaveRoom(){
                 var msg = '{"message":"'+ $nickName.val() +'已經離開","sender":"system","received":""}';
                webSocket.send(msg);
                webSocket.close();
                $chatWindow.empty();
                $('.chat-wrapper').hide();
                $('.chat-signin').show();
                $nickName.focus();
            }
            
            function enterRoom(){
            	var msg = '{"message":"'+ $nickName.val() +'加入","sender":"system","received":""}';
                webSocket.send(msg);
            }
            
            function onTime(evt){
            	var msg = evt.data;
            	//console.log(msg);
            	$time.html(msg);
            }
            $(document).ready(function(){
                $nickName = $('#nickname');
                $message = $('#message');
                $chatWindow = $('#response');
                $time = $("#time");
                $('.chat-wrapper').hide();
                $nickName.focus();
                
                $('#enterRoom').click(function(evt){
                    evt.preventDefault();
                    connectToChatserver();
                    $('.chat-wrapper h2').text('Chat #' + $nickName.val() + "@" + room);
                    $('.chat-signin').hide();
                    $('.chat-wrapper').show();
                    $message.focus();
                });
                
                $('#do-chat').submit(function(evt){
                    evt.preventDefault();
                    sendMessage();
                });
                
                $('#leave-room').click(function(){
                    leaveRoom();
                });
            });
        </script>
    </head>
    <body>
        <div class="container chat-signin">
            <form class="form-signin">
                <h2 class="form-signin-heading">聊天室登入</h2>
                <label for="nickname">暱稱</label><input type="text" class="input-block-level" placeholder="暱稱" id="nickname">
                <div class="btn-group">
                    <label for="chatroom">聊天室</label>
                    <select size="1" id="chatroom">
                        <option>arduino</option>
                        <option>java</option>
                        <option>groovy</option>
                        <option>scala</option>
                    </select>
                </div>
                <button class="btn btn-large btn-primary" type="submit" id="enterRoom">登入</button>
            </form>
        </div>
        
        <div class="container chat-wrapper">
            <form id="do-chat">
                <h2 class="alert alert-success"></h2>
                <div id="time" class="alert alert-success"></div>
                <table id="response" class="table table-bordered"></table>
                
                <fieldset>
                    <legend>輸入你的訊息...</legend>
                    <div class="controls">
                        <input type="text" class="input-block-level" placeholder="Your message..." id="message" style="height:60px"/>
						<input type="submit" class="btn btn-large btn-block btn-primary" value="Send message" />
						<button class="btn btn-large btn-block" type="button" id="leave-room">離開聊天室</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>
