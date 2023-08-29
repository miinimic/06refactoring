package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		String menu = request.getParameter("menu");
		int page = Integer.parseInt(request.getParameter("currentPage"));
		String tranCode=request.getParameter("tranCode");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("tranCode : "+tranCode+" + prodNo :"+prodNo);	
		
		
	
			Product product=new Product();		
			ProductService proservice=new ProductServiceImpl();
			product = proservice.findProduct(prodNo);
			
			Purchase purchase=new Purchase();
			
			purchase.setTranCode(tranCode);
			purchase.setPurchaseProd(product);
			purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
			
			PurchaseService service=new PurchaseServiceImpl();
			service.updateTranCode(purchase);
			System.out.println("updateTranCode ½ÇÇà ³¡");
	
			if(menu.equals("manage")) {
				return "forward:/listProduct.do?menu=manage&page="+page;
			} else {
				return "forward:/listPurchase.do?&page="+page;
			}
		
	}
}