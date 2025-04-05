package com.csp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DEBUG: WithdrawServlet - Starting processing");

        String amount = request.getParameter("amount");
        String accountNumber = request.getParameter("accountNumber");
        String type = "withdrawal";

        System.out.println("DEBUG: WithdrawServlet - Received amount = " + amount);
        System.out.println("DEBUG: WithdrawServlet - Received type = " + type);
        System.out.println("DEBUG: WithdrawServlet - Received accountNumber = " + accountNumber);

        if (amount == null || accountNumber == null || Double.parseDouble(amount) <= 0) {
            System.out.println("DEBUG: WithdrawServlet - Validation failed, redirecting to withdraw.html");
            response.getWriter().write("<script>alert('Withdrawal data is missing or invalid. Please try again.'); window.location.href='withdraw.html';</script>");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("amount", amount);
        session.setAttribute("type", type);
        session.setAttribute("accountNumber", accountNumber);

        System.out.println("DEBUG: WithdrawServlet - Redirecting to enterpin.html");
        response.sendRedirect("enterpin.html");
    }
}