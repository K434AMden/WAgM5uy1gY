// 代码生成时间: 2025-10-17 17:51:59
package com.example.optionpricing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/optionpricing")
public class OptionPricingService {

    /*
    * 计算欧式看涨期权的Black-Scholes模型
    *
    * 参数：
    * s - 当前股票价格
    * k - 执行价格
    * t - 到期时间（年）
    * r - 无风险利率
    * sigma - 股票价格波动率
    */
    @GET
    @Path("/blackscholes/{s}/{k}/{t}/{r}/{sigma}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateBlackScholes(@PathParam("s") double s, @PathParam("k\) double k, @PathParam("t\) double t, @PathParam("r\) double r, @PathParam("sigma\) double sigma) {
        try {
            double optionPrice = BlackScholesModel.callOptionPrice(s, k, t, r, sigma);
            return Response.ok().entity(optionPrice).build();
        } catch (Exception e) {
            // 错误处理
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error calculating option price: " + e.getMessage()).build();
        }
    }

    /*
    * 计算欧式看跌期权的Black-Scholes模型
    *
    * 参数：
    * s - 当前股票价格
    * k - 执行价格
    * t - 到期时间（年）
    * r - 无风险利率
    * sigma - 股票价格波动率
    */
    @GET
    @Path("/blackscholesput/{s}/{k}/{t}/{r}/{sigma}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateBlackScholesPut(@PathParam("s\) double s, @PathParam("k\) double k, @PathParam("t\) double t, @PathParam("r\) double r, @PathParam("sigma\) double sigma) {
        try {
            double optionPrice = BlackScholesModel.putOptionPrice(s, k, t, r, sigma);
            return Response.ok().entity(optionPrice).build();
        } catch (Exception e) {
            // 错误处理
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error calculating option price: " + e.getMessage()).build();
        }
    }

    // 内部类实现Black-Scholes模型
    private static class BlackScholesModel {

        // 计算欧式看涨期权价格
        public static double callOptionPrice(double s, double k, double t, double r, double sigma) {
            double d1 = (Math.log(s / k) + (r + 0.5 * sigma * sigma) * t) / (sigma * Math.sqrt(t));
            double d2 = d1 - sigma * Math.sqrt(t);
            return s * BlackScholesModel.cumulativeStandardNormalDistribution(d1) - k * Math.exp(-r * t) * BlackScholesModel.cumulativeStandardNormalDistribution(d2);
        }

        // 计算欧式看跌期权价格
        public static double putOptionPrice(double s, double k, double t, double r, double sigma) {
            double d1 = (Math.log(s / k) + (r + 0.5 * sigma * sigma) * t) / (sigma * Math.sqrt(t));
            double d2 = d1 - sigma * Math.sqrt(t);
            return k * Math.exp(-r * t) * BlackScholesModel.cumulativeStandardNormalDistribution(-d2) - s * BlackScholesModel.cumulativeStandardNormalDistribution(-d1);
        }

        // 计算标准正态分布的累积分布函数
        public static double cumulativeStandardNormalDistribution(double x) {
            // 使用近似方法计算标准正态分布的累积分布函数
            double a1 = 0.25777003;
            double a2 = -0.021098;
            double a3 = 0.00134339;
            double a4 = -0.00093384;
            double a5 = 0.00031938;
            double a6 = -0.00004014;
            double a7 = 0.00000053;

            double l = Math.abs(x);
            double k = 1.0 / (1.0 + 0.2316419 * l);
            double s = k * (a1 + k * (a2 + k * (a3 + k * (a4 + k * (a5 + k * a6 + k * a7))))) -
                    l * 0.39894 * Math.exp(-0.5 * l * l);
            if (x < 0) {
                s = 1.0 - s;
            }
            return s;
        }
    }
}