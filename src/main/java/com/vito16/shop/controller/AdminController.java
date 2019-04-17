package com.vito16.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vito16.shop.common.Page;
import com.vito16.shop.common.PageUtil;
import com.vito16.shop.model.News;
import com.vito16.shop.model.Order;
import com.vito16.shop.service.NewsService;
import com.vito16.shop.service.OrderService;
import com.vito16.shop.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vito16.shop.model.Admin;
import com.vito16.shop.service.AdminService;
import com.vito16.shop.util.AdminUtil;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author Vito saldkfjaljdsflfdsl;kjasd;lfjsadfsdf11111111111111111@gmail.com
 * @date 2013-7-8
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    OrderService orderService;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news2(Model model,HttpServletRequest request) {
        Page<News> page = new Page<News>(request);
        newsService.findNews(page);
        model.addAttribute("page",page);
        return "admin/news/newsAdmin";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg() {
        return "admin/adminReg";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String doReg(Admin admin, HttpSession session) {
        adminService.save(admin);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/adminLogin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(Admin admin, HttpSession session) {
        if (adminService.checkLogin(admin)) {
            AdminUtil.saveAdminToSession(session, adminService.findByUsernameAndPassword(admin.getUsername(), admin.getPassword()));
            logger.debug("管理员[{}]登陆成功",admin.getUsername());
            return "redirect:/admin/product";
        }
        return "redirect:/admin/login?errorPwd=true";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(HttpSession session) {
        AdminUtil.deleteAdminFromSession(session);
        return "redirect:/";
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(Model model,HttpServletRequest request) {
        Page<News> page = new Page<News>(request);
        newsService.findNews(page);
        model.addAttribute("page",page);
        return "admin/news/newsAdmin";
    }

    @RequestMapping(value = "/news/delete/{id}")
    @ResponseBody
    public String newsDelete(@PathVariable("id") Integer id) {
        newsService.delNews(id);
        return "success";
    }

    @RequestMapping(value = "/news/{id}")
    public ModelAndView newsView(@PathVariable("id") Integer id,ModelAndView model) {
        News news = newsService.findById(id);
        model.addObject("news", news);
        model.setViewName("admin/news/newsDetail");
        return model;
    }

    @RequestMapping(value = "/news/new", method = RequestMethod.GET)
    public String newsAdd(HttpSession session) {
        if (AdminUtil.getAdminFromSession(session) == null) {
            return "redirect:/admin/login?error=true";
        }
        return "admin/news/newsAdd";
    }

    @RequestMapping(value = "/news/add", method = RequestMethod.POST)
    public String newsAdd(News news,HttpSession session) {
        news.setInputUser(AdminUtil.getAdminFromSession(session));
        news.setCreateTime(new Date());
        newsService.addNews(news);
        return "redirect:/admin/news";
    }


}
