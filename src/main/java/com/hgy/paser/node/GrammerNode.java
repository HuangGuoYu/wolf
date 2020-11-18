package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.lexer.Token;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class GrammerNode {

    protected Token token; // 词法单元
    protected String label; // 备注(标签)
    protected GrammerNodeTypes type;

    //子节点
    protected List<GrammerNode> children = new ArrayList<>();
    //父节点
    protected GrammerNode parent;


    public GrammerNode() {
    }

    public GrammerNode(GrammerNodeTypes type, String label) {
        this.type = type;
        this.label = label;
    }

    public GrammerNode getChild(int index) {
        if(index >= this.children.size()) {
            return null;
        }
        return this.children.get(index);
    }

    public void addChild(GrammerNode node) {
        node.parent = this;
        children.add(node);
    }
}
