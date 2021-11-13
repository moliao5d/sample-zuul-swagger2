package cn.springcloud.sample.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonApplicationContextInitializer;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created on 2021/11/13.
 */
@Configuration
public class AuthFilter extends ZuulFilter {

    @Value("#{'${auth.filter.ignoreUrls:login}'.split(',')}")
    private List<String> ignoreUrls;

    private static AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        String requestURI = requestContext.getRequest().getRequestURI();
        for (String ignoreUrl : ignoreUrls) {
            if(ANT_PATH_MATCHER.match(ignoreUrl,requestURI)){
                return false;
            }
        }
        return true;

    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String token = requestContext.getRequest().getHeader("token");
        boolean authed=auth(token);
        if(authed){
            requestContext.addZuulRequestHeader("AUTH-HEADER", "someUserInfo");
        }else {
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("authfailed");
            requestContext.setSendZuulResponse(false);
            return null;

        }
        return null;
    }

    boolean auth(String token){
        return true;
    }
}
