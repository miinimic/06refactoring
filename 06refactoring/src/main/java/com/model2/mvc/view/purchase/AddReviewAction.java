package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class AddReviewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo + " : tranNo");
		
		String review = request.getParameter("review");
			
		PurchaseService service=new PurchaseServiceImpl(); 
		service.addReview(tranNo, review);
		
		return "forward:/listReview.do";
	}
}