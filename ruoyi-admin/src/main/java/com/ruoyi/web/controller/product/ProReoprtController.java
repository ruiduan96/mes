package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.product.order.domain.ProOrder;
import com.ruoyi.product.report.service.ProReportService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 报工管理操作
 * @author: 869771468@qq.com
 * @create: 2021-12-04 16:01
 **/

@RestController
@RequestMapping("/product/report")
public class ProReoprtController extends BaseController {

    @Autowired
    private ProReportService proReportService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProOrder proOrder)
    {
        startPage();
        List<ProOrder> list = proReportService.selectProReportList(proOrder);
        return getDataTable(list);
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:report:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return AjaxResult.success(proReportService.selectProReportByOrderId(orderId));
    }
}
