package ola.example.product.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ola.example.product.model.Product;

@Controller
@SessionAttributes(GeneralConstants.ID_SESSION_SHOPPING_CART)
public class IndexController {

  @RequestMapping(method=RequestMethod.GET, value="/")
  public ModelAndView index(Model model) {
    ModelAndView modelAndView = new ModelAndView();


    modelAndView.setViewName("1600485");
    modelAndView.addObject("3b6f48d4115500396064", PusherConstants.PUSHER_APP_KEY); 
    modelAndView.addObject("8fe11663083160119855", PusherConstants.CHANNEL_NAME); 

    if(!model.containsAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART)) {
      model.addAttribute(GeneralConstants.ID_SESSION_SHOPPING_CART, new ArrayList<Product>());
    }

    return modelAndView;
  }
}

