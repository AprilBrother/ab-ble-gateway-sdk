package demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Parse extends HttpServlet {
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public Parse() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("doPost");
		StringBuffer info = new java.lang.StringBuffer();
		InputStream in = null;
		try {
			in = request.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (info != null && info.toString() != null
				&& !info.toString().equals("")) {
			// System.out.println(info.toString());
			String[] mySplit = info.toString().split("0D0A");
			if (mySplit.length < 1)
				return;
			String myHead = mySplit[0];
			byte[] myByte = hexStringToBytes(myHead);
			String head = new String(myByte);
			// System.out.println(head);
			String content = "";
			if (mySplit.length < 3)
				return;
			for (int i = 2; i < mySplit.length; i++) {
				if (i != mySplit.length - 1) {
					content = content + "\'" + mySplit[i] + "\'" + "," + "\r\n";
				} else {
					content = content + "\'" + mySplit[i] + "\'";
					content = "[" + content + "]";
				}

			}
			// System.out.println(content);
			String mParse = head + "\r\n" + "\r\n" + content;
			System.out.println(mParse);
			System.out.println("-------------------------------------------");
		}
	}

	public void init() throws ServletException {
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
