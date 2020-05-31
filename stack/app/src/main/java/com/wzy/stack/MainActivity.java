package com.wzy.stack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }


    //递归的先序遍历实现
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode t = root.left;
            root.left = root.right;
            root.right = t;
            invertTree(root.left);
            invertTree(root.right);
            return root;
        } else {
            return null;
        }
    }

    public TreeNode invertTreeNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        invertTreeNode(root.left);
        invertTreeNode(root.right);
        return root;
    }

    public static class TreeNode {
        private int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }


        public TreeNode(int value, TreeNode left, TreeNode right) {
            this(value);
            this.left = left;
            this.right = right;
        }
    }

    private void test() {
        CustomArrayStack stack = new CustomArrayStack();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Object pop = stack.pop();
            Log.d(TAG, "test: pop = " + pop);
        }


    }
}
