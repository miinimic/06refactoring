package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class AddPurchaseViewAction extends Action{ 

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		String userId = request.getParameter("userId");	
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));	
		System.out.println(userId+"+userID¿Í prodNo+ "+prodNo);
		
		ProductService service=new ProductServiceImpl();
		Product product=service.findProduct(prodNo);
		
		UserService userService=new UserServiceImpl();
		User user=userService.getUser(userId);
		
		request.setAttribute("product", product);
		request.setAttribute("User", user);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
