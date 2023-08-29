package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddReviewViewAction extends Action{ 

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchase=service.findPurchase(tranNo);
		
		ProductService service2=new ProductServiceImpl();
		Product product = service2.findProduct(prodNo);
		
		request.setAttribute("purchase", purchase);
		request.setAttribute("product", product);
		
		return "forward:/purchase/addReviewView.jsp";
	}
}
