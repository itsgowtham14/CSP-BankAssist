package com.csp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DEBUG: DepositServlet - Unexpectedly hit from withdraw.html?");
        
        String amount = request.getParameter("amount");
        String type = request.getParameter("type");
        String accountNumber = request.getParameter("accountNumber");

        System.out.println("DEBUG: Received amount = " + amount);
        System.out.println("DEBUG: Received type = " + type);
        System.out.println("DEBUG: Received accountNumber = " + accountNumber);

        if (amount == null || type == null || accountNumber == null || Double.parseDouble(amount) <= 0) {
            System.out.println("DEBUG: DepositServlet - Validation failed, redirecting to deposit.html");
            response.getWriter().write("<script>alert('Transaction data is missing or invalid. Please try again.'); window.location.href='deposit.html';</script>");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("amount", amount);
        session.setAttribute("type", type);
        session.setAttribute("accountNumber", accountNumber);

        System.out.println("DEBUG: DepositServlet - Redirecting to enterpin.html");
        response.sendRedirect("enterpin.html");
    }
}