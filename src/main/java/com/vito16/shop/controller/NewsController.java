package com.vito16.shop.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vito16.shop.common.Page;
import com.vito16.shop.common.PageUtil;
import com.vito16.shop.model.News;
import com.vito16.shop.service.NewsService;
import com.vito16.shop.util.AdminUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Vito zhouwentao16@gmail.com
 * @date 2013-7-8
 * @desc 今天天气很黑
 *
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model, HttpServletRequest request) {
        Page<News> page = new Page<News>(request);
        newsService.findNews(page);
        model.addObject("page", page);
        model.setViewName("news/newsList");
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewNews(@PathVariable Integer id,ModelAndView model, HttpServletRequest request) {
        News news = newsService.findById(id);
        model.addObject("news", news);
        model.setViewName("news/newsDetail");
        return model;
    }

    //淘宝添加商户接口
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewNews2(@PathVariable Integer id,ModelAndView model, HttpServletRequest request) {
        News news = newsService.findById(id);
        model.addObject("news", news);
        model.setViewName("news/newsDetail");
        System.out.println("***");
        return model;
    }

}
