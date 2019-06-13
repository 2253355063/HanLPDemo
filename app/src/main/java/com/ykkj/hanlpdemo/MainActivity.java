package com.ykkj.hanlpdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.ykkj.hanlpdemo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBD;

    String content="内存120MB以上（-Xms120m -Xmx120m -Xmn64m），标准数据包（35万核心词库+默认用户词典），分词测试正常。全部词典和模型都是惰性加载的，不使用的模型相当于不存在，可以自由删除。\n" +
            "HanLP对词典的数据结构进行了长期的优化，可以应对绝大多数场景。哪怕HanLP的词典上百兆也无需担心，因为在内存中被精心压缩过。如果内存非常有限，请使用小词典。HanLP默认使用大词典，同时提供小词典，请参考配置文件章节";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBD = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBD.button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //简单实例
                List<Term> termList= HanLP.segment(content);
                StringBuilder stringBuilder=new StringBuilder();
                for (Term term:termList){
                    stringBuilder.append(term.toString());
                }
                mBD.content.setText(stringBuilder.toString());
            }
        });
        mBD.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //标准分词
            }
        });

        mBD.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NLP分词
                List<Term> termList= NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？");
                StringBuilder stringBuilder=new StringBuilder();
                for (Term term:termList){
                    stringBuilder.append(term.toString());
                }
                mBD.content.setText(stringBuilder.toString());

            }
        });
        mBD.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //索引分词
                mBD.content.setText("");
                List<Term> termList = IndexTokenizer.segment("主副食品");
                for (Term term : termList)
                {
                    mBD.content.append(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
                }
            }
        });
        mBD.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //中国人名识别
                String[] testCase = new String[]{
                        "一桶冰水当头倒下，微软的比尔盖茨、Facebook的扎克伯格跟桑德博格、亚马逊的贝索斯、苹果的库克全都不惜湿身入镜，这些硅谷的科技人，飞蛾扑火似地牺牲演出，其实全为了慈善。",
                        "世界上最长的姓名是简森·乔伊·亚历山大·比基·卡利斯勒·达夫·埃利奥特·福克斯·伊维鲁莫·马尔尼·梅尔斯·帕特森·汤普森·华莱士·普雷斯顿。",
                };
                Segment segment = HanLP.newSegment().enableTranslatedNameRecognize(true);
                for (String sentence : testCase)
                {
                    List<Term> termList = segment.seg(sentence);
                    StringBuilder stringBuilder=new StringBuilder();
                    for (Term term:termList){
                        stringBuilder.append(term.toString());
                    }
                   mBD.content.setText(stringBuilder.toString());
                }
            }
        });
        mBD.button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBD.content.setText("");
                String text = "重载不是重任";
                List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
                System.out.print("原文,");
                for (char c : text.toCharArray())
                {
                    System.out.printf("%c,", c);
                }
                System.out.println();

                System.out.print("拼音（数字音调）,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin);
                }
                System.out.println();

                System.out.print("拼音（符号音调）,");
                for (Pinyin pinyin : pinyinList)
                {
                    mBD.content.append(pinyin.getPinyinWithToneMark());
                    System.out.printf("%s,", pinyin.getPinyinWithToneMark());
                }
                System.out.println();

                System.out.print("拼音（无音调）,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin.getPinyinWithoutTone());
                }
                System.out.println();

                System.out.print("声调,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin.getTone());
                }
                System.out.println();

                System.out.print("声母,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin.getShengmu());
                }
                System.out.println();

                System.out.print("韵母,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin.getYunmu());
                }
                System.out.println();

                System.out.print("输入法头,");
                for (Pinyin pinyin : pinyinList)
                {
                    System.out.printf("%s,", pinyin.getHead());
                }
                System.out.println();
            }
        });
        mBD.button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBD.content.setText("");
                mBD.content.append("用笔记本电脑写程序转繁体为：\n");
                mBD.content.append(HanLP.convertToTraditionalChinese("用笔记本电脑写程序"));
                mBD.content.append("「以後等妳當上皇后，就能買士多啤梨慶祝了」转简体为：\n");
                mBD.content.append(HanLP.convertToSimplifiedChinese("「以後等妳當上皇后，就能買士多啤梨慶祝了」"));
            }
        });
    }
}
