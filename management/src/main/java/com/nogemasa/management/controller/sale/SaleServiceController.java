package com.nogemasa.management.controller.sale;

import com.nogemasa.common.pojo.MemberPojo;
import com.nogemasa.management.pojo.*;
import com.nogemasa.management.service.goods.IGoodsService;
import com.nogemasa.management.service.goods.IInventoryService;
import com.nogemasa.management.service.member.IMemberService;
import com.nogemasa.management.service.sale.IFinanceAccountService;
import com.nogemasa.management.service.sale.ISaleRecordContentService;
import com.nogemasa.management.service.sale.ISaleRecordDetailService;
import com.nogemasa.management.service.sale.ISaleService;
import com.nogemasa.signature.util.json.MessageParser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <br/>create at 15-8-24
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/sale")
public class SaleServiceController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ISaleService saleService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IInventoryService inventoryService;
    @Autowired
    private IFinanceAccountService financeAccountService;
    @Autowired
    private ISaleRecordContentService saleRecordContentService;
    @Autowired
    private ISaleRecordDetailService saleRecordDetailService;

    @RequestMapping("/addSaleRecord")
    @ResponseBody
    public JSONObject addSaleRecord(@RequestBody(required = false) String message,
            @RequestParam(value = "message", required = false) String messageGet) {
        JSONObject params = MessageParser.getParams(message, messageGet);
        JSONObject json = new JSONObject();
        try {
            String storeSid = params.containsKey("storeSid") ? params.getString("storeSid") : "1";
            String employeeSid = params.containsKey("employeeSid") ? params.getString("employeeSid") : "0";
            String promotionSid = params.containsKey("promotionSid") ? params.getString("promotionSid") : "0";
            double totalPrice = params.getDouble("totalPrice");
            double totalCost = params.getDouble("totalCost");
            double proportion = totalCost / totalPrice;
            String memberCardNo = params.containsKey("memberCardNo") ? params.getString("memberCardNo") : "";
            String costType = params.containsKey("costType") ? params.getString("costType") : "";
            if (!params.containsKey("goodsSns")) {
                json.put("success", false);
                json.put("errorCode", 403);
                json.put("message", "数据传输不完整，请检查后再试！");
                return json;
            }
            SaleRecordContentPojo content = new SaleRecordContentPojo(storeSid, employeeSid, memberCardNo, promotionSid,
                    costType, totalPrice, totalCost);
            saleRecordContentService.addSaleRecordContent(content);
            String goodss = params.getString("goodsSns");
            String[] goodsSnArray = goodss.split("!#!");
            for (String goodsInfo : goodsSnArray) {
                try {
                    String[] gs = goodsInfo.split("@");
                    if (gs.length >= 2) {
                        double goodsPrice = Double.valueOf(gs[0]);
                        double goodsOriginalCost = goodsPrice * proportion;
                        SaleRecordPojo saleRecord = new SaleRecordPojo();
                        StorePojo store = new StorePojo();
                        store.setSid(storeSid);
                        saleRecord.setStore(store);
                        EmployeePojo employee = new EmployeePojo();
                        employee.setSid(employeeSid);
                        saleRecord.setEmployee(employee);
                        GoodsPojo goods = new GoodsPojo();
                        goods.setSn(gs[1]);
                        saleRecord.setGoods(goods);
                        MemberPojo member = new MemberPojo();
                        member.setCard_no(memberCardNo);
                        saleRecord.setMember(member);
                        saleRecord.setGoodsOriginalCost(goodsOriginalCost);
                        saleRecord.setGoodsPrice(goodsPrice);
                        PromotionPojo promotion = new PromotionPojo();
                        promotion.setSid(promotionSid);
                        saleRecord.setPromotion(promotion);
                        SaleRecordDetailPojo detail = new SaleRecordDetailPojo(content.getSid(), gs[1],
                                goodsOriginalCost, goodsPrice);
                        saleRecordDetailService.addSaleRecordDetail(detail);
                        saleService.addSaleRecord(saleRecord);
                        goods = goodsService.getGoods(goods.getSn());
                        inventoryService.reduceInventoryCount4Sale(storeSid, goods.getSid());
                    }
                } catch (Exception e) {
                    logger.error("存储错误", e);
                }
            }
            try {
                financeAccountService.updateFinanceAccount(costType, totalCost);
            } catch (Exception e) {
                logger.error("修改财务账户余额出错，修改账户类型{}，添加金额{}", costType, totalCost, e);
            }
            try {
                if (memberCardNo != null && !memberCardNo.trim().isEmpty()) {
                    memberService.addPointsForMember(memberCardNo, (int) totalCost);
                }
            } catch (Exception e) {
                logger.error("添加积分错误,会员卡号{}，积分数{}", memberCardNo, (int) totalCost, e);
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("errorCode", 500);
            json.put("message", "服务器错误，请稍后再试！");
        }
        return json;
    }
}
