package com.nogemasa.weixin.web.controller;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <br/>create at 15-8-31
 *
 * @author liuxh
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service/bar-code")
public class BarCodeServiceController {
    @RequestMapping("")
    public String index(@RequestParam("cardNo") String cardNo, HttpServletRequest request) {
        request.setAttribute("cardNo", cardNo);
        return "barCode";
    }

    @RequestMapping("/image/{code}")
    public void getBarCodeImage(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("code") String code)
            throws InvalidAtributeException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(),
                BaseLineTextPainter.getInstance());
        BufferedImage paramBufferedImage = localJBarcode.createBarcode(code);
        OutputStream out = response.getOutputStream();
        ImageUtil.encodeAndWrite(paramBufferedImage, ImageUtil.PNG, out, 600, 500);
        out.flush();
        out.close();
    }
}
