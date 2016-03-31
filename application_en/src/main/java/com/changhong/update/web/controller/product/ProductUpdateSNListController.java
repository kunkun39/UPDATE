package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-8-19
 * Time: 下午3:22
 */
public class ProductUpdateSNListController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<String> snLists = new ArrayList<String>();

        int updateId = ServletRequestUtils.getIntParameter(request, "updateId", -1);
        String filepath = productService.obtainSNListFilepath(updateId);

        File file = new File(filepath);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            while ((line = br.readLine()) != null) {
                snLists.add(line);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("snLists", snLists);
        return new ModelAndView("/backend/product/snlist", model);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
