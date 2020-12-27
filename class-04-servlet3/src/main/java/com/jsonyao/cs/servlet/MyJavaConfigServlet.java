package com.jsonyao.cs.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 控制台乱码解决: vm = -Dfile.encoding=UTF-8
@WebServlet("/myJavaConfigServlet")
public class MyJavaConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Test MyJavaConfigServlet~~~");
    }
}
