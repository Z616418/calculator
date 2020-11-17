package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button[] Commandbtn=new Button[7];//符号的运算符
    private Button[] Numbtn=new Button[11];//用于保存数值变量（包含小数点）
    private EditText edit=null;//编辑框变量
    private Button Clearbtn=null;
    private static String lastCommand;//用于保存运算符
    private boolean clearFlag;//用于判断是否清空显示区域的值，true需要，false不需要
    private boolean firstFlag;//用于判断是否是首次输入,true为首次，false不是首次
    private double result;//计算结果
    public MainActivity()
    {
        result=0;
        firstFlag=true;
        clearFlag=false;
        lastCommand="=";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取运算符和数字

        Commandbtn[0]=(Button)findViewById(R.id.sub);//-键
        Commandbtn[1]=(Button)findViewById(R.id.mod);//%键
        Commandbtn[2]=(Button)findViewById(R.id.div);//除法键
        Commandbtn[3]=(Button)findViewById(R.id.multi);//乘法键
        Commandbtn[4]=(Button)findViewById(R.id.add);//加法键
        Commandbtn[5]=(Button)findViewById(R.id.equal);//等号键
        Numbtn[0]=(Button)findViewById(R.id.num0);//0键
        Numbtn[1]=(Button)findViewById(R.id.num1);//1键
        Numbtn[2]=(Button)findViewById(R.id.num2);//2键
        Numbtn[3]=(Button)findViewById(R.id.num3);//3键
        Numbtn[4]=(Button)findViewById(R.id.num4);//4键
        Numbtn[5]=(Button)findViewById(R.id.num5);//5键
        Commandbtn[6]=(Button)findViewById(R.id.sub);//减法键
        Numbtn[6]=(Button)findViewById(R.id.num6);//6键
        Numbtn[7]=(Button)findViewById(R.id.num7);//7键

        Numbtn[8]=(Button)findViewById(R.id.num8);//8键
        Numbtn[9]=(Button)findViewById(R.id.num9);//9键
        Numbtn[10]=(Button)findViewById(R.id.point);//小数点键

        edit=(EditText)findViewById(R.id.resultedit);
        //初始化显示编辑框的结果
        edit.setText("0");
        //实例化监听器对象
        NumberAction na=new NumberAction();
        CommandAction ca=new CommandAction();
        for(Button bc:Commandbtn){
            bc.setOnClickListener((View.OnClickListener)ca);
        }
        for(Button bc:Numbtn){
            bc.setOnClickListener((View.OnClickListener)na);
        }
        Clearbtn=(Button)findViewById(R.id.clearbtn);//AC键
        Clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText("0");
                result=0;//x的值
                firstFlag=true;//首次运算
                clearFlag=false;//不需要清空
                lastCommand="=";
            }
        });
        //clear按钮的动作
    }
    //数字按钮监听器
    private class NumberAction implements View.OnClickListener {

        @Override
        public void onClick(View view)
        {

            Button btn=(Button)view;
            String input=btn.getText().toString();
            if(firstFlag)//系统判定首次输入
            {//如果输入小数点就什么也不做
                if(input.equals("."))
                {
                    return;
                }
                if(edit.getText().toString().equals("0"))
                {
                    edit.setText("");
                }
                firstFlag=false;//改变是否首次输入的标记值
            }
            else
            {
                String editTextStr=edit.getText().toString();
                //判断显示区域的值里面是否已有"."，如果有输入的又是.，就什么都不做
                if(editTextStr.indexOf(".")!=-1&&input.equals("."))
                {
                    return;
                }
                //显示区域里只有“-”,输入的又是"."，就什么都不做
                if(editTextStr.equals("-")&&input.equals(".")){
                    return;
                }
                if(editTextStr.equals("0")&&!input.equals("."))
                {
                    return;
                }
            }
            //如果我点击运算符以后，再输入数字的话，就要清空显示区域的值
            if(clearFlag)
            {
                edit.setText("");
                clearFlag=false;
            }
            edit.setText(edit.getText().toString()+input);//设置显示区域的值
        }
    }

    //符号按钮监听器
    private class CommandAction implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button btn=(Button)view;
            String inputCommand=(String)btn.getText();
            if(firstFlag){//首次输入-的情况
                if(inputCommand.equals("-")) {
                    edit.setText("-");//显示区域的内容设置为"-"
                    firstFlag = false;
                }
            }
            else {
                if(!clearFlag){
                    //如果flag=false不需要清空显示区的值，就调用方法计算
                        calculate1(Double.parseDouble(edit.getText().toString()));//保存显示区的值并计算
                }

                //保存你的运算符
                lastCommand=inputCommand;
                clearFlag=true;
            }
        }
    }
    public void calculate1(double x)
    {
        //lastCommand是空值没有发生实质性的结果
        if(lastCommand.equals("+")){
            result+=x;//做加法
        }
        else if(lastCommand.equals("-")){
            result-=x;//做减法
        }
        else if(lastCommand.equals("×")){
            result*=x;//做乘法
        }
        else if(lastCommand.equals("÷")){
            if(x==0)
            {
                Toast.makeText(MainActivity.this,"除数不能为零",Toast.LENGTH_LONG).show();
            }
            result/=x;
        }
        else if(lastCommand.equals("mod"))
        {
            result%=x;
        }
        else if(lastCommand.equals("=")){
            result=x;
        }
        edit.setText(""+result);

    }


}