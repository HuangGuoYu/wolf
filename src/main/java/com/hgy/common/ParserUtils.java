package com.hgy.common;

import com.hgy.paser.node.Factor;
import com.hgy.paser.node.GrammerNode;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.LinkedList;

public class ParserUtils {
    // Prefix
    // Postfix
    public static String toPostfixExpression(GrammerNode node){

        if(node instanceof Factor) {
            return node.getToken().getValue();
        }

        ArrayList<String> prts = new ArrayList<>();
        for(GrammerNode child : node.getChildren()) {
            prts.add(toPostfixExpression(child));
        }
        String lexemeStr = node.getToken() != null ? node.getToken().getValue() : "";
        if(lexemeStr.length() > 0) {
            return StringUtils.join(prts, " ") + " " + lexemeStr;
        } else {
            return StringUtils.join(prts, " ");
        }
    }

    public static String toBFSString(GrammerNode root, int max) {

        LinkedList<GrammerNode> queue = new LinkedList<GrammerNode>();
        ArrayList<String> list = new ArrayList<String>();
        queue.add(root);

        int c = 0;
        while(queue.size() > 0 && c++ < max) {
            GrammerNode node = queue.poll();
            list.add(node.getLabel());
            for(GrammerNode child : node.getChildren()) {
                queue.add(child);
            }
        }
        return StringUtils.join(list, " ");
    }
}