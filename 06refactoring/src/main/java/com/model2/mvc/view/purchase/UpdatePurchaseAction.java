package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println(request.getParameter("tranNo") + "이것이 update Action으로 넘어온 tranNo");
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
	
		Purchase purchase=new Purchase();

		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo"))); 
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setItem(Integer.parseInt(request.getParameter("item"))); 
		
		PurchaseService service=new PurchaseServiceImpl();
		service.updatePurchase(purchase, prodNo);
		System.out.println("updatePurchase 실행 끝");
		request.setAttribute("vo", purchase);

		return "forward:/getPurchase.do?tranNo="+tranNo;
		
	}
}