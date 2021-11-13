package cn.springcloud.sample.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.netflix.zuul.context.RequestContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created on 2021/11/13.
 */
@Configuration
public class BalanceRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }



    @Override
    public Server choose(Object o) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String group = request.getHeader("group");
        List<Server> serverList = this.getLoadBalancer().getAllServers();

        if (group != null) {
            serverList = serverList.stream().filter(s -> {
                if (s instanceof DiscoveryEnabledServer) {
                    InstanceInfo instanceInfo = ((DiscoveryEnabledServer) s).getInstanceInfo();
                    String appGroupName = instanceInfo.getAppGroupName();
                    if (appGroupName != null) {
                        return appGroupName.equalsIgnoreCase(group);
                    }

                }
                return false;

            }).collect(Collectors.toList());
        }
        return get(serverList);


    }

    AtomicInteger atomIndex = new AtomicInteger(0);

    private Server get(List<Server> serverList) {
        if (serverList.isEmpty()) {
            return null;
        }
        int i = atomIndex.get();
        if (i >= serverList.size()) {
            i = 0;
            atomIndex.set(0);
        }
        Server server = serverList.get(i);
        atomIndex.incrementAndGet();
        return server;
    }
}
