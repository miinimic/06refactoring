package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class DeleteReviewAction extends Action {
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int currentPage=1;
	
		if(request.getParameter("currentPage") != null ){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		System.out.println("deleteReview로 들어온 tranNo : "+Integer.parseInt(request.getParameter("tranNo")));	
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service=new PurchaseServiceImpl();	
		service.deleteReview(tranNo);

		return "redirect:/listReview.do";
	}
}