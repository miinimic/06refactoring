package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdateReviewAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println(request.getParameter("tranNo") + "이것이 update Action으로 넘어온 tranNo");
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		String review = request.getParameter("review");
	
		PurchaseService service=new PurchaseServiceImpl();
		service.updateReview(tranNo, review);
		System.out.println("updateReview 실행 끝");

		return "forward:/listReview.do";
		
	}
}