

SecurityCorsConfiguration 解决跨于的filter
SessionFilter 解决保持会话的filter
示例界面：static/ajax.html
模拟后台服务类：ServiceApi


示例启动类:DemoApplication
访问 :http://localhost:9090/ajax.html
操作：1、点击login按钮，表示登录
     2、登录后点击hello按钮，表示做业务，这时候会话一直保持
     3、点击注销按钮，然后点击hello，发现会话为null了
      （注意：注销的时候之所以能弹出会话id是因为SessionFilter在调用logout方法的之前设置了head头参数，但走完logout方法其实后台已经没有会话了）
     4、再次点击login按钮，登录发现重新创建了一个会话