package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class ListCartAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");

		System.out.println(user.getUserId()+" : cart action ���� �Ѿ�� usrid");
		
		Search search=new Search();
		
		int currentPage=1;
		String menu = null;

		if(request.getParameter("currentPage") != null ){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		if(request.getParameter("menu") != null){
			menu=request.getParameter("menu");
		}
		
		if(request.getParameter("order") != null) {
			search.setOrder(request.getParameter("order"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);

		// Business logic ����
			ProductService service=new ProductServiceImpl();
			Map<String,Object> map=service.getCartList(search, user.getUserId());	
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListCartAction ::"+resultPage);
		
		// Model �� View ����
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listCart.jsp";
	}
}