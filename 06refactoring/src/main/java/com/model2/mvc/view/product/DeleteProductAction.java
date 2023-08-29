package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class DeleteProductAction extends Action {
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int currentPage=1;
		String menu = null;
		if(request.getParameter("currentPage") != null ){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		if(request.getParameter("menu") != null){
			menu=request.getParameter("menu");
		}
		
		System.out.println("deleteProduct로 들어온 prodNo : "+Integer.parseInt(request.getParameter("prodNo")));	
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();	
		service.deleteProduct(prodNo); 

		return "redirect:/listProduct.do?currentPage="+currentPage+"&menu="+menu;
	}
}