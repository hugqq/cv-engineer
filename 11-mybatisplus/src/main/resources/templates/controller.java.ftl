package ${package.Controller};

import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


import ${package.Entity}.${ entity};
import ${package.Service}.${table.serviceName};

/**
 * @author ${author}
 * @since ${date}
 */

@RestController
@RequestMapping("${entity?uncap_first}")
@Slf4j
public class ${table.controllerName}  {

    @Autowired
    private  ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping("list")
    public ResponseEntity list(${entity} ${entity?uncap_first}, HttpServletRequest request){
        LambdaQueryWrapper<${entity}> lambdaQueryWrapper = new LambdaQueryWrapper<${entity}>();
        List<${entity}> list = ${table.serviceName?uncap_first}.list(lambdaQueryWrapper);
        return ResponseEntity.ok(list);
    }

    @GetMapping("page")
    public ResponseEntity pageList(${entity} ${entity?uncap_first},
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request){
        LambdaQueryWrapper<${entity}> lambdaQueryWrapper = new LambdaQueryWrapper<${entity}>();
        Page<${entity}> page = ${table.serviceName?uncap_first}.page(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
        return ResponseEntity.ok(page);
    }

    @GetMapping("getOne")
    public ResponseEntity getOne(${entity} ${entity?uncap_first}, HttpServletRequest request){
        LambdaQueryWrapper<${entity}> lambdaQueryWrapper = new LambdaQueryWrapper<${entity}>();
        ${entity} one = ${table.serviceName?uncap_first}.getOne(lambdaQueryWrapper);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(String id, HttpServletRequest request) {
        ${entity} one = ${table.serviceName?uncap_first}.getById(id);
        return ResponseEntity.ok(one);
    }

    @PostMapping("saveOrUpdate")
    public ResponseEntity saveOrUpdate(${entity} ${entity?uncap_first}, HttpServletRequest request){
        return ResponseEntity.ok(${table.serviceName?uncap_first}.saveOrUpdate(${entity?uncap_first}));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity del(@PathVariable String id, HttpServletRequest request){
        return ResponseEntity.ok(${table.serviceName?uncap_first}.removeById(id));
    }

}


