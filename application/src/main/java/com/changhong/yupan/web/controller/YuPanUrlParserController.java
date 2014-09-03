package com.changhong.yupan.web.controller;

import com.changhong.yupan.utils.ExternalUrlGetterUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-4
 * Time: 上午10:22
 */
public class YuPanUrlParserController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = ServletRequestUtils.getStringParameter(request, "url", "");
        if (!StringUtils.hasText(url)) {
            return new ModelAndView("yunpan/notvalidate");
        }

        String actualUrl = ExternalUrlGetterUtils.getActualUrl(url);
        if (!StringUtils.hasText(actualUrl)) {
            return new ModelAndView("yunpan/notvalidate");
        }
        response.sendRedirect(actualUrl);
        return null;
    }
}
