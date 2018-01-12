package com.starsmobi;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 小工具类
 * Created by VINCENT on 2018-01-09.
 */
public class ToolUntils {

    /**
     *  数组内数组位置置换
     * @param  s = "[[[1,2],[2,3]],[[5,4],[8,7],[2,9]]]"
     * @return s2 = "[[[2,1],[3,2]],[[4,5],[7,8],[9,2]]]"
     */
    public static Object[] stringPositionReplacement(String s){

        JSONArray arrays = JSONArray.parseArray(s);
        List<Object> cc = new ArrayList<>();
        for (int i= 0; i< arrays.size();i++){
            List<Object> c = new ArrayList<>();
            JSONArray rr = (JSONArray)arrays.get(i);

            Object last = null;
            for (int j =0; j< rr.size();j++){
                JSONArray rs = (JSONArray)rr.get(j);
                List<Double> rt = rs.toJavaList(Double.class);
                Collections.reverse(rt);


                if (j==0){
                    last = rt.toArray();
                }
                c.add(rt.toArray());

                if (j==rr.size()-1){
                    c.add(last);
                }
            }
            cc.add(c.toArray());
        }
        Object[] tt = cc.toArray();
        return tt;
    }
}
