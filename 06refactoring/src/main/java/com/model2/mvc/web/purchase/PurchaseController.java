package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


//==> 회원관리 Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	/*@RequestMapping("/listCart.do")
	public String listCart( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		String userId = user.getUserId();
		System.out.println("/listCart.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		} 
		
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getCartList(search, userId);	
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/product/listCart.jsp";
	
	}*/
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(@RequestParam("prodNo") int prodNo, @RequestParam("buyerId") String userId, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/addPurchase.do");
		
		Purchase purchase = new Purchase();
		Product product = productService.findProduct(prodNo);
		User user = userService.getUser(userId);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setTranCode("2");
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));	
		purchase.setItem(Integer.parseInt(request.getParameter("item")));
		
		purchaseService.insertPurchase(purchase);
		
		model.addAttribute("purchase", purchase);		
		
		return "forward:/purchase/addPurchase.jsp";
		
	}
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam("prodNo") int prodNo, @RequestParam("userId") String userId, Model model) throws Exception {

		System.out.println("/addPurchaseView.do");
		
		System.out.println("prodno : "+prodNo+"  userId : "+userId);
		
		Product product = productService.findProduct(prodNo);
		User user = userService.getUser(userId);
		
		model.addAttribute("product", product);
		model.addAttribute("user", user);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase( @RequestParam("tranNo") int tranNo , Model model ) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		System.out.println(tranNo+" : getPurchaseControllers tranNo");
		Map<String , Object> purchase = purchaseService.findPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);

		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/listPurchase")
	public String listPurchase( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listPurchase");
		
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();	
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		} 

		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, buyerId);	
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		System.out.println("purchase list : "+map.get("list"));
	
		return "forward:/purchase/listPurchase.jsp";
		
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Map<String , Object> purchase = purchaseService.findPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
	
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase( @ModelAttribute("purchase") Purchase purchase , @RequestParam("prodNo") int prodNo, Model model , HttpSession session) throws Exception{

		System.out.println("/updatePurchase.do");
		//Business Logic

		int tranNo = purchase.getTranNo();
		
		purchaseService.updatePurchase(purchase, prodNo);
		
		Map<String , Object> result = purchaseService.findPurchase(tranNo);
		
		model.addAttribute("purchase", result);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode( @ModelAttribute("search") Search search, @RequestParam("tranNo") int tranNo, @RequestParam("tranCode") String tranCode, @RequestParam("prodNo") int prodNo, Model model , HttpSession session) throws Exception{

		System.out.println("/updateTranCode.do");
		//Business Logic
		
		Product product = productService.findProduct(prodNo);
		Map<String , Object> result = purchaseService.findPurchase(tranNo);
		Purchase purchase = (Purchase)result.get("purchase");
			
		purchase.setTranCode(tranCode);
		purchase.setPurchaseProd(product);
		purchase.setTranNo(tranNo);
		
		purchaseService.updateTranCode(purchase);

			if( search.getMenu().equals("manage")) {
				return "forward:/listProduct.do?page="+search.getCurrentPage();
			} else {
				return "forward:/listPurchase.do?page="+search.getCurrentPage();
			}
	}
	/*
	@RequestMapping("/addCart.do")
	public String addCart(@RequestParam("prodNo") int prodNo, @RequestParam("userId") String userId) throws Exception {

		System.out.println("/addCart.do");
		productService.addCart(prodNo, userId);

		return "redirect:/listProduct.do?menu=search";
	}
	

	

	

*/
	
}