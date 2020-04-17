package com.gyj.yingx;



import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gyj.yingx.entity.Admin;
import com.gyj.yingx.service.AdminService;
import com.gyj.yingx.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxAppApplicationTests {

    @Autowired
    AdminService adminService;
    
    @Test
    public void contextLoads() {
        Admin admin = new Admin();
        String uuid = UUIDUtil.getUUID();
        admin.setId(uuid);
        admin.setPassword("123456");
        admin.setUsername("guoyunjie");
        adminService.insertAddmin(admin);
    }

    @Test
    public void tes(){
        List<Admin> admins = adminService.queryAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    @Test
    public void testdele(){
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fft2pfxzwE2P2qLez3N";
        String accessKeySecret = "vfpqevOMfwxGPEX7PRSJ9T5a7FoZof";

        String bucket="yingx-186";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucket, "video-screenshorts/1585916750151-泰山之旅.jpg");

// 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test
    public void stay(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "*****";
        String accessKeySecret = "*****";

        String bucket="yingx-186";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(bucket, "video-screenshorts/1585916750151-泰山之旅.jpg");
        System.out.println(found);

// 关闭OSSClient。
        ossClient.shutdown();

    }
}
