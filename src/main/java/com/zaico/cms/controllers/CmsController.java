package com.zaico.cms.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ZAITNIK on 06.11.2016.
 */
@Controller
public class CmsController {

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title","CMS");
        mav.setViewName("components/cmsmn");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        mav.addObject("errMessage",authentication.toString());
        return mav;
    }
}
