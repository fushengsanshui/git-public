import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 聊天系统服务器程序
 */
 //主要功能是监听客户端的连接请求，并为每个连接创建一个独立的线程来处理客户端的通信
public class AppServer extends Thread
{
	private ServerSocket serverSocket;

	private ServerFrame sFrame;

	private static Vector userOnline = new Vector(1, 1);
	
	private static Vector v = new Vector(1, 1);

	/**
	 * 创建服务器 启动服务监听1001端口
	 */
	public AppServer()
	{
		sFrame = new ServerFrame();
		try
		{
			serverSocket = new ServerSocket(1001);
			InetAddress address = InetAddress.getLocalHost();
			sFrame.txtServerName.setText(address.getHostName());
			sFrame.txtIP.setText(address.getHostAddress());
			sFrame.txtPort.setText("1001");
		} catch (IOException e)
		{
			fail(e, "不能启动服务！");
		}
		sFrame.txtStatus.setText("已启动...");
		this.start(); // 启动线程
	}

	/**
	 * 退出服务器
	 * 
	 * @param e
	 *                异常
	 * @param str
	 *                退出信息
	 */
	public static void fail(Exception e, String str)
	{
		System.out.println(str + " 。" + e);
	}

	/**
	 * 监听客户的请求，当有用户请求时创建 Connection线程
	 */
	public void run()
	{
		try
		{
			while (true)
			{
				Socket client = serverSocket.accept();
				new Connection(sFrame, client, userOnline, v); // 支持多线程
			}
		} catch (IOException e)
		{
			fail(e, "不能监听！");
		}
	}

	/** 
	 * 启动服务器
	 */
	public static void main(String args[])
	{
		new AppServer();
	}
}
