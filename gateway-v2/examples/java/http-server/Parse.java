package demo;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Parse extends HttpServlet {

	public Parse() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream in = null;
		try {
			in = request.getInputStream();
			String parseReslut = ParseUtils.parse(in);
			System.out.println(parseReslut);
			System.out.println("-----------");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void init() throws ServletException {
	}
}
