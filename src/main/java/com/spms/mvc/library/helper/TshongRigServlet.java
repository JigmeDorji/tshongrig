package com.spms.mvc.library.helper;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TshongRigServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        out.println("Helo,\n " +
                " We request you" +
                " to clear your due before " +
                "using this" +
                " system to your clients." +
                "\n" +
                "Thanks in advance");
        out.close();
    }
}
