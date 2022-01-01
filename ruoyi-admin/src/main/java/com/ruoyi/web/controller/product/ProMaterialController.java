package com.ruoyi.web.controller.product;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.product.material.domain.ProMaterial;
import com.ruoyi.product.material.service.ProMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物料信息Controller
 * 
 * @author ruoyi
 * @date 2021-12-05
 */
@RestController
@RequestMapping("/product/material")
public class ProMaterialController extends BaseController
{
    @Autowired
    private ProMaterialService proMaterialService;

    /**
     * 查询物料信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProMaterial proMaterial)
    {
        startPage();
        List<ProMaterial> list = proMaterialService.selectProMaterialList(proMaterial);
        return getDataTable(list);
    }

    /**
     * 导出物料信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:material:export')")
    @Log(title = "物料信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProMaterial proMaterial)
    {
        List<ProMaterial> list = proMaterialService.selectProMaterialList(proMaterial);
        ExcelUtil<ProMaterial> util = new ExcelUtil<ProMaterial>(ProMaterial.class);
        util.exportExcel(response, list, "物料信息数据");
    }

    /**
     * 获取物料信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return AjaxResult.success(proMaterialService.selectProMaterialByMaterialId(materialId));
    }

    /**
     * 新增物料信息
     */
    @PreAuthorize("@ss.hasPermi('product:material:add')")
    @Log(title = "物料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProMaterial proMaterial)
    {
        return toAjax(proMaterialService.insertProMaterial(proMaterial));
    }

    /**
     * 修改物料信息
     */
    @PreAuthorize("@ss.hasPermi('product:material:edit')")
    @Log(title = "物料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProMaterial proMaterial)
    {
        return toAjax(proMaterialService.updateProMaterial(proMaterial));
    }

    /**
     * 修改物料剩余信息
     */
    @PreAuthorize("@ss.hasPermi('product:material:subtract')")
    @Log(title = "物料剩余信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{subtractMaterial}")
    public AjaxResult edit(@RequestBody Integer subtractMaterial)
    {
        return toAjax(proMaterialService.subtractMaterial(subtractMaterial));
    }

    /**
     * 删除物料信息
     */
    @PreAuthorize("@ss.hasPermi('product:material:remove')")
    @Log(title = "物料信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(proMaterialService.deleteProMaterialByMaterialIds(materialIds));
    }
}
