package com.csp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/balance-enquiry")
public class BalanceEnquiryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            response.getWriter().write("<script>alert('Invalid Account Number'); window.location.href='balance_enquiry.html';</script>");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("accountNumber", accountNumber);
        session.setAttribute("type", "balance");

        // Debugging output
        System.out.println("===== BALANCE ENQUIRY SESSION DEBUG =====");
        System.out.println("Session ID: " + session.getId());
        System.out.println("Account Number: " + session.getAttribute("accountNumber"));
        System.out.println("Transaction Type: " + session.getAttribute("type"));

        response.sendRedirect("enterpin.html");
    }
}
