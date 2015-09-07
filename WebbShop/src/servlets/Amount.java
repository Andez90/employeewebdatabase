package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Amount
 */
@WebServlet("/Amount")
public class Amount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession cart = request.getSession();
		String currentProduct = (String)cart.getAttribute("currentProduct");
		String choice = request.getParameter("Option");
		if(choice.equals("Add")){
			doAdd(cart, request);
			response.sendRedirect("index.jsp");
		}
		if(choice.equals("Cart")){
			response.sendRedirect("Cart");
		}
	}
	
	private void doAdd(HttpSession cart, HttpServletRequest request){
		String currentProduct = (String)cart.getAttribute("currentProduct");
		String amount = (String)request.getParameter("Amount");
		if(amount != null){
			if(currentProduct.equals(currentProduct))
				cart.setAttribute(currentProduct, amount);
		}
	}
}
