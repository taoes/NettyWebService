package com.seven.web.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seven.web.core.common.enums.ContentType;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 09:56
 */
public class HttpRequestUtils {

  public static Map<String, List<String>> getParameterMap(HttpRequest httpRequest) {
    HttpMethod method = httpRequest.method();
    Map<String, List<String>> map = new HashMap<>();
    if (HttpMethod.GET.equals(method)) {
      String uri = httpRequest.uri();
      QueryStringDecoder stringDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
      map = stringDecoder.parameters();
    } else if (HttpMethod.POST.equals(method)) {
      FullHttpRequest request = (FullHttpRequest) httpRequest;
      map = getPostParameterMap(request);
    }
    return map;
  }

  private static Map<String, List<String>> getPostParameterMap(FullHttpRequest fullRequest) {
    Map<String, List<String>> paramMap = new HashMap<>();
    HttpHeaders headers = fullRequest.headers();
    String contentType = getContentType(headers);
    if (ContentType.APPLICATION_JSON.toString().equals(contentType)) {
      String jsonStr = fullRequest.content().toString(CharsetUtil.UTF_8);
      JSONObject obj = JSON.parseObject(jsonStr);
      for (Map.Entry<String, Object> item : obj.entrySet()) {
        String key = item.getKey();
        Object value = item.getValue();
        Class<?> valueType = value.getClass();

        List<String> valueList;
        if (paramMap.containsKey(key)) {
          valueList = paramMap.get(key);
        } else {
          valueList = new ArrayList<String>();
        }

        if (PrimitiveTypeUtil.isPriType(valueType)) {
          valueList.add(value.toString());
          paramMap.put(key, valueList);

        } else if (valueType.isArray()) {
          int length = Array.getLength(value);
          for (int i = 0; i < length; i++) {
            String arrayItem = String.valueOf(Array.get(value, i));
            valueList.add(arrayItem);
          }
          paramMap.put(key, valueList);

        } else if (List.class.isAssignableFrom(valueType)) {
          if (valueType.equals(JSONArray.class)) {
            JSONArray jArray = JSONArray.parseArray(value.toString());
            for (int i = 0; i < jArray.size(); i++) {
              valueList.add(jArray.getString(i));
            }
          } else {
            valueList = (ArrayList<String>) value;
          }
          paramMap.put(key, valueList);

        } else if (Map.class.isAssignableFrom(valueType)) {
          Map<String, String> tempMap = (Map<String, String>) value;
          for (Map.Entry<String, String> entry : tempMap.entrySet()) {
            List<String> tempList = new ArrayList<String>();
            tempList.add(entry.getValue());
            paramMap.put(entry.getKey(), tempList);
          }
        }
      }

    } else if (ContentType.APPLICATION_FORM_URLENCODED.toString().equals(contentType)) {
      String jsonStr = fullRequest.content().toString(CharsetUtil.UTF_8);
      QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
      paramMap = queryDecoder.parameters();
    }

    return paramMap;
  }

  private static String getContentType(HttpHeaders httpHeaders) {
    String contentType = httpHeaders.get(HttpHeaderNames.CONTENT_TYPE);
    String[] split = contentType.split(";");
    if (split.length > 1) {
      return split[0];
    }
    return null;
  }
}
