package cn.springcloud.sample.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created on 2021/11/13.
 */
@Api(tags = "测试源服务API接口")
@RequestMapping("/test")
public interface TestAPI {



    @ApiOperation(value = "加法", notes = "加法")
    @GetMapping("/add")
    public Integer add(@RequestParam("a")Integer a, @RequestParam("b")Integer b);


    @ApiOperation(value = "减法", notes = "减法")
    @GetMapping("/sub")
    public Integer sub(@RequestParam("a")Integer a, @RequestParam("b")Integer b);


    @ApiOperation(value = "乘法", notes = "乘法")
    @GetMapping("/mul")
    public Integer mul(@RequestParam("a")Integer a, @RequestParam("b")Integer b);


    @ApiOperation(value = "除法", notes = "除法")
    @GetMapping("/div")
    public Integer div(@RequestParam("a")Integer a, @RequestParam("b")Integer b);

}

