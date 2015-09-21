package com.nogemasa.management.controller.order;

import com.google.common.collect.Maps;
import com.nogemasa.management.pojo.*;
import com.nogemasa.management.service.goods.IPriceService;
import com.nogemasa.management.service.order.IGodownOrderListService;
import com.nogemasa.management.service.order.IGodownOrderService;
import com.nogemasa.management.service.store.IStoreService;
import com.nogemasa.util.UnicodeStringUtil;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>create at 15-8-3
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/order/godown")
public class GodownOrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IGodownOrderService godownOrderService;
    @Autowired
    private IGodownOrderListService godownOrderListService;
    @Autowired
    private IStoreService storeService;
    @Autowired
    private IPriceService priceService;

    @RequestMapping("read")
    @ResponseBody
    public Map<String, Object> goodsList(HttpServletRequest request, int start, int limit) {
        Map<String, Object> map = Maps.newHashMap();
        List<GodownOrderPojo> purchaseOrders = godownOrderService.getAllGodownOrders();
        if (purchaseOrders.size() == 0) {
            map.put("success", true);
            map.put("total", 0);
            map.put("list", Collections.emptyList());
            return map;
        }
        map.put("success", true);
        map.put("total", purchaseOrders.size());
        map.put("list", purchaseOrders
                .subList(start, purchaseOrders.size() < start + limit ? purchaseOrders.size() : start + limit));
        return map;
    }

    @RequestMapping("create")
    @ResponseBody
    public JSONObject createGodownOrder(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的入库单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data)); // 解码转换
            GodownOrderPojo goodsPojo = (GodownOrderPojo) JSONObject.toBean(json, GodownOrderPojo.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPojo userPojo = (UserPojo) authentication.getPrincipal();
            goodsPojo.setStore(storeService.getStoreByUserSid(userPojo.getSid()));
            godownOrderService.addGodownOrder(goodsPojo);
            logger.debug("插入后的id" + goodsPojo.getSid());
            json.put("sid", goodsPojo.getSid());
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "该入库单已经存在，请检查！");
            logger.error("创建入库单出错 GodownOrderController.createGodownOrder", e);
            throw new RuntimeException("创建入库单出错 GodownOrderController.createGodownOrder", e);
        }
        return json;
    }

    @RequestMapping("commit")
    @ResponseBody
    public JSONObject commitGodownOrder(HttpServletRequest request, String sid, String storeSid) {
        JSONObject json = new JSONObject();
        if (sid == null || sid.isEmpty() || storeSid == null || storeSid.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的入库单数据，请检查！");
            return json;
        }
        try {
            GodownOrderPojo order = new GodownOrderPojo();
            order.setSid(sid);
            StorePojo store = new StorePojo();
            store.setSid(storeSid);
            order.setStore(store);
            godownOrderService.updateGodownOrder(order);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "入库单数据错误，请检查后再试！");
            logger.error("修改入库单出错 GodownOrderController.commitGodownOrder", e);
            throw new RuntimeException("修改入库单出错 GodownOrderController.commitGodownOrder", e);
        }
        return json;
    }

    @RequestMapping("destroy")
    @ResponseBody
    public JSONObject deleteGodownOrder(HttpServletRequest request, String data) {
        JSONObject json = new JSONObject();
        if (data == null || data.isEmpty()) {
            json.put("success", false);
            json.put("msg", "未输入正确的入库单数据，请检查！");
            return json;
        }
        try {
            json = JSONObject.fromObject(UnicodeStringUtil.fromUnicodeString(data));
            GodownOrderPojo order = (GodownOrderPojo) JSONObject.toBean(json, GodownOrderPojo.class);
            godownOrderService.deleteGodownOrder(order);
            json.put("success", true);
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "入库单数据错误，请检查后再试！");
            logger.error("删除入库单出错 GodownOrderController.deleteGodownOrder", e);
            throw new RuntimeException("删除入库单出错 GodownOrderController.deleteGodownOrder", e);
        }
        return json;
    }

    /*
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
            Model model) {
        logger.debug("开始");
        try {
            String templateFileName = request.getServletContext().getRealPath("/") + "/template/student.xml";
            InputStream inputXML = new BufferedInputStream(new FileInputStream(templateFileName));
            String path = request.getServletContext().getRealPath("/");
            logger.debug(path);
            XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
            InputStream inputXLS = new BufferedInputStream(file.getInputStream());
            Student stu = new Student();
            List<Student> students = Lists.newArrayList();
            Map<String, Object> beans = new HashMap<>();
            beans.put("stu", stu);
            beans.put("students", students);
            XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
            if(readStatus.isStatusOK()) {
                model.addAttribute("success", true);
                model.addAttribute("students", students);
            } else {
                model.addAttribute("success", false);
                model.addAttribute("msg", "上传失败，请检查后再试！");
            }
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("msg", "上传失败，请检查后再试！");
            logger.error("上传失败 GodownOrderController.upload", e);
        }
        return "jsonView";
    }
    */

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response, String godownSid) {
        String templateFileName = request.getServletContext().getRealPath("/") + "/template/templateGodownList.xls";
        String destFileName = "godownList.xls";
        GodownOrderPojo godownOrder = godownOrderService.getGodownOrderBySid(godownSid);
        if (godownOrder == null) {
            return;
        }
        List<GodownOrderListPojo> orderList = godownOrderListService.getAllGodownOrderList(godownSid);
        orderList.forEach(order -> {
            PricePojo price = priceService.getPriceInfo(order.getGoods().getSn(), godownOrder.getStore().getSid());
            if (price != null) {
                order.setPrice(price.getPrice());
            }
        });
        Map<String, Object> beans = new HashMap<>();
        beans.put("store", godownOrder.getStore());
        beans.put("time", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(godownOrder.getEndTime()));
        beans.put("orderList", orderList);
        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        OutputStream out = null;
        //设置响应
        response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);
        response.setContentType("application/vnd.ms-excel");
        try {
            in = new BufferedInputStream(new FileInputStream(templateFileName));
            Workbook workbook = transformer.transformXLS(in, beans);
            out = response.getOutputStream();
            //将内容写入输出流并
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private class ExportPojo {
        private GoodsPojo goods;
        private double price;
        private int count;

        public GoodsPojo getGoods() {
            return goods;
        }

        public void setGoods(GoodsPojo goods) {
            this.goods = goods;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
