package com.changhong.system.web.controller;

import com.changhong.common.web.session.SessionKey;
import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: Jack Wang
 * Date: 14-7-3
 * Time: 上午10:54
 */
public class SystemClientUpdateReportControllerTest extends TestCase {

    SystemClientUpdateReportController controller;

    @Override
    public void setUp() throws Exception {
        controller = new SystemClientUpdateReportController();
    }

    public void testHandleRequestInternal() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "wahtfejfief");
        MockHttpServletResponse response = new MockHttpServletResponse();

        ModelAndView view = controller.handleRequestInternal(request, response);
        assertEquals(request.getSession().getAttribute(SessionKey.BROSWER_LOCATION), "STA");
        assertEquals(view.getViewName(), "backend/system/systemclientupdatereport");

    }
}
