package com.bjpowernode.p2p.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016110200787550";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcZYkv30BnA27qiIJ4OU8q5L5X77EAWCNSarLwY2kDl/FC++Az5Ymd6hYBoo+ljnz5nf5iB1vix1LP3DweIkEWCnoRVj+OO5b+nrKkabi6wmYSgYJPmhkpVzQ+exykdzzkurkCUR0cN1cYaaIY0zVYmwfVNBHgWoy+v4Gj8CWuwXFy+DHg2eBcPZcPtd/RWKwrRRNYK2nGIOBhmljHTKO9+/+myA1O6F7dPCjbC0ERbMCc+R5B8jjOnNTTYep+ACpyeXaXAfOew6uTN1/7LCAnB+S5XQTk7XxzwLDYPhOlx28gHIVohwzQy0rykFD9tB3lgksFPXvAE26tRPICR9blAgMBAAECggEAbuDxfPYbU600cqRrU07WGMPpSrDNXUAfcqvgxtrDtd+AFYw67LAce89c4LXef3JgmhvwIJG1DDdiN6mdGYQRfs3YsvfhwTXwG/5pCnVFmWp/MTmOKF9I3W1MxctEiDlc+5CB3+mf6MQ3RECHwm/jDzmCOR5cWiH4TLe68n4sSRMSK2srrboM57ZS6XY4M5la5+TWCTVzZ4ci5aaXSupXE+jIlxp6bdmxR4dEgX12RqcKfcpXH9AO2T3JHSgundsQwyhdBfOVqdatx3nzGoMDiWcp8lfSY1zpoDD4nQLnsQF/qD5fTQI0vAIAuNzM8MishusTSEiFjjoBZMVU0q5voQKBgQDQANIXPXPR3m5BS5omcUF/TbyC3Eo55HX8cy7JxkWE+V41Ojdmd7s2oGM9Z61haXoYelZhcYmD90cN9Dbewt0qzHQtNXw73RtJPhhENI6pCmIsyR6A06t2JvsDQXRUlWmRgbckpi8LRePfskxHhIzYVFP4IWrk3bT/K6CLSCeaqQKBgQDAfDUx22KED12Q6LQSI3rnlvt+LLqv3MLIqimc00E3E2J5s4Ii1fxRZPX9MYOjiz7sDhc5so5eE8pIxpOSnul2WX1IYF4Q8M2J3TsKL6cL5pOBv0ZUiKAN4uHfYuZZYb1Apab2kUP6vgwJzmBKycp2sFoTYH+hO6P0Tcv/JLub3QKBgDjKEJIhyA5joUyYfJTEVTKRocdEp8tYkdIEDKEwTei5QLxdkaSbSfTnI1EvjknLR3eJZy4hln7+M1se8yAsqb+2pH13XdzmSD3eAzgT9WM3n4hEvNxFbknK6LuB0mYJiRIeDUenS67TPRzlDCh2ixHBLE/Rp+KmmXUVjzaX06BJAoGAVJ9Gk6Crcvt+WxKy97Z5dg8+iX4IpQAc8jM9ECEf7qKrDG4KrF3/C5mjO6jlAqVZCFY5h7gkNt/kcpI6lp8ebFVzarpTIId9FTsZlaTVf7ni54u707YtB23Td233bxw1I5KwEH5UsIuUp0r/smF8jSmCsDogYjXiIqmBIa54fFECgYBPq1R0tcZtGpovdE6kfmeCz3Hna9VMMYqr3vayOS+mwt8o5jDv1tXCInyLBclAXJY6aw5ldK3vNx6Y7c02EqacCj+sERLt4T1ZNNNRk9hMADZcu1uOvuNxLnYISIPUeBAMK1weqCH0y+mqsvO4xyvxbh9p335nc3XezDo0BqrAqw==";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";

    public static String notify_url = "http://localhost:8080/p2p/loan/notifyCall";

	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
//	public static String return_url = "http://127.0.0.1:8090/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    //本地web工程的url回调地址
    public static String return_url = "http://localhost:8080/p2p/loan/returnCall";

	// 请求网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiWNUxsp8Aeh53/O6Ho4YxdWEK4ebnUDM5y2FL80g6Ou597QDoJsyC5ogYOEezsZRhVBvka/Lw7c+HChlOE9HsuG0oR7x+wR3d4cyiejayI1mvUJ//WUx4MjhU4QOGOOMTCg7kpSwUTMwR69YC5KblILUsQjAUKDw+v5klulS1Tg/mNFqpWqU/gLRcu7eVlqNBaWuf4gaKsRd2ffinJlbRu+sZ8A4gNRV+MxtL0kkmC5tz1dnbphCVfmg+u4Bj7NiD5UiOsBMxcQdqBlPEzzreoFwEs4q7NI34XmPGWtgtGtA0qb7Z4S70Bwq2wVcvP5fJmKYpJLpf/xFmpXhQNx9QQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
