package com.starsmobi;

import com.alibaba.fastjson.JSONArray;

/** 小工具类
 * Created by VINCENT on 2018-01-09.
 */
public class ToolUntils {

    /**
     *  数组内数组位置置换
     * @param  s = "[[[1,2],[2,3]],[[5,4],[8,7],[2,9]]]"
     * @return s2 = "[[[2,1],[3,2]],[[4,5],[7,8],[9,2]]]"
     */
    public static JSONArray stringPositionReplacement(String s){

        StringBuffer s2 = new StringBuffer();
        for (int j = 0; j<s.length();j++ ){
            if (s.charAt(j)=='[' || s.charAt(j)==']' || s.charAt(j)==','){
                if (s.charAt(j)==',' && s.charAt(j+1)=='[')
                {
                    s2.append(s.charAt(j));
                }
                if (s.charAt(j)=='[' || s.charAt(j)==']'){
                    s2.append(s.charAt(j));
                }
            }else {
                if (s.charAt(j+1)==','){
                    s2.append(s.charAt(j+2));
                    s2.append(',');
                    s2.append(s.charAt(j));
                }
            }
        }

        JSONArray arrays = JSONArray.parseArray(s2.toString());
        return arrays;
    }
}
