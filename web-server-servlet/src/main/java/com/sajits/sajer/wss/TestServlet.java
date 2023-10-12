package com.sajits.sajer.wss;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sajits.sajer.core.Test;

public class TestServlet  extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Test ts = new Test();

		resp.setStatus(200);
		resp.getOutputStream().println(ts.test());
	}
}
