package com.model2.mvc.web.product;

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
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


//==> 회원관리 Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
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
	
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct.do");
		productService.insertProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/addCart.do")
	public String addCart(@RequestParam("prodNo") int prodNo, @RequestParam("userId") String userId) throws Exception {

		System.out.println("/addCart.do");
		productService.addCart(prodNo, userId);

		return "redirect:/listProduct.do?menu=search";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.findProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.findProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
	
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		
		System.out.println("regdate : "+product.getRegDate());

		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+product.getProdNo()+"&menu=manage";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		} 
		
		search.setPageSize(pageSize);
		
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);	
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	/*@RequestMapping("/listCart.do") // purchase controller에 구현하기
	public String listCart( @ModelAttribute("search") Search search , @RequestParam("userId") String userId, Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listCart.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		} 
		
		search.setPageSize(pageSize);
		
		if(search.getMenu() != null){
			search.setMenu(search.getMenu());
		}
		
		if(search.getOrder() != null){
			search.setOrder(search.getOrder());
		}
			
		
		// Business logic 수행
		Map<String , Object> map=productService.getCartList(search, userId);	
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listCart.jsp";

	}*/
}