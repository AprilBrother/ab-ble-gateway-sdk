package demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ParseUtils {

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * @param in
	 * @return
	 */
	public static String parse(InputStream in) {
		StringBuffer info = readInputSrtreamToHexString(in);
		if (info == null || info.toString().equals(""))
			return null;
		String mParse;
		mParse = ParseInfo(info);
		return mParse;
	}

	/**
	 * parse info
	 * 
	 * 解析info信息
	 * 
	 * info format : head info + two newline + content info
	 * 
	 * 
	 * @param info
	 * @return
	 */
	private static String ParseInfo(StringBuffer info) {

		String[] mySplit = info.toString().split("0D0A");
		if (mySplit.length < 1)
			return null;
		// parse head
		String head = parseHead(mySplit);
		// parse content
		String content = parseContent(mySplit);
		// parse result is head + newline +newline +content
		String parseResult = head + "\r\n" + "\r\n" + content;
		return parseResult;
	}

	/**
	 * parse content
	 * 
	 * split[2]...spilt[last] is content
	 * 
	 * combination every content to JsonArray
	 * 
	 * 第三条到最后是cotent
	 * 
	 * 组合content中所有信息 以JsonArray格式返回
	 * 
	 * 
	 * @param mySplit
	 * @return
	 */
	private static String parseContent(String[] mySplit) {
		String content = "";
		if (mySplit.length < 3)
			return null;
		for (int i = 2; i < mySplit.length; i++) {
			if (i != mySplit.length - 1) {
				content = content + "\'" + mySplit[i] + "\'" + "," + "\r\n";
			} else {
				content = content + "\'" + mySplit[i] + "\'";
				content = "[" + content + "]";
			}
		}
		return content;
	}

	/**
	 * parse head
	 * 
	 * split[0] is head
	 * 
	 * change HexString to String the result is head
	 * 
	 * 通过0A0D分割后的第一个数据 是head
	 * 
	 * 讲HexString转为String 就是head
	 * 
	 * @param mySplit
	 * @return
	 */
	private static String parseHead(String[] mySplit) {
		String myHead = mySplit[0];
		byte[] myByte = hexStringToBytes(myHead);
		String head = new String(myByte);
		return head;
	}

	/**
	 * 
	 * read InputStream to HexString
	 * 
	 * 读取输入流 用string接收 接受为为HexString
	 * 
	 * @param in
	 * @return
	 */
	private static StringBuffer readInputSrtreamToHexString(InputStream in) {
		StringBuffer info = new java.lang.StringBuffer();
		BufferedInputStream buf = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int iRead;
		try {
			while ((iRead = buf.read(buffer)) != -1) {
				info.append(str2HexStr(buffer).subSequence(0, iRead * 2));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	public static String str2HexStr(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
