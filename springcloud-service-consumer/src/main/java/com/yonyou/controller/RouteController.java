package com.yonyou.controller;

import com.yonyou.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author zhangyu18
 */
@RequestMapping(value = "/route")
@RestController
@Slf4j
public class RouteController {
    @Value("${woa_url}")
    private String woaUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public RouteController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 统一转发入口
     */
    @RequestMapping(value = "api/**")
    private Object route(HttpServletRequest request, HttpServletResponse response) {

        String msg = "请求异常,具体请查看系统日志.....";
        Object result;
        try {
            result = dispatch(request, response);
        } catch (ServletException e) {
            log.error("转发异常：" + e.getMessage());
            return msg;
        }

        return result;
    }

    /**
     * 请求的转发
     */
    private Object dispatch(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        RequestEntity<byte[]> requestEntity;
        try {
            String prefix = "/route/api";
            String requestUri = req.getRequestURI().replace(" ", "");
            String path = requestUri.substring(prefix.length());
            String url = woaUrl + path;
            String queryString = req.getQueryString();
            if (queryString != null) {
                url += "?" + queryString;
            }
            log.info(url);
            requestEntity = this.createRequestEntity(req, url);
        } catch (Exception e) {
            log.error("构造request请求实体报错:" + e);
            return null;
        }
        String msg = "非401鉴权不通过的其他状态错误,请根据日志定位";
        try {
            ResponseEntity<String> result = restTemplate.exchange(requestEntity, String.class);
            createResponseHeaders(res, result);
            if (200 == result.getStatusCodeValue()) {
                return result.getBody();
            }
        } catch (RestClientResponseException e) {
            //捕获非200状态码异常
            //获取接口返回状态码
            int stu = e.getRawStatusCode();
            if (401 == stu) {
                log.error("鉴权不通过.......");
                msg = "鉴权不通过......";
            } else {
                log.error("非401鉴权不通过的其他状态错误,请根据日志定位", e);
            }
        }
        return msg;
    }

    /**
     * 构造response请求头
     */
    private void createResponseHeaders(HttpServletResponse res, ResponseEntity<String> result) {
        HttpHeaders httpHeaders = result.getHeaders();
        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                res.addHeader(headerName, headerValue);
            }
        }
    }

    /**
     * 构造request实体
     */
    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest request, String url)
            throws URISyntaxException, IOException {
        String method = request.getMethod();
        //获取请求方式
        HttpMethod httpMethod = HttpMethod.resolve(method);
        //处理header部分
        MultiValueMap<String, String> headers = createRequestHeaders(request);
        //处理body部分
        byte[] body = createRequestBody(request);
        //构造request
        return new RequestEntity<>(body, headers, httpMethod, new URI(url));
    }

    /**
     * 处理body部分
     */
    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    /**
     * 处理请求头
     */
    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("dptServer", "dptServer");
        String consumerIp = IpUtils.getIpAddr(request);
        log.info("客户端ip: " + consumerIp);
        headers.add("consumerIp", consumerIp);
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }


    public void uploadDispatch(ServletRequest request, ServletResponse res, String toUrl)
            throws ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存缓冲区，超过后写入临时文件
        factory.setSizeThreshold(4096);
        // 设置上传到服务器上文件的临时存放目录 -- 非常重要，防止存放到系统盘造成系统盘空间不足
        factory.setRepository(new File("./uploadFileTemp"));

        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setHeaderEncoding("utf-8");
        // 设置单个文件的最大上传值
        // 文件上传上限10G
        fileUpload.setSizeMax(10L * 1024 * 1024 * 1024);
        List<FileItem> fileItemList;
        try {
            fileItemList = fileUpload.parseRequest(req);
        } catch (FileUploadException e) {
            log.error("上传文件解析错误,{}", e.getMessage());
            throw new ServletException(e);
        }
        /*
         * 注意，在SpringMVC环境中，需要配置spring.servlet.multipart.enabled=false
         * 来去掉SpringMVC对上传操作的解析，否则这里得到的上传文件个数为0
         */
        if (fileItemList == null || fileItemList.size() == 0) {
            throw new ServletException("没有文件");
        }
        List<Object> fileList = new ArrayList<>();
        for (final FileItem fileItem : fileItemList) {
            log.info(">>>file name:{}", fileItem.getName());
            ByteArrayResource byteArr = new ByteArrayResource(fileItem.get()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return fileItem.getName();
                }
            };
            fileList.add(byteArr);
        }
        // 进行转发
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        if (fileList.size() == 1) {
            parts.add("file", fileList.get(0));
        } else {
            parts.add("file", fileList);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> mutiReq = new HttpEntity<>(parts, headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(toUrl, HttpMethod.POST, mutiReq, byte[].class,
                new HashMap<>(16));

        if (responseEntity.hasBody()) {
            try {
                ServletOutputStream outputStream = res.getOutputStream();
                outputStream.write(Objects.requireNonNull(responseEntity.getBody()));
                outputStream.flush();
            } catch (IOException e) {
                throw new ServletException(e);
            }
        }
    }

}
