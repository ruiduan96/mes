package com.ruoyi.web.controller.product;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.product.order.domain.ProOrder;
import com.ruoyi.product.order.service.ProOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 * 
 * @author ruoyi
 * @date 2021-11-30
 */


@RestController
@RequestMapping("/product/order")
public class ProOrderController extends BaseController
{
    @Autowired
    private ProOrderService proOrderService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProOrder proOrder)
    {

        startPage();
        List<ProOrder> list = proOrderService.selectProOrderList(proOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:order:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProOrder proOrder)
    {
        List<ProOrder> list = proOrderService.selectProOrderList(proOrder);
        ExcelUtil<ProOrder> util = new ExcelUtil<ProOrder>(ProOrder.class);
        util.exportExcel(response, list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return AjaxResult.success(proOrderService.selectProOrderByOrderId(orderId));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('product:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProOrder proOrder)
    {
        return toAjax(proOrderService.insertProOrder(proOrder));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('product:order:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProOrder proOrder)
    {
        return toAjax(proOrderService.updateProOrder(proOrder));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('product:order:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(proOrderService.deleteProOrderByOrderIds(orderIds));
    }
}
