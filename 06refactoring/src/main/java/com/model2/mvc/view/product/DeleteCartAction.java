package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class DeleteCartAction extends Action {
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int currentPage=1;
	
		if(request.getParameter("currentPage") != null ){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		System.out.println("deleteCart로 들어온 prodNo : "+Integer.parseInt(request.getParameter("prodNo")));	
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		PurchaseService service=new PurchaseServiceImpl();	
		service.deleteCart(prodNo);

		return "redirect:/listCart.do";
	}
}