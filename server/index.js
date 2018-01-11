var ws = require("nodejs-websocket");
var server = ws.createServer(function(conn){
  console.log('new conneciton');
  //监听text事件，每当收到文本时触发
  conn.on("text",function(str){
    broadcast(server,str);
  });
  //当任何一端关闭连接时触发，这里就是在控制台输出connection closed
  conn.on("close",function(code,reason){
    console.log('connection closed');
  });
  //检测错误，防止崩溃
  conn.on("error",function(err){
	  console.log("发生错误，但是没事");
  });
}).listen(8888);
//注意这里的listen监听是刚才开通的那个服务器的端口，客户端将消息发送到这里处理
  
function broadcast(server, msg) {
  //server.connections是一个数组，包含所有连接进来的客户端
  server.connections.forEach(function (conn) {
    //connection.sendText方法可以发送指定的内容到客户端，传入一个字符串
    //这里为遍历每一个客户端为其发送内容
    conn.sendText(msg);
  });
}
