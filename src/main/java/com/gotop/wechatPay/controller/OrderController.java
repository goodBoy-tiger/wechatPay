package com.gotop.wechatPay.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.gotop.wechatPay.domain.JsonData;
import com.gotop.wechatPay.dto.VideoOrderDto;
import com.gotop.wechatPay.service.VideoOrderService;
import com.gotop.wechatPay.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**订单接口
 * @ClassName OrderController
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/22 15:05
 */
@RestController
//@RequestMapping("/user/api/v1/order")
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "video_id") int videoId, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {

    String ip = IpUtils.getIpAddr(httpServletRequest);
    //int userId = (int) httpServletRequest.getAttribute("user_id");
    int userId = 1;
    VideoOrderDto viderOrderDto = new VideoOrderDto();
    viderOrderDto.setUserId(userId);
    viderOrderDto.setIp(ip);
    viderOrderDto.setVideoId(videoId);
    String codeUrl = videoOrderService.save(viderOrderDto);
    if(codeUrl == null){
        throw new NullPointerException();
    }

    try {
        //生成二维码配置
        Map<EncodeHintType,Object> hints = new HashMap<>() ;
        //设置纠错等级
        ((HashMap) hints).put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //编码类型
        ((HashMap) hints).put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,hints);

        OutputStream out = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
    }catch (Exception e){
        e.printStackTrace();
    }


    }
}
