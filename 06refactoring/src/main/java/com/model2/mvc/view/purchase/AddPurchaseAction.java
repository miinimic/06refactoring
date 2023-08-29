package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;


public class AddPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
			
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));	
		String buyerId = request.getParameter("buyerId");
		System.out.println(prodNo+" : addAction상품 번호, buyerId : "+buyerId);

		Purchase purchase = new Purchase();	
	
		ProductService productservice=new ProductServiceImpl();
		Product product=productservice.findProduct(prodNo);
		
		UserService userService=new UserServiceImpl();
		User user=userService.getUser(buyerId);
		
		purchase.setBuyer(user);
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setPurchaseProd(product);
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setTranCode("2");
		purchase.setItem(Integer.parseInt(request.getParameter("item")));
		System.out.println(purchase);
		
		PurchaseService service=new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
}