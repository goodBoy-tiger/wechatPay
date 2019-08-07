package com.gotop.wechatPay.controller;

import com.gotop.wechatPay.config.WeChatConfig;
import com.gotop.wechatPay.domain.JsonData;
import com.gotop.wechatPay.domain.User;
import com.gotop.wechatPay.domain.VideoOrder;
import com.gotop.wechatPay.mapper.VideoOrderMapper;
import com.gotop.wechatPay.service.UserService;
import com.gotop.wechatPay.service.VideoOrderService;
import com.gotop.wechatPay.utils.JwtUtils;
import com.gotop.wechatPay.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

/**
 * @ClassName WechatController
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/14 10:52
 */
@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

@Autowired
private WeChatConfig weChatConfig;
@Autowired
private UserService userService;
@Autowired
private VideoOrderMapper videoOrderMapper;
@Autowired
private VideoOrderService videoOrderService;

    /**
     * 拼装微信扫一扫登入url
     * @param accessPage
     * @return
     * @throws Exception
     */
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true) String accessPage) throws Exception{
        String redirectUrl = weChatConfig.getOpenRedirectUrl();  //获取开放平台重定向url
        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK");   //进行编码
        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callbackUrl,accessPage);
        return JsonData.buildSuccess(qrcodeUrl);
    }

    /**
     * 微信扫码登入回调地址
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("user/callback")
    public void wechatUserCallback(@RequestParam(value = "code") String code, String state, HttpServletResponse response)throws IOException {
        User user = userService.saveWechatUser(code);
        if(user != null){


            //生产token给前端
           String token = JwtUtils.geneJsonWebToken(user);

             //state 当前用户的页面地址，需要拼接http://  这样才不会站内跳转
            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));
    }
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        //BufferedReader是包装设计模型，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line=in.readLine())!=null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());

        SortedMap<String,String> sortedMap = WXPayUtil.getSortedMap(callbackMap);
        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){
            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderMapper.findByOutTradeNo(outTradeNo);
                if(dbVideoOrder.getState() == 0){ //判断逻辑看实际业务场景
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);
                    int rows = videoOrderService.updateOrderByOutTradeNo(videoOrder);
                    if(rows == 1){ //通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");

                    }
                }
            }
        }
        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }


    @RequestMapping("/order/callback1")
    public void orderCallback1(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String realPath = "E:\\wechatPay\\src\\main\\resources\\upload\\33.png";
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
        response.setHeader("content-disposition", "attachment;filename="+fileName);
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        byte[] b = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(b)) > 0){
            out.write(b,0,len);
        }
        in.close();
    }
}
