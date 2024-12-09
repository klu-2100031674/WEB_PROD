package com.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import org.json.JSONObject;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.swagger.oas.annotations.parameters.RequestBody;

import com.fashion.model.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import com.paytm.pgplus.models.Money;
import com.paytm.pgplus.models.UserInfo;
import com.fashion.service.AdminService;
import com.fashion.service.OrdersService;
import com.fashion.service.ProductService;
import com.fashion.service.UsersCartService;
import com.fashion.service.UsersService;
import com.paytm.merchant.models.*;
import com.paytm.merchant.models.Time;
import com.paytm.pg.Payment;
import com.paytm.pg.constants.LibraryConstants;
import com.paytm.pg.constants.MerchantProperties;
import com.paytm.pgplus.enums.EChannelId;
import com.paytm.pgplus.enums.EnumCurrency;
import com.paytm.pgplus.response.InitiateTransactionResponse;
import com.paytm.pgplus.response.NativePaymentStatusResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.fashion.controller.*;
import java.util.*;


@Controller
public class RestController {
	
  
    @Autowired
    private Environment env;

	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private AdminService adminService;
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UsersCartService cartService;
	
	@Autowired
	private OrdersService orderService;
	

	    
	@GetMapping("/pay")
	public String paytm() {
		return "paymentex";
	}
	
	@GetMapping("/alladmins")
	public ModelAndView Admins(HttpServletRequest request,Model model) {
		 List<Admin> admins = adminService.getAllAdmins();
	     model.addAttribute("admins",admins);
	       

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");
	    String email =(String)session.getAttribute("email");
	    mv.addObject("ename", ename);
	    if (ename != null) {
	        mv.setViewName("AllAdmins");
	        mv.addObject("ename", ename);
	        mv.addObject("email",email);// Add the user's name to the model
	    } else {
	        mv.setViewName("AllAdmins");
	    }

	    return mv;
	}
	
	@GetMapping("/")
	public ModelAndView home(HttpServletRequest request,Model model) {
		 List<Products> products = productService.getAllProducts();
	     model.addAttribute("products", products);
	       

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");
	    String email =(String)session.getAttribute("email");
	    
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
	    mv.addObject("ename", ename);
	    if (ename != null) {
	        mv.setViewName("home");
	        mv.addObject("ename", ename);
	        mv.addObject("email",email);// Add the user's name to the model
	    } else {
	        mv.setViewName("home");
	    }

	    return mv;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	@GetMapping("/register")
	public ModelAndView register()
	{
		return new ModelAndView("register");
	}
	@PostMapping("/checkout")
	public ModelAndView order(HttpServletRequest request,Model model)
	{
		String mgs = null;
	     ModelAndView mv = new ModelAndView();
	     HttpSession session = request.getSession();
		 String email =(String)session.getAttribute("email");
		    
		 List<Cart> orders = cartService.getCartItemsByEmail(email);
	     String pname = request.getParameter("name");
	     String doorno = request.getParameter("doorno");
	     String street = request.getParameter("street");
	     String city = request.getParameter("city");
	     String state = request.getParameter("state");
	     String zip_code = request.getParameter("zip_code");
	     String phone = request.getParameter("phone");
	     try {
	    	 for(int i=0;i<orders.size();i++)
	    	 {
	    		 Orders ord = new Orders();
	    		 ord.setCategory(orders.get(i).getCategory());
	    		 ord.setEmail(orders.get(i).getEmail());
	    		 ord.setGender(orders.get(i).getGender());
	    		 ord.setImage(orders.get(i).getImage());
	    		 ord.setPdis(orders.get(i).getDis());
	    		 ord.setPrice(orders.get(i).getPrice());
	    		 ord.setDelivery_date("7");
	    		 ord.setDono(doorno);
	    		 ord.setPhononumber(phone);
	    		 ord.setPincode(zip_code);
	    		 ord.setPname(orders.get(i).getName());
	    		 ord.setProid(orders.get(i).getCartid());
	    		 ord.setState(state);
	    		 ord.setStreet(street);
	    		 ord.setPaiername(pname);
	    		 ord.setCity(city);
	    		 ord.setDelivery_status("shipped");
	    		 mgs=orderService.addorder(ord); 
	    		 if(mgs=="orderSuccess")
	    		 {
	    			cartService.deleteCartItem(orders.get(i).getId());
	    		 }
	    	 }
	         mv.setViewName("orderSuccess");
	         mv.addObject("message", mgs);
	     } catch (Exception e) {
	         mgs = e.getMessage();
	         mv.setViewName("orderSuccess");
	         mv.addObject("message", mgs);
	     }

	     return mv;
	}
	
	@GetMapping("/myorders")
	public ModelAndView myorders(HttpServletRequest request,Model model)
	{
	
	       

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");
	    String email =(String)session.getAttribute("email");
		List<Orders> car = orderService.getCartItemsByEmail(email);
		model.addAttribute("orders",car);
        model.addAttribute("cartItems", car);
	    mv.addObject("ename", ename);
	    if (ename != null) {
	        mv.setViewName("myorders");
	        mv.addObject("ename", ename);
	        mv.addObject("email",email);// Add the user's name to the model
	    } else {
	        mv.setViewName("myorders");
	    }

	    return mv;
	}
	
	@PostMapping("orderaddress")
	public ModelAndView orderprogress(HttpServletRequest request,Model model)
	{
		
		ModelAndView mv=new ModelAndView();
		 List<Cart> cartitm = (List<Cart>) request.getAttribute("cartitems");
	        model.addAttribute("cartitm", cartitm);
		
	        HttpSession session = request.getSession();
		String email = request.getParameter("email");
		System.out.println(session.getAttribute("amount"));
		 String id =(String)session.getAttribute("id");
		 mv.setViewName("orderaddress");
	 
	    return mv;
	    
	}
	@PostMapping("checkuserlogin")
	public ModelAndView checkemplogin(HttpServletRequest request,Model model)
	{
		
		ModelAndView mv=new ModelAndView();
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);
		
		String email = request.getParameter("email");
	    String pwd = request.getParameter("pwd");
	     
	    Users emp =  userService.checkemplogin(email, pwd);
	    
	    
	    if(emp!=null)
	    {
	    	if(emp.getAdmin().equals("admin")) {
		    	mv.addObject("name",emp.getName());
		    	mv.setViewName("adminhome");
		    }else {
	      HttpSession session = request.getSession();
	      
	      session.setAttribute("email", emp.getEmail());
	      session.setAttribute("eid", emp.getId()); //eid is a session variable
	      session.setAttribute("ename", emp.getName()); //ename is a session variable
	   // Set the "ename" attribute with the user's name
	      List<Cart> car = cartService.getCartItemsByEmail(email);
		    model.addAttribute("cartcount",car.size());
	        model.addAttribute("cartItems", car);
	      mv.addObject("ename", emp.getName());
	      mv.addObject("email",emp.getEmail());
	      mv.setViewName("home");
		    }
	    }
	    else
	    {
	      mv.setViewName("login");
	      mv.addObject("faild", "Email or Password are Incorrect");
	    }
	    
	 
	    return mv;
	    
	}
	@PostMapping("insertemp")
	public ModelAndView insertaction(HttpServletRequest request)
	{
		
		String mgs=null;
		ModelAndView mv=new ModelAndView();
		try 
		{
			String name = request.getParameter("name");
		    String email = request.getParameter("email");
		    long mobilenumber =  Long.parseLong(request.getParameter("mobilenumber"));
		    String pwd = request.getParameter("pwd");
		    
		    Users usr = new Users();
		      usr.setName(name);
		      usr.setEmail(email);
		      usr.setNumber(mobilenumber);
		      usr.setPassword(pwd);
		      usr.setCountry("India");
		      usr.setActive(true);
		      usr.setAdmin("user");
		      
		    mgs=userService.adduser(usr);
		  	mv.setViewName("login");
			mv.addObject("message",mgs);
			
			
		      
		} 
		catch (Exception e) 
		{
			mgs=e.getMessage();
			mv.setViewName("register");
			mv.addObject("message","Email is Already Exist");
			
		}
	
		
		return mv;
		
	}
	 @GetMapping("account")
	  public ModelAndView updateemp(HttpServletRequest request)
	  {
	    ModelAndView mv = new ModelAndView();
	    
	    HttpSession session = request.getSession();
	    
	    mv.setViewName("account");
	    
	    mv.addObject("eid", session.getAttribute("eid"));
	    mv.addObject("ename", session.getAttribute("ename"));
	    
	    String id = (String) session.getAttribute("eid");
	    
	    Users user = userService.viewuserbyid(id);
	    
	    mv.addObject("user", user);
	    
	    return mv;
	  }
	 
	 @PostMapping("savedetails")
	 public ModelAndView updateaction(HttpServletRequest request) {
	     String msg = null;
	     ModelAndView mv = new ModelAndView();

	     HttpSession session = request.getSession();

	     String id = (String) session.getAttribute("eid");

	     try {
	         String name = request.getParameter("name");
	         String number = request.getParameter("number");
	         String addr1 = request.getParameter("addr1");
	         String addr2 = request.getParameter("addr2");
	         String pincode = request.getParameter("pincode");
	         String state = request.getParameter("state");

	         // Fetch the existing user by ID
	         Users existingUser = userService.viewuserbyid(id);

	         if (existingUser != null) {
	             // Update the user's information
	             existingUser.setName(name);
	             existingUser.setNumber(Long.parseLong(number));
	             existingUser.setAddressLine1(addr1);
	             existingUser.setAddressLine2(addr2);
	             existingUser.setPostcode(pincode);
	             existingUser.setState(state);

	             // Ensure the email is not null by fetching it from the existing user
	             existingUser.setEmail(existingUser.getEmail());
	             existingUser.setAdmin(existingUser.getAdmin());

	             msg = userService.updateuser(existingUser);

	     	    String ename = (String) session.getAttribute("ename");
	     	    mv.addObject("ename",existingUser.getName());
	             mv.setViewName("home");
	             mv.addObject("message", "Saved successfully");
	         } else {
	             mv.setViewName("updateerror");
	             mv.addObject("message", "User not found");
	         }
	     } catch (Exception e) {
	         msg = e.getMessage();
	         mv.setViewName("updateerror");
	         mv.addObject("message", msg);
	     }

	     return mv;
	 }
	 @GetMapping("/logout")
	 public ModelAndView logout(HttpServletRequest request) {
	     HttpSession session = request.getSession();
	     session.invalidate();
	     return new ModelAndView("home");
	 }
	 @PostMapping("/insertadmin")
	 public ModelAndView admin(HttpServletRequest request) {
			String mgs=null;
			ModelAndView mv=new ModelAndView();
			try 
			{
				String admin = request.getParameter("admincode");
				System.out.println(admin);
				if(admin.equals("31674")) {
				System.out.println(admin);
				String name = request.getParameter("name");
			    String email = request.getParameter("email");
			    System.out.println(email);
			    long mobilenumber =  Long.parseLong(request.getParameter("mobilenumber"));
			    String pwd = request.getParameter("pwd");
			    
			    Users usr = new Users();
			      usr.setName(name);
			      usr.setEmail(email);
			      usr.setNumber(mobilenumber);
			      usr.setPassword(pwd);
			      usr.setCountry("India");
			      usr.setActive(true);
			      usr.setAdmin("admin");
			      
			    mgs=userService.adduser(usr);
			  	mv.setViewName("login");
				mv.addObject("message",mgs);}
				else {
					mv.setViewName("admin");
					mv.addObject("message","Admin Code is Incorrect");
				}
				
				
			      
			}
			catch(Exception e)
			{
				mgs=e.getMessage();
				mv.setViewName("admin");
				mv.addObject("message",mgs);
			}
		 
		 return mv;
		 
	 }
	 @GetMapping("/adminreg")
		public ModelAndView adminregister()
		{
			return new ModelAndView("admin");
		}
	 @GetMapping("/adminaddproducts")
	 public ModelAndView adminAddProdducts()
	 {
		 return new ModelAndView("adminaddproducts");
	 }
	 @PostMapping("/saveproduct")
	 public ModelAndView adminaddproducts(@RequestParam("image") MultipartFile imageFile, HttpServletRequest request) {
	     String mgs = null;
	     ModelAndView mv = new ModelAndView();

	     try {
	         String name = request.getParameter("name");
	         String dic = request.getParameter("dis");
	         String quantity = request.getParameter("quantity");
	         String price = request.getParameter("price");
	         String gender = request.getParameter("gender");
	         String category = request.getParameter("cat");

	         // Create a Products object and set its properties
	         Products product = new Products();
	         product.setName(name);
	         product.setDis(dic);
	         product.setQuantity(quantity);
	         product.setPrice(price);
	         product.setGender(gender);
	         product.setCategory(category);

	         // Check if an image was provided
	         if (!imageFile.isEmpty()) {
	             // Set the product image from the uploaded file
	             product.setImage(imageFile.getBytes());
	         }

	         mgs = productService.addproduct(product);
	         mv.setViewName("adminaddproducts");
	         mv.addObject("message", mgs);
	     } catch (Exception e) {
	         mgs = e.getMessage();
	         mv.setViewName("adminaddproducts");
	         mv.addObject("message", mgs);
	     }

	     return mv;
	 }
	 @GetMapping("/productList")
	    public String productList(Model model) {
	        List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	        return "products";
	    }
	 @GetMapping("/men")
	 public ModelAndView productMen(Model model,HttpServletRequest request) {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    if (ename != null) {
	        mv.setViewName("men");
	        mv.addObject("ename", ename); // Add the user's name to the model
	    } else {
	        mv.setViewName("men");
	    }

	    return mv;
	    }
	 @GetMapping("/tshirts")
	 public ModelAndView tshirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");
	    
	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);

	        mv.setViewName("tshirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/casulaShirts")
	 public ModelAndView casualshirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);
	     System.out.print(products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	    String ename = (String) session.getAttribute("ename");

	        mv.setViewName("casulaShirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/formalshirts")
	 public ModelAndView formalshirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	    String ename = (String) session.getAttribute("ename");

	        mv.setViewName("formalshirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/sweatshirts")
	 public ModelAndView sweatshirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	    String ename = (String) session.getAttribute("ename");

	        mv.setViewName("sweatshirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/jackets")
	 public ModelAndView jackets(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	    String ename = (String) session.getAttribute("ename");

	        mv.setViewName("jackets");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/jeens")
	 public ModelAndView jeens(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	        
	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	    String ename = (String) session.getAttribute("ename");

	        mv.setViewName("jeens");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/causaltrousera")
	 public ModelAndView cautrousers(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("causaltrousera");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/trackjoggers")
	 public ModelAndView trackjoggers(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("trackjoggers");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/kurtas")
	 public ModelAndView kurtas(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("kurtas");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/sarees")
	 public ModelAndView sarees(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("sarees");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/skirts")
	 public ModelAndView skirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("skirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/womensweatshirts")
	 public ModelAndView womensweatshirts(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("womensweatshirts");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/womenjackets")
	 public ModelAndView womenjackets(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("womenjackets");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/wjeens")
	 public ModelAndView wjeens(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("wjeens");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/dress")
	 public ModelAndView dress(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("dress");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @GetMapping("/ethnicware")
	 public ModelAndView ethnicware(Model model,HttpServletRequest request)
	 {
		 List<Products> products = productService.getAllProducts();
	        model.addAttribute("products", products);

	    ModelAndView mv = new ModelAndView();
	    HttpSession session = request.getSession();

	    String ename = (String) session.getAttribute("ename");

	    String email =(String)session.getAttribute("email");
	    List<Cart> car = cartService.getCartItemsByEmail(email);
	    model.addAttribute("cartcount",car.size());
        model.addAttribute("cartItems", car);
        
	        mv.setViewName("ethnicware");
	        mv.addObject("ename", ename); // Add the user's name to the model

	    return mv;
	 }
	 @PostMapping("/add-to-cart")
	 public String addToCart(@RequestParam String productId, HttpServletRequest request, @RequestParam(required = false, defaultValue = "/") String returnUrl) {
	     // Get the product details from the database based on the productId.
	     Products product = productService.viewuserbyid(productId);
	     HttpSession session = request.getSession();
	     String s=(String) session.getAttribute("email");
	     if (product != null) {
	         // Create a Cart item and populate it with product details.
	         Cart cartItem = new Cart();
	         cartItem.setCartid(productId);
	         cartItem.setName(product.getName());
	         cartItem.setDis(product.getDis());
	         cartItem.setPrice(product.getPrice());
	         cartItem.setCategory(product.getCategory());
	         cartItem.setGender(product.getGender());
	         cartItem.setEmail(s);
	         cartItem.setImage(product.getImage());
	         // Set the user's email

	         // Save the cart item to the database using the CartService.
	         cartService.adduser(cartItem);
	         if (returnUrl.endsWith(".jsp")) {
	             returnUrl = returnUrl.substring(0, returnUrl.lastIndexOf("."));
	         }
	         if(returnUrl==null) {
	        	 return "redirect:"+"/";
	         }
	         // Redirect to a page that shows the updated cart contents.
	         return "redirect:" + returnUrl;
	     } else {
	         // Handle the case where the product does not exist.
	    	  return "redirect:" + returnUrl;
	     }
	 }
	 @GetMapping("/cart")
	 public ModelAndView cartitems(HttpServletRequest request,Model model)
	 {
		  	ModelAndView mv = new ModelAndView();
		    HttpSession session = request.getSession();
		    String email =(String)session.getAttribute("email");
		    List<Cart> car = cartService.getCartItemsByEmail(email);
		    model.addAttribute("cartitems",car);
		    mv.setViewName("Carts");
		    return mv;
	 }
	 @GetMapping("/deleteCartItem")
	 public String deleteCartItem(@RequestParam("id") String itemId) {
	     // Implement the code to delete the item from the cart based on the 'itemId'.
	     // You can use your cartService to delete the item by its ID.
	     // After deleting, you can redirect the user back to the cart page.
	     // Make sure to handle exceptions and validation as needed.
	     
	     // For example:
	     cartService.deleteCartItem(itemId);
	     
	     return "redirect:/cart"; // Redirect back to the cart page after deleting.
	 }
	 
	 @RequestMapping(value = {"/payment"}, method = RequestMethod.GET)
	 public String payment(Model model){
	     model.addAttribute("rzp_key_id", "rzp_test_igrEOuPAHcLSxg");
	     model.addAttribute("rzp_currency", "INR");
	     model.addAttribute("rzp_company_name", "fashion_shop");
	     return "users/payment";
	 }
	 @GetMapping("/payment/createOrderId/{amount}")
	 @ResponseBody
	 public String createPaymentOrderId(@PathVariable String amount) {
	     String orderId=null;
	     try {
	         RazorpayClient razorpay = new RazorpayClient("rzp_test_igrEOuPAHcLSxg", "IM0tVcEsnKGCV8iWHOcKaqkO");
	         JSONObject orderRequest = new JSONObject();
	         orderRequest.put("amount", "10"); // amount in the smallest currency unit
	         orderRequest.put("currency", "INR");
	         orderRequest.put("receipt", "order_rcptid_11");

	         Order order = razorpay.Orders.create(orderRequest);
	         orderId = order.get("id");
	     } catch (RazorpayException e) {
	         // Handle Exception
	         System.out.println(e.getMessage());
	     }
	     return orderId;
	 }
	 @RequestMapping(value = {"/payment/success/{amount}/{contactCount}/{companyName}/{currency}/{description}"}, method = RequestMethod.POST)
	    public String paymentSuccess(Model model,
	                                 Authentication authentication,
	                                 @RequestParam("razorpay_payment_id") String razorpayPaymentId,
	                                 @RequestParam("razorpay_order_id") String razorpayOrderId,
	                                 @RequestParam("razorpay_signature") String razorpaySignature,
	                                 @PathVariable Float amount,
	                                 @PathVariable Integer contactCount,
	                                 @PathVariable String companyName,
	                                 @PathVariable String currency,
	                                 @PathVariable String description,
	                                 RedirectAttributes redirectAttributes
	    ){
	        System.out.println("Save all data, which on success we get!");
	        return "redirect:/payment";
	    }
	 
	//xl service
	@PostMapping("/addAllProductsFromExcel")
	public ModelAndView uploadProducts(@RequestParam("excelFile") MultipartFile excelFile) {
	    String message = null;
	    ModelAndView mv = new ModelAndView();

	    try {
	        // Check if an Excel file was provided
	        if (!excelFile.isEmpty()) {
	            // Process the Excel file and extract product information
	            List<Products> products = extractProductsFromExcel(excelFile);

	            // Add products to the database
	            productService.addAllProducts(products);

	            message = "Products added successfully from the Excel sheet.";
	        } else {
	            message = "No Excel file provided.";
	        }

	        mv.setViewName("adminaddproducts");
	        mv.addObject("message", message);
	    } catch (Exception e) {
	        message = "Error: " + e.getMessage();
	        mv.setViewName("adminaddproducts");
	        mv.addObject("message", message);
	    }

	    return mv;
	}


public List<Products> extractProductsFromExcel(MultipartFile excelFile) throws IOException {
    List<Products> products = new ArrayList<>();

    try (InputStream is = excelFile.getInputStream()) {
        Workbook workbook = new XSSFWorkbook(is); // Use XSSFWorkbook for .xlsx files
        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

        DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheet) {
            // Assuming the Excel columns are in a specific order
            Cell nameCell = row.getCell(0);
            Cell disCell = row.getCell(1);
            Cell quantityCell = row.getCell(2);
            Cell priceCell = row.getCell(3);
            Cell genderCell = row.getCell(4);
            Cell categoryCell = row.getCell(5);
            Cell image = row.getCell(6);
            

            String name = dataFormatter.formatCellValue(nameCell);
            String dis = dataFormatter.formatCellValue(disCell);
            String quantity = dataFormatter.formatCellValue(quantityCell);
            String price = dataFormatter.formatCellValue(priceCell);
            String gender = dataFormatter.formatCellValue(genderCell);
            String category = dataFormatter.formatCellValue(categoryCell);
            String image_url = dataFormatter.formatCellValue(image);
            
            
            Products product = new Products();
            product.setName(name);
            product.setDis(dis);
            product.setQuantity(quantity);
            product.setPrice(price);
            product.setGender(gender);
            product.setCategory(category);
            product.setImage_url(image_url);

            products.add(product);
        }
    }

    return products;
}
private byte[] getByteArrayFromImageCell(Cell imageCell) {
    // Check if the cell is not empty
    if (imageCell != null) {
        try {
            // Get the cell value as a string
            String imageData = imageCell.getStringCellValue();

            // Decode the Base64-encoded image data
            byte[] imageBytes = Base64.getDecoder().decode(imageData);

            return imageBytes;
        } catch (Exception e) {
            // Handle any exceptions that may occur during decoding
            e.printStackTrace();
        }
    }

    return null; // Return null if the cell is empty or if the data cannot be decoded
}
@PostMapping("/create_order")
@ResponseBody
public  String createOrder(@RequestBody  HashMap<String,Object> data,HttpServletRequest request) throws RazorpayException
{
	HttpSession session = request.getSession();
	Long amt = (Long) session.getAttribute("amount");
	
	System.out.println("Received JSON data: " +amt );
	var client = new RazorpayClient("rzp_test_igrEOuPAHcLSxg", "IM0tVcEsnKGCV8iWHOcKaqkO");
	JSONObject ob = new JSONObject();
	ob.put("amount", amt*100);
	ob.put("currency", "INR");
	ob.put("receipt", "txn_235425");
	Order order = client.Orders.create(ob);
	
	System.out.print(order);
	return order.toString();
}

	  
}
